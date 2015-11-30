package teamoortcloud.scenes;

/**
 * Created by Cameron on 11/29/15.
 */

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import teamoortcloud.other.Order;
import teamoortcloud.other.Shop;

public class CheckoutCashState extends AppState {

    Button btnPay;
    Button btnGoBack;
    Order order;
    Shop shop;
    
    Double howmuch;
    
    Label current;
    Label[] valid;
    TextField[] textFields;
    String[] fieldTypes = { "Pennies", "Nickles", "Dimes", "Quarters",
        "Ones", "Fives", "Tens", "Twenties"};
    
    public CheckoutCashState(StateManager sm, Order order, Shop shop) {
        super(sm);
        this.order = order;
        this.shop = shop;
        howmuch=0.0;
        valid = new Label[8];
        VBox rootPane = new VBox();
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(5);
        grid.setPadding(new Insets(20));
        btnPay = new Button("Submit");
        btnPay.setDisable(true);
        btnGoBack = new Button("Cancel Order");
        
        btnPay.setOnAction(e -> payUsingCash());
        btnGoBack.setOnAction(e -> cancelOrder());
        grid.add(new Label(order.getCustomer().getName()+" wallet"), 1, 0);
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
            
            grid.add(new Label(fieldTypes[i] + ": "), 0, i+1);
            grid.add(textFields[i], 2, i+1);
            
        }
        for(int k=0;k<8;k++)
        {
        	valid[k]=new Label("Valid Amount");
        	grid.add(valid[k], 3, k+1);
        }
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getPennies())), 1, 1);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getNickels())), 1, 2);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getDimes())), 1, 3);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getQuarters())), 1, 4);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getOnes())), 1, 5);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getFives())), 1, 6);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getTens())), 1, 7);
        grid.add(new Label(String.valueOf(order.getCustomer().getWallet().getTwenties())), 1, 8);
        grid.add(new Label("$"+String.valueOf(order.getCustomer().getTotalMoney())), 1, 9);
        current = new Label("$"+String.valueOf(howmuch));
        for(int j=0;j< fieldTypes.length; j++)
        	textFields[j].textProperty().addListener((observable, oldValue, newValue) -> {
        		validAmount();
        	});
        grid.add(current, 2, 9);
        grid.add(new Label("Need: "+String.valueOf(order.getTotal())), 3, 9);
        //Init the buttons
        grid.add(btnPay,1,10);
        grid.add(btnGoBack,0,10);
        //Add grid and buttons to the root pane
        rootPane.getChildren().addAll(grid);
        
        this.scene = new Scene(rootPane,600,350);
    }
    public void validAmount()
    {
    	int p=0,n=0,d=0,q=0,o=0,f=0,t=0,tt=0;
    	if(textFields[0].getText().length()>0)
    		p=Integer.parseInt(textFields[0].getText());
    	if(textFields[1].getText().length()>0)
    		n=Integer.parseInt(textFields[1].getText());
    	if(textFields[2].getText().length()>0)
    		d=Integer.parseInt(textFields[2].getText());
    	if(textFields[3].getText().length()>0)
    		q=Integer.parseInt(textFields[3].getText());
    	if(textFields[4].getText().length()>0)
    		o=Integer.parseInt(textFields[4].getText());
    	if(textFields[5].getText().length()>0)
    		f=Integer.parseInt(textFields[5].getText());
    	if(textFields[6].getText().length()>0)
    		t=Integer.parseInt(textFields[6].getText());
    	if(textFields[7].getText().length()>0)
    		tt=Integer.parseInt(textFields[7].getText());
    	if(p>order.getCustomer().getWallet().getPennies())
    		valid[0].setText("Not Valid Amount");
    	else if(p<=order.getCustomer().getWallet().getPennies())
    	{
    		valid[0].setText("Valid Amount");
    		changeAmount();
    	}
    	if(n>order.getCustomer().getWallet().getNickels())
    		valid[1].setText("Not Valid Amount");
    	else if(n<=order.getCustomer().getWallet().getNickels())
    	{
    		valid[1].setText("Valid Amount");
    		changeAmount();
    	}
    	if(d>order.getCustomer().getWallet().getDimes())
    		valid[2].setText("Not Valid Amount");
    	else if(d<=order.getCustomer().getWallet().getDimes())
    	{
    		valid[2].setText("Valid Amount");
    		changeAmount();
    	}
    	if(q>order.getCustomer().getWallet().getQuarters())
    		valid[3].setText("Not Valid Amount");
    	else if(q<=order.getCustomer().getWallet().getQuarters())
    	{
    		valid[3].setText("Valid Amount");
    		changeAmount();
    	}
    	if(o>order.getCustomer().getWallet().getOnes())
    		valid[4].setText("Not Valid Amount");
    	else if(o<=order.getCustomer().getWallet().getOnes())
    	{
    		valid[4].setText("Valid Amount");
    		changeAmount();
    	}
    	if(f>order.getCustomer().getWallet().getFives())
    		valid[5].setText("Not Valid Amount");
    	else if(f<=order.getCustomer().getWallet().getFives())
    	{
    		valid[5].setText("Valid Amount");
    		changeAmount();
    	}
    	if(t>order.getCustomer().getWallet().getTens())
    		valid[6].setText("Not Valid Amount");
    	else if(t<=order.getCustomer().getWallet().getTens())
    	{
    		valid[6].setText("Valid Amount");
    		changeAmount();
    	}
    	if(tt>order.getCustomer().getWallet().getTwenties())
    		valid[7].setText("Not Valid Amount");
    	else if(tt<=order.getCustomer().getWallet().getTwenties())
    	{
    		valid[7].setText("Valid Amount");
    		changeAmount();
    	}
    	double enough = howmuch;
    	if(enough>=order.getTotal())
    		btnPay.setDisable(false);
    	else
    		btnPay.setDisable(true);
    }
    
    public void changeAmount()
	{
    	int pennies;
    	int nickels;
    	int dimes;
    	int quarters;
    	int ones;
    	int fives;
    	int tens;
    	int twenties;
    	if(textFields[0].getText().length() > 0)
    		pennies=Integer.parseInt(textFields[0].getText());
    	else
    		pennies=0;
    	if(textFields[1].getText().length() > 0)
    		nickels=Integer.parseInt(textFields[1].getText());
    	else
    		nickels=0;
    	if(textFields[2].getText().length() > 0)
    		dimes=Integer.parseInt(textFields[2].getText());
    	else
    		dimes=0;
    	if(textFields[3].getText().length() > 0)
    		quarters=Integer.parseInt(textFields[3].getText());
    	else
    		quarters=0;
    	if(textFields[4].getText().length() > 0)
    		ones=Integer.parseInt(textFields[4].getText());
    	else
    		ones=0;
    	if(textFields[5].getText().length() > 0)
    		fives=Integer.parseInt(textFields[5].getText());
    	else
    		fives=0;
    	if(textFields[6].getText().length() > 0)
    		tens=Integer.parseInt(textFields[6].getText());
    	else
    		tens=0;
    	if(textFields[7].getText().length() > 0)
    		twenties=Integer.parseInt(textFields[7].getText());
    	else
    		twenties=0;
    	howmuch=(pennies*0.01)+
				(nickels*0.05)+
				(dimes*0.10)+
				(quarters*0.25)+
				(ones*1.0)+
				(fives*5.0)+
				(tens*10.0)+
				(twenties*20.0);
				
		
		current.setText("$"+String.valueOf(howmuch));
	}
 

    private void payUsingCash() {
        shop.getRegister().makeChange(order.getCustomer().getWallet(), howmuch);
		shop.addOrder(order);
        sm.getStage().close();
    }

    private void cancelOrder() {
        sm.getStage().close();
    }
}
