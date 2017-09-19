package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import shape.GEEllipse;
import shape.GELine;
import shape.GEPolygon;
import shape.GERectangle;
import shape.GESelect;
import constants.GEConstants;
import constants.GEConstants.ETooLBarButtons;

public class GEToolBar extends JToolBar{
	
	private ButtonGroup buttonGroup;
	private GEDrawingPanel drawingPanel;
	private GETToolBarHandler shapeToolBarHandler;
	
	//생성자 
	public GEToolBar(String label){
		super(label);
		buttonGroup = new ButtonGroup(); //버튼의 집합
		JRadioButton rButton = null;
		shapeToolBarHandler = new GETToolBarHandler();
		for(ETooLBarButtons btn : ETooLBarButtons.values()){
			rButton = new JRadioButton(); //JradioButton 객체 생성
			rButton.setIcon(new ImageIcon(GEConstants.IMG_URL+btn.toString()+GEConstants.SUFFIX_TOOLBAR_BTN));//클릭 되기 이전의 이미지 URL
			rButton.setSelectedIcon(new ImageIcon(GEConstants.IMG_URL+btn.toString()+GEConstants.SUFFIX_TOOLBAR_BTN_SLT));//클릭된 이후의 이미지 URL
			rButton.addActionListener(shapeToolBarHandler);
			rButton.setActionCommand(btn.toString());//btn의 action 설정
			this.add(rButton);
			buttonGroup.add(rButton);
		}
	}
	public void init(GEDrawingPanel dp){
		drawingPanel = dp;
		setSize(GEConstants.WIDTH_SHAPETOOLBAR,GEConstants.HEIGHT_SHAPETOOLBAR);//Tool Bar의 너비 높이 설정, 사이즈 지정
		clickDefaultButton();//메소드 호출
	}
	private void clickDefaultButton() {
		JRadioButton rButton = (JRadioButton)this.getComponent(ETooLBarButtons.Ellipse.ordinal());//처음 선택되는 버튼을 임시로  rectangle로 설정
		rButton.doClick();
	}
	
	private class GETToolBarHandler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton)e.getSource();
			if(button.getActionCommand().equals(ETooLBarButtons.Rectangle.name())){
				drawingPanel.setCurrentShape(new GERectangle());
				//button의 이름이  Rectangle 이면 Rectangle객체를 생성
			}else if(button.getActionCommand().equals(ETooLBarButtons.Ellipse.name())){
				drawingPanel.setCurrentShape(new GEEllipse());
				//button의 이름이  Ellipse 이면 Rectangle객체를 생성
			}else if(button.getActionCommand().equals(ETooLBarButtons.Line.name())){
				drawingPanel.setCurrentShape(new GELine());
				//button의 이름이  Line 이면 Rectangle객체를 생성
			}else if(button.getActionCommand().equals(ETooLBarButtons.Polygon.name())){
				drawingPanel.setCurrentShape(new GEPolygon());
			}else if(button.getActionCommand().equals(ETooLBarButtons.Select.name())){
				drawingPanel.setCurrentShape(new GESelect());
			}
		}		
	}
}
