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

	//������ ��ǥ�� ����
	public void init(Point p){
		previousP = p;
	}
	
	//������ �̵��� �����ִ� �޼ҵ�
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
