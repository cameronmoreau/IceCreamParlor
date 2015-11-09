package teamoortcloud.engine;

import java.io.FileReader;
import java.util.Scanner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameShop {
	
	public static final int TILE_WIDTH = 45;
	public static final int TILE_HEIGHT = 20;
	public static final int TILE_SIZE = 16;
	
	int tiles[][];
	
	public GameShop() {
		tiles = new int[TILE_HEIGHT][TILE_WIDTH];
		
		tiles[3][4] = 1;
		tiles[3][5] = 2;
		tiles[3][6] = 2;
		tiles[3][7] = 2;
		
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

	
	public void draw(GraphicsContext gc) {
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
				
				gc.setFill(c);
				gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
	}
}
