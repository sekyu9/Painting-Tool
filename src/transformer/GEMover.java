package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import constants.GEConstants;
import shape.GEShape;

public class GEMover extends GETransformer {

	public GEMover(GEShape shape) {
		super(shape);
		previousP = new Point();
	}

	//이전의 좌표를 저장
	public void init(Point p){
		previousP = p;
	}
	
	//도형의 이동을 보여주는 메소드
	@Override
	public void transformer(Graphics2D g2D, Point p) {
		Point tempP = new Point(p.x-previousP.x, p.y-previousP.y);
		g2D.setXORMode(GEConstants.BACKGROUND_COLOR);
		g2D.setStroke(dasheLineStroke);
		shape.draw(g2D);
		shape.moveCoordinate(tempP);
		shape.draw(g2D);
		previousP = p;

	}

}
