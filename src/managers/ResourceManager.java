package managers;

import org.w3c.dom.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class ResourceManager {
	private ResourceManager() {
	}

	public static AssetManager assets = new AssetManager();
	public static LabelStyle buttonStyle;
	public static TextButtonStyle textButtonStyle;

	public static void loadAllResources() {

		// mapas
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		assets.load("maps/mapa0.tmx", TiledMap.class);
		assets.load("maps/mapa2.tmx", TiledMap.class);
		assets.load("maps/Human'sFate.tmx", TiledMap.class);

		assets.load("maps/images/barrel.png", Texture.class);

		// jugador
		assets.load("player/derechaQuieto.png", Texture.class);
		assets.load("player/derechaMoviendo.png", Texture.class);
		assets.load("player/izquierdaQuieto.png", Texture.class);
		assets.load("player/izquierdaMoviendo.png", Texture.class);
		assets.load("player/grande.png", Texture.class);
		assets.load("player/frenteWalk.png", Texture.class);
		assets.load("player/frenteGrande.png", Texture.class);
		assets.load("player/izquieredawalk.png", Texture.class);
		assets.load("player/derechawalk.png", Texture.class);
		assets.load("player/food.png", Texture.class);
		assets.load("player/water.png", Texture.class);

		// objetos
		// assets.load("objects/bomb.png",Texture.class);
		// assets.load("objects/hookl.png",Texture.class);
		// assets.load("objects/sword.png",Texture.class);
		// assets.load("objects/swordA.png",Texture.class);

		// Audio
		assets.load("audio/sounds/jump.mp3", Sound.class);
		assets.load("audio/music/swing.mp3", Music.class);
		assets.load("audio/music/space.mp3", Music.class);

		// UI
		assets.load("ui/rojo.jpg", Texture.class);
		assets.load("ui/morado.jpg", Texture.class);
		assets.load("ui/dayProgressBar/progressBarAnimated.png", Texture.class);
		for (int i = 0; i < 11; i++) {
			String fileName = String.format("ui/dayProgressBar/%d.png", i);
			assets.load(fileName, Texture.class);
		}
		for (int i = 0; i < 10; i++) {
			String fileName = String.format("ui/hunger/%d.png", i);
			assets.load(fileName, Texture.class);
		}
		for (int i = 0; i < 10; i++) {
			String fileName = String.format("ui/thirst/%d.png", i);
			assets.load(fileName, Texture.class);
		}
		for (int i = 0; i < 10; i++) {
			String fileName = String.format("ui/exercise/%d.png", i);
			assets.load(fileName, Texture.class);
		}
		for (int i = 0; i < 32; i++) {
			String fileName = String.format("ui/progressBar/%d.png", i);
			assets.load(fileName, Texture.class);
		}

		// a�adir m�s elementos

	}

	public static boolean update() {
		return assets.update();
	}

	public static void botones() {

		// estilo para botones
		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("VCR_OSD.ttf"));
		FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

		ftfp.size = 36;
		ftfp.color = Color.WHITE;
		ftfp.borderColor = Color.BLACK;
		ftfp.borderWidth = 2;

		BitmapFont fuentePropia = ftfg.generateFont(ftfp);
		buttonStyle = new LabelStyle();
		buttonStyle.font = fuentePropia;
		textButtonStyle = new TextButtonStyle();
		Texture buttonText = ResourceManager.getTexture("maps/images/barrel.png");
		NinePatch buttonPatch = new NinePatch(buttonText);
		textButtonStyle.up = new NinePatchDrawable(buttonPatch);
		textButtonStyle.font = fuentePropia;

	}

	/*
	 * public static TextureAtlas getAtlas(String path){ return assets.get(path,
	 * TextureAtlas.class);
	 * 
	 * }
	 */

	public static Texture getTexture(String path) {
		return assets.get(path, Texture.class);
	}

	public static Music getMusic(String path) {
		return assets.get(path, Music.class);
	}

	public static Sound getSound(String path) {
		return assets.get(path, Sound.class);
	}

	public static TiledMap getMap(String path) {
		return assets.get(path, TiledMap.class);
	}

	public static void dispose() {
		assets.dispose();
	}
}
