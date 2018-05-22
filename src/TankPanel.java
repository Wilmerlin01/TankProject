import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

public class TankPanel extends JPanel {


	boolean inMenu = true;

	int level = -1; // initialize at 1, menu ending changes to 0
	boolean level1FirstTime = true;

	public static int panelWidth = 1400;
	public static int panelHeight = 800;

	public static int numWallsAcross = 28;
	public static int numWallsDown = 16;

	public static int wallWidth =  panelWidth/numWallsAcross;
	public static int wallHeight = panelHeight/numWallsDown;

	boolean rightPressed, leftPressed, upPressed, downPressed;

	ArrayList<Arena> arenaList = new ArrayList<Arena>();
	Menu theMenu;
	Arena level1Arena;
	Arena level2Arena;
	Arena level3Arena;

	ImageLibrary imageLibrary = new ImageLibrary();

	int crosshairX;
	int crosshairY;

	//	private BufferedImage indestructableWall;


	Timer timer = new Timer(1,null);



	public static void main(String[] args) {


		
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Tank Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TankPanel tp = new TankPanel();
		frame.add(tp);
		tp.setPreferredSize(new Dimension(panelWidth,panelHeight));
		frame.pack();
		frame.setVisible(true);
		tp.setUpKeyMappings();
		tp.setUpMouseListeners();
		tp.startGame();
		frame.setResizable(false);

		//create cursor
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);


	}
	private void setUpKeyMappings() {

		this.getInputMap().put(KeyStroke.getKeyStroke("A"),"left");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"left");
		this.getActionMap().put("left",new AbstractAction(){


			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("Hit left arrow!!");
				leftPressed = true;

			}
		});

		
		
		this.getInputMap().put(KeyStroke.getKeyStroke("released A"),"releasedLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"),"releasedLeft");
		this.getActionMap().put("releasedLeft",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("released left arrow!!");
				leftPressed = false;

			}
		});


		this.getInputMap().put(KeyStroke.getKeyStroke("D"),"right");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"right");
		this.getActionMap().put("right",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("Hit right arrow!!");
				rightPressed = true;

			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("released D"),"releasedRight");
		this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"),"releasedRight");
		this.getActionMap().put("releasedRight",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("released right arrow!!");
				rightPressed = false;

			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("W"),"up");
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"),"up");
		this.getActionMap().put("up",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("Hit up arrow!!");
				upPressed = true;


			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("released W"),"releasedUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("released UP"),"releasedUp");
		this.getActionMap().put("releasedUp",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("released up arrow!!");
				upPressed = false;


			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("S"),"down");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"down");
		this.getActionMap().put("down",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("Hit down arrow!!");
				downPressed = true;
			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("released S"),"releasedDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"),"releasedDown");
		this.getActionMap().put("releasedDown",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// what do you want to do when the left arrow is pressed?
				System.out.println("released down arrow!!");
				downPressed = false;
			}
		});


		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"spaceBar");
		this.getActionMap().put("spaceBar",new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("pressed spacebar!!");
				
				if(!inMenu){
					arenaList.get(level).playerTank.fire();
				}
			}
		});
		
		
		this.requestFocusInWindow();

	}

	private void setUpMouseListeners() {
		{
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println("You just clicked!! "+arg0);
					if(inMenu)
					{
						if((arg0.getX() > 350 && arg0.getX() < 1050) 
								&&
								(arg0.getY() > 500 && arg0.getY() < 700)
								)
						{
							inMenu = false;
							level = 2;
						}
					}
					//TODO if() for levels to create projectile
				
				
				}

				@Override
				public void mousePressed(MouseEvent e){

					if(!inMenu){
						arenaList.get(level).playerTank.fire();
					}

				}

				@Override
				public void mouseReleased(MouseEvent e){
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e){
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e){
					// TODO Auto-generated method stub

				}

			});
		}

		{
			this.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e)
				{
					// TODO Nothing

				}

				@Override
				public void mouseMoved(MouseEvent e){
					// send to crosshair
					crosshairX = e.getX();
					crosshairY = e.getY();
//					int Tx = arenaList.get(level).playerTankLocX()+25;
//					int Ty = arenaList.get(level).playerTankLocY()+25;
//					arenaList.get(level).playerTank.setTurretAngle(Math.atan2(crosshairY-Ty, crosshairX-Tx));

					//	double Xd =(crosshairX-level1Arena.playerTankLocX()+25);
					//	double Yd =(crosshairY-level1Arena.playerTankLocX()+25);
					//	double radAngle = Math.atan(Yd/Xd);


				}

			});
		}


	}

	private void startGame() {
		timer.setDelay(1);
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tick();
			}

		});
		timer.start();





		//		try
		//		{
		//			indestructableWall = ImageIO.read(new File("images/indestructable2small.png"));	
		//			
		//
		//		} catch (IOException e)                      
		//		{
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		

	}
	protected void tick() {
		//System.out.println("timer has gone off");
		repaint();
	}
	public void paintComponent(Graphics g) {
		//	long start = System.currentTimeMillis();


		if(!level1FirstTime){
			arenaList.get(level).setInputMoveArr(getInputMoveArr());
		}
		if(level1FirstTime){
			theMenu = new Menu();
		}

		if (inMenu){
			theMenu.draw(g, imageLibrary);
		}
		else 
		{
			if(level1FirstTime){
				
				level1Arena = new Arena(1, numWallsAcross, numWallsDown);
				level2Arena = new Arena(2, numWallsAcross, numWallsDown);
				level3Arena = new Arena(3, numWallsAcross, numWallsDown);
				
				arenaList.add(level1Arena);
				arenaList.add(level2Arena);
				arenaList.add(level3Arena);
				
				level1FirstTime = false;
			}
			
			Arena currentArena = arenaList.get(level);
			
			if(currentArena.advanceLevel){
				level++;
			}
			
			currentArena.moveTanks();
			
			int playerTankX = currentArena.playerTank.getX();
			int playerTankY = currentArena.playerTank.getY();
			for(Tank t: currentArena.tankList){
				if (t.getClass() == AITank.class) {
					t.setTurretAngleByTarget(playerTankX+25, playerTankY+25);
				}
			}
			currentArena.playerTank.setTurretAngleByTarget(crosshairX, crosshairY);
			
			currentArena.draw(g, imageLibrary);
		
			//	g.setColor(Color.WHITE);		
			//g.drawLine(arenaList.get(level).playerTankLocX()+25, arenaList.get(level).playerTankLocY()+25, crosshairX, crosshairY);
			//g.drawLine(level1Arena.playerTankLocX(), level1Arena.playerTankLocY(), crosshairX, crosshairY);
			
		}

		g.drawImage(imageLibrary.crosshair, crosshairX-10, crosshairY-10, null);
		


	}


	public int[] getInputMoveArr(){
		int[] XandY = new int[2];
		XandY[0] = 0;
		XandY[1] = 0;

		if(rightPressed){
			XandY[0]+=1;
		}
		if(leftPressed){
			XandY[0]-=1;
		}
		if(upPressed){
			XandY[1]+=1;
		}
		if(downPressed){
			XandY[1]-=1;
		}

		return XandY;
	}






}
