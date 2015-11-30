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
import teamoortcloud.people.Customer;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

public class CustomerState extends AppState {
	
	final static String[] WORKER_TYPES = {"Worker", "Stocker", "Cashier"};
	NumberFormat moneyFormat;
	
	Shop shop;
	Customer selectedCustomer;
	
	Label labelSelectedCustomer;
	ComboBox<String> comboSelectedType, comboWorkerType;
	Button btnAddWorker, btnUpdateWorker, btnSetActive;
	TextField tfCustomerName, tfCustomerMoney;
	ListView<String> customerList;
	

	public CustomerState(StateManager sm) {
		super(sm);
	}
	
	public CustomerState(StateManager sm, Shop shop) {
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
		
		customerList = new ListView<>();
		customerList.setItems(getCustomerListData());
		customerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {

                int index = customerList.getSelectionModel().getSelectedIndex();
                if(index != -1) {
                    selectedCustomer = shop.getCustomers().get(customerList.getSelectionModel().getSelectedIndex());
                    updateSelectedCustomer();
                }
			}
			
		});
		
		pane.getChildren().add(customerList);
		
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
		tfCustomerName = new TextField();
		tfCustomerName.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewForm();
		});
		tfCustomerMoney = new TextField();
		tfCustomerMoney.textPropert().addListener(())
		
		
		topPane.getChildren().addAll(
			new Label("Customer's Name:"), tfCustomerName,
			new Label("Money:"), tfCustomerMoney,
			btnAddWorker
		);
		
		//Bottom Pane
		HBox updatePane = new HBox();
		updatePane.setSpacing(5);
		
		//comboSelectedType = new ComboBox<>(FXCollections.observableArrayList(WORKER_TYPES));
		//btnUpdateWorker = new Button("Update");
		//btnSetActive = new Button("Set Active");
		labelSelectedCustomer = new Label("Nothing selected...");

		
		bottomPane.getChildren().addAll(labelSelectedCustomer);
		
		pane.setTop(topPane);
		pane.setBottom(bottomPane);
		return pane;
	}
	
	//Helper functions
	private void updateSelectedCustomer() {
		



		labelSelectedCustomer.setText(String.format(
			"NAME: %s\nMONEY: %d\nHAPPINESS: %d",
			selectedCustomer.getName(), selectedCustomer.getTotalMoney(), selectedCustomer.getHappiness()
		));
		
	}
	
	private void updateNewForm() {
		btnAddWorker.setDisable(!newFormIsValid());
	}
	
	private void addNewCustomer() {
		Customer temp;
		String name = tfCustomerName.getText();
		double money = tfCustomerCash.getText();
		temp = new Customer(0, name, )
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
		customerList.setItems(getCustomerListData());
		
		//Clear lists
		tfCustomerName.clear();
		comboWorkerType.getSelectionModel().clearSelection();
		btnAddWorker.setDisable(true);
	}
	
	private Boolean newFormIsValid() {
		if(tfCustomerName.getText().length() < 1) return false;
		if(comboWorkerType.getSelectionModel().getSelectedIndex() == -1) return false;
		if(tfCustomerMoney.getText())
		return true;
	}


    private void setActiveCustomer() {
        
        customerList.setItems(getCustomerListData());
    }

	private ObservableList<String> getCustomerListData() {
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
