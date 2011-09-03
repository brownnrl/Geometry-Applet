public class InvertPoint extends PointElement {

  // Invert the point A in the circle C to get this point

  PointElement A;
  CircleElement C;

  InvertPoint (PointElement Aval, CircleElement Cval) {
    dimension = 0;
    A = Aval;  C = Cval;
    AP = C.AP;
  }

  protected void update() {toInvertPoint(A,C);}

}
