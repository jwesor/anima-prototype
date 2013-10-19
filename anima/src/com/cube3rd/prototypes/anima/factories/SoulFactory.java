package com.cube3rd.prototypes.anima.factories;

import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.EntityFactory;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.AlignmentComp;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.DirectControlComp;
import com.cube3rd.prototypes.anima.components.FrictionComp;
import com.cube3rd.prototypes.anima.components.InputComp;
import com.cube3rd.prototypes.anima.components.MotionLinkComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.components.VelocityComp;
import com.cube3rd.prototypes.anima.components.ai.FollowAiComp;
import com.cube3rd.prototypes.anima.components.ai.TurretAiComp;
import com.cube3rd.prototypes.anima.render.Id;

/* AI-controlled linkable units.*/
public class SoulFactory extends EntityFactory {

	public SoulFactory(World w) {
		super(w);
	}
	
	public Entity core(double x, double y, double size, Color col) {
		Entity e = new Entity();
		world.entities.add(e);
		world.positions.addComponentToEntity(e, new PositionComp(x, y));
		world.velocities.addComponentToEntity(e, new VelocityComp());
		world.friction.addComponentToEntity(e, new FrictionComp(0.8));
		world.dimensions.addComponentToEntity(e, new DimensionComp(size, size));
		world.colors.addComponentToEntity(e, new ColorComp(col));
		world.sprites.addComponentToEntity(e, new SpriteComp(Id.soul, true, 1));
		world.alignments.addComponentToEntity(e, new AlignmentComp(AlignmentComp.ALLY));
		world.links.addComponentToEntity(e, new MotionLinkComp());
		return e;
	}
	
	public Entity createPlayerSoul(double x, double y, Color col) {
		Entity e = core(x, y, World.PLAYER_SIZE, col);
		world.interactables.addComponentToEntity(e, new InputComp());
		world.controllables.addComponentToEntity(e, new DirectControlComp());
		return e;
	}
	
	public Entity createComponentSoul(double x, double y, Color col) {
		Entity e = core(x, y, World.ALLY_SIZE, col);
		world.interactables.addComponentToEntity(e, new InputComp());
		return e;
	}
	
	public Entity createTurretSoul(double x, double y, Color col) {
		Entity e = createComponentSoul(x, y, col);
		world.turretAi.addComponentToEntity(e, new TurretAiComp());
		return e;
	}
	
	public Entity createMobileSoul(double x, double y, Color col) {
		Entity e  = createComponentSoul(x, y, col);
		world.followAi.addComponentToEntity(e, new FollowAiComp());
		return e;
	}
	
}
