package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import game.Params;
import managers.SoundManager;
import screens.GameScreen;

public class Minigame extends Element {
	public GameScreen gameScreen;

	public int maxPoints;
	public int actualPoints;
	public boolean isEnabled;

	public Minigame(float x, float y, Stage s, GameScreen gameScreen, int maxPoints) {
		super(x, y, s);
		// TODO Auto-generated constructor stub
		this.gameScreen = gameScreen;
		this.maxPoints = maxPoints;
		this.actualPoints = 0;
		this.setRectangle();
		setIsEnabled(false);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		if (!isEnabled)
			return;
		updateUI();

		super.act(delta);
	}

	private void updateUI() {
		Params.progressBarCount = (int) (Math.floor((actualPoints) * 31 / maxPoints));
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		Params.enabledMinigameUI = isEnabled;
	}

}
