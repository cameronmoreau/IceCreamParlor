package teamoortcloud.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Worker;

import java.util.ArrayList;

public class ShopOverviewState extends AppState {

	Shop shop;
	ArrayList<Customer> customer;
	ArrayList<Worker> worker;
	
	Button btnBack;
	
	ListView<String> customerList;

	public ShopOverviewState(StateManager sm) {
		super(sm);
	}
	
	public ShopOverviewState(StateManager sm, Shop shop) {
		super(sm);
		this.shop=shop;
		this.customer = shop.getCustomers();
		this.worker = shop.getEmployees();
		
		//Panes
		BorderPane basePane = new BorderPane();
		basePane.setPadding(new Insets(15));
		
		basePane.setTop(setupPane());
		
		scene = new Scene(basePane, 600, 600);
                
	}
	
	private VBox setupPane() {
		VBox graphPane = new VBox();
		graphPane.setSpacing(5);
		graphPane.setPadding(new Insets(0, 5, 5, 5));
		
		ObservableList<PieChart.Data> pieHappiness = FXCollections.observableArrayList();
		for(Customer c : customer) pieHappiness.add(new PieChart.Data(c.getName(),c.getHappiness()));
        final PieChart pchartHappiness = new PieChart(pieHappiness);
        pchartHappiness.setTitle("Customer Happiness");
        //pchartHappiness.setPrefWidth(250);
        //pchartHappiness.setPrefHeight(250);
        pchartHappiness.setPrefSize(250,250);
        ObservableList<PieChart.Data> pieMoney = FXCollections.observableArrayList();
		for(Worker w : worker) pieMoney.add(new PieChart.Data(w.getName(),w.getMoneyTaken()));
        final PieChart pchartMoney = new PieChart(pieMoney);
        pchartMoney.setTitle("Worker Money"); 
        //pchartMoney.setPrefWidth(250);
        //pchartMoney.setPrefHeight(250);
        
        pchartMoney.setPrefSize(250,250);
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bchartHappiness = new BarChart<String,Number>(xAxis,yAxis);
        bchartHappiness.setTitle("");
        bchartHappiness.setLegendVisible(false);
        //bchartHappiness.setPrefWidth(250);
        //bchartHappiness.setPrefHeight(250);
        bchartHappiness.setPrefSize(250,300);
        xAxis.setLabel("Customer");       
        yAxis.setLabel("Happiness");
 
        XYChart.Series series = new XYChart.Series();
        for(Customer c : customer) series.getData().add(new XYChart.Data(c.getName(), c.getHappiness()));
        bchartHappiness.getData().add(series);
        
        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        final BarChart<String,Number> bchartMoney = new BarChart<String,Number>(xAxis2,yAxis2);
        bchartMoney.setTitle("");
        bchartMoney.setLegendVisible(false);
        //bchartMoney.setPrefWidth(250);
        //bchartMoney.setPrefHeight(250);
        bchartMoney.setPrefSize(250,300);
        xAxis2.setLabel("Worker");       
        yAxis2.setLabel("Money");
 
        XYChart.Series series2 = new XYChart.Series();
        for(Worker w : worker) series2.getData().add(new XYChart.Data(w.getName(),w.getMoneyTaken()));
        bchartMoney.getData().add(series2);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(new VBox(pchartHappiness,bchartHappiness));
		borderPane.setRight(new VBox(pchartMoney,bchartMoney));
		
		graphPane.getChildren().addAll(borderPane);
		
		return graphPane;
	}
	
	
}
