package Week01;
import Week02.Shader;
import Week02.ShaderAbstract;
import Week02.AffineTransformation;
import Week02.Color;
import Week02.ColorAbstract;
import Week02.ScanConvertLine;
import Week02.Shader;
import Week02.Matrix;
import Week02.MatrixAbstract;

/**
 * Implementation of the Triangle class extending TriangleAbstract
 */
@SuppressWarnings("unused")
public class Triangle extends TriangleAbstract {

    /**
     * Constructor to initialize the triangle with three vertices.
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param v3 the third vertex
     */
    public Triangle(VectorAbstract v1, VectorAbstract v2, VectorAbstract v3) {
        this.vertices = new VectorAbstract[]{v1, v2, v3};
    }

	@Override
    public Vector getCenter() {
        // The center is the average of the three vertices
        double centerX = (vertices[0].getX() + vertices[1].getX() + vertices[2].getX()) / 3;
        double centerY = (vertices[0].getY() + vertices[1].getY() + vertices[2].getY()) / 3;
        double centerZ = (vertices[0].getZ() + vertices[1].getZ() + vertices[2].getZ()) / 3;
        return new Vector(centerX, centerY, centerZ, null);
    }

    @Override
    public double getPerimeter() {
        // Compute the perimeter as the sum of distances between vertices
        double side1 = ((Vector) vertices[0]).sub((Vector) vertices[1]).length();
        double side2 = ((Vector) vertices[1]).sub((Vector) vertices[2]).length();
        double side3 = ((Vector) vertices[2]).sub((Vector) vertices[0]).length();
        return side1 + side2 + side3;
    }

    @Override
    public double getArea() {
        // Compute the area using the cross product of two sides
        Vector side1 = (Vector) ((Vector) vertices[1]).sub((Vector) vertices[0]);
        Vector side2 = (Vector) ((Vector) vertices[2]).sub((Vector) vertices[0]);
        Vector crossProduct = (Vector) side1.cross(side2);
        return 0.5 * crossProduct.length();
    }

