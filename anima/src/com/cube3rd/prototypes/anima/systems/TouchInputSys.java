package com.cube3rd.prototypes.anima.systems;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.MotionLinkComp;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System that processing input from the touchscreen.
 * Touch down on one entity and release on another to link. */
public class TouchInputSys extends EcSystem implements InputProcessor {
	private Camera camera;
	private Vector2d touch, tmp;
	private Vector2d rawTouch, rawPrevTouch;
	private Entity selected;
	private double delta;
	
	private static double STRENGTH = 80;
	private static double STRENGTH_3 = 50;
	private static int CLICK_RANGE = 20;
	private static int MAX_MOVEMENT = 1;

	public TouchInputSys(World w, Camera camera) {
		super(w);
		this.camera = camera;
		touch = Vector2d.create();
		rawTouch = Vector2d.create();
		rawPrevTouch = Vector2d.create();
		tmp = Vector2d.create();
	}


	@Override
	public void update(double delta) {
		this.delta = delta;
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.interactables.hasComponent(e);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		touch = convert(x, y);
		rawPrevTouch.set(x, y);
		double dist = CLICK_RANGE * CLICK_RANGE;
		selected = null;
		
		for (Entity e: world.entities) {
			if (isRelevant(e)) {
				Vector2d pos = world.positions.get(e).pos;
				double newDist = touch.distanceSqr(pos);
				if (newDist < dist) {
					dist = newDist;
					selected = e;
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		touch = convert(x, y);
		if (selected != null) {
			double dist = CLICK_RANGE * CLICK_RANGE;
			Entity closest = null;
			for (Entity e: world.entities) {
				if (isRelevant(e)) {
					Vector2d pos = world.positions.get(e).pos;
					double newDist = touch.distanceSqr(pos);
					if (newDist < dist) {
						dist = newDist;
						closest = e;
					}
				}
			}
			if (closest != null) {
				MotionLinkComp linkA = world.links.get(closest);
				MotionLinkComp linkB = world.links.get(selected);
				linkA.link(selected);
				linkA.link(linkB, closest);
				linkB.link(closest);
				linkB.link(linkA, selected);
			}
			else {
				touch.set(x, y);
				tmp.set(rawPrevTouch, touch).multiply(delta * STRENGTH_3);
				System.out.println(tmp.length());
				if (world.followAi.hasComponent(selected)) {
					MotionLinkComp link = world.links.get(selected);
					tmp.multiply(1d/link.inertia());
					world.velocities.get(selected).vel.add(tmp);
				}
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (selected == null) {
			rawTouch.set(x, y);
			touch = convert(x, y);
			double len = tmp.set(rawPrevTouch, rawTouch).length();
			if (len > MAX_MOVEMENT) len = MAX_MOVEMENT;
			tmp.set(rawPrevTouch, rawTouch).multiply(delta * STRENGTH);
			for (Entity e: world.entities) {
				if (world.controllables.hasComponent(e)) {
					MotionLinkComp link = world.links.get(e);
					tmp.multiply(1d/link.inertia());
					world.velocities.get(e).vel.add(tmp);
				}
			}
			rawPrevTouch.set(rawTouch);
		}
		return true;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	private Vector2d convert(double x, double y) {
		Vector3 tmp = Vector3.tmp;
		tmp.x = (float)x;
		tmp.y = (float)y;
		camera.unproject(tmp);
		touch.set(tmp.x, tmp.y);
		return touch;
	}
}
