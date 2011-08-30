public class Circumcircle extends CircleElement {

  /*--------------------------------------------------------------------+
  | This is the circle passing through the three points B, C, and D.	|
  | It is assumed that all three points lie in the plane AP.		|
  +--------------------------------------------------------------------*/

  PointElement C, D;		// Along with B, the three given points

  Circumcircle (PointElement Bval, PointElement Cval, PointElement Dval,
                PlaneElement APval) {
    dimension = 2;
    B = Bval;  C = Cval; D = Dval;  AP = APval;
    Center = new PointElement(AP);
    A = Center;  
  }

  protected void update() {Center.toCircumcenter(B,C,D);}

  protected void translate (double dx, double dy) {Center.translate(dx,dy);}

  protected void rotate (PointElement pivot, double c, double s) {
    Center.rotate(pivot,c,s);
} }
