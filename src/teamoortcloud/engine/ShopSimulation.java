package teamoortcloud.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import teamoortcloud.entities.CustomerManager;
import teamoortcloud.entities.FallingIceCream;
import teamoortcloud.other.Shop;

import java.util.Random;

public class ShopSimulation {
	
	public static final int TILE_WIDTH = 45;
	public static final int TILE_HEIGHT = 18;
	public static final int TILE_SIZE = 16;
	
	int tiles[][];

    Shop shop;
	CustomerManager customerManager;
	Image bg, poopImage;

    boolean crappyHour = false;

    FallingIceCream poop[];
	
	public ShopSimulation(Shop shop) {
        this.shop = shop;
		tiles = new int[TILE_HEIGHT][TILE_WIDTH];
		
		tiles[3][4] = 1;
		tiles[3][5] = 2;
		tiles[3][6] = 2;
		tiles[3][7] = 2;
		
		customerManager = new CustomerManager();
		bg = new Image("file:res/images/bg_shop.png");
		poopImage = new Image("file:res/images/poop.png");

		//customer.travelTo(150, 300);
		
//		try {
//			Scanner fileReader = new Scanner(new FileReader("res/maps/map_detail.txt"));
//			String line;
//			String tiles[];
//			
//			while(fileReader.hasNextLine()) {
//				line = fileReader.nextLine();
//				tiles = line.split(",");
//				
//			}
//		} catch(Exception e) {
//			
//		}
	}
	
	public void update() {
		customerManager.update();
	}
	
	public void addCustomer() {
		customerManager.queueNewCustomer();
	}
	
	public void removeCustomer() {
		customerManager.dequeueCustomer();
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(bg, 0, 0, TILE_WIDTH * TILE_SIZE, TILE_HEIGHT * TILE_SIZE);
		customerManager.draw(gc);

        if(crappyHour) {
            for(int i = 0; i < 15; i++) {
                FallingIceCream f = poop[i];
                f.update();
                f.draw(gc, poopImage);
            }
        }

		for(int y = 0; y < TILE_HEIGHT; y++) {
			for(int x = 0; x < TILE_WIDTH; x++) {
				Color c = Color.BLACK;

				switch(tiles[y][x]) {
				case 0:
					c = Color.WHITE;
					break;

				case 1:
					c = Color.AQUA;
					break;

				case 2:
					c = Color.RED;
					break;

				//Or -1
				default:
					c = Color.BLACK;
					break;
				}

				//gc.setFill(c);
				//gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
	}

    public void startCrappyHour() {
        Random rand = new Random();
        int maxX = TILE_WIDTH * TILE_SIZE - (int)poopImage.getWidth();
        int maxY = TILE_HEIGHT * TILE_SIZE - (int)poopImage.getHeight();
        int maxEntities = 15;

        poop = new FallingIceCream[maxEntities];

        for(int i = 0; i < maxEntities; i++) {
            poop[i] =  new FallingIceCream(rand, maxX, maxY, (int)poopImage.getHeight());
        }

        crappyHour = true;
    }
}
