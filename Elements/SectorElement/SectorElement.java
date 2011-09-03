/*----------------------------------------------------------------------+
|	Title:	SectorElement.java					|
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

public class SectorElement extends Element {

  PointElement Center, A, B;
  PlaneElement P; 	// plane of the circle
  // A and B are two points on a circle with the given center

  SectorElement () {dimension = 2;}

  SectorElement (PointElement Oval, PointElement Aval, PointElement Bval,
                 PlaneElement Pval) {
    dimension = 2;
    Center = Oval; A = Aval; B = Bval; P = Pval;
  }

  public String toString() {
    return "[" + name + ": Center="+Center + " A="+ A + " B=" + B + "]";
  }

  double radius() {return Center.distance(A);}
  double radius2() {return Center.distance2(A);}

  protected void update () {P.update();}

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
      int startAngle = - (int)Math.round(Math.atan2(A.y-Center.y,A.x-Center.x)*180.0/Math.PI);
      int arcAngle = -(int)Math.round(Center.angle(A,B,P)*180.0/Math.PI);
      if (arcAngle < 0)
	arcAngle += 360;
      g.drawArc((int)Math.round(Center.x-r),(int)Math.round(Center.y-r),
		 d,d, startAngle, arcAngle);
  }   } 

  protected void drawFace (Graphics g) {
    if (faceColor!=null  && defined()) {
      g.setColor(faceColor);
      double r = radius();
      int d = (int)Math.round(2*r);
      int startAngle = - (int)Math.round(Math.atan2(A.y-Center.y,A.x-Center.x)*180.0/Math.PI);
      int arcAngle = -(int)Math.round(Center.angle(A,B,P)*180.0/Math.PI);
      if (arcAngle < 0)
	arcAngle += 360;
      g.fillArc((int)Math.round(Center.x-r),(int)Math.round(Center.y-r),
		 d,d, startAngle, arcAngle);
} }   } 


