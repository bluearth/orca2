package com.akiradata.orca;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		MainPane mainPane = new MainPane(stage);
		Scene mainScene = new Scene(mainPane);
//		stage.setMaximized(true);
		stage.setTitle("Orca");
		stage.setScene(mainScene);
		stage.show();
		stage.setOnCloseRequest(event -> mainPane.applicationCloseRequested());
	}

	public static void main(String ... args) {
		launch(args);
	}
}
