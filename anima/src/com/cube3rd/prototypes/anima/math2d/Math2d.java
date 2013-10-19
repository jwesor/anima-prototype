package com.cube3rd.prototypes.anima.math2d;

public final class Math2d {
	public static double APPROXIMATION = 6;
	public static double CRUDE = 2;
	public static double PRECISION = Math.pow(10, APPROXIMATION);
	public static double CRUDE_PRECISION = Math.pow(10, CRUDE);
	public static final Vector2d[] COORDINATE_ARRAY = new Vector2d[0];
	
	public static double pi = Math.PI;
	public static double tau = Math.PI * 2;
	
	public static double approximate(double d, double pre) {
		return (int)((d * pre) + 0.5) / pre;
	}
	
	public static double approximate(double d) {
		return (int)((d * PRECISION) + 0.5) / PRECISION;
	}
	
	public static double crudeApproximate(double d) {
		return approximate(d, Math.pow(10, APPROXIMATION - 2.0));
	}
	
	public static double standardizeAngle(double a) {
		while (a <= 0)
			a += tau;
		while (a > tau)
			a -= tau;
		return a;
	}
	
	public static Vector2d segmentsIntersection(Vector2d p1, Vector2d p2, Vector2d p3, Vector2d p4, Vector2d result) {
		double x1 = p1.x, y1 = p1.y, x2 = p2.x, y2 = p2.y, x3 = p3.x, y3 = p3.y, x4 = p4.x, y4 = p4.y;
		double d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (d == 0)
			return null;

		double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / d;
		double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / d;

		if (ua < 0 || ua > 1)
			return null;
		if (ub < 0 || ub > 1)
			return null;

		result.set(x1 + (x2 - x1) * ua, y1 + (y2 - y1) * ua);
		return result;
	}
	
	public static Vector2d[] rayCircleIntersection(Vector2d start, Vector2d dir, Vector2d center, double radius, Vector2d[] result, Vector2d[] tmpV) {
		double radius2 = radius * radius;
		Vector2d unitDir = tmpV[0].set(dir).unit();
		double test = start.distanceSqr(center);
		Vector2d testDir = tmpV[1].set(unitDir).multiply(test);
		Vector2d tmp = tmpV[2].set(start).add(testDir);
		Vector2d[] tmpV2 = new Vector2d[]{tmpV[3], tmpV[4]};
		
		Vector2d closest = pointSegmentDistance(start, tmp, center, tmpV[5], tmpV2);
		double dist2 = closest.distanceSqr(center);
		if (dist2 > radius2)
			return new Vector2d[0]; //ray does not strike circle
		boolean oneResult = false;
		if (start == closest) {
			oneResult = true;
			closest = pointSegmentDistance(tmpV[6].set(start).subtract(testDir), tmp, center, tmpV[5], tmpV2); //ray points outwards
			dist2 = closest.distanceSqr(center);
		}
		double halfchord = Math.sqrt(radius2 - dist2); //pythagorean theorem
		unitDir.multiply(halfchord);
		
		Vector2d res1 = result[0].set(closest).add(unitDir);
		if (center.distanceSqr(start) < radius2 || oneResult)
			return new Vector2d[]{res1}; //ray starts inside of circle 
		Vector2d res2 = result[1].set(closest).subtract(unitDir); //ray starts outside of circle, passes through two points
		if (approximate(res1.distance(res2)) == 0)
			return new Vector2d[]{res1}; //ray runs tangent to circle
		return new Vector2d[]{res1, res2};
	}
	
	public static boolean segmentIntersectsCircle(Vector2d start, Vector2d end, Vector2d center, double radius, Vector2d result, Vector2d[] tmpV) {
		return pointSegmentDistance(start, end, center, result, tmpV).distanceSqr(center) <= radius * radius;
	}
	
	public static Vector2d[] rayArcIntersection(Vector2d start, Vector2d dir, Vector2d center, Vector2d focus, double sector, Vector2d[] result, Vector2d[] tmpV) {
		Vector2d[] results = rayCircleIntersection(start, dir, center, center.distance(focus), result, tmpV);
		int size = results.length;
		boolean[] valid = new boolean[size];
		int count = 0;
		for (int i = 0; i < size; i ++) {
			if (pointWithinSector(results[i], center, focus, sector, tmpV)) {
				count ++;
				valid[i] = true;
			}
		}
		if (count == size)
			return results;
		Vector2d[] newResults = new Vector2d[count];
		int j = 0;
		for (int i = 0; i < size; i ++) {
			if (valid[i]) {
				newResults[j] = results[i];
				j ++;
			}
		}
		return newResults;
	}
	
	public static Vector2d pointSegmentDistance(Vector2d start, Vector2d end, Vector2d point, Vector2d result, Vector2d[] tmpV) {
		Vector2d tmp = tmpV[0].set(end);
		double l2 = tmp.subtract(start).lengthSqr();
		if (l2 == 0) //start = end
			return result.set(start);
		tmp.set(point).subtract(start);
		Vector2d tmp2 = tmpV[1].set(end).subtract(start);
		
		double t = tmp.dotProduct(tmp2) / l2;
		if (t < 0.0)
			return result.set(start); // Beyond 'start'-end of the segment
		else if (t > 1.0)
			return result.set(end); // Beyond 'end'-end of the segment

		tmp.set(end);
		tmp.subtract(start).multiply(t).add(start);
		result.set(tmp);
		return result;
	}
	
	public static boolean pointWithinBounds(Vector2d c, Vector2d v1, Vector2d v2) {
		double cx = Math2d.approximate(c.x);
		double cy = Math2d.approximate(c.y);
		double v1x = Math2d.approximate(v1.x);
		double v2x = Math2d.approximate(v2.x);
		double v1y = Math2d.approximate(v1.y);
		double v2y = Math2d.approximate(v2.y);
		boolean x = false;
		boolean y = false;
		if (v1x < v2x && (cx >= v1x && cx <= v2x) )
			x = true;
		else if (v1x >= v2x && (cx <= v1x && cx >= v2x) )
			x = true;
		if (v1y < v2y && (cy >= v1y &&cy <= v2y) )
			y = true;
		else if (v1y >= v2y && (cy <= v1y && cy >= v2y) )
			y = true;
		return x && y;
	}
	
	public static boolean pointWithinSector(Vector2d c, Vector2d focus, Vector2d midpoint, double sector, Vector2d[] tmpV) {
		double pointer = tmpV[0].set(focus, c).angle();
		Vector2d bisector = tmpV[1].set(focus, midpoint);
		double a = bisector.angle() - sector / 2;
		double b = bisector.angle() + sector / 2;
		while (pointer <= a)
			pointer += Math.PI * 2;
		while (pointer >= a) {
			if (pointer >= a && pointer <= b)
				return true;
			pointer -= Math.PI * 2;
		}
		return false;
	}
}
