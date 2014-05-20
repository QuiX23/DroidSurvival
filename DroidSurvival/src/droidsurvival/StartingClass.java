package droidsurvival;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import drodisurvival.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener,
		MouseListener, MouseMotionListener {

	enum GameState {
		Running, Dead, Menu
	}

	static GameState state = GameState.Menu;
	public static MenuScreen menu = new MenuScreen();
	Random generator = new Random();
	public static Character character;
	public static ArrayList<Droid> droids ;
	public static ArrayList<Corpses> corpses ;
	public static Bonus bonus;
	private Image image, background, playerStand, playerLeft, playerRight, dd,
			droidDead, droidLeft, droidRight, currentSprite, bon,menuBack;
	public static ArrayList<Animation> danims ;
	private Animation anim, danim;
	private int frameCount;
	private Graphics second;
	private URL base;
	private int mouseX, mouseY;
	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);
	private Font fontMenu = new Font(null, Font.BOLD, 80);
	private Thread thread ;

	@Override
	public void init() {
		frameCount = 0;

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Zombie Survival");

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// test
		// Image Setups
		playerStand = getImage(base, "data/graczstoi.png");
		playerLeft = getImage(base, "data/graczlewa.png");
		playerRight = getImage(base, "data/graczprawa.png");

		anim = new Animation();
		anim.addFrame(playerStand, 50);
		anim.addFrame(playerLeft, 50);
		anim.addFrame(playerRight, 50);

		droidRight = getImage(base, "data/zombieprawa.png");
		droidDead = getImage(base, "data/deadzombie.png");
		droidLeft = getImage(base, "data/zombielewa.png");
		dd = getImage(base, "data/zombiestoi.png");

		danim = new Animation();
		danim.addFrame(dd, 100);
		danim.addFrame(droidRight, 100);
		danim.addFrame(dd, 100);
		danim.addFrame(droidLeft, 100);

		background = getImage(base, "data/background.png");
		menuBack = getImage(base, "data/menu.png");
		
		bon = getImage(base, "data/bonus.png");
		
		

	}

	@Override
	public void start() {
		droids = new ArrayList<Droid>();
		danims = new ArrayList<Animation>();
		corpses = new ArrayList<Corpses>();

		character = new Character();


		bonus = new Bonus(70, 70);

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		thread.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {
				// System.out.println(danims.get(1).size());

				frameCount = (frameCount + 1) % 240;
				newEnemy();

				character.update(mouseX, mouseY);

				currentSprite = anim.getImage();

				bonus.update();

				for (int i = 0; i < droids.size(); i++) {
					if (!droids.get(i).isState()) {
						danims.remove(i);
						corpses.add(new Corpses(droids.get(i).getCenterX(),
								droids.get(i).getCenterY(), droids.get(i)
										.getAngle() + 180));
						droids.remove(i);
					}
					droids.get(i).update(character.getCenterX(),
							character.getCenterY());

					danims.get(i).update(5);

				}

				ArrayList<Projectile> projectiles = character.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
					}
				}

				animate();
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void animate() {
		if (character.isDown() || character.isLeft() || character.isRight()
				|| character.isUp())
			anim.update(5);
		else
			currentSprite = playerStand;
		// danim.update(3);
	}

	public void newEnemy() {
		switch (frameCount) {
		case 0: {
			droids.add(new Droid(-40, -40));
			try {
				danims.add((Animation) danim.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 60: {
			droids.add(new Droid(-40, 640));
			try {
				danims.add((Animation) danim.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		case 120: {
			droids.add(new Droid(840, -40));
			try {
				danims.add((Animation) danim.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		case 180: {
			droids.add(new Droid(840, 640));
			try {
				danims.add((Animation) danim.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
		}

	}

	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.drawImage(background, 0, 0, this);

			for (int i = 0; i < corpses.size(); i++) {
				rotateImage(droidDead, corpses.get(i).getX(), corpses.get(i)
						.getY(), corpses.get(i).getAngle(), g);
				System.out.println(corpses.get(i).getAngle());
			}

			rotateImage(currentSprite, character.getCenterX(),
					character.getCenterY(), character.getAngle(), g);

			g.drawImage(bon, bonus.getCenterX(), bonus.getCenterY(), this);

			ArrayList<Projectile> projectiles = character.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}

			for (int i = 0; i < droids.size(); i++) {
				rotateImage(danims.get(i).getImage(), droids.get(i)
						.getCenterX(), droids.get(i).getCenterY(), droids
						.get(i).getAngle(), g);
			}

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		} else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
			g.drawString("Dead", 360, 200);
			g.drawString("Enter- New Game", 300, 240);
			g.drawString("Esc- End", 320, 280);
			this.stop();

		}
		else if (state == GameState.Menu){
			g.drawImage(menuBack, 0, 0, this);
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);
		g.setColor(Color.BLACK);
		g.drawImage(image, 0, 0, this);

	}

	private void rotateImage(Image image, int drawLocationX, int drawLocationY,
			double angle, Graphics g) {

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bimage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		// Rotation information

		double rotationRequired = Math.toRadians(90 - angle);
		double locationX = bimage.getWidth() / 2;
		double locationY = bimage.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(op.filter(bimage, null), drawLocationX, drawLocationY,
				null);

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			// System.out.println("Move up");
			character.moveUp();
			break;

		case KeyEvent.VK_DOWN:
			// System.out.println("Move down");
			character.moveDown();
			break;

		case KeyEvent.VK_LEFT:
			// System.out.println("Move left");
			character.moveLeft();
			break;

		case KeyEvent.VK_RIGHT:
			// System.out.println("Move right");
			character.moveRight();
			break;
		case KeyEvent.VK_W:
			// System.out.println("Move right");
			character.moveUp();
			break;
		case KeyEvent.VK_S:
			// System.out.println("Move right");
			character.moveDown();
			break;
		case KeyEvent.VK_A:
			// System.out.println("Move right");
			character.moveLeft();
			break;
		case KeyEvent.VK_D:
			// System.out.println("Move right");
			character.moveRight();
			break;
		case KeyEvent.VK_ENTER:
			if (state == GameState.Dead) {
				state = GameState.Running;
				this.start();
			}
			if (state == GameState.Menu ) {
				this.start();
				state = GameState.Running;
			}
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			character.stopUp();
			break;

		case KeyEvent.VK_DOWN:
			character.stopDown();
			break;

		case KeyEvent.VK_LEFT:
			character.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			character.stopRight();
			break;
		case KeyEvent.VK_W:
			// System.out.println("stop right");
			character.stopUp();
			break;
		case KeyEvent.VK_S:
			// System.out.println("stop right");
			character.stopDown();
			break;
		case KeyEvent.VK_A:
			// System.out.println("stop right");
			character.stopLeft();
			break;
		case KeyEvent.VK_D:
			// System.out.println("stop right");
			character.stopRight();

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (state == GameState.Running)character.shoot();
		else if (state == GameState.Menu && menu.onStart(e.getX(), e.getY())) {
			this.start();
			state = GameState.Running;
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public class Corpses {

		private int x, y;
		private double angle;

		Corpses(int x, int y, double angle) {
			this.x = x;
			this.y = y;
			this.angle = angle;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public double getAngle() {
			return angle;
		}

	}

}
