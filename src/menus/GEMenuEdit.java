package menus;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.text.DefaultEditorKit.PasteAction;

import shape.GEGroup;
import shape.GEShape;
import constants.GEConstants.EColorMenuItems;
import constants.GEConstants.EEditMenuItems;
import frames.GEDrawingPanel;

public class GEMenuEdit extends JMenu{

	private GEDrawingPanel drawingPanel;
	private EditMenuHandler editMenuHandler;
	private ArrayList<GEShape> copyList;
	
	
	public GEMenuEdit(String label){
		super(label);
		editMenuHandler = new EditMenuHandler();
		for(EEditMenuItems btn :EEditMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(editMenuHandler);
			menuItem.setActionCommand(btn.toString());
			this.add(menuItem);			
		}
		copyList = new ArrayList<GEShape>();
	}
	
	public void init(GEDrawingPanel dp){
		drawingPanel = dp;
	}
	
	public void ungroup(){
		drawingPanel.unGroup();
	}
	
	private void group(){
		drawingPanel.group(new GEGroup());
	}
	
	private void copy(){
		copyList.clear();
		copyList.addAll(drawingPanel.copy());
	}
	
	private void paste(){
		drawingPanel.paste(copyList);
	}
	
	private void cut(){
		copyList.clear();
		copyList.addAll(drawingPanel.cut());
	}
	
	private void delete(){
		drawingPanel.delete();
	}
	private class EditMenuHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EEditMenuItems.valueOf(e.getActionCommand())){
			case Group:
				group();
				break;
			case UndoGroup:
				ungroup();
				break;
			case Redo:
				break;
			case Undo:
				break;
			case Delete:
				delete();
				break;
			case Cut:
				cut();
				break;
			case Copy:
				copy();
				break;
			case Paste:
				paste();
				break;
			}	
		}
	}
}
