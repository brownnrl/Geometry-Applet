/*----------------------------------------------------------------------+
|	Title:	PolygonElement.java					|
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

public class PolygonElement extends Element {

  int n;
  PointElement V[];	// array of vertices
  
  PolygonElement() {dimension = 2;}
  
  PolygonElement (int nVal) {
    dimension = 2;    
    n = nVal;
    V = new PointElement[n];
  }

  PolygonElement (PointElement A, PointElement B, PointElement C) {
    dimension = 2;    n = 3;
    V = new PointElement[3];
    V[0] = A;  V[1] = B;  V[2] = C;
  }

  PolygonElement (PointElement A, PointElement B, PointElement C,
                  PointElement D) {
    dimension = 2;    n = 4;
    V = new PointElement[4];
    V[0] = A;  V[1] = B;  V[2] = C;  V[3] = D;
  }

  PolygonElement (PointElement A, PointElement B, PointElement C,
                  PointElement D, PointElement E) {
    dimension = 2;   n = 5;
    V = new PointElement[5];
    V[0] = A;  V[1] = B;  V[2] = C;  V[3] = D; V[4] = E;
  }

  PolygonElement (PointElement A, PointElement B, PointElement C,
                  PointElement D, PointElement E, PointElement F) {
    dimension = 2;    n = 6;
    V = new PointElement[6];
    V[0] = A;  V[1] = B;  V[2] = C;  V[3] = D; V[4] = E; V[5] = F;
  }

  PolygonElement (PointElement A, PointElement B, PointElement C,
                  PointElement D, PointElement E, PointElement F,
		  PointElement G, PointElement H) {
    dimension = 2;    n = 8;
    V = new PointElement[8];
    V[0] = A;  V[1] = B;  V[2] = C;  V[3] = D;
    V[4] = E;  V[5] = F;  V[6] = G;  V[7] = H;
  }
  
  public String toString() {
    return "[" + name + ": n=" + n + "]";
  }

  protected boolean defined() {
    for (int i=0; i<n; ++i) 
      if (!V[i].defined()) return false;
    return true;
  }

  protected void drawName (Graphics g, Dimension d) {
    if (nameColor!=null && name!=null && defined()) {
      g.setColor(nameColor);
      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(name);
      int h = fm.getHeight();
      double x=0, y=0;
      for (int i=0; i<n; ++i) {
	x += V[i].x; y += V[i].y;
      }
      x /= n; y /= n;
      g.drawString(name, (int)x - w/2, (int)y - h/2 + fm.getAscent());
  } }


  protected void drawVertex (Graphics g) {
    if (vertexColor != null && defined())
      for (int i=1; i<n; ++i)
        V[i].drawVertex(g,vertexColor);
  }

  protected void drawEdge (Graphics g) {
    if (edgeColor != null && defined()) 
      for (int i=0; i<n; ++i) 
        LineElement.drawEdge(V[i],V[(i+1)%n],g,edgeColor);
  }
      
  protected void drawFace (Graphics g) {drawFace(g,faceColor);}

  protected void drawFace (Graphics g, Color clr) {
    if (clr != null && defined()) {
      g.setColor(clr);
      int ix[] = new int[n];
      int iy[] = new int[n];
      for (int i=0; i<n; ++i) {
        ix[i] = (int)Math.round(V[i].x);
        iy[i] = (int)Math.round(V[i].y);
      }
      g.fillPolygon(ix,iy,n);
  } }

  double area() {
    // compute the area of this polygon (assuming it's planar & convex)
    double sum = 0.0;
    for (int i=0; i<n-2; ++i) {
      sum += PointElement.area(V[0],V[i+1],V[i+2]);
    }
    return sum;
} }



