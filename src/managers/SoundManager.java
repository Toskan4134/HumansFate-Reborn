package managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import game.Params;

public class SoundManager {
	static Music currentMusic;
	static String currentMusicName;
	static Sound sound;

	static public void playMusic(String path) {
		if (currentMusicName != path) {
			currentMusic.stop();
			currentMusicName = path;
			currentMusic = ResourceManager.getMusic(path);
			currentMusic.setVolume(Params.musicVolume);
			currentMusic.setLooping(true);
			currentMusic.play();

		}

	}

	static public void playSound(String path) {

		sound = ResourceManager.getSound(path);
		sound.play(Params.soundVolume);

	}

}
