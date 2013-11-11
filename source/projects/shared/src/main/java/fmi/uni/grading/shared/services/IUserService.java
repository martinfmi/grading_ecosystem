package fmi.uni.grading.shared.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fmi.uni.grading.shared.beans.User;
import fmi.uni.grading.shared.exceptions.AbstractServerException;

/**
 * Provides operations for manipulation of the user accounts in the repository.
 * 
 * @author Martin Toshev
 */
@Path("users")
public interface IUserService {

	/**
	 * Retrieves a list of all users.
	 * 
	 * @return A list of {@link User} instances.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() throws AbstractServerException;

	/**
	 * Retrieves a particular user.
	 * 
	 * @param id
	 *            The user ID.
	 * @return An {@link User} instance.
	 * @throws AbstractServerException
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") String id)
			throws AbstractServerException;

	/**
	 * Creates a user.
	 * 
	 * @param user
	 *            The user data
	 * @return A {@link User} instance.
	 * @throws AbstractServerException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(User user)
			throws AbstractServerException;

	/**
	 * Edits a user.
	 * 
	 * @param user
	 *            The user data
	 * @return A {@link User} instance
	 * @throws AbstractServerException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User editUser(User user) throws AbstractServerException;

	/**
	 * Deletes a user.
	 * 
	 * @param id
	 *            The user ID
	 * @throws AbstractServerException
	 */
	@DELETE
	@Path("{id}")
	public void deleteUser(@PathParam("id") String id)
			throws AbstractServerException;

}
