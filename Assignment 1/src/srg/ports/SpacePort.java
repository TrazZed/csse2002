package srg.ports;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing an arbitrary SpacePort with a name and position in space
 */
public class SpacePort {

    /**
     * The Name of the SpacePort
     */
    private String name;
    /**
     * The Position of the SpacePort
     */
    private Position position;

    /**
     * Initialises the SpacePort with a unique name and a set of coordinates for its location
     *
     * @require name must be unique
     * @param name the name of the SpacePort
     * @param position the position of the SpacePort
     */
    public SpacePort(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Returns the name of the SpacePort as a String
     *
     * @return name of SpacePort
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the position of the SpacePort as a Position
     *
     * @return position of SpacePort
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns a String detailing the SpacePort
     *
     * @return String detailing the SpacePort name, type and position
     */
    @Override
    public String toString() {
        String nameDetails = this.getName();
        String portType = this.getClass().getSimpleName();
        String positionDetails = this.getPosition().toString();
        return "PORT: \"" + nameDetails + "\" " + portType + " at " + positionDetails;
    }

    /**
     * Return a list of Strings detailing possible actions
     *
     * @return List of Strings detailing possible actions
     */
    public List<String> getActions() {
        //Return an empty list since generic SpacePort has no actions
        return new ArrayList<>();
    }
}
