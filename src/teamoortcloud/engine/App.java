package teamoortcloud.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import teamoortcloud.scenes.StateManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
	
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 400;
	
	StateManager sm;
	
//	Button btnscene1, btnscene2;
//    Label lblscene1, lblscene2;
//    FlowPane pane1, pane2;
//    Scene scene1, scene2;
//    Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		sm = new StateManager(stage);
		
		//Setup basic attributes
		stage.setTitle("Game title!");
		stage.setWidth(SCREEN_WIDTH);
		stage.setHeight(SCREEN_HEIGHT);
		
		//Center window to display
		stage.setX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - stage.getWidth() / 2);
		stage.setY(Screen.getPrimary().getVisualBounds().getHeight() / 2 - stage.getHeight() / 2);
//		
//		//Test stuff
//		btnscene1 = new Button("Button 1");
//		btnscene2 = new Button("Button 2");
//		
//		btnscene1.setOnAction(e -> buttonClicked(e));
//		btnscene2.setOnAction(e -> buttonClicked(e));
//		
//		lblscene1=new Label("Scene 1");
//        lblscene2=new Label("Scene 2");
//        
//        //make 2 Panes
//        pane1=new FlowPane();
//        pane2=new FlowPane();
//        pane1.setVgap(10);
//        pane2.setVgap(10);
//        
//        pane1.setStyle("-fx-background-color: tan;-fx-padding: 10px;");
//        pane2.setStyle("-fx-background-color: red;-fx-padding: 10px;");
//           
//        //add everything to panes
//        pane1.getChildren().addAll(lblscene1, btnscene1);
//        pane2.getChildren().addAll(lblscene2, btnscene2);
//        
//        //make 2 scenes from 2 panes
//        scene1 = new Scene(pane1, 200, 100);
//        scene2 = new Scene(pane2, 200, 100);
        
        sm.start();
        stage.show();
	}
	
//	public void buttonClicked(ActionEvent e) {
//		if (e.getSource()==btnscene1)
//            stage.setScene(scene2);
//        else
//            stage.setScene(scene1);
//	}
	
}
