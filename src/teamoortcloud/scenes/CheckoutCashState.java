package teamoortcloud.scenes;

/**
 * Created by Cameron on 11/29/15.
 */
import static java.awt.SystemColor.text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamoortcloud.other.Shop;

public class CheckoutCashState extends AppState {

    Button btnPay;
    Button btnGoBack;
    Shop shop;
    
    TextField[] textFields;
    String[] fieldTypes = { "Pennies", "Nickles", "Dimes", "Quarters",
        "Ones", "Fives", "Tens", "Twenties"};
    
    public CheckoutCashState(StateManager sm) {
        super(sm);
        
        VBox rootPane = new VBox();
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        btnPay = new Button("Submit");
        btnGoBack = new Button("Go Back");
        
        btnPay.setOnAction(e -> payUsingCash());
        btnGoBack.setOnAction(e -> goBack());
        
        //Init grid textfields and labels
        textFields = new TextField[fieldTypes.length];
        for(int i = 0; i < fieldTypes.length; i++) {
            textFields[i] = new TextField("0") {
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
            
            grid.add(new Label(fieldTypes[i] + ": "), 0, i);
            grid.add(textFields[i], 1, i);
        }
        
        //Init the buttons
        grid.add(btnPay,1,9);
        grid.add(btnGoBack,0,9);
        //Add grid and buttons to the root pane
        rootPane.getChildren().addAll(grid);
        
        this.scene = new Scene(rootPane);
    }
    

 

    private void payUsingCash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void goBack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
