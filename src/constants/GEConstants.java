package constants;

import java.awt.Color;

public class GEConstants {

	//GEMainFrame
	public static final int widthMainFrame = 400;//���� ����
	public static final int heightMainFrame = 600;//���� ����
	public static final String TitleMainFrame = "Graphic Editor Iteration 1";//Ÿ��Ʋ �� �̸�
	
	//GEMenu
	public static final String TitleFileMenu = "File";
	public static final String TitleEditMenu = "Edit";
	public static final String TitleColorMenu = "Color";
	
	//GEMenuitems
	public static enum EFileMenuItems{New, Open, Save, SaveAs, ESC};
	public static enum EEditMenuItems{Undo, Redo, Delete, Cut, Copy, Paste, Group, UndoGroup};
	public static enum EColorMenuItems{SetLineColor, ClearLineColor, SetFillColor, ClearFillColor};
	
	//GEToolbarShape
	public static final String TITLE_SHAPETOOLBAR = "Shape Tool";
	public static int WIDTH_SHAPETOOLBAR = 30;
	public static int HEIGHT_SHAPETOOLBAR = 200;
	
	public static enum ETooLBarButtons { Select, Rectangle, Line, Ellipse, Polygon};
	public static final String IMG_URL = "images/";
	public static final String SUFFIX_TOOLBAR_BTN=".gif";
	public static final String SUFFIX_TOOLBAR_BTN_SLT="SLT.gif";
	
	//GEDrawingPanel
	public static final Color FOREGROUND_COLOR = Color.BLACK;//���� ���� ����
	public static final Color BACKGROUND_COLOR = Color.WHITE;//���� ����
	public static enum EState {Idle, TwoPointsDrawing, NPointsDrawing, Moving,Resize,Selecting};//  �׸��� ���¸� ǥ���ϴ� ����
	public static final Color COLOR_LINE_DEFAULT = Color.black;
	public static final Color COLOR_FILL_DEFAULT = Color.WHITE;
	//Idle = ó�� ������ �׸��� �����ϴ� ����
	//TwoPointDrawing = �� ���� ��ǥ�� �׸� �� �ִ� ����(�簢��, ��, ��)
	//NPointDrawing = �ٰ��� �׸��� ����
	
	//GEAnchorList
	public static final int ANCHOR_W = 6;//Anchor's Width
	public static final int ANCHOR_H = 6;//Anchor's Height
	public static final int RR_OFFSET = 40;//Anchor�� �ֻ�� ��Ŀ�� ��ġ ��� �Ҵ�
	public static final Color ANCHOR_LINECOLOR = Color.BLACK;//���� ��
	public static final Color ANCHOR_FILECOLOR = Color.WHITE;//�������
	public static enum EAnchorType {NW,NN,NE,WW,EE,SW,SS,SE,RR,NONE}//��Ŀ Ÿ�� 9���� ����(Ellipse2D ��ü ������ ����)
	
	//GECmenuColor
	public static final String FILECOLOR_TITLE = "Selected Fill Color";
	public static final String LINECOLOR_TITLE = "Selected Line Color";
	
	//GETransformer
	public final static int DEFAULT_DASH_OFFSET = 4;
	public final static int DEFAULT_DASHDELINE_WIDTH = 1;
}
