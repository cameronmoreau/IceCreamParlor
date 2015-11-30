package teamoortcloud.engine;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Created by Cameron on 11/29/15.
 */
public class ShopLog extends Label {

    public ShopLog() {
        super();
        this.setTextFill(Color.web("#FFFFFF"));
    }

    public void addLog(String log) {
        this.setText(log + "\n" + this.getText());
    }
}
