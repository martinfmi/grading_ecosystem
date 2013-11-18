package fmi.uni.grading.server.db;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.server.ServerCache;
import fmi.uni.grading.shared.beans.Tutorial;

public class TutorialDAO {

	public List<Tutorial> getTutorials() {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		Query query = new Query();
		query.fields().include("id").include("name");
		List<Tutorial> tutorials = mongoTemplate.find(query, Tutorial.class);
		return tutorials;
	}
	
	public Tutorial getTutorial(String id) {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		Tutorial tutorial = mongoTemplate.findOne(query, Tutorial.class);
		return tutorial;
	}

	public Tutorial createTutorial(Tutorial tutorial) {

		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		mongoTemplate.insert(tutorial);

		return tutorial;
	}

	public Tutorial editTutorial(Tutorial tutorial) {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		mongoTemplate.save(tutorial);

		return tutorial;
	}

	public void deleteTutorial(String id) {
		MongoTemplate mongoTemplate = ServerCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, Tutorial.class);
	}
}