    @Override
    public Vector getNormal() {
        // Compute the normal using the cross product of two sides
        Vector side1 = (Vector) ((Vector) vertices[1]).sub((Vector) vertices[0]);
        Vector side2 = (Vector) ((Vector) vertices[2]).sub((Vector) vertices[0]);
        Vector crossProduct = (Vector) side1.cross(side2);
        return (Vector) crossProduct.unit();
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
    

    
    public void render(int[][][] framebuffer, boolean shownormal) {
        ScanConvertLine scanConverter = new ScanConvertLine();

        // Cast vertices to int for rendering
        int x0 = (int) vertices[0].getX();
        int y0 = (int) vertices[0].getY();
        int x1 = (int) vertices[1].getX();
        int y1 = (int) vertices[1].getY();
        int x2 = (int) vertices[2].getX();
        int y2 = (int) vertices[2].getY();
        Color c0 = (Color) vertices[0].getColor();
        Color c1 = (Color) vertices[1].getColor();
        Color c2 = (Color) vertices[2].getColor();
        
        // Draw triangle edges using Bresenham's algorithm
        scanConverter.bresenham(x0, y0, x1, y1, c0, c1, framebuffer);
        scanConverter.bresenham(x1, y1, x2, y2, c1, c2, framebuffer);
        scanConverter.bresenham(x2, y2, x0, y0, c2, c0, framebuffer);

        // Draw the normal if required
        if (shownormal) {
            VectorAbstract center = getCenter();
            VectorAbstract normalEnd = center.add(getNormal().mult(20)); // Scale normal by 20 pixels

            int nx = (int) normalEnd.getX();
            int ny = (int) normalEnd.getY();
            int cx = (int) center.getX();
            int cy = (int) center.getY();
            Color white = new Color(1.0, 1.0, 1.0);

            scanConverter.bresenham(cx, cy, nx, ny, white, white, framebuffer);
        }
    }
    /**
    @Override
    public void render(int[][][] framebuffer, boolean shownormal, ShaderAbstract.FILLSTYLE fill, VectorAbstract viewpoint) {
        
    	Color c0 = (Color) vertices[0].getColor();
        Color c1 = (Color) vertices[1].getColor();
        Color c2 = (Color) vertices[2].getColor();
    	
    	if (!isVisible(viewpoint)) return;

        Shader shader = new Shader();
        switch (fill) {
            case FILL:
                shader.solidFill(this, framebuffer);
                break;
            case SHADE:
                shader.shadeFill(this, framebuffer);
                break;
            case NONE:
                ScanConvertLine line = new ScanConvertLine();
                line.bresenham((int) vertices[0].getX(), (int) vertices[0].getY(),
                               (int) vertices[1].getX(), (int) vertices[1].getY(), c0, c1, framebuffer);
                line.bresenham((int) vertices[1].getX(), (int) vertices[1].getY(),
                               (int) vertices[2].getX(), (int) vertices[2].getY(), c1, c2, framebuffer);
                line.bresenham((int) vertices[2].getX(), (int) vertices[2].getY(),
                               (int) vertices[0].getX(), (int) vertices[0].getY(), c2, c0, framebuffer);
                
                if (shownormal) {
                    VectorAbstract center = getCenter();
                    VectorAbstract normalEnd = center.add(getNormal().mult(20)); // Scale normal by 20 pixels

                    int nx = (int) normalEnd.getX();
                    int ny = (int) normalEnd.getY();
                    int cx = (int) center.getX();
                    int cy = (int) center.getY();
                    
                    Color white = new Color(1.0, 1.0, 1.0);

                    line.bresenham(cx, cy, nx, ny, white, white, framebuffer);
                }
                break;
        }
        
    }
    **/
    
    @Override
    public void render(int[][][] framebuffer, boolean shownormal, ShaderAbstract.FILLSTYLE fill,VectorAbstract viewpoint)
    {
        if (!isVisible(viewpoint)) {
            return; // Skip rendering if it's not visible
        }

        // Create an instance of Shader
        Shader shader = new Shader();

        // Choose the fill method based on the specified fill option
        if (fill == ShaderAbstract.FILLSTYLE.FILL) {
            shader.solidFill(this, framebuffer);
        } else if (fill == ShaderAbstract.FILLSTYLE.SHADE) {
            shader.shadeFill(this, framebuffer);
        }

        //I had to create an instance of ScanConvertLine because bresenham is not static
        ScanConvertLine line = new ScanConvertLine();
        //line 1
        int x0 = (int) vertices[0].getX();
        int y0 = (int) vertices[0].getY();
        //line 2
        int x1 = (int) vertices[1].getX();
        int y1 = (int) vertices[1].getY();
        //line3
        int x2 = (int) vertices[2].getX();
        int y2 = (int) vertices[2].getY();
/// new
        // Check if all points are in bounds
        boolean allPointsInBounds =
                isPointInBounds(x0, y0, framebuffer) &&
                        isPointInBounds(x1, y1, framebuffer) &&
                        isPointInBounds(x2, y2, framebuffer);

        ColorAbstract[] colors = { vertices[0].getColor(), vertices[1].getColor(), vertices[2].getColor() };

        //calling method through instnace
        if(fill != ShaderAbstract.FILLSTYLE.FILL && allPointsInBounds) {
            line.bresenham(x0, y0, x1, y1, colors[0], colors[0], framebuffer);
            line.bresenham(x1, y1, x2, y2, colors[1], colors[1], framebuffer);
            line.bresenham(x2, y2, x0, y0, colors[2], colors[2], framebuffer);
        }
        //From the assignment description:
        if (shownormal) {
            VectorAbstract triangleCenter = getCenter();
            VectorAbstract surfaceNormal = getNormal();

            int scale = 20;

            double normalX = surfaceNormal.getX() * scale;
            double normalY = surfaceNormal.getY() * scale;

            int centerX = (int) triangleCenter.getX();
            int centerY = (int) triangleCenter.getY();

            int normalEndX = centerX + (int) normalX;
            int normalEndY = centerY + (int) normalY;

            // Clamp values to prevent out-of-bounds errors
            normalEndX = Math.max(0, Math.min(255, normalEndX));
            normalEndY = Math.max(0, Math.min(255, normalEndY));
            centerX = Math.max(0, Math.min(255, centerX));
            centerY = Math.max(0, Math.min(255, centerY));

            ColorAbstract normalColor = new Color(0, 1, 0); // Green for normal because it looks cool
            line.bresenham(centerX, centerY, normalEndX, normalEndY, normalColor, normalColor, framebuffer);
        }


    }
    
    @Override
    public TriangleAbstract translate(VectorAbstract transvec, TriangleAbstract data) {
        AffineTransformation transform = new AffineTransformation();
        MatrixAbstract transformedMatrix = transform.translate(transvec, toMatrix(data));
        return fromMatrix(transformedMatrix);
    }

    @Override
    public TriangleAbstract rotateX(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation transform = new AffineTransformation();
        MatrixAbstract transformedMatrix = transform.rotateX(theta, fixedpoint, toMatrix(data));
        return fromMatrix(transformedMatrix);
    }

    @Override
    public TriangleAbstract rotateY(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation transform = new AffineTransformation();
        MatrixAbstract transformedMatrix = transform.rotateY(theta, fixedpoint, toMatrix(data));
        return fromMatrix(transformedMatrix);
    }

    @Override
    public TriangleAbstract rotateZ(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation transform = new AffineTransformation();
        MatrixAbstract transformedMatrix = transform.rotateZ(theta, fixedpoint, toMatrix(data));
        return fromMatrix(transformedMatrix);
    }

    @Override
    public TriangleAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation transform = new AffineTransformation();
        MatrixAbstract transformedMatrix = transform.scale(factor, fixedpoint, toMatrix(data));
        return fromMatrix(transformedMatrix);
    }

