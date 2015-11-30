package teamoortcloud.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StateManager {
	
	public static final int STATE_MENU = 0;
	public static final int STATE_GAME = 1;
	public static final int STATE_ABOUT = 2;
	public static final int STATE_FILE = 3;

	
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
		}
	}

	public Stage getStage() { return this.stage; }

    void setTitle(String pay_with_Cash) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
