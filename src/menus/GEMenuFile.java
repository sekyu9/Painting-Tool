package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import shape.GEShape;
import constants.GEConstants.EFileMenuItems;
import frames.GEDrawingPanel;

public class GEMenuFile extends JMenu {

	private GEDrawingPanel drawingPanel;
	private FileMenuHandler fileMenuHandler;
	
	public GEMenuFile(String label){
		super(label);
		fileMenuHandler = new FileMenuHandler();
		for(EFileMenuItems btn : EFileMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(fileMenuHandler);
			menuItem.setActionCommand(btn.toString());
			this.add(menuItem);
			}
		}
	
	public void init(GEDrawingPanel dp){
		drawingPanel = dp;
	}
	
	private void newFile(){
		drawingPanel.clearShapeList();
	}
	
	private void open(){
		JFileChooser fileDialog = new JFileChooser(new File("."));
		fileDialog.showOpenDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectInputStream in = null;
		if(file!=null){
			try{
				in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
				Object obj = in.readObject();
				drawingPanel.setShapeList((ArrayList<GEShape>) obj);
				
			} catch(IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try{
					if(in != null) in.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void save(){
		JFileChooser fileDialog = new JFileChooser(new File("."));
		fileDialog.showSaveDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectOutputStream out = null;
		if(file!=null){
			try{
				out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				out.writeObject(drawingPanel.getShapeList());
			} catch(IOException e){
				e.printStackTrace();
			} finally {
				try{
					if(out != null) out.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void close(){
		System.exit(0);
	}
	
	private class FileMenuHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (EFileMenuItems.valueOf(e.getActionCommand())) {
			case New:
				newFile();
				break;
			case Open:
				open();
				break;
			case Save:
				save();
				break;
			case SaveAs:
				break;
			case ESC:
				close();
				break;
			}
			
		}		
	}
}


