package teamoortcloud.people;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stocker extends Worker {

	int stamina;
	boolean onBreak;
	
	public Stocker() {
		super();
		this.stamina = 18;
		this.onBreak = false;
	}

	public Stocker(long id, String name, long customersServed,
			long scoopsServed, double moneyTaken, int stamina) {
		super(id, name, customersServed, scoopsServed, moneyTaken);
		this.stamina = stamina;
	}
	
	public Stocker(long id, String name) {
		super(id, name);
	}
	
	@Override
	public StringProperty onBreakProperty() {
		return new SimpleStringProperty(String.valueOf(onBreak));
	}
	
	public void toggleBreak() { this.onBreak = !this.onBreak; }

	public boolean getOnBreak() { return this.onBreak; }
	
	public int getStamina()
	{
		return stamina;
	}
	
	public void changeStamina(int work)
	{
		stamina+=work;
	}
	
	@Override
	public String getType() {
		return "Stocker";
	}

	@Override
	public String toString() {
		return "Stocker [stamina=" + stamina + ", onBreak=" + onBreak + ", id="
				+ id + ", name=" + name + ", customersServed="
				+ customersServed + ", scoopsServed=" + scoopsServed
				+ ", moneyTaken=" + moneyTaken + "]\n";
	}
	
}
