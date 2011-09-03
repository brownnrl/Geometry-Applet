/*----------------------------------------------------------------------+
|	Title:	PolyhedronElement.java					|
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

public class PolyhedronElement extends Element {

  int n;
  PolygonElement P[];	// array of polygons

  PolyhedronElement () {
  }

  public String toString() {
    return "[" + name + ": n=" + n + "]";
  }

  protected boolean defined() {
    for (int i=0; i<n; ++i) 
      if (!P[i].defined()) return false;
    return true;
  }

  protected void drawName (Graphics g, Dimension d) {
    if (nameColor!=null && name!=null && defined()) {
      g.setColor(nameColor);
      FontMetrics fm = g.getFontMetrics();
      int w = fm.stringWidth(name);
      int h = fm.getHeight();
      double x=0, y=0;
      int ct = 0;
      for (int j=0; j<n; ++j)
        for (int i=0; i<P[j].n; ++i) {
	  x += P[j].V[i].x; 
	  y += P[j].V[i].y;
	  ++ct;
      }
      x /= ct; y /= ct;
      g.drawString(name, (int)x - w/2, (int)y - h/2 + fm.getAscent());
  } }


  protected void drawVertex (Graphics g) {
    if (vertexColor != null && defined())
      for (int j=0; j<n; ++j)
        for (int i=0; i<P[j].n; ++i)
        P[n].V[i].drawVertex(g,vertexColor);
  }

  protected void drawEdge (Graphics g) {
    if (edgeColor != null && defined()) 
      for (int j=0; j<n; ++j)
        for (int i=0; i<P[j].n; ++i)
          LineElement.drawEdge(P[j].V[i],P[j].V[(i+1)%P[j].n],g,edgeColor);
  }
      
  protected void drawFace (Graphics g) {
    if (faceColor != null && defined()) 
      for (int j=0; j<n; ++j)
        for (int i=0; i<P[j].n; ++i) 
          P[j].drawFace(g,faceColor);
  }
}


