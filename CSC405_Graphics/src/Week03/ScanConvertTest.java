package Week03;

import Common.ReadWriteImage;
import Week01.Triangle;
import Week01.TriangleAbstract;
import Week01.Vector;
import Week01.VectorAbstract;
import Week02.ScanConvertAbstract;
import Week02.ScanConvertLine;

@SuppressWarnings("unused")
public class ScanConvertTest {
	public static void main (String[] args) {
		ScanConvertAbstract sc = new ScanConvertLine();
		int anglesteps = 32;
		
		{
			int framebuffer[][][] = new int[3][256][256];
			try {
				for (int x = 0; x < framebuffer[0][0].length; x += anglesteps) {
					((ScanConvertLine) sc).digitaldifferentialanalyzer(x, 0, framebuffer[0][0].length - x - 1, framebuffer[0].length - 1, framebuffer);
				}
				for (int y = 0; y < framebuffer[0].length; y += anglesteps) {
					((ScanConvertLine) sc).digitaldifferentialanalyzer(0, y, framebuffer[0][0].length - 1, framebuffer[0].length - y - 1, framebuffer);
				}
				ReadWriteImage.writeImage(framebuffer, "differential.png");
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
		{
			int framebuffer[][][] = new int[3][256][256];
			try {
				for (int x = 0; x < framebuffer[0][0].length; x += anglesteps) {
					sc.bresenham(x, 0, framebuffer[0][0].length - x - 1, framebuffer[0].length - 1, null, null, framebuffer);
				}
				for (int y = 0; y < framebuffer[0].length; y += anglesteps) {
					sc.bresenham(0, y, framebuffer[0][0].length - 1, framebuffer[0].length - y - 1, null, null, framebuffer);
				}
				ReadWriteImage.writeImage(framebuffer, "bresenham.png");
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}	
	}

}
