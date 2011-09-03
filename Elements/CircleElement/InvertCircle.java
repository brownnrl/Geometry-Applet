public class InvertCircle extends CircleElement {

  /*--------------------------------------------------------------------+
  | Invert the circle C in the circle D to get this circle.  It is	|
  | assumed that C and D lie in the same plane C.AP.			|
  +--------------------------------------------------------------------*/

  CircleElement C, D;		// Invert C in D to get this

  InvertCircle (CircleElement Cval, CircleElement Dval) {
    dimension = 2;
    AP = C.AP;
    Center = new PointElement(AP);
    A = Center;  
    B = new PointElement(AP);
    C = Cval;    D = Dval;
  }

  protected void update() {
    double d2 = C.Center.distance2(D.Center);
    double r2 = C.radius2();
    double factor = D.radius2()/(d2-r2);
    Center.to(C.Center).minus(D.Center).times(factor).plus(D.Center);
    factor = 1.0 + Math.sqrt(r2/d2);
    B.to(Center).minus(D.Center).times(factor).plus(D.Center);
  }

  protected void translate (double dx, double dy) {
    Center.translate(dx,dy);
    B.translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double ac, double as) {
    Center.rotate(pivot,ac,as);
    B.rotate(pivot,ac,as);
} }
