package com.cube3rd.prototypes.anima.base;

public abstract class EcSystem {
	protected World world;
	public EcSystem(World w) {
		world = w;
	}
	public abstract void update(double delta);
	public abstract boolean isRelevant(Entity e);
}
