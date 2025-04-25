package Week02;

import java.util.ArrayList;

import Week01.Vector;
import Week01.VectorAbstract;

public class Curves {

    /**
     * Generate a cubic Bézier curve.
     */
    public ArrayList<VectorAbstract> bezier(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3) {
        ArrayList<VectorAbstract> curvePoints = new ArrayList<>();
        ScanConvertLine scan = new ScanConvertLine();

        VectorAbstract last = p0;
        for (double u = 0.01; u <= 1.0; u += 0.01) {
            VectorAbstract curr = evaluateBezier(p0, p1, p2, p3, u);
            scan.bresenham(
                (int) last.getX(), (int) last.getY(),
                (int) curr.getX(), (int) curr.getY(),
                last.getColor(), curr.getColor(),
                curvePoints
            );
            last = curr;
        }
        return curvePoints;
    }

    /**
     * Evaluate a cubic Bézier curve at parameter u ∈ [0, 1]
     */
    private VectorAbstract evaluateBezier(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3, double u) {
        double oneMinusU = 1 - u;
        double b0 = oneMinusU * oneMinusU * oneMinusU;
        double b1 = 3 * oneMinusU * oneMinusU * u;
        double b2 = 3 * oneMinusU * u * u;
        double b3 = u * u * u;

        double x = b0 * p0.getX() + b1 * p1.getX() + b2 * p2.getX() + b3 * p3.getX();
        double y = b0 * p0.getY() + b1 * p1.getY() + b2 * p2.getY() + b3 * p3.getY();

        double r = b0 * ((Color) p0.getColor()).r + b1 * ((Color) p1.getColor()).r +
                   b2 * ((Color) p2.getColor()).r + b3 * ((Color) p3.getColor()).r;
        double g = b0 * ((Color) p0.getColor()).g + b1 * ((Color) p1.getColor()).g +
                   b2 * ((Color) p2.getColor()).g + b3 * ((Color) p3.getColor()).g;
        double b = b0 * ((Color) p0.getColor()).b + b1 * ((Color) p1.getColor()).b +
                   b2 * ((Color) p2.getColor()).b + b3 * ((Color) p3.getColor()).b;

        return new Vector(x, y, 0, safeColor(r, g, b));

    }

    /**
     * Generate a cubic Hermite curve.
     */
    public ArrayList<VectorAbstract> hermite(VectorAbstract p0, VectorAbstract p1, VectorAbstract t0, VectorAbstract t1) {
        ArrayList<VectorAbstract> curvePoints = new ArrayList<>();
        ScanConvertLine scan = new ScanConvertLine();

        VectorAbstract last = p0;
        for (double u = 0.01; u <= 1.0; u += 0.01) {
            VectorAbstract curr = evaluateHermite(p0, p1, t0, t1, u);
            scan.bresenham(
                (int) last.getX(), (int) last.getY(),
                (int) curr.getX(), (int) curr.getY(),
                last.getColor(), curr.getColor(),
                curvePoints
            );
            last = curr;
        }
        return curvePoints;
    }

    /**
     * Evaluate a cubic Hermite curve at parameter u ∈ [0, 1]
     */
    private VectorAbstract evaluateHermite(VectorAbstract p0, VectorAbstract p1, VectorAbstract t0, VectorAbstract t1, double u) {
        double h00 = 2 * u * u * u - 3 * u * u + 1;
        double h10 = u * u * u - 2 * u * u + u;
        double h01 = -2 * u * u * u + 3 * u * u;
        double h11 = u * u * u - u * u;

        double x = h00 * p0.getX() + h10 * t0.getX() + h01 * p1.getX() + h11 * t1.getX();
        double y = h00 * p0.getY() + h10 * t0.getY() + h01 * p1.getY() + h11 * t1.getY();

        double r = h00 * ((Color) p0.getColor()).r + h10 * ((Color) t0.getColor()).r +
                   h01 * ((Color) p1.getColor()).r + h11 * ((Color) t1.getColor()).r;
        double g = h00 * ((Color) p0.getColor()).g + h10 * ((Color) t0.getColor()).g +
                   h01 * ((Color) p1.getColor()).g + h11 * ((Color) t1.getColor()).g;
        double b = h00 * ((Color) p0.getColor()).b + h10 * ((Color) t0.getColor()).b +
                   h01 * ((Color) p1.getColor()).b + h11 * ((Color) t1.getColor()).b;

        return new Vector(x, y, 0, safeColor(r, g, b));

    }
    
    private Color safeColor(double r, double g, double b) {
        r = Math.max(0.0, Math.min(1.0, r));
        g = Math.max(0.0, Math.min(1.0, g));
        b = Math.max(0.0, Math.min(1.0, b));
        return new Color(r, g, b);
    }
}

