package fmi.uni.grading.server.shared.db;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.shared.beans.User;

public class UserDAO {

	public List<User> getUsers(MongoTemplate mongoTemplate) {
		Query query = new Query();
		query.fields().exclude("pass");
		List<User> users = mongoTemplate.find(query, User.class);
		return users;
	}

	public User getUser(MongoTemplate mongoTemplate, String id) {
		Query query = new Query(Criteria.where("id").is(id));
		User user = mongoTemplate.findOne(query, User.class);
		return user;
	}

	public User createUser(MongoTemplate mongoTemplate, User user) {
		mongoTemplate.insert(user);
		return user;
	}

	public User editUser(MongoTemplate mongoTemplate, User user) {
		mongoTemplate.save(user);
		return user;
	}

	public void deleteUser(MongoTemplate mongoTemplate, String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, User.class);
	}
}
