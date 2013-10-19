package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;

public class AngleComp extends Component {
	private double angle;
	
	private double TAU = Math.PI * 2;
	public AngleComp(double a) {
		setAngle(a);
	}
	
	public void setAngle(double a) {
		angle = a;
		while (angle < 0)
			angle += TAU;
		while (angle >= TAU)
			angle -= TAU;
	}
	
	public double degrees() {
		return Math.toDegrees(angle);
	}
	
	public double angle() {
		return angle;
	}
}
