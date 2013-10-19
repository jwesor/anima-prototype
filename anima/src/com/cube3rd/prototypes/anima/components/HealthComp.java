package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;

public class HealthComp extends Component {
	public int health;
	public HealthComp(int h) {
		health = h;
	}
	
	public boolean damage(int i) {
		health -= i;
		return health > 0;
	}
}
