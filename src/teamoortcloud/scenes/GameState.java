package teamoortcloud.scenes;

import java.awt.Point;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teamoortcloud.engine.App;
import teamoortcloud.engine.DataLoader;
import teamoortcloud.engine.GameShop;
import teamoortcloud.people.Worker;

public class GameState extends AppState {
	
	ArrayList<Worker> workers;
	GameShop game;
	
	public GameState(StateManager sm) {
		super(sm);
		
		game = new GameShop();
		
		workers = DataLoader.getWorkers();
		System.out.println(workers.toString());
		
		
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
		btnShop.setOnAction(e -> game.addCustomer());
		btnCashier.setOnAction(e -> checkoutWindow());
		btnStocker.setOnAction(e -> game.removeCustomer());
		
		leftPane.getChildren().addAll(btnCashier, btnStocker, btnManager, btnShop);
		
		//right ToolBar
		Button btnStats = new Button("stats");
		rightPane.getChildren().addAll(btnStats);
		
		
		pane.setLeft(leftPane);
		pane.setRight(rightPane);
		
		return pane;
	}
	
	private Group initGame() {
		Group pane = new Group();
		
		//FIX TO ACTUAL SIZE
		Canvas canvas = new Canvas(App.SCREEN_WIDTH, App.SCREEN_HEIGHT - 80);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Point mouseLoc = new Point(0, 0);
		
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mouseLoc.setLocation(e.getX(), e.getY());
			}
			
		});
		
		//gc.setFill(Color.BLACK);
		//gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//game.draw(gc);
		
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				game.update();
				game.draw(gc);
				
				drawDebug(gc, mouseLoc);
				
			}
			
		}.start();
		
		pane.getChildren().add(canvas);
		
		return pane;
	}
	
	private void drawDebug(GraphicsContext gc, Point mouse) {
		
		gc.setFill(Color.WHITE);
		gc.fillText(String.format(
				"\nMX: %d MY: %d \nTileX: %d TileY: %d", 
				(int)mouse.getX(), (int)mouse.getY(),
				(int)mouse.getX() / 16, (int)mouse.getY() / 16
		), 0, 0);
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
	
	private void checkoutWindow() {
		
		//Stage
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(this.scene.getWindow());
		stage.setTitle("Cash Register");
		//stage.setWidth(App.SCREEN_WIDTH);
		
		//Panes
		BorderPane basePane = new BorderPane();
		VBox leftPane = new VBox();
		VBox rightPane = new VBox();
		
		basePane.setPadding(new Insets(15));
		
		Label labelCustomer = new Label("Current Customer: <Customer>");
		
		TableView<String> itemsTable = new TableView<>();
		itemsTable.getColumns().addAll(
			new TableColumn<String, String>("Item"),
			new TableColumn<String, String>("Details"),
			new TableColumn<String, String>("Price")
		);
		
		//Bottom buttons
		Button btnAddItem = new Button("Add Item");
		Button btnCheckoutCredit = new Button("Checkout with Credit");
		Button btnCheckoutCash = new Button("Checkout with Cash  ");
		
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(btnAddItem);
		borderPane.setRight(new VBox(btnCheckoutCredit, btnCheckoutCash));
		
		leftPane.getChildren().addAll(labelCustomer, itemsTable, borderPane);
		
		
		String orderTypes[] = {"Cone", "Sundae", "Split", "Float", "Soda"};
		ObservableList<String> orderTypesList = FXCollections.observableArrayList(orderTypes);
		
		//Right side
		//VBox formPane = new VBox();
		
		ComboBox comboOrderType = new ComboBox(orderTypesList);
		rightPane.getChildren().addAll(comboOrderType);
		
		//cone[cake, sugar, waffle] Flavors: 3
		//sundae, flavors: 1, nuts, extraFlavor
		//Split, flavors: 1, nuts, 3 extra flavors
		//float, nothing
		//Soda, flavors: 1
		
		basePane.setLeft(leftPane);
		basePane.setRight(rightPane);
		
		stage.setScene(new Scene(basePane, 450, 300));
		stage.show();
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
