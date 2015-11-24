package teamoortcloud.people;

public class Stocker extends Worker {

	int stamina;
	boolean onBreak;
	
	public Stocker() {
		super();
		this.stamina = 0;
		this.onBreak = false;
	}

	public Stocker(long id, String name, long customersServed,
			long scoopsServed, double moneyTaken, int stamina) {
		super(id, name, customersServed, scoopsServed, moneyTaken);
		this.stamina = stamina;
	}

	@Override
	public String toString() {
		return "Stocker [stamina=" + stamina + ", onBreak=" + onBreak + ", id="
				+ id + ", name=" + name + ", customersServed="
				+ customersServed + ", scoopsServed=" + scoopsServed
				+ ", moneyTaken=" + moneyTaken + "]\n";
	}
	
}
