package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.sql.PreparedStatement;

import constants.GEConstants;
import constants.GEConstants.EAnchorType;
import shape.GEShape;

public class GEResizer extends GETransformer{

	private Point2D resizeAnchor;
	
	public GEResizer(GEShape shape) {
		super(shape);
		previousP = new Point();
	}

	public void init(Point p){
		previousP = p;
		//resizeAnchor = getResizeAnchor();
		shape.moveReverse(resizeAnchor);
	}
	
	@Override
	public void transformer(Graphics2D g2D, Point p) {
	
		g2D.setXORMode(GEConstants.BACKGROUND_COLOR);
		g2D.setStroke(dasheLineStroke);
		Point2D resizeFactor = computeResizeFactor(previousP,p);
		AffineTransform tempAffine = g2D.getTransform();
		g2D.translate(resizeAnchor.getX(), resizeAnchor.getY());
		shape.draw(g2D);
		shape.resizeCoordinate(resizeFactor);
		shape.draw(g2D);
		g2D.setTransform(tempAffine);
		previousP = p;
	}

	private java.awt.geom.Point2D.Double computeResizeFactor(Point previousP, Point currentP){
		double deltaW=0;
		double deltaH=0;
		if(shape.getSelectedAnchor()==EAnchorType.NW){
			deltaW=-(currentP.x-previousP.x);
			deltaH=-(currentP.y-previousP.y);
		} else if(shape.getSelectedAnchor()==EAnchorType.NN){
			deltaW=0;
			deltaH=-(currentP.y-previousP.y);
		} else if(shape.getSelectedAnchor()==EAnchorType.NE){
			deltaW= (currentP.x-previousP.x);
			deltaH=-(currentP.y-previousP.y);
		} else if(shape.getSelectedAnchor()==EAnchorType.WW){
			deltaW=-(currentP.x-previousP.x);
			deltaH=0;
		} else if(shape.getSelectedAnchor()==EAnchorType.EE){
			deltaW= (currentP.x-previousP.x);
			deltaH=0;
		} else if(shape.getSelectedAnchor()==EAnchorType.SW){
			deltaW=-(currentP.x-previousP.x);
			deltaH=(currentP.y-previousP.y);
		} else if(shape.getSelectedAnchor()==EAnchorType.SS){
			deltaW=0;
			deltaH=(currentP.y-previousP.y);
		} else if(shape.getSelectedAnchor()==EAnchorType.SE){
			deltaW=(currentP.x-previousP.x);
			deltaH=(currentP.y-previousP.y);
		}
		double currentW = shape.getBounds().getWidth();
		double currentH = shape.getBounds().getHeight();
		double xFactor = 1.0;
		double yFactor = 1.0;
		if(currentW > 0.0)
			xFactor = (1.0+deltaW/currentW);
		if(currentH>0.0)
			yFactor = (1.0+deltaH/currentH);
		return new Point.Double(xFactor,yFactor);
	}
	
	/*private Point2D getResizeAnchor() {
		Point resizeAnchor = new Point();
		if(shape.getSelectedAnchor()==EAnchorType.NW){
			resizeAnchor.setLocation(shape.)
		}
		return null;
	}*/

	public void finalize(Point point) {
		// TODO Auto-generated method stub
		
	}
	
}
