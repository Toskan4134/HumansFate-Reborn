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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;

import elements.Element;
import elements.Event;
import elements.Interaction;
import elements.Minigame;
import elements.Player;
import elements.Solid;
import elements.Interactions.Exercise;
import elements.Interactions.Faucet;
import elements.Interactions.Fridge;
import elements.Interactions.Toilet;
import elements.Minigames.PoopGame;
import elements.events.CommunicationPanel;
import elements.events.SolarPanel;
import game.Demo;
import game.Params;
import managers.AudioManager;
import managers.ResourceManager;
import managers.SoundManager;

public class GameScreen extends BScreen {

	Stage mainStage;

	public Array<Solid> paredes;
	public Array<Solid> suelo;
	public Array<Solid> muerte;
	public Array<Interaction> interactions;
	public Array<Event> events;
	public Array<Minigame> minigames;

	OrthographicCamera camara;
	public TiledMap map;

	private int[] primerPlano = new int[] { 3, 5 };

	private Label interactionLabel;
	private Label daysLabel;
	private Label secondsLabel;
	private Element progressBar;
	private Element dayProgressBar;
	private Element hungerProgress;
	private Element thirstProgress;
	private Element exerciseProgress;
	private Element sleepProgress;
	private Element poopProgress;
	public Element key;

	private OrthogonalTiledMapRenderer renderer;

	public Player player;
	public Interaction juego1;

	private boolean touchedDead;

