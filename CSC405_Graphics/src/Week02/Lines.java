package Week02;

public class Lines extends LineAbstract {
    
    @Override
    public void twoPointForm(int x0, int y0, int x1, int y1, int framebuffer[][][]) throws NullPointerException {
        if (framebuffer == null) {
            throw new NullPointerException("Framebuffer cannot be null");
        }
        
        int dx = x1 - x0;
        int dy = y1 - y0;
        
        if (dx == 0) { // Vertical line case
            for (int y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
                framebuffer[0][y][x0] = 255;
            }
        } else {
            double m = (double) dy / dx;
            for (int x = Math.min(x0, x1); x <= Math.max(x0, x1); x++) {
                int y = (int) (y0 + m * (x - x0));
                framebuffer[0][y][x] = 255;
            }
        }
    }
    
    @Override
    public void parametricForm(int x0, int y0, int x1, int y1, int framebuffer[][][]) throws NullPointerException {
        if (framebuffer == null) {
            throw new NullPointerException("Framebuffer cannot be null");
        }
        
        int steps = Math.max(Math.abs(x1 - x0), Math.abs(y1 - y0));
        
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            int x = (int) (x0 + t * (x1 - x0));
            int y = (int) (y0 + t * (y1 - y0));
            
            framebuffer[0][y][x] = 255;
        }
    }
}
