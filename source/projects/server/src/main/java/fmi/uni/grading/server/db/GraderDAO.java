package fmi.uni.grading.server.db;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.server.ServerCache;
import fmi.uni.grading.shared.beans.GraderInstance;

public class GraderDAO {

	public List<GraderInstance> getGraderInstance() {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		List<GraderInstance> graderInstances = mongoTemplate
				.findAll(GraderInstance.class);
		return graderInstances;
	}

	public GraderInstance getGraderInstance(String id) {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		GraderInstance graderInstance = mongoTemplate.findOne(query,
				GraderInstance.class);
		return graderInstance;
	}

	public GraderInstance createGraderInstance(GraderInstance graderInstance) {

		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		mongoTemplate.insert(graderInstance);

		return graderInstance;
	}

	public GraderInstance editGraderInstance(GraderInstance graderInstance) {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		mongoTemplate.save(graderInstance);
		return graderInstance;
	}

	public void deleteGraderInstance(String id) {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, GraderInstance.class);
	}
}
