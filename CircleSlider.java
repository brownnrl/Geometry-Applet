public class CircleSlider extends PointElement {

  CircleElement C;
  double initx, inity, initz;

  CircleSlider (CircleElement Cval, double xVal, double yVal, double zVal) {
    dimension = 0;
    dragable = true;
    C = Cval;
    x = initx = xVal;
    y = inity = yVal;
    z = initz = zVal;
  }

  protected void reset () {
    x = initx; y = inity; z = initz;
    toCircle(C);
  }

  protected void update() {
    toCircle(C);
  }

  PointElement newP = new PointElement();

  protected boolean drag (double tox, double toy) {
    x = tox; y = toy;
    if (!defined()) z = initz;
    newP.to(this).uptoPlane(C.AP);
    if (!newP.defined())	// vertical plane
      newP.to(this).toPlane(C.AP);
    newP.toSphere(C.Center,C.radius());
    if ((newP.x-x)*(newP.x-x) + (newP.y-y)*(newP.y-y) < 0.5)
      return false;
    this.to(newP);
    return true;
} }
