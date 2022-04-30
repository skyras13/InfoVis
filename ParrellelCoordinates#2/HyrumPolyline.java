package src.com.company; /*********************************
 * This file was originally created for the
 * "Interactive Fan Chart Demo"
 * by Geoffrey M. Draper.
 * 
 * Geoffrey M. Draper, author of this work, hereby grants
 * everyone permission to use and distribute this code,
 * with or without modifications for any purpose,
 * commercial or otherwise. 
 * 
 * It is requested, although not required, that derivative
 * works include an acknowledgment somewhere in the
 * derivative work's credits.
 *********************************/

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//This composite class gives me the precision of the Path2D
//class, while still providing the low-level access to individual
//vertices I got accustomed to when using the legacy Polygon class.

public class HyrumPolyline
		implements Serializable, Comparable<HyrumPolyline> {
	private Path2D.Double polygon;
	private List<Point2D> points;
	
	private enum State {
		NORMAL,
		HIGHLIGHTED,
		INVISIBLE
	}
	
	private Color invisibleColor = new Color(218, 203, 203, 255);
	State state = State.NORMAL;

	public HyrumPolyline() {
		polygon = new Path2D.Double(Path2D.Double.WIND_EVEN_ODD);
		points = new ArrayList<Point2D>();
	}

	public HyrumPolyline(HyrumPolyline hp) {
		polygon = new Path2D.Double(hp.polygon);
		points = new ArrayList<Point2D>(hp.points);
	}

	public void addPoint(final Point2D p) {
		addPoint(p.getX(), p.getY());
	}

	public void addPoint(final double x, final double y) {
		if (points.isEmpty()) {
			polygon.moveTo(x, y);
		} else {
			polygon.lineTo(x, y);
		}
		points.add(new Point2D.Double(x,y));
	}

	public Point2D getPointAt(final int index) {
		return points.get(index);
	}

	public int getNumPoints() {
		return points.size();
	}

	public void reset() {
		polygon.reset();
		points.clear();
	}

	public void draw(final Graphics2D g) {
		if (state == State.HIGHLIGHTED) {
			g.setStroke(new BasicStroke(5));
			g.setColor(Color.GREEN);
		} else if (state==State.NORMAL) {
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
		} else {//invisible
			g.setStroke(new BasicStroke(2));
			g.setColor(invisibleColor);
		}
		g.draw(polygon);			
	}

	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.draw(polygon);
	}

	public void highlight() {
		state = State.HIGHLIGHTED;
	}

	public void fade() {
		state = State.INVISIBLE;
	}

	public void unhighlight() {
		state = State.NORMAL;
	}

	public boolean isInvisible() {
		return (state == State.INVISIBLE);
	}

	public boolean isHighlighted() {
		return (state == State.HIGHLIGHTED);
	}

	public boolean isNormal() {
		return (state == State.NORMAL);
	}

	@Override
	public int compareTo(HyrumPolyline other) {
		if (isHighlighted()) {
			return 1;
		}
		if (isInvisible()) {
			return -1;
		}
		if (isNormal()) {
			if (other.isHighlighted()) {
				return -1;
			}
			if (other.isInvisible()) {
				return 1;
			}
		}
		return 0;
	}

	//used to check box intersects
	public boolean intersects(Rectangle r){
		boolean result = false;
		for (int i = 1; i < points.size();i++){
			var p1 = points.get(i-1);
			var p2 = points.get(i);
			var segment = new Line2D.Double(p1,p2);
			result = segment.intersects(r);
			if (result == true){
				break;
			}
		}
		return result;
	}

	//getting correct intersections for
	public boolean contains(Point p){
		boolean result = false;
		for (int i = 1; i < points.size();i++){
			var p1 = points.get(i-1);
			var p2 = points.get(i);
			var segment = new Line2D.Double(p1,p2);
			result = segment.contains(p);
			if (result == true){
				break;
			}
		}
		return result;
	}

	//getting the correct line for single lines hover highlight.
	public double getDistanceFromPoint(int x, int y) {
		double min = java.lang.Double.MAX_VALUE;
		for (int i=1; i<points.size(); i++) {
			var p1 = points.get(i-1);
			var p2 = points.get(i);
			var segment = new Line2D.Double(p1, p2);
			var dist = segment.ptSegDist(x,y);
			if (dist < min) {
				min = dist;
			}
		}
		return min;
	}

}
