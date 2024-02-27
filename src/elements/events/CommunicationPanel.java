package elements.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Event;
import elements.Interaction;
import elements.Minigame;
import elements.Minigames.HackGame;
import elements.Minigames.PoopGame;
import game.Params;
import managers.SoundManager;
import screens.GameScreen;

public class CommunicationPanel extends Event {
	private Minigame hack;

	public CommunicationPanel(float x, float y, Stage s, float w, float h, String type, boolean isEnabled,
			GameScreen gameScreen, String deathCause) {
		super(x, y, s, w, h, type, isEnabled, gameScreen, deathCause);
		hack = new HackGame(0, 0, getStage(), gameScreen, 25);
		gameScreen.minigames.add(hack);
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		if (!Params.eventTileModified) {
			gameScreen.map.getLayers().get("Screen").setVisible(false);
			gameScreen.map.getLayers().get("ScreenError").setVisible(true);
			SoundManager.playSound("audio/sounds/alert.mp3");

			Params.eventTileModified = true;
		}
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				gameScreen.player.isInteracting = true;
				gameScreen.player.direction = 0;
				playerLockInMiddle(false);
				hack.setIsEnabled(true);
				isEnabled = false;
			}
		}
		this.applyPhysics(delta);

	}

}
