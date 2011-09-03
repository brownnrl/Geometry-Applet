public class Perpendicular extends LineElement {

  // draw perpendicular to CD in the plane P.  A is the proj of 
  // C onto P, while AB is perpendicular to CD and equals EF.
  
  PointElement C,D,E,F;
  PlaneElement P;

  Perpendicular (PointElement Cval, PointElement Dval, PlaneElement Pval,
                 PointElement Eval, PointElement Fval) {
    dimension = 1;
    P = Pval;
    A = new PointElement(P);
    B = new PointElement(P);
    C = Cval; D = Dval;
    E = Eval; F = Fval;
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
    B.to(D).minus(A);
    double Bs = PointElement.dot(B,P.S);
    double Bt = PointElement.dot(B,P.T);
    double factor = Math.sqrt(E.distance2(F)/(Bs*Bs+Bt*Bt));
    Bs = -Bs/factor;
    Bt /= factor;
    B.x = Bt*P.S.x + Bs*P.T.x + A.x;
    B.y = Bt*P.S.y + Bs*P.T.y + A.y;
    B.z = Bt*P.S.z + Bs*P.T.z + A.z;
} }

