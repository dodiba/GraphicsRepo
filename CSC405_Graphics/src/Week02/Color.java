package Week02;

public class Color extends ColorAbstract {

    /**
     * Constructor that initializes the color components.
     * Throws an exception if any value is out of range [0.0, 1.0].
     */
    public Color(double r, double g, double b) throws IllegalArgumentException {
        if (r < 0.0 || r > 1.0 || g < 0.0 || g > 1.0 || b < 0.0 || b > 1.0) {
            throw new IllegalArgumentException("Color values must be in the range [0.0, 1.0]");
        }
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Scale method that converts color values to an integer range based on the given scale.
     */
    @Override
    public int[] scale(int s) {
        int red = (int) Math.round(r * s);
        int green = (int) Math.round(g * s);
        int blue = (int) Math.round(b * s);
        return new int[]{red, green, blue};
    }
    
    
    

}

