package com.cube3rd.prototypes.anima.base;

public class Entity {
	private int activeMask, mask;
	
	public void disableComponent(int com) {
		if ((com & activeMask) == com)
			activeMask -= com;
	}
	
	public void enableComponent(int com) {
		if ((com & activeMask) != com)
			activeMask += com;
	}
	
	public void addComponent(int com) {
		if ((com & mask) != com) {
			mask += com;
			activeMask += com;
		}
	}
	
	public void removeComponent(int com) {
		if ((com & mask) == com) {
			activeMask -= com;
			mask -= com;
		}
	}
	
	public int getActiveMask() {
		return activeMask;
	}
	
	@Override
	public String toString() {
		return "Entity " + Integer.toBinaryString(mask);
	}
}
