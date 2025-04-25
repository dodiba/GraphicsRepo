package Week02;

import java.util.ArrayList;

import Week01.Vector;
import Week01.VectorAbstract;

public class ScanConvertLine extends ScanConvertAbstract {

    
    public void digitaldifferentialanalyzer(int x0, int y0, int x1, int y1, int framebuffer[][][]) 
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }

        int dx = x1 - x0;
        int dy = y1 - y0;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x0;
        float y = y0;

        for (int i = 0; i <= steps; i++) {
            setPixel_(framebuffer, Math.round(x), Math.round(y));
            x += xIncrement;
            y += yIncrement;
        }
    }
    /**
    public void bresenham_(int x0, int y0, int x1, int y1, int framebuffer[][][]) 
            throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            setPixel_(framebuffer, x0, y0);

            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) { 
                err -= dy; 
                x0 += sx; 
            }
            if (e2 < dx) { 
                err += dx; 
                y0 += sy; 
            }
        }
    }
    **/
    
	@Override
    public void bresenham(int x0, int y0, int x1, int y1, ColorAbstract cor0, ColorAbstract cor1, int framebuffer[][][])
            throws NullPointerException, ArrayIndexOutOfBoundsException {

        if (framebuffer == null) {
            throw new NullPointerException("Frame buffer is null.");
        }

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        // Convert colors to their integer representations (assuming 255 scale)
        int[] startRGB = cor0.scale(255);
        int[] endRGB = cor1.scale(255);

        // Color interpolation values
        double r = startRGB[0];
        double g = startRGB[1];
        double b = startRGB[2];

        double dr = (endRGB[0] - startRGB[0]) / (double) Math.max(dx, dy);
        double dg = (endRGB[1] - startRGB[1]) / (double) Math.max(dx, dy);
        double db = (endRGB[2] - startRGB[2]) / (double) Math.max(dx, dy);

        while (true) {
            setPixel(framebuffer, x0, y0, (int) r, (int) g, (int) b);

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }

            // Update color values for interpolation
            r += dr;
            g += dg;
            b += db;
        }
    }

	@Override
	public void bresenham(int x0, int y0, int x1, int y1, Color c0, Color c1, ArrayList<VectorAbstract> points) {
	    int dx = Math.abs(x1 - x0);
	    int dy = Math.abs(y1 - y0);
	    int sx = x0 < x1 ? 1 : -1;
	    int sy = y0 < y1 ? 1 : -1;
	    int err = dx - dy;

	    int steps = Math.max(dx, dy);
	    double dr = ((Color) c1).r - ((Color) c0).r;
	    double dg = ((Color) c1).g - ((Color) c0).g;
	    double db = ((Color) c1).b - ((Color) c0).b;

	    for (int i = 0; i <= steps; i++) {
	        double t = steps == 0 ? 0 : (double) i / steps;
	        double r = ((Color) c0).r + t * dr;
	        double g = ((Color) c0).g + t * dg;
	        double b = ((Color) c0).b + t * db;

	        Color color = new Color(r, g, b);
	        points.add(new Vector(x0, y0, 0, color));

	        if (x0 == x1 && y0 == y1) break;
	        int e2 = 2 * err;
	        if (e2 > -dy) {
	            err -= dy;
	            x0 += sx;
	        }
	        if (e2 < dx) {
	            err += dx;
	            y0 += sy;
	        }
	    }
	}

    /**
     * Helper function to safely set a pixel in the framebuffer with color values.
     */
    private void setPixel(int framebuffer[][][], int x, int y, int r, int g, int b)
            throws ArrayIndexOutOfBoundsException {

        if (x < 0 || x >= framebuffer[0][0].length || y < 0 || y >= framebuffer[0].length) {
            throw new ArrayIndexOutOfBoundsException("Pixel coordinate out of framebuffer bounds: (" + x + ", " + y + ")");
        }

        framebuffer[0][y][x] = r;
        framebuffer[1][y][x] = g;
        framebuffer[2][y][x] = b;
    }


    /**
     * Helper function to safely set a pixel in the framebuffer
    */
    private void setPixel_(int framebuffer[][][], int x, int y) throws ArrayIndexOutOfBoundsException {
        if (x < 0 || x >= framebuffer[0][0].length || y < 0 || y >= framebuffer[0].length) {
            throw new ArrayIndexOutOfBoundsException("Pixel coordinate out of framebuffer bounds: (" + x + ", " + y + ")");
        }

        // Assuming a grayscale image, set the pixel in all three channels
        framebuffer[0][y][x] = 255;
        framebuffer[1][y][x] = 255;
        framebuffer[2][y][x] = 255;
    }

	

}

