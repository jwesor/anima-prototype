package com.cube3rd.prototypes.anima.factories;

import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.EntityFactory;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DamageComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.DurationComp;
import com.cube3rd.prototypes.anima.components.FrictionComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.components.VelocityComp;
import com.cube3rd.prototypes.anima.components.ai.TargetFastHomingComp;
import com.cube3rd.prototypes.anima.math2d.Math2d;
import com.cube3rd.prototypes.anima.math2d.Vector2d;
import com.cube3rd.prototypes.anima.render.Id;

/* Projectiles.*/
public class ProjectileFactory extends EntityFactory {
	private Vector2d tmp;
	public ProjectileFactory(World w) {
		super(w);
		tmp = Vector2d.create();
	}
	
	public Entity createFastHomingProjectile(double x, double y, Entity target) {
		Entity e = new Entity();
		world.entities.add(e);
		world.positions.addComponentToEntity(e, new PositionComp(x, y));
		tmp.setPolar(20, Math.random() * Math2d.tau);
		world.velocities.addComponentToEntity(e, new VelocityComp(tmp));
		world.friction.addComponentToEntity(e, new FrictionComp(0.5));
		world.dimensions.addComponentToEntity(e, new DimensionComp(World.PROJECTILE_SIZE, World.PROJECTILE_SIZE));
		world.colors.addComponentToEntity(e, new ColorComp(World.PROJECTILE_COLOR));
		world.sprites.addComponentToEntity(e, new SpriteComp(Id.soul, true, 2));
		world.fastHomingTargets.addComponentToEntity(e, new TargetFastHomingComp(target));
		world.durations.addComponentToEntity(e, new DurationComp(3));
		world.damages.addComponentToEntity(e, new DamageComp(1));
		return e;
	}
}
