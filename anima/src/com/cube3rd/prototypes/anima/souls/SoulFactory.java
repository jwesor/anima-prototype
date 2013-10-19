package com.cube3rd.prototypes.anima.souls;

import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.EntityFactory;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.AlignmentComp;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.render.Id;

public class SoulFactory extends EntityFactory {

	public SoulFactory(World w) {
		super(w);
	}
	
	public Entity createBaseSoul(double x, double y, Color col) {
		Entity e = new Entity();
		world.entities.add(e);
		world.positions.addComponentToEntity(e, new PositionComp(x, y));
		world.dimensions.addComponentToEntity(e, new DimensionComp(World.ALLY_SIZE, World.ALLY_SIZE));
		world.colors.addComponentToEntity(e, new ColorComp(World.ALLY_COLOR));
		world.sprites.addComponentToEntity(e, new SpriteComp(Id.soul, true, 1));
		world.alignments.addComponentToEntity(e, new AlignmentComp(AlignmentComp.ALLY));
		return e;
	}
	
	public Entity createTurretSoul(double x, double y, Color col) {
		Entity e = createBaseSoul(x, y, col);
		return e;
	}
	
}
