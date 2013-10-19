package com.cube3rd.prototypes.anima.components.ai;

import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.math2d.Math2d;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

public class WanderAiComp extends Component {
	private double tolerance;
	private double seekDist;
	public Vector2d target;
	private boolean initial;
	public double wanderSpeed;
	
	private static final double DEFAULT_TOLERANCE = 20;
	private static final double DEFAULT_SEEK_DIST = 100;
	private static final double DEFAULT_WANDER_SPEED = 30;
	
	public WanderAiComp() {
		tolerance = DEFAULT_TOLERANCE;
		seekDist = DEFAULT_SEEK_DIST;
		initial = true;
		wanderSpeed = DEFAULT_WANDER_SPEED;
		target = Vector2d.create();
	}
	
	public boolean reachedTarget(Vector2d pos) {
		if (initial) {
			initial = false;
			return true;
		}
		return pos.distanceSqr(target) < tolerance * tolerance;
	}
	
	
	public void setNewTarget(Vector2d pos) {
		double angle = Math.random() * Math2d.tau;
		target.set(pos).addPolar(seekDist, angle);
	}
}
