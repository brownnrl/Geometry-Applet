/*----------------------------------------------------------------------+
|	Title:	SphereElement.java					|
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
|	Date:	May, 1997.						|
+----------------------------------------------------------------------*/

import java.awt.*;

public class SphereElement extends Element {

  PointElement Center, A, B;	// radius is AB

  SphereElement () {dimension = 2;}

  SphereElement (PointElement Oval, PointElement Aval, PointElement Bval) {
    dimension = 2;
    Center = Oval; A = Aval; B = Bval;
  }

  SphereElement (PointElement Oval, PointElement Bval) {
    dimension = 2;
    Center = Oval; A = Oval; B = Bval;
  }

  public String toString() {
    return "[" + name + ": Center="+Center + " A="+ A + " B=" + B + "]";
  }

  protected void drawName (Graphics g, Dimension d) {
    if (nameColor!=null && name!=null) {
      g.setColor(nameColor);
      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(name);
      int h = fm.getHeight();
      g.drawString(name, (int)Center.x - w/2,
			 (int)Center.y - h/2 + fm.getAscent());
  } }

  protected boolean defined() {
    return true;
//    return Center.defined() && A.defined() && B.defined();
  }

  protected void drawEdge (Graphics g) {
    if (edgeColor!=null && defined()) {
      g.setColor(edgeColor);
      double r = radius();
      int d = (int)Math.round(2*r);
      g.drawOval((int)Math.round(Center.x-r),(int)Math.round(Center.y-r),d,d);
  }   } 

  protected void drawFace (Graphics g) {
    if (faceColor!=null  && defined()) {
      g.setColor(faceColor);
      double r = radius();
      int d = (int)Math.round(2*r);
      g.fillOval((int)Math.round(Center.x-r),(int)Math.round(Center.y-r),d,d);
  }   } 


  double radius() {return A.distance(B);}
  double radius2() {return A.distance2(B);}
}

