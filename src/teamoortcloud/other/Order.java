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

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public boolean isPaid() {
        return paid;
    }

    public Worker getWorker() {
        return worker;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotal() {
        double t = 0;
        for(Serving s : servings) {
            t += s.getPrice();
        }
        return t;
    }
}
