package Week03;

import java.util.ArrayList;

import Week02.Color;
import Week02.ColorAbstract;
import Week02.Curves;
import Common.ReadWriteImage;
import Week02.ScanConvertLine;
import Week01.Vector;
import Week01.VectorAbstract;

public class CurvesTest {
    public static void main(String[] args) {
        ScanConvertLine scan = new ScanConvertLine();
        Curves curves = new Curves();

        ColorAbstract red = new Color(1.0, 0.0, 0.0);
        ColorAbstract green = new Color(0.0, 1.0, 0.0);

        int[][][] framebuffer = new int[3][600][600];

        try {
            // Bézier curve
            VectorAbstract p0 = new Vector(50, 500, 0, (Color) red);
            VectorAbstract p1 = new Vector(150, 100, 0, (Color) red);
            VectorAbstract p2 = new Vector(300, 600, 0, (Color) red);
            VectorAbstract p3 = new Vector(500, 100, 0, (Color) red);

            ArrayList<VectorAbstract> bezierPoints = curves.bezier(p0, p1, p2, p3);
            for (int i = 0; i < bezierPoints.size() - 1; i++) {
                VectorAbstract a = bezierPoints.get(i);
                VectorAbstract b = bezierPoints.get(i + 1);
                scan.bresenham(
                    (int) a.getX(), (int) a.getY(),
                    (int) b.getX(), (int) b.getY(),
                    a.getColor(), b.getColor(),
                    framebuffer
                );
            }

            // Hermite curve
            VectorAbstract h0 = new Vector(50, 100, 0, (Color) green);
            VectorAbstract h1 = new Vector(550, 500, 0, (Color) green);
            VectorAbstract t0 = new Vector(300, 0, 0, (Color) green);
            VectorAbstract t1 = new Vector(0, -300, 0, (Color) green);

            ArrayList<VectorAbstract> hermitePoints = curves.hermite(h0, h1, t0, t1);
            for (int i = 0; i < hermitePoints.size() - 1; i++) {
                VectorAbstract a = hermitePoints.get(i);
                VectorAbstract b = hermitePoints.get(i + 1);
                scan.bresenham(
                    (int) a.getX(), (int) a.getY(),
                    (int) b.getX(), (int) b.getY(),
                    a.getColor(), b.getColor(),
                    framebuffer
                );
            }

            ReadWriteImage.writeImage(framebuffer, "curves.png");
            System.out.println(" curves.png generated successfully!");
        } catch (Exception e) {
            System.out.println("Error generating curve: " + e.getMessage());
        }
    }
}
