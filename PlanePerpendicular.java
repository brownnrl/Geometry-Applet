public class PlanePerpendicular extends LineElement {

  // draw perpendicular at C to the plane P.  A is the proj of C onto P, while
  // AB is perpendicular to P and equal to DE
  
  PointElement C,D,E;
  PlaneElement P;
  // The line AB will be perpendicular to the plane P.  A will be the projection
  // of C on to the plane P.  AB will equal DE.

  PlanePerpendicular (PointElement Cval, PlaneElement Pval,
                      PointElement Dval, PointElement Eval) {
    dimension = 1;
    A = new PointElement(P); 
    B = new PointElement();
    C = Cval; D = Dval; E = Eval; P = Pval;
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
    A.to(C).toPlane(P);
    B.toCross(P.S,P.T);
    B.times(D.distance(E)).plus(A);
} }
