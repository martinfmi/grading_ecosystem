package fmi.uni.grading.repository.services;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import fmi.uni.grading.repository.db.ArticleDAO;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.exceptions.MissingResourceException;
import fmi.uni.grading.shared.services.repository.IArticleService;

public class ArticleService implements IArticleService {

	@Context
	private UriInfo uriInfo;
	
	@Autowired
	private ArticleDAO articleDAO;

	public List<Article> getArticles() throws AbstractServerException {
		List<Article> articles;
		try {
			articles = articleDAO.getArticles();
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return articles;
	}

	public Article getArticle(String id) throws AbstractServerException {
		Article article = null;
		try {
			article = articleDAO.getArticle(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		if (article == null) {
			throw new MissingResourceException(String.format(
					"No article with ID '%s' found in repository.", id));
		}
		return article;
	}

	public Article createArticle(Article article)
			throws AbstractServerException {
		if (article.getTitle() == null) {
			throw new BadRequestException("No article title provided.");
		}

		if (article.getId() != null) {
			throw new BadRequestException(
					"Article ID must not be specified upon creation.");
		}

		Article createdArticle = null;
		try {
			createdArticle = articleDAO.createArticle(article);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return createdArticle;
	}

	public Article editArticle(Article article) throws AbstractServerException {
		if (article.getId() == null) {
			throw new BadRequestException("No article ID provided.");
		}

		if (article.getTitle() == null) {
			throw new BadRequestException("No article title provided.");
		}

		Article editedArticle = null;
		try {
			editedArticle = articleDAO.editArticle(article);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return editedArticle;
	}
	
	public void deleteArticle(String id) {
		try {
			articleDAO.deleteArticle(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}
