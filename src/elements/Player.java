package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Params;

public class Player extends Element {
	private Animation<TextureRegion> stopFront;
	private Animation<TextureRegion> stopBack;
	private Animation<TextureRegion> stopRight;
	private Animation<TextureRegion> stopLeft;
	private Animation<TextureRegion> moveFront;
	private Animation<TextureRegion> moveBack;
	private Animation<TextureRegion> moveRight;
	private Animation<TextureRegion> moveLeft;
	private float acceleration;
	private float maxSpeed;
	private float deceleration;
	public boolean isInteracting;
	public boolean canMove;
	public int direction;

	public Player(float x, float y, Stage s) {
		super(x, y, s);

		stopRight = loadFullAnimation("player/derechaQuieto.png", 2, 1, 0.5f, true);
		moveRight = loadFullAnimation("player/derechaMoviendo.png", 2, 1, 0.5f, true);
		stopLeft = loadFullAnimation("player/izquierdaQuieto.png", 2, 1, 0.5f, true);
		moveLeft = loadFullAnimation("player/izquierdaMoviendo.png", 2, 1, 0.5f, true);

		this.setPolygon(8);
		this.acceleration = Params.acceleration;
		this.maxSpeed = Params.maxSpeed;
		this.deceleration = Params.deceleration;
		this.canMove = true;
		this.direction = 1;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		controles();
		changeSprite();
		this.applyPhysics(delta);
	}

	private void controles() {

		// Movimiento
		if (Gdx.input.isKeyPressed(Keys.W) && canMove) {
			accelerate(0, velocity.y + acceleration);
		} else if (Gdx.input.isKeyPressed(Keys.S) && canMove) {
			accelerate(0, velocity.y - acceleration);
		} else {
			decelerateY();
		}

		if (Gdx.input.isKeyPressed(Keys.A) && canMove) {
			accelerate(velocity.x - acceleration, 0);
		} else if (Gdx.input.isKeyPressed(Keys.D) && canMove) {
			accelerate(velocity.x + acceleration, 0);
		} else {
			decelerateX();
		}
	}

	private void changeSprite() {
		if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
			if (velocity.x > 0) {
				direction = 1;
				this.setAnimation(moveRight);
			} else {
				direction = 3;
				this.setAnimation(moveLeft);
			}

		} else if (Math.abs(velocity.x) < Math.abs(velocity.y)) {
			if (velocity.y < 0) {
				direction = 0;
				// this.setAnimation(moveBack);
			} else {
				direction = 2;
				// this.setAnimation(moveFront);
			}
		} else {
			switch (direction) {
			case 0:
				// this.setAnimation(stopBack);
				break;
			case 1:
				this.setAnimation(stopRight);
				break;
			case 2:
				// this.setAnimation(stopFront);
				break;
			case 3:
				this.setAnimation(stopLeft);
				break;
			}
		}
	}

	private void accelerate(float targetSpeedX, float targetSpeedY) {
		float accelerationX = targetSpeedX - this.velocity.x;
		float accelerationY = targetSpeedY - this.velocity.y;

		this.velocity.x = MathUtils.clamp(this.velocity.x + accelerationX * Gdx.graphics.getDeltaTime(), -maxSpeed,
				maxSpeed);
		this.velocity.y = MathUtils.clamp(this.velocity.y + accelerationY * Gdx.graphics.getDeltaTime(), -maxSpeed,
				maxSpeed);

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
