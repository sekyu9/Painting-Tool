package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Currency;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import constants.GEConstants;
import constants.GEConstants.EAnchorType;
import constants.GEConstants.EState;
import constants.GEConstants.ETooLBarButtons;
import shape.GEEllipse;
import shape.GEGroup;
import shape.GELine;
import shape.GEPolygon;
import shape.GERectangle;
import shape.GESelect;
import shape.GEShape;
import transformer.GEDrawer;
import transformer.GEGrouper;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GETransformer;
import utils.GECursorManger;

public class GEDrawingPanel extends JPanel{

	private MouseDrawingHandler drawingHandler;//MouseDrawingHandler Ÿ���� drawingHandler ����
	private ArrayList<GEShape> shapeList;//���� �Ǵ� ���� ��ü�� �����ϴ� arrayList
	private EState currentState;//�׸��� ����(�׸��� ����,�� ���� ��ǥ�� �׸� �� �ִ� ����(�簢��, ��, ��),�ٰ��� �׸��� ���� )
	private GEShape currentShape;//������ �׸����� �ϴ� ������ ������ �����ϴ� �ʵ�
	private GEShape selectedShape;//������ �׷��� �����߿��� ������ ������ ������ �����ϴ� �ʵ�(�׸��� ����,�� ���� ��ǥ�� �׸� �� �ִ� ����(�簢��, ��, ��),�ٰ��� �׸��� ���� )
	private GETransformer transformer;//transformer Ŭ������ ������ �׸��� Ŭ����
	private Color lineColor, fillColor;
	private GECursorManger cursors;
	
	//������ ����
	public GEDrawingPanel(){
		//������ ȣ��, JPANEL
		super();
		currentState = EState.Idle; //�ʱⰪ�� ������ �׸��� ������ ���·� ����
		shapeList = new ArrayList<GEShape>();//arrayList instance ����
		drawingHandler = new MouseDrawingHandler(); //drawingHandler �ν��Ͻ� ����
		cursors = new GECursorManger();
		addMouseListener(drawingHandler);//Receives Mouse Events
		addMouseMotionListener(drawingHandler);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		initializeGraphicsAttribute();
	}

	public ArrayList<GEShape> getShapeList(){
		return shapeList;
	}
	
	public void setShapeList(ArrayList<GEShape> shapeList){
		this.shapeList = shapeList;
		repaint();
	}
	
	public void clearShapeList(){
		shapeList.clear();
		repaint();
	}
	
	public void paste(ArrayList<GEShape> shapes){
		for(GEShape shape : shapes){
			shapeList.add(shape.deepCopy());
		}
		repaint();
	}
	
	public ArrayList<GEShape> copy(){
		ArrayList<GEShape> returnList = new ArrayList<GEShape>();
		for(GEShape shape:shapeList){
			if(shape.isSelected()){
				returnList.add(shape.deepCopy());
			}
		}
		repaint();
		return returnList;
	}
	
