package de.hdm.GWT_GUI.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>BlogeintragService</code>.
 */
public interface BlogeintragServiceAsync {
	// Variable hat keinen Rückgabewert - es muss nicht auf Server Antwort in Code Ablauf gewartet werden!
	void blogServer(String titleInput, String subtitleInput, String textInput, AsyncCallback<String> callback) throws IllegalArgumentException;
}
