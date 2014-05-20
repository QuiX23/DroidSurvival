package droidsurvival;

import java.awt.Rectangle;

public class Projectile {

	private int x, y, speedX, speedY, speed;
	double angle;
	private boolean visible;
	private Rectangle r;

	public Projectile(int startX, int startY, int pointX, int pointY) {
		// TODO Auto-generated constructor stub

		r = new Rectangle(0, 0, 0, 0);
		x = startX;
		y = startY;
		speed = 15;
		angle = Angle.getAngle(x, y, pointX, pointY);

		double a = (double) speed * Math.sin(Math.toRadians(angle));
		speedY = (int) a;
		speedX = (int) Math.sqrt(Math.pow((double) speed, 2) - Math.pow(a, 2));
		System.out.println(angle);

		if (angle > 90)
			speedX = -speedX;
		speedY = -speedY;

		visible = true;
	}

	public void update() {
		x += speedX;
		if (x > 800 || x < 0) {
			visible = false;
		}
		y += speedY;
		if (y > 480 || y < 0) {
			visible = false;
		}

		r.setBounds(x, y, 10, 5);

		if (visible) {
			checkCollision();
		}

	}


	private void checkCollision() {
		
		for (int i = 0; i < StartingClass.droids.size(); i++) {
			if (r.intersects(StartingClass.droids.get(i).r)) {
				visible = false;
				StartingClass.droids.get(i).health -= 1;

			}
		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}