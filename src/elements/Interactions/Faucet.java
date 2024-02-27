package elements.Interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Interaction;
import elements.Projectile;
import managers.SoundManager;
import screens.GameScreen;

public class Faucet extends Interaction {
	private Projectile water;
	private float waterDefaultCD;
	private float waterCD;
	public int dropplets;
	public boolean canStartFaucet;

	public Faucet(float x, float y, Stage s, float w, float h, String type, boolean isEnabled, GameScreen gameScreen) {
		super(x, y, s, w, h, type, isEnabled, gameScreen);
		// TODO Auto-generated constructor stub
		this.water = new Projectile(x, y, s, "Water", isEnabled, gameScreen);
		this.waterDefaultCD = 3;
		this.waterCD = 0;
		this.dropplets = 3;
	}

	@Override
	public void act(float delta) {
		updateWaterCD(delta);
		if (!isEnabled)
			return;
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				throwWater();
				SoundManager.playSound("audio/sounds/success.mp3");
			}
		}
		this.applyPhysics(delta);

	}

	private void updateWaterCD(float delta) {
		if (!type.equals("Faucet") || !canStartFaucet)
			return;

		if (waterCD > 0) {
			waterCD -= delta;
		}
		if (waterCD <= 0) {
			if (dropplets == 0) {
				water.setEnabled(false);
				return;
			}
			throwWater();
		}
	}

	private void throwWater() {
		this.isEnabled = false;
		canStartFaucet = true;
		if (waterCD <= 0 && dropplets > 0) {
			waterCD = waterDefaultCD;

			dropplets--;
			float velocityX = MathUtils.random(1, 30);
			float velocityY = MathUtils.random(-50, -10);
			this.water.shoot(velocityX, velocityY, this.getX() + 16, this.getY() + 32);
		}
	}
}
