package teamoortcloud.scenes;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teamoortcloud.engine.App;
import teamoortcloud.engine.GameShop;
import teamoortcloud.people.Cashier;
import teamoortcloud.people.Worker;

public class GameState extends AppState {
	
	List<Worker> workers;
	
	public GameState(StateManager sm) {
		super(sm);
		
		workers = new ArrayList<>();
		workers.add(new Worker(1, "Cookie Monster"));
		workers.add(new Worker(2, "Jonna Hill"));
		workers.add(new Worker(15, "Swag daddy princeess"));
		
		for(Worker w : workers) {
			System.out.println(w.toString());
		}
		
		
		//Setup basic panes + contents
		VBox rootPane = new VBox();
		rootPane.getChildren().addAll(initToolBar(), initGame());
		
		scene = new Scene(rootPane);
	}
	
	private BorderPane initToolBar() {
		BorderPane pane = new BorderPane();
		HBox leftPane = new HBox();
		HBox rightPane = new HBox();
		
		final int PANE_SPACING = 15;
		final int PANE_PADDING = 10;
		
		leftPane.setSpacing(PANE_SPACING);
		leftPane.setPadding(new Insets(PANE_PADDING));
		leftPane.setStyle("-fx-background-color: orange;");
		
		rightPane.setSpacing(PANE_SPACING);
		rightPane.setPadding(new Insets(PANE_PADDING));
		rightPane.setStyle("-fx-background-color: red;");
		
		//Left ToolBar
		Button btnCashier, btnStocker, btnManager, btnShop;
		btnCashier = new Button("Cashier");
		btnStocker = new Button("Stocker");
		btnManager = new Button("Employees");
		btnShop = new Button("Shop Settings");
		
		btnManager.setOnAction(e -> employeesWindow());
		
		leftPane.getChildren().addAll(btnCashier, btnStocker, btnManager, btnShop);
		
		//right ToolBar
		Button btnStats = new Button("stats");
		rightPane.getChildren().addAll(btnStats);
		
		
		pane.setLeft(leftPane);
		pane.setRight(rightPane);
		
		System.out.println(leftPane.getHeight());
		
		return pane;
	}
	
	private Group initGame() {
		Group pane = new Group();
		
		//FIX TO ACTUAL SIZE
		Canvas canvas = new Canvas(App.SCREEN_WIDTH, App.SCREEN_HEIGHT - 80);
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
	
	private void employeesWindow() {
		VBox pane = new VBox();
		
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.scene.getWindow());
		stage.setTitle("Employees");
		
		
		TableView employeeTable = new TableView();
		employeeTable.getColumns().addAll(
			new TableColumn("Name"),
			new TableColumn("Type"),
			new TableColumn("On Break"),
			new TableColumn("Edit")
		);
		
		pane.getChildren().add(employeeTable);
		
		stage.setScene(new Scene(pane, 300, 300));
		stage.show();
	}

}
