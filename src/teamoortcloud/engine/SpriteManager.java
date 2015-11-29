package teamoortcloud.engine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteManager {
	
	BufferedImage spriteSheet;
	
	public SpriteManager(String fileUrl) {
		try {
			spriteSheet = ImageIO.read(new File("res/images/" + fileUrl + ".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y) {
		return spriteSheet.getSubimage(x * 16, y * 16, 16, 16);
	}
}
