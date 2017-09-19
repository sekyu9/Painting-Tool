package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import menus.GEMenuBar;
import constants.GEConstants;

public class GEMainFrame extends JFrame{

	private static GEMainFrame uniqueMainFrame = new GEMainFrame(GEConstants.TitleMainFrame);//MainFrame ��ü ����(������ ��ü)
	private GEDrawingPanel drawingPanel;
	private GEMenuBar menuBar;
	private GEToolBar shapeToolBar;
	
	//������ ����
	private GEMainFrame(String title){
		
		super(title);
		drawingPanel = new GEDrawingPanel();
		add(drawingPanel);
		menuBar = new GEMenuBar();
		setJMenuBar(menuBar);
		shapeToolBar = new GEToolBar(GEConstants.TITLE_SHAPETOOLBAR);
		add(BorderLayout.NORTH, shapeToolBar);
	}
	
	public static GEMainFrame getInstance(){
		return uniqueMainFrame; 
	}
	
	public void init(){
		
		menuBar.init(drawingPanel);
		shapeToolBar.init(drawingPanel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(GEConstants.widthMainFrame, GEConstants.heightMainFrame);
		setVisible(true);
	}
}
