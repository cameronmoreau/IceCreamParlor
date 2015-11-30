package teamoortcloud.other;

public class Transaction {

    public int pennies,
            nickels,
            dimes,
            quarters,
            ones,
            tens,
            fives,
            twenties;

    public double getTotal() {
        return pennies*.01 + nickels*.05 +
                dimes*.10 + quarters*.25 +
                ones + fives*5 + tens*10 +
                twenties*20;
    }
    
    public void findBills(double money)
    {
    	CashRegister register = new CashRegister(money);
    	Transaction temp = register.makeChange(choosenWallet, price)
    }

}
