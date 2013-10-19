package com.cube3rd.prototypes.anima.systems;

import java.util.HashSet;

import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.MotionLinkComp;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

/* System that controls the attractive force between linked entities.*/
public class LinkAttractionSys extends EcSystem {
	private HashSet<Entity> processed;
	private Vector2d ave, tmp2;

	public LinkAttractionSys(World w) {
		super(w);
		processed = new HashSet<Entity>();
		ave = Vector2d.create();
		tmp2 = Vector2d.create();
	}

	@Override
	public void update(double delta) {
		processed.clear();
		for (Entity e: world.entities) {
			if (!isRelevant(e) || processed.contains(e) || world.controllables.hasComponent(e))
				continue;
			MotionLinkComp link = world.links.get(e);
			if (!link.hasLinks())
				continue;
			ave.set(0, 0);
			processed.add(e);
			for (Entity entity: link.links) {
				if (world.positions.hasComponent(entity))
					ave.add(world.positions.get(entity).pos);
			}
			ave.multiply(1d / link.links.size());
			Vector2d pos = world.positions.get(e).pos;
			double length = tmp2.set(pos, ave).length();
			double displacement = (length - link.length);
			if (displacement < 0) displacement *= -displacement;
			tmp2.unit().multiply(link.strength * delta * displacement);
			world.velocities.get(e).vel.add(tmp2);
		}
	}

	@Override
	protected boolean isRelevant(Entity e) {
		return world.links.hasComponent(e) &&
				world.positions.hasComponent(e) &&
				world.velocities.hasComponent(e);
	}

}
