package fmi.uni.grading.shared.client.repository;

import java.util.List;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.repository.IArticleService;

public class ArticleClient extends AbstractClient<IArticleService> {
	
	public ArticleClient(String url, String user, String password) {
		super(url, IArticleService.class, user, password);
	}
	
	public List<Article> getArticles() throws ServerResponseException {
		List<Article> articles = null;
		try {
			articles = getService().getArticles();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return articles;
	}

	public Article getArticle(String id) throws ServerResponseException {

		Article article = null;
		try {
			article = getService().getArticle(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return article;
	}

	public Article createArticle(String title, String format, String content,
			List<String> authors, List<String> categories, boolean isVisible,
			String ref) throws ServerResponseException {
		Article article = buildArticle(null, title, format, content, authors,
				categories, isVisible, ref);
		Article createdArticle = createArticle(article);
		return createdArticle;
	}

	public Article createArticle(Article article)
			throws ServerResponseException {
		Article createdArticle = null;
		try {
			createdArticle = getService().createArticle(article);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}

		return createdArticle;
	}

	public Article editArticle(String id, String title, String format,
			String content, List<String> authors, List<String> categories,
			boolean isVisible, String ref) throws ServerResponseException {
		Article article = buildArticle(id, title, format, content, authors,
				categories, isVisible, ref);
		Article editedArticle = editArticle(article);
		return editedArticle;
	}

	public Article editArticle(Article article) throws ServerResponseException {
		Article editedArticle = null;
		try {
			editedArticle = getService().editArticle(article);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}

		return editedArticle;
	}

	public void deleteArticle(String id) throws ServerResponseException {
		try {
			getService().deleteArticle(id);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}
	}

	private Article buildArticle(String id, String title, String format,
			String content, List<String> authors, List<String> categories,
			boolean isVisible, String ref) {
		Article article = new Article();
		article.setId(id);
		article.setTitle(title);
		article.setFormat(format);
		article.setContent(content);
		article.setAuthors(authors);
		article.setCategories(categories);
		article.setVisible(isVisible);
		article.setRef(ref);
		return article;
	}
}
