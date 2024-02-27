package elements.Interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Interaction;
import elements.Minigame;
import elements.Minigames.PoopGame;
import screens.GameScreen;

public class Toilet extends Interaction {
	private Minigame toilet;

	public Toilet(float x, float y, Stage s, float w, float h, String type, boolean isEnabled, GameScreen gameScreen) {
		super(x, y, s, w, h, type, isEnabled, gameScreen);
		toilet = new PoopGame(0, 0, getStage(), gameScreen, 20);
		gameScreen.minigames.add(toilet);
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				gameScreen.player.isInteracting = true;
				gameScreen.player.direction = 3;
				playerLockInMiddle(false);
				toilet.setIsEnabled(true);
				isEnabled = false;
			}
		}
		this.applyPhysics(delta);

	}

}
