package srg.ship;

import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.ports.ShipYard;
import srg.ports.SpacePort;
import srg.ports.Store;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A subclass of Room that represents the NavigationRoom of a ship, responsible for
 * tracking the ship and tracking SpacePorts which the ship can travel to
 */
public class NavigationRoom extends Room {
    /**
     * A list of all SpacePorts in the galaxy
     */
    public List<SpacePort> galaxyMap;
    /**
     * The current port that the NavigationRoom is at
     */
    private SpacePort currentPort;

    /**
     * Constructs a NavigationRoom with the specified Room Tier and starts at the
     * first SpacePort on the galaxy map
     *
     * @param roomTier the quality of the NavigationRoom's tier
     * @param galaxyMap a list of all SpacePorts in the galaxy
     */
    public NavigationRoom(RoomTier roomTier, List<SpacePort> galaxyMap) {
        super(roomTier);
        this.galaxyMap = galaxyMap;
        this.currentPort = galaxyMap.get(0);
    }

    /**
     * Get and return the current SpacePort the NavigationRoom is at
     *
     * @return the current SpacePort
     */
    public SpacePort getCurrentPort() {
        return this.currentPort;
    }

    /**
     * Determine which SpacePorts the NavigationRoom can fly to, i.e. the distance is
     * less than or equal to the maximum flying distance
     *
     * @return a list of SpacePorts that can be flown to
     */
    public List<SpacePort> getPortsInFlyRange() {
        //Initialise all the required variables
        List<SpacePort> portsInRange = new ArrayList<SpacePort>();
        SpacePort port;
        //Calculate maximum distance away
        int maximumDistance = this.getMaximumFlyDistance();
        Iterator<SpacePort> it = this.galaxyMap.iterator();
        //For each SpacePort on the galaxy map
        while (it.hasNext()) {
            port = it.next();
            if (port != this.getCurrentPort()) {
                //Check if the distance between the position of this SpacePort and
                //the other SpacePort is less than the maximum distance
                if (this.getFuelNeeded(port) <= maximumDistance) {
                    portsInRange.add(port);
                }
            }
        }
        return portsInRange;
    }

    /**
     * Determine the maximum flying distance of the NavigationRoom based on the tier.
     * A BASIC NavigationRoom can fly 200 units
     * An AVERAGE NavigationRoom can fly 400 units
     * A PRIME NavigationRoom can fly 600 units
     *
     * @return the maximum flying distance of the NavigationRoom
     */
    public int getMaximumFlyDistance() {
        //Determine maximum flying distance based on tier of NavigationRoom
        switch (this.getTier()) {
            case BASIC:
                return 200;
            case AVERAGE:
                return 400;
            case PRIME:
                return 600;
            default:
                return 0;
        }
    }

    /**
     * Determine which SpacePorts the NavigationRoom can jump to, i.e. the distance is greater
     * than or equal to the maximum flying distance but less than or equal to the maximum jump
     * distance
     *
     * @return a list of SpacePorts that can be jumped to
     */
    public List<SpacePort> getPortsInJumpRange() {
        //Initialise all the required variables
        List<SpacePort> portsInRange = new ArrayList<SpacePort>();
        SpacePort port;
        int distance;
        //Calculate maximum distance away
        int maximumJumpDistance = this.getMaximumJumpDistance();
        int maximumFlyDistance = this.getMaximumFlyDistance();
        Iterator<SpacePort> it = this.galaxyMap.iterator();
        //For each SpacePort on the galaxy map
        while (it.hasNext()) {
            port = it.next();
            //Make sure not to add the current port
            if (port != this.getCurrentPort()) {
                //Check if the distance between the position of this SpacePort
                //and the other SpacePort is greater than the maximum fly distance
                //but less than the maximum jump distance
                distance = this.getFuelNeeded(port);
                if (distance > maximumFlyDistance && distance <= maximumJumpDistance) {
                    portsInRange.add(port);
                }
            }
        }
        return portsInRange;
    }

    /**
     * Determines the maximum jump distance for the NavigationRoom based on the tier
     * A BASIC NavigationRoom can jump 500 units
     * An AVERAGE NavigationRoom can jump 750 units
     * A PRIME NavigationRoom can jump 1000 units
     *
     * @return the maximum jump distance of the NavigationRoom
     */
    public int getMaximumJumpDistance() {
        //Determine maximum jumping distance based on tier of NavigationRoom
        switch (this.getTier()) {
            case BASIC:
                return 500;
            case AVERAGE:
                return 750;
            case PRIME:
                return 1000;
            default:
                return 0;
        }
    }

