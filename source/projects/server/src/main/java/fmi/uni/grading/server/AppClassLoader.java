package fmi.uni.grading.server;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import fmi.uni.grading.server.grader.Grader;

/**
 * Loads server application. The main grader class of the application is
 * specified by the 'Grader' attribute in the manifest file of the application.
 * 
 * @author Martin Toshev
 */
public class AppClassLoader extends URLClassLoader {

	public static final String ATTRIBUTE_GRADER = "Grader-Class";

	private URL url;

	public AppClassLoader(URL url) {
		super(new URL[] { url });
		this.url = url;
	}

	public Grader loadGrader() {
		try {
			URL u = new URL("jar", "", url + "!/");
			JarURLConnection uc = (JarURLConnection) u.openConnection();
			Manifest manifest = uc.getManifest();
			if (manifest == null) {
				throw new ServerError(
						"Failed to read manifest file - please check your application at URL: "
								+ url);
			}
			Attributes attributes = manifest.getMainAttributes();
			if (attributes == null) {
				throw new ServerError(
						"Failed to read manifest file attributes - please check the MANIFEST.MF file of your application at URL: "
								+ url);
			}

			String graderAttr = attributes.getValue(ATTRIBUTE_GRADER);
			if (graderAttr == null) {
				throw new ServerError(
						"'Grader' attribute is missing from manifest file of application with URL: "
								+ url.toString());
			}
			Class<?> graderClass = loadClass(graderAttr);
			if (!Grader.class.isAssignableFrom(graderClass)) {
				throw new ServerError(
						"Grader class: "
								+ graderAttr
								+ " specified in application with URL: "
								+ url.toString()
								+ " should extend the fmi.uni.grading.server.grader.Grader class");
			}

			Grader instance = (Grader) graderClass.newInstance();
			return instance;
		} catch (MalformedURLException e) {
			throw new ServerError("Failed to read application from URL: "
					+ url.toString(), e);
		} catch (IOException e) {
			throw new ServerError("Error reading application jar from: "
					+ url.toString(), e);
		} catch (ClassNotFoundException e) {
			throw new ServerError(
					"Missing class specified by 'Grader' attribute of application with URL: "
							+ url.toString());
		} catch (InstantiationException e) {
			throw new ServerError(
					"Failed to create grader instance for application with URL: "
							+ url.toString(), e);
		} catch (IllegalAccessException e) {
			throw new ServerError(
					"Failed to create grader instance for application with URL: "
							+ url.toString(), e);
		}
	}
}
