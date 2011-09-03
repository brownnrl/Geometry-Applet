public class Arc extends SectorElement {

  // construct an AMB arc of a circle 

  PointElement M;		// Along with A and B, the three given points

  Arc (PointElement Aval, PointElement Mval, PointElement Bval, PlaneElement Pval) {
    dimension = 2;
    Center = new PointElement();
    A = Aval;  M = Mval;  B = Bval;
    P = Pval;	// the plane of the arc
  }

  protected void update() {
    P.update();
    Center.toCircumcenter(A,M,B);
  }

  protected void translate (double dx, double dy) {Center.translate(dx,dy);}

  protected void rotate (PointElement pivot, double ac, double as) {
    Center.rotate(pivot,ac,as);
} }
