package teamoortcloud.scenes;

import java.awt.Point;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teamoortcloud.engine.App;
import teamoortcloud.engine.DataLoader;
import teamoortcloud.engine.ShopSimulation;
import teamoortcloud.icecream.IceCream;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Worker;

public class GameState extends AppState {

	Shop shop;
    
	ShopSimulation game;
	
	StateManager subManager;
	
	public GameState(StateManager sm) {
		super(sm);
		shop = new Shop();
		game = new ShopSimulation();

        
		//Setup basic panes + contents
		VBox rootPane = new VBox();
		rootPane.setStyle("-fx-background-color: #474F53;");
		rootPane.getChildren().addAll(initGame(), initToolBar());
		
		startGame();
		
		scene = new Scene(rootPane);
		setupStyle();
        setupSubWindow();
	}
	
	private BorderPane initToolBar() {
		BorderPane pane = new BorderPane();
		HBox leftPane = new HBox();
		HBox rightPane = new HBox();
		
		final int PANE_SPACING = 15;
		final int PANE_PADDING = 10;
		
		leftPane.setSpacing(PANE_SPACING);
		leftPane.setPadding(new Insets(PANE_PADDING));
		pane.setStyle("-fx-background-color: #3C4346;");
		
		rightPane.setSpacing(PANE_SPACING);
		rightPane.setPadding(new Insets(PANE_PADDING));
		
		//Left ToolBar
		Button btnCashier, btnStocker, btnManager, btnShop;
		btnCashier = new Button("Cashier");
		btnStocker = new Button("Stocker");
		btnManager = new Button("Employees");
		btnShop = new Button("Shop Settings");
		
		btnCashier.getStyleClass().add("menu-button");
		btnStocker.getStyleClass().add("menu-button");
		btnManager.getStyleClass().add("menu-button");
		btnShop.getStyleClass().add("menu-button");
		
		btnManager.setOnAction(e -> employeesWindow());
		btnShop.setOnAction(e -> game.addCustomer());
		btnCashier.setOnAction(e -> checkoutWindow());
		btnStocker.setOnAction(e -> game.removeCustomer());
		
		leftPane.getChildren().addAll(btnCashier, btnStocker, btnManager, btnShop);
		
		//right ToolBar
		Button btnStats = new Button("Shop Overview");
		btnStats.getStyleClass().add("menu-button");
		btnShop.getStyleClass().add("menu-button");
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

    private void setupSubWindow() {
        //Setup Stage
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.scene.getWindow());
        stage.setMinWidth(300);

        //Setup sub manager
        this.subManager = new StateManager(stage);
    }

	private void checkoutWindow() {
		this.subManager.setScene(new CheckoutState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}
	
	private void employeesWindow() {
        this.subManager.setScene(new EmployeeManagerState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}
	
	private void startGame() {
		//Queue customers
		new Thread(new Runnable() {

			@Override
			public void run() {
				for(Customer c : shop.getCustomers()) {
					game.addCustomer();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return;
			}
			
		}).start();
	}

}
