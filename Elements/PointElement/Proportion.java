public class Proportion extends PointElement {

  // Given lines S,T,U,V, cut off from V a fourth proportional V' so that
  // S:T=U:V'

  PointElement S0,S1,T0,T1,U0,U1,V0,V1;

  Proportion (PointElement S0val, PointElement S1val, PointElement T0val,
	PointElement T1val, PointElement U0val, PointElement U1val,
	PointElement V0val, PointElement V1val ) {
    dimension = 0;
    S0 = S0val; S1 = S1val;
    T0 = T0val; T1 = T1val;
    U0 = U0val; U1 = U1val;
    V0 = V0val; V1 = V1val;
    if (V0.AP == V1.AP)
      AP = V0.AP;
  }

  protected void update() {
    double factor = T0.distance2(T1) * U0.distance2(U1) 
		  / (S0.distance2(S1) * V0.distance2(V1));
    factor = Math.sqrt(factor);
    this.to(V1).minus(V0);
    this.times(factor).plus(V0);
} }