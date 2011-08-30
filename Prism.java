public class Prism extends PolyhedronElement {

  // The base is given as well as a line segment CD for the sides to be
  // parallel to.

  PointElement C,D;

  Prism (PolygonElement Base, PointElement Cval, PointElement Dval) {
    dimension = 2;
    C = Cval; D = Dval;
    n = 2+Base.n;
    P = new PolygonElement[n];
    P[0] = Base;
    // create the top
    P[1] = new PolygonElement(Base.n);
    for (int j=0; j<Base.n; ++j) {
      P[1].V[j] = new PointElement();
      P[1].V[j].to(Base.V[j]).plus(D).minus(C);
    }
    // create the sides
    for (int i=2; i<n; ++i)
      P[i] = new PolygonElement(Base.V[i-2],Base.V[(i-1)%Base.n],
                  P[1].V[(i-1)%Base.n],P[1].V[i-2]);
  }

  protected void translate (double dx, double dy) {
    // translate the top vertices
    for (int i=0; i<P[1].n; ++i)
      P[1].V[i].translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double c, double s) {
    // rotate the top vertices
    for (int i=0; i<P[1].n; ++i)
      P[1].V[i].rotate(pivot,c,s);
  }

  protected void update() {
    // update the top vertices
    for (int j=0; j<P[1].n; ++j)
      P[1].V[j].to(P[0].V[j]).plus(D).minus(C);
} }