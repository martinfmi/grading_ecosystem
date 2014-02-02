package fmi.uni.grading.repository.db;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.repository.RepositoryCache;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.beans.Category;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.exceptions.BadRequestException;

public class CategoryDAO {
	
	public List<Category> getRootCategories() {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query();
		query.fields().include("id").include("name");
		query.addCriteria(Criteria.where("parent").exists(false));
		List<Category> categories = mongoTemplate.find(query, Category.class);
		return categories;
	}

	public Category getCategory(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		Category category = mongoTemplate.findOne(query, Category.class);
		return category;
	}

	public Category getCategoryByName(String name) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("name").is(name));
		Category category = mongoTemplate.findOne(query, Category.class);
		return category;
	}

	public List<Category> getChildCategories(String parentId) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query();
		query.fields().include("id").include("name");
		query.addCriteria(Criteria.where("parent").is(parentId));
		List<Category> categories = mongoTemplate.find(query, Category.class);
		return categories;
	}

	public Category createCategory(Category category) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(category);
		return category;
	}

	public Category editCategory(Category category) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.save(category);
		return category;
	}

	public void deleteCategory(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, Category.class);
	}

	public List<Problem> getCategoryProblems(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		Category category = mongoTemplate.findOne(query, Category.class);

		if (category == null) {
			throw new BadRequestException(String.format(
					"No category with id: '%s' ", id));
		}

		query = new Query();
		query.fields().include("id").include("title");
		query.addCriteria(Criteria.where("categories").in(category.getName()));
		List<Problem> problems = mongoTemplate.find(query, Problem.class);
		return problems;
	}

	public List<Article> getCategoryArticles(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		Query query = new Query(Criteria.where("id").is(id));
		Category category = mongoTemplate.findOne(query, Category.class);

		if (category == null) {
			throw new BadRequestException(String.format(
					"No category with id: '%s' ", id));
		}
		
		query = new Query();
		query.fields().include("id").include("title");
		query.addCriteria(Criteria.where("categories").in(category.getName()));
		List<Article> articles = mongoTemplate.find(query, Article.class);
		return articles;
	}
}
