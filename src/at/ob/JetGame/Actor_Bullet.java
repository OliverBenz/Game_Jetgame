package at.ob.JetGame;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class Actor_Bullet {
	private float radius;
	private float x, y;
	private static final float SPEED = 2.5f;
	private Color color;
	private Shape rect;

	public Actor_Bullet(float x, float y) {
		this.radius = 4;
		this.color = new Color(255, 255, 255);
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void moveLeft(int millisSinceLastCall) {
		this.x -= millisSinceLastCall * SPEED;
	}

	public void moveRight(int millisSinceLastCall) {
		this.x += millisSinceLastCall * SPEED;
	}

	public void render(Graphics g) {
		g.setColor(this.color);
		this.rect = new Rectangle(this.x, this.y, this.radius * 2, this.radius * 2);
		g.fill(this.rect);

	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

}