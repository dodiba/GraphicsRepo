package Week02;

import Week01.Vector;
import Week01.VectorAbstract;

public class AffineTransformation extends AffineTransformationAbstract {

    public AffineTransformation() {} // Prevent instantiation

    @Override
    public MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data) {
        double[][] translationMatrix = {
            {1, 0, 0, transvec.getX()},
            {0, 1, 0, transvec.getY()},
            {0, 0, 1, transvec.getZ()},
            {0, 0, 0, 1}
        };
        return new Matrix(translationMatrix).mult(data);
    }

    @Override
    public MatrixAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] scaleMatrix = {
            {factor.getX(), 0, 0, (1 - factor.getX()) * fixedpoint.getX()},
            {0, factor.getY(), 0, (1 - factor.getY()) * fixedpoint.getY()},
            {0, 0, factor.getZ(), (1 - factor.getZ()) * fixedpoint.getZ()},
            {0, 0, 0, 1}
        };
        return new Matrix(scaleMatrix).mult(data);
    }

    @Override
    public MatrixAbstract rotateX(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] rotationMatrix = {
            {1, 0, 0, 0},
            {0, Math.cos(theta), -Math.sin(theta), fixedpoint.getY() * (1 - Math.cos(theta)) + fixedpoint.getZ() * Math.sin(theta)},
            {0, Math.sin(theta), Math.cos(theta), fixedpoint.getZ() * (1 - Math.cos(theta)) - fixedpoint.getY() * Math.sin(theta)},
            {0, 0, 0, 1}
        };
        return new Matrix(rotationMatrix).mult(data);
    }

    @Override
    public MatrixAbstract rotateY(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] rotationMatrix = {
            {Math.cos(theta), 0, Math.sin(theta), fixedpoint.getX() * (1 - Math.cos(theta)) - fixedpoint.getZ() * Math.sin(theta)},
            {0, 1, 0, 0},
            {-Math.sin(theta), 0, Math.cos(theta), fixedpoint.getZ() * (1 - Math.cos(theta)) + fixedpoint.getX() * Math.sin(theta)},
            {0, 0, 0, 1}
        };
        return new Matrix(rotationMatrix).mult(data);
    }

    @Override
    public MatrixAbstract rotateZ(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        double[][] rotationMatrix = {
            {Math.cos(theta), -Math.sin(theta), 0, fixedpoint.getX() * (1 - Math.cos(theta)) + fixedpoint.getY() * Math.sin(theta)},
            {Math.sin(theta), Math.cos(theta), 0, fixedpoint.getY() * (1 - Math.cos(theta)) - fixedpoint.getX() * Math.sin(theta)},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
        return new Matrix(rotationMatrix).mult(data);
    }

	@Override
	public MatrixAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads, MatrixAbstract data) {
		
		// Step 0: Normalize the axis
	    VectorAbstract u = axis.unit();
	    double ux = u.getX(), uy = u.getY(), uz = u.getZ();

	    // Step 1: Translate the fixed point to the origin (T(-p0))
	    MatrixAbstract T1 = translate(new Vector(-fixedpoint.getX(), -fixedpoint.getY(), -fixedpoint.getZ(), null), identity(4));

	    // Step 2: Align the arbitrary axis with the z-axis
	    // Compute angles to rotate the axis to align with z
	    double d = Math.sqrt(uy * uy + uz * uz);
	    double thetaX = (d == 0) ? 0 : Math.atan2(uy, uz);  // rotation about X to bring into xz-plane
	    double thetaY = Math.atan2(ux, d);                 // rotation about Y to align with z

	    MatrixAbstract Rx1 = rotateX(-thetaX, origin(), identity(4)); // rotate around X by -thetaX
	    MatrixAbstract Ry1 = rotateY(-thetaY, origin(), identity(4)); // rotate around Y by -thetaY

	    // Step 3: Rotate around Z axis by desired angle
	    MatrixAbstract Rz = rotateZ(arads, origin(), identity(4)); // rotation in z

	    // Step 4: Undo alignment
	    MatrixAbstract Ry2 = rotateY(thetaY, origin(), identity(4));  // rotate back around Y
	    MatrixAbstract Rx2 = rotateX(thetaX, origin(), identity(4));  // rotate back around X

	    // Step 5: Translate back to original fixed point (T(p0))
	    MatrixAbstract T2 = translate(fixedpoint, identity(4));

	    // Combine all matrices: M = T2 * Rx2 * Ry2 * Rz * Ry1 * Rx1 * T1
	    MatrixAbstract M = T2.mult(Rx2).mult(Ry2).mult(Rz).mult(Ry1).mult(Rx1).mult(T1);

	    return M.mult(data);
	}
	
	private VectorAbstract origin() {
	    return new Vector(0, 0, 0, null);
	}

	private MatrixAbstract identity(int size) {
	    double[][] id = new double[size][size];
	    for (int i = 0; i < size; i++) {
	        id[i][i] = 1;
	    }
	    return new Matrix(id);
	}

}
