package fmi.uni.grading.shared.client.repository;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.repository.IArticleService;

public class ArticleClient extends AbstractClient<IArticleService> {

	public ArticleClient(String url, String user, String password) {
		super(url, IArticleService.class, user, password);
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

	public static void main(String[] args) throws ServerResponseException {
		ArticleClient client = new ArticleClient(
				"http://localhost:6608/services", "test", "test");
		Article article = client.getArticle("ttt");
		System.err.println(article.getTitle());
	}
}
