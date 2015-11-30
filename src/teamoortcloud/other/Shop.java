package teamoortcloud.other;

import teamoortcloud.engine.DataLoader;
import teamoortcloud.engine.ShopLog;
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
        void newCustomer();
        void removeCustomer();
    }

    ArrayList<IceCream> icecream;
    ArrayList<Customer> customers;
    ArrayList<Worker> employees;
    ArrayList<Order> orders;

    Stocker activeStocker;
    Cashier activeCashier;
    ShopLog log;

    CashRegister register;

    private ShopDataChangedListener listener;

    public Shop() {

        DataLoader dl = new DataLoader();
        employees = dl.getWorkers();
        customers = dl.getCustomers();
        icecream = dl.getIceCream();

        orders = new ArrayList<>();
        register = new CashRegister();
        log = new ShopLog();
    }

    public ArrayList<Stocker> getOnBreakStockers() {
        ArrayList<Stocker> l = new ArrayList<>();
        for(Worker w : employees) {
            if(w.getClass() == Stocker.class) {
                if(((Stocker)w).getOnBreak()) l.add((Stocker)w);
            }
        }

        return l;
    }

    public ArrayList<Cashier> getOnBreakCashiers() {
        ArrayList<Cashier> l = new ArrayList<>();
        for(Worker w : employees) {
            if(w.getClass() == Cashier.class) {
                if(((Cashier)w).getOnBreak()) l.add((Cashier)w);
            }
        }

        return l;
    }

    public ShopLog getLog() {
        return log;
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
    
    public void addIceCream(IceCream ic) {this.icecream.add(ic); }
    
    public void removeIceCream(int i) {this.icecream.remove(i); }

    public void addWorker(Worker w) { this.employees.add(w); }

    public void removeWorker(int i) { this.employees.remove(i); }

    public void addCustomer(Customer c) {
        if(listener != null) listener.newCustomer();
        this.customers.add(c);
    }

    public void removeCustomer(int i) {
        if(listener != null) listener.removeCustomer();
        this.customers.remove(i);
    }
    
    public void addOrder(Order o) {
        this.orders.add(o);

        if(listener != null) listener.removeCustomer();

        for(Stocker s : getOnBreakStockers()) s.changeStamina(1);
        for(Cashier s : getOnBreakCashiers()) s.updatePatience(1);
    }

    public void removeOrder(int i) { this.orders.remove(i); }

    public void setDataChanged() {
        if(listener != null) listener.dataChanged();
    }
}
