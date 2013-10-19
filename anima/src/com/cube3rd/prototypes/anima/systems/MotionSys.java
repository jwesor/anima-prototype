package com.cube3rd.prototypes.anima.systems;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

public class MotionSys extends EcSystem {
	private final Vector2d tmp;
	
	public MotionSys(World w) {
		super(w);
		tmp = Vector2d.create();
	}

	@Override
	public void update(double delta) {
		for (Entity e: world.entities) {
			if (isRelevant(e)) {
				Vector2d vel = world.velocities.get(e).vel;
				tmp.set(vel);
				tmp.multiply(delta);
				world.positions.get(e).pos.add(tmp);
				double friction = world.friction.get(e).friction;
				vel.multiply(1 - friction * delta);
			}
		}
	}

	@Override
	public boolean isRelevant(Entity e) {
		return world.positions.hasComponent(e) &&
				world.friction.hasComponent(e) &&
				world.velocities.hasComponent(e);
	}

}
