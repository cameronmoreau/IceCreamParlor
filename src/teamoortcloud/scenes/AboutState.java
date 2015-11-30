package teamoortcloud.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
 
public class AboutState extends AppState {
	
	
    NameHolder[] team = {
        new NameHolder("Adhish Deshpande", "face_adhish.png", "Music and Coding \n go hand in hand d*_*b "),
        new NameHolder("Cameron Moreau", "face_cameron.png", "\"Never EVER use Eclipse\""),
        new NameHolder("Robert Page", "face_page.jpg", "Once you go FX,\n you never go back")
    };

	public AboutState(StateManager sm) {
		super(sm);
                
        VBox root = new VBox();
        root.setSpacing(15);
        root.setStyle("-fx-background: #FCF4CC");


        ImageView title = new ImageView();
        title.setImage(new Image("file:res/images/title.png"));
        title.setSmooth(true);
        title.setCache(true);

        //Setup grid
        GridPane teamGrid = new GridPane();
        teamGrid.setAlignment(Pos.CENTER);
        teamGrid.setHgap(70);
        teamGrid.setVgap(10);
        teamGrid.setPadding(new Insets(0, 0, 0, 0));

        for(int i = 0; i < team.length; i++) {

            //Image
            ImageView image = new ImageView();
            image.setImage(new Image("file:res/team/" + team[i].fileUrl));
            image.setSmooth(true);
            image.setCache(true);
            image.setFitWidth(100);
            image.setFitHeight(100);

            //Name
            Label name = new Label(team[i].name);
            name.setAlignment(Pos.CENTER);

            //Text
            Label desc = new Label(team[i].text);
            desc.setAlignment(Pos.CENTER);

            teamGrid.add(name, i, 1);
            teamGrid.add(image, i, 2);
            teamGrid.add(desc, i, 3);
        }

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> sm.setStage(StateManager.STATE_MENU));
        btnBack.getStyleClass().add("menu-button");
        btnBack.setMinWidth(300);

        root.getChildren().addAll(title, teamGrid, btnBack);
        root.setAlignment(Pos.CENTER);

        this.scene = new Scene(root);
        setupStyle();
	}
	
	//setup menu frame

    private class NameHolder {
        String name, fileUrl, text;
        
        public NameHolder(String name, String fileUrl, String text) {
            this.name = name;
            this.fileUrl = fileUrl;
            this.text = text;
        }
    }
}
