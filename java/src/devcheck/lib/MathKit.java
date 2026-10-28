package devcheck.lib;

/** Reusable mathematical helpers used by the Java CLI. */
public final class MathKit {
    private MathKit() {}
    public static double add(double a, double b) { return a + b; }
    public static double subtract(double a, double b) { return a - b; }
    public static double multiply(double a, double b) { return a * b; }
    public static double divide(double a, double b) {
        if (b == 0.0) throw new IllegalArgumentException("cannot divide by zero");
        return a / b;
    }
    public static double mean(double[] values) {
        if (values.length == 0) throw new IllegalArgumentException("at least one number is required");
        double sum = 0; for (double v : values) sum += v; return sum / values.length;
    }
    public static double min(double[] values) {
        if (values.length == 0) throw new IllegalArgumentException("at least one number is required");
        double result = values[0]; for (double v : values) if (v < result) result = v; return result;
    }
    public static double modulus(double a, double b) { if (b == 0) throw new IllegalArgumentException("modulus by zero"); return a % b; }
    public static long factorial(int n) { if (n < 0) throw new IllegalArgumentException("n must be >= 0"); long r=1; for(int i=2;i<=n;i++) r=Math.multiplyExact(r,i); return r; }
    public static double integrateX2(double a, double b, int steps) { if(steps<1) throw new IllegalArgumentException("steps >= 1"); double h=(b-a)/steps, sum=(a*a+b*b)/2; for(int i=1;i<steps;i++){double x=a+i*h;sum+=x*x;} return sum*h; }
    public static double[] vectorAdd(double[] a,double[] b){if(a.length!=b.length)throw new IllegalArgumentException("dimension mismatch");double[] r=new double[a.length];for(int i=0;i<a.length;i++)r[i]=a[i]+b[i];return r;}
    public static double vectorDot(double[] a,double[] b){if(a.length!=b.length)throw new IllegalArgumentException("dimension mismatch");double r=0;for(int i=0;i<a.length;i++)r+=a[i]*b[i];return r;}
    public static double linearRegressionPredict(double[] xs,double[] ys,double x){if(xs.length!=ys.length||xs.length<2)throw new IllegalArgumentException("need >=2 paired samples");double mx=mean(xs),my=mean(ys),num=0,den=0;for(int i=0;i<xs.length;i++){num+=(xs[i]-mx)*(ys[i]-my);den+=(xs[i]-mx)*(xs[i]-mx);}if(den==0)throw new IllegalArgumentException("x values must vary");return my+(num/den)*(x-mx);}
    public static double max(double[] values) {
        if (values.length == 0) throw new IllegalArgumentException("at least one number is required");
        double result = values[0]; for (double v : values) if (v > result) result = v; return result;
    }
}
