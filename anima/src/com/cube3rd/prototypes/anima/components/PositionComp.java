package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;
import com.cube3rd.prototypes.anima.math2d.Vector2d;

public class PositionComp extends Component {
	public Vector2d pos;
	
	public PositionComp(double x, double y) {
		pos = Vector2d.create(x, y);
	}
}
