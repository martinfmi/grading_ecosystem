package fmi.uni.grading.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

import fmi.uni.grading.server.configuration.Configuration;
import fmi.uni.grading.server.configuration.MongoInstance;

public class CacheUtil {

	private static Configuration configuration;

	private static Mongo replica;

	public static void init(Configuration configuration) throws ServerError {
		CacheUtil.configuration = configuration;
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

		CacheUtil.replica = new Mongo(mongoReplica);
	}

	public static Mongo getReplica() {
		return replica;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static boolean isMaster() {
		return Configuration.TYPE_MASTER.equals(configuration.getServerType());
	}

	public static boolean isSlave() {
		return Configuration.TYPE_SLAVE.equals(configuration.getServerType());
	}
}
