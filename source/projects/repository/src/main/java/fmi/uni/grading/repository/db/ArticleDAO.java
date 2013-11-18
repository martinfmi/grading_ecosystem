package fmi.uni.grading.repository.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fmi.uni.grading.repository.RepositoryCache;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.exceptions.MissingResourceException;

public class ArticleDAO {

	@Autowired
	private CategoryDAO categoryDAO;

	public List<Article> getArticles() {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query();
		query.fields().include("id").include("title");
		List<Article> articles = mongoTemplate.find(query, Article.class);
		return articles;
	}

	public Article getArticle(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		Query query = new Query(Criteria.where("id").is(id));
		Article article = mongoTemplate.findOne(query, Article.class);
		return article;
	}

	public Article createArticle(Article article) {
		checkForMissingCategory(article);

		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.insert(article);

		return article;
	}

	public Article editArticle(Article article) {
		checkForMissingCategory(article);

		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();
		mongoTemplate.save(article);

		return article;
	}

	public void deleteArticle(String id) {
		MongoTemplate mongoTemplate = RepositoryCache.getMongoTemplate();

		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, Article.class);
	}

	private void checkForMissingCategory(Article article) {
		if (article.getCategories() != null) {
			for (String category : article.getCategories()) {
				if (categoryDAO.getCategoryByName(category) == null) {
					throw new MissingResourceException(String.format(
							"No category with name '%s' found in repository.",
							category));
				}
			}
		}
	}
}
