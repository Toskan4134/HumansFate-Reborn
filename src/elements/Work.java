package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Work extends Element {

	private String type;

	public Work(float x, float y, Stage s, String type) {
		super(x, y, s);
		this.type = type;
		this.loadFullAnimation("maps/images/characters_packed.png", 3, 9, 0.2f, false);
		this.setRectangle();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		switch (this.type) {
		case "cinta":
			break;
		}
		this.applyPhysics(delta);

	}

}
