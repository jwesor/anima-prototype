package com.cube3rd.prototypes.anima.systems;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.ai.WanderAiComp;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System that controls the WanderAi, which instructs the entity to 
 * pick random nearby points and move towards them. */
public class WanderAiSys extends EcSystem {
	private Vector2d tmp;
	public WanderAiSys(World w) {
		super(w);
		tmp = Vector2d.create();
	}

	@Override
	public void update(double delta) {
		for (Entity e: world.entities) {
			if (isRelevant(e)) {
				WanderAiComp ai = world.wanderAi.get(e);
				Vector2d pos = world.positions.get(e).pos;
				Vector2d vel = world.velocities.get(e).vel;
				if (ai.reachedTarget(pos)) {
					do {
						ai.setNewTarget(pos);
					} while (world.maze.checkCollision(ai.target));
				}
				tmp.set(pos, ai.target).unit().multiply(ai.wanderSpeed * delta);
				vel.add(tmp);
			}
		}
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.wanderAi.hasComponent(e) &&
				world.positions.hasComponent(e) &&
				world.velocities.hasComponent(e);
	}

}
