package fmi.uni.grading.server.services;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import fmi.uni.grading.server.db.TutorialDAO;
import fmi.uni.grading.shared.beans.Tutorial;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.BadRequestException;
import fmi.uni.grading.shared.exceptions.MissingResourceException;
import fmi.uni.grading.shared.services.server.ITutorialService;

public class TutorialService implements ITutorialService {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private TutorialDAO tutorialDAO;

	public List<Tutorial> getTutorials() throws AbstractServerException {
		List<Tutorial> tutorials;
		try {
			tutorials = tutorialDAO.getTutorials();
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return tutorials;
	}

	public Tutorial getTutorial(String id) throws AbstractServerException {
		Tutorial tutorial = null;
		try {
			tutorial = tutorialDAO.getTutorial(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		if (tutorial == null) {
			throw new MissingResourceException(String.format(
					"No tutorial with ID '%s' found.", id));
		}
		
		return tutorial;
	}

	public Tutorial createTutorial(Tutorial tutorial)
			throws AbstractServerException {
		if (tutorial.getName() == null) {
			throw new BadRequestException("No tutorial name provided.");
		}

		if (tutorial.getId() != null) {
			throw new BadRequestException(
					"Tutorial ID must not be specified upon creation.");
		}

		Tutorial createdTutorial = null;
		try {
			createdTutorial = tutorialDAO.createTutorial(tutorial);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return createdTutorial;
	}

	public Tutorial editTutorial(Tutorial tutorial)
			throws AbstractServerException {
		if (tutorial.getId() == null) {
			throw new BadRequestException("No tutorial ID provided.");
		}

		if (tutorial.getName() == null) {
			throw new BadRequestException("No tutorial name provided.");
		}

		Tutorial editedTutorial = null;
		try {
			editedTutorial = tutorialDAO.editTutorial(tutorial);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}

		return editedTutorial;
	}

	public void deleteTutorial(String id) throws AbstractServerException {
		try {
			tutorialDAO.deleteTutorial(id);
		} catch (DataAccessException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}
