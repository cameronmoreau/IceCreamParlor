package teamoortcloud.engine;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import teamoortcloud.scenes.StateManager;

public class App extends Application {
	
	public static final int SCREEN_WIDTH = 720;
	public static final int SCREEN_HEIGHT = 480;
	
	StateManager sm;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		sm = new StateManager(stage);
		
		//Setup basic attributes
		stage.setTitle("Honey Badger Ice Cream Parlor!");
		stage.setWidth(SCREEN_WIDTH);
		stage.setHeight(SCREEN_HEIGHT);
		
		//Center window to display
		stage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - stage.getWidth() / 2);
		stage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2 - stage.getHeight() / 2);
        
        sm.start();
        stage.show();
	}
	
}
