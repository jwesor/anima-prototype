package com.cube3rd.prototypes.anima.base;

import java.util.HashMap;
import java.util.Map;

public class EcManager<T extends Component> {
	
	private final Map<Entity, T> map;
	public final int mask;
	
	public EcManager(Class<T> type) {
		map = new HashMap<Entity, T>();
		mask = ComponentMasks.getMask(type);
	}
	
	public T get(Entity e) {
		return map.get(e);
	}
	
	public boolean hasComponent(Entity e) {
		return (e.getActiveMask() & mask) == mask;
	}
	
	public void removeComponentFromEntity(Entity t) {
		map.remove(t);
		t.removeComponent(mask);
	}
	
	public void addComponentToEntity(Entity t, T comp) {
		map.put(t, comp);
		t.addComponent(mask);
	}
	
	public void disableEntityComponent(Entity t) {
		t.disableComponent(mask);
	}
	
	public void enableEntityComponent(Entity t) {
		t.enableComponent(mask);
	}
}