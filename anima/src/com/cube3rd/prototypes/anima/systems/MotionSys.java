package com.cube3rd.prototypes.anima.systems;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System that controls the motion of all objects based on their velocities.
 * Also regulates collisions with maze walls. */
public class MotionSys extends EcSystem {
	private final Vector2d tmp, tmp2;
	
	private static final double ENERGY_LOSS_COLLISION = 0.5;
	public MotionSys(World w) {
		super(w);
		tmp = Vector2d.create();
		tmp2 = Vector2d.create();
	}

	@Override
	public void update(double delta) {
		for (Entity e: world.entities) {
			if (isRelevant(e)) {
				Vector2d vel = world.velocities.get(e).vel;
				tmp.set(vel);
				tmp.multiply(delta);
				/*if (world.links.hasComponent(e)) {
					MotionLinkComp link = world.links.get(e);
					tmp.multiply(1d / link.inertia());
					for (Entity e2: link.links) {
						if (world.positions.hasComponent(e2)) {
							world.positions.get(e2).pos.add(tmp);
						}
					}
				}
				else*/
				Vector2d pos = world.positions.get(e).pos;
				tmp2.set(pos).add(tmp.x, 0);
				boolean xCol = world.maze.checkCollision(tmp2);
				tmp2.set(pos).add(0, tmp.y);
				boolean yCol = world.maze.checkCollision(tmp2);
				if (xCol) {
					vel.x = -vel.x * ENERGY_LOSS_COLLISION;
					tmp.x = 0;
				}
				if (yCol) {
					vel.y = -vel.y * ENERGY_LOSS_COLLISION;
					tmp.y = 0;
				}
				pos.add(tmp);
				if (world.friction.hasComponent(e)) {
					double friction = world.friction.get(e).friction;
					vel.multiply(1 - friction * delta);
				}
			}
		}
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.positions.hasComponent(e) &&
				world.velocities.hasComponent(e);
	}

}
