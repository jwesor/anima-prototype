package com.cube3rd.prototypes.anima.components;

import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.base.Component;

public class ColorComp extends Component  {
	public Color color;
	
	public ColorComp(Color c) {
		color = c;
	}
	
	public ColorComp(double r, double g, double b, double a) {
		this(new Color((float)r, (float)g, (float)b, (float)a));
	}
}
