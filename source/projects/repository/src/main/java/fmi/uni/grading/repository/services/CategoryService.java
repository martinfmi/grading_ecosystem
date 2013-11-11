package fmi.uni.grading.repository.services;

import java.util.List;

import fmi.uni.grading.shared.beans.Article;
import fmi.uni.grading.shared.beans.Category;
import fmi.uni.grading.shared.beans.Problem;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.services.repository.ICategoryService;

public class CategoryService implements ICategoryService {

	public List<Category> getRootCategories() throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public Category getCategory(String id) throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Category> getChildCategories(String parentId)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public Category createCategory(Category category)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public Category editCategory(Category category)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteCategory(String id) throws AbstractServerException {
		// TODO Auto-generated method stub

	}

	public List<Problem> getCategoryProblems(String id)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Article> getCategoryArticles(String id)
			throws AbstractServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
