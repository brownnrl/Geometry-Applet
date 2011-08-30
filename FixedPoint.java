public class FixedPoint extends PointElement {

  PointElement A,B;
  double initx, inity, initz;

  FixedPoint (double xVal, double yVal, double zVal) {
    dimension = 0;
    x = initx = xVal;
    y = inity = yVal;
    z = initz = zVal;
  }

  protected void reset() {
    x = initx; y = inity; z = initz;
} }
