public class MeanProportional extends PointElement {

  // Given lines S,T,U cut off from U a mean proportional U' so that
  // S:U'=U':T

  PointElement S0,S1,T0,T1,U0,U1;

  MeanProportional (PointElement S0val, PointElement S1val, PointElement T0val,
	            PointElement T1val, PointElement U0val, PointElement U1val) {
    dimension = 0;
    S0 = S0val; S1 = S1val;
    T0 = T0val; T1 = T1val;
    U0 = U0val; U1 = U1val;
    if (U0.AP == U1.AP) 
      AP = U0.AP;
  }

  protected void update() {
    double factor = Math.sqrt(S0.distance2(S1) * T0.distance2(T1)); 
    factor = Math.sqrt(factor / U0.distance2(U1));
    this.to(U1).minus(U0);
    this.times(factor).plus(U0);
} }
