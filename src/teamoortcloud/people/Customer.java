package teamoortcloud.people;

import teamoortcloud.other.Transaction;

import java.util.Random;

public class Customer {
    long id;
    String name;
    double totalMoney;
    int happiness;
    Transaction wallet;
    
    public Customer(long id, String name, double totalMoney, int happiness, int pennies, int nickles,
                int dimes, int quarters, int dollar1, int dollar5, int dollar10, int dollar20) {
		super();
		this.id = id;
		this.name = name;
		this.totalMoney = totalMoney;
        this.happiness = happiness;

        this.wallet = new Transaction();
        this.wallet.pennies = pennies;
        this.wallet.nickels = nickles;
        this.wallet.dimes = dimes;
        this.wallet.quarters = quarters;
        this.wallet.ones = dollar1;
        this.wallet.fives = dollar5;
        this.wallet.tens = dollar10;
        this.wallet.twenties = dollar20;
	}

    //Make sure they have enough money
    public boolean checkWallet(Transaction choosenWallet) {
        Transaction newWallet = new Transaction();

        newWallet.pennies = wallet.pennies - choosenWallet.pennies;
        newWallet.nickels = wallet.nickels - choosenWallet.nickels;
        newWallet.dimes = wallet.dimes - choosenWallet.dimes;
        newWallet.quarters = wallet.quarters - choosenWallet.quarters;
        newWallet.ones = wallet.ones - choosenWallet.ones;
        newWallet.fives = wallet.fives - choosenWallet.fives;
        newWallet.tens = wallet.tens - choosenWallet.tens;
        newWallet.twenties = wallet.twenties - choosenWallet.twenties;

        //Make sure customer has enough
        if(newWallet.pennies < 0 || newWallet.nickels < 0 ||
                newWallet.dimes < 0 || newWallet.quarters < 0 ||
                newWallet.ones < 0 || newWallet.fives < 0 ||
                newWallet.tens < 0 || newWallet.twenties < 0) return false;

        return true;
    }

    public void takeMoney(Transaction change) {
        wallet.pennies -= change.pennies;
        wallet.nickels -= change.nickels;
        wallet.dimes -= change.dimes;
        wallet.quarters -= change.quarters;
        wallet.ones -= change.ones;
        wallet.fives -= change.fives;
        wallet.tens -= change.tens;
        wallet.twenties -= change.twenties;
    }



    public String pureDissapointment() {
        String badActions[] = {
                "begins flipping tables",
                "rates the store 1 start on yelp",
                "begins crying and pulling their hair out",
                "sets fire to the shop",
                "drives their car though the window"
        };

        return name + " " + badActions[new Random().nextInt(badActions.length)] + " and leaves";
	}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getHappiness() {
        return happiness;
    }

    public Transaction getWallet() {
        return wallet;
    }
}
