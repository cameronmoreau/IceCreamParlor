package teamoortcloud.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CustomerCharacter {
	
	int x, y;
	float vel = 2.5f;
	
	Image image;
	boolean traveling = false;
	int travelX, travelY;
	
	public CustomerCharacter(int x, int y) {
		image = new Image("file:res/images/character.png");
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

}
