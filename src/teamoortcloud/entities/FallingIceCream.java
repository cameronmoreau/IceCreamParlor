package teamoortcloud.entities;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FallingIceCream {
	
	int x, y;
	float scale, velocity;
	
	int maxX, maxY;
	int imageHeight;
	Random rand;
	
	public FallingIceCream(Random rand, int maxX, int maxY, int imageHeight) {
		this.rand = rand;
		this.maxX = maxX;
		this.maxY = maxY;
		this.imageHeight = imageHeight;
		
		init();
	}
	
	private void init() {
		//int randomNum = rand.nextInt((max - min) + 1) + min;
		
		x = rand.nextInt(maxX);
		y = rand.nextInt(maxY);
		velocity = rand.nextInt(5) + 1;
		scale = rand.nextInt((75 - 25) + 1) + 25;
		scale /= 100;
	}
	
	public void update() {
		y += velocity;
		
		if(y > maxY + imageHeight) {
			init();
			y = -20;
		}
	}
	
	public void draw(GraphicsContext gc, Image image) {
		gc.drawImage(image, x, y, image.getWidth() * scale, image.getHeight() * scale);
	}
	
}
