package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants;
import constants.GEConstants.EColorMenuItems;
import frames.GEDrawingPanel;

public class GEMenuColor extends JMenu{
	
	private GEDrawingPanel drawingPanel;
	private ColorMenuHandler colorMenuHandler;
	
	public GEMenuColor(String label){
		super(label);
		colorMenuHandler = new ColorMenuHandler();
		
		for(EColorMenuItems btn : EColorMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(colorMenuHandler);
			menuItem.setActionCommand(btn.name());
			this.add(menuItem);
		}
	}

	public void init(GEDrawingPanel dp) {
		drawingPanel = dp;
	}
	
	private void setLineColor() {
		Color lineColor = JColorChooser.showDialog(null, GEConstants.LINECOLOR_TITLE,null);
		if(lineColor!=null){
			drawingPanel.setLineColor(lineColor);
		}
	}
	
	private void setFillColor() {
		Color fillColor = JColorChooser.showDialog(null, GEConstants.FILECOLOR_TITLE,null);
		if(fillColor!=null){
			drawingPanel.setFillColor(fillColor);
		}
	}
	
	private void delLineColor() {
		drawingPanel.setLineColor(GEConstants.COLOR_LINE_DEFAULT);
	}
	
	private void delFillColor() {
		drawingPanel.setFillColor(GEConstants.COLOR_FILL_DEFAULT);
	}
	
	private class ColorMenuHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EColorMenuItems.valueOf(e.getActionCommand())){
			case SetLineColor:
				setLineColor();
				break;
			case SetFillColor:
				setFillColor();
				break;
			case ClearLineColor:
				delLineColor();
				break;
			case ClearFillColor:
				delFillColor();
				break;
			
			}
			
		}

		

		

		

		
		
	}

}
