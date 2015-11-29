package teamoortcloud.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import democredit.PadDraw;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teamoortcloud.engine.App;
import teamoortcloud.engine.DataLoader;
import teamoortcloud.engine.GameShop;
import teamoortcloud.icecream.IceCream;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Worker;

public class CreditState extends AppState implements ActionListener {

	private double price;
	public CreditState(StateManager sm) {
		super(sm);
	}
	
	public CreditState(StateManager sm, double price)
	{
		super(sm);
		this.price=price;
		JFrame signature = new JFrame();
		signature.setTitle("Please sign below");
		signature.setSize(1000, 700);
		signature.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel pnlButtons = new JPanel();
		
		Container content = signature.getContentPane();
		//Creates a new container
		content.setLayout(new BorderLayout());
		//sets the layout
		
		final PadDraw drawPad = new PadDraw();
		//creates a new padDraw, which is pretty much the paint program
		
		content.add(drawPad, BorderLayout.CENTER);
		
		JButton btnDone = new JButton("Done");
		JButton btnClear = new JButton("Clear");
		JButton btnCancel = new JButton("Cancel");
		
		btnDone.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("Hello!!!");
	        }
	    });
		btnClear.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            drawPad.clear();
	        }
	    });
		btnCancel.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            signature.dispose();
	        }
	    });
		
		pnlButtons.add(btnDone);
		pnlButtons.add(btnClear);
		pnlButtons.add(btnCancel);
		
		content.add(pnlButtons, BorderLayout.SOUTH);
		
		
		
	}
	
	class PadDraw extends JComponent{
		Image image;
		//this is gonna be your image that you draw on
		Graphics2D graphics2D;
		//this is what we'll be using to draw on
		int currentX, currentY, oldX, oldY;
		//these are gonna hold our mouse coordinates

		//Now for the constructors
		public PadDraw(){
			setDoubleBuffered(false);
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					oldX = (int) e.getX();
					oldY = (int) e.getY();
				}
			});
			//if the mouse is pressed it sets the oldX & oldY
			//coordinates as the mouses x & y coordinates
			addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseDragged(MouseEvent e){
					currentX = (int) e.getX();
					currentY = (int) e.getY();
					if(graphics2D != null)
					graphics2D.drawLine(oldX, oldY, currentX, currentY);
					repaint();
					oldX = currentX;
					oldY = currentY;
				}

			});

		}

		public void paintComponent(Graphics g){
			if(image == null){
				image = createImage(getSize().width, getSize().height);
				graphics2D = (Graphics2D)image.getGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				clear();

			}
			g.drawImage(image, 0, 0, null);
		}
	

		public void clear(){
			graphics2D.setPaint(Color.WHITE);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			graphics2D.setPaint(Color.black);
			repaint();
		}
		//this is the clear
	
}
}