    /**
     * Create and return a list of possible actions to complete from this NavigationRoom
     *
     * @return a list of possible actions to perform
     */
    public List<String> getActions() {
        SpacePort port;
        int fuelNeeded;
        List<String> navigationActions = new ArrayList<String>();
        //Iterates over all possible ports to jump to and adds to list
        if (this.getPortsInJumpRange() != null) {
            Iterator<SpacePort> jumpIt = this.getPortsInJumpRange().iterator();
            while (jumpIt.hasNext()) {
                port = jumpIt.next();
                String portName = port.getName();
                navigationActions.add("jump to \"" + portName + "\" [COST: 1 HYPERDRIVE CORE]");
            }
        }
        //Iterates over all possible ports to fly to and adds to list
        if (this.getPortsInFlyRange() != null) {
            Iterator<SpacePort> flyIt = this.getPortsInFlyRange().iterator();
            while (flyIt.hasNext()) {
                port = flyIt.next();
                fuelNeeded = this.getFuelNeeded(port);
                String actionToAdd = "fly to \"" + port.getName() + "\": PORT: \"" + port.getName();
                actionToAdd += "\" " + port.getClass().getSimpleName() + " at ";
                actionToAdd += port.getPosition().toString();
                actionToAdd += " [COST: " + fuelNeeded + " TRITIUM FUEL]";
                navigationActions.add(actionToAdd);
            }
        }
        return navigationActions;
    }

    /**
     * Get the fuel required to reach the specified port from the current port
     *
     * @param port the port to try and reach
     * @return how much fuel is required (the distance to it)
     */
    public int getFuelNeeded(SpacePort port) {
        return this.getCurrentPort().getPosition().distanceTo(port.getPosition());
    }

    /**
     * Checks if the current port is a ShipYard, and if it is, returns the ShipYard,
     * otherwise returns null.
     *
     * @return the current port's ShipYard, or null
     */
    public ShipYard getShipYard() {
        //Check if current port is ShipYard
        if (this.getCurrentPort() instanceof ShipYard) {
            return (ShipYard) this.getCurrentPort();
        }
        return null;
    }

    /**
     * Checks if the current port is a Store, and if it is, returns the Store,
     * otherwise returns null.
     *
     * @return the current port's Store, or null
     */
    public Store getStore() {
        //Check if current port is a Store
        if (this.getCurrentPort() instanceof Store) {
            return (Store) this.getCurrentPort();
        }
        return null;
    }

    /**
     * Changes the current port to the new port
     *
     * @param port the port to be currently at
     */
    private void setCurrentPort(SpacePort port) {
        this.currentPort = port;
    }

    /**
     * If the flight chosen is possible, flies from the current SpacePort to the requested
     * SpacePort, and consumes the required amount of fuel.
     *
     * @param portName the name of the SpacePort to fly to
     * @param cargoHold the CargoHold to consume fuel from
     * @throws InsufficientResourcesException if there is not enough fuel to fly to the SpacePort,
     * or one of the rooms is broken
     * @throws NoPathException if the SpacePort is not in flying range
     */
    public void flyTo(String portName, CargoHold cargoHold)
            throws InsufficientResourcesException, NoPathException {
        SpacePort port = this.getSpacePortFromName(portName);
        List<SpacePort> portsInRange = this.getPortsInFlyRange();
        //If port isn't in range, throw exception
        if (!(portsInRange.contains(port))) {
            throw new NoPathException();
        }
        int distance = this.getCurrentPort().getPosition().distanceTo(port.getPosition());
        //If there is not enough fuel, or one of the rooms is broken, throw an exception
        if (distance > cargoHold.getTotalAmountByType(FuelGrade.TRITIUM)
                || cargoHold.isBroken() || this.isBroken()) {
            throw new InsufficientResourcesException();
        }
        //If criteria is met, fly to port and apply damage to rooms, consume fuel
        this.setCurrentPort(port);
        this.damage();
        cargoHold.damage();
        cargoHold.consumeResource(FuelGrade.TRITIUM, distance);
    }

    /**
     * If the jump chosen is possible, jumps from the current SpacePort to the requested SpacePort,
     * and consumes a HyperDrive Core
     *
     * @param portName the name of the SpacePort to jump to
     * @param cargoHold the CargoHold to consume the fuel from
     * @throws InsufficientResourcesException if there is not enough fuel, or one of the rooms
     * is broken
     * @throws NoPathException if the port isn't in jump range
     */
    public void jumpTo(String portName, CargoHold cargoHold)
            throws InsufficientResourcesException, NoPathException {
        SpacePort port = this.getSpacePortFromName(portName);
        List<SpacePort> portsInRange = this.getPortsInJumpRange();
        //If port isn't in jump range, throw exception
        if (!(portsInRange.contains(port))) {
            throw new NoPathException();
        }
        //If there is not a Hyper Drive core, or one of the rooms is broken, throw an exception
        if (cargoHold.getResourceByType(FuelGrade.HYPERDRIVE_CORE).isEmpty()
                || cargoHold.isBroken() || this.isBroken()) {
            throw new InsufficientResourcesException();
        }
        //If criteria is met, fly to port and apply damage to rooms, consume fuel
        this.setCurrentPort(port);
        this.damage();
        cargoHold.damage();
        cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, 1);
    }

    /**
     * Returns a SpacePort based on the specified name given
     *
     * @param name the name of the SpacePort to find
     * @return the SpacePort that was trying to be found
     * @throws NoPathException if the SpacePort searched for does not exist
     */
    public SpacePort getSpacePortFromName(String name)  throws NoPathException {
        //Iterates ove the list of SpacePorts
        for (int i = 0; i < this.galaxyMap.size(); i++) {
            //If the SpacePort's name is equal to the requested name, return this SpacePort
            if (this.galaxyMap.get(i).getName().equals(name)) {
                return this.galaxyMap.get(i);
            }
        }
        throw new NoPathException();
    }
}
