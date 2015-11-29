package teamoortcloud.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StateManager {
	
	public static final int STATE_MENU = 0;
	public static final int STATE_GAME = 1;
	public static final int STATE_ABOUT = 2;
	public static final int STATE_FILE = 3;

    public static final int STATE_SUB_CHECKOUT = 4;
    public static final int STATE_SUB_CHECKOUT_CASH = 5;
    public static final int STATE_SUB_CHECKOUT_CREDIT = 6;
    public static final int STATE_SUB_EMPLOYEE_MANAGER = 7;
    public static final int STATE_SUB_STOCKER = 8;

	
	private Stage stage;
	
	public StateManager(Stage stage) {
		this.stage = stage;
		
	}
	
	public void start() {
		setStage(STATE_MENU);
	}

    public void setScene(Scene scene) {
        this.stage.setScene(scene);
    }
	
	public void setStage(int stageKey) {
		switch(stageKey) {
            case STATE_MENU:
                stage.setScene(new MenuState(this).scene);
                break;
            case STATE_GAME:
                stage.setScene(new GameState(this).scene);
                break;
            case STATE_ABOUT:
                stage.setScene(new AboutState(this).scene);
                break;
            case STATE_FILE:
                stage.setScene(new FileState(this).scene);
                break;

            case STATE_SUB_CHECKOUT:
                stage.setScene(new CheckoutState(this).scene);
                break;
            case STATE_SUB_CHECKOUT_CASH:
                stage.setScene(new CheckoutCashState(this).scene);
                break;
            case STATE_SUB_CHECKOUT_CREDIT:
                stage.setScene(new CheckoutCreditState(this).scene);
                break;
            case STATE_SUB_EMPLOYEE_MANAGER:
                stage.setScene(new EmployeeManagerState(this).scene);
                stage.show();
                break;
            case STATE_SUB_STOCKER:
                //stage.setScene(new StockerState(this).scene);
                break;
		}
	}

	public Stage getStage() { return this.stage; }

}
