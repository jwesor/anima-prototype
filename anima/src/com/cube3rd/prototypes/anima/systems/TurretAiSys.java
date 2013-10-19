package com.cube3rd.prototypes.anima.systems;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.ai.TurretAiComp;
import com.cube3rd.prototypes.anima.factories.ProjectileFactory;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System controls entities with TurretAi component, which instructs
 * the entity to produce projectile entities when near a target. */
public class TurretAiSys extends EcSystem {
	private final ProjectileFactory factory;
	
	public TurretAiSys(World w, ProjectileFactory fac) {
		super(w);
		factory = fac;
	}

	@Override
	public void update(double delta) {
		int size = world.entities.size();
		for (int i = 0; i < size; i ++) {
			Entity e = world.entities.get(i);
			if (!isRelevant(e))
				continue;
			TurretAiComp ai = world.turretAi.get(e);
			if (!ai.readyToFire(delta))
				continue;
			double dist = Math.pow(ai.range, 2);
			Vector2d position = world.positions.get(e).pos;
			Entity closest = null;
			for (int j = 0; j < size; j ++) {
				Entity t = world.entities.get(j);
				if (i != j && validTarget(t, e)) {
					Vector2d current = world.positions.get(t).pos;
					double newDist = position.distanceSqr(current);
					if (newDist < dist) {
						newDist = dist;
						closest = t;
					}
				}
			}
			if (closest != null) {
				ai.fire();
				factory.createFastHomingProjectile(position.x, position.y, closest);
			}
		}
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.turretAi.hasComponent(e) &&
				world.positions.hasComponent(e) &&
				world.alignments.hasComponent(e);
	}
	
	private boolean validTarget(Entity target, Entity source) {
		if (world.alignments.hasComponent(target) && world.positions.hasComponent(target)) {
			return world.alignments.get(target).alignment != world.alignments.get(source).alignment;
		}
		return false;
	}
}
