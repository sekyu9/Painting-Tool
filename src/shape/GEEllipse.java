package shape;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class GEEllipse extends GEShape{

	public GEEllipse(){
		super(new Ellipse2D.Double());
	}
	public void initDraw(Point startP){
		this.startP = startP;
	}
	public void setCoordinate(Point currentP){
		Ellipse2D tempEllipse = (Ellipse2D)myShape;
		tempEllipse.setFrameFromDiagonal(startP.x, startP.y, currentP.x, currentP.y);
		
		if(anchorList!=null){
			anchorList.setPosition(myShape.getBounds());
		}
	}
	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GEEllipse();
	}
	@Override
	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEEllipse shape = new GEEllipse();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return null;
	}
	
}
