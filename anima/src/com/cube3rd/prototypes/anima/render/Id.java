package com.cube3rd.prototypes.anima.render;

public class Id {
	public static final int soul = 10;
	public static final int color_overlay = 11;
	
	public static final String soul_s = "soul";
	public static final String color_overlay_s = "color_overlay";
	
	public static final int[] ids;
	public static final String[] resources;
	public static final int resourceCount = 2;
	static {
		ids = new int[resourceCount];
		ids[0] = soul;
		ids[1] = color_overlay;
		
		resources = new String[resourceCount];
		resources[0] = soul_s;
		resources[1] = color_overlay_s;
	}
}
