/*----------------------------------------------------------------------+
|	Title:	PointElement.java					|
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
|	Date:	February, 1996.   Version 2.0.0 May, 1997.		|
+----------------------------------------------------------------------*/

import java.awt.*;

public class PointElement extends Element {

  double x,y,z;		// coordinates of the point
  PlaneElement AP;  	// ambient plane
  
  PointElement () { }
  PointElement (PlaneElement APval) {AP = APval;}
  PointElement (double xVal, double yVal, double zVal) {
    x = xVal; y = yVal; z = zVal;
  }
  
  protected boolean defined() {
    return  !Double.isNaN(x) && !Double.isNaN(y) && !Double.isNaN(z);
  }
 
  public String toString() {
    return "[" + name + "=(" +x+ "," +y+ "," +z+ ")]";
  }
  
  PointElement to(PointElement B) {x = B.x; y = B.y; z = B.z; return this;}
  PointElement plus(PointElement B) {x += B.x; y += B.y; z += B.z; return this;}
  PointElement minus(PointElement B) {x -= B.x; y -= B.y; z -= B.z; return this;}
  PointElement times(double a) {x *= a; y *= a; z *= a; return this;}
  static PointElement sum(PointElement A, PointElement B) {
    return new PointElement(A.x+B.x, A.y+B.y, A.z+B.z);
  }
  static PointElement difference(PointElement A, PointElement B) {
    return new PointElement(A.x-B.x, A.y-B.y, A.z-B.z);
  }
  static PointElement product(double a, PointElement B) {
    return new PointElement(a*B.x, a*B.y, a*B.z);
  }
  public static double dot(PointElement A, PointElement B) {
    return A.x*B.x + A.y*B.y + A.z*B.z;
  }

  public double length2() {return x*x + y*y + z*z;}
  public double length() {return Math.sqrt(x*x + y*y + z*z);}
  public double distance2(PointElement B) {
    return (x-B.x)*(x-B.x) + (y-B.y)*(y-B.y) + (z-B.z)*(z-B.z);
  }
  public double distance(PointElement B) {
    return Math.sqrt((x-B.x)*(x-B.x) + (y-B.y)*(y-B.y) +
                     (z-B.z)*(z-B.z));
  }

  public PointElement toCross(PointElement A, PointElement B) {
    // set to the cross product of A and B
    x = A.y*B.z - A.z*B.y;
    y = A.z*B.x - A.x*B.z;
    z = A.x*B.y - A.y*B.x;
    return this;
  }
  
  public static PointElement cross(PointElement A, PointElement B) {
    // return the cross product of A and B
    return new PointElement(A.y*B.z - A.z*B.y, A.z*B.x - A.x*B.z, A.x*B.y - A.y*B.x);
  }
  
  public static double triple(PointElement A, PointElement B, PointElement C) {
    // return the triple product of A, B, and C
    return A.x*(B.y*C.z - B.z*C.y)
         + B.x*(C.y*A.z - C.z*A.y)
         + C.x*(A.y*B.z - A.z*B.y);
  }

  PointElement toLine (PointElement A, PointElement B, boolean segment) {
    /*---------------------------------------------------------------------+
    |  Project this point to the foot of the perpendicular from it to the  |
    |  line determined by the points A and B. If A were the origin, then   |
    |  the foot would be at ((this dot B)/B^2) B.  When segment is true    |
    |  and the foot is beyond A or B, then move the point to the closer    |
    |  of A and B.							   |
    +---------------------------------------------------------------------*/
    PointElement V = difference(B,A);
    this.minus(A);
    double factor = dot(V,this)/V.length2();
    if (segment) {
      if (factor < 0.0) factor = 0.0;
      else if (factor > 1.0) factor = 1.0;
    }
    V.times(factor);
    return this.to(V).plus(A);
  }

   PointElement toPlane (PlaneElement P) {
    /*---------------------------------------------------------------------+
    |  Project this point to the foot of the perpendicular from it to the  |
    |  plane P.                         				   |
    +---------------------------------------------------------------------*/
    if (P.isScreen)
      z = 0.0;
    else {
      this.minus(P.A);
      double s = dot(this,P.S),   t = dot(this,P.T);
      this.to(P.S).times(s).plus(product(t,P.T)).plus(P.A);
    }
    return this;
  }

   PointElement uptoPlane (PlaneElement P) {
    /*---------------------------------------------------------------------+
    |  Project this point to the point on the plane P where the vertical   |
    |  line through this meets P.             				   |
    +---------------------------------------------------------------------*/
    if (P.isScreen)
      z = 0.0;
    else {
      this.minus(P.A);
      double den = P.S.x*P.T.y - P.S.y*P.T.x;
      double s = (x*P.T.y - y*P.T.x)/den;
      double t = (y*P.S.x - x*P.S.y)/den;
      this.to(P.S).times(s).plus(product(t,P.T)).plus(P.A);
    }
    return this;
  }

