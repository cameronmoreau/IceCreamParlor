package teamoortcloud.people;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cashier extends Worker {
	
	int patience;
	boolean onBreak;

	public Cashier() {
		super();
		this.patience = 0;
		this.onBreak = false;
	}
	
	public Cashier(long id, String name, long customersServed,
			long scoopsServed, double moneyTaken, int patience) {
		super(id, name, customersServed, scoopsServed, moneyTaken);
		this.patience = patience;
	}
	
	
	public Cashier(long id, String name) {
		super(id, name);
	}

    public void toggleBreak() { this.onBreak = !this.onBreak; }

	public boolean getOnBreak() { return this.onBreak; }

    public int getPatience() {
        return patience;
    }

    public void updatePatience(int a) { this.patience += a; }

    @Override
	public StringProperty onBreakProperty() {
		return new SimpleStringProperty(String.valueOf(onBreak));
	}
	
	@Override
	public String getType() {
		return "Cashier";
	}

	@Override
	public String toString() {
		return "Cashier [patience=" + patience + ", onBreak=" + onBreak
				+ ", id=" + id + ", name=" + name + ", customersServed="
				+ customersServed + ", scoopsServed=" + scoopsServed
				+ ", moneyTaken=" + moneyTaken + "]\n";
	}
	
}
