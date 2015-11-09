package teamoortcloud.scenes;

import teamoortcloud.engine.App;
import teamoortcloud.engine.GameShop;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GameState extends AppState {
	
	public GameState(StateManager sm) {
		super(sm);
		
		//Setup basic panes
		BorderPane rootPane = new BorderPane();
		
		//Setup pane contents
		rootPane.setTop(initTopBar());
		rootPane.setCenter(initCenterGame());
		rootPane.setBottom(initBottomStats());
		
		scene = new Scene(rootPane);
	}
	
	private HBox initTopBar() {
		HBox pane = new HBox();
		//pane.setMaxHeight(30);
		pane.setSpacing(15);
		pane.setPadding(new Insets(10));
		pane.setStyle("-fx-background-color: orange;");
		
		Button btnCashier, btnStocker, btnManager, btnShop;
		btnCashier = new Button("Cashier");
		btnStocker = new Button("Stocker");
		btnManager = new Button("Employees");
		btnShop = new Button("Shop Settings");
		
		pane.getChildren().addAll(btnCashier, btnStocker, btnManager, btnShop);
		
		return pane;
	}
	
	private Group initCenterGame() {
		Group pane = new Group();
		
		Canvas canvas = new Canvas(App.SCREEN_WIDTH, App.SCREEN_HEIGHT - 100);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		GameShop game = new GameShop();
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		game.draw(gc);
		
		pane.getChildren().add(canvas);
		
		return pane;
	}
	
	private HBox initBottomStats() {
		HBox pane = new HBox();
		
		pane.getChildren().addAll(
			new Label("Total Happines: <number>"),
			new Label("Total Customers: <number>"),
			new Label("Current Time: <number>")
		);
		
		return pane;
	}

}
