package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import game.Demo;
import game.Params;
import managers.ResourceManager;

public class TitleScreen extends BScreen {
    private Table tabla;

    public TitleScreen(Demo game) {
        super(game);

        tabla = new Table();
        tabla.setFillParent(true);

        // Añadir la tabla al stage
        uiStage.addActor(tabla);

        // Crear botón "Jugar"
        TextButton botonJugar = new TextButton("Jugar", ResourceManager.textButtonStyle);
        botonJugar.addListener((Event e) -> {
            if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
                return false;
            this.dispose();
            if (Params.gameScreen == null) {
                game.setScreen(new GameScreen(game));
            } else {
                game.setScreen(Params.gameScreen);
            }
            return false;
        });

        // Crear botón "Opciones"
        TextButton botonOpciones = new TextButton("Opciones", ResourceManager.textButtonStyle);

        // Crear botón "Salir"
        TextButton botonSalir = new TextButton("Salir", ResourceManager.textButtonStyle);
        botonSalir.addListener((Event e) -> {
            if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
                return false;
            this.dispose();
            Gdx.app.exit();
            return false;
        });

        // Agregar los botones a la tabla
        tabla.add(botonJugar).padBottom(20).row();
        tabla.add(botonOpciones).padBottom(20).row();
        tabla.add(botonSalir).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        // Actualizar el tamaño de la pantalla
        Params.setAltoPantalla(height);
        Params.setAnchoPantalla(width);
    }
}