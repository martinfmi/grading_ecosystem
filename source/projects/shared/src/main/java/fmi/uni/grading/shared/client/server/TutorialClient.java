package fmi.uni.grading.shared.client.server;

import java.util.List;

import fmi.uni.grading.shared.AbstractClient;
import fmi.uni.grading.shared.beans.Tutorial;
import fmi.uni.grading.shared.beans.TutorialEntry;
import fmi.uni.grading.shared.exceptions.AbstractServerException;
import fmi.uni.grading.shared.exceptions.client.ServerResponseException;
import fmi.uni.grading.shared.services.server.ITutorialService;

public class TutorialClient extends AbstractClient<ITutorialService> {

	public TutorialClient(String url, String user, String password) {
		super(url, ITutorialService.class, user, password);
	}

	public List<Tutorial> getTutorials() throws ServerResponseException {
		List<Tutorial> tutorials = null;
		try {
			tutorials = getService().getTutorials();
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return tutorials;
	}

	public Tutorial getTutorial(String id) throws ServerResponseException {

		Tutorial tutorial = null;
		try {
			tutorial = getService().getTutorial(id);
		} catch (AbstractServerException ex) {
			throw new ServerResponseException(ex);
		}
		return tutorial;
	}

	public Tutorial createTutorial(String name,
	List<TutorialEntry> entries) throws ServerResponseException {
		Tutorial tutorial = buildTutorial(null, name, entries);
		Tutorial createdTutorial = createTutorial(tutorial);
		return createdTutorial;
	}
	
	public Tutorial createTutorial(Tutorial tutorial)
			throws ServerResponseException {
		Tutorial createdTutorial = null;
		try {
			createdTutorial = getService().createTutorial(tutorial);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}

		return createdTutorial;
	}
	
	public Tutorial editTutorial(String id, String name,
			List<TutorialEntry> entries) throws ServerResponseException {
		Tutorial tutorial = buildTutorial(id, name, entries);
		Tutorial editedTutorial = editTutorial(tutorial);
		return editedTutorial;
	}
	
	public Tutorial editTutorial(Tutorial tutorial)
			throws ServerResponseException {
		Tutorial editedTutorial = null;
		try {
			editedTutorial = getService().editTutorial(tutorial);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}

		return editedTutorial;
	}
	
	public void deleteTutorial(String id) throws ServerResponseException {
		try {
			getService().deleteTutorial(id);
		} catch (AbstractServerException e) {
			throw new ServerResponseException(e);
		}
	}

	private Tutorial buildTutorial(String id, String name,
			List<TutorialEntry> entries) {
		Tutorial tutorial = new Tutorial();
		tutorial.setId(id);
		tutorial.setName(name);
		tutorial.setEntries(entries);
		return tutorial;
	}
}
