package elements.Interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Interaction;
import elements.Minigame;
import elements.Minigames.GymGame;
import elements.Minigames.PoopGame;
import screens.GameScreen;

public class Exercise extends Interaction {
	private Minigame gym;

	public Exercise(float x, float y, Stage s, float w, float h, String type, boolean isEnabled,
			GameScreen gameScreen) {
		super(x, y, s, w, h, type, isEnabled, gameScreen);
		gym = new GymGame(0, 0, getStage(), gameScreen, 30);
		gameScreen.minigames.add(gym);
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				gameScreen.player.isInteracting = true;
				gameScreen.player.direction = 0;
				playerLockInMiddle(false);
				gym.setIsEnabled(true);
				isEnabled = false;
			}
		}
		this.applyPhysics(delta);

	}

}
