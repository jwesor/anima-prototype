package com.cube3rd.prototypes.anima.math2d;

public class Vector2d {
	
	public double x;
	public double y;
	
	public Vector2d() {
		x = 0;
		y = 0;
	}
	public static Vector2d create() {
		return new Vector2d(0,0);
	}
	
	public static Vector2d create(double x, double y) {
		return new Vector2d(x, y);
	}
	
	public static Vector2d create(Vector2d start, Vector2d finish) {
		return new Vector2d(finish.x - start.x, finish.y - start.y);
	}
	
	public static Vector2d createPolar(double r, double a) {
		return new Vector2d(r * Math.cos(a), r * Math.sin(a));
	}
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2d copy() {
		return new Vector2d(x, y);
	}
	
	public Vector2d set(Vector2d v) {
		x = v.x;
		y = v.y;
		return this;
	}
	
	public Vector2d set(Vector2d start, Vector2d finish) {
		x = finish.x - start.x;
		y = finish.y - start.y;
		return this;
	}
	
	public Vector2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2d setPolar(double r, double a) {
		this.x = r * Math.cos(a);
		this.y = r * Math.sin(a);
		return this;
	}
	
	public Vector2d add(Vector2d v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector2d add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2d addPolar(double r, double a) {
		this.x += r * Math.cos(a);
		this.y += r * Math.sin(a);
		return this;
	}
	
	public Vector2d subtract(Vector2d v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vector2d subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2d subtractPolar(double r, double a) {
		this.x -= r * Math.cos(a);
		this.y -= r * Math.sin(a);
		return this;
	}
	
	public Vector2d rotate (double rad) {
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		double newX = this.x * cos - this.y * sin;
		double newY = this.x * sin + this.y * cos;
		
		this.x = newX;
		this.y = newY;
		
		return this;
	}

	public Vector2d multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2d unit() {
		double length = length();
		if (length != 0) {
			x /= length;
			y /= length;	
		}
		return this;
	}
	
	public double angle() {
		double angle = Math.atan2(y, x);
		if (angle < 0) angle += Math.PI * 2;
		return angle; //0 to 2*pi
	}
	
	public static double angle(Vector2d a, Vector2d b) {
		double x = b.x - a.x;
		double y = b.y - a.y;
		double angle = Math.atan2(y, x);
		if (angle < 0) angle += Math.PI * 2;
		return angle; //0 to 2*pi
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double lengthSqr() {
		return x*x + y*y;
	}
		
	public double dotProduct(Vector2d v) {
		return (x * v.x + y * v.y);
	} 
	
	public double distance(Vector2d v) {
		double xdif = v.x - x;
		double ydif = v.y - y;
		return Math.sqrt(xdif * xdif + ydif * ydif);
	}
	
	public double distanceSqr(Vector2d v) {
		double xdif = v.x - x;
		double ydif = v.y - y;
		return xdif * xdif + ydif * ydif;
	}
	
	public double distance(double vx, double vy) {
		double xdif = vx - x;
		double ydif = vy - y;
		return Math.sqrt(xdif * xdif + ydif * ydif);
	}
	
	public double distanceSqr(double vx, double vy) {
		double xdif = vx - x;
		double ydif = vy - y;
		return xdif * xdif + ydif * ydif;
	}
	
	public Vector2d midpoint(Vector2d one, Vector2d two) {
		return set((one.x + two.x) / 2.0, (one.y + two.y) / 2.0);
	}
	
	@Override
	public String toString() {
		return "<" + x + ":" + y + ">";
	}
	
	public boolean equals(Vector2d other) {
		if (other.x == this.x && other.y == this.y)
			return true;
		return false;
	}

	
}