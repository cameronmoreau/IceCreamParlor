package teamoortcloud.scenes;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import teamoortcloud.engine.App;
import entities.FallingIceCream;
 
public class MenuState extends AppState {
	
	Button btnStart, btnHighScore, btnHelp;

	public MenuState(StateManager sm) {
		super(sm);
		
		//Setup basic panes
		StackPane rootPane = new StackPane();
		VBox pane = new VBox();
		Group canvasPane = new Group();
		
		rootPane.getChildren().addAll(canvasPane, pane);
		
		//Setup pane contents
		initCanvas(canvasPane);
		initFrame(pane);
		
		scene = new Scene(rootPane);
	}
	
	//setup menu frame
	private void initFrame(VBox pane) {
		pane.setSpacing(25);
		pane.setAlignment(Pos.CENTER);
		
		final int minButtonWidth = 300;
		final int minButtonHeight = 45;
		
		//Setup buttons and attributes
		btnStart = new Button("Start Game");
		btnHighScore = new Button("High Scores");
		btnHelp = new Button("Help");
		
		btnStart.setMinWidth(minButtonWidth);
		btnStart.setMinHeight(minButtonHeight);
		btnHighScore.setMinWidth(minButtonWidth);
		btnHighScore.setMinHeight(minButtonHeight);
		btnHelp.setMinWidth(minButtonWidth);
		btnHelp.setMinHeight(minButtonHeight);
		
		btnStart.setOnAction(e -> sm.setStage(StateManager.STATE_GAME));
		btnHighScore.setOnAction(e -> buttonClicked(e));
		btnHelp.setOnAction(e -> buttonClicked(e));
		
		pane.getChildren().addAll(btnStart, btnHighScore, btnHelp);	
	}
	
	private void initCanvas(Group canvasPane) {
		//Setup canvas and graphics
		Canvas canvas = new Canvas(App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Load images
		Image iceCreamImage = new Image("file:res/icecream_cone_happy.png");
		Image bg = new Image("file:res/menu_bg.jpg");
		
		//Generate falling ice cream
		Random rand = new Random();
		int maxX = (int)canvas.getWidth() - (int)iceCreamImage.getWidth();
		int maxY = (int)canvas.getHeight() - (int)iceCreamImage.getHeight();
		int maxEntities = 15;
		
		FallingIceCream falling[] = new FallingIceCream[maxEntities];
		
		for(int i = 0; i < maxEntities; i++) {
			falling[i] =  new FallingIceCream(rand, maxX, maxY, (int)iceCreamImage.getHeight());
		}
		
		//Animation timer for falling effect
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				gc.drawImage(bg, 0, 0, canvas.getWidth(), canvas.getHeight());
				
				for(int i = 0; i < maxEntities; i++) {
					FallingIceCream f = falling[i];
					f.update();
					f.draw(gc, iceCreamImage);
				}
			}
			
			
		}.start();
		
		canvasPane.getChildren().add(canvas);
	}
	
}
