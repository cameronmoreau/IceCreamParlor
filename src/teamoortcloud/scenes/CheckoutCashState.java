package teamoortcloud.scenes;

/**
 * Created by Cameron on 11/29/15.
 */
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import teamoortcloud.other.Shop;

public class CheckoutCashState extends AppState {

    Button btnPay;
    Button btnGoBack;
    Shop shop;
    
    public CheckoutCashState(StateManager sm) {
        super(sm);
        
    }
    public CheckoutCashState(StateManager sm, Shop shop){
        super(sm);
        this.shop = shop;
        BorderPane basePane = new BorderPane();
        basePane.setPadding(new Insets(15));
		
        basePane.setRight(setupRightPane());
    	basePane.setLeft(setupLeftPane());
		
	scene = new Scene(basePane);
    }

    private Node setupRightPane() {
        VBox leftPane = new VBox();
	leftPane.setSpacing(5);
	leftPane.setPadding(new Insets(0, 5, 5, 5));
        Button btnPay= new Button("Pay");
        btnPay.setOnAction(e -> payUsingCash());
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(new VBox(btnPay));
        borderPane.setLeft(btnGoBack);
        btnGoBack.setOnAction(e -> goBack());
        leftPane.getChildren().addAll( borderPane);
        return leftPane;
    }

    private Node setupLeftPane() {
        VBox rightPane = new VBox();
	rightPane.setSpacing(5);
	rightPane.setPadding(new Insets(0, 5, 5, 5));
        return rightPane;
    }

    private void payUsingCash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void goBack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
