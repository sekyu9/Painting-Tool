package constants;

import java.awt.Color;

public class GEConstants {

	//GEMainFrame
	public static final int widthMainFrame = 400;//가로 길이
	public static final int heightMainFrame = 600;//세로 길이
	public static final String TitleMainFrame = "Graphic Editor Iteration 1";//타이틀 바 이름
	
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
	public static final Color FOREGROUND_COLOR = Color.BLACK;//도형 윤곽 색깔
	public static final Color BACKGROUND_COLOR = Color.WHITE;//도형 배경색
	public static enum EState {Idle, TwoPointsDrawing, NPointsDrawing, Moving,Resize,Selecting};//  그리기 상태를 표현하는 변수
	public static final Color COLOR_LINE_DEFAULT = Color.black;
	public static final Color COLOR_FILL_DEFAULT = Color.WHITE;
	//Idle = 처음 도형을 그리기 시작하는 상태
	//TwoPointDrawing = 두 개의 좌표로 그릴 수 있는 상태(사각형, 원, 선)
	//NPointDrawing = 다각형 그리기 상태
	
	//GEAnchorList
	public static final int ANCHOR_W = 6;//Anchor's Width
	public static final int ANCHOR_H = 6;//Anchor's Height
	public static final int RR_OFFSET = 40;//Anchor의 최상단 앵커의 위치 상수 할당
	public static final Color ANCHOR_LINECOLOR = Color.BLACK;//윤곽 색
	public static final Color ANCHOR_FILECOLOR = Color.WHITE;//도형면색
	public static enum EAnchorType {NW,NN,NE,WW,EE,SW,SS,SE,RR,NONE}//앵커 타입 9개의 방향(Ellipse2D 객체 관리를 위해)
	
	//GECmenuColor
	public static final String FILECOLOR_TITLE = "Selected Fill Color";
	public static final String LINECOLOR_TITLE = "Selected Line Color";
	
	//GETransformer
	public final static int DEFAULT_DASH_OFFSET = 4;
	public final static int DEFAULT_DASHDELINE_WIDTH = 1;
}
