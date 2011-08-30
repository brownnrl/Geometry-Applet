public class RegularPolygon extends PolygonElement {

  /*--------------------------------------------------------------------+
  | The regular polygon has n vertices in the plane P, with two, A and	|
  | B, being given. The variables cos and sin are the cosine and sine	|
  | of an internal angle.  Regular star polygons are given by the	|
  | second constructor with density d, that is, edges connect every	|
  | d-th vertex.							|
  +--------------------------------------------------------------------*/


  double cos, sin;
  PlaneElement P;

  RegularPolygon (PointElement A, PointElement B, PlaneElement Pval, int nVal) {
    dimension = 2;
    n = nVal;
    P = Pval;
    V = new PointElement[n];
    double theta = Math.PI * (n-2.0)/n;
    cos = Math.cos(theta);
    sin = Math.sin(theta);
    V[0] = A; V[1] = B;
    for (int i=2; i<n; ++i)
      V[i] = new PointElement(P);
  }

  RegularPolygon (PointElement A, PointElement B, PlaneElement Pval,
                  int nVal, int d) {
    dimension = 2;
    n = nVal;
    P = Pval;
    V = new PointElement[n];
    double theta = Math.PI * d*(n-2.0)/n;
    cos = Math.cos(theta);
    sin = Math.sin(theta);
    V[0] = A; V[1] = B;
    for (int i=2; i<n; ++i)
      V[i] = new PointElement(P);
  }

  protected void translate (double dx, double dy) {
    for (int i=2; i<n; ++i)
      V[i].translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double c, double s) {
    for (int i=2; i<n; ++i)
      V[i].rotate(pivot,c,s,P);
  }

  protected void update() {
    for (int i=2; i<n; ++i)
      V[i].to(V[i-2]).rotate(V[i-1],cos,sin,P);
} }

