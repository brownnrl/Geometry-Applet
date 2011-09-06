/*----------------------------------------------------------------------+
|	Title:	LineElement.java					|
|		Java Class extends Element				|
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

public class LineElement extends Element {
  PointElement A,B;

  LineElement () {dimension = 1;}

  LineElement (PointElement Aval, PointElement Bval) {
    dimension = 1;
    A = Aval; B = Bval;
  }

  public String toString() {
    return "[" + name + ": " + A + " " + B + "]";
  }

  protected boolean defined() {return A.defined() && B.defined();}

  public boolean hitTest(int x, int y)
  {
	  /*
	   * Algorithm taken from
	   * http://stackoverflow.com/questions/1073336/circle-line-collision-detection
	   * 9/06/2011
	   */
	  PointElement C = new PointElement((double)x,(double)y,0.0);
	  
	  PointElement direction = new PointElement(B.x-A.x,B.y-A.y, 0.0);
	  
	  PointElement toStart   = new PointElement(A.x-C.x, A.y-C.y, 0.0);
	  
	  double a = PointElement.dot(direction, direction);
	  double b = 2.0*PointElement.dot(toStart, direction);
	  double c = PointElement.dot(toStart,toStart) - (double)pixelTolerance;

	  double discriminant = b*b-4*a*c;
	  
	  if( discriminant < 0 )
	  {
	    // no intersection
		  return false;
	  }
	  else
	  {
	    // ray didn't totally miss sphere,
	    // so there is a solution to
	    // the equation.
		return true;
	  }
  }
  
  protected void drawName (Graphics g, Dimension d) {
    if (nameColor!=null && name!=null && defined()) {
      int ix = (int)Math.round((A.x+B.x)/2.0);
      int iy = (int)Math.round((A.y+B.y)/2.0);
      drawString(ix,iy, g,d);
  } }

  protected void drawVertex (Graphics g) {
    if (vertexColor != null && defined()) {
      A.drawVertex(g,vertexColor);
      B.drawVertex(g,vertexColor);
  } }

  public static void drawEdge (PointElement A, PointElement B, Graphics g, Color c) {
    if (c!=null  && A.defined() && B.defined()) {
      g.setColor(c);
      g.drawLine ((int)Math.round(A.x), (int)Math.round(A.y),
	          (int)Math.round(B.x), (int)Math.round(B.y));
  } }

  protected void drawEdge (Graphics g) 
  {
	  if(shouldHighlight)
		  drawEdge(A,B,g,edgeHighlightColor);
	  else
		  drawEdge(A,B,g,edgeColor);
  }
}


