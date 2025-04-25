package Week02;

import Week01.TriangleAbstract;
import Week01.VectorAbstract;

public class Shader extends ShaderAbstract {

    @Override
    public void solidFill(TriangleAbstract tri, int[][][] framebuffer) {
        VectorAbstract[] v = tri.getVertices();
        Color color = (Color) v[0].getColor();  // Flat fill using first vertex color

        int[] rgb = color.scale(255);

        int minX = (int) Math.min(v[0].getX(), Math.min(v[1].getX(), v[2].getX()));
        int maxX = (int) Math.max(v[0].getX(), Math.max(v[1].getX(), v[2].getX()));
        int minY = (int) Math.min(v[0].getY(), Math.min(v[1].getY(), v[2].getY()));
        int maxY = (int) Math.max(v[0].getY(), Math.max(v[1].getY(), v[2].getY()));

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (insideTriangle(x, y, v[0], v[1], v[2])) {
                    framebuffer[0][y][x] = rgb[0];
                    framebuffer[1][y][x] = rgb[1];
                    framebuffer[2][y][x] = rgb[2];
                }
            }
        }
    }

    @Override
    public void shadeFill(TriangleAbstract tri, int[][][] framebuffer) {
        VectorAbstract[] v = tri.getVertices();
        Color c0 = (Color) v[0].getColor();
        Color c1 = (Color) v[1].getColor();
        Color c2 = (Color) v[2].getColor();

        int minX = (int) Math.min(v[0].getX(), Math.min(v[1].getX(), v[2].getX()));
        int maxX = (int) Math.max(v[0].getX(), Math.max(v[1].getX(), v[2].getX()));
        int minY = (int) Math.min(v[0].getY(), Math.min(v[1].getY(), v[2].getY()));
        int maxY = (int) Math.max(v[0].getY(), Math.max(v[1].getY(), v[2].getY()));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                double[] bary = barycentricCoords(x, y, v[0], v[1], v[2]);
                if (bary != null && bary[0] >= 0 && bary[1] >= 0 && bary[2] >= 0) {
                    // Interpolate color components manually
                    double r = bary[0] * c0.r + bary[1] * c1.r + bary[2] * c2.r;
                    double g = bary[0] * c0.g + bary[1] * c1.g + bary[2] * c2.g;
                    double b = bary[0] * c0.b + bary[1] * c1.b + bary[2] * c2.b;

                    // Clamp color values between 0.0 and 1.0
                    r = Math.max(0, Math.min(1, r));
                    g = Math.max(0, Math.min(1, g));
                    b = Math.max(0, Math.min(1, b));

                    Color shaded = new Color(r, g, b);
                    int[] rgb = shaded.scale(255);

                    framebuffer[0][y][x] = rgb[0];
                    framebuffer[1][y][x] = rgb[1];
                    framebuffer[2][y][x] = rgb[2];
                }
            }
        }
    }

    private boolean insideTriangle(int x, int y, VectorAbstract v0, VectorAbstract v1, VectorAbstract v2) {
        double[] bc = barycentricCoords(x, y, v0, v1, v2);
        return bc != null && bc[0] >= 0 && bc[1] >= 0 && bc[2] >= 0;
    }

    private double[] barycentricCoords(double x, double y, VectorAbstract a, VectorAbstract b, VectorAbstract c) {
        double denom = (b.getY() - c.getY()) * (a.getX() - c.getX()) +
                       (c.getX() - b.getX()) * (a.getY() - c.getY());
        if (Math.abs(denom) < 1e-5) return null;

        double alpha = ((b.getY() - c.getY()) * (x - c.getX()) +
                        (c.getX() - b.getX()) * (y - c.getY())) / denom;
        double beta = ((c.getY() - a.getY()) * (x - c.getX()) +
                       (a.getX() - c.getX()) * (y - c.getY())) / denom;
        double gamma = 1.0 - alpha - beta;

        return new double[]{alpha, beta, gamma};
    }
}
