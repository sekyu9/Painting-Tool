package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import shape.GEPolygon;
import shape.GEShape;
import frames.GEDrawingPanel;


//GEDrawer 역할은 도형들을 화면에 그리는 역할
//도형 그리기, 도형의 이동, 도형의 크기 변경
public class GEDrawer extends GETransformer{
	
	public GEDrawer(GEShape shape){
		super(shape);
	}
	
	public void init(Point p){
		shape.initDraw(p);
	}
	
	//도형을 화면에 출력한다
	public void transformer(Graphics2D g2D, Point p){
		g2D.setXORMode(g2D.getBackground());
		g2D.setStroke(dasheLineStroke);
		shape.draw(g2D);
		shape.setCoordinate(p);
		shape.draw(g2D);
	}
	
	//도형 그리기를 완료, 도형을 저장하는 메소드
	public void finalize(ArrayList<GEShape> shapeList){
		shapeList.add(shape);
	}
	
	//다각형을 그릴때 사용되는 메소드
	public void continueDrawing(Point p){
		((GEPolygon)shape).continueDrawing(p);
	}
}
