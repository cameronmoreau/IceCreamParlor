package teamoortcloud.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class CustomerManager {
	
	ArrayList<CustomerCharacter> customers;
	int baseY = 200; 
	int startX = 0;
	int endX = 300;

	Image images[];
	
	public CustomerManager() {
		customers = new ArrayList<>();

		images = new Image[8];
		for(int i = 0; i < images.length; i++) {
			images[i] = new Image("file:res/characters/character-" + (i + 1) + ".png");
		}
	}
	
	private ArrayList<CustomerCharacter> getSelectCustomers(int state) {
		ArrayList<CustomerCharacter> customers = new ArrayList<>();
		for(CustomerCharacter c : this.customers) {
			if(state == c.getState()) customers.add(c);
		}
		
		return customers;
	}
	
	public void update() {
		for(CustomerCharacter c : customers) c.update();
	}
	
	public void draw(GraphicsContext gc) {
		for(CustomerCharacter c : customers) c.draw(gc);
	}
	
	public void queueNewCustomer() {
		Random r = new Random();

		CustomerCharacter c = new CustomerCharacter(images[r.nextInt(images.length)], 496, 272);
		travelToLine(c, getSelectCustomers(CustomerCharacter.STATE_LINE).size());
		customers.add(c);
	}
	
	public void dequeueCustomer() {
		ArrayList<CustomerCharacter> line = getSelectCustomers(CustomerCharacter.STATE_LINE);
		if(line.size() < 1) return;
		
		//Convert customer
		CustomerCharacter c = line.get(0);
		c.setState(CustomerCharacter.STATE_SITTING);
		
		//Leave for door
		c.travelTo(320, 32);
		
		//Re arrange line
		for(int i = 1; i < line.size(); i++) {
			CustomerCharacter cc = line.get(i);
			cc.stopTraveling();
			travelToLine(cc, i - 1);
		}
		
	}
	
	private void travelToLine(CustomerCharacter c, int index) {
		c.travelTo(496, (index * 20) + 144);
	}
}
