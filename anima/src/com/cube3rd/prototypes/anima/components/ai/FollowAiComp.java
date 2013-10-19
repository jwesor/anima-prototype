package com.cube3rd.prototypes.anima.components.ai;

import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.base.Entity;

/* Component holding necessary data for FollowAi*/
public class FollowAiComp extends Component {
	
	public Entity target;	
	public double range;
	public double accel;
	
	private static double DEFAULT_RANGE = 400;
	private static double DEFAULT_ACCEl = 50;
	
	public FollowAiComp() {
		range = DEFAULT_RANGE;
		accel = DEFAULT_ACCEl;
	}
	
	public boolean targetSet() {
		return target != null;
	}
	
	public void setTarget(Entity e) {
		target = e;
	}
}
