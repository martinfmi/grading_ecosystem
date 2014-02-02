package fmi.uni.grading.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import fmi.uni.grading.server.configuration.Configuration;
import fmi.uni.grading.server.configuration.MongoInstance;
import fmi.uni.grading.server.grader.Grader;
import fmi.uni.grading.shared.beans.GraderInstance;

/**
 * Stores global information about the server.
 * 
 * @author Martin Toshev
 */
public class ServerCache {

	private static Configuration configuration;

	private static Mongo replica;

	private static MongoTemplate mongoTemplate;

	private static ConcurrentHashMap<String, Grader> graderTypes = new ConcurrentHashMap<String, Grader>();

	private static ConcurrentHashMap<String, GraderInstance> graderInstances = new ConcurrentHashMap<String, GraderInstance>();

	public static void init(Configuration configuration) throws ServerError {
		ServerCache.configuration = configuration;
		List<MongoInstance> configReplica = configuration.getMongoInstances();
		if (configReplica == null || configReplica.isEmpty()) {
			System.err.println(configReplica);
			throw new ServerError(
					"MongoDB replica is empty - please check your configuration.");
		}

		List<ServerAddress> mongoReplica = new ArrayList<ServerAddress>();

		for (MongoInstance instance : configReplica) {
			if (instance.getHost() == null) {
				throw new ServerError(
						"No MongoDB instance host specified - please check your configuration.");
			}

			try {
				mongoReplica.add(new ServerAddress(instance.getHost(), instance
						.getPort()));
			} catch (UnknownHostException e) {
				throw new ServerError(e);
			}
		}

		ServerCache.replica = new Mongo(mongoReplica);
		ServerCache.mongoTemplate = new MongoTemplate(replica,
				configuration.getDbName());
		mongoTemplate.setWriteConcern(WriteConcern.SAFE);
	}

	public static Mongo getReplica() {
		return replica;
	}

	public static MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static Grader getGrader(String typeName) {
		return graderTypes.get(typeName);
	}

	public static void addGrader(String typeName, Grader grader) {
		Grader existingGrader = graderTypes.putIfAbsent(typeName, grader);
		if (existingGrader != null) {
			throw new ServerError(String.format(
					"Grader type: '%s' already exists in server. ", typeName));
		}
	}

	public static Set<String> getGraderTypes() {
		return graderTypes.keySet();
	}

	public static GraderInstance getGraderInstance(String id) {
		return graderInstances.get(id);
	}

	public static void addGraderInstance(GraderInstance instance) {
		GraderInstance existingInstance = graderInstances.putIfAbsent(
				instance.getId(), instance);
		if (existingInstance != null) {
			throw new ServerError(String.format(
					"Grader instance with id: '%s' already exists in server. ",
					instance.getId()));
		}
	}

	public static void updateGraderInstance(GraderInstance instance) {
		GraderInstance graderInstance = graderInstances.replace(
				instance.getId(), instance);
		if (graderInstance == null) {
			throw new ServerError(
					String.format(
							"Grader instance with id: '%s' does not exists in server. ",
							instance.getId()));
		}
	}

	public static void removeGraderInstance(String id) {
		graderInstances.remove(id);
	}

	public static Collection<GraderInstance> getGraderInstances() {
		Collection<GraderInstance> instances = graderInstances.values();
		return instances;
	}
}
