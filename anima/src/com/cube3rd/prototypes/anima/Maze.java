package com.cube3rd.prototypes.anima;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cube3rd.prototypes.anima.base.World;
import com.cube3rd.prototypes.anima.factories.EnemyFactory;
import com.cube3rd.prototypes.anima.math2d.Vector2d;
import com.cube3rd.prototypes.anima.render.Id;
import com.cube3rd.prototypes.anima.render.Renderer;

/* This ugly mess of code generates a 2d "maze", represented by a 2d array
 * of booleans that are false where the maze is a wall and true elsewhere.
 * 
 * Generation works by picking several random spots on an all-wall grid,
 * clearing out rectangles around then, then connecting the rectangles with
 * corridors.
 * */
public class Maze {
	private boolean[][] cells;
	private int[] roomX, roomY, roomW, roomH;
	int width, height;
	Sprite sprite;
	static int ROOMS = 12;
	static int ROOM_MIN_SIZE = 15;
	static int ROOM_MAX_SIZE = 35;

	public static final int EXPAND = 15; //30 per cell
	public static final int MAZE_SIZE = 100;
	
	public static final int ROOMS_TO_DRAW = 20;
	

	public void render(Vector2d loc) {
		int xS = (int)(loc.x / EXPAND);
		int yS = (int)(loc.y / EXPAND);
		for (int i = xS - ROOMS_TO_DRAW; i < xS + ROOMS_TO_DRAW; i ++) {
			for (int j = yS - ROOMS_TO_DRAW; j < yS + ROOMS_TO_DRAW; j ++) {
				if (i < width && i >= 0 && j < height && j >= 0) {
					Color c = cells[i][j] ? World.BACKGROUND_COLOR : Color.BLACK;
					sprite.setColor(c);
					sprite.setBounds(i * EXPAND, j * EXPAND, EXPAND, EXPAND);
					Renderer.render(sprite, false);
				}
			}
		}
	}
	
	/* Constructs the randomly generated maze. */
	public Maze(int w, int h) {
		sprite = new Sprite(Renderer.textureMap.get(Id.color_overlay));
		width = w;
		height = h;
		cells = new boolean[w][h];
		cells = new boolean[w][h];
		
		//Generate room centers
		roomX = new int[ROOMS];
		roomY = new int[ROOMS];
		for (int i = 0; i < ROOMS; i ++) {
			roomX[i] = (int)(Math.random() * (w - 3)) + 2;
			roomY[i] = (int)(Math.random() * (h - 3)) + 2;
		}
		
		roomW = new int[ROOMS];
		roomH = new int[ROOMS];
		for (int i = 0; i < ROOMS; i ++) {
			roomW[i] = (int)(Math.random() * (ROOM_MAX_SIZE - ROOM_MIN_SIZE)) + ROOM_MIN_SIZE;
			roomH[i] = (int)(Math.random() * (ROOM_MAX_SIZE - ROOM_MIN_SIZE)) + ROOM_MIN_SIZE;
		}
		
		for (int i = 0; i < ROOMS; i ++) {
			for (int j = -roomW[i]/2; j < roomW[i]/2; j ++) {
				for (int k = -roomH[i]/2; k < roomH[i]/2; k ++) {
					if ( (roomX[i] + j) > 1 && (roomX[i] + j) < w - 2 &&
							(roomY[i] + k) > 1 && (roomY[i] + k) < h - 2)
						cells[roomX[i] + j][roomY[i] + k] = true;
				}
			}
		}
		
		//Connect rooms
		boolean[] done = new boolean[ROOMS];
		int[] order = new int[ROOMS];
		for (int i = 0; i < ROOMS - 1; i ++) {
			int x = roomX[i];
			int y = roomY[i];
			int dist = -1, index = -1;
			for (int j = 0; j < ROOMS; j ++) {
				if (i == j || done[j])
					continue;
				int newDist = Math.abs(x - roomX[j]) + Math.abs(y - roomY[j]);
				if (dist == -1 || newDist < dist) {
					dist = newDist;
					index = j;
				}
			}
			done[index] = true;
			order[i + 1] = index;
		}
		
		for (int i = 0; i < ROOMS; i ++) {
			int i2 = i + 1;
			if (i == ROOMS - 1) i2 = 0;
			int x1 = (roomX[order[i]]);
			int y1 = (roomY[order[i]]);
			int x2 = (roomX[order[i2]]);
			int y2 = (roomY[order[i2]]);
			int xI = (x1 < x2) ? 1 : -1;
			int yI = (y1 < y2) ? 1 : -1;
			for (int j = x1; j != x2; j += xI) {
				for (int a = -2; a <= 2; a ++) {
					if (y1 + a > 1 && y1 + a < h - 2)
						cells[j][y1 + a] = true;
				}
			}
			for (int k = y1; k != y2; k += yI) {
				for (int a = -2; a <= 2; a ++) {
					if (x1 + a > 1 && x1 + a < w - 2)
						cells[x1 + a][k] = true;
				}
			}
		}
		
	}
	
	/* Checks of if a point is inside of a wall. */
	public boolean checkCollision(Vector2d loc) { 
		int xS = (int)(loc.x / EXPAND);
		int yS = (int)(loc.y / EXPAND);
		return (xS >= 0 && xS < width && yS >= 0 && yS < height) && !cells[xS][yS];
	}
	
	public Vector2d getStartLocation() {
		return Vector2d.create(roomX[0] * EXPAND + EXPAND / 2, roomY[0] * EXPAND + EXPAND / 2);
	}
	
	public void generateEnemies(EnemyFactory fact, int mult) {
		for (int i = 0; i < ROOMS; i ++) {
			int w = roomW[i];
			int h = roomH[i];
			int x = roomX[i];
			int y = roomY[i];
			
			for (int j = 0; j < (w + h)/10; j ++) {
				double ranX = Math.random() * (w/2) - w/4 + x;
				double ranY = Math.random() * (h/2) - h/4 + y;
				fact.createWanderEnemy(ranX * EXPAND, ranY* EXPAND, World.ENEMY_COLOR);
			}
		}
	}
	
}
