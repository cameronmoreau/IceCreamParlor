package teamoortcloud.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CustomerCharacter {
	
	public static final int STATE_LINE = 0;
	public static final int STATE_SITTING = 1;
	
	int x, y;
	float vel = 2.5f;
	
	Image image;
	boolean traveling = false;
	int travelX, travelY;
	int state;
	
	public CustomerCharacter(Image image, int x, int y) {
		this.image = image;
		this.state = STATE_LINE;
		this.x = x;
		this.y = y;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(image, x, y, 16, 16);
	}
	
	public void update() {
		if(traveling) {
			//Stop if at destination
			if(x == travelX && y == travelY) traveling = false;
			
			//Travel to coords
			if(x != travelX) {
				if(x < travelX) x += vel;
				else x -= vel;
			}
			if(y != travelY) {
				if(y < travelY) y += vel;
				else y -= vel;
			}
		}
	}
	
	public void travelTo(int x, int y) {
		traveling = true;
		travelX = x;
		travelY = y;
	}
	
	public void stopTraveling() {
		traveling = false;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	

}
