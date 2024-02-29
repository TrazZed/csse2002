package srg.ports;

import srg.ship.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a ShipYard, which is a SpacePort where the ship can be upgraded
 */
public class ShipYard extends SpacePort {

    /**
     * A List of rooms that can be upgraded at the ShipYard
     */
    private List<String> canUpgrade;

    /**
     * Initialises the ShipYard with a list of potential rooms that can be upgraded
     *
     * @require name must be unique
     * @param name the name of the ShipYard
     * @param position the position of the ShipYard
     * @param canUpgrade the list of rooms that can be upgraded at the ShipYard
     */
    public ShipYard(String name, Position position, List<String> canUpgrade) {
        super(name, position);
        this.canUpgrade = canUpgrade;
    }

    /**
     * Upgrades the room specified, if the room is available on the list of rooms the
     * ShipYard can upgrade
     *
     * @param room the room to be upgraded
     */
    public void upgrade(Room room) {
        //If the room is not in the upgrade list, throw an exception
        if (!(this.canUpgrade.contains(room.getClass().getSimpleName()))) {
            throw new IllegalArgumentException();
        }
        //Upgrade the room
        room.upgrade();
    }

    /**
     * Get a list of potential rooms that the ShipYard can upgrade. Returns the form
     *
     * upgrade ROOM
     *
     * @return the list of rooms that can be upgraded
     */
    @Override
    public List<String> getActions() {
        //Generate a new empty list
        List<String> yardActions = new ArrayList<>();
        Iterator<String> it = this.canUpgrade.iterator();
        //Add all rooms that can be upgraded to the list
        while (it.hasNext()) {
            yardActions.add("upgrade " + it.next());
        }
        return yardActions;
    }
}
