package de.hdm.GWT_GUI.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("blogServlet")
public interface BlogeintragService extends RemoteService {
	String blogServer(String titleInput, String subtitleInput, String textInput) throws IllegalArgumentException;
}
