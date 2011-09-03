public class Pyramid extends PolyhedronElement {

  Pyramid (PolygonElement Base, PointElement Apex) {
    dimension = 2;
    n = 1+Base.n;
    P = new PolygonElement[n];
    P[0] = Base;
    for (int i=1; i<n; ++i)
      P[i] = new PolygonElement(Apex,Base.V[i-1],Base.V[i%Base.n]);
  }

}