package de.hdm.GWT_GUI.server;

import de.hdm.GWT_GUI.client.BlogeintragService;
import de.hdm.GWT_GUI.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BlogeintragServiceImpl extends RemoteServiceServlet implements BlogeintragService {

	public String blogServer(String titleInput, String subtitleInput, String textInput) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidTitle(titleInput)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long for the Title");
		}
		
		else if (!FieldVerifier.isValidSubtitle(subtitleInput)) {
				// If the input is not valid, throw an IllegalArgumentException back to
				// the client.
				throw new IllegalArgumentException("Name must be at least 4 characters long for the Subtitle");
			}
		
		else if (!FieldVerifier.isValidText(textInput)) {
				// If the input is not valid, throw an IllegalArgumentException back to
				// the client.
				throw new IllegalArgumentException("Name must be at least 4 characters long for the Text");
			} 

		// Empfangen der Server Info aus AsynCallback??
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		titleInput = escapeHtml(titleInput);
		userAgent = escapeHtml(userAgent);
		subtitleInput = escapeHtml(subtitleInput);
		userAgent = escapeHtml(userAgent);
		textInput = escapeHtml(textInput);
		userAgent = escapeHtml(userAgent);

		return "Eingabe gespeichert!";
	}

	
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
