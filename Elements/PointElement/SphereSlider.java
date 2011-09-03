public class SphereSlider extends PointElement {

  SphereElement S;
  double initx, inity, initz;

  SphereSlider (SphereElement Sval, double xVal, double yVal, double zVal) {
    dimension = 0;
    dragable = true;
    S = Sval;
    x = initx = xVal;
    y = inity = yVal;
    z = initz = zVal;
  }
  
  protected void reset() {
    x = initx; y = inity; z = initz;
    toSphere(S.Center,S.radius());
  }

  protected void update() {
    toSphere(S.Center,S.radius());
  }

  protected boolean drag (double tox, double toy) {
    double dist2 = (S.Center.x-tox)*(S.Center.x-tox) + (S.Center.y-toy)*(S.Center.y-toy);
    double r2 = S.radius2();
    if (dist2 <= r2) {
      x = tox; y = toy;
      if (z > S.Center.z)
        z = S.Center.z + Math.sqrt(r2 - dist2);
      else
        z = S.Center.z - Math.sqrt(r2 - dist2);
    }
    else { // beyond shadow of sphere
      tox -= S.Center.x; toy -= S.Center.y;
      double factor = Math.sqrt((tox*tox + toy*toy)/r2);
      tox = tox/factor + S.Center.x;
      toy = toy/factor + S.Center.y;
      if ((tox-x)*(tox-x) + (toy-y)*(toy-y) < 0.5) return false;
      x = tox; y = toy; z = S.Center.z;
    }
    return true;
} }
