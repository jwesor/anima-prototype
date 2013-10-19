package com.cube3rd.prototypes.anima.systems;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.ai.FollowAiComp;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System controls entities with the FollowAi component, 
 * which instructs the entity to pick a nearby target and
 * move towards it.*/

public class FollowAiSys extends EcSystem {
	private final Vector2d tmp;
	public FollowAiSys(World w) {
		super(w);
		tmp = Vector2d.create();
	}

	@Override
	public void update(double delta) {
		int size = world.entities.size();
		for (int i = 0; i < size; i ++) {
			Entity e = world.entities.get(i);
			if (!isRelevant(e))
				continue;
			FollowAiComp ai = world.followAi.get(e);
			Vector2d pos = world.positions.get(e).pos;
			boolean following = ai.targetSet();
			if (following) {
				PositionComp targetPos = world.positions.get(ai.target);
				if (targetPos == null)
					ai.setTarget(null);
				else {
					tmp.set(pos, targetPos.pos);
					if (tmp.lengthSqr() > ai.range * ai.range) {
						following = false;
						ai.setTarget(null);
					}
					else {
						tmp.unit().multiply(ai.accel * delta);
						world.velocities.get(e).vel.add(tmp);
					}
				}
			}
			if (!following) {
				Entity closest = null;
				double dist = ai.range * ai.range;
				for (int j = 0; j < size; j ++) {
					Entity t = world.entities.get(j);
					if (validTarget(t, e)) {
						double closestDist = pos.distanceSqr(world.positions.get(t).pos);
						if (closestDist < dist) {
							dist = ai.range * ai.range;
							closest = t;
						}
					}
				}
				ai.setTarget(closest);
			}
		}
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.followAi.hasComponent(e) &&
				world.positions.hasComponent(e) &&
				world.velocities.hasComponent(e) &&
				world.alignments.hasComponent(e);
	}

	private boolean validTarget(Entity target, Entity source) {
		if (world.alignments.hasComponent(target) && world.positions.hasComponent(target)) {
			return world.alignments.get(target).alignment != world.alignments.get(source).alignment;
		}
		return false;
	}
}
