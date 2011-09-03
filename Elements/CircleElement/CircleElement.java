/*----------------------------------------------------------------------+
|	Title:	CircleElement.java					|
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
|	Date:	February, 1996. Version 2.0.0 May, 1997.		|
+----------------------------------------------------------------------*/

import java.awt.*;

public class CircleElement extends Element {


  /*--------------------------------------------------------------------+
  | The circle has center Center, and radius AB, and lies in plane AP.	|
  +--------------------------------------------------------------------*/


  PointElement Center, A, B;	// radius is AB
  PlaneElement AP;		// ambient plane of the circle

  CircleElement () {dimension = 2;}

  CircleElement (PointElement Oval, PointElement Aval, PointElement Bval,
                 PlaneElement APval) {
    dimension = 2;
    Center = Oval; A = Aval; B = Bval; AP = APval;
  }

  CircleElement (PointElement Oval, PointElement Bval, PlaneElement APval) {
    dimension = 2;
    Center = Oval; A = Oval; B = Bval; AP = APval;
  }

  public String toString() {
    return "["+name+": Center="+Center+" A="+A+" B="+B +" AP="+AP+"]";
  }

  double radius() {return A.distance(B);}
  double radius2() {return A.distance2(B);}

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
//    return Center.defined() && A.defined() && B.defined() && AP.defined();
  }

  protected void drawEdge (Graphics g) {
    if (edgeColor!=null  && defined()) {
      g.setColor(edgeColor);
      double r2 = radius2();
      double r = Math.sqrt(r2);
      double amp2 = AP.S.z*AP.S.z + AP.T.z*AP.T.z;
      if (Math.abs(amp2) < 0.01) { // the circle is flat
	g.drawOval((int)Math.round(Center.x-r),
		   (int)Math.round(Center.y-r),
                   (int)Math.round(2.0*r),(int)Math.round(2.0*r));
        return;
      }
      double h = r/Math.sqrt(amp2);
      // determine major and minor radius vectors
      double rcos = h*AP.T.z;
      double rsin = -h*AP.S.z;
      double majorx = rcos*AP.S.x + rsin*AP.T.x;
      double majory = rcos*AP.S.y + rsin*AP.T.y;
      double factor = (amp2 < 1.0)? Math.sqrt(1.0-amp2) : 0.0;
      double minorx = -factor*majory;
      double minory = factor*majorx;
      drawEllipse(g,(int)Math.round(Center.x),(int)Math.round(Center.y),
            majorx,majory,minorx,minory);
  }   }

  protected void drawFace (Graphics g) {
    if (faceColor!=null  && defined()) {
      g.setColor(faceColor);
      double r2 = radius2();
      double r = Math.sqrt(r2);
      double amp2 = AP.S.z*AP.S.z + AP.T.z*AP.T.z;
      if (Math.abs(amp2) < 0.01) { // the circle is flat
	g.fillOval((int)Math.round(Center.x-r),
		   (int)Math.round(Center.y-r),
                   (int)Math.round(2.0*r),(int)Math.round(2.0*r));
        return;
      }
      double h = r/Math.sqrt(amp2);
      // determine major and minor radius vectors
      double rcos = h*AP.T.z;
      double rsin = -h*AP.S.z;
      double majorx = rcos*AP.S.x + rsin*AP.T.x;
      double majory = rcos*AP.S.y + rsin*AP.T.y;
      double factor = (amp2 < 1.0)? Math.sqrt(1.0-amp2) : 0.0;
      double minorx = -factor*majory;
      double minory = factor*majorx;
      fillEllipse(g,(int)Math.round(Center.x),(int)Math.round(Center.y),
            majorx,majory,minorx,minory);
  }   }

  public static void fillEllipse(Graphics g,int Cx,int Cy,
          double a, double b, double c, double d) {
  /*--------------------------------------------------------------------+
  | Fill an ellipse whose center is at (Cx,Cy), and perpendicular	|
  | radius  vectors are (a,b) and (c,d).  If the ellipse isn't tilted,	|
  | then fillOval still works.  Otherwise, the equation of the		|
  | origin-centered ellipse is found in the form x^2 - 2Bxy + Cy^2 = D	|
  | where B is (ab+cd)/(b^2+d^2), C is (a^2+c^2)/(b^2+d^2), and D is	|
  | (ad-bc)^2/(b^2+d^2).  The maximum and minimum y values on this	|
  | ellipse are +/- sqrt(D/(C-B^2)).  For a given y, the max and min	|
  | x values are By +/- sqrt((B^2-C)y^2+CD).				|
  +--------------------------------------------------------------------*/
    if (Math.abs(a) < 0.5 && Math.abs(d)<0.5) {
      // axes are vert & horiz, so can still use fillOval
      b = Math.abs(b); c = Math.abs(c);
      g.fillOval(Cx-(int)Math.round(c),Cy-(int)Math.round(b),
        (int)Math.round(2.0*c),(int)Math.round(2.0*b));
      return;
    }
    if (Math.abs(b) < 0.5 && Math.abs(c)<0.5) {
      // axes are horiz & vert, so can still use fillOval
      a = Math.abs(a); d = Math.abs(d);
      g.fillOval(Cx-(int)Math.round(a),Cy-(int)Math.round(d),
        (int)Math.round(2.0*a),(int)Math.round(2.0*d));
      return;
    } 
    if (Math.abs(c) + Math.abs(d) < 0.5) {
      // ellipse is so thin it looks like a line
      g.drawLine(Cx-(int)a,Cy-(int)b,Cx+(int)a,Cy+(int)b);
      return;
    } 
    if (Math.abs(a) + Math.abs(b) < 0.5) {
      // ellipse is so thin it looks like a line
      g.drawLine(Cx-(int)c,Cy-(int)d,Cx+(int)c,Cy+(int)d);
      return;
    } // otherwise the ellipse is really tilted
    double b2d2 = b*b+d*d;
    double B = (a*b+c*d)/b2d2, B2 = B*B;
    double C = (a*a+c*c)/b2d2, B2mC= B2-C;
    double D = a*d-b*c;  D = D*D/b2d2;
    int maxy = (int)Math.round(Math.sqrt(D/(C-B2)));
    for (int y=0; y<=maxy; ++y) {
      if (Cy+y < 0) break;
      double w = Math.sqrt(B2mC*(y*y)+D);
      double By = B*y;
      g.drawLine((int)(Cx+By+w),Cy+y,(int)(Cx+By-w),Cy+y);
      g.drawLine((int)(Cx-By+w),Cy-y,(int)(Cx-By-w),Cy-y);
  } }

  public static void drawEllipse(Graphics g,int Cx,int Cy,
          double a, double b, double c, double d) {
  /*--------------------------------------------------------------------+
  | Similar to fillEllipse except only the circumference is drawn.	|
  +--------------------------------------------------------------------*/
    if (Math.abs(a) < 0.5 && Math.abs(d)<0.5) {
      // axes are vert & horiz, so can still use drawOval
      b = Math.abs(b); c = Math.abs(c);
      g.drawOval((int)Math.round(Cx-c),(int)Math.round(Cy-b),
                 (int)Math.round(2.0*c),(int)Math.round(2.0*b));
      return;
    }
    if (Math.abs(b) < 0.5 && Math.abs(c)<0.5) {
      // axes are horiz & vert, so can still use drawOval
      a = Math.abs(a); d = Math.abs(d);
      g.drawOval((int)Math.round(Cx-a),(int)Math.round(Cy-d),
                 (int)Math.round(2.0*a),(int)Math.round(2.0*d));
      return;
    }
    if (Math.abs(c) + Math.abs(d) < 0.5) {
      // ellipse is so thin it looks like a line
      g.drawLine(Cx-(int)a,Cy-(int)b,Cx+(int)a,Cy+(int)b);
      return;
    } 
    if (Math.abs(a) + Math.abs(b) < 0.5) {
      // ellipse is so thin it looks like a line
      g.drawLine(Cx-(int)c,Cy-(int)d,Cx+(int)c,Cy+(int)d);
      return;
    } // otherwise the ellipse is really tilted
    double b2d2 = b*b+d*d;
    double B = (a*b+c*d)/b2d2, B2 = B*B;
    double C = (a*a+c*c)/b2d2, B2mC= B2-C;
    double D = a*d-b*c;  D = D*D/b2d2;
    int maxy = (int)Math.floor(Math.sqrt(D/(C-B2)));
    int olduleft=0,olduright=0,oldlleft=0,oldlright=0;
    for (int y=0; y<=maxy; ++y) {
      int uy = Cy-y, ly = Cy+y;
      if (ly < 0) break;
      double w = Math.sqrt(B2mC*(y*y)+D);
      double By = B*y;
      int uleft = (int)(Cx-By-w), uright = (int)(Cx-By+w);
      int lleft = (int)(Cx+By-w), lright = (int)(Cx+By+w);
      if (y == 0) {
        g.drawLine(uleft,uy,uleft,uy);
        g.drawLine(uright,uy,uright,uy);
      } else {
        g.drawLine(uleft,uy,olduleft,uy);
        g.drawLine(olduright,uy,uright,uy);
        g.drawLine(lleft,ly,oldlleft,ly);
        g.drawLine(oldlright,ly,lright,ly);
      }
      olduleft = uleft; olduright = uright;
      oldlleft = lleft; oldlright = lright;
    }
    g.drawLine(olduleft,Cy-maxy-1,olduright,Cy-maxy-1);
    g.drawLine(oldlleft,Cy+maxy+1,oldlright,Cy+maxy+1);
} }


