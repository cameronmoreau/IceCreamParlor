package teamoortcloud.other;

import java.util.ArrayList;

import teamoortcloud.icecream.Serving;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Worker;

public class Order {

	int orderNumber;
	boolean paid;
	
	Worker worker;
	Customer customer;
	ArrayList<Serving> servings;
	
	public Order() {
		servings = new ArrayList<>();
	}
	
	public void addServing(Serving serving) {
		servings.add(serving);
	}
	
	public void removeServing(int pos) {
		servings.remove(pos);
	}
	
	public ArrayList<Serving> getServings() {
		return servings;
	}
}
