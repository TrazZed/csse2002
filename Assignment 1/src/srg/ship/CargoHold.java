package srg.ship;

import srg.exceptions.*;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Subclass of a Room that can be used to store resources
 */
public class CargoHold extends Room {

    /**
     * The list of resources stored in the CargoHold
     */
    private List<ResourceContainer> resources;

    /**
     * Create the CargoHold with a specific room tier specified
     *
     * @param roomTier the room tier of the CargoHold
     */
    public CargoHold(RoomTier roomTier) {
        super(roomTier);
        this.resources = new ArrayList<ResourceContainer>();
    }

    /**
     * Determine the remaining capacity of the CargoHold
     * i.e. how many more resources can it store
     *
     * @return the remaining capacity of the CargoHold
     */
    public int getRemainingCapacity() {
        return this.getMaximumCapacity() - this.getResources().size();
    }

    /**
     * Determine the max number of resources the CargoHold can store
     *
     * @return the maximum number of items it can store
     */
    public int getMaximumCapacity() {
        //Returns maximum capacity based on the tier of the CargoHold
        switch (this.getTier()) {
            case BASIC:
                return 5;
            case AVERAGE:
                return 10;
            case PRIME:
                return 15;
        }
        return 0;
    }

    /**
     * Determine what resources the CargoHold currently stores
     *
     * @return a list of resources stored in the CargoHold
     */
    public List<ResourceContainer> getResources() {
        return this.resources;
    }

    /**
     * Attempts to add a new resource to the CargoHold, and if possible, stores it
     *
     * @param resource the resource to be stored in the CargoHold
     * @throws InsufficientCapcaityException if there is no room in the CargoHold
     */
    public void storeResource(ResourceContainer resource) throws InsufficientCapcaityException {
        //Check if CargoHold has enough room to store resource, and if not, throw exception
        if (this.getResources().size() >= this.getMaximumCapacity()) {
            throw new InsufficientCapcaityException();
        }
        //Adds resource to the CargoHold
        this.getResources().add(resource);
    }

    /**
     * Gets a list of ResourceContainers held in the CargoHold holding a specific resource type
     *
     * @param resource the resource to return a list of stored in the CargoHold
     * @return the list of ResourceContainers storing the resource
     */
    public List<ResourceContainer> getResourceByType(ResourceType resource) {
        List<ResourceContainer> selectedResources = new ArrayList<ResourceContainer>();
        //Iterate over each ResourceContainer
        for (int i = 0; i < this.getResources().size(); i++) {
            //If the ResourceContainer is the type we are looking for
            if (this.getResources().get(i).getType() == resource) {
                //Add this resource to the list
                selectedResources.add(this.getResources().get(i));
            }
        }
        return selectedResources;
    }

    /**
     * Gets a list of FuelContainers being held in the CargoHold holding a specific fuel grade
     *
     * @param grade the grade of fuel to return a list of stored in the CargoHold
     * @return the list of FuelContainers storing the fuel grade
     */
    public List<ResourceContainer> getResourceByType(FuelGrade grade) {
        List<ResourceContainer> selectedResources = new ArrayList<ResourceContainer>();
        //Iterate over each ResourceContainer
        for (int i = 0; i < this.getResources().size(); i++) {
            //Check if the ResourceContainer is a FuelContainer
            if (this.getResources().get(i) instanceof FuelContainer) {
                //Check if the FuelContainer has the specified grade
                if (((FuelContainer) this.getResources().get(i)).getFuelGrade() == grade) {
                    //Add this to the list
                    selectedResources.add(this.getResources().get(i));
                }
            }
        }
        return selectedResources;
    }

    /**
     * Sum the quantity of a specific resource stored in the CargoHold by ResourceContainers
     *
     * @param type the type of resource to sum over
     * @return the amount of resource specified in the CargoHold
     */
    public int getTotalAmountByType(ResourceType type) {
        int total = 0;
        //Get a list of ResourceContainers holding the specified type
        List<ResourceContainer> resourceContainersSpecified = getResourceByType(type);
        //For each ResourceContainer with the type, do a running sum
        for (int i = 0; i < resourceContainersSpecified.size(); i++) {
            total += resourceContainersSpecified.get(i).getAmount();
        }
        return total;
    }

    /**
     * Sum the quantity of a specific fuel grade stored in the CargoHold by FuelContainers
     *
     * @param grade the type of fuel grade to sum over
     * @return the amount of fuel grade specified in the CargoHold
     */
    public int getTotalAmountByType(FuelGrade grade) {
        int total = 0;
        //Get a list of FuelContainers holding the specified grade
        List<ResourceContainer> resourceContainersSpecified = getResourceByType(grade);
        //For each FuelContainer with the grade, do a running sum
        for (int i = 0; i < resourceContainersSpecified.size(); i++) {
            total += resourceContainersSpecified.get(i).getAmount();
        }
        return total;
    }

