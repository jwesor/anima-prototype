package com.cube3rd.prototypes.anima.components;

import java.util.HashSet;

import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.base.Entity;

public class MotionLinkComp extends Component {
	public HashSet<Entity> links;
	public double strength;
	public double length;
	
	private static final double DEFAULT_LINK_STRENGTH = 2;
	private static final double DEFAULT_LENGTH = 12;
	
	public MotionLinkComp() {
		links = new HashSet<Entity>();
		strength = DEFAULT_LINK_STRENGTH;
		length = DEFAULT_LENGTH;
	}
	
	public boolean hasLinks() {
		return !links.isEmpty();
	}
	
	public void link(Entity e) {
		links.add(e);
	}
	
	public void link(MotionLinkComp c, Entity e) {
		links.addAll(c.links);
		links.remove(e);
	}
	
	public void unlink(MotionLinkComp c) {
		links.removeAll(c.links);
	}
	
	public int inertia() {
		return 1 + links.size();
	}
}
