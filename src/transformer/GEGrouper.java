package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import shape.GEShape;

public class GEGrouper extends GETransformer{
	public GEGrouper(GEShape shape){
		super(shape);
	}

	public void init(Point p){
		shape.initDraw(p);
	}
	@Override
	public void transformer(Graphics2D g2D, Point p) {
		// TODO Auto-generated method stub
		g2D.setXORMode(g2D.getBackground());
		g2D.setStroke(dasheLineStroke);
		shape.draw(g2D);
		shape.setCoordinate(p);
		shape.draw(g2D);
	}
	
	public void finalize(ArrayList<GEShape>shapeList){
		for(GEShape tempShape:shapeList){
			if(shape.getBounds().contains(tempShape.getBounds())){
				tempShape.setSelected(true);
			}
		}
	}
}
