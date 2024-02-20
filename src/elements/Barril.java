package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Barril extends Element {
	float speed = 50;
	final float tiempoPatrulla = 2;
	float tiempoPatrullado = 0;

	public Barril(float x, float y, Stage s) {
		super(x, y, s);
		this.loadFullAnimation("maps/images/barrel.png");
		this.setRectangle();
		this.velocity.y = speed;
	}

	public void act(float delta) {
		super.act(delta);
		if (tiempoPatrullado > tiempoPatrulla) {
			this.velocity.y *= -1;
			tiempoPatrullado = 0;
		} else {
			tiempoPatrullado += delta;
		}

		this.applyPhysics(delta);
	}

}
