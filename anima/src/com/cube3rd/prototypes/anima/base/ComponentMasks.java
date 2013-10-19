package com.cube3rd.prototypes.anima.base;

import java.util.HashMap;
import java.util.Map;

import com.cube3rd.prototypes.anima.components.AlignmentComp;
import com.cube3rd.prototypes.anima.components.AngleComp;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.FrictionComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.components.VelocityComp;
import com.cube3rd.prototypes.anima.components.ai.WanderAiComp;

public class ComponentMasks {
	public static final Map<Class<? extends Component>, Integer> masks = generateMasks();

	public static Map<Class<? extends Component>, Integer> generateMasks() {
		Map<Class<? extends Component>, Integer> map = new HashMap<Class<? extends Component>, Integer>();
		map.put(PositionComp.class, 1 << 0);
		map.put(VelocityComp.class, 1 << 1);
		map.put(DimensionComp.class, 1 << 2);
		map.put(AngleComp.class, 1 << 3);
		map.put(ColorComp.class, 1 << 4);
		map.put(SpriteComp.class, 1 << 5);
		map.put(WanderAiComp.class, 1 << 6);
		map.put(FrictionComp.class, 1 << 7);
		map.put(AlignmentComp.class, 1 << 8);
		map.put(TurretAiComp.class, 1 << 9);
		return map;
	}
	
	public static int getMask(Class<? extends Component> key) {
		return masks.get(key);
	}
	
	public static int getMask(Component key) {
		return getMask(key.getClass());
	}
}
