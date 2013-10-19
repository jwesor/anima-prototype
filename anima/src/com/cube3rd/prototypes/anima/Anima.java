package com.cube3rd.prototypes.anima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.render.Renderer;

public class Anima extends Game {
	private OrthographicCamera camera;
    private Rectangle viewport;
	private SpriteBatch batch;
	
	private AnimaScreen screen;
	private Renderer render;
	private World world;

	public static final int VIRTUAL_WIDTH = 480;
    public static final int VIRTUAL_HEIGHT = 320;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(true, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		
		batch = new SpriteBatch();
		render = new Renderer(batch);
		Renderer.setRenderer(render);

		world = new World();	
	}

	@Override
	public void dispose() {
		render.dispose();
	}

	@Override
	public void render() {
		camera.update();
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.update(Gdx.graphics.getDeltaTime());
		batch.end();
		//getScreen().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }
 
        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
