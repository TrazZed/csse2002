package srg.ports;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ship.CargoHold;
import srg.ship.RoomTier;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a form of SpacePort called a Store
 */
public class Store extends SpacePort {

    /**
     * The CargoHold being stored at the SpacePort
     */
    private CargoHold cargoHold;
    /**
     * Initialises the Store as a SpacePort with a unique name and position.
     * Initialises the Store with 4 ResourceContainers, storing each ResourceType and
     * FuelGrade with maximum capacity
     *
     * @require name must be unique
     * @param name the name of the Store
     * @param position the position of the Store
     */

    public Store(String name, Position position) {
        super(name, position);
        this.cargoHold = new CargoHold(RoomTier.AVERAGE);
        //Initialise the Store with 10 of every resource and 1000 of every fuel grade
        try {
            this.cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, 10));
            this.cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 1000));
            this.cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM, 1000));
        } catch (InsufficientCapcaityException e) {
            //ignore
        }
    }

    /**
     * If there is enough of the selected resource available, removes this from the store
     * and returns a ResourceContainer containing the same amount of the specified resource
     *
     * @require amount > 0 && amount < MAXIMUM_CAPACITY
     * @param item the item to be removed ("purchased")
     * @param amount the amount of item to be removed ("purchased")
     * @return a ResourceContainer with the same amount of items as removed from the Store
     * @throws InsufficientResourcesException if there is not enough resource available in the
     * store as requested by amount
     */
    public ResourceContainer purchase(String item, int amount)
            throws InsufficientResourcesException {
        switch (item) {
            case "REPAIR_KIT":
                if (this.cargoHold.getTotalAmountByType(ResourceType.REPAIR_KIT) < amount) {
                    throw new InsufficientResourcesException();
                }
                //Removes Repair Kits from Store, and adds to new ResourceContainer
                this.cargoHold.consumeResource(ResourceType.REPAIR_KIT, amount);
                return new ResourceContainer(ResourceType.REPAIR_KIT, amount);
            case "HYPERDRIVE_CORE":
                //Checks if there are enough HyperDrive Cores, throws exception if not
                if (this.cargoHold.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE) < amount) {
                    throw new InsufficientResourcesException();
                }
                //Removes HyperDrive Cores from Store, and adds to new FuelContainer
                this.cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, amount);
                return new FuelContainer(FuelGrade.HYPERDRIVE_CORE, amount);
            case "TRITIUM":
                //Checks if there is enough Tritium, throws exception if not
                if (this.cargoHold.getTotalAmountByType(FuelGrade.TRITIUM) < amount) {
                    throw new InsufficientResourcesException();
                }
                //Removes Tritium from Store, and adds to new FuelContainer
                this.cargoHold.consumeResource(FuelGrade.TRITIUM, amount);
                return new FuelContainer(FuelGrade.TRITIUM, amount);
            default:
                //Runs if the resource does not exist, throw exception
                throw new InsufficientResourcesException("The specified resource does not exist.");
        }
    }

    /**
     * Create and return a list of possible items that can be bought from the Store. Of the form
     *
     * buy TYPE 1..AMOUNT
     *
     * @return the list of possible actions
     */
    @Override
    public List<String> getActions() {
        List<String> storeActions = new ArrayList<String>();
        String type;
        int amount;
        //For each ResourceContainer or FuelContainer
        for (int i = 0; i < this.cargoHold.getResources().size(); i++) {
            if (this.cargoHold.getResources().get(i) instanceof FuelContainer) {
                FuelContainer fuelContainer = (FuelContainer) this.cargoHold.getResources().get(i);
                type = fuelContainer.getFuelGrade().toString();
            } else {
                type = this.cargoHold.getResources().get(i).getType().toString();
            }
            amount = this.cargoHold.getResources().get(i).getAmount();
            storeActions.add("buy " + type + " 1.." + amount);
        }
        return storeActions;
    }
}
