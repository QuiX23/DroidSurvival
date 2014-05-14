package droidsurvival;

import java.awt.Rectangle;

import droidsurvival.StartingClass.GameState;

public class Enemy {

	private int  power, speedX=0, speedY=0,speed=3, centerX,
			centerY;
	private double angle;
	public int health = 4;
	
	public Rectangle r = new Rectangle(0,0,0,0);
	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public void update(int pointX, int pointY) {
		
		angle=Angle.getAngle(centerX, centerY, pointX, pointY);
		
		double a=(double)speed*Math.sin(Math.toRadians(angle));
		speedY=(int)a;
		speedX=(int)Math.sqrt(Math.pow((double)speed, 2)-Math.pow(a, 2));
		System.out.println(angle);		
		
			if(angle<90)centerX += speedX;
			else centerX -= speedX;
			centerY -= speedY;
			
			r.setBounds(centerX , centerY, 40, 40);
			

				checkCollision();

		
		
	}
	
	private void checkCollision() {
		if (r.intersects(Character.rect)){
			System.out.println("collision");
			StartingClass.state=GameState.Dead;
			
			}
		}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
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

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public double getAngle() {
		return angle;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void die() {
	}

	public void attack() {
	}

}
