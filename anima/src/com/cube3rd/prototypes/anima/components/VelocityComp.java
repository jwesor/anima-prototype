package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

public class VelocityComp extends Component {

	public Vector2d vel;
	
	public VelocityComp() {
		vel = Vector2d.create();
	}

	public VelocityComp(Vector2d tmp) {
		vel = tmp.copy();
	}
}
