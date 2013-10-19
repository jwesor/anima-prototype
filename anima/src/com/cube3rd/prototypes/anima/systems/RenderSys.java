package com.cube3rd.prototypes.anima.systems;

import java.util.PriorityQueue;

import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.base.EcSystem;
import com.cube3rd.prototypes.anima.base.Entity;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;

/* System regulates and organizes the rendering of all sprites in specific order. */
public class RenderSys extends EcSystem {
	private PriorityQueue<SpriteComp> order;
	public RenderSys(World w) {
		super(w);
		order = new PriorityQueue<SpriteComp>(64, new SpriteComp.SpriteOrder());
	}

	@Override
	public void update(double delta) {
		for (Entity e: world.entities) {
			if (isRelevant(e)) {
				SpriteComp sprite = world.sprites.get(e);
				DimensionComp dimen = world.dimensions.get(e);
				sprite.setBounds(world.positions.get(e).pos, dimen.width, dimen.height);
				if (world.angles.hasComponent(e))
					sprite.setAngle(world.angles.get(e).degrees());
				if (world.colors.hasComponent(e)) {
					sprite.setColor(world.colors.get(e).color);
				}
				else
					sprite.setColor(Color.WHITE);
				order.add(sprite);
			}
		}
		while (!order.isEmpty())
			order.poll().render();
	}
	
	
	@Override
	protected boolean isRelevant(Entity e) {
		return world.sprites.hasComponent(e) &&
				world.positions.hasComponent(e) &&
				world.dimensions.hasComponent(e);
	}

}
