package fmi.uni.grading.shared.client.repository;

import java.util.List;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.beans.Category;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.repository.ICategoryService;

public class CategoryClient extends AbstractClient<ICategoryService> {

	public CategoryClient(String url, String user, String password) {
		super(url, ICategoryService.class, user, password);
	}

	public List<Category> getRootCategories() throws ServerResponseException {
		List<Category> categories = null;
		try {
			categories = getService().getRootCategories();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return categories;
	}

	public Category getCategory(String id) throws ServerResponseException {
		Category category = null;
		try {
			category = getService().getCategory(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return category;
	}

	public List<Category> getChildCategories(String parentId)
			throws ServerResponseException {
		List<Category> categories = null;
		try {
			categories = getService().getChildCategories(parentId);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return categories;
	}

	public Category createCategory(String name, String description,
			String parent) throws ServerResponseException {
		Category category = buildCategory(null, name, description, parent);
		Category createdCategory = createCategory(category);
		return createdCategory;
	}

	public Category createCategory(Category category)
			throws ServerResponseException {
		Category createdCategory = null;
		try {
			createdCategory = getService().createCategory(category);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return createdCategory;
	}

	public Category editCategory(String id, String name, String description,
			String parent) throws ServerResponseException {
		Category category = buildCategory(id, name, description, parent);
		Category editedCategory = editCategory(category);
		return editedCategory;
	}

	public Category editCategory(Category category)
			throws ServerResponseException {
		Category editedCategory = null;
		try {
			editedCategory = getService().editCategory(category);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return editedCategory;
	}

	public void deleteCategory(String id) throws ServerResponseException {
		try {
			getService().deleteCategory(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
	}

	public List<Problem> getCategoryProblems(String id)
			throws ServerResponseException {
		List<Problem> problems = null;

		try {
			problems = getService().getCategoryProblems(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return problems;
	}

	public List<Article> getCategoryArticles(String id)
			throws ServerResponseException {
		List<Article> articles = null;

		try {
			articles = getService().getCategoryArticles(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}

		return articles;
	}

	private Category buildCategory(String id, String name, String description,
			String parent) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		category.setDecription(description);
		category.setParent(parent);
		return category;
	}
}
