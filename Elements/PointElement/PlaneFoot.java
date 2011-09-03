public class PlaneFoot extends PointElement {

  PointElement A;
  PlaneElement P;  // foot of perpendicular from point A to the ambient plane AP

  PlaneFoot (PointElement Aval, PlaneElement APval) {
    dimension = 0;
    A = Aval;  AP = APval;
  }

  protected void update() {this.to(A).toPlane(AP);}
}
