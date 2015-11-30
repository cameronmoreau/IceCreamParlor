package teamoortcloud.other;

import teamoortcloud.engine.DataLoader;
import teamoortcloud.icecream.IceCream;
import teamoortcloud.people.Cashier;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

import java.util.ArrayList;

/**
 * Created by Cameron on 11/29/15.
 */

public class Shop {

    public interface ShopDataChangedListener {
        void dataChanged();
    }

    ArrayList<IceCream> icecream;
    ArrayList<Customer> customers;
    ArrayList<Worker> employees;
    ArrayList<Order> orders;

    Stocker activeStocker;
    Cashier activeCashier;

    CashRegister register;

    private ShopDataChangedListener listener;

    public Shop() {
        employees = DataLoader.getWorkers();
        customers = DataLoader.getCustomers();
        icecream = DataLoader.getIceCream();

        orders = new ArrayList<>();
        register = new CashRegister();
    }

    public void setListener(ShopDataChangedListener listener) {
        this.listener = listener;
    }

    public ArrayList<IceCream> getIcecream() {
        return icecream;
    }

    public void setIcecream(ArrayList<IceCream> icecream) {
        this.icecream = icecream;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Worker> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Worker> employees) {
        this.employees = employees;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Stocker getActiveStocker() {
        return activeStocker;
    }

    public void setActiveStocker(Stocker activeStocker) {
        this.activeStocker = activeStocker;
    }

    public Cashier getActiveCashier() {
        return activeCashier;
    }

    public void setActiveCashier(Cashier activeCashier) {
        this.activeCashier = activeCashier;
    }

    public CashRegister getRegister() {
        return register;
    }

    public void setRegister(CashRegister register) {
        this.register = register;
    }

    public void addWorker(Worker w) { this.employees.add(w); }

    public void removeWorker(int i) { this.employees.remove(i); }

    public void addCustomer(Customer c) { this.customers.add(c); }

    public void removeCustomer(int i) { this.customers.remove(i); }
    
    public void addOrder(Order o) { this.orders.add(o); }

    public void removeOrder(int i) { this.orders.remove(i); }

    public void setDataChanged() {
        if(listener != null) listener.dataChanged();
    }
}
