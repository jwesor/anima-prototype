package com.cube3rd.prototypes.anima.components;

import com.cube3rd.prototypes.anima.base.Component;

public class AlignmentComp extends Component {
	public int alignment;
	
	public AlignmentComp(int a) {
		alignment = a;
	}
	
	public static final int ALLY = 0;
	public static final int ENEMY = 1;
}
