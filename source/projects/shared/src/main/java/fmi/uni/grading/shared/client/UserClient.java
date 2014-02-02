package fmi.uni.grading.shared.client;

import java.util.List;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.User;
import fmi.uni.grading.shared.beans.User.Permissions;
import fmi.uni.grading.shared.beans.User.Role;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.IUserService;

public class UserClient extends AbstractClient<IUserService> {

	public UserClient(String url, String user, String password) {
		super(url, IUserService.class, user, password);
	}
	
	public List<User> getUsers() throws ServerResponseException {
		List<User> users = null;
		try {
			users = getService().getUsers();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return users;
	}

	public User getUser(String id) throws ServerResponseException {

		User user = null;
		try {
			user = getService().getUser(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return user;
	}

	public User createUser(String handle, String name, String pass,
			String email, Role role, String details, Permissions permissions)
			throws ServerResponseException {
		User user = buildUser(null, handle, name, pass, email, role, details,
				permissions);
		User createdUser = createUser(user);
		return createdUser;
	}
	
	public User createUser(User user) throws ServerResponseException {
		User createdUser = null;
		try {
			createdUser = getService().createUser(user);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}

		return createdUser;
	}

	public User editUser(String id, String handle, String name, String pass,
			String email, Role role, String details, Permissions permissions)
			throws ServerResponseException {
		User user = buildUser(id, handle, name, pass, email, role, details,
				permissions);
		User editedUser = editUser(user);
		return editedUser;
	}

	public User editUser(User user) throws ServerResponseException {
		User editedUser = null;
		try {
			editedUser = getService().editUser(user);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}

		return editedUser;
	}

	public void deleteUser(String id) throws ServerResponseException {
		try {
			getService().deleteUser(id);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}
	}

	private User buildUser(String id, String handle, String name, String pass,
			String email, Role role, String details, Permissions permissions) {

		User user = new User();
		user.setId(id);
		user.setHandle(handle);
		user.setName(name);
		user.setPass(pass);
		user.setEmail(email);
		user.setRole(role);
		user.setDetails(details);
		user.setPermissions(permissions);

		return user;
	}
}
