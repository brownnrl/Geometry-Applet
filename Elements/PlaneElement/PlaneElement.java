/*----------------------------------------------------------------------+
|	Title:	PlaneElement.java					|
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

public class PlaneElement extends Element {

  /*--------------------------------------------------------------------+
  | The plane is represented by three points on it.  It's displayed as  |
  | a parallelogram with three vertices A, B, and C,projected onto the  |
  | xy-plane. S is a unit vector in the direction AB, T is a 	        |
  | perpendicular unit in the plane, and U is perpendicular to both.	|					|								|
  +--------------------------------------------------------------------*/
  PointElement A,B,C;
  PointElement S,T,U;
  boolean isScreen = false;  // set true only for initial screen plane
  PointElement pivot;	// pivot point for the plane, if any

  PlaneElement () {dimension = 2;}

  PlaneElement (PointElement Aval, PointElement Bval, PointElement Cval) {
    dimension = 2;
    A = Aval; B = Bval; C = Cval;
    S = new PointElement(); T = new PointElement(); U = new PointElement();
  }

  public String toString() {
    return "[" + name + ": " + A + " " + B + " " + C + "]";
  }

  protected void rotate (PointElement pivot, double ac, double as) {
    update();
  }

  protected void update () {
    if (isScreen && (A.z!=0.0 || B.z!=0.0 || C.z!=0.0))
      isScreen = false;
    // update the frame S,T,U
    S.to(B).minus(A);
    T.to(C).minus(A);
    S.times(1.0/S.length());
    double st = PointElement.dot(T,S);
    T.x -= st*S.x; 
    T.y -= st*S.y; 
    T.z -= st*S.z; 
    T.times(1.0/T.length());
    U.toCross(S,T);
  }

  protected boolean defined() {
    return A.defined() && B.defined() && C.defined();
  }

  protected void drawName (Graphics g, Dimension d) {
    if (nameColor!=null && name!=null && defined()) {
      g.setColor(nameColor);
      int ix = (int)Math.round((B.x+C.x)/2.0);
      int iy = (int)Math.round((B.y+C.y)/2.0);
      drawString(ix,iy, g,d);
  } }

  PointElement D = new PointElement();	// temporary used in drawing

  protected void drawVertex (Graphics g) {
    if (vertexColor != null && defined()) {
      A.drawVertex(g,vertexColor);
      B.drawVertex(g,vertexColor);
      C.drawVertex(g,vertexColor);
      D.x = B.x + C.x - A.x;
      D.y = B.y + C.y - A.y;
      D.z = B.z + C.z - A.z;
      D.drawVertex(g,vertexColor);
  } }

  protected void drawEdge (Graphics g) {
    if (edgeColor!=null  && defined()) {
      D.x = B.x + C.x - A.x;
      D.y = B.y + C.y - A.y;
      D.z = B.z + C.z - A.z;
      LineElement.drawEdge(A,B,g,edgeColor);
      LineElement.drawEdge(B,D,g,edgeColor);
      LineElement.drawEdge(D,C,g,edgeColor);
      LineElement.drawEdge(C,A,g,edgeColor);
  } }
  
  protected void drawFace (Graphics g) {
    if (faceColor!=null  && defined()) {
      g.setColor(faceColor);
      int ix[] = new int[4];
      int iy[] = new int[4];
      ix[0] = (int)Math.round(A.x);
      iy[0] = (int)Math.round(A.y);
      ix[1] = (int)Math.round(B.x);
      iy[1] = (int)Math.round(B.y);
      ix[2] = (int)Math.round(B.x + C.x - A.x);
      iy[2] = (int)Math.round(B.y + C.y - A.y);
      ix[3] = (int)Math.round(C.x);
      iy[3] = (int)Math.round(C.y);
      g.fillPolygon(ix,iy,4);
} } }


