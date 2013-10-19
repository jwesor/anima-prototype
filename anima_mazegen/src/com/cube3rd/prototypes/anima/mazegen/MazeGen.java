package com.cube3rd.prototypes.anima.mazegen;


public class MazeGen {
	
	boolean[][] cells;
	int width, height;
	int[] roomX, roomY, roomW, roomH;
	
	static int ROOMS = 12;
	static int ROOM_MIN_SIZE = 10;
	static int ROOM_MAX_SIZE = 25;
	public MazeGen(int w, int h){
		cells = new boolean[w][h];
		width = w;
		height = h;
		
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
		
		for (int i = 0; i < ROOMS; i ++)
			System.out.println(order[i]);
		
		for (int i = 0; i < ROOMS; i ++) {
			/*int x1 = (roomX[i] < roomX[i + 1]) ? roomX[i] : roomX[i + 1];
			int y1 = (roomY[i] < roomY[i + 1]) ? roomY[i] : roomY[i + 1];
			int x2 = (roomX[i] > roomX[i + 1]) ? roomX[i] : roomX[i + 1];
			int y2 = (roomY[i] > roomY[i + 1]) ? roomY[i] : roomY[i + 1];
			 */
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
		System.out.println(roomX[0] + " " + roomY[0]);
	}
	
	public void print() {
		for (int i = 0; i < cells.length; i ++) {
			for (int j = 0; j < cells[0].length; j ++) {
				if (!cells[i][j])
					System.out.print("#");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		MazeGen run = new MazeGen(100, 100);
		run.print();
	}
}
