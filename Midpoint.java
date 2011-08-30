public class Midpoint extends PointElement {

  PointElement A,B;

  Midpoint (PointElement Aval, PointElement Bval) {
    dimension = 0;
    A = Aval;  B = Bval;
    if (A.AP == B.AP)
      AP = A.AP;
  }

  protected void update() {
    x = (A.x + B.x) / 2.0;
    y = (A.y + B.y) / 2.0;
    z = (A.z + B.z) / 2.0;
} }
