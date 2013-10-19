package com.cube3rd.prototypes.anima.factories;

import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.EntityFactory;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.AlignmentComp;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.FrictionComp;
import com.cube3rd.prototypes.anima.components.HealthComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.components.VelocityComp;
import com.cube3rd.prototypes.anima.components.ai.WanderAiComp;
import com.cube3rd.prototypes.anima.render.Id;

/* Ai-controlled "enemy" units*/
public class EnemyFactory extends EntityFactory {

	public EnemyFactory(World w) {
		super(w);
	}
	
	public Entity createBaseEnemy(double x, double y, Color col) {
		Entity e = new Entity();
		world.entities.add(e);
		world.positions.addComponentToEntity(e, new PositionComp(x, y));
		world.dimensions.addComponentToEntity(e, new DimensionComp(World.ENEMY_SIZE, World.ENEMY_SIZE));
		world.colors.addComponentToEntity(e, new ColorComp(World.ENEMY_COLOR));
		world.sprites.addComponentToEntity(e, new SpriteComp(Id.soul, true, 4));
		world.alignments.addComponentToEntity(e, new AlignmentComp(AlignmentComp.ENEMY));
		world.healths.addComponentToEntity(e, new HealthComp(15));
		return e;
	}
	
	public Entity createWanderEnemy(double x, double y, Color col) {
		Entity e = createBaseEnemy(x, y, col);
		world.velocities.addComponentToEntity(e, new VelocityComp());
		world.friction.addComponentToEntity(e, new FrictionComp(0.3));
		world.wanderAi.addComponentToEntity(e, new WanderAiComp());
		return e;
	}
}
