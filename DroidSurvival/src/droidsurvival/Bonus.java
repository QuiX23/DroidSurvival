package droidsurvival;

import java.awt.Rectangle;
import java.util.Random;

public class Bonus {
	
	private int centerX = 0;
	private int centerY = 0;
	private int value;
	
	public static Rectangle r = new Rectangle(0, 0, 0, 0);
	
	public Bonus(int x,int y) {
		centerX=x;
		centerY=y;
		value=5;
		r = new Rectangle(x, y, 20, 20);
	}
	
	public void update() {
		checkCollision();
		r.setBounds(centerX, centerY, 20, 20);
	}
	
	private void checkCollision() {
		if(r.intersects(StartingClass.character.rect)){
			StartingClass.score += value;
			
			Random generator = new Random();
			centerX=generator.nextInt(780);
			centerY=generator.nextInt(400);
			
		}
		

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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static Rectangle getR() {
		return r;
	}

	public static void setR(Rectangle r) {
		Bonus.r = r;
	}

}
