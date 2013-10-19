package com.cube3rd.prototypes.anima.components.ai;

import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.base.Entity;

/* Ai component for projectile */
public class TargetFastHomingComp extends Component {
	
	public Entity target;
	public double accel;
	
	private static final double DEFAULT_ACCEL = 400;
	
	public TargetFastHomingComp(Entity pos) {
		target = pos;
		accel = DEFAULT_ACCEL;
	}
}
