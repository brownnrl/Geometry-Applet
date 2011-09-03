public class Chord extends LineElement {

  /*--------------------------------------------------------------------+
  | This line AB is the segment of the line DE (extended) that		|
  | interects the circle C.  It is assumed that DE and C lie in the	|
  | same plane, namely that of C.					|
  +--------------------------------------------------------------------*/

  PointElement D,E;
  CircleElement C;

  Chord (PointElement Dval, PointElement Eval, CircleElement Cval) {
    dimension = 1;
    C = Cval;  D = Dval;  E = Eval;
    A = new PointElement(C.AP);
    B = new PointElement(C.AP);
  }

  protected void translate (double dx, double dy) {
    A.translate(dx,dy);
    B.translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double ac, double as) {
    A.rotate(pivot,ac,as);
    B.rotate(pivot,ac,as);
  }

  protected void update() {
    B.to(C.Center).toLine(D,E,false);
    double d2 = C.Center.distance2(B);
    double r2 = C.radius2();
    if (d2 > r2) {
      A.x = A.y = A.z = B.x = B.y = B.z = 0.0/0.0;
      return;
    }
    double s = Math.sqrt(r2 - d2);
    double factor = s/D.distance(B);
    if (factor < 1e10)
      A.to(D).minus(B).times(factor).plus(B);
    else {
      factor = s/E.distance(B);
      A.to(E).minus(B).times(factor).plus(B);
    }
    B.times(2.0).minus(A);
} }

