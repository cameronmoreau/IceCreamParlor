package teamoortcloud.scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Cashier;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

import java.text.NumberFormat;

public class EmployeeManagerState extends AppState {
	
	final static String[] WORKER_TYPES = {"Worker", "Stocker", "Cashier"};
	NumberFormat moneyFormat;
	
	Shop shop;
	Worker selectedEmployee;
	
	Label labelSelectedEmployee;
	ComboBox<String> comboWorkerType;
	Button btnAddWorker, btnSetActive, btnFireWorker;
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

		btnSetActive = new Button("Set Active");
        btnFireWorker = new Button("Fire");
		labelSelectedEmployee = new Label("Nothing selected...");

        btnSetActive.setDisable(true);
        btnSetActive.setOnAction(e -> setActiveWorker());

        btnFireWorker.setDisable(true);
        btnFireWorker.setOnAction(e -> fireWorker());

		bottomPane.getChildren().addAll(labelSelectedEmployee, btnSetActive, btnFireWorker);
		
		pane.setTop(topPane);
		pane.setBottom(bottomPane);
		return pane;
	}

    //Helper functions
	private void updateSelectedEmployee() {
		btnFireWorker.setDisable(false);

        //Active + break
        if(selectedEmployee.getClass() == Worker.class) btnSetActive.setDisable(true);
        else btnSetActive.setDisable(false);

        String text = String.format(
                "NAME: %s\nCUSTOMERS SERVED: %d\nSCOOPS SERVED: %d\nMONEY TAKEN: %s\nTYPE: %s",
                selectedEmployee.getName(), selectedEmployee.getCustomersServed(), selectedEmployee.getScoopsServed(),
                moneyFormat.format(selectedEmployee.getMoneyTaken()), selectedEmployee.getType()
        );

        if(selectedEmployee.getClass() == Stocker.class)
            text += "\nSTAMINA: " + ((Stocker)selectedEmployee).getStamina();
        else if(selectedEmployee.getClass() == Cashier.class)
            text += "\nPatience: " + ((Cashier)selectedEmployee).getPatience();

		labelSelectedEmployee.setText(text);
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
        shop.setDataChanged();
		
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

    private void fireWorker() {
        //If active
        if(selectedEmployee.getClass() == Stocker.class) {
            if(selectedEmployee == shop.getActiveStocker()) shop.setActiveStocker(null);
        }else if(selectedEmployee.getClass() == Cashier.class) {
            if(selectedEmployee == shop.getActiveCashier()) shop.setActiveCashier(null);
        }

        shop.removeWorker(employeeList.getSelectionModel().getSelectedIndex());
        employeeList.setItems(getEmployeesListData());
        shop.setDataChanged();
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
