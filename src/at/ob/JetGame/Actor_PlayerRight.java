package at.ob.JetGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Actor_PlayerRight {
	private int health;
	private float x, y;
	private static final float SPEED = 0.8f;
	private int height, width;
	private Image player;

	public Actor_PlayerRight(float x, float y) throws SlickException {
		super();
		this.x = x;
		this.y = y;
		this.health = 5;
		this.height = 64;
		this.width = 122;
		this.player = new Image("Media/Player/c63c74041844e9b42936dad14d1c41b4.png");
	}

	public void render(Graphics g) {
		g.drawImage(this.player, this.x + 122, this.y, this.x, this.y + 64, 0, 0, 1024, 457);
	}

	public void moveUP(int millisSinceLastCall) {
		if (this.y >= 0) {
			this.y -= millisSinceLastCall * SPEED;
		}
	}

	public void moveDown(int millisSinceLastCall) {
		if (this.y < 1080 - this.height) {
			this.y += millisSinceLastCall * SPEED;
		}
	}

	public void moveLeft(int millisSinceLastCall) {
		if (this.x > 1920 / 2 + 10) {
			this.x -= millisSinceLastCall * SPEED;
		}
	}

	public void moveRight(int millisSinceLastCall) {
		if (this.x < 1920 - this.width) {
			this.x += millisSinceLastCall * SPEED;
		}
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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
