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
    
    public int getPennies()
    {
    	return pennies;
    }
    public int getNickels()
    {
    	return nickels;
    }
    public int getDimes()
    {
    	return dimes;
    }
    public int getQuarters()
    {
    	return quarters;
    }
    public int getOnes()
    {
    	return ones;
    }
    public int getFives()
    {
    	return fives;
    }
    public int getTens()
    {
    	return tens;
    }
    public int getTwenties()
    {
    	return twenties;
    }
}
