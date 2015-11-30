package teamoortcloud.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CheckoutCreditState extends AppState {

    public CheckoutCreditState(StateManager sm) {
        super(sm);

        VBox rootPane = new VBox();
        rootPane.getChildren().addAll(new Label("Checkout with Credit"));

        this.scene = new Scene(rootPane);
    }
}