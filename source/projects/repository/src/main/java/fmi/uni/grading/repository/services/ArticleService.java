package fmi.uni.grading.repository.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fmi.uni.grading.repository.db.ArticleDAO;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.services.repository.IArticleService;

public class ArticleService implements IArticleService {

	@Autowired
	private ArticleDAO articleDAO;

	public List<Article> getArticles() throws AbstractServerException {
		List<Article> articles = articleDAO.getArticles();
		return articles;
	}

	public Article getArticle(String id) throws AbstractServerException {
		Article article = articleDAO.getArticle(id);
		return article;
	}

	public Article createArticle(Article article) {
		return null;
	}

	public Article editArticle(Article article) {
		Article editedArticle = articleDAO.editArticle(article);
		return editedArticle;
	}

	public void deleteArticle(String id) {
		// TODO Auto-generated method stub

	}

}
