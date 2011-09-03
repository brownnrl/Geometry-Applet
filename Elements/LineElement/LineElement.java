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

  protected void drawEdge (Graphics g) {drawEdge(A,B,g,edgeColor);}
}


