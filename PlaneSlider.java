public class PlaneSlider extends PointElement {

  // this point can be dragged anywhere on the ambient plane AP
  
  double initx, inity, initz;

  PlaneSlider (PlaneElement Qval, double xVal, double yVal, double zVal) {
    dimension = 0;
    dragable = true;
    AP = Qval;
    x = initx = xVal;
    y = inity = yVal;
    z = initz = zVal;
  }
  
  protected void update() {
    toPlane(AP);
  }

  protected void reset() {
    x = initx; y = inity; z = initz;
    toPlane(AP);
  }

  PointElement newP = new PointElement();
  
  protected boolean drag (double tox, double toy) {
    x = tox; y = toy;
    if (!defined()) z = initz;
    newP.to(this).uptoPlane(AP);
    if (newP.defined())
      this.to(newP);
    else 
      this.toPlane(AP);
    return true;
} }
