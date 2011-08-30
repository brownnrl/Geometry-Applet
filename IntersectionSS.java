public class IntersectionSS extends CircleElement {

  /*--------------------------------------------------------------------+
  | This circle is the intersection of two spheres S and T. The center	|
  | of the circle is Center, which is A, lies in the plane AP, and	|
  | has radius AB.							|
  +--------------------------------------------------------------------*/

  SphereElement S, T; // data

  IntersectionSS (SphereElement Sval, SphereElement Tval) {
    dimension = 2;
    S = Sval; T = Tval;
    Center = A = new PointElement();
    B = new PointElement();
    AP = new PerpendicularPL(Center,T.Center);  // the ambient plane
    Center.AP = AP;
  }

  protected void update() {
    double d2 = T.Center.distance2(S.Center);
    double t2 = T.radius2(); 
    double factor = 0.5 + (t2-S.radius2())/(2.0*d2);
    Center.to(S.Center).minus(T.Center).times(factor).plus(T.Center);
    double radius = Math.sqrt(t2-Center.distance2(T.Center));
    B.to(Center);
    B.z += radius;
    AP.update();  // that updates the plane of the circle
  }

  protected void translate (double dx, double dy) {
    Center.translate(dx,dy);
    B.translate(dx,dy);
    AP.translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double c, double s) {
    Center.rotate(pivot,c,s);
    B.rotate(pivot,c,s);
    AP.rotate(pivot,c,s);
} }
