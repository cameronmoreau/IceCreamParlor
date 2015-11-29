package teamoortcloud.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Scene;

public class AppState {

	StateManager sm;
	Scene scene;
	
	public AppState(StateManager sm) {
		this.sm = sm;
	}
	
	protected void setupStyle() {
		scene.getStylesheets().add("file:res/styles/style.css");
	}
}
