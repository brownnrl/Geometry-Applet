public class PerpendicularPL extends PlaneElement {

  // this is the plane perpendicular to the line AE at the point A
  
  PointElement E;

  PerpendicularPL (PointElement Aval, PointElement Eval) {
    dimension = 2;
    A = Aval;  E = Eval;
    B = new PointElement(this);  C = new PointElement(this);
    S = new PointElement();  T = new PointElement();
  }

  protected void translate (double dx, double dy) {
    B.translate(dx,dy);  C.translate(dx,dy);
  }

  protected void rotate (PointElement pivot, double ac, double as) {
    B.rotate(pivot,ac,as);  C.rotate(pivot,ac,as);
    super.update();
  }

  protected void update() {
    U = PointElement.difference(E,A);
    double len = U.length();
    U.times(1.0/len);
    double lxy = Math.sqrt(U.x*U.x + U.y*U.y);
    if (lxy >= 0.000001) {
      S.x = -U.y/lxy;
      S.y = U.x/lxy;
      S.z = 0.0;
      T.toCross(U,S);
    } else {
      S.x = 1.0; S.y = 0.0; S.z = 0.0;
      T.x = 0.0; T.y = 1.0; T.z = 0.0;
    }    
    B.to(S).times(len).plus(A);
    C.to(T).times(len).plus(A);
  }
}
