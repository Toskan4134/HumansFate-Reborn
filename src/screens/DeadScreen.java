package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import game.Demo;
import game.Params;
import managers.AudioManager;
import managers.ResourceManager;

public class DeadScreen extends BScreen {
	private float loadDelay = 7;
	private float loadCount = loadDelay;

	public DeadScreen(Demo game) {

		super(game);
		// this.resourceManager=new ResourceManager();
		// game.resourceManager=this.resourceManager;

		ResourceManager.loadAllResources();
		// while(!ResourceManager.update()){}

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		Params.gameScreen = null;

		if (ResourceManager.update()) {
			AudioManager.currentMusic.stop();

			FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("VCR_OSD.ttf"));
			FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

			ftfp.size = 48;
			ftfp.color = Color.WHITE;
			ftfp.borderColor = Color.BLACK;
			ftfp.borderWidth = 2;

			BitmapFont font = ftfg.generateFont(ftfp);
			uiStyle = new LabelStyle(font, Color.WHITE);

			Label deadLabel = new Label("Has Muerto", uiStyle);
			deadLabel.setPosition((Params.getAnchoPantalla() - deadLabel.getWidth()) / 2,
					Params.getAltoPantalla() / 3 + Params.getAltoPantalla() / 3);

			ftfp.size = 24;
			ftfp.color = Color.WHITE;
			ftfp.borderColor = Color.BLACK;
			ftfp.borderWidth = 2;

			font = ftfg.generateFont(ftfp);
			uiStyle = new LabelStyle(font, Color.WHITE);

			Label causeLabel = new Label(Params.deathCause, uiStyle);
			causeLabel.setWrap(true);
			// Pack label
			causeLabel.pack(); // This might not be necessary, unless you're changing other attributes such as
								// font scale.
			// Manual sizing
			causeLabel.setWidth(Params.getAnchoPantalla() - 100); // Set the width directly
			causeLabel.setPosition((Params.getAnchoPantalla() - causeLabel.getWidth()) / 2 + 25,
					Params.getAltoPantalla() / 2 - 50);

			uiStage.addActor(deadLabel);
			uiStage.addActor(causeLabel);
			uiStage.act();
			uiStage.draw();

		}
		if (loadCount >= 0) {
			loadCount -= delta;
		} else if (loadCount < 0 || Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new TitleScreen(game));
		}

	}

}
