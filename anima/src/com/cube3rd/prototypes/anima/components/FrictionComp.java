package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;

public class FrictionComp extends Component {
	public double friction;
	
	public FrictionComp(double f) {
		friction = f;
		if (friction > 1)
			friction = 1;
	}
}
