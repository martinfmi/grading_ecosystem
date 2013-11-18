package fmi.uni.grading.server.shared.services;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;

import fmi.uni.grading.server.shared.db.UserDAO;
import fmi.uni.grading.shared.beans.User;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.exceptions.MissingResourceException;
import fmi.uni.grading.shared.services.IUserService;

public class UserService implements IUserService {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<User> getUsers() throws AbstractServerException {
		List<User> users;
		try {
			users = userDAO.getUsers(mongoTemplate);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return users;
	}

	public User getUser(String id) throws AbstractServerException {
		User user = null;
		try {
			user = userDAO.getUser(mongoTemplate, id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		if (user == null) {
			throw new MissingResourceException(String.format(
					"No user with ID '%s' found.", id));
		}
		return user;
	}

	public User createUser(User user) throws AbstractServerException {

		if (user.getId() != null) {
			throw new BadRequestException(
					"User ID must not be specified upon creation.");
		}
		checkUser(user);

		User createdUser = null;
		try {
			createdUser = userDAO.createUser(mongoTemplate, user);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return createdUser;
	}

	public User editUser(User user) throws AbstractServerException {

		if (user.getId() == null) {
			throw new BadRequestException("No user ID provided.");
		}

		checkUser(user);

		User editedUser = null;
		try {
			editedUser = userDAO.editUser(mongoTemplate, user);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return editedUser;
	}

	public void deleteUser(String id) {
		try {
			userDAO.deleteUser(mongoTemplate, id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	private void checkUser(User user) {

		if (user.getHandle() == null) {
			throw new BadRequestException("No handle provided.");
		}

		if (user.getName() != null) {
			throw new BadRequestException("No name provided.");
		}

		if (user.getPass() == null) {
			throw new BadRequestException("No password provided.");
		}

		String pass = user.getPass();
		checkPass(pass);

		if (user.getRole() == null) {
			throw new BadRequestException("No permissions provided");
		}
	}

	private void checkPass(String pass) {
		if (pass.length() < 7) {
			throw new BadRequestException(
					"Password must be at least 7 characters long");
		} else {
			boolean upper = false;
			boolean lower = false;
			boolean number = false;
			for (char c : pass.toCharArray()) {
				if (Character.isUpperCase(c)) {
					upper = true;
				} else if (Character.isLowerCase(c)) {
					lower = true;
				} else if (Character.isDigit(c)) {
					number = true;
				}
			}

			if (!upper) {
				throw new BadRequestException(
						"Password must contain at least one upper case letter.");
			} else if (!lower) {
				throw new BadRequestException(
						"Password must contain at least one lower case letter.");
			} else if (!number) {
				throw new BadRequestException(
						"Password must contain at least one number.");
			}
		}
	}
}
