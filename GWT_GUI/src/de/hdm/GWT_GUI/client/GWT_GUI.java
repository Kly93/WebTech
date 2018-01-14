package de.hdm.GWT_GUI.client;

import de.hdm.GWT_GUI.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWT_GUI implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Blogeintrag service.
	 */
	private final BlogeintragServiceAsync blogService = GWT.create(BlogeintragService.class);
	
	//Variable zum Hochzaehlen der angelegten Blogeintraege
	private int blogeintragCount = 1;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Eingaben speichern");
		final Label blogeintragCounter = new Label("Blogeintrag Nr. " + blogeintragCount + " anlegen");
		final TextBox titleField = new TextBox();
		titleField.setText("Beispiel Titel");
		final TextBox subtitleField = new TextBox();
		subtitleField.setText("Beispiel Subtitel");
		final TextBox textField = new TextBox();
		textField.setText("Beispiel Text");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		// Referenziert auf id in Host Page!
		RootPanel.get("blogeintragTitleContainer").add(blogeintragCounter);
		RootPanel.get("titleFieldContainer").add(titleField);
		RootPanel.get("subtitleFieldContainer").add(subtitleField);
		RootPanel.get("textFieldContainer").add(textField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		titleField.setFocus(true);
		titleField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Blogeintrag");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label titleToServerLabel = new Label();
		final Label subtitleToServerLabel = new Label();
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Titel wird gesendet:</b>"));
		dialogVPanel.add(titleToServerLabel);
		dialogVPanel.add(new HTML("<b>Subtitel wird gesendet:</b>"));
		dialogVPanel.add(subtitleToServerLabel);
		dialogVPanel.add(new HTML("<b>Text wird gesendet:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server Antwort:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox and clear Textfields
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
				titleField.setText("");
				subtitleField.setText("");
				textField.setText("");
				blogeintragCount++;
				blogeintragCounter.setText("Blogeintrag Nr. " + blogeintragCount + " anlegen");
			}
		});
		
		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendContentToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendContentToServer();
				}
			}

			/**
			 * Send the title from the titleField, subtitleField and textField to the server and wait for a response.
			 */
			private void sendContentToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String titleToServer = titleField.getText();
				String subtitleToServer = subtitleField.getText();
				String textToServer = textField.getText();
				if (!FieldVerifier.isValidTitle(titleToServer)) {
					errorLabel.setText("Please enter at least four characters for the Title");
					return;
				}
				
				if (!FieldVerifier.isValidSubtitle(subtitleToServer)) {
					errorLabel.setText("Please enter at least four characters for the Subtitle");
					return;
				}
				
				if (!FieldVerifier.isValidText(textToServer)) {
					errorLabel.setText("Please enter at least four characters for the Text");
					return;
				} 

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				titleToServerLabel.setText(titleToServer);
				subtitleToServerLabel.setText(subtitleToServer);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				blogService.blogServer(titleToServer, subtitleToServer, textToServer, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Neuer Blogeintrag: ");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true); 
					}
				});
			}
		}

		
		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		MyHandler titlehandler = new MyHandler();
		MyHandler subtitlehandler = new MyHandler();
		MyHandler texthandler = new MyHandler(); 
		
		sendButton.addClickHandler(handler);
		titleField.addKeyUpHandler(titlehandler);
		subtitleField.addKeyUpHandler(subtitlehandler);
		textField.addKeyUpHandler(texthandler);
	}
}
