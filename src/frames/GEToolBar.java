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
	
	//������ 
	public GEToolBar(String label){
		super(label);
		buttonGroup = new ButtonGroup(); //��ư�� ����
		JRadioButton rButton = null;
		shapeToolBarHandler = new GETToolBarHandler();
		for(ETooLBarButtons btn : ETooLBarButtons.values()){
			rButton = new JRadioButton(); //JradioButton ��ü ����
			rButton.setIcon(new ImageIcon(GEConstants.IMG_URL+btn.toString()+GEConstants.SUFFIX_TOOLBAR_BTN));//Ŭ�� �Ǳ� ������ �̹��� URL
			rButton.setSelectedIcon(new ImageIcon(GEConstants.IMG_URL+btn.toString()+GEConstants.SUFFIX_TOOLBAR_BTN_SLT));//Ŭ���� ������ �̹��� URL
			rButton.addActionListener(shapeToolBarHandler);
			rButton.setActionCommand(btn.toString());//btn�� action ����
			this.add(rButton);
			buttonGroup.add(rButton);
		}
	}
	public void init(GEDrawingPanel dp){
		drawingPanel = dp;
		setSize(GEConstants.WIDTH_SHAPETOOLBAR,GEConstants.HEIGHT_SHAPETOOLBAR);//Tool Bar�� �ʺ� ���� ����, ������ ����
		clickDefaultButton();//�޼ҵ� ȣ��
	}
	private void clickDefaultButton() {
		JRadioButton rButton = (JRadioButton)this.getComponent(ETooLBarButtons.Ellipse.ordinal());//ó�� ���õǴ� ��ư�� �ӽ÷�  rectangle�� ����
		rButton.doClick();
	}
	
	private class GETToolBarHandler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton)e.getSource();
			if(button.getActionCommand().equals(ETooLBarButtons.Rectangle.name())){
				drawingPanel.setCurrentShape(new GERectangle());
				//button�� �̸���  Rectangle �̸� Rectangle��ü�� ����
			}else if(button.getActionCommand().equals(ETooLBarButtons.Ellipse.name())){
				drawingPanel.setCurrentShape(new GEEllipse());
				//button�� �̸���  Ellipse �̸� Rectangle��ü�� ����
			}else if(button.getActionCommand().equals(ETooLBarButtons.Line.name())){
				drawingPanel.setCurrentShape(new GELine());
				//button�� �̸���  Line �̸� Rectangle��ü�� ����
			}else if(button.getActionCommand().equals(ETooLBarButtons.Polygon.name())){
				drawingPanel.setCurrentShape(new GEPolygon());
			}else if(button.getActionCommand().equals(ETooLBarButtons.Select.name())){
				drawingPanel.setCurrentShape(new GESelect());
			}
		}		
	}
}
