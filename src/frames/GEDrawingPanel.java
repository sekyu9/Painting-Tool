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

	private MouseDrawingHandler drawingHandler;//MouseDrawingHandler 타입의 drawingHandler 선언
	private ArrayList<GEShape> shapeList;//생성 되는 도형 객체를 저장하는 arrayList
	private EState currentState;//그리기 상태(그리기 이전,두 개의 좌표로 그릴 수 있는 상태(사각형, 원, 선),다각형 그리기 상태 )
	private GEShape currentShape;//유저가 그리고자 하는 도형의 정보를 저장하는 필드
	private GEShape selectedShape;//유저가 그려진 도형중에서 산택한 도형의 정보를 저장하는 필드(그리기 이전,두 개의 좌표로 그릴 수 있는 상태(사각형, 원, 선),다각형 그리기 상태 )
	private GETransformer transformer;//transformer 클래스는 도형을 그리는 클래스
	private Color lineColor, fillColor;
	private GECursorManger cursors;
	
	//생성자 선언
	public GEDrawingPanel(){
		//생성자 호출, JPANEL
		super();
		currentState = EState.Idle; //초기값을 도형을 그리기 이전의 상태로 선언
		shapeList = new ArrayList<GEShape>();//arrayList instance 생성
		drawingHandler = new MouseDrawingHandler(); //drawingHandler 인스턴스 생성
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
	
	//현재 도형 setter
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
	
	
	// 화면에 표시하기 위해 호출하는 메소드
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
	
	//도형의 시작 점을 받아서
	private void createShape(Point startP){
		currentShape = currentShape.clone();
		currentShape.setLineColor(lineColor);
		currentShape.setFillColor(fillColor);
	}
	
	//다각형을 그릴때 사용 되는 메소드
	private void continueDrawing(Point p){
		((GEDrawer)transformer).continueDrawing(p);
	}
	
	//어떤 도형을 선택 했는지 판별하는 메소드
	private GEShape onShape(Point p){
		for(int i=shapeList.size();i>0;i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.onShape(p)){
				return shape;
			}
		}
		return null;
	}
	
	//선택 이후 새로운 도형을 만들 때 사용되는 메소드
	private void clearSelectedShapes(){
		for(GEShape shape:shapeList)
			shape.setSelected(false);
	}
	
	//마우스를 사용했떄의 모든 이벤트를 처리하는 클래스
	private class MouseDrawingHandler extends MouseInputAdapter {

		// 1) 마우스를 눌렸을 때
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
		
		// 다각형을 그릴 때
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

		// 원, 선, 사각형
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
