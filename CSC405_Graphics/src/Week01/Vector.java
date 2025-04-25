package Week01;

import Week02.Color;

/**
 * Implementation of the Vector class extending VectorAbstract
 */
public class Vector extends VectorAbstract {

    /**
     * Constructor to initialize the vector with x, y, z components.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     */
    
    
    public Vector(double x, double y, double z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public Vector(double x, double y, double z) {
    	this.x = x;
        this.y = y;
        this.z = z;
	}

	@Override
    public double angleBetween(VectorAbstract v2) {
        double dotProduct = this.dot(v2);
        double lengthsProduct = this.length() * v2.length();
        return Math.acos(dotProduct / lengthsProduct);
    }

    @Override
    public double dot(VectorAbstract v2) {
        return this.x * v2.getX() + this.y * v2.getY() + this.z * v2.getZ();
    }

    @Override
    public VectorAbstract cross(VectorAbstract v2) {
        double crossX = this.y * v2.getZ() - this.z * v2.getY();
        double crossY = this.z * v2.getX() - this.x * v2.getZ();
        double crossZ = this.x * v2.getY() - this.y * v2.getX();
        return new Vector(crossX, crossY, crossZ, this.color);
    }

    @Override
    public VectorAbstract unit() {
        double length = this.length();
        if (length == 0) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        return new Vector(this.x / length, this.y / length, this.z / length, this.color);
    }

    @Override
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    @Override
    public VectorAbstract add(VectorAbstract v2) {
        double newX = this.x + v2.getX();
        double newY = this.y + v2.getY();
        double newZ = this.z + v2.getZ();
        return new Vector(newX, newY, newZ, this.color);
    }

    @Override
    public VectorAbstract sub(VectorAbstract v2) {
        double newX = this.x - v2.getX();
        double newY = this.y - v2.getY();
        double newZ = this.z - v2.getZ();
        return new Vector(newX, newY, newZ, this.color);
    }

    @Override
    public VectorAbstract mult(double scale) {
        double newX = this.x * scale;
        double newY = this.y * scale;
        double newZ = this.z * scale;
        return new Vector(newX, newY, newZ, this.color);
    }
}

