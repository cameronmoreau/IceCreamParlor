package teamoortcloud.scenes;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teamoortcloud.engine.GameClock;
import teamoortcloud.engine.ShopSimulation;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Customer;

import java.awt.*;
import java.text.NumberFormat;

public class GameState extends AppState implements Shop.ShopDataChangedListener {

	Shop shop;
    
	ShopSimulation game;
    GameClock gameClock;

    Label logLabel;
    Label statusLabel;
    NumberFormat moneyFormat;
	
	StateManager subManager;

    private static final boolean debug = true;
	
	public GameState(StateManager sm) {
		super(sm);
		shop = new Shop();
		game = new ShopSimulation(shop);
        gameClock = new GameClock();

        gameClock.setListener(new GameClock.GameClockListener() {
            @Override
            public void crappyHourBegins() {
                game.startCrappyHour();
                Platform.runLater(() -> shop.getLog().addLog("The chaos has begun.."));
            }

            @Override
            public void shopClosed() {

                Platform.runLater(() -> endGame());
            }
        });

        //Check if game closed
        sm.getStage().setOnCloseRequest(e -> stageClosed());

        statusLabel = new Label();
        shop.setListener(this);

        moneyFormat = NumberFormat.getCurrencyInstance();
        
		//Setup basic panes + contents
		BorderPane rootPane = new BorderPane();
		rootPane.setStyle("-fx-background-color: #474F53;");

        rootPane.setTop(new VBox(initMenu(), initStatusBar()));

        rootPane.setBottom(initGame());
		
		startGame();
		
		scene = new Scene(rootPane);
		setupStyle();
        setupSubWindow();
	}

    private BorderPane initStatusBar() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5));

        logLabel = new Label();
        logLabel.setTextFill(Color.WHITE);
        statusLabel.setTextFill(Color.WHITE);
        pane.setLeft(logLabel);
        pane.setRight(statusLabel);

        return pane;
    }

    private MenuBar initMenu() {
        MenuBar bar = new MenuBar();
        bar.setPadding(new Insets(5));
        bar.setStyle("-fx-background-color: #3C4346;");

        Menu menuTasks = new Menu("Tasks");
        Menu menuPeople = new Menu("People");
        Menu menuShop = new Menu("Shop");

        MenuItem itemCashRegister = new MenuItem("Cash Register");
        MenuItem itemStocker = new MenuItem("Stocker");
        itemCashRegister.setOnAction(e -> checkoutWindow());
        itemStocker.setOnAction(e -> stockerWindow());

        MenuItem itemEmployees = new MenuItem("Employees");
        MenuItem itemCustomers = new MenuItem("Customers");
        itemEmployees.setOnAction(e -> employeesWindow());
        itemCustomers.setOnAction(e -> customerWindow());

        MenuItem itemOverview = new MenuItem("Shop Overview");
        MenuItem itemEnd = new MenuItem("End Game");
        itemOverview.setOnAction(e -> shopoverviewWindow());
        itemEnd.setOnAction(e -> endGame());

        menuTasks.getItems().addAll(itemCashRegister, itemStocker);
        menuPeople.getItems().addAll(itemEmployees, itemCustomers);
        menuShop.getItems().addAll(itemOverview, itemEnd);


        bar.getMenus().addAll(menuTasks, menuPeople, menuShop);

        return bar;
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
				
				if(debug) drawDebug(gc, mouseLoc);

                //Draw Clock
                gc.setFill(Color.web("rgba(0, 0, 0, 0.5)"));
                gc.fillRoundRect(5, 5, 60, 20, 8, 8);

                gc.setFill(Color.WHITE);
                gc.fillText(gameClock.getClockString(), 10, 20);
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
		), 0, 50);
	}

    private void updateStatus() {
        logLabel.setText(shop.getLog().getMessages());

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
	
	private void shopoverviewWindow() {
		this.subManager.setScene(new ShopOverviewState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}
	
	private void employeesWindow() {
        this.subManager.setScene(new EmployeeManagerState(this.subManager, shop).scene);
        this.subManager.getStage().show();
	}
	
	private void customerWindow() {
        this.subManager.setScene(new CustomerState(this.subManager, shop).scene);
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
        //Start clock
        gameClock.start();

		//Queue customers
        for(Customer c : shop.getCustomers()) shop.getLog().addLog(c.getName() + " enters the building");

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

    private void endGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("That's it, you failed");
        alert.setContentText("Score: -1");

        alert.setOnCloseRequest(e -> sm.setStage(StateManager.STATE_MENU));

        alert.showAndWait();
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Uh oh...");
        alert.setContentText(error);

        alert.showAndWait();
    }

    private void stageClosed() {
        gameClock.stop();
    }

    @Override
    public void dataChanged() {
        updateStatus();
    }
}
