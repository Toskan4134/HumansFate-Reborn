package elements.Interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import elements.Interaction;
import elements.Projectile;
import screens.GameScreen;

public class Fridge extends Interaction {
	private Projectile food;

	public Fridge(float x, float y, Stage s, float w, float h, String type, boolean isEnabled, GameScreen gameScreen) {
		super(x, y, s, w, h, type, isEnabled, gameScreen);
		// TODO Auto-generated constructor stub
		this.food = new Projectile(x, y, s, "Food", isEnabled, gameScreen);
		gameScreen.interactions.add(food);

	}

	@Override
	public void act(float delta) {
		if (!isEnabled)
			return;
		if (gameScreen.player.overlaps(this)) {
			if (Gdx.input.isKeyJustPressed(Keys.E)) {
				throwFood();
			}
		}
		this.applyPhysics(delta);

	}

	private void throwFood() {
		this.isEnabled = false;
		gameScreen.player.isInteracting = true;
		float velocityX = MathUtils.random(1, 50);
		float velocityY = MathUtils.random(-50, 50);
		this.food.shoot(velocityX, velocityY, this.getX() + 32, this.getY() + 32);
	}

}
