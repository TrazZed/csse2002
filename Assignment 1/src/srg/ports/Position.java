package srg.ports;

/**
 * A class representing an arbitrary position on the 3D plane (x,y,z)
 */
public class Position {
    /**
     * Coordinate of the x-axis
     */
    public final int x;
    /**
     * Coordinate of the y-axis
     */
    public final int y;
    /**
     * Coordinate of the z-axis
     */
    public final int z;

    /**
     * Initialises the Position as a 3D coordinate with coordinates specified
     *
     * @param x the coordinate of the Position along the x-axis
     * @param y the coordinate of the Position along the y-axis
     * @param z the coordinate of the Position along the z-axis
     */
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculate the (Euclidean) distance between two positions (Position Objects)
     *
     * @param other the other Position to find the distance from
     * @return the distance to the other point
     */
    public int distanceTo(Position other) {
        //Calculates terms to be summed for the sqrt term
        double squaredX = Math.pow(this.x - other.x, 2);
        double squaredY = Math.pow(this.y - other.y, 2);
        double squaredZ = Math.pow(this.z - other.z, 2);
        //Calculates the distance
        return (int) Math.sqrt(squaredX + squaredY + squaredZ);
    }

    /**
     * Express the Position Coordinates as a string in the form (x,y,z)
     *
     * @return A string displaying the Position coordinates in the form (x,y,z)
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
