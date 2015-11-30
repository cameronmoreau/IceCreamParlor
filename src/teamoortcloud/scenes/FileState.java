package teamoortcloud.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import teamoortcloud.engine.DataLoader;

import java.io.File;


public class FileState extends AppState {
	
	Button btnWorker, btnCustomer, btnIceCream, btnBack;
	FileChooser fileChooser;
    File tempFile;
    DataLoader dataLoader;

	public FileState(StateManager sm) {
		super(sm);

        //Setup file chooser
        fileChooser = new FileChooser();

        dataLoader = new DataLoader();
		
		//Setup basic panes
		StackPane rootPane = new StackPane();
		VBox pane = new VBox();
		Group canvasPane = new Group();
		
		rootPane.getChildren().addAll(canvasPane, pane);
		
		//Setup pane contents
		initFrame(pane);
		
		scene = new Scene(rootPane);
        setupStyle();
	}

    private void pickFile(int type) {
        fileChooser.setTitle("Select <> file");
        tempFile = fileChooser.showOpenDialog(sm.getStage());

        //File wasnt picked
        if(tempFile == null) return;

        dataLoader.setNewPath(type, tempFile.getPath());
        updateButtonText();
    }
	
	//setup menu frame
	private void initFrame(VBox pane) {
		pane.setSpacing(25);
		pane.setAlignment(Pos.CENTER);
		
		final int minButtonWidth = 300;
		final int minButtonHeight = 45;
		
		btnWorker = new Button("Open Worker File");
		btnCustomer = new Button("Open Customer File");
		btnIceCream = new Button("Open Ice Cream File");

        updateButtonText();

        btnBack = new Button("Back");
        btnBack.setPadding(new Insets(10, 0, 0, 0));

        btnWorker.getStyleClass().add("menu-button");
        btnCustomer.getStyleClass().add("menu-button");
        btnIceCream.getStyleClass().add("menu-button");
        btnBack.getStyleClass().add("menu-button");

        btnWorker.setOnAction(e -> pickFile(DataLoader.WORKER));
        btnCustomer.setOnAction(e -> pickFile(DataLoader.CUSTOMER));
        btnIceCream.setOnAction(e -> pickFile(DataLoader.ICECREAM));
        btnBack.setOnAction(e -> sm.setStage(StateManager.STATE_MENU));

		btnWorker.setMinWidth(minButtonWidth);
		btnWorker.setMinHeight(minButtonHeight);
		btnCustomer.setMinWidth(minButtonWidth);
		btnCustomer.setMinHeight(minButtonHeight);
		btnIceCream.setMinWidth(minButtonWidth);
		btnIceCream.setMinHeight(minButtonHeight);
		btnBack.setMinWidth(minButtonWidth);
		btnBack.setMinHeight(minButtonHeight);

		
		pane.getChildren().addAll(btnWorker, btnCustomer, btnIceCream, btnBack);

	}

    private void updateButtonText() {
        String workerPath = dataLoader.getPath(DataLoader.WORKER);
        if(workerPath.equals(DataLoader.DEFAULT_WORKER_PATH)) workerPath = "Default";

        String customerPath = dataLoader.getPath(DataLoader.CUSTOMER);
        if(customerPath.equals(DataLoader.DEFAULT_CUSTOMER_PATH)) customerPath = "Default";

        String icecreamPath = dataLoader.getPath(DataLoader.ICECREAM);
        if(icecreamPath.equals(DataLoader.DEFAULT_ICECREAM_PATH)) icecreamPath = "Default";

        btnWorker.setText("Choose Worker File:\n" + workerPath);
        btnCustomer.setText("Choose Customer File:\n" + customerPath);
        btnIceCream.setText("Choose Ice Cream File:\n" + icecreamPath);
    }
	
}