	public ArrayList<GEShape> cut(){
		ArrayList<GEShape> returnList = new ArrayList<GEShape>();
		for(int i = shapeList.size();i>0;i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.isSelected()){
				returnList.add(0, shape.deepCopy());
				shapeList.remove(shape);
			}
		}
		repaint();
		return returnList;
	}
	
	public void delete(){
		for(int i = shapeList.size();i>0;i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.isSelected()){
				shapeList.remove(shape);
			}
		}
		repaint();
	}
	
	public void setLineColor(Color lineColor){
		if(selectedSetColor(true, lineColor)){
			return;
		}
		this.lineColor = lineColor;
	}
	
	public void setFillColor(Color fillColor){
		if(selectedSetColor(true, fillColor)){
			return;
		}
		this.fillColor = fillColor;
	}
	
	private boolean selectedSetColor(boolean flag, Color color){
		boolean returnValue = false;
		if(selectedShape != null){
			if(flag){
				selectedShape.setLineColor(color);
			}
			else
			{
				selectedShape.setFillColor(color);
			}
			repaint();
			returnValue = true;
		}
		return returnValue;
	}
	
	//���� ���� setter
	public void setCurrentShape(GEShape currentShape) {
		this.currentShape = currentShape;
	}
	
	public void setCurrentState(EState currentState){
		this.currentState = currentState;
	}

	public void group(GEGroup group){
		boolean check = false;
		for(int i=shapeList.size();i>0;i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.isSelected()){
				shape.setSelected(false);
				group.addShape(shape);
				shapeList.remove(shape);
				check = true;
			}
		}
		if(check){
			group.setSelected(true);
			shapeList.add(group);
		}
		repaint();
		
	}
	
	public void unGroup(){
		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
		for(int i=shapeList.size();i>0;i--){
			GEShape shape = shapeList.get(i-1);
			if(shape instanceof GEGroup && shape.isSelected()){
				for(GEShape childShape: ((GEGroup)shape).getChildList()){
					childShape.setSelected(true);
					tempList.add(childShape);
				}
				shapeList.remove(shape);
			}
		}
		shapeList.addAll(tempList);
		repaint();
	}
	
	
	// ȭ�鿡 ǥ���ϱ� ���� ȣ���ϴ� �޼ҵ�
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		for(GEShape shape :shapeList){
			shape.draw(g2D);
		}
	}
	
	private void initializeGraphicsAttribute(){
		lineColor = GEConstants.COLOR_LINE_DEFAULT;
		fillColor = GEConstants.COLOR_FILL_DEFAULT;
	}
	
	//������ ���� ���� �޾Ƽ�
	private void createShape(Point startP){
		currentShape = currentShape.clone();
		currentShape.setLineColor(lineColor);
		currentShape.setFillColor(fillColor);
	}
	
	//�ٰ����� �׸��� ��� �Ǵ� �޼ҵ�
	private void continueDrawing(Point p){
		((GEDrawer)transformer).continueDrawing(p);
	}
	
	//� ������ ���� �ߴ��� �Ǻ��ϴ� �޼ҵ�
	private GEShape onShape(Point p){
		for(int i=shapeList.size();i>0;i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.onShape(p)){
				return shape;
			}
		}
		return null;
	}
	
	//���� ���� ���ο� ������ ���� �� ���Ǵ� �޼ҵ�
	private void clearSelectedShapes(){
		for(GEShape shape:shapeList)
			shape.setSelected(false);
	}
	
	//���콺�� ����ߋ��� ��� �̺�Ʈ�� ó���ϴ� Ŭ����
	private class MouseDrawingHandler extends MouseInputAdapter {

		// 1) ���콺�� ������ ��
		public void mousePressed(MouseEvent e) {
			if (currentState == EState.Idle) {
				
				if(currentShape instanceof GESelect){
					selectedShape = onShape(e.getPoint());
					if(selectedShape!=null){
						clearSelectedShapes();
						selectedShape.setSelected(true);
						selectedShape.onAnchor(e.getPoint());
						if(selectedShape.getSelectedAnchor()==EAnchorType.NONE){
							transformer = new GEMover(selectedShape);
							((GEMover)transformer).init(e.getPoint());
							setCurrentState(EState.Moving);
						}
						else
						{
							transformer = new GEResizer(selectedShape);
							((GEResizer)transformer).init(e.getPoint());
							setCurrentState(EState.Resize);
						}
					}
					else {
						setCurrentState(EState.Selecting);
						clearSelectedShapes();
						createShape(e.getPoint());
						transformer = new GEGrouper(currentShape);
						((GEGrouper)transformer).init(e.getPoint());
					}
				}
				else{
					clearSelectedShapes();
					createShape(e.getPoint());
					transformer = new GEDrawer(currentShape);
					((GEDrawer)transformer).init(e.getPoint());
					if(currentShape instanceof GEPolygon){
						setCurrentState(EState.NPointsDrawing);
					}
					else{
						setCurrentState(EState.TwoPointsDrawing);
					}
				}
			}
		}
		
		// �ٰ����� �׸� ��
		public void mouseMoved(MouseEvent e) {
			if (currentState == EState.NPointsDrawing) {
				transformer.transformer((Graphics2D) getGraphics(), e.getPoint());
			} else if(currentState == EState.Idle){
				GEShape shape = onShape(e.getPoint());
				if(shape==null){
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				} else{
					if(shape.isSelected()){
						EAnchorType anchorType = shape.onAnchor(e.getPoint());
						setCursor(cursors.get(anchorType.ordinal()));
					}
				}
			}
		}

		// ��, ��, �簢��
		public void mouseDragged(MouseEvent e) {
			if (currentState != EState.Idle) {
				transformer.transformer((Graphics2D) getGraphics(),
						e.getPoint());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (currentState == EState.TwoPointsDrawing) {
				((GEDrawer) transformer).finalize(shapeList);
			} else if (currentState == EState.NPointsDrawing) {
				return;
			} else if(currentState == EState.Resize){
				((GEResizer)transformer).finalize(e.getPoint());
			} else if(currentState == EState.Selecting){
				((GEGrouper)transformer).finalize(shapeList);
			}
			setCurrentState(EState.Idle);
			repaint();
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (currentState == EState.NPointsDrawing) {
					if (e.getClickCount() == 1) {
						continueDrawing(e.getPoint());
					} else if (e.getClickCount() == 2) {
						((GEDrawer) transformer).finalize(shapeList);
						currentState = EState.Idle;
						repaint();
					}
				}
			}
		}
	}
}
