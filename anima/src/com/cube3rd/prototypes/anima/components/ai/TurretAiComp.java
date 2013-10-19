package com.cube3rd.prototypes.anima.components.ai;

import com.cube3rd.prototypes.anima.base.Component;

/* Ai component for turrets. */
public class TurretAiComp extends Component {
	public double range;
	private double cooldown;
	private double currentTime;
	
	private static final double DEFAULT_RANGE = 100;
	private static final double DEFAULT_COOLDOWN = 0.3;
	
	public TurretAiComp() {
		range = DEFAULT_RANGE;
		cooldown = DEFAULT_COOLDOWN;
		currentTime = 0;
	}
	
	public boolean readyToFire(double delta) {
		currentTime += delta;
		return currentTime >= cooldown;
	}
	
	public void fire() {
		currentTime %= cooldown;
	}
	
}
