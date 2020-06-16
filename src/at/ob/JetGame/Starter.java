package at.ob.JetGame;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class Starter extends BasicGame {

	private Image Background;
	private Image Health;
	private Image winLeft;
	private Image winRight;
	private Music backMusic;

	private Actor_PlayerLeft playerLeft;
	private Actor_PlayerRight playerRight;

	private List<Actor_Bullet> bulletLeft;
	private List<Actor_Bullet> bulletRight;

	int BulLeftCD = 0;
	int BulRightCD = 0;

	private int width;
	private int height;
	private int end;

	public Starter() {
		super("Jet Fighter");
	}

	public void restart() throws SlickException {
		playerLeft.setHealth(5);
		playerRight.setHealth(5);
		playerLeft.setX(0);
		playerLeft.setY(this.height / 2);
		playerRight.setX(this.width - 128);
		playerRight.setY(this.height / 2);
		winRight.destroy();
		winLeft.destroy();
		this.winLeft = new Image("Media/Background/win_left.png");
		this.winRight = new Image("Media/Background/win_right.png");
		backMusic.stop();
		backMusic.play();

		this.end = 0;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		g.drawImage(this.Background, 0, 0);

		playerRight.render(g);
		playerLeft.render(g);

		for (Actor_Bullet b : bulletLeft) {
			b.render(g);
		}
		for (Actor_Bullet b : bulletRight) {
			b.render(g);
		}

		showLivesPlayerLeft(g, playerLeft.getHealth());
		showLivesPlayerRight(g, playerRight.getHealth());

		// Death Screen
		if (playerLeft.getHealth() <= 0 || playerRight.getHealth() <= 0) {
			if (playerLeft.getHealth() <= 0 && playerRight.getHealth() <= 0) {
				g.drawImage(this.Health, playerRight.getX() + 100, playerRight.getY() - 10);
				g.drawImage(this.winRight, this.width / 2, 0);
				for (int i = 0; i < bulletLeft.size(); i++) {
					bulletLeft.remove(i);
				}
				for (int i = 0; i < bulletRight.size(); i++) {
					bulletRight.remove(i);
				}
			}
			if (playerLeft.getHealth() <= 0 && playerRight.getHealth() != 0) {
				g.drawImage(this.winRight, this.width / 2, 0);
				for (int i = 0; i < bulletLeft.size(); i++) {
					bulletLeft.remove(i);
				}
			}
			if (playerRight.getHealth() <= 0 && playerLeft.getHealth() != 0) {
				g.drawImage(this.winLeft, 0, 0, 0, 0, 960, 540);
				for (int i = 0; i < bulletRight.size(); i++) {
					bulletRight.remove(i);
				}
			}
			this.end = 1;
		}
	}

	private void showLivesPlayerLeft(Graphics g, int amount) {
		for (int i = 1; i <= amount; i++) {
			g.drawImage(this.Health, playerLeft.getX() + (20 * i), playerLeft.getY() - 10);
		}
	}

	private void showLivesPlayerRight(Graphics g, int amount) {
		for (int i = 1; i <= amount; i++) {
			g.drawImage(this.Health, playerRight.getX() + 120 - (20 * i), playerRight.getY() - 10);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		this.Background = new Image("Media/Background/background.png");
		this.Health = new Image("Media/Player/player_heart.png");
		this.winLeft = new Image("Media/Background/win_left.png");
		this.winRight = new Image("Media/Background/win_right.png");

		this.backMusic = new Music("Media/Music/Ocean_Man_Lyrics.ogg");
		backMusic.play();

		this.width = gc.getWidth();
		this.height = gc.getHeight();

		this.playerLeft = new Actor_PlayerLeft(0, this.height / 2);
		this.playerRight = new Actor_PlayerRight(this.width - 128, this.height / 2);

		this.bulletLeft = new ArrayList<>();
		this.bulletRight = new ArrayList<>();

		this.end = 0;
	}

	@Override
	public void update(GameContainer gc, int millisSinceLastCall) throws SlickException {
		gc.setVSync(true);
		actionPlayerLeft(gc, millisSinceLastCall);
		actionPlayerRight(gc, millisSinceLastCall);

		for (Actor_Bullet b : bulletLeft) {
			b.moveRight(millisSinceLastCall);
		}
		for (Actor_Bullet b : bulletRight) {
			b.moveLeft(millisSinceLastCall);
		}

		if (BulLeftCD > 0) {
			BulLeftCD -= 1;
		}
		if (BulRightCD > 0) {
			BulRightCD -= 1;
		}

		for (int i = 0; i < this.bulletLeft.size(); i++) {
			if ((bulletLeft.get(i).getX() >= playerRight.getX() && bulletLeft.get(i).getX() < playerRight.getX() + 122)
					&& (bulletLeft.get(i).getY() > playerRight.getY()
							&& bulletLeft.get(i).getY() < playerRight.getY() + 64)) {
				playerRight.setHealth(playerRight.getHealth() - 1);
				bulletLeft.remove(i);
			}
		}
		for (int i = 0; i < this.bulletRight.size(); i++) {
			if ((bulletRight.get(i).getX() >= playerLeft.getX() && bulletRight.get(i).getX() < playerLeft.getX() + 122)
					&& (bulletRight.get(i).getY() > playerLeft.getY()
							&& bulletRight.get(i).getY() < playerLeft.getY() + 64)) {
				playerLeft.setHealth(playerLeft.getHealth() - 1);
				bulletRight.remove(i);
			}
		}

		// Performance
		for (int i = 0; i < bulletLeft.size(); i++) {
			if (bulletLeft.get(i).getX() <= 0) {
				bulletLeft.remove(i);
			}
		}
		for (int i = 0; i < bulletRight.size(); i++) {
			if (bulletRight.get(i).getX() > this.width) {
				bulletRight.remove(i);
			}
		}
	}

	public void actionPlayerLeft(GameContainer gc, int millisSinceLastCall) {
		gc.getInput();
		if (gc.getInput().isKeyDown(Input.KEY_W)) {
			playerLeft.moveUP(millisSinceLastCall);
		}

		if (gc.getInput().isKeyDown(Input.KEY_A)) {
			playerLeft.moveLeft(millisSinceLastCall);
		}
		if (gc.getInput().isKeyDown(Input.KEY_S)) {
			playerLeft.moveDown(millisSinceLastCall);
		}
		if (gc.getInput().isKeyDown(Input.KEY_D)) {
			playerLeft.moveRight(millisSinceLastCall);
		}

		if (gc.getInput().isKeyDown(Input.KEY_T)) {
			if (BulLeftCD == 0) {
				if (this.end != 1) {
					bulletPlayerLeft(gc);
				}
			}
		}
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
	}

	public void actionPlayerRight(GameContainer gc, int millisSinceLastCall) throws SlickException {
		gc.getInput();
		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			playerRight.moveUP(millisSinceLastCall);
		}

		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			playerRight.moveLeft(millisSinceLastCall);
		}
		if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			playerRight.moveDown(millisSinceLastCall);
		}
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			playerRight.moveRight(millisSinceLastCall);
		}
		if (gc.getInput().isKeyDown(Input.KEY_NUMPAD0)) {
			if (BulRightCD == 0) {
				if (this.end != 1) {
					bulletPlayerRight(gc);
				}
			}
		}
		if (this.end == 1) {
			if (gc.getInput().isKeyDown(Input.KEY_0)) {
				restart();
			}
		}
	}

	public void bulletPlayerLeft(GameContainer gc) {
		this.bulletLeft.add(new Actor_Bullet(playerLeft.getX() + playerLeft.getWidth(),
				playerLeft.getY() + playerLeft.getHeight() / 2));
		BulLeftCD = 20;
	}

	public void bulletPlayerRight(GameContainer gc) {
		this.bulletRight.add(new Actor_Bullet(playerRight.getX(), playerRight.getY() + playerRight.getHeight() / 2));
		BulRightCD = 20;
	}

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new Starter());
			container.setDisplayMode(1920, 1080, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}