  PointElement toCircumcenter (PointElement A, PointElement B, PointElement C) {
    /*---------------------------------------------------------------------+
    | Move this point to the center of the circle passing through the      |
    | points A, B, and C.                                                  |
    +---------------------------------------------------------------------*/
    if (A.z == 0.0 && B.z == 0.0 && C.z == 0.0) {
      double u = ((A.x-B.x)*(A.x+B.x) + (A.y-B.y)*(A.y+B.y)) / 2.0;
      double v = ((B.x-C.x)*(B.x+C.x) + (B.y-C.y)*(B.y+C.y)) / 2.0;
      double den = (A.x-B.x)*(B.y-C.y) - (B.x-C.x)*(A.y-B.y);
      x = (u * (B.y-C.y) - v*(A.y-B.y)) / den;
      y = (v * (A.x-B.x) - u*(B.x-C.x)) / den;
      z = 0.0;
    } else {
      PointElement BmA = difference(B,A), CmA = difference(C,A);
      double BC = dot(BmA,CmA);
      double B2 = BmA.length2(), C2 = CmA.length2();
      double BC2 = BC*BC;       
      double den = 2.0*(B2*C2-BC*BC);
      double s = C2*(B2-BC)/den;
      double t = B2*(C2-BC)/den;
      this.to(A).plus(BmA.times(s)).plus(CmA.times(t));
    }
    return this;
  }

  PointElement toCircle (CircleElement C) {
    /*---------------------------------------------------------------------+
    | Move this point to the nearest point on the circle C.                |
    +---------------------------------------------------------------------*/
    if (C.AP.isScreen) {
      double factor = C.radius() / distance(C.Center);
      x = C.Center.x + factor*(x - C.Center.x);
      y = C.Center.y + factor*(y - C.Center.y);
      z = 0.0;
    } else { // 3d case: project to plane of circle then move to sphere of circle
      toPlane(C.AP);
      toSphere(C.Center,C.radius());
    }
    return this;
  }

  PointElement toSphere (PointElement Center, double radius) {
    /*---------------------------------------------------------------------+
    | Move this point to the nearest point on the sphere S.                |
    +---------------------------------------------------------------------*/
    double factor = radius / distance(Center);
    x = Center.x + factor*(x - Center.x);
    y = Center.y + factor*(y - Center.y);
    z = Center.z + factor*(z - Center.z);
    return this;
  }

  public static double area(PointElement A, PointElement B, PointElement C) {
    // return the area of the triangle ABC
    PointElement U = difference(B,A);
    PointElement V = difference(C,A);
    return cross(U,V).length()/2.0;
  }
  
  public double angle(PointElement B, PointElement C, PlaneElement P) {
    // Determine the angle BAC in the plane P where this is A.
    // The angle lies between -pi and pi (-180 degrees and 180 degrees)
    double Bx = B.x - x,	Cx = C.x - x;
    double By = B.y - y,	Cy = C.y - y;
    if (P.isScreen)
      return Math.atan2 (Bx*Cy - By*Cx, Bx*Cx + By*Cy);
    else { // 3d case.  First get P-coordinates for B and C
      double Bz = B.z - z,	Cz = C.z - z;
      double Bs = Bx*P.S.x + By*P.S.y + Bz*P.S.z ;
      double Bt = Bx*P.T.x + By*P.T.y + Bz*P.T.z ;
      double Cs = Cx*P.S.x + Cy*P.S.y + Cz*P.S.z ;
      double Ct = Cx*P.T.x + Cy*P.T.y + Cz*P.T.z ;
      return Math.atan2 (Bs*Ct - Bt*Cs, Bs*Cs + Bt*Ct);
  } }
  
  protected void translate (double dx, double dy) {
    // translate by (dx,dy) in the x-y plane
    x += dx; y += dy;
  }


