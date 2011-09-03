public class Intersection extends PointElement {
  // this point is the intersection of lines AB and CD in the ambient plane AP

  PointElement A,B,C,D;

  Intersection (PointElement Aval, PointElement Bval, PointElement Cval,
                PointElement Dval, PlaneElement APval) {
    dimension = 0;
    A = Aval;  B = Bval;  C = Cval;  D = Dval;  AP = APval;
  }

  protected void update() {toIntersection(A,B,C,D,AP);}
}
