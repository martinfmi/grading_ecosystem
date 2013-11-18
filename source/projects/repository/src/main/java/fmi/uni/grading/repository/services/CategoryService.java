package fmi.uni.grading.repository.services;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import fmi.uni.grading.repository.db.CategoryDAO;
import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.beans.Category;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.services.repository.ICategoryService;

public class CategoryService implements ICategoryService {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private CategoryDAO categoryDAO;

	public List<Category> getRootCategories() throws AbstractServerException {
		List<Category> categories = null;
		try {
			categories = categoryDAO.getRootCategories();
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return categories;
	}

	public Category getCategory(String id) throws AbstractServerException {
		Category categories = null;
		try {
			categories = categoryDAO.getCategory(id);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return categories;
	}

	public List<Category> getChildCategories(String parentId)
			throws AbstractServerException {
		List<Category> categories = null;
		try {
			categories = categoryDAO.getChildCategories(parentId);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return categories;
	}

	public Category createCategory(Category category)
			throws AbstractServerException {

		if (category.getName() == null) {
			throw new BadRequestException("No category name provided.");
		}

		if (category.getId() != null) {
			throw new BadRequestException(
					"Category ID must not be specified upon creation.");
		}

		Category categories = null;
		try {
			categories = categoryDAO.createCategory(category);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return categories;
	}

	public Category editCategory(Category category)
			throws AbstractServerException {
		if (category.getId() == null) {
			throw new BadRequestException("No category ID provided.");
		}

		if (category.getName() == null) {
			throw new BadRequestException("No category name provided.");
		}

		Category categories = null;
		try {
			categories = categoryDAO.editCategory(category);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		return categories;
	}

	public void deleteCategory(String id) throws AbstractServerException {
		try {
			categoryDAO.deleteCategory(id);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	public List<Problem> getCategoryProblems(String id)
			throws AbstractServerException {

		List<Problem> problems = null;
		try {
			problems = categoryDAO.getCategoryProblems(id);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}

		return problems;
	}

	public List<Article> getCategoryArticles(String id)
			throws AbstractServerException {
		List<Article> articles = null;
		try {
			articles = categoryDAO.getCategoryArticles(id);
		} catch (DataAccessException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		
		return articles;
	}
}
