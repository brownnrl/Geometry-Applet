public class Harmonic extends PointElement {

  // The harmonic conjugate A of B with respect to the points C and D.  If B,C,D 
  // are collinear,then A also lies on that line; if not, A lies on the 
  // circumcircle through B,C,D.
  //
  // If B,C,D lie in the x-y plane, then they may be treated as complex
  // numbers, and then A has the quotient of C(B-D)+D(B-C) divided by
  // (B-D)+(B-C).
  // More generally, A can be computed as follows:  Let M be the midpoint of BC.
  // Then reflect the vector MB in the line BC and adjust it's length so that
  // MB:MC=MC:MA.

  PointElement B,C,D;
  PointElement E,F,M;  // temporary variables

  Harmonic (PointElement Bval, PointElement Cval, PointElement Dval) {
    dimension = 0;
    B = Bval;  C = Cval;  D = Dval;
    E = new PointElement(); F = new PointElement(); M = new PointElement();
  }

  protected void update() {
    if (B.z==0.0 && C.z==0.0 && D.z==0.0) {
      // let E = B-C and F = B-D
      double Ex = B.x-C.x, Ey = B.y-C.y;
      double Fx = B.x-D.x, Fy = B.y-D.y;
      double BFx = C.x*Fx-C.y*Fy, BFy = C.x*Fy+C.y*Fx;
      double DEx = D.x*Ex-D.y*Ey, DEy = D.x*Ey+D.y*Ex;
      double den = (Fx+Ex)*(Fx+Ex) + (Fy+Ey)*(Fy+Ey);
      double numx = (BFx+DEx)*(Fx+Ex) + (BFy+DEy)*(Fy+Ey);
      double numy = -(BFx+DEx)*(Fy+Ey) + (BFy+DEy)*(Fx+Ex);
      x = numx/den; y = numy/den;
    } else { // 3d case
      M.to(C).plus(D).times(0.5);	// M = (C+D)/2
      F.to(C).minus(M);			// F = C-M
      E.to(B).minus(M);			// E = B-M
      double rB2 = 1.0/E.length2();
      double C2 = F.length2();
      this.to(F).times(2.0*dot(E,F)).minus(E.times(C2)).times(rB2).plus(M);
  } }

}
