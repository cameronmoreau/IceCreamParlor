package teamoortcloud.scenes;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

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
import teamoortcloud.people.Stocker;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StockerState extends AppState {

	Shop shop;
	ArrayList<IceCream> icecream;
	IceCream selectedIceCream;
	Stocker stocker;
	
	Button btnUpdateIceCream,btnBreak,btnReturn;
	
	ListView<String> icecreamList;

	public StockerState(StateManager sm) {
		super(sm);
	}
	
	public StockerState(StateManager sm, Shop shop) {
		super(sm);
		this.shop=shop;
		stocker = shop.getActiveStocker;
		this.icecream = shop.getIceCream();
		
		
		//Panes
		BorderPane basePane = new BorderPane();
		basePane.setPadding(new Insets(15));
		
		basePane.setTop(setupPane());
		
		scene = new Scene(basePane);
	}
	
	private VBox setupPane() {
		VBox stockerPane = new VBox();
		stockerPane.setSpacing(5);
		stockerPane.setPadding(new Insets(0, 5, 5, 5));
		
		Label labelWorker = new Label("Current Stocker: " + stocker.getName() + "\tStamina: 18");// + stocker.getStamina());
		
		icecreamList = new ListView<>();
		icecreamList.setItems(getIceCreamListData());
		icecreamList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {
				
				selectedIceCream = icecream.get(icecreamList.getSelectionModel().getSelectedIndex());
				updateSelectedIceCream();
			}
			
		});
		btnUpdateIceCream = new Button("Fill Ice Cream");
		btnBreak = new Button("Go on break");
		btnReturn = new Button("Return from break");
		
		btnUpdateIceCream.setOnAction(e -> stockIceCream());
		btnBreak.setOnAction(e -> goOnBreak());
		btnReturn.setOnAction(e-> leaveBreak());
		btnReturn.setVisible(false);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(btnUpdateIceCream);
		borderPane.setRight(new HBox(btnBreak,btnReturn));
		
		stockerPane.getChildren().addAll(labelWorker, icecreamList, borderPane);
		
		return stockerPane;
	}
	
	private void stockIceCream() 
	{
		selectedIceCream.setScoops(80);
		stocker.changeStamina(-1);
	}
	
	private void goOnBreak()
	{
		//stocker.goOnBreak();
		btnReturn.setVisible(true);
		btnBreak.setVisible(false);
		btnUpdateIceCream.setVisible(false);
	}
	
	private void leaveBreak()
	{
		//stocker.leaveBreak();
		btnBreak.setVisible(true);
		btnReturn.setVisible(false);
		btnUpdateIceCream.setVisible(true);
	}

	//Helper functions
	private void updateSelectedIceCream() {
		if(selectedIceCream.getScoops()==80||stocker.getStamina()==0)
			btnUpdateIceCream.setDisable(true);
		else
			btnUpdateIceCream.setDisable(false);
		
	}

	private ObservableList<String> getIceCreamListData() {
		ObservableList<String> array = FXCollections.observableArrayList();
		for(IceCream i : icecream) array.add(i.getFlavor() + ": " + i.getScoops());
		return array;
	}
}
