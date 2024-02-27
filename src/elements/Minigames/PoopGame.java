package elements.Minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Minigame;
import game.Params;
import managers.SoundManager;
import screens.GameScreen;
import screens.LoadScreen;

public class PoopGame extends Minigame {

	private float maxCD;
	private float CD;

	public PoopGame(float x, float y, Stage s, GameScreen gameScreen, int maxPoints) {
		super(x, y, s, gameScreen, maxPoints);
		// TODO Auto-generated constructor stub
		this.maxCD = 0.25f;
		this.CD = 0;
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		gameScreen.key.setEnabled(true);
		gameScreen.key.loadSprite("ui/keys/KeySpace.png");
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			actualPoints = Math.min(maxPoints, actualPoints + 1);
			SoundManager.playSound("audio/sounds/point.mp3");
		}
		countdownBar(delta);
		if (actualPoints == maxPoints) {
			endMinigame();
		}
		super.act(delta);
	}

	private void endMinigame() {
		setIsEnabled(false);
		gameScreen.key.setEnabled(false);
		Params.poop = Math.min(Params.MAX_POINTS, Params.poop + Params.POOP_LOSS + 3);
		SoundManager.playSound("audio/sounds/success.mp3");
		gameScreen.player.canMove = true;
		gameScreen.player.isInteracting = false;
		actualPoints = 0;
		CD = 0;
	}

	private void countdownBar(float delta) {
		if (CD >= 0) {
			CD -= delta;
		} else {
			actualPoints = Math.max(0, actualPoints - 1);
			CD = maxCD;
		}
	}

}
