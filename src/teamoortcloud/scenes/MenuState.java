package teamoortcloud.scenes;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import teamoortcloud.engine.App;
import teamoortcloud.entities.FallingIceCream;
 
public class MenuState extends AppState {
	
	Button btnStart, btnAbout, btnSettings;

	public MenuState(StateManager sm) {
		super(sm);
		
		//Setup basic panes
		StackPane rootPane = new StackPane();
		HBox pane = new HBox();
		Group canvasPane = new Group();
		
		rootPane.getChildren().addAll(canvasPane, pane);
		
		//Setup pane contents
		initCanvas(canvasPane);
		initFrame(pane);
		
		scene = new Scene(rootPane);
		setupStyle();
	}
	
	//setup menu frame
	private void initFrame(HBox pane) {
		pane.setSpacing(25);
		pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(275, 0, 0, 0));
		
		final int minButtonWidth = 200;
		final int minButtonHeight = 45;
		
		//Setup buttons and attributes
		btnStart = new Button("Start Game");
		btnAbout = new Button("About");
        btnSettings = new Button("Settings");
		
		btnStart.setMinWidth(minButtonWidth);
		btnStart.setMinHeight(minButtonHeight);
		btnAbout.setMinWidth(minButtonWidth);
		btnAbout.setMinHeight(minButtonHeight);
        btnSettings.setMinWidth(minButtonWidth);
        btnSettings.setMinHeight(minButtonHeight);

		btnStart.getStyleClass().add("menu-button");
		btnAbout.getStyleClass().add("menu-button");
        btnSettings.getStyleClass().add("menu-button");
		
		btnStart.setOnAction(e -> sm.setStage(StateManager.STATE_GAME));
		//btnHighScore.setOnAction(e -> buttonClicked(e));
		btnAbout.setOnAction(e -> sm.setStage(StateManager.STATE_ABOUT));
        btnSettings.setOnAction(e -> sm.setStage(StateManager.STATE_FILE));
		
		pane.getChildren().addAll(btnStart, btnSettings, btnAbout);
	}
	
	private void initCanvas(Group canvasPane) {
		//Setup canvas and graphics
		Canvas canvas = new Canvas(App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Load images
		Image iceCreamImage = new Image("file:res/images/icecream_cone_happy.png");
		Image bg = new Image("file:res/images/menu_bg.jpg");
		
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
