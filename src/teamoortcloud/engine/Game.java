package teamoortcloud.engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Game extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		//Setup basic attributes
		stage.setTitle("Game title!");
		stage.setWidth(640);
		stage.setHeight(400);
		
		//Center window to display
		stage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - stage.getWidth() / 2);
		stage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2 - stage.getHeight() / 2);
		
		//Test stuff
		Button b = new Button("This is a button");
		
		//Create a new view and it to the stage
		StackPane root = new StackPane();
		root.getChildren().add(b);
		stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
		
		//Start the window
		stage.show();
	}
	
}
