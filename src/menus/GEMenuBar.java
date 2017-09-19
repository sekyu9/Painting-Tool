package menus;

import javax.swing.JMenuBar;

import constants.GEConstants;
import frames.GEDrawingPanel;

public class GEMenuBar extends JMenuBar{

	private GEMenuFile fileMenu;
	private GEMenuEdit editMenu;
	private GEMenuColor colorMenu;
	
	//»ý¼ºÀÚ
	public GEMenuBar(){
		fileMenu = new GEMenuFile(GEConstants.TitleFileMenu);
		this.add(fileMenu);
		editMenu = new GEMenuEdit(GEConstants.TitleEditMenu);
		this.add(editMenu);
		colorMenu = new GEMenuColor(GEConstants.TitleColorMenu);
		this.add(colorMenu);
		}

	public void init(GEDrawingPanel dp) {
		// TODO Auto-generated method stub
		colorMenu.init(dp);
		editMenu.init(dp);
		fileMenu.init(dp);
	}
}
