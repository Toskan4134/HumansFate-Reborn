package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Params;

public class Player extends Element {
	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> espalda;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;
	private float acceleration;
	private float maxSpeed;
	private float deceleration;
	public boolean isWorking;

	public Player(float x, float y, Stage s) {
		super(x, y, s);

		frente = loadFullAnimation("player/frenteWalk.png", 4, 1, 0.2f, true);

		this.setPolygon(8);
		this.acceleration = Params.acceleration;
		this.maxSpeed = Params.maxSpeed;
		this.deceleration = Params.deceleration;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		controles();

		this.applyPhysics(delta);
	}

	private void controles() {

		if (isWorking)
			return;
		// Movimiento
		if (Gdx.input.isKeyPressed(Keys.W)) {
			accelerate(0, velocity.y + acceleration);
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			accelerate(0, velocity.y - acceleration);
		} else {
			decelerateY();
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			accelerate(velocity.x - acceleration, 0);
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			accelerate(velocity.x + acceleration, 0);
		} else {
			decelerateX();
		}

	}

	private void accelerate(float targetSpeedX, float targetSpeedY) {
		float accelerationX = targetSpeedX - this.velocity.x;
		float accelerationY = targetSpeedY - this.velocity.y;
		this.velocity.x += accelerationX * Gdx.graphics.getDeltaTime();
		this.velocity.y += accelerationY * Gdx.graphics.getDeltaTime();

		this.velocity.x = MathUtils.clamp(this.velocity.x, -maxSpeed, maxSpeed);
		this.velocity.y = MathUtils.clamp(this.velocity.y, -maxSpeed, maxSpeed);
	}

	private void decelerateX() {
		if (velocity.x > 0) {
			velocity.x -= deceleration * Gdx.graphics.getDeltaTime();
			if (velocity.x < 0)
				velocity.x = 0;
		} else if (velocity.x < 0) {
			velocity.x += deceleration * Gdx.graphics.getDeltaTime();
			if (velocity.x > 0)
				velocity.x = 0;
		}
	}

	private void decelerateY() {
		if (velocity.y > 0) {
			velocity.y -= deceleration * Gdx.graphics.getDeltaTime();
			if (velocity.y < 0)
				velocity.y = 0;
		} else if (velocity.y < 0) {
			velocity.y += deceleration * Gdx.graphics.getDeltaTime();
			if (velocity.y > 0)
				velocity.y = 0;
		}
	}

}
