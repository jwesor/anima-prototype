package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;

public class DurationComp extends Component {
	public double duration;
	public DurationComp(double d) {
		duration = d;
	}
	
	public boolean count(double d) {
		duration -= d;
		return duration > 0;
	}
}
