public class Layoff extends PointElement {

  // Lay off a line AB with endpoint A given, so that AB is parallel to CD
  // and equal to EF.  (this is B)

  PointElement A,C,D,E,F;

  Layoff (PointElement Aval, PointElement Cval, PointElement Dval,
          PointElement Eval, PointElement Fval) {
    dimension = 0;
    A = Aval;  C = Cval;
    D = Dval;  E = Eval;  F = Fval;
    if (A.AP == C.AP && A.AP == D.AP)
      AP = A.AP;
  }

  protected void update() {
    double factor = E.distance(F) / C.distance(D);
    this.to(D).minus(C);
    this.times(factor).plus(A);
} }
