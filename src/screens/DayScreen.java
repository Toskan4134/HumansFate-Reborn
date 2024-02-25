package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import game.Demo;
import game.Params;
import managers.ResourceManager;

public class DayScreen extends BScreen {
	private float loadDelay = 5;
	private float loadCount = loadDelay;

	public DayScreen(Demo game) {

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
		if (ResourceManager.update()) {
			FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("VCR_OSD.ttf"));
			FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

			ftfp.size = 48;
			ftfp.color = Color.WHITE;
			ftfp.borderColor = Color.BLACK;
			ftfp.borderWidth = 2;

			BitmapFont font = ftfg.generateFont(ftfp);
			uiStyle = new LabelStyle(font, Color.WHITE);

			Label dayLabel = new Label(Params.days+" Días Restantes", uiStyle);

			dayLabel.setPosition((Params.getAnchoPantalla() - dayLabel.getWidth()) / 2,
					(Params.getAltoPantalla() - dayLabel.getHeight()) / 2);
			uiStage.addActor(dayLabel);
			uiStage.act();
			uiStage.draw();

		}
		if (loadCount >= 0) {
			loadCount -= delta;
		} else {
			game.setScreen(Params.gameScreen);
		}

	}

}
