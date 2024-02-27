package elements.Minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Minigame;
import game.Params;
import screens.GameScreen;
import screens.LoadScreen;

public class GymGame extends Minigame {

	private int lastKey;

	public GymGame(float x, float y, Stage s, GameScreen gameScreen, int maxPoints) {
		super(x, y, s, gameScreen, maxPoints);
		// TODO Auto-generated constructor stub
		this.lastKey = -1;
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		gameScreen.key.setEnabled(true);
		if (Gdx.input.isKeyJustPressed(Keys.W) && (lastKey == Keys.S || lastKey == -1)) {
			gameScreen.key.loadSprite("ui/keys/KeyS.png");
			lastKey = Keys.W;
			actualPoints++;
		} else if (Gdx.input.isKeyJustPressed(Keys.S) && (lastKey == Keys.W || lastKey == -1)) {
			gameScreen.key.loadSprite("ui/keys/KeyW.png");
			lastKey = Keys.S;
			actualPoints++;
		} else if ((Gdx.input.isKeyJustPressed(Keys.W) && lastKey == Keys.W)
				|| (Gdx.input.isKeyJustPressed(Keys.S) && lastKey == Keys.S)) {
			actualPoints = 0;
		} else if (lastKey == -1) {
			gameScreen.key.loadSprite("ui/keys/KeyW.png");
		}

		if (actualPoints == maxPoints) {
			endMinigame();
		}
		super.act(delta);
	}

	private void endMinigame() {
		setIsEnabled(false);
		gameScreen.key.setEnabled(false);
		Params.exercise = Math.min(Params.MAX_POINTS, Params.EXERCISE_LOSS + Params.EXERCISE_LOSS * 2);
		gameScreen.player.canMove = true;
		gameScreen.player.isInteracting = false;
		lastKey = -1;
		actualPoints = 0;
	}

}
