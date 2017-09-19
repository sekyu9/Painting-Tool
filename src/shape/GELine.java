package shape;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.sound.sampled.Line;

public class GELine extends GEShape{
	
	public GELine(){
		super(new Line2D.Double());
	}
	
	public void initDraw(Point startP){
		this.startP = startP;
	}
	
	public void setCoordinate(Point currentP){
		Line2D tempLine = (Line2D)myShape;
		tempLine.setLine(startP.x, startP.y, currentP.x, currentP.y);
		if(anchorList!=null){
			anchorList.setPosition(myShape.getBounds());
		}
	}
	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GELine();
	}

	@Override
	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GELine shape = new GELine();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return null;
	}
}
