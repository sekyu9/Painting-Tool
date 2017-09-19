package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import shape.GEPolygon;
import shape.GEShape;
import frames.GEDrawingPanel;


//GEDrawer ������ �������� ȭ�鿡 �׸��� ����
//���� �׸���, ������ �̵�, ������ ũ�� ����
public class GEDrawer extends GETransformer{
	
	public GEDrawer(GEShape shape){
		super(shape);
	}
	
	public void init(Point p){
		shape.initDraw(p);
	}
	
	//������ ȭ�鿡 ����Ѵ�
	public void transformer(Graphics2D g2D, Point p){
		g2D.setXORMode(g2D.getBackground());
		g2D.setStroke(dasheLineStroke);
		shape.draw(g2D);
		shape.setCoordinate(p);
		shape.draw(g2D);
	}
	
	//���� �׸��⸦ �Ϸ�, ������ �����ϴ� �޼ҵ�
	public void finalize(ArrayList<GEShape> shapeList){
		shapeList.add(shape);
	}
	
	//�ٰ����� �׸��� ���Ǵ� �޼ҵ�
	public void continueDrawing(Point p){
		((GEPolygon)shape).continueDrawing(p);
	}
}
