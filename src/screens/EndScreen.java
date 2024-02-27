package screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;

import elements.Element;

import elements.Player;
import elements.Solid;
import game.Demo;
import game.Params;
import managers.AudioManager;
import managers.ResourceManager;

public class EndScreen extends BScreen {

	Stage mainStage;

	public Array<Solid> suelo;

	Solid end;

	OrthographicCamera camara;
	private TiledMap map;

	private OrthogonalTiledMapRenderer renderer;

	public Player player;

	private Label gameOverLabel;

	public EndScreen(Demo game) {

		super(game);

		mainStage = new Stage();
		map = ResourceManager.getMap("maps/FinalScreen.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, mainStage.getBatch());
		camara = (OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Params.getAnchoPantalla() * Params.zoom, Params.getAltoPantalla() * Params.zoom);
		renderer.setView(camara);
		Params.zoom = 0.2f;
		uiStage = new Stage();

		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("VCR_OSD.ttf"));
		FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

		ftfp.size = 72;
		ftfp.color = Color.WHITE;
		ftfp.borderColor = Color.BLACK;
		ftfp.borderWidth = 2;

		BitmapFont font = ftfg.generateFont(ftfp);
		uiStyle = new LabelStyle(font, Color.WHITE);
		gameOverLabel = new Label("Game Over", uiStyle);

		AudioManager.playMusic("audio/music/endSong.mp3");

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		mainStage.act();
		uiStage.act();
		renderer.render();
		renderer.setView(camara);
		camara.setToOrtho(false, Params.getAnchoPantalla() * Params.zoom, Params.getAltoPantalla() * Params.zoom);
		camara.position.x = 1280;
		camara.position.y = 960;
		camara.update();
		actualizarInterfaz(delta);

		mainStage.draw();
		uiStage.draw();

	}

	public void colide() {

	}

	public ArrayList<MapObject> getRectangleList(String propertyName) {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {
					list.add(obj);
				}

			}

		}

		return list;
	}


	private void actualizarInterfaz(float delta) {
		Params.zoom = (float) Math.min(1.5f, Params.zoom + 0.1 * delta);

		if (Params.zoom == 1.5f) {

			gameOverLabel.setPosition(Params.getAnchoPantalla() / 2 - gameOverLabel.getWidth() / 2,
					Params.getAltoPantalla() - gameOverLabel.getHeight() - 74);
			uiStage.addActor(gameOverLabel);
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				AudioManager.currentMusic.stop();
				game.setScreen(new TitleScreen(game));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		Params.setAltoPantalla(height);
		Params.setAnchoPantalla(width);
		uiStage = new Stage();
		gameOverLabel.setPosition(Params.getAnchoPantalla() / 2 - gameOverLabel.getWidth() / 2,
				Params.getAltoPantalla() - gameOverLabel.getHeight() - 74);
		camara.setToOrtho(false, width * Params.zoom, height * Params.zoom);
	}
}
