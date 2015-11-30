package teamoortcloud.scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import teamoortcloud.icecream.IceCream;
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
		
		scene = new Scene(basePane);
	}
	
	private VBox setupPane() {
		VBox graphPane = new VBox();
		graphPane.setSpacing(5);
		graphPane.setPadding(new Insets(0, 5, 5, 5));
		
		ObservableList<PieChart.Data> pieHappiness = FXCollections.observableArrayList();
		for(Customer c : customer) pieHappiness.add(new PieChart.Data(c.getName(),c.getHappiness()));
        final PieChart pchartHappiness = new PieChart(pieHappiness);
        pchartHappiness.setTitle("Customer Happiness");
        
        ObservableList<PieChart.Data> pieMoney = FXCollections.observableArrayList();
		for(Worker w : worker) pieMoney.add(new PieChart.Data(w.getName(),w.getMoneyTaken()));
        final PieChart pchartMoney = new PieChart(pieMoney);
        pchartMoney.setTitle("Worker Money");
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bchartHappiness = new BarChart<String,Number>(xAxis,yAxis);
        bchartHappiness.setTitle("");
        bchartHappiness.setLegendVisible(false);
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
