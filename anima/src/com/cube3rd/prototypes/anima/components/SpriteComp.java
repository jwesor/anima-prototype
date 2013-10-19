package com.cube3rd.prototypes.anima.components;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.math2d.Vector2d;
import com.cube3rd.prototypes.anima.render.Renderer;

public class SpriteComp extends Component {
	private Sprite sprite;
	private boolean additiveBlending;
	private int order;

	public SpriteComp(int texture, boolean blend, int o) {
		sprite = new Sprite(Renderer.textureMap.get(texture));
		additiveBlending = blend;
		order = o;
	}
	
	public void setBounds(Vector2d vec, double w, double h) {
		setBounds(vec.x, vec.y, w, h);
	}
	public void setBounds(double x, double y, double w, double h) {
		float halfw = (float)(w/2);
		float halfh = (float)(h/2);
		sprite.setBounds((float)x-halfw, (float)y-halfh, (float)w, (float)h);
	}
	
	public void setAngle(double a) {
		sprite.setRotation((float)(Math.toDegrees(a)));
	}
	
	public void setColor(Color c) {
		sprite.setColor(c);
	}
	
	public void setBlending(boolean b) {
		additiveBlending = b;
	}
	
	public void render() {
		Renderer.render(sprite, additiveBlending);
	}
	
	public void setOrder(int i) {
		if (i > 0)
			order = i;
	}
	
	public int getOrder() {
		return order;
	}
	
	public static class SpriteOrder implements Comparator<SpriteComp> {
		@Override
		public int compare(SpriteComp a, SpriteComp b) {
			return a.order - b.order;
		}
		
	}
}
