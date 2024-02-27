package elements.Minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Minigame;
import game.Params;
import managers.SoundManager;
import screens.GameScreen;
import screens.LoadScreen;

public class CleanGame extends Minigame {

	private int lastKey;

	public CleanGame(float x, float y, Stage s, GameScreen gameScreen, int maxPoints) {
		super(x, y, s, gameScreen, maxPoints);
		// TODO Auto-generated constructor stub
		this.lastKey = -1;
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		gameScreen.key.setEnabled(true);
		if (Gdx.input.isKeyJustPressed(Keys.A) && (lastKey == Keys.D || lastKey == -1)) {
			gameScreen.key.loadSprite("ui/keys/KeyD.png");
			lastKey = Keys.A;
			actualPoints++;
			SoundManager.playSound("audio/sounds/point.mp3");

		} else if (Gdx.input.isKeyJustPressed(Keys.D) && (lastKey == Keys.A || lastKey == -1)) {
			gameScreen.key.loadSprite("ui/keys/KeyA.png");
			lastKey = Keys.D;
			actualPoints++;
			SoundManager.playSound("audio/sounds/point.mp3");

		} else if ((Gdx.input.isKeyJustPressed(Keys.A) && lastKey == Keys.A)
				|| (Gdx.input.isKeyJustPressed(Keys.D) && lastKey == Keys.D)) {
			actualPoints = 0;
			SoundManager.playSound("audio/sounds/fail.mp3");

		} else if (lastKey == -1) {
			gameScreen.key.loadSprite("ui/keys/KeyA.png");
		}

		if (actualPoints == maxPoints) {
			endMinigame();
		}
		super.act(delta);
	}

	private void endMinigame() {
		setIsEnabled(false);
		gameScreen.key.setEnabled(false);
		gameScreen.map.getLayers().get("SolarPanel").setVisible(true);
		gameScreen.map.getLayers().get("SolarPanelDirty").setVisible(false);
		SoundManager.playSound("audio/sounds/success.mp3");
		Params.eventTileModified = false;
		gameScreen.player.canMove = true;
		gameScreen.player.isInteracting = false;
		lastKey = -1;
		actualPoints = 0;
	}

}
