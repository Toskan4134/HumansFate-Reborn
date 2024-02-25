package game;

import java.util.List;

import com.badlogic.gdx.utils.Array;

import elements.Interaction;
import screens.GameScreen;

public class Params {

//Screen
	// private static int anchoPantalla=1200;
	// private static int altoPantalla=900;

	private static int anchoPantalla = 800;
	private static int altoPantalla = 600;

	public static boolean debug = true;

	// Audio;
	public static float musicVolume = 0.04f;
	public static float soundVolume = 1;

	// public static float zoom=0.24f;
	public static float zoom = 1f;

	// Jugador
	public static float jugadorx = 352;
	public static float jugadory = 576;
	public static int acceleration = 100;
	public static int maxSpeed = 300;
	public static int deceleration = 70;

	// variables de juego
	public static GameScreen gameScreen;
	public static final int MAX_DAYS = 20;
	public static int days;
	public static final float MAX_SECS_PER_ROUND = 45f;
	public static float secsPerRound;
	public static String deathCause;

	public static final int MAX_POINTS = 12;
	public static final int HUNGER_LOSS = 3;
	public static final int THIRST_LOSS = 6;
	public static final int SLEEP_LOSS = 4;
	public static final int EXERCISE_LOSS = 4;
	public static final int POOP_LOSS = 3;
	public static int hunger;
	public static int thirst;
	public static int sleep;
	public static int exercise;
	public static int poop;

	// Minigame
	public static int progressBarCount;
	public static boolean enabledMinigameUI;

	public static int getAnchoPantalla() {
		return anchoPantalla;
	}

	public static void setAnchoPantalla(int anchoPantalla) {
		Params.anchoPantalla = anchoPantalla;
	}

	public static int getAltoPantalla() {
		return altoPantalla;
	}

	public static void setAltoPantalla(int altoPantalla) {
		Params.altoPantalla = altoPantalla;
	}

}
