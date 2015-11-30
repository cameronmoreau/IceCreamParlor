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
import teamoortcloud.icecream.IceCream;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Cashier;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

public class IceCreamState extends AppState {
	
	NumberFormat moneyFormat;
	
	Shop shop;
	IceCream selectedIceCream;
	
	Label labelSelectedIceCream;
	ComboBox<String> comboSelectedType, comboWorkerType;
	Button btnAddIceCream, btnUpdateName, btnUpdateCost, btnUpdateFlavor;
	TextField tfIceCreamName, tfIceCreamCost, tfNewIceCreamName, tfNewIceCreamCost, tfIceCreamFlavor, tfNewIceCreamFlavor;
	ListView<String> iceCreamList;
	

	public IceCreamState(StateManager sm) {
		super(sm);
	}
	
	public IceCreamState(StateManager sm, Shop shop) {
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
		
		iceCreamList = new ListView<>();
		iceCreamList.setItems(getIceCreamListData());
		iceCreamList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> list,
					String previousValue, String value) {

                int index = iceCreamList.getSelectionModel().getSelectedIndex();
                if(index != -1) {
                    selectedIceCream = shop.getIcecream().get(iceCreamList.getSelectionModel().getSelectedIndex());
                    updateSelectedIceCream();
                }
			}
			
		});
		
		pane.getChildren().add(iceCreamList);
		
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
		
		btnAddIceCream = new Button("Add Ice Cream");
		btnAddIceCream.setDisable(true);
		btnAddIceCream.setOnAction(e -> addNewIceCream());
		tfIceCreamName = new TextField();
		tfIceCreamName.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewForm();
		});
		tfIceCreamFlavor = new TextField();
		tfIceCreamFlavor.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewForm();
		});
		tfIceCreamCost = new TextField(){
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
        tfIceCreamCost.textProperty().addListener((observable, oldValue, newValue) -> {updateNewForm();});
		
		
		topPane.getChildren().addAll(
			new Label("Customer's Name:"), tfIceCreamName,
			new Label("Cost:"), tfIceCreamCost,
			new Label("Flavor:"),tfIceCreamFlavor,
			btnAddIceCream
		);
		
		//Bottom Pane
		HBox updatePane = new HBox();
		updatePane.setSpacing(5);
		
		
		btnUpdateName = new Button("Update Name");
		btnUpdateCost = new Button("Update Cost");
		btnUpdateFlavor = new Button("Update Flavor");
		btnUpdateName.setOnAction(e -> updateName());
		btnUpdateCost.setOnAction(e -> updateCost());
		btnUpdateFlavor.setOnAction(e -> updateFlavor());
		btnUpdateName.setDisable(true);
		btnUpdateCost.setDisable(true);
		btnUpdateFlavor.setDisable(true);
		
		labelSelectedIceCream = new Label("Nothing selected...");
		

		tfNewIceCreamName = new TextField();
		tfNewIceCreamName.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewNameForm();
		});
		tfNewIceCreamFlavor = new TextField();
		tfNewIceCreamFlavor.textProperty().addListener((observable, oldValue, newValue) -> {
			updateNewFlavorForm();
		});
		tfNewIceCreamCost = new TextField(){
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
		tfNewIceCreamCost.textProperty().addListener((observable, oldValue, newValue) -> {updateNewCostForm();});
		
		bottomPane.getChildren().addAll(labelSelectedIceCream,tfNewIceCreamName,btnUpdateName,tfNewIceCreamCost,btnUpdateCost, tfNewIceCreamFlavor, btnUpdateFlavor);
		
		pane.setTop(topPane);
		pane.setBottom(bottomPane);
		return pane;
	}

	//Helper functions
	private void updateSelectedIceCream() {

		labelSelectedIceCream.setText("NAME: "+selectedIceCream.getName()+
				"\nCost: "+moneyFormat.format(selectedIceCream.getPrice())+
				"\nFlavor: "+selectedIceCream.getFlavor());

		
	}
	
	private void updateNewForm() {
		btnAddIceCream.setDisable(!newFormIsValid());
	}
	
	private void updateNewNameForm()
	{
		btnUpdateName.setDisable(tfNewIceCreamName.getText().length()<1);
	}
	
	private void updateNewFlavorForm()
	{
		btnUpdateFlavor.setDisable(tfNewIceCreamFlavor.getText().length()<1);
	}
	
	private void updateNewCostForm()
	{
		if(tfNewIceCreamCost.getText().length()<0)
			btnUpdateCost.setDisable(tfNewIceCreamCost.getText()==null);
	}

	private void updateName()
	{
		selectedIceCream.setName(tfNewIceCreamName.getText());
		updateSelectedIceCream();
		iceCreamList.setItems(getIceCreamListData());
		btnUpdateName.setDisable(true);
		tfNewIceCreamName.clear();
		shop.setDataChanged();
	}
	
	private void updateCost()
	{
		selectedIceCream.setPrice(Double.parseDouble(tfNewIceCreamCost.getText()));
		updateSelectedIceCream();
		iceCreamList.setItems(getIceCreamListData());
		btnUpdateCost.setDisable(true);
		tfNewIceCreamCost.clear();
		shop.setDataChanged();
	}
	
	private void updateFlavor()
	{
		selectedIceCream.setFlavor(tfNewIceCreamFlavor.getText());
		updateSelectedIceCream();
		iceCreamList.setItems(getIceCreamListData());
		btnUpdateCost.setDisable(true);
		tfNewIceCreamFlavor.clear();
		shop.setDataChanged();
	}
	
	private void addNewIceCream() {
		IceCream temp;
		String name = tfIceCreamName.getText();
		double money = Double.parseDouble(tfIceCreamCost.getText());
		String flavor = tfIceCreamFlavor.getText();
		temp = new IceCream(0, name, money, flavor);
		
		//Update list
		shop.addIceCream(temp);
		iceCreamList.setItems(getIceCreamListData());
		shop.setDataChanged();
		
		//Clear lists
		tfIceCreamName.clear();
		tfIceCreamCost.clear();
		tfIceCreamFlavor.clear();
		btnAddIceCream.setDisable(true);
	}
	
	private Boolean newFormIsValid() {
		if(tfIceCreamName.getText()!=null) return true;
		if(tfIceCreamCost.getText()!=null)
				if(Double.parseDouble(tfIceCreamCost.getText())>0) return true;
		if(tfIceCreamFlavor.getText()!=null) return true;
		return false;
	}
	

    private void setActiveIceCream() {
        
        iceCreamList.setItems(getIceCreamListData());
    }

	private ObservableList<String> getIceCreamListData() {
		ObservableList<String> array = FXCollections.observableArrayList();
		for(IceCream ic : shop.getIcecream()) {
            String s = ic.getName();
            array.add(s);
        }
		return array;
	}
}
