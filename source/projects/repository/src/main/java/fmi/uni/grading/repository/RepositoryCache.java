package fmi.uni.grading.repository;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

import fmi.uni.grading.repository.configuration.Configuration;
import fmi.uni.grading.repository.configuration.MongoInstance;

/**
 * Stores global information about the repository.
 * 
 * @author Martin Toshev
 */
public class RepositoryCache {

	private static Configuration configuration;

	private static Mongo replica;

	private static MongoTemplate mongoTemplate;

	public static void init(Configuration configuration) throws RepositoryError {
		RepositoryCache.configuration = configuration;
		List<MongoInstance> configReplica = configuration.getMongoInstances();
		if (configReplica == null || configReplica.isEmpty()) {
			throw new RepositoryError(
					"MongoDB replica is empty - please check your configuration.");
		}

		List<ServerAddress> mongoReplica = new ArrayList<ServerAddress>();

		for (MongoInstance instance : configReplica) {
			if (instance.getHost() == null) {
				throw new RepositoryError(
						"No MongoDB instance host specified - please check your configuration.");
			}

			try {
				mongoReplica.add(new ServerAddress(instance.getHost(), instance
						.getPort()));
			} catch (UnknownHostException e) {
				throw new RepositoryError(e);
			}
		}

		RepositoryCache.replica = new Mongo(mongoReplica);
		RepositoryCache.mongoTemplate = new MongoTemplate(replica,
				configuration.getDbName());
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
}
