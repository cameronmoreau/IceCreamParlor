package teamoortcloud.scenes;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamoortcloud.engine.App;
import teamoortcloud.entities.FallingIceCream;
 
public class AboutState extends AppState {
	
	
        NameHolder[] team = {
            new NameHolder("Adhish Deshpande", "file:res/images/face_adhish.png", "Music and Coding \n go hand in hand d*_*b "),
            new NameHolder("Cameron Moreau", "file:res/images/face_cameron.png", "\"Never EVER use Eclipse\""),
            new NameHolder("Robert Page", "file:res/images/face_page.png", "Once you go FX,\n you never go back")
        };

	public AboutState(StateManager sm) {
		super(sm);
                
                VBox root = new VBox();
                
                //Setup grid
                GridPane teamGrid = new GridPane();
                teamGrid.setAlignment(Pos.CENTER);
                teamGrid.setHgap(70);
                teamGrid.setVgap(30);
                teamGrid.setPadding(new Insets(0, 0, 0, 0));

                for(int i = 0; i < team.length; i++) {
                    
                    //Image
                    ImageView image = new ImageView();
                    image.setImage(new Image(team[i].fileUrl));
                    image.setSmooth(true);
                    image.setCache(true);
                    image.setFitWidth(100);
                    image.setFitHeight(100);
                    
                    //Name
                    Label name = new Label(team[i].name);
                    
                    //Text
                    Label desc = new Label(team[i].text);
                    
                    teamGrid.add(name, i, 1);
                    teamGrid.add(image, i, 2);
                    teamGrid.add(desc, i, 3);
                }
                
                root.getChildren().addAll(teamGrid);
                
                this.scene = new Scene(root);
                setupStyle();
	}
	
	//setup menu frame
	
	
	private void initCanvas(Group canvasPane) {
		//Setup canvas and graphics
		Canvas canvas = new Canvas(App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Load images
		Image iceCreamImage = new Image("file:res/images/icecream_cone_happy.png");
		Image bg = new Image("file:res/images/menu_bg.jpg");
		
		//Generate falling ice cream
		Random rand = new Random();
		int maxX = (int)canvas.getWidth() - (int)iceCreamImage.getWidth();
		int maxY = (int)canvas.getHeight() - (int)iceCreamImage.getHeight();
		int maxEntities = 15;
		
		FallingIceCream falling[] = new FallingIceCream[maxEntities];
		
		for(int i = 0; i < maxEntities; i++) {
			falling[i] =  new FallingIceCream(rand, maxX, maxY, (int)iceCreamImage.getHeight());
		}
		
		//Animation timer for falling effect
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				gc.drawImage(bg, 0, 0, canvas.getWidth(), canvas.getHeight());
				
				for(int i = 0; i < maxEntities; i++) {
					FallingIceCream f = falling[i];
					f.update();
					f.draw(gc, iceCreamImage);
				}
			}
			
			
		}.start();
		
		canvasPane.getChildren().add(canvas);
	}

    private class NameHolder {
        String name, fileUrl, text;
        
        public NameHolder(String name, String fileUrl, String text) {
            this.name = name;
            this.fileUrl = fileUrl;
            this.text = text;
        }
    }
}
