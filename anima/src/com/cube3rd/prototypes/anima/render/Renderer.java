package com.cube3rd.prototypes.anima.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

public class Renderer {
	private ArrayMap<Integer, TextureRegion> textures;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private boolean additiveBlending;
	
	private static Renderer r = null;
	public static ArrayMap<Integer, TextureRegion> textureMap;

	public static void setRenderer(Renderer ren) {
		Renderer.r = ren;
		Renderer.textureMap = ren.textures;
	}
	
	public Renderer(SpriteBatch batch) {
		this.batch = batch;
		textures = new ArrayMap<Integer, TextureRegion>();
		atlas = new TextureAtlas(Gdx.files.internal("anima_textures.pack"));
		
		for (int i = 0; i < Id.resourceCount; i ++) {
			textures.put(Id.ids[i], atlas.findRegion(Id.resources[i]));
			textures.get(Id.ids[i]).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		}
		additiveBlending = false;
		batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
	}
	
	public void dispose() {
		atlas.dispose();
		batch.dispose();
	}
	
	public static void render(Sprite sprite, boolean blend) {
		if (r == null)
			return;
		if (!blend && r.additiveBlending) {
			r.additiveBlending = blend;
			r.batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}
		else if (blend && !r.additiveBlending) {
			r.additiveBlending = blend;
			r.batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		}
		sprite.draw(r.batch);
	}
}
