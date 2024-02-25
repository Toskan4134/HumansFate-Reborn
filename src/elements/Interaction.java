package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import game.Params;
import screens.GameScreen;

public class Interaction extends Element {

	public String type;
	public GameScreen gameScreen;
	private float width;
	private float height;
	public boolean isEnabled;

	public Interaction(float x, float y, Stage s, float w, float h, String type, boolean isEnabled,
			GameScreen gameScreen) {
		super(x, y, s);
		this.width = w;
		this.height = h;
		this.isEnabled = isEnabled;
		this.type = type;
		float[] vertices = { 0, 0, w, 0, w, h, 0, h };
		colision = new Polygon(vertices);
		this.setSize(w, h);
		this.gameScreen = gameScreen;

	}

	public Interaction(float x, float y, Stage s, String type, boolean isEnabled, GameScreen gameScreen) {
		super(x, y, s);
		this.isEnabled = isEnabled;
		this.type = type;
		this.gameScreen = gameScreen;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (!isEnabled)
			return;
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
//				player.isWorking = true;
				executeAction(delta);
			}
		}

		this.applyPhysics(delta);

	}

	private void executeAction(float delta) {
		switch (this.type) {
		case "Exit":

			gameScreen.player.setY(1136);
			gameScreen.player.setX(1134);
			gameScreen.player.isInteracting = true;
			break;
		case "Entrance":
			if (gameScreen.player.isInteracting) {
				gameScreen.player.isInteracting = false;
				break;
			}
			gameScreen.player.setY(976);
			gameScreen.player.setX(1120);
			break;
		case "Bed":
			playerLockInMiddle(true);
			Params.secsPerRound = 0f;
			Params.sleep = MathUtils.clamp(Params.sleep + Params.SLEEP_LOSS * 2, 0, 12 + Params.SLEEP_LOSS);
			break;
		}
	}

	public void playerLockInMiddle(boolean canMove) {
		gameScreen.player.canMove = canMove;
		gameScreen.player.setVelocity(new Vector2(0, 0));
		gameScreen.player.setX(this.getX() + this.width / 2 - gameScreen.player.getWidth() / 2);
		gameScreen.player.setY(this.getY() + this.height / 2 - gameScreen.player.getHeight() / 2);
	}

}
