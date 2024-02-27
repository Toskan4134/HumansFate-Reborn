package elements.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Event;
import elements.Interaction;
import elements.Minigame;
import elements.Minigames.CleanGame;
import elements.Minigames.HackGame;
import elements.Minigames.PoopGame;
import game.Params;
import managers.SoundManager;
import screens.GameScreen;

public class SolarPanel extends Event {
	private Minigame clean;

	public SolarPanel(float x, float y, Stage s, float w, float h, String type, boolean isEnabled,
			GameScreen gameScreen, String deathCause) {
		super(x, y, s, w, h, type, isEnabled, gameScreen, deathCause);
		clean = new CleanGame(0, 0, getStage(), gameScreen, 25);
		gameScreen.minigames.add(clean);
	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		if (!Params.eventTileModified) {
			gameScreen.map.getLayers().get("SolarPanel").setVisible(false);
			gameScreen.map.getLayers().get("SolarPanelDirty").setVisible(true);
			SoundManager.playSound("audio/sounds/alert.mp3");
			Params.eventTileModified = true;
		}
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				gameScreen.player.isInteracting = true;
				gameScreen.player.direction = 0;
				gameScreen.player.canMove = false;
				gameScreen.player.setVelocity(new Vector2(0, 0));
				clean.setIsEnabled(true);
				isEnabled = false;
			}
		}
		this.applyPhysics(delta);

	}

}