	public GameScreen(Demo game) {

		super(game);

		mainStage = new Stage();
		map = ResourceManager.getMap("maps/Human'sFate.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, mainStage.getBatch());

		camara = (OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Params.getAnchoPantalla() * Params.zoom, Params.getAltoPantalla() * Params.zoom);

		minigames = new Array<Minigame>();

		// Params
		Params.days = Params.MAX_DAYS;
		Params.secsPerRound = Params.MAX_SECS_PER_ROUND;
		Params.hunger = Params.MAX_POINTS;
		Params.thirst = Params.MAX_POINTS;
		Params.sleep = Params.MAX_POINTS;
		Params.poop = Params.MAX_POINTS;
		Params.exercise = Params.MAX_POINTS;
		Params.zoom = 1f;
		Params.deathCause = null;
		this.touchedDead = false;

		player = new Player(Params.jugadorx, Params.jugadory, mainStage);

		// Solids
		ArrayList<MapObject> solidHitboxes;
		solidHitboxes = getRectangleList("Solid");
		MapProperties props;
		Solid solid;
		paredes = new Array<Solid>();

		for (MapObject collision : solidHitboxes) {
			props = collision.getProperties();
			solid = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
			paredes.add(solid);
		}

		// Deaths
		ArrayList<MapObject> deathHitboxes;
		deathHitboxes = getRectangleList("Death");
		Solid death;
		muerte = new Array<Solid>();
		for (MapObject deathCollision : deathHitboxes) {
			props = deathCollision.getProperties();
			death = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
			muerte.add(death);
		}

		// Interactions
		loadInteractions();
		loadEvents();
		AudioManager.playMusic("audio/music/space.mp3");

		generateUIStyle();

		// UI
		uiStage = new Stage();
		daysLabel = new Label(Params.days + " Días", uiStyle);
		secondsLabel = new Label((int) Params.secsPerRound + "s", uiStyle);
		interactionLabel = new Label("Presiona la tecla \"E\" para interactuar", uiStyle);

		progressBar = new Element(Params.getAnchoPantalla() / 2 - 128, 80, uiStage);
		dayProgressBar = new Element(20, Params.getAltoPantalla() - 64, uiStage);
		hungerProgress = new Element(Params.getAnchoPantalla() - 84, Params.getAltoPantalla() - 84, uiStage);
		thirstProgress = new Element(Params.getAnchoPantalla() - 84 * 2, Params.getAltoPantalla() - 84, uiStage);
		exerciseProgress = new Element(Params.getAnchoPantalla() - 84 * 3, Params.getAltoPantalla() - 84, uiStage);
		sleepProgress = new Element(Params.getAnchoPantalla() - 84 * 4, Params.getAltoPantalla() - 84, uiStage);
		poopProgress = new Element(Params.getAnchoPantalla() - 84 * 5, Params.getAltoPantalla() - 84, uiStage);
		key = new Element(Params.getAnchoPantalla() / 2 - 32, 140, uiStage);
		key.setEnabled(false);
	}

	public void updateInterface() {

		// Day Progress Bar
		dayProgressBar.loadSprite("ui/dayProgressBar/"
				+ (int) (Math.floor((Params.MAX_DAYS - Params.days) * 11 / Params.MAX_DAYS)) + ".png");

		// Day Label
		daysLabel.setText(Params.days + " Días");
		daysLabel.setPosition(20, Params.getAltoPantalla() - daysLabel.getHeight() - 74);

		// Seconds Label
		secondsLabel.setText((int) Params.secsPerRound + "s");
		secondsLabel.setPosition(Params.getAnchoPantalla() - secondsLabel.getWidth() - 20, 20);

		// Interaction Label

		interactionLabel.setPosition((Params.getAnchoPantalla() - interactionLabel.getWidth()) / 2, 50);
		interactionLabel.setVisible(false);

		// Minigame UI
		if (Params.enabledMinigameUI) {
			progressBar.setEnabled(true);
			progressBar.loadSprite("ui/progressBar/" + Params.progressBarCount + ".png");
		} else {
			progressBar.setEnabled(false);
		}

		// Stadistics UI
		hungerProgress.loadSprite("ui/hunger/"
				+ Math.min(9, (int) (Math.floor((Params.MAX_POINTS - Params.hunger) * 9 / Params.MAX_POINTS)))
				+ ".png");

		thirstProgress.loadSprite("ui/thirst/"
				+ Math.min(9, (int) (Math.floor((Params.MAX_POINTS - Params.thirst) * 9 / Params.MAX_POINTS)))
				+ ".png");

		exerciseProgress
				.loadSprite(
						"ui/exercise/"
								+ Math.min(9,
										(int) (Math
												.floor((Params.MAX_POINTS - Params.exercise) * 9 / Params.MAX_POINTS)))
								+ ".png");
		sleepProgress.loadSprite("ui/sleep/"
				+ Math.min(9, (int) (Math.floor((Params.MAX_POINTS - Params.sleep) * 9 / Params.MAX_POINTS))) + ".png");

		poopProgress.loadSprite("ui/poop/"
				+ Math.min(9, (int) (Math.floor((Params.MAX_POINTS - Params.poop) * 9 / Params.MAX_POINTS))) + ".png");

		boolean playerOverlapsAnyInteraction = false;

		for (Interaction interaction : interactions) {
			if (player.overlaps(interaction) && interaction.isEnabled) {
				playerOverlapsAnyInteraction = true;
				break;
			}
		}
		for (Interaction event : events) {
			if (player.overlaps(event) && event.isEnabled) {
				playerOverlapsAnyInteraction = true;
				break;
			}
		}
		if (playerOverlapsAnyInteraction && !interactionLabel.isVisible() && !player.isInteracting) {
			interactionLabel.setVisible(true);
			key.setEnabled(true);
			key.loadSprite("ui/keys/KeyE.png");

		} else if (!playerOverlapsAnyInteraction && !player.isInteracting) {
			interactionLabel.setVisible(false);
			key.setEnabled(false);
		}

		uiStage.addActor(daysLabel);
		uiStage.addActor(secondsLabel);
		uiStage.addActor(interactionLabel);
		uiStage.addActor(dayProgressBar);
		uiStage.addActor(progressBar);
		uiStage.addActor(hungerProgress);
		uiStage.addActor(thirstProgress);
		uiStage.addActor(exerciseProgress);
		uiStage.addActor(sleepProgress);
		uiStage.addActor(poopProgress);
		uiStage.addActor(key);
	}

	private void activateEvent() {
		float probability = 0.1f + (0.8f * ((float) Params.days / Params.MAX_DAYS));
		float random = MathUtils.random();
		if (random > probability) {
			int randomIndex = MathUtils.random(0, events.size - 1);
			Interaction event = events.get(randomIndex);
			event.isEnabled = true;
		}
	}

	public String getActivatedEventDeadCause() {
		for (Event evento : events) {
			if (evento.isEnabled) {
				return evento.deathCause;
			}
		}
		return null;
	}

	public void loadEvents() {
		ArrayList<MapObject> eventHitboxes;
		eventHitboxes = getRectangleList("Event");
		Event work;
		events = new Array<Event>();
		MapProperties props;

		for (MapObject event : eventHitboxes) {
			props = event.getProperties();
			if (props.get("Type").equals("CommunicationPanel")) {
				work = new CommunicationPanel((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"), (String) props.get("Type"),
						(boolean) props.get("Enabled"), this, (String) props.get("DeathCause"));
			} else if (props.get("Type").equals("SolarPanel")) {
				work = new SolarPanel((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"), (String) props.get("Type"),
						(boolean) props.get("Enabled"), this, (String) props.get("DeathCause"));
			} else {
				work = new Event((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
						(float) props.get("height"), (String) props.get("Type"), (boolean) props.get("Enabled"), this,
						(String) props.get("DeathCause"));
			}
			events.add(work);
		}

	}

	public void loadInteractions() {
		ArrayList<MapObject> interactionsHitboxes;
		interactionsHitboxes = getRectangleList("Interaction");
		Interaction work;
		interactions = new Array<Interaction>();
		MapProperties props;

		for (MapObject interaction : interactionsHitboxes) {
			props = interaction.getProperties();
			if (props.get("Type").equals("Faucet")) {
				work = new Faucet((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
						(float) props.get("height"), (String) props.get("Type"), (boolean) props.get("Enabled"), this);
			} else if (props.get("Type").equals("Fridge")) {
				work = new Fridge((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
						(float) props.get("height"), (String) props.get("Type"), (boolean) props.get("Enabled"), this);
			} else if (props.get("Type").equals("Toilet")) {
				work = new Toilet((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
						(float) props.get("height"), (String) props.get("Type"), (boolean) props.get("Enabled"), this);
			} else if (props.get("Type").equals("Exercise")) {
				work = new Exercise((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"), (String) props.get("Type"),
						(boolean) props.get("Enabled"), this);
			} else {
				work = new Interaction((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"), (String) props.get("Type"),
						(boolean) props.get("Enabled"), this);
			}
			interactions.add(work);
		}
	}

	public void restartInteractions() {
		interactions.forEach(interaction -> {
			interaction.isEnabled = true;
			if (interaction instanceof Faucet) {
				((Faucet) interaction).dropplets = 3;
				((Faucet) interaction).canStartFaucet = false;
			}
		});
	}

	public void generateUIStyle() {
		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("VCR_OSD.ttf"));
		FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

		ftfp.size = 28;
		ftfp.color = Color.WHITE;
		ftfp.borderColor = Color.BLACK;
		ftfp.borderWidth = 2;

		BitmapFont font = ftfg.generateFont(ftfp);
		uiStyle = new LabelStyle(font, Color.WHITE);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		mainStage.act();
		uiStage.act();
		colide();

		centrarCamara();
		renderer.setView(camara);
		updateTimer(delta);
		renderer.render();
		mainStage.draw();
		renderer.render(primerPlano);
		updateInterface();
		uiStage.draw();

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Params.gameScreen = this;
			game.setScreen(new LoadScreen(game));
		}

	}

	public void updateTimer(float delta) {
		if (Params.secsPerRound > 0) {
			Params.secsPerRound -= delta;
		}

		if (Params.secsPerRound <= 0) {
			Params.days--;
			changeDay();
			Params.secsPerRound = Params.MAX_SECS_PER_ROUND;
		}

		if (Params.days == 0) {
			Params.days = Params.MAX_DAYS;
			Params.gameScreen = null;
			game.setScreen(new EndScreen(game));

		}
	}

	public void colide() {
		for (Solid pared : paredes) {
			if (player.overlaps(pared)) {
				player.preventOverlap(pared);
			}
		}
		for (Element death : muerte) {
			if (player.overlaps(death)) {
				touchedDead = true;
				checkDead();
			}
		}
	}

	public void centrarCamara() {
		this.camara.position.x = player.getX() + 32;
		this.camara.position.y = player.getY() + 32;
		camara.update();

	}

	public boolean checkDead() {
		String deathCause = null;
		if (touchedDead) {
			deathCause = "¡Brasita Espacial! Nuestro héroe no tenía ganas de terminar el juego y ha decidido hacer una barbacoa humana.";
		} else if (Params.hunger < 0) {
			deathCause = "¡Oops! Nuestro aventurero murió de hambre. Parece que le daba asco la comida espacial...";
		} else if (Params.thirst < 0) {
			deathCause = "¡Qué sed tan mortal! Nuestro explorador se deshidrató y se despidió de la vida.";
		} else if (Params.sleep < 0) {
			deathCause = "¡A dormir para siempre! Nuestro valiente protagonista se quedó sin energía y cayó en un sueño eterno.";
		} else if (Params.exercise < 0) {
			deathCause = "¡Demasiado sedentario! Nuestro aventurero se saltó el día de pierna y no se ha vuelto a levantar.";
		} else if (Params.poop < 0) {
			deathCause = "¡Atasco mortal! Nuestro héroe no pudo hacer su deber y tuvo un final de mierda.";
		} else {
			deathCause = getActivatedEventDeadCause();
		}
		if (deathCause != null) {
			SoundManager.playSound("audio/sounds/dead.mp3");
			Params.deathCause = deathCause;
			game.setScreen(new DeadScreen(game));
			return true;
		}

		return false;
	}

	public void changeDay() {
		Params.hunger -= Params.HUNGER_LOSS;
		Params.thirst -= Params.THIRST_LOSS;
		Params.sleep -= Params.SLEEP_LOSS;
		Params.poop -= Params.POOP_LOSS;
		Params.exercise -= Params.EXERCISE_LOSS;
		if (checkDead())
			return;

		restartInteractions();
		Params.gameScreen = this;
		game.setScreen(new DayScreen(game));
		activateEvent();
	}

	public ArrayList<MapObject> getRectangleList(String propertyName) {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("Action") && props.get("Action").equals(propertyName)) {
					list.add(obj);
				}
			}
		}
		return list;
	}

	public ArrayList<Polygon> getPolygonList(String propertyName) {

		Polygon poly;
		ArrayList<Polygon> list = new ArrayList<Polygon>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {

				if (!(obj instanceof PolygonMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("Action") && props.get("Action").equals(propertyName)) {

					poly = ((PolygonMapObject) obj).getPolygon();
					list.add(poly);
				}
			}
		}
		return list;
	}

	public ArrayList<MapObject> getEnemyList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("Enemy")) {
					list.add(obj);

				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}
				}
			}
		}
		return list;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		Params.setAltoPantalla(height);
		Params.setAnchoPantalla(width);
		uiStage = new Stage();
		dayProgressBar.setPosition(20, Params.getAltoPantalla() - 64);
		progressBar.setPosition(Params.getAnchoPantalla() / 2 - 128, 80);
		hungerProgress.setPosition(Params.getAnchoPantalla() - 84, Params.getAltoPantalla() - 84);
		thirstProgress.setPosition(Params.getAnchoPantalla() - 84 * 2, Params.getAltoPantalla() - 84);
		exerciseProgress.setPosition(Params.getAnchoPantalla() - 84 * 3, Params.getAltoPantalla() - 84);
		sleepProgress.setPosition(Params.getAnchoPantalla() - 84 * 4, Params.getAltoPantalla() - 84);
		poopProgress.setPosition(Params.getAnchoPantalla() - 84 * 5, Params.getAltoPantalla() - 84);
		key.setPosition(Params.getAnchoPantalla() / 2 - 32, 140);

		camara.setToOrtho(false, width * Params.zoom, height * Params.zoom);
	}
}
