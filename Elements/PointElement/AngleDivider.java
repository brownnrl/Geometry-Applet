public class AngleDivider extends PointElement {

  PointElement A,B,C;	// n-sect angle BAC in the ambient plane AP
  int n;

  AngleDivider (PointElement Bval, PointElement Aval, PointElement Cval,
                PlaneElement APval, int nVal) {
    dimension = 0;
    A = Aval;  B = Bval;  C = Cval;  AP = APval; n = nVal;
  }

  protected void update() {
    double theta = A.angle(B,C,AP)/n;
    double cos = Math.cos(theta);
    double sin = Math.sin(theta);
    if (AP.isScreen) {
      x = A.x + cos*(B.x-A.x) - sin*(B.y-A.y);
      y = A.y + sin*(B.x-A.x) + cos*(B.y-A.y);
      z = 0.0;
    } else { // 3D case
      this.to(B).rotate(A,cos,sin,AP);
    }
    this.toIntersection(this,A,B,C,AP);
} }
