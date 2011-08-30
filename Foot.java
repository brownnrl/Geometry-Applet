public class Foot extends PointElement {

  PointElement A,B,C;	  // foot of perpendicular from point A to line BC

  Foot (PointElement Aval, PointElement Bval, PointElement Cval) {
    dimension = 0;
    A = Aval;  B = Bval;  C = Cval;
    if (B.AP == C.AP)
      AP = B.AP;
  }

  protected void update() {this.to(A).toLine(B,C,false);}
}
