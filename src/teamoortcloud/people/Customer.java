package teamoortcloud.people;

public class Customer {
    long id;
    String name;
    double wallet;
    int happiness;
    int pennies, nickles, dimes, quarters, dollar1, dollar5, dollar10, dollar20; 
    
    public Customer(long id, String name, double wallet, int happiness, int pennies, int nickles,
                int dimes, int quarters, int dollar1, int dollar5, int dollar10, int dollar20) {
		super();
		this.id = id;
		this.name = name;
		this.wallet = wallet;
		this.happiness = happiness;
		this.pennies = pennies;
                this.nickles = nickles;
                this.dimes = dimes;
                this.quarters = quarters;
                this.dollar1 = dollar1;
                this.dollar5 = dollar5;
                this.dollar10 = dollar10;
                this.dollar20 = dollar20;
                
	}
    
    /**
     *
     * @return
     */
    
    public int getHappiness()
    {
    	return happiness;
    }
    public String getName()
    {
    	return name;
    }
    @Override
    public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", wallet=" + wallet +
                    ", happiness=" + happiness + ", pennies=" + pennies + 
                    ", nickles=" + nickles + ", dimes=" + dimes + ", quarters=" + quarters +
                    ", dollar1=" + dollar1 + ", dollar5=" + dollar5 + 
                    ", dollar10=" + dollar10 + ", dollar20=" + dollar20 + "]\n";
	}
	
}
