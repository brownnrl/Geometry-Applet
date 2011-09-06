/*----------------------------------------------------------------------+
|	Title:	Element.java						|
|		Java Class						|
|									|
|	Author:	David E. Joyce						|
|		Department of Mathematics and Computer Science   	|
|		Clark University					|
|		Worcester, MA 01610-1477				|
|		U.S.A.							|
|									|
|		http://aleph0.clarku.edu/~djoyce/home.html		|
|		djoyce@clarku.edu					|
|									|
|	Date:	February, 1996.  Version 2.0.0 May, 1997.		|
+----------------------------------------------------------------------*/

import java.awt.*;
import java.lang.String;

public abstract class Element extends Object {
  String name;
  Color nameColor, vertexColor, edgeColor, faceColor;
  Color nameHighlightColor = Color.red;
  Color vertexHighlightColor = Color.WHITE;
  Color edgeHighlightColor   = Color.white;
  Color faceHighlightColor   = Color.CYAN;
  
  boolean dragable = false;
  int dimension;

  int align;
  final static int CENTRAL = 0;	// label positions
  final static int LEFT = 1;
  final static int RIGHT = 2;
  final static int ABOVE = 3;
  final static int BELOW = 4;
  
  boolean shouldHighlight = false;
  int pixelTolerance = 10;


  public void drawString (int ix, int iy, Graphics g, Dimension d) {
      g.setColor(nameColor);

      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(name);
      int h = fm.getHeight();
      switch (align) {
	case Element.LEFT:
	  g.drawString(name, ix-w-6, iy+h/2-4);
	  return;
	case Element.RIGHT:
	  g.drawString(name, ix+2, iy+h/2-4);
	  return;
	case Element.ABOVE:
	  g.drawString(name, ix-w/2, iy-h/2+4);
	  return;
	case Element.BELOW:
	  g.drawString(name, ix-w/2, iy+h/2+6);
	  return;
      }  // otherwise CENTRAL
      // compute (dx,dy) coordinates relative to center of canvas
      // and normalized
      int dx = (ix - d.width/2) * d.height;
      int dy = (iy - d.height/2) * d.width;
      if (dy > dx) {
	if (dy >= -dx)	// put name below
	  g.drawString(name, ix-w/2, iy+h/2+6);
	else 		// put name left
	  g.drawString(name, ix-w-6, iy+h/2-4);
      }	
      else {
	if (dy >= -dx)	// put name right
	  g.drawString(name, ix+2, iy+h/2-4);
	else 		// put name above
	  g.drawString(name, ix-w/2, iy-h/2+4);
  }   }

  boolean inClass (String className) {
    try {
      Class cl = getClass();
      Class ecl = Class.forName(className);
      if (!cl.equals(ecl) && !cl.getSuperclass().equals(ecl)) 
        return false;
    } catch (ClassNotFoundException exc) {
      return false;
    }
    return true;
  }

  public String getName()
  {
	  return name;
  }
  
  public boolean hitTest(int x, int y)
  {
	  return false;
  }
  
  public void setHighlight(boolean highlight) 
  {
	  this.shouldHighlight = highlight;
  }
  
  protected void reset() {update();}
  protected void update() {}
  protected void translate (double dx, double dy) {}
  protected boolean drag (double tox, double toy) {return false;}
    // drag returns true when the element is actually dragged
  protected void rotate (PointElement pivot, double ac, double as) {}
  protected boolean defined() {return false;}
  protected void drawName (Graphics g, Dimension d) {}
  protected void drawFace (Graphics g) {}
  protected void drawEdge (Graphics g) {}
  protected void drawVertex (Graphics g) {}
  
}



