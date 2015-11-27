package teamoortcloud.scenes;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.text.NumberFormatter;

import teamoortcloud.icecream.IceCream;
import teamoortcloud.icecream.IceCreamBananaSplit;
import teamoortcloud.icecream.IceCreamCone;
import teamoortcloud.icecream.IceCreamExtra;
import teamoortcloud.icecream.IceCreamRootBeerFloat;
import teamoortcloud.icecream.IceCreamSoda;
import teamoortcloud.icecream.IceCreamSundae;
import teamoortcloud.icecream.Serving;
import teamoortcloud.other.Order;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Worker;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CheckoutState extends AppState {
	
	static final int MAX_FLAVORS = 3;
	static final int MAX_EXTRAS = 3;
	
	Serving tempServing;
	Order order;
	
	NumberFormat moneyFormat;
	
	ArrayList<Worker> workers;
	ArrayList<Customer> customers;
	ArrayList<IceCream> icecream;
	
	ListView<String> orderList;
	
	ComboBox<String> comboFlavors[];
	ComboBox<String> comboExtras[];
	ComboBox<String> comboCone;
	
	Button btnAddToOrder;

	public CheckoutState(StateManager sm) {
		super(sm);
	}
	
	public CheckoutState(StateManager sm, ArrayList<Worker> workers, ArrayList<Customer> customers, ArrayList<IceCream> icecream) {
		super(sm);
		this.workers = workers;
		this.customers = customers;
		this.icecream = icecream;
		
		order = new Order();
		moneyFormat = NumberFormat.getCurrencyInstance();
		
		//Panes
		BorderPane basePane = new BorderPane();
		basePane.setPadding(new Insets(15));
		
		basePane.setRight(setupRightPane());
		basePane.setLeft(setupLeftPane());
		
		scene = new Scene(basePane);
	}
	
	private VBox setupLeftPane() {
		VBox leftPane = new VBox();
		leftPane.setSpacing(5);
		leftPane.setPadding(new Insets(0, 5, 5, 5));
		
		Label labelCustomer = new Label("Current Customer: <Customer>");
		
		orderList = new ListView<String>();
		
		//Bottom buttons
		Button btnRemoveItem = new Button("Remove Item");
		Button btnCheckoutCredit = new Button("Checkout with Credit");
		Button btnCheckoutCash = new Button("Checkout with Cash  ");
		
		btnRemoveItem.setOnAction(e -> removeFromOrder());
		
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(btnRemoveItem);
		borderPane.setRight(new VBox(btnCheckoutCredit, btnCheckoutCash));
		
		leftPane.getChildren().addAll(labelCustomer, orderList, borderPane);
		
		return leftPane;
	}
	
	private VBox setupRightPane() {
		VBox rightPane = new VBox();
		rightPane.setSpacing(5);
		rightPane.setPadding(new Insets(0, 5, 5, 5));
		
		//Type combo box
		String orderTypes[] = {"Cone", "Sundae", "Split", "Float", "Soda"};
		ObservableList<String> orderTypesList = FXCollections.observableArrayList(orderTypes);
		
		//Setup compbo flavors
		String flavors[] = new String[icecream.size()];
		for(int i = 0; i < icecream.size(); i++) {
			flavors[i] = icecream.get(i).getFlavor();
		}
		
		ObservableList<String> flavorTypesList = FXCollections.observableArrayList(flavors);
		
		comboFlavors = new ComboBox[MAX_FLAVORS];
		ComboBox<String> comboOrderType = new ComboBox(orderTypesList);
		comboOrderType.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {
			
				iceCreamSelected(value);
				clearFields();
			}
			
		});
		
		rightPane.getChildren().addAll(new Label("Icecream Type:"), comboOrderType);
		
		//Cone
		comboCone = new ComboBox<String>();
		comboCone.getItems().addAll("Cake Cone", "Sugar Cone", "Waffle Cone");
		comboCone.setDisable(true);
		comboCone.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {
				
				updateFields();
			}
			
		});
		rightPane.getChildren().addAll(new Label("Cone Type:"), comboCone);
		
		//Flavors
		rightPane.getChildren().add(new Label("Flavors:"));
		for(int i = 0; i < MAX_FLAVORS; i++) {
			final int index = i;
			comboFlavors[i] = new ComboBox<String>(flavorTypesList);
			comboFlavors[i].setDisable(true);
			comboFlavors[i].valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> list,
						String previousValue, String value) {
					
					int selectedIndex = comboFlavors[index].getSelectionModel().getSelectedIndex();
					if(selectedIndex != -1) {			
						tempServing.addIceCreamAtPos(
							index, 
							icecream.get(selectedIndex)
						);
					}
					
					updateFields();
				}
				
			});
			
			rightPane.getChildren().add(comboFlavors[i]);
		}
		
		//Extras
		rightPane.getChildren().add(new Label("Icecream Extras:"));
		comboExtras = new ComboBox[MAX_EXTRAS];
		ObservableList<String> orderExtrasList = FXCollections.observableArrayList(IceCreamExtra.getAll());
		
		for(int i = 0; i < MAX_EXTRAS; i++) {
			final int index = i;
			comboExtras[i] = new ComboBox<String>(orderExtrasList);
			comboExtras[i].setDisable(true);
			comboExtras[i].valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> list,
						String previousValue, String value) {
					
					//Select extra and add to ice cream
					int selectedIndex = comboFlavors[index].getSelectionModel().getSelectedIndex();
					if(selectedIndex != -1) {			
						tempServing.addExtraAtPos(index, selectedIndex);
					}
					updateFields();
				}
				
			});
			
			rightPane.getChildren().add(comboExtras[i]);
		}
		
		//Add and estimate price
		btnAddToOrder = new Button("Add to order");
		btnAddToOrder.setDisable(true);
		btnAddToOrder.setOnAction(e -> addToOrder());
		
		rightPane.getChildren().addAll(btnAddToOrder);

		return rightPane;
	}
	
	private void iceCreamSelected(String selected) {
		switch(selected) {
		case "Cone":
			tempServing = new IceCreamCone();
			break;
		case "Sundae":
			tempServing = new IceCreamSundae();
			break;
		case "Split":
			tempServing = new IceCreamBananaSplit();
			break;
		case "Float":
			tempServing = new IceCreamRootBeerFloat();
			break;
		case "Soda":
			tempServing = new IceCreamSoda();
			break;
		default:
			tempServing = null;
			break;
		}
		
		setAllowedCombo(tempServing.getMaxScoops(), comboFlavors);
		setAllowedCombo(tempServing.getMaxExtras(), comboExtras);
		checkOtherFields();
	}
	
	//Helper functions
	private void updateOrderList() {
		orderList.setItems(getServingsListData());
	}
	
	private ObservableList<String> getServingsListData() {
		ObservableList<String> array = FXCollections.observableArrayList();
		for(Serving s : order.getServings()) array.add(s.getName() + ": " + moneyFormat.format(s.getPrice()));
		return array;
	}
	
	private void addToOrder() {
		order.addServing(tempServing);
		tempServing = null;
		clearFields();
		
		//Add to list
		updateOrderList();
	}
	
	private void removeFromOrder() {
		int index = orderList.getSelectionModel().getSelectedIndex();
		if(orderList.getSelectionModel().getSelectedIndex() != -1) {
			orderList.getItems().remove(index);
			order.removeServing(index);
		}
	}
	
	private void checkOtherFields() {
		//Check for cone
		if(tempServing.getClass() == IceCreamCone.class) comboCone.setDisable(false);
		else comboCone.setDisable(true);
	}
	
	private void clearFields() {
		for(int i = 0; i < MAX_FLAVORS; i++) comboFlavors[i].getSelectionModel().clearSelection();
		for(int i = 0; i < MAX_EXTRAS; i++) comboExtras[i].getSelectionModel().clearSelection();
		comboCone.getSelectionModel().clearSelection();
		btnAddToOrder.setDisable(true);
	}
	
	private void updateFields() {
		if(formIsValid()) {
			btnAddToOrder.setDisable(false);
			btnAddToOrder.setText("Add To Order: " + moneyFormat.format(tempServing.getPrice()));
		} else {
			btnAddToOrder.setDisable(true);
			btnAddToOrder.setText("Add To Order");
		}
	}
	
	private boolean formIsValid() {
		if(tempServing == null) return false;
		
		for(int i = 0; i < tempServing.getMaxScoops(); i++) {
			if(comboFlavors[i].getSelectionModel().getSelectedIndex() == -1) return false;
		}
		
		for(int i = 0; i < tempServing.getMaxExtras(); i++) 
			if(comboExtras[i].getSelectionModel().getSelectedIndex() == -1) return false;
		
		return true;
	}
	
	private void setAllowedCombo(int num, ComboBox[] boxes) {
		for(int i = 0; i < MAX_FLAVORS; i++) {
			if(i < num) boxes[i].setDisable(false);
			else boxes[i].setDisable(true);
		}
	}

}