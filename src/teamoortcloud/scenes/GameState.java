package teamoortcloud.scenes;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teamoortcloud.engine.ShopLog;
import teamoortcloud.engine.ShopSimulation;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Customer;

import java.awt.*;
import java.text.NumberFormat;

public class GameState extends AppState implements Shop.ShopDataChangedListener {

	Shop shop;
    
	ShopSimulation game;
    ShopLog log;
    Label statusLabel;
    NumberFormat moneyFormat;
	
	StateManager subManager;
	
	public GameState(StateManager sm) {
		super(sm);
		shop = new Shop();
		game = new ShopSimulation();

        statusLabel = new Label();
        shop.setListener(this);

        moneyFormat = NumberFormat.getCurrencyInstance();
        
		//Setup basic panes + contents
		BorderPane rootPane = new BorderPane();
		rootPane.setStyle("-fx-background-color: #474F53;");
        rootPane.setTop(new VBox(initToolBar(), initStatusBar()));
        rootPane.setBottom(initGame());
		
		startGame();
		
		scene = new Scene(rootPane);
		setupStyle();
        setupSubWindow();
	}

    private BorderPane initStatusBar() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5));

        log = new ShopLog();
        statusLabel.setTextFill(Color.WHITE);
        pane.setLeft(log);
        pane.setRight(statusLabel);

        return pane;
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
		Button btnCashier, btnStocker, btnManager, btnCustomers;
		btnCashier = new Button("Cashier");
		btnStocker = new Button("Stocker");
		btnManager = new Button("Employees");
		btnCustomers = new Button("Customers");

		btnCashier.getStyleClass().add("menu-button");
		btnStocker.getStyleClass().add("menu-button");
		btnManager.getStyleClass().add("menu-button");
		btnCustomers.getStyleClass().add("menu-button");

		btnManager.setOnAction(e -> employeesWindow());
		btnStocker.setOnAction(e -> stockerWindow());
		btnCashier.setOnAction(e -> checkoutWindow());
		btnCashier.setOnAction(e -> checkoutWindow());

		leftPane.getChildren().addAll(btnCashier, btnStocker, btnManager, btnCustomers);

		//right ToolBar
		Button btnStats = new Button("Shop Overview");
        btnStats.setOnAction(e -> statsWindow());
		btnStats.getStyleClass().add("menu-button");
		btnCustomers.getStyleClass().add("menu-button");
		rightPane.getChildren().addAll(btnStats);


		pane.setLeft(leftPane);
		pane.setRight(rightPane);

		return pane;
	}
	
	private Group initGame() {
		Group pane = new Group();
		
		//FIX TO ACTUAL SIZE
		Canvas canvas = new Canvas(
                ShopSimulation.TILE_WIDTH * ShopSimulation.TILE_SIZE,
                ShopSimulation.TILE_HEIGHT * ShopSimulation.TILE_SIZE
        );
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Point mouseLoc = new Point(0, 0);
		
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mouseLoc.setLocation(e.getX(), e.getY());
			}
			
		});
		
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

    private void updateStatus() {
        statusLabel.setText(String.format(
                "Total Customers: %d\nTotal Employees: %d\n\nRegister Til: %s\nOrders Proccessed: %d",
                shop.getCustomers().size(), shop.getEmployees().size(),
                moneyFormat.format(shop.getRegister().getTil().getTotal()),
                shop.getOrders().size()
        ));
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
        //Check for errors
        if(shop.getActiveCashier() == null) {
            showError("You must choose an active cashier");
            return;
        }
        else if(shop.getActiveCashier().getPatience() < 1) {
            showError("Cashier is out of patience");
            return;
        }

		this.subManager.setScene(new CheckoutState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}

    private void statsWindow() {
        this.subManager.setScene(new ShopOverviewState(this.subManager, shop).scene);
        this.subManager.getStage().show();
    }
	
	private void employeesWindow() {
        this.subManager.setScene(new EmployeeManagerState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}
	
	private void stockerWindow() {
        if(shop.getActiveStocker() == null) {
            showError("You must choose an active stocker");
            return;
        }

        this.subManager.setScene(new StockerState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}
	
	private void startGame() {
		//Queue customers
        for(Customer c : shop.getCustomers()) log.addLog(c.getName() + " enters the building");

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

        //Status
        updateStatus();
	}

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Uh oh...");
        alert.setContentText(error);

        alert.showAndWait();
    }

    @Override
    public void dataChanged() {
        updateStatus();
    }
}
