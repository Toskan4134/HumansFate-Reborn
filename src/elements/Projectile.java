package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Params;
import managers.SoundManager;
import screens.GameScreen;

public class Projectile extends Interaction {

	public float speed;
	public Element top;
	public Element bottom;
	public Element left;
	public Element right;

	public Projectile(float x, float y, Stage s, String type, boolean isEnabled, GameScreen gameScreen) {
		super(x, y, s, type, isEnabled, gameScreen);
		loadSprite();
		this.setRectangle();
		this.setEnabled(false);
		this.speed = 10;
		generateFoodHitboxes(s);

	}

	@Override
	public void act(float delta) {
		if (!this.getEnabled())
			return;
		for (Actor actor : this.getStage().getActors()) {
			if (actor instanceof Solid && (top.overlaps((Solid) actor) || bottom.overlaps((Solid) actor))) {
				this.velocity.y = -this.velocity.y;
				break;
			} else if (actor instanceof Solid && (left.overlaps((Solid) actor) || right.overlaps((Solid) actor))) {
				this.velocity.x = -this.velocity.x;
				break;
			}
		}

		if (gameScreen.player.overlaps(this)) {
			if (this.type == "Food" && Gdx.input.isKeyJustPressed(Keys.E)) {
				foodEaten();
			}
			if (this.type == "Water") {
				waterDrunk();
			}
		}
		super.act(delta);
		this.applyPhysics(delta);
		if (this.type == "Food")
			setFoodParts();

	}

	private void loadSprite() {
		if (this.type.equals("Water")) {
			this.loadSprite("player/water.png");

		} else {
			this.loadSprite("player/food.png");

		}
	}

	private void generateFoodHitboxes(Stage s) {
		this.top = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 3);
		this.top.setRectangle();
		this.bottom = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 3);
		this.bottom.setRectangle();
		this.left = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 3);
		this.left.setRectangle();
		this.right = new Element(0, 0, s, this.getWidth() / 3, this.getHeight() / 3);
		this.right.setRectangle();
	}

	public void shoot(float velocityX, float velocityY, float x, float y) {
		this.setEnabled(true);
		this.setPosition(x, y);
		this.velocity.x = velocityX + speed;
		this.velocity.y = velocityY + speed;
	}

	public void setFoodParts() {
		top.setPosition(this.getX() + this.getWidth() / 2 - this.getWidth() / 6,
				this.getY() + this.getHeight() - this.getHeight() / 3);

		bottom.setPosition(this.getX() + this.getWidth() / 2 - this.getWidth() / 6, this.getY());

		left.setPosition(this.getX(), this.getY() + this.getHeight() / 2 - this.getHeight() / 6);

		right.setPosition(this.getX() + this.getWidth() - this.getWidth() / 3,
				this.getY() + this.getHeight() / 2 - this.getHeight() / 6);
	}

	private void foodEaten() {
		if (gameScreen.player.isInteracting) {
			gameScreen.player.isInteracting = false;
			return;
		}
		SoundManager.playSound("audio/sounds/eat.mp3");

		Params.hunger = MathUtils.clamp(Params.hunger + Params.HUNGER_LOSS * 2, 0, 12);
		this.isEnabled = false;
		this.setEnabled(false);
		top.setEnabled(false);
		bottom.setEnabled(false);
		left.setEnabled(false);
		right.setEnabled(false);
	}

	private void waterDrunk() {
		Params.thirst = MathUtils.clamp(Params.thirst + Params.THIRST_LOSS / 3 * 2, 0, 12);
		SoundManager.playSound("audio/sounds/drink.mp3");
		this.setEnabled(false);
	}
}
