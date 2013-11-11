package fmi.uni.grading.repository.db;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.repository.RepositoryCache;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.beans.Problem;

public class ArticleDAO {

	public List<Article> getArticles() {

		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		List<Article> articles = mongoTemplate.findAll(Article.class);
		return articles;
	}

	public Article getArticle(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		Article article = mongoTemplate.findOne(query, Article.class);
		return article;
	}

	public Article createArticle(Article article) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(article);

		Query query = new Query(Criteria.where("title").is(article.getTitle()));
		Article insertedArticle = mongoTemplate.findOne(query, Article.class);
		return insertedArticle;
	}

	public Article editArticle(Article article) {
		MongoOperations mongoOps = new MongoTemplate(
				RepositoryCache.getReplica(), RepositoryCache
						.getConfiguration().getDbName());
		
		Query searchUserQuery = new Query(Criteria.where("id").is(article.getId()));
		return null;
		// mongoOperation.updateFirst(searchUserQuery,
		// Update.update("password", "new password"),User.class);

		// mongoOps.updateFirst(arg0, arg1, ProblemBean.class);
	}

	public void deleteProblem(String id) {
		MongoOperations mongoOps = new MongoTemplate(
				RepositoryCache.getReplica(), RepositoryCache
						.getConfiguration().getDbName());

		Query query = new Query(Criteria.where("id").is(id));
		mongoOps.remove(query, Problem.class);
	}
}
