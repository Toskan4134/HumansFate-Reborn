package elements.Minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Minigame;
import game.Params;
import managers.SoundManager;
import screens.GameScreen;

public class HackGame extends Minigame {

	private int lastKey;
	private float maxCD;
	private float CD;
	private boolean newKey;

	public HackGame(float x, float y, Stage s, GameScreen gameScreen, int maxPoints) {
		super(x, y, s, gameScreen, maxPoints);
		this.lastKey = -1;
		this.maxCD = 1f;
		this.CD = maxCD;
		this.newKey = true;
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;

		gameScreen.key.setEnabled(true);

		if (newKey) {
			setRandomKey();
			newKey = false;
		}

		if (Gdx.input.isKeyJustPressed(lastKey)) {
			handleKeyPressed();
		} else if (isAnyKeyPressed()) {
			handleKeyMissed();
		}

		if (actualPoints == maxPoints) {
			endMinigame();
		}

		countdownBar(delta);

		super.act(delta);
	}

	private void setRandomKey() {
		int randomKeyIndex = MathUtils.random(0, 3);
		switch (randomKeyIndex) {
		case 0:
			lastKey = Keys.A;
			gameScreen.key.loadSprite("ui/keys/KeyA.png");
			break;
		case 1:
			lastKey = Keys.W;
			gameScreen.key.loadSprite("ui/keys/KeyW.png");
			break;
		case 2:
			lastKey = Keys.S;
			gameScreen.key.loadSprite("ui/keys/KeyS.png");
			break;
		case 3:
			lastKey = Keys.D;
			gameScreen.key.loadSprite("ui/keys/KeyD.png");
			break;
		}
		String keySpritePath = String.format("ui/keys/Key%s.png", (char) lastKey);

	}

	private boolean isAnyKeyPressed() {
		return Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.W)
				|| Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.D);
	}

	private void handleKeyPressed() {
		actualPoints++;
		CD = maxCD;
		newKey = true;
		SoundManager.playSound("audio/sounds/point.mp3");
	}

	private void handleKeyMissed() {
		actualPoints -= 5;
		CD = maxCD;
		newKey = true;
		SoundManager.playSound("audio/sounds/fail.mp3");

	}

	private void countdownBar(float delta) {
		if (CD >= 0) {
			CD -= delta;
		} else {
			actualPoints = 0;
			CD = maxCD;
			newKey = true;
		}
	}

	private void endMinigame() {
		setIsEnabled(false);
		gameScreen.map.getLayers().get("Screen").setVisible(true);
		gameScreen.map.getLayers().get("ScreenError").setVisible(false);
		gameScreen.key.setEnabled(false);
		gameScreen.player.canMove = true;
		SoundManager.playSound("audio/sounds/success.mp3");
		Params.eventTileModified = false;
		gameScreen.player.isInteracting = false;
		lastKey = -1;
		actualPoints = 0;
	}
}
