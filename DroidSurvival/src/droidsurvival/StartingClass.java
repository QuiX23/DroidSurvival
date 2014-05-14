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

public class StartingClass extends Applet implements Runnable, KeyListener,
		MouseListener, MouseMotionListener {

	enum GameState {
		Running, Dead
	}

	static GameState state = GameState.Running;

	public static Character character;
	public static Droid droid;
	public static Bonus bonus;
	private Image image, background, player, dd, bon;
	private Graphics second;
	private URL base;
	private int mouseX, mouseY;
	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Droid Survival");

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		player = getImage(base, "data/character.png");
		dd = getImage(base, "data/enemy.png");
		background = getImage(base, "data/background.png");
		bon = getImage(base, "data/bonus.png");

	}

	@Override
	public void start() {
		character = new Character();
		droid = new Droid(20, 20);
		bonus = new Bonus(70, 70);

		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {
				character.update(mouseX, mouseY);
				droid.update(character.getCenterX(), character.getCenterY());
				bonus.update();

				ArrayList projectiles = character.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
					}
				}

				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.drawImage(background, 0, 0, this);

			rotateImage(player, character.getCenterX(), character.getCenterY(),
					character.getAngle(), g);
			rotateImage(dd, droid.getCenterX(), droid.getCenterY(),
					droid.getAngle(), g);

			g.drawImage(bon, bonus.getCenterX(), bonus.getCenterY(), this);

			ArrayList projectiles = character.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		}
		else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);


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
		character.shoot();

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

}