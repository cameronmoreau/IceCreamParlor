package teamoortcloud.scenes;

import java.util.ArrayList;

import teamoortcloud.icecream.IceCream;
import teamoortcloud.icecream.IceCreamBananaSplit;
import teamoortcloud.icecream.IceCreamCone;
import teamoortcloud.icecream.IceCreamRootBeerFloat;
import teamoortcloud.icecream.IceCreamSoda;
import teamoortcloud.icecream.IceCreamSundae;
import teamoortcloud.icecream.Serving;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CheckoutState extends AppState {
	
	static final int MAX_FLAVORS = 3;
	static final int MAX_EXTRAS = 3;
	Serving tempServing;
	
	ArrayList<Worker> workers;
	ArrayList<Customer> customers;
	ArrayList<IceCream> icecream;
	
	ComboBox<String> comboFlavors[];
	ComboBox<String> comboExtras[];
	ComboBox<String> comboCone;

	public CheckoutState(StateManager sm) {
		super(sm);
	}
	
	public CheckoutState(StateManager sm, ArrayList<Worker> workers, ArrayList<Customer> customers, ArrayList<IceCream> icecream) {
		super(sm);
		this.workers = workers;
		this.customers = customers;
		this.icecream = icecream;
		
		//Panes
		BorderPane basePane = new BorderPane();
		basePane.setPadding(new Insets(15));
		
		
		//cone[cake, sugar, waffle] Flavors: 3
		//sundae, flavors: 1, nuts, extraFlavor
		//Split, flavors: 1, nuts, 3 extra flavors
		//float, nothing
		//Soda, flavors: 1
		
		basePane.setRight(setupRightPane());
		basePane.setLeft(setupLeftPane());
		
				
		scene = new Scene(basePane);
	}
	
	private VBox setupLeftPane() {
		VBox leftPane = new VBox();
		
		Label labelCustomer = new Label("Current Customer: <Customer>");
		
		TableView<String> itemsTable = new TableView<>();
		itemsTable.getColumns().addAll(
			new TableColumn<String, String>("Item"),
			new TableColumn<String, String>("Details"),
			new TableColumn<String, String>("Price")
		);
		
		//Bottom buttons
		Button btnAddItem = new Button("Add Item");
		Button btnCheckoutCredit = new Button("Checkout with Credit");
		Button btnCheckoutCash = new Button("Checkout with Cash  ");
		
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(btnAddItem);
		borderPane.setRight(new VBox(btnCheckoutCredit, btnCheckoutCash));
		
		leftPane.getChildren().addAll(labelCustomer, itemsTable, borderPane);
		
		return leftPane;
	}
	
	private VBox setupRightPane() {
		VBox rightPane = new VBox();
		
		//Setup combo boxes
		String orderTypes[] = {"Cone", "Sundae", "Split", "Float", "Soda"};
		ObservableList<String> orderTypesList = FXCollections.observableArrayList(orderTypes);
		
		String flavors[] = new String[icecream.size()];
		for(int i = 0; i < icecream.size(); i++) {
			flavors[i] = icecream.get(i).getFlavor();
		}
		
		ObservableList<String> flavorTypesList = FXCollections.observableArrayList(flavors);
		
		//Setup compbo flavors
		comboFlavors = new ComboBox[MAX_FLAVORS];
		
		
		
		ComboBox<String> comboOrderType = new ComboBox(orderTypesList);
		comboOrderType.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {
			
				iceCreamSelected(value);
			}
			
		});
		
		rightPane.getChildren().addAll(new Label("Icecream Type:"), comboOrderType);
		
		//Cone
		comboCone = new ComboBox<String>();
		comboCone.getItems().addAll("Cake Cone", "Sugar Cone", "Waffle Cone");
		rightPane.getChildren().addAll(new Label("Cone Type:"), comboCone);
		
		//Flavors
		rightPane.getChildren().add(new Label("Flavors:"));
		for(int i = 0; i < MAX_FLAVORS; i++) {
			comboFlavors[i] = new ComboBox<String>(flavorTypesList);
			comboFlavors[i].setDisable(true);
			
			rightPane.getChildren().add(comboFlavors[i]);
		}
		
		//Extras
		rightPane.getChildren().add(new Label("Icecream Extras:"));
//		comboExtras = new ComboBox[MAX_EXTRAS];
//		
//		for(int i = 0; i < MAX_EXTRAS; i++) {
//			comboExtras[i] = new ComboBox<String>(flavorTypesList);
//			comboExtras[i].setDisable(true);
//			
//			rightPane.getChildren().add(comboFlavors[i]);
//		}

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
		
		setAllowedFlavors(tempServing.getMaxScoops());
		System.out.println(tempServing.getMaxScoops());
	}
	
	private void setAllowedFlavors(int num) {
		for(int i = 0; i < MAX_FLAVORS; i++) {
			if(i < num) comboFlavors[i].setDisable(false);
			else comboFlavors[i].setDisable(true);
		}
	}

}
