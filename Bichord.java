public class Bichord extends LineElement {

  /*--------------------------------------------------------------------+
  | This line AB connects the two points where the two circles C and D	|
  | meet. It is assumed that the two circles lie in the same plane, the	|
  | plane of C.								|
  +--------------------------------------------------------------------*/

  CircleElement C,D;

  Bichord (CircleElement Cval, CircleElement Dval) {
    dimension = 1;
    A = new PointElement();  B = new PointElement();
    C = Cval;  D = Dval;
    A.AP = B.AP = C.AP;
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
    double r = C.radius();
    double s = D.radius();
    double d = C.Center.distance(D.Center);
    if (d > r + s) {
      A.x = A.y = B.x = B.y = A.z = B.z = 0.0/0.0;
      return;
    }
    double costheta = (d*d + r*r - s*s) / (2.0 * d * r);
    double sintheta = Math.sqrt(1.0-costheta*costheta);
    A.to(D.Center).toCircle(C);
    B.to(A);
    A.rotate(C.Center,costheta,sintheta,C.AP);
    B.rotate(C.Center,costheta,-sintheta,C.AP);
} }
