package teamoortcloud.entities;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class CustomerManager {
	
	ArrayList<CustomerCharacter> customers;
	int baseY = 200; 
	int startX = 0;
	int endX = 300;
	
	public CustomerManager() {
		customers = new ArrayList<>();
	}
	
	public void update() {
		for(CustomerCharacter c : customers) c.update();
	}
	
	public void draw(GraphicsContext gc) {
		for(CustomerCharacter c : customers) c.draw(gc);
	}
	
	public void queueNewCustomer() {
		CustomerCharacter c = new CustomerCharacter(496, 272);
		travelToLine(c);
		customers.add(c);
	}
	
	public void dequeueCustomer() {
		//customers.remove(0);
		customers.get(0).travelTo(320, 32);
//		for(CustomerCharacter c : customers) {
//			c.stopTraveling();
//			travelToLine(c);
//		}
	}
	
	private void travelToLine(CustomerCharacter c) {
		c.travelTo(496, (customers.size() * 20) + 144);
	}
}