  PointElement toIntersection (PointElement A, PointElement B,
		       PointElement C, PointElement D, PlaneElement P) {
    if (P.isScreen) {
      // move this point to where the two lines AB and CD meet
      double d0 = A.x*B.y - A.y*B.x;
      double d1 = C.x*D.y - C.y*D.x;
      double den = (B.y-A.y)*(C.x-D.x) - (A.x-B.x)*(D.y-C.y);
      x = (d0*(C.x-D.x) - d1*(A.x-B.x)) / den;
      y = (d1*(B.y-A.y) - d0*(D.y-C.y)) / den;
    } else { // 3d case
      PointElement AmA = difference(A,P.A), BmA = difference(B,P.A);
      PointElement CmA = difference(C,P.A), DmA = difference(D,P.A);
      double Ax = dot(AmA,P.S),  Ay = dot(AmA,P.T);
      double Bx = dot(BmA,P.S),  By = dot(BmA,P.T);
      double Cx = dot(CmA,P.S),  Cy = dot(CmA,P.T);
      double Dx = dot(DmA,P.S),  Dy = dot(DmA,P.T);
      double d0 = Ax*By - Ay*Bx;
      double d1 = Cx*Dy - Cy*Dx;
      double den = (By-Ay)*(Cx-Dx) - (Ax-Bx)*(Dy-Cy);
      double s = (d0*(Cx-Dx) - d1*(Ax-Bx)) / den;
      double t = (d1*(By-Ay) - d0*(Dy-Cy)) / den;
      this.to(P.S).times(s).plus(product(t,P.T)).plus(P.A);
    }
    return this;
  }
  
  PointElement toIntersectionPL (PlaneElement P, PointElement D,
		       PointElement E) {
    // move this point to where the plane P meets the line DE
    this.to(E).minus(D); 
    PointElement DmA = difference(D,P.A);
    double u = -triple(P.S,P.T,DmA)/triple(P.S,P.T,this);
    return this.times(u).plus(D);
  } 

  PointElement toInvertPoint (PointElement A, CircleElement C) {
    // move this point to the inversion of the point A in the circle C
    double factor = C.radius2() / A.distance2(C.Center);
    return this.to(A).minus(C.Center).times(factor).plus(C.Center);
  }
  
  PointElement toSimilar (PointElement A, PointElement B, PlaneElement P,
                  PointElement D, PointElement E, PointElement F, PlaneElement Q) {
    // move this point to the location C so that triangle ABC is similar
    // to triangle DEF.
    double theta = D.angle(E,F,Q);
    double co = Math.cos(theta), si = Math.sin(theta);
    double factor = D.distance(F) / D.distance(E);
    if (P.isScreen) {
      x = B.x; y = B.y;
      rotate(A,co,si,P);
      x = A.x + factor*(x - A.x);
      y = A.y + factor*(y - A.y);
      z = 0.0;
    } else {
      PointElement BmA = difference(B,A);
      double  s = dot(BmA,P.S),  t = dot(BmA,P.T);
      double ss = factor*(co*s - si*t);
      double tt = factor*(si*s + co*t);
      x = ss*P.S.x + tt*P.T.x + A.x;
      y = ss*P.S.y + tt*P.T.y + A.y;
      z = ss*P.S.z + tt*P.T.z + A.z;
    }
    return this;
  }
  
  protected void rotate (PointElement pivot, double ac, double as) {
    rotate (pivot,ac,as,pivot.AP);
  }
  
  protected void rotate (PointElement pivot, double ac, double as,
   	PlaneElement plane) {
    /*--------------------------------------------------------------------------+
    | Scale and rotate this point around the axis through the pivot and		|
    | perpendicular to the plane.  Scale by a factor of a, and rotate by the	|
    | angle theta where ac = a cos theta, and as = a sin theta.			|
    +--------------------------------------------------------------------------*/
    if (this == pivot) return;
    if (plane.isScreen) {
      double dx = x - pivot.x;
      double dy = y - pivot.y;
      x = pivot.x + ac*dx - as*dy;
      y = pivot.y + as*dx + ac*dy;
    } else {
      this.minus(pivot);
      PointElement S = plane.S, T = plane.T, U = plane.U;
      double s = dot(this,S);
      double t = dot(this,T);
      double z1 = dot(this,U);
      double x1 = ac*s - as*t;
      double y1 = as*s + ac*t;
      x = pivot.x + x1*S.x + y1*T.x + z1*U.x;
      y = pivot.y + x1*S.y + y1*T.y + z1*U.y;
      z = pivot.z + x1*S.z + y1*T.z + z1*U.z;
  } }
  
  protected void drawName (Graphics g, Dimension d) {
    if (nameColor!=null && name!=null && defined())
      drawString((int)Math.round(x),(int)Math.round(y), g,d);
  }

  protected void drawVertex (Graphics g) {drawVertex(g,vertexColor);}

  public void drawVertex (Graphics g, Color c) {
    if (c != null && defined()) {
      g.setColor(c);
      g.fillOval ((int)Math.round(x) - 2, (int)Math.round(y) - 2, 4, 4);
  } }
}

