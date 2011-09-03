public class Application extends PolygonElement {

  PolygonElement P;
  PointElement C;

  // Apply a polygon P to get a parallelogram
  // with a side AB in an angle CAB

  Application (PolygonElement Pval, PointElement A, PointElement B,
	PointElement Cval) {
    dimension = 2;
    n = 4;
    P = Pval; C = Cval;
    V = new PointElement[4];
    V[0] = A; V[1] = B;
    V[2] = new PointElement();
    V[3] = new PointElement();
    if (A.AP == B.AP && A.AP == C.AP) {
      V[2].AP = A.AP;
      V[3].AP = A.AP;
  } }

  protected void translate (double dx, double dy) {
    V[2].translate(dx,dy);
    V[3].translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double ac, double as) {
    V[2].rotate(pivot,ac,as);
    V[3].rotate(pivot,ac,as);
  }

  protected void update() {
    double factor = P.area()/(2.0*PointElement.area(V[0],V[1],C));
    factor = Math.abs(factor);
    V[3].x = V[0].x + factor*(C.x-V[0].x);
    V[3].y = V[0].y + factor*(C.y-V[0].y);
    V[3].z = V[0].z + factor*(C.z-V[0].z);
    V[2].x = V[1].x + V[3].x - V[0].x;
    V[2].y = V[1].y + V[3].y - V[0].y;
    V[2].z = V[1].z + V[3].z - V[0].z;
} }

