package droidsurvival;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Character {
	// In Java, Class Variables should be private so that only its methods can
	// change them.
	private int centerX = 400;
	private int centerY = 240;
	private int pointX, pointY;
	private boolean left = false, right = false, up = false, down = false;
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);

	public double angle = 0;

	private int speedX = 0;
	private int speedY = 0;

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update(int pointX, int pointY) {

		this.pointX = pointX;
		this.pointY = pointY;
		rect.setRect(centerX+10, centerY+10, 20, 20);
		angle = Angle.getAngle(centerX, centerY, pointX, pointY);

		if ((centerX += speedX) > 760)
			centerX = 760;
		if ((centerY += speedY) > 440)
			centerY = 440;
		if (centerY < 0)
			centerY = 0;
		if (centerX < 0)
			centerX = 0;

	}

	public double getAngle() {

		return angle;

	}

	public void shoot() {
		Projectile p = new Projectile(centerX + 20, centerY + 20, pointX,
				pointY);
		projectiles.add(p);
	}

	public void moveRight() {
		if (left == true)
			speedX = 0;
		else
			speedX = 5;

		right = true;
	}

	public void moveLeft() {
		if (right == true)
			speedX = 0;
		else
			speedX = -5;

		left = true;
	}

	public int getPointX() {
		return pointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void moveUp() {
		if (down == true)
			speedY = 0;
		else
			speedY = -5;

		up = true;
	}

	public void moveDown() {
		if (up == true)
			speedY = 0;
		else
			speedY = 5;

		down = true;
	}

	public void stopUp() {
		if (down == true)
			speedY = 5;
		else
			speedY = 0;

		up = false;
	}

	public void stopDown() {
		if (up == true)
			speedY = -5;
		else
			speedY = 0;

		down = false;
	}

	public void stopLeft() {
		if (right == true)
			speedX = 5;
		else
			speedX = 0;

		left = false;
	}

	public void stopRight() {
		if (left == true)
			speedX = -5;
		else
			speedX = 0;

		right = false;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

}