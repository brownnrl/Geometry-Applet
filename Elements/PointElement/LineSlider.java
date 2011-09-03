public class LineSlider extends PointElement {

  PointElement A,B;
  double initx, inity, initz;
  boolean segment;

  LineSlider (PointElement Aval, PointElement Bval,
              double xVal, double yVal, double zVal, boolean segVal) {
    dimension = 0;
    dragable = true;
    A = Aval;  B = Bval;
    x = initx = xVal;
    y = inity = yVal;
    z = initz = zVal;
    segment = segVal;
    if (A.AP == B.AP)
      AP = A.AP;
  }

  protected void reset() {
    x = initx; y = inity; z = initz;
    toLine(A,B,segment);
  }

  protected void update() {
    toLine(A,B,segment);
  }

  protected boolean drag (double tox, double toy) {
    // first move (tox,toy) under the shadow of the line
    tox -= A.x; toy -= A.y;
    double dx = B.x - A.x,  dy = B.y - A.y;
    double factor = (tox*dx + toy*dy)/(dx*dx + dy*dy);
    if (segment) {
      if (factor < 0.0) factor = 0.0;
      else if (factor > 1.0) factor = 1.0;
    }
    tox = A.x + dx*factor;  toy = A.y + dy*factor;
    if ((tox-x)*(tox-x) + (tox-y)*(tox-y) < 0.5) return false;
    // now drag this point
    x = tox; y = toy;
    z = A.z + (B.z-A.z)*factor;
    return true;
  }
}
