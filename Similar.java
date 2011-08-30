public class Similar extends PointElement {

  /*--------------------------------------------------------------------+
  |  Construct a triangle ABthis in the ambient plane AP similar to the |
  |  triangle DEF in the plane Q.					|
  +--------------------------------------------------------------------*/
  
  PointElement A, B, D, E, F;
  PlaneElement Q;

  Similar (PointElement Aval, PointElement Bval, PlaneElement APval,
           PointElement Dval, PointElement Eval, PointElement Fval, 
           PlaneElement Qval) {
    dimension = 0;
    A = Aval; B = Bval; AP = APval;
    D = Dval; E = Eval; F = Fval; Q = Qval;
  }

 protected void update() {
    this.toSimilar(A,B,AP,D,E,F,Q);
} }

