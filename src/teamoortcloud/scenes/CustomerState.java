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
	
	NumberFormat moneyFormat;
	
	Shop shop;
	Customer selectedCustomer;
	
	Label labelSelectedCustomer;
	ComboBox<String> comboSelectedType, comboWorkerType;
	Button btnAddCustomer, btnUpdateName, btnUpdateMoney;
	TextField tfCustomerName, tfCustomerMoney, tfNewCustomerName, tfNewCustomerMoney;
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
		
		btnAddCustomer = new Button("Add Customer");
		btnAddCustomer.setDisable(true);
		btnAddCustomer.setOnAction(e -> addNewCustomer());
		tfCustomerName = new TextField();
		tfCustomerName.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewForm();
		});
		tfCustomerMoney = new TextField(){
            @Override 
            public void replaceText(int start, int end, String text) {
                // If the replaced text would end up being invalid, then simply
                // ignore this call!
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override 
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
		tfCustomerMoney.textProperty().addListener((observable, oldValue, newValue) -> {updateNewForm();});
		
		
		topPane.getChildren().addAll(
			new Label("Customer's Name:"), tfCustomerName,
			new Label("Money:"), tfCustomerMoney,
			btnAddCustomer
		);
		
		//Bottom Pane
		HBox updatePane = new HBox();
		updatePane.setSpacing(5);
		
		
		btnUpdateName = new Button("Update Name");
		btnUpdateMoney = new Button("Update Money");
		btnUpdateName.setOnAction(e -> updateName());
		btnUpdateMoney.setOnAction(e -> updateMoney());
		btnUpdateName.setDisable(true);
		btnUpdateMoney.setDisable(true);
		
		labelSelectedCustomer = new Label("Nothing selected...");
		

		tfNewCustomerName = new TextField();
		tfNewCustomerName.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewNameForm();
		});
		tfNewCustomerMoney = new TextField(){
            @Override 
            public void replaceText(int start, int end, String text) {
                // If the replaced text would end up being invalid, then simply
                // ignore this call!
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override 
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };
		tfNewCustomerMoney.textProperty().addListener((observable, oldValue, newValue) -> {updateNewMoneyForm();});
		
		bottomPane.getChildren().addAll(labelSelectedCustomer,tfNewCustomerName,btnUpdateName,tfNewCustomerMoney,btnUpdateMoney);
		
		pane.setTop(topPane);
		pane.setBottom(bottomPane);
		return pane;
	}
	
	//Helper functions
	private void updateSelectedCustomer() {

		labelSelectedCustomer.setText("NAME: "+selectedCustomer.getName()+
				"\nMONEY: "+moneyFormat.format(selectedCustomer.getTotalMoney())+
				"\nHAPPINESS: "+selectedCustomer.getHappiness());

		
	}
	
	private void updateNewForm() {
		btnAddCustomer.setDisable(!newFormIsValid());
	}
	
	private void updateNewNameForm()
	{
		btnUpdateName.setDisable(tfNewCustomerName.getText().length()<1);
	}
	
	private void updateNewMoneyForm()
	{
		if(tfNewCustomerMoney.getText()!=null)
			btnUpdateMoney.setDisable(tfNewCustomerMoney.getText()==null);
	}
	
	private void updateName()
	{
		selectedCustomer.changeName(tfNewCustomerName.getText());
		updateSelectedCustomer();
		customerList.setItems(getCustomerListData());
		btnUpdateName.setDisable(true);
		tfNewCustomerName.clear();
	}
	
	private void updateMoney()
	{
		selectedCustomer.changeMoney(Double.parseDouble(tfNewCustomerMoney.getText()));
		updateSelectedCustomer();
		customerList.setItems(getCustomerListData());
		btnUpdateMoney.setDisable(true);
		tfNewCustomerMoney.clear();
	}
	
	private void addNewCustomer() {
		Customer temp;
		String name = tfCustomerName.getText();
		double money = Double.parseDouble(tfCustomerMoney.getText());
		temp = new Customer(0, name, money);
		
		//Update list
		shop.addCustomer(temp);
		customerList.setItems(getCustomerListData());
		
		//Clear lists
		tfCustomerName.clear();
		tfCustomerMoney.clear();
		btnAddCustomer.setDisable(true);
	}
	
	private Boolean newFormIsValid() {
		if(tfCustomerName.getText()!=null) return true;
		if(tfCustomerMoney.getText()!=null)
				if(Double.parseDouble(tfCustomerMoney.getText())>0) return true;
		return false;
	}
	

    private void setActiveCustomer() {
        
        customerList.setItems(getCustomerListData());
    }

	private ObservableList<String> getCustomerListData() {
		ObservableList<String> array = FXCollections.observableArrayList();
		for(Customer c : shop.getCustomers()) {
            String s = c.getName() + ": " + moneyFormat.format(c.getTotalMoney());
            array.add(s);
        }
		return array;
	}
}
