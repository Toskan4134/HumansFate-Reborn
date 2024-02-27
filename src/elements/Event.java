package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Event extends Interaction {

	public String deathCause;


	public Event(float x, float y, Stage s, float w, float h, String type, boolean isEnabled, GameScreen gameScreen,
			String deathCause) {
		super(x, y, s, w, h, type, isEnabled, gameScreen);
		// TODO Auto-generated constructor stub
		this.deathCause = deathCause;
	}

}
