package shape;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GEPolygon extends GEShape{

	public GEPolygon() {
		super(new Polygon());
		
	}
	
	public void initDraw(Point p) {
		// TODO Auto-generated method stub
		((Polygon)myShape).addPoint(p.x, p.y);
	}
	
	public void setCoordinate(Point p) {
		// TODO Auto-generated method stub
		((Polygon)myShape).xpoints[((Polygon)myShape).npoints-1] = p.x;
		((Polygon)myShape).ypoints[((Polygon)myShape).npoints-1] = p.y;
		if(anchorList!=null){
			anchorList.setPosition(myShape.getBounds());
		}
	}
	
	public void continueDrawing(Point p){
		((Polygon)myShape).addPoint(p.x, p.y);
	}

	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GEPolygon();
	}

	@Override
	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEPolygon shape = new GEPolygon();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return null;
	}

	
}