    /**
     * Converts a TriangleAbstract into a MatrixAbstract using homogeneous coordinates.
     */
    private MatrixAbstract toMatrix(TriangleAbstract triangle) {
        double[][] matrixData = {
            {triangle.getVertices()[0].getX(), triangle.getVertices()[1].getX(), triangle.getVertices()[2].getX(), 1},
            {triangle.getVertices()[0].getY(), triangle.getVertices()[1].getY(), triangle.getVertices()[2].getY(), 1},
            {triangle.getVertices()[0].getZ(), triangle.getVertices()[1].getZ(), triangle.getVertices()[2].getZ(), 1},
            {1, 1, 1, 1} // Homogeneous coordinate row
        };
        return new Matrix(matrixData);
    }

    /**
     * Converts a MatrixAbstract back into a TriangleAbstract after transformation.
     */
    private TriangleAbstract fromMatrix(MatrixAbstract matrix) {
        double[][] m = matrix.getMatrix();
        VectorAbstract v0 = new Vector(m[0][0], m[1][0], m[2][0], vertices[0].getColor());
        VectorAbstract v1 = new Vector(m[0][1], m[1][1], m[2][1], vertices[1].getColor());
        VectorAbstract v2 = new Vector(m[0][2], m[1][2], m[2][2], vertices[2].getColor());
        return new Triangle(v0, v1, v2);
    }

	@Override
	public TriangleAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads,
			TriangleAbstract data) {
		AffineTransformation transform = new AffineTransformation();
        MatrixAbstract transformedMatrix = transform.rotateAxis(axis, fixedpoint, arads, toMatrix(data));
        return fromMatrix(transformedMatrix);
	}

	@Override
    public boolean isVisible(VectorAbstract viewpoint)
    {
        VectorAbstract normalizedVP = viewpoint.unit();
        VectorAbstract normalizedNM = getNormal().unit();
        double angleinR = normalizedNM.angleBetween(normalizedVP);
        double angle = Math.toDegrees(angleinR);//Edit, I think i was measuring radians to degrees at some point
        //System.out.println("Angle between normal and viewpoint: " + angle); gave me pi as output at some point
        // Check if the angle is within the visible range (typically between -90 and 90 degrees)
        if (angle >= -90 && angle <= 90)
        {
            return false; // Visible if the angle is within the range
        }
        else
        {
            return true; // Not visible if the angle is outside the range
        }//swapped my false and true for mor accurate results
    }
	
	private boolean isPointInBounds(int x, int y, int[][][] framebuffer) {
        int width = framebuffer[0][0].length;
        int height = framebuffer[0].length;
        return x >= 0 && x < width && y >= 0 && y < height;
    }


}
