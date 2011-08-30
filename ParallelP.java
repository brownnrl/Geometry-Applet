public class ParallelP extends PlaneElement {

  // this is the plane parallel to the plane P passing through the point A
  
  PlaneElement P;

  ParallelP (PlaneElement Pval, PointElement Aval) {
    dimension = 2;
    P = Pval;  A = Aval;
    B = new PointElement(this);  C = new PointElement(this);
    S = P.S;  T = P.T;  U = P.U;
  }

  protected void translate (double dx, double dy) {
    B.translate(dx,dy);  C.translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double ac, double as) {
    B.rotate(pivot,ac,as);  C.rotate(pivot,ac,as);
  }

  protected void update() {
    B.to(P.B).minus(P.A).plus(A);
    C.to(P.C).minus(P.A).plus(A);
  }
}
