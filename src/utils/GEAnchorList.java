package utils;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Vector;

import constants.GEConstants;
import constants.GEConstants.EAnchorType;

public class GEAnchorList {

	private Vector<Ellipse2D.Double> anchors;
	public GEAnchorList(){
		anchors = new Vector<Ellipse2D.Double>();
		for(int i=0;i<GEConstants.EAnchorType.values().length-1;i++){
			anchors.add(new Ellipse2D.Double(0,0,0,0));
		}
	}
	
	
	//모든 앵커들의 위치를 선정하는 메소드
	public void setPosition(Rectangle r){
		int x = r.x;
		int y = r.y;
		int w = r.width;
		int h = r.height;
		int dx = GEConstants.ANCHOR_W/2;
		int dy = GEConstants.ANCHOR_H/2;
		
		anchors.get(GEConstants.EAnchorType.NW.ordinal()).setFrame(x-dx, y-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.NN.ordinal()).setFrame(x+w/2-dx, y-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.NE.ordinal()).setFrame(x+w-dx, y-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.WW.ordinal()).setFrame(x-dx, y+h/2-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.EE.ordinal()).setFrame(x+w-dx, y+h/2-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.SW.ordinal()).setFrame(x-dx, y+h-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.SS.ordinal()).setFrame(x+w/2-dx, y+h-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.SE.ordinal()).setFrame(x+w-dx, y+h-dy, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
		anchors.get(GEConstants.EAnchorType.RR.ordinal()).setFrame(x+w/2-dx, y-GEConstants.RR_OFFSET, GEConstants.ANCHOR_W, GEConstants.ANCHOR_H);
	}
	
	public EAnchorType onAnchors(Point p){
		for(Ellipse2D ellipse:anchors){
			if(ellipse.contains(new Point (p))){
				return EAnchorType.values()[anchors.indexOf(ellipse)];
			}
		}
		return EAnchorType.NONE;
	}
	
	public void draw(Graphics2D g2D){
		for(int i=0;i<EAnchorType.values().length-1;i++){
			Ellipse2D.Double ellipse = anchors.get(i);
			g2D.setColor(GEConstants.ANCHOR_FILECOLOR);
			g2D.fill(ellipse);
			g2D.setColor(GEConstants.ANCHOR_LINECOLOR);
			g2D.draw(ellipse);
		}
	}
}