    /**
     * Removes all empty containers from the CargoHold
     */
    private void removeEmptyContainers() {
        Iterator<ResourceContainer> it = this.getResources().iterator();
        while (it.hasNext()) {
            ResourceContainer currentContainer = it.next();
            if (currentContainer.getAmount() == 0) {
                it.remove();
            }
        }
    }

    /**
     * Consumes the specified amount of non-fuel resources, and removes empty ResourceContainers
     *
     * @param type the type of non-fuel resource to be consumed
     * @param amount the amount of resource to be consumed
     * @throws InsufficientResourcesException if amount is greater than the total amount
     * of stored resource
     */
    public void consumeResource(ResourceType type, int amount)
            throws InsufficientResourcesException {
        //If the ResourceType is Fuel, throw exception
        if (type == ResourceType.FUEL) {
            throw new IllegalArgumentException();
        }
        //If requesting to consume more resource than available, throw exception
        if (amount > this.getTotalAmountByType(type)) {
            throw new InsufficientResourcesException();
        }

        //Start iterating over each container to use
        for (int i = 0; i < this.getResources().size(); i++) {
            ResourceContainer container = this.getResources().get(i);
            //Check if the resource container holds the resource wanted to be consumed
            if (container.getType() == type) {
                //If container has more than enough resource, then
                //use this container only and exit the loop
                if (container.getAmount() >= amount) {
                    container.setAmount(container.getAmount() - amount);
                    break;
                } else {
                    //Runs if the requested amount is more than the current container holds,
                    //so use all from the current container and iterate to the next one
                    amount -= container.getAmount();
                    container.setAmount(0);
                }
            }
        }
        //Remove all empty ResourceContainers
        this.removeEmptyContainers();
    }

    /**
     * Consumes the specified amount of fuel resources, and removes any empty FuelContainers
     *
     * @require amount > 0
     * @param grade the grade of fuel to be consumed
     * @param amount the amount of fuel to be consumed
     * @throws InsufficientResourcesException if amount is greater than the total
     * amount of stored fuel
     */
    public void consumeResource(FuelGrade grade, int amount) throws InsufficientResourcesException {
        //If requesting to consume more fuel than available, throw exception
        if (amount > this.getTotalAmountByType(grade)) {
            throw new InsufficientResourcesException();
        }

        //Start iterating over each container to use
        for (int i = 0; i < this.getResources().size(); i++) {
            //Make sure to only choose from FuelContainers
            ResourceContainer container = this.getResources().get(i);
            if (container instanceof FuelContainer) {
                //Check if the FuelContainer holds the resource wanted to be consumed
                if (((FuelContainer) container).getFuelGrade() == grade) {
                    //If the container has more than enough resource,
                    //then use this container only and exit the loop
                    if (container.getAmount() >= amount) {
                        container.setAmount(container.getAmount() - amount);
                        break;
                    } else {
                        //Runs if the requested amount is more than the current container
                        //holds, so use as much as possible from
                        //the current container and iterate to the next one
                        amount -= container.getAmount();
                        container.setAmount(0);
                    }
                }
            }
        }
        //Remove all empty ResourceContainers
        Iterator<ResourceContainer> it = this.getResources().iterator();
        while (it.hasNext()) {
            ResourceContainer currentContainer = it.next();
            if (currentContainer.getAmount() == 0) {
                it.remove();
            }
        }
    }

    /**
     * Creates a string detailing information about the CargoHold, including
     * name, tier, health, if it needs repairing, capacity, maximum capacity,
     * number of ResourceContainers (including FuelContainers), and details for
     * each ResourceContainer.
     *
     * @return the string detailing the CargoHold
     */
    @Override
    public String toString() {
        String cargoString = "ROOM: " + this.getClass().getSimpleName();
        cargoString += "(" + this.getTier().toString() + ")";
        cargoString += " health: " + this.getHealth();
        cargoString += "%, needs repair: " + this.needsRepair();
        cargoString += ", capacity: " + this.getMaximumCapacity();
        cargoString += ", items: " + this.getResources().size();
        for (int i = 0; i < this.getResources().size(); i++) {
            cargoString += "\n    " + this.getResources().get(i).toString();
        }
        return cargoString;
    }

    /**
     * Creates a list of possible actions to perform in this CargoHold
     * A CargoHold is able to repair Rooms in the Ship if it has a REPAIR_KIT available
     * Of the form
     *
     * repair CargoHold [COST: 1 REPAIR_KIT]
     * repair NavigationRoom [COST: 1 REPAIR_KIT]
     *
     * @return the list of possible actions
     */
    @Override
    public List<String> getActions() {
        List<String> cargoActions = new ArrayList<String>();
        //If there exists a Repair Kit inside the list, add possible actios to
        //repair any room on the ship
        for (int i = 0; i < this.getResources().size(); i++) {
            if (this.getResources().get(i).getType() == ResourceType.REPAIR_KIT) {
                cargoActions.add("repair CargoHold [COST: 1 REPAIR_KIT]");
                cargoActions.add("repair NavigationRoom [COST: 1 REPAIR_KIT]");
                break;
            }
        }
        return cargoActions;
    }
}
