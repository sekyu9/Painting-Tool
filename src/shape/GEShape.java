package shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import constants.GEConstants.EAnchorType;
import utils.GEAnchorList;

public abstract class GEShape implements Serializable{

	protected Shape myShape;
	protected Point startP;
	protected boolean selected;
	protected GEAnchorList anchorList;
	protected EAnchorType selectedAnchor;
	protected Color lineColor, fillColor;
	protected AffineTransform affineTransform;// AffineTransform class ������ ���³� ��ġ�� ��ȯ �� �� �ִ� Ŭ����
	
	public GEShape(Shape shape){
		this.myShape = shape;
		affineTransform = new AffineTransform();
		anchorList = null;
		selected = false;
	}
	
	public void setGraphicsAttributes(GEShape shape){
		setLineColor(shape.getLineColor());
		setFillColor(shape.getFillColor());
		setAnchorList(shape.getAnchorList());
		setSelected(shape.isSelected());
	
	}
	protected void setShape(Shape shape){
		myShape = shape;
	}
	public void setLineColor(Color lineColor){
		this.lineColor = lineColor;
	}
	
	public void setFillColor(Color fillColor){
		this.fillColor = fillColor;
	}
	public Color getLineColor(){
		return lineColor;
	}
	public Color getFillColor(){
		return fillColor;
	}
	public GEAnchorList getAnchorList(){
		return anchorList;
	}
	public void setAnchorList(GEAnchorList anchorList){
		this.anchorList = anchorList;
	}
	public EAnchorType getSelectedAnchor(){
		return selectedAnchor;
	}
	
	public Rectangle getBounds(){
		return myShape.getBounds();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
		if(selected){
			anchorList = new GEAnchorList();
			anchorList.setPosition(myShape.getBounds());
		}
		else {
			anchorList=null;
		}
	}
	
	public boolean onShape(Point p){
		if(anchorList!=null){
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != EAnchorType.NONE)
				return true;
		}
		return myShape.intersects(new Rectangle(p.x,p.y,2,2));
	}
	
	public EAnchorType onAnchor(Point p){
		this.selectedAnchor = anchorList.onAnchors(p);
		return selectedAnchor;
	}
	
	public void draw(Graphics2D g2D){
		if(this.fillColor != null){
			g2D.setColor(this.fillColor);
			g2D.fill(myShape);
		}
		if(this.lineColor!= null){
			g2D.setColor(this.lineColor);
			g2D.draw(myShape);
		}
		if(selected){
			this.anchorList.setPosition(myShape.getBounds());
			this.anchorList.draw(g2D);
		}
	}
	
	//������ �̵��� ����ϴ� �޼ҵ�
	public void moveCoordinate(Point moveP){
		//setToTranslation ������ �̵� ������ ���� �̵� ���·� �����ϴ� �޼ҵ�
		affineTransform.setToTranslation(moveP.getX(), moveP.getY());//�Ķ���ʹ� ���� �̵��� ��ǥ
		myShape = affineTransform.createTransformedShape(myShape);//createTransformedShape affineTransform Ŭ������ ���´�� ���� ������ ��ȯ��
		//setTotranslation �� ���ؼ� �޼ҵ��� �̵� ���¸� �����ϰ�
		//createTransformedShape�� ���ؼ� ������ �̵��Ѵ�.
	}
	
	public void resizeCoordinate(Point2D resizeFactor){
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		myShape = (affineTransform.createTransformedShape(myShape));
	}
	
	public void moveReverse(Point2D resizeAnchor){
		affineTransform.setToTranslation(-resizeAnchor.getX(), -resizeAnchor.getY());
		myShape = (affineTransform.createTransformedShape(myShape));
	}
	
	public void move(Point2D resizeAnchor){
		affineTransform.setToTranslation(resizeAnchor.getX(), resizeAnchor.getY());
		myShape = (affineTransform.createTransformedShape(myShape));
	}
	abstract public void initDraw(Point startP);//initDraw �޼ҵ��� ������ ó�� ���콺 �̺�Ʈ�� �߻� �Ͽ��� �� ���O�� ��������  startP�� ����
	abstract public void setCoordinate(Point currentP);//������ ��ġ�� ũ�⸦ �����ϴ� ����
	abstract public GEShape clone();
	abstract public GEShape deepCopy();
}
