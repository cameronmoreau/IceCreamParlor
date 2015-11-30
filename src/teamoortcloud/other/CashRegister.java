package teamoortcloud.other;

public class CashRegister {

    double totalMoney;
    Transaction change;

    public CashRegister() {
        this.change = new Transaction();
        this.change.pennies = 40;
        this.change.nickels = 40;
        this.change.dimes = 40;
        this.change.quarters = 25;
        this.change.ones = 40;
        this.change.fives = 20;
        this.change.tens = 15;
        this.change.fives = 10;

        this.totalMoney += change.getTotal();
    }
    
    public CashRegister(double money)
    {
    	totalMoney = money;
    }

    //How much from the very beginning
    public double getTotalMoney() {
        return totalMoney;
    }

    //How much is currently in the cash register
    public Transaction getTil() {
        return change;
    }

    public Transaction makeChange(Transaction choosenWallet, double price) {
        Transaction changeBack = new Transaction();

        int priceChange = (int)((Math.ceil(price) - price) * 100);
        int coinChange = (int)((Math.ceil(choosenWallet.getTotal()) - choosenWallet.getTotal()) * 100);
        int dollarRemaining = (int)(Math.ceil(choosenWallet.getTotal()) - Math.ceil(price));
        int remaining = priceChange - coinChange;

        if(remaining != 0) dollarRemaining += 1;

        //Get Change Back
        changeBack.quarters = remaining / 25;
        remaining = remaining % 25;

        changeBack.dimes = remaining / 10;
        remaining = remaining % 10;

        changeBack.nickels = remaining / 5;
        remaining = remaining % 5;

        changeBack.pennies = remaining / 1;

        //Get Dollars change back
        changeBack.twenties = dollarRemaining / 20;
        remaining = remaining % 20;

        changeBack.tens = dollarRemaining / 10;
        remaining = remaining % 10;

        changeBack.fives = dollarRemaining / 5;
        remaining = remaining % 5;

        changeBack.ones = dollarRemaining / 1;

        return changeBack;
    }

    public void takeFromRegister(Transaction amountToTake) {
        this.change.pennies -= amountToTake.pennies;
        this.change.nickels -= amountToTake.nickels;
        this.change.dimes -= amountToTake.dimes;
        this.change.quarters -= amountToTake.quarters;
        this.change.ones -= amountToTake.ones;
        this.change.fives -= amountToTake.fives;
        this.change.tens -= amountToTake.tens;
        this.change.twenties -= amountToTake.twenties;

        totalMoney -= amountToTake.getTotal();
    }

    public void addMoney(Transaction transaction) {
        this.change.pennies += transaction.pennies;
        this.change.nickels += transaction.nickels;
        this.change.dimes += transaction.dimes;
        this.change.quarters += transaction.quarters;
        this.change.ones += transaction.ones;
        this.change.fives += transaction.fives;
        this.change.tens += transaction.tens;
        this.change.twenties += transaction.twenties;

        totalMoney += transaction.getTotal();
    }

    //MARK: -- Getters and setters
    public void addCredit(double amount) {
        totalMoney += amount;
    }

    public double getTotalCredit() {
        return totalMoney - change.getTotal();
    }

}
