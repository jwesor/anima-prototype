package com.cube3rd.prototypes.anima.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.cube3rd.prototypes.anima.Maze;
import com.cube3rd.prototypes.anima.components.AlignmentComp;
import com.cube3rd.prototypes.anima.components.AngleComp;
import com.cube3rd.prototypes.anima.components.ColorComp;
import com.cube3rd.prototypes.anima.components.DamageComp;
import com.cube3rd.prototypes.anima.components.DimensionComp;
import com.cube3rd.prototypes.anima.components.DirectControlComp;
import com.cube3rd.prototypes.anima.components.DurationComp;
import com.cube3rd.prototypes.anima.components.FrictionComp;
import com.cube3rd.prototypes.anima.components.HealthComp;
import com.cube3rd.prototypes.anima.components.InputComp;
import com.cube3rd.prototypes.anima.components.MotionLinkComp;
import com.cube3rd.prototypes.anima.components.PositionComp;
import com.cube3rd.prototypes.anima.components.SpriteComp;
import com.cube3rd.prototypes.anima.components.VelocityComp;
import com.cube3rd.prototypes.anima.components.ai.FollowAiComp;
import com.cube3rd.prototypes.anima.components.ai.TargetFastHomingComp;
import com.cube3rd.prototypes.anima.components.ai.TurretAiComp;
import com.cube3rd.prototypes.anima.components.ai.WanderAiComp;
import com.cube3rd.prototypes.anima.factories.EnemyFactory;
import com.cube3rd.prototypes.anima.factories.ProjectileFactory;
import com.cube3rd.prototypes.anima.factories.SoulFactory;
import com.cube3rd.prototypes.anima.math2d.Vector2d;
import com.cube3rd.prototypes.anima.render.Id;
import com.cube3rd.prototypes.anima.systems.FollowAiSys;
import com.cube3rd.prototypes.anima.systems.LinkAttractionSys;
import com.cube3rd.prototypes.anima.systems.MotionSys;
import com.cube3rd.prototypes.anima.systems.RenderSys;
import com.cube3rd.prototypes.anima.systems.TargetFastHomingSys;
import com.cube3rd.prototypes.anima.systems.TouchInputSys;
import com.cube3rd.prototypes.anima.systems.TurretAiSys;
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
	public final EcManager<AlignmentComp> alignments;
	public final EcManager<WanderAiComp> wanderAi;
	public final EcManager<TurretAiComp> turretAi;
	public final EcManager<TargetFastHomingComp> fastHomingTargets;
	public final EcManager<FollowAiComp> followAi;
	public final EcManager<MotionLinkComp> links;
	public final EcManager<InputComp> interactables;
	public final EcManager<DirectControlComp> controllables;
	public final EcManager<DamageComp> damages;
	public final EcManager<HealthComp> healths;
	public final EcManager<DurationComp> durations;
	
	private final MotionSys motionSys;
	private final WanderAiSys wanderAiSys;
	private final TurretAiSys turretAiSys;
	private final FollowAiSys followAiSys;
	private final RenderSys renderSys;
	private final TargetFastHomingSys fastHomingSys;
	private final LinkAttractionSys linkAttSys;
	
	private final TouchInputSys inputSys;
	
	private final EnemyFactory enemyFactory;
	private final SoulFactory soulFactory;
	private final ProjectileFactory projectileFactory;
	
	public final Vector2d camCenter, lastCam;
	
	public final Camera camera;
	public final Maze maze;
	
	public static final Color BACKGROUND_COLOR = new Color(0.2f, 0.2f, 0.3f, 1f);
	public static final double ENEMY_SIZE = 24;
	public static final double ALLY_SIZE = 12;
	public static final double PLAYER_SIZE = 18;
	public static final double PROJECTILE_SIZE = 6;
	public static final Color ENEMY_COLOR = new Color(1f, 0.5f, 0.25f, 1f);
	public static final Color ALLY_COLOR_A = new Color(0.25f, 0.5f, 1f, 1f);
	public static final Color ALLY_COLOR_B = new Color(0.25f, 0f, 0.5f, 1f);
	public static final Color PLAYER_COLOR = new Color(0.25f, 1f, 1f, 1f);
	public static final Color PROJECTILE_COLOR = new Color(1, 1, 1, 1);
	
	
	public World(Camera camera) {
		this.camera = camera;
		entities = new ArrayList<Entity>();
		positions = new EcManager<PositionComp>(PositionComp.class);
		velocities = new EcManager<VelocityComp>(VelocityComp.class);
		sprites = new EcManager<SpriteComp>(SpriteComp.class);
		dimensions = new EcManager<DimensionComp>(DimensionComp.class);
		angles = new EcManager<AngleComp>(AngleComp.class);
		colors = new EcManager<ColorComp>(ColorComp.class);
		friction = new EcManager<FrictionComp>(FrictionComp.class);
		alignments = new EcManager<AlignmentComp>(AlignmentComp.class);
		wanderAi = new EcManager<WanderAiComp>(WanderAiComp.class);
		turretAi = new EcManager<TurretAiComp>(TurretAiComp.class);
		fastHomingTargets = new EcManager<TargetFastHomingComp>(TargetFastHomingComp.class);
		followAi = new EcManager<FollowAiComp>(FollowAiComp.class);
		links = new EcManager<MotionLinkComp>(MotionLinkComp.class);
		interactables = new EcManager<InputComp>(InputComp.class);
		controllables = new EcManager<DirectControlComp>(DirectControlComp.class);
		damages = new EcManager<DamageComp>(DamageComp.class);
		healths = new EcManager<HealthComp>(HealthComp.class);
		durations = new EcManager<DurationComp>(DurationComp.class);
		
		enemyFactory = new EnemyFactory(this);
		soulFactory = new SoulFactory(this);
		projectileFactory = new ProjectileFactory(this);
		

		motionSys = new MotionSys(this);
		wanderAiSys = new WanderAiSys(this);
		turretAiSys = new TurretAiSys(this, projectileFactory);
		followAiSys = new FollowAiSys(this);
		renderSys = new RenderSys(this);
		fastHomingSys = new TargetFastHomingSys(this, soulFactory);
		linkAttSys = new LinkAttractionSys(this);
		inputSys = new TouchInputSys(this, camera);
		
		Gdx.input.setInputProcessor(inputSys);
		
		
		lastCam = Vector2d.create(240, 160);
		maze = new Maze(100, 100);
		Vector2d start = maze.getStartLocation();
		Entity player = soulFactory.createPlayerSoul(start.x, start.y, PLAYER_COLOR);
		camCenter = positions.get(player).pos;
		maze.generateEnemies(enemyFactory, 1);

		enemyFactory.createWanderEnemy(camCenter.x, camCenter.y, World.ENEMY_COLOR);
		soulFactory.createTurretSoul(start.x, start.y, ALLY_COLOR_B);
	}
	
	public void update(double delta) {
		motionSys.update(delta);
		wanderAiSys.update(delta);
		turretAiSys.update(delta);
		followAiSys.update(delta);
		fastHomingSys.update(delta);
		linkAttSys.update(delta);
		
		camera.translate((float)(camCenter.x - lastCam.x), (float)(camCenter.y - lastCam.y), 0);
		lastCam.set(camCenter);
		maze.render(camCenter);
		renderSys.update(delta);
		
		inputSys.update(delta);
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
