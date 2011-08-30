public class IntersectionPL extends PointElement {
  // this point is the intersection of the ambient plane AP and the line AB

  PointElement A,B;

  IntersectionPL (PlaneElement APval, PointElement Aval, PointElement Bval) {
    dimension = 0;
    A = Aval;  B = Bval;  AP = APval;
  }

  protected void update() {toIntersectionPL(AP,A,B);}
}
