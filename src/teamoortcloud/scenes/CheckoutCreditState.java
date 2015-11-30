package teamoortcloud.scenes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import teamoortcloud.other.Order;
import teamoortcloud.other.Shop;
import teamoortcloud.people.Cashier;

public class CheckoutCreditState extends AppState {

    Order order;
    Shop shop;

    public CheckoutCreditState(StateManager sm, Order order, Shop shop) {
        super(sm);

        this.shop = shop;
        this.order = order;

        VBox rootPane = new VBox();
        rootPane.setPadding(new Insets(5));

        //Stack pane
        StackPane stackPane = new StackPane();

        ImageView iphoneImage = new ImageView();
        iphoneImage.setImage(new Image("file:res/images/iphone_signature.png"));
        iphoneImage.setSmooth(true);
        iphoneImage.setCache(true);

        Canvas drawCanvas = new Canvas();
        GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        setupCanvas(drawCanvas, gc);
        initDraw(gc);

        stackPane.getChildren().addAll(iphoneImage, drawCanvas);
        stackPane.setAlignment(Pos.TOP_LEFT);
        StackPane.setMargin(drawCanvas, new Insets(18, 0, 0, 76));

        //Buttons
        HBox buttonPane = new HBox();
        buttonPane.setSpacing(15);

        Button btnClear = new Button("Clear Signature");
        Button btnSubmit = new Button("Submit");

        btnClear.getStyleClass().add("menu-button");
        btnSubmit.getStyleClass().add("menu-button");

        btnClear.setOnAction(e -> initDraw(gc));
        btnSubmit.setOnAction(e -> submit());

        buttonPane.getChildren().addAll(btnClear, btnSubmit);

        rootPane.getChildren().addAll(stackPane, buttonPane);

        this.scene = new Scene(rootPane);
        setupStyle();
    }

    private void setupCanvas(Canvas canvas, GraphicsContext gc) {
        canvas.setHeight(140);
        canvas.setWidth(275);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
    }

    private void submit() {
        shop.getRegister().addCredit(order.getTotal());
        order.getWorker().addMoneyTaken(order.getTotal());

        if(order.getWorker().getClass() == Cashier.class) {
            ((Cashier)order.getWorker()).updatePatience(-1);
        }

        order.setPaid(true);
        shop.addOrder(order);
        shop.setDataChanged();

        sm.getStage().close();
    }

    private void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(5);

        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        gc.strokeRect(0, 0, canvasWidth, canvasHeight);

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

    }
}