package com.cube3rd.prototypes.anima.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.components.AlignmentComp;
import com.cube3rd.prototypes.anima.components.AngleComp;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.FrictionComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.components.VelocityComp;
import com.cube3rd.prototypes.anima.components.ai.WanderAiComp;
import com.cube3rd.prototypes.anima.render.Id;
import com.cube3rd.prototypes.anima.souls.EnemyFactory;
import com.cube3rd.prototypes.anima.souls.SoulFactory;
import com.cube3rd.prototypes.anima.systems.MotionSys;
import com.cube3rd.prototypes.anima.systems.RenderSys;
import com.cube3rd.prototypes.anima.systems.WanderAiSys;


public class World {
	public final List<Entity> entities;
	public final EcManager<PositionComp> positions;
	public final EcManager<VelocityComp> velocities;
	public final EcManager<DimensionComp> dimensions;
	public final EcManager<AngleComp> angles;
	public final EcManager<ColorComp> colors;
	public final EcManager<SpriteComp> sprites;
	public final EcManager<FrictionComp> friction;
	public final EcManager<WanderAiComp> wanderAi;
	public final EcManager<AlignmentComp> alignments;
	
	private final MotionSys motionSys;
	private final WanderAiSys wanderAiSys;
	private final RenderSys renderSys;
	
	private final EnemyFactory enemyFactory;
	private final SoulFactory soulFactory;
	
	private static final Color BACKGROUND_COLOR = new Color(0.2f, 0.2f, 0.3f, 1f);
	public static final double ENEMY_SIZE = 24;
	public static final double ALLY_SIZE = 12;
	public static final Color ENEMY_COLOR = new Color(1f, 0.5f, 0.25f, 1f);
	public static final Color ALLY_COLOR = new Color(0.25f, 0.5f, 1f, 1f);
	
	public World() {
		entities = new ArrayList<Entity>();
		positions = new EcManager<PositionComp>(PositionComp.class);
		velocities = new EcManager<VelocityComp>(VelocityComp.class);
		sprites = new EcManager<SpriteComp>(SpriteComp.class);
		dimensions = new EcManager<DimensionComp>(DimensionComp.class);
		angles = new EcManager<AngleComp>(AngleComp.class);
		colors = new EcManager<ColorComp>(ColorComp.class);
		friction = new EcManager<FrictionComp>(FrictionComp.class);
		wanderAi = new EcManager<WanderAiComp>(WanderAiComp.class);
		alignments = new EcManager<AlignmentComp>(AlignmentComp.class);
		
		motionSys = new MotionSys(this);
		wanderAiSys = new WanderAiSys(this);
		renderSys = new RenderSys(this);
		
		createBackground();
		
		enemyFactory = new EnemyFactory(this);
		enemyFactory.createWanderEnemy(240, 160, ENEMY_COLOR);
		
		soulFactory = new SoulFactory(this);
		soulFactory.createBaseSoul(240, 100, ALLY_COLOR);
	}
	
	public void update(double delta) {
		motionSys.update(delta);
		wanderAiSys.update(delta);
		renderSys.update(delta);
	}
	
	private void createBackground() {
		Entity background = new Entity();
		positions.addComponentToEntity(background, new PositionComp(240, 160));
		dimensions.addComponentToEntity(background, new DimensionComp(480, 320));
		colors.addComponentToEntity(background, new ColorComp(BACKGROUND_COLOR));
		sprites.addComponentToEntity(background, new SpriteComp(Id.color_overlay, false, 0));
		entities.add(background);
	}
	
}
