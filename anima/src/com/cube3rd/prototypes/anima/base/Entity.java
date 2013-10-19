package com.cube3rd.prototypes.anima.base;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	private int activeMask, mask;
	
	private final List<EcManager<? extends Component>> managers;
	
	public Entity() {
		managers = new ArrayList<EcManager<? extends Component>>();
	}
	
	protected static boolean hasComponent(int mask, int com) {
		return (com & mask) == com;
	}
	
	public void disableComponent(int com) {
		if (hasComponent(activeMask, com))
			activeMask -= com;
	}
	
	public void enableComponent(int com) {
		if (!hasComponent(activeMask, com))
			activeMask += com;
	}
	
	public void addComponent(int com, EcManager<? extends Component> ecm) {
		if (!hasComponent(mask, com)) {
			mask += com;
			activeMask += com;
			managers.add(ecm);
		}
	}
	
	public void removeComponent(int com, EcManager<? extends Component> ecm) {
		if (hasComponent(mask, com)) {
			activeMask -= com;
			mask -= com;
			managers.remove(ecm);
		}
	}
	
	public void delete() {
		while (!managers.isEmpty())
			managers.get(0).removeComponentFromEntity(this);
	}
	
	public int getActiveMask() {
		return activeMask;
	}
	
	@Override
	public String toString() {
		return "Entity " + Integer.toBinaryString(mask);
	}
}
