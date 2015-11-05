package teamoortcloud.scenes;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class MenuState extends AppState {
	
	Button btnStart, btnHighScore, btnHelp;

	public MenuState(StateManager sm) {
		super(sm);
		VBox pane = new VBox();
		scene = new Scene(pane);
		init(pane);
	}
	
	private void init(VBox pane) {
		pane.setSpacing(25);
		pane.setAlignment(Pos.CENTER);
		
		final int minButtonWidth = 300;
		final int minButtonHeight = 45;
		
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

	@Override
	void buttonClicked(ActionEvent e) {
		super.buttonClicked(e);
		
		System.out.println(e.getSource());
	}
	
	
	
}
