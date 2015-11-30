package teamoortcloud.people;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Worker {
	
	long id;
	String name;
	long customersServed, scoopsServed;
	double moneyTaken;
	
	public Worker() {
		this.customersServed = 0;
		this.scoopsServed = 0;
		this.moneyTaken = 0;
	}
	
	public Worker(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Worker(long id, String name, long customersServed,
			long scoopsServed, double moneyTaken) {
		super();
		this.id = id;
		this.name = name;
		this.customersServed = customersServed;
		this.scoopsServed = scoopsServed;
		this.moneyTaken = moneyTaken;
	}

	public void addMoneyTaken(double money) { this.moneyTaken += money; }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCustomersServed() {
		return customersServed;
	}

	public void setCustomersServed(long customersServed) {
		this.customersServed = customersServed;
	}

	public long getScoopsServed() {
		return scoopsServed;
	}

	public void setScoopsServed(long scoopsServed) {
		this.scoopsServed = scoopsServed;
	}

	public double getMoneyTaken() {
		return moneyTaken;
	}

	public void setMoneyTaken(double moneyTaken) {
		this.moneyTaken = moneyTaken;
	}
	
	public String getType() {
		return "Worker";
	}
	
	public StringProperty nameProperty() { 
		return new SimpleStringProperty(name); 
	}
	
	public StringProperty typeProperty() {
		return new SimpleStringProperty(getType());
	}
	
	public StringProperty onBreakProperty() {
		return new SimpleStringProperty("N/A");
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", customersServed="
				+ customersServed + ", scoopsServed=" + scoopsServed
				+ ", moneyTaken=" + moneyTaken + "]\n";
	}
	
	
	
}
