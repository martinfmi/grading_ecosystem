package fmi.uni.grading.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

import fmi.uni.grading.server.configuration.Configuration;
import fmi.uni.grading.server.configuration.MongoInstance;

/**
 * Stores global information about the server.
 * 
 * @author Martin Toshev
 */
public class ServerCache {

	private static Configuration configuration;

	private static Mongo replica;

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
	}

	public static Mongo getReplica() {
		return replica;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}
}
