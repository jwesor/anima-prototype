package com.cube3rd.prototypes.anima.systems;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.ai.TargetFastHomingComp;
import com.cube3rd.prototypes.anima.factories.SoulFactory;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System that controls the lifetime of projectiles filed by the Turret Ai.
 * Moves towards a predetermined target, and will delete itself upon collision.
 * The target's HealthComponent will be modified to affect damage, and maybe 
 * be destroyed as well. */

public class TargetFastHomingSys extends EcSystem {
	
	private final Vector2d tmp;
	private final SoulFactory factory;
	
	public TargetFastHomingSys(World w, SoulFactory f) {
		super(w);
		tmp = Vector2d.create();
		factory = f;
	}

	@Override
	public void update(double delta) {
		for (int i = 0; i < world.entities.size(); i ++) {
			Entity e = world.entities.get(i);
			if (!isRelevant(e))
				continue;
			if (!world.durations.get(e).count(delta)) {
				e.delete();
				continue;
			}
			TargetFastHomingComp target = world.fastHomingTargets.get(e);
			if (validTarget(target.target)) {
				Vector2d pos = world.positions.get(e).pos;
				Vector2d vel = world.velocities.get(e).vel;
				Vector2d targetPos = world.positions.get(target.target).pos;
				double targetDist = pos.distanceSqr(targetPos);
				double travelDist = vel.lengthSqr() * delta * delta;
				if (targetDist < travelDist || (world.dimensions.hasComponent(target.target) &&
						targetDist < Math.pow(world.dimensions.get(target.target).shorter() * 0.5, 2))) {
					if (!world.healths.get(target.target).damage(world.damages.get(e).damage)) {
						Vector2d loc = world.positions.get(target.target).pos;
						if (Math.random() > 0.5)
							factory.createMobileSoul(loc.x, loc.y, World.ALLY_COLOR_A);
						else
							factory.createTurretSoul(loc.x, loc.y, World.ALLY_COLOR_B);
						target.target.delete();
					}
					e.delete();
				}
				else {
					tmp.set(pos, targetPos).unit().multiply(target.accel * delta);
					vel.add(tmp);
				}
			}
		}
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.fastHomingTargets.hasComponent(e) &&
				world.positions.hasComponent(e) &&
				world.velocities.hasComponent(e) && 
				world.durations.hasComponent(e) &&
				world.damages.hasComponent(e);
	}
	
	private boolean validTarget(Entity e) {
		return world.positions.hasComponent(e);
	}
}
