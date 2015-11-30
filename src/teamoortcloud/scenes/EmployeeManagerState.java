package teamoortcloud.scenes;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Cashier;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

public class EmployeeManagerState extends AppState {
	
	final static String[] WORKER_TYPES = {"Worker", "Stocker", "Cashier"};
	NumberFormat moneyFormat;
	
	Shop shop;
	Worker selectedEmployee;
	
	Label labelSelectedEmployee;
	ComboBox<String> comboSelectedType, comboWorkerType;
	Button btnAddWorker, btnUpdateWorker, btnSetActive;
	TextField tfWorkerName;
	ListView<String> employeeList;
	

	public EmployeeManagerState(StateManager sm) {
		super(sm);
	}
	
	public EmployeeManagerState(StateManager sm, Shop shop) {
		super(sm);
		this.shop = shop;
		moneyFormat = NumberFormat.getCurrencyInstance();
		
		//Panes
		BorderPane basePane = new BorderPane();
		basePane.setPadding(new Insets(15));
		
		basePane.setRight(setupRightPane());
		basePane.setLeft(setupLeftPane());
		
		this.scene = new Scene(basePane);
	}
	
	private VBox setupLeftPane() {
		VBox pane = new VBox();
		pane.setSpacing(5);
		pane.setPadding(new Insets(0, 5, 5, 5));
		
		employeeList = new ListView<>();
		employeeList.setItems(getEmployeesListData());
		employeeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {

                int index = employeeList.getSelectionModel().getSelectedIndex();
                if(index != -1) {
                    selectedEmployee = shop.getEmployees().get(employeeList.getSelectionModel().getSelectedIndex());
                    updateSelectedEmployee();
                }
			}
			
		});
		
		pane.getChildren().add(employeeList);
		
		return pane;
	}
	
	private BorderPane setupRightPane() {
		BorderPane pane = new BorderPane();
		VBox topPane = new VBox();
		VBox bottomPane = new VBox();
		
		final String style = "-fx-background-color: #DADADA; -fx-background-radius: 5;";
		
		topPane.setStyle(style);
		topPane.setSpacing(5);
		topPane.setPadding(new Insets(0, 5, 5, 5));
		bottomPane.setStyle(style);
		bottomPane.setSpacing(5);
		bottomPane.setPadding(new Insets(0, 5, 5, 5));
		
		btnAddWorker = new Button("Add Worker");
		btnAddWorker.setDisable(true);
		btnAddWorker.setOnAction(e -> addNewWorker());
		tfWorkerName = new TextField();
		tfWorkerName.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewForm();
		});
		
		comboWorkerType = new ComboBox<>(FXCollections.observableArrayList(WORKER_TYPES));
		comboWorkerType.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {
				
				updateNewForm();
			}
			
		});
		
		topPane.getChildren().addAll(
			new Label("Worker's Name:"), tfWorkerName,
			new Label("Type:"), comboWorkerType,
			btnAddWorker
		);
		
		//Bottom Pane
		HBox updatePane = new HBox();
		updatePane.setSpacing(5);
		
		comboSelectedType = new ComboBox<>(FXCollections.observableArrayList(WORKER_TYPES));
		btnUpdateWorker = new Button("Update");
		btnSetActive = new Button("Set Active");
		labelSelectedEmployee = new Label("Nothing selected...");

		btnUpdateWorker.setDisable(true);
        btnSetActive.setDisable(true);

        btnUpdateWorker.setOnAction(e -> updateWorker());
        btnSetActive.setOnAction(e -> setActiveWorker());

		comboSelectedType.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {
				
				//Check that user actually changed the value
				if(selectedEmployee.getType() != value)
					btnUpdateWorker.setDisable(false);
			}
			
		});
		
		updatePane.getChildren().addAll(comboSelectedType, btnUpdateWorker);
		bottomPane.getChildren().addAll(labelSelectedEmployee, updatePane, btnSetActive);
		
		pane.setTop(topPane);
		pane.setBottom(bottomPane);
		return pane;
	}
	
	//Helper functions
	private void updateSelectedEmployee() {
		btnUpdateWorker.setDisable(true);

        //Active + break
        if(selectedEmployee.getClass() == Worker.class) btnSetActive.setDisable(true);
        else btnSetActive.setDisable(false);

		labelSelectedEmployee.setText(String.format(
			"NAME: %s\nCUSTOMERS SERVED: %d\nSCOOPS SERVED: %d\nMONEY TAKEN: %s",
			selectedEmployee.getName(), selectedEmployee.getCustomersServed(), selectedEmployee.getScoopsServed(),
			moneyFormat.format(selectedEmployee.getMoneyTaken())
		));
		
		//Set combo box
		int index = Arrays.asList(WORKER_TYPES).indexOf(selectedEmployee.getType());
		comboSelectedType.getSelectionModel().select(index);
	}
	
	private void updateNewForm() {
		btnAddWorker.setDisable(!newFormIsValid());
	}
	
	private void addNewWorker() {
		Worker temp;
		String name = tfWorkerName.getText();
		
		switch(comboWorkerType.getSelectionModel().getSelectedItem()) {
		case "Cashier":
			temp = new Cashier(0, name);
			break;
		case "Stocker":
			temp = new Stocker(0, name);
			break;
		default:
			temp = new Worker(0, name);
		}
		
		//Update list
		shop.addWorker(temp);
		employeeList.setItems(getEmployeesListData());
		
		//Clear lists
		tfWorkerName.clear();
		comboWorkerType.getSelectionModel().clearSelection();
		btnAddWorker.setDisable(true);
	}
	
	private Boolean newFormIsValid() {
		if(tfWorkerName.getText().length() < 1) return false;
		if(comboWorkerType.getSelectionModel().getSelectedIndex() == -1) return false;
		
		return true;
	}

    private void updateWorker() {

    }

    private void setActiveWorker() {
        if(selectedEmployee.getClass() == Stocker.class) {
            if(shop.getActiveStocker() != null) shop.getActiveStocker().toggleBreak();

            shop.setActiveStocker((Stocker)selectedEmployee);
            ((Stocker)selectedEmployee).toggleBreak();
        }
        else if(selectedEmployee.getClass() == Cashier.class) {
            if(shop.getActiveCashier() != null) shop.getActiveCashier().toggleBreak();

            shop.setActiveCashier((Cashier)selectedEmployee);
            ((Cashier)selectedEmployee).toggleBreak();
        }

        employeeList.setItems(getEmployeesListData());
    }

	private ObservableList<String> getEmployeesListData() {
		ObservableList<String> array = FXCollections.observableArrayList();
		for(Worker w : shop.getEmployees()) {
            String s = w.getName() + ": " + w.getType();
            if(shop.getActiveStocker() == w) s += " (Active Stocker)";
            else if(shop.getActiveCashier() == w) s += " (Active Cashier)";
            array.add(s);
        }
		return array;
	}
}
