package srg.ship;

import srg.cli.given.IO;
import srg.cli.given.PurchaseCommand;
import srg.cli.given.ShipCommand;
import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ports.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spaceship, which has a unique name, a unique ID, a registered owner,
 * a CargoHold and a NavigationRoom.
 * @version 1.0
 * @ass1
 */
public class Ship {

    /**
     * The CargoHold for the Ship
     */
    private CargoHold cargoHold;
    /**
     * The NavigationRoom for the Ship
     */
    private NavigationRoom navigationRoom;
    /**
     * The name of the Ship
     */
    private String name;
    /**
     * The name of the owner of the Ship
     */
    private String owner;
    /**
     * The given ID of the Ship
     */
    private String id;
    /**
     * The tier of the CargoHold on the Ship
     */
    private RoomTier cargoHoldTier;
    /**
     * The tier of the NavigationRoom on the Ship
     */
    private RoomTier navigationRoomTier;
    /**
     * The list of all SpacePorts in the galaxy for the Ship
     */
    private List<SpacePort> galaxyMap;

    /**
     * Creates a new Ship with the specified attributes, and stores default resource of
     * 5 x REPAIR_KIT, 100 x TRITIUM, 5 x HYPERDRIVE_CORE
     *
     * @param name the name of the ship
     * @param owner the owner of the ship
     * @param id the id of the ship
     * @param cargoHoldTier the tier of the CargoHold
     * @param navigationRoomTier the tier of the NavigationRoom
     * @param galaxyMap the list of potential SpacePorts
     * @throws InsufficientCapcaityException if cannot store new item due to out of room
     */
    public Ship(String name, String owner, String id, RoomTier cargoHoldTier,
                RoomTier navigationRoomTier, List<SpacePort> galaxyMap) {
        this.name = name;
        this.owner = owner;
        this.id = id;
        this.cargoHoldTier = cargoHoldTier;
        this.navigationRoomTier = navigationRoomTier;
        this.galaxyMap = galaxyMap;

        //Initialise Ship with a CargoHold and NavigationRoom
        this.cargoHold = new CargoHold(this.cargoHoldTier);
        this.navigationRoom = new NavigationRoom(this.navigationRoomTier, this.galaxyMap);

        //Adds initial resources to the CargoHold, and squash exception
        try {
            this.cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT, 5));
            this.cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM, 100));
            this.cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 5));
        } catch (InsufficientCapcaityException ignore) {
            //ignore
        }
    }

    /**
     * Returns the room based on the given String
     *
     * @param name the name of the room to retrieve
     * @return the room corresponding to the name given
     * @throws IllegalArgumentException if the room with that name does not exist
     */
    public Room getRoomByName(String name) throws IllegalArgumentException {
        //Return CargoHold if user requests for CargoHold, or
        //Return NavigationRoom if user requests for NavigationRoom, else
        //Throws exception if the room requested does not exist
        if (name.equals("CargoHold")) {
            return this.cargoHold;
        } else if (name.equals("NavigationRoom")) {
            return this.navigationRoom;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Details information about the Ship and its CargoHold and NavigationRoom
     *
     * @return a string detailing the Ship
     */
    public String toString() {
        String shipDetails = "SHIP: " + this.name + " (" + this.id + ") owned by " + this.owner;
        shipDetails += "\n----\n";
        shipDetails += this.cargoHold.toString();
        shipDetails += "\n";
        shipDetails += this.navigationRoom.toString();
        return shipDetails;
    }

    /**
     * Create and return a list of all possible actions performable by the ship, i.e. the
     * actions accessible by the CargoHold and NavigationRoom. Combination of the
     * CargoHold and NavigationRoom actions.
     *
     * @return a list of Strings of possible actions
     */
    public List<String> getActions() {
        List<String> cargoActions = this.cargoHold.getActions();
        List<String> navigationActions = this.navigationRoom.getActions();
        List<String> shipActions = new ArrayList<String>();
        shipActions.addAll(cargoActions);
        shipActions.addAll(navigationActions);
        return shipActions;
    }

    /**
     * This method is provided as it interfaces with the command line interface.
     *
     * @param ioHandler Handles IO
     * @param command   A command to the ship
     */
    public void performCommand(IO ioHandler, ShipCommand command) {
        try {
            processCommand(ioHandler, command);
        } catch (InsufficientResourcesException error) {
            ioHandler.writeLn("Unable to perform action due to broken component or " +
                    "insufficient resources."
                    + System.lineSeparator() + error.getMessage());
        } catch (IllegalArgumentException | NoPathException | InsufficientCapcaityException error) {
            ioHandler.writeLn(error.getMessage());
        }
    }

    /**
     * This method is provided as it interfaces with the command line interface.
     * @param ioHandler Handles IO
     * @param command   A command to the ship
     * @throws InsufficientResourcesException
     *      If an action cannot be performed due to a lack or resources or a broken Room.
     * @throws NoPathException
     *      If a specified SpacePort cannot be found, or cannot be reached.
     * @throws InsufficientCapcaityException
     *      If resources cannot be added because there is not enough capacity in the CargoHold.
     */
    public void processCommand(IO ioHandler, ShipCommand command)
            throws InsufficientResourcesException, NoPathException, InsufficientCapcaityException {
        switch (command.type) {
            case SHOW_ROOM -> {
                ioHandler.writeLn(getRoomByName(command.value).toString());
            }
            case FLY_TO -> {
                navigationRoom.flyTo(command.value, cargoHold);
            }
            case JUMP_TO -> {
                navigationRoom.jumpTo(command.value, cargoHold);
            }
            case REPAIR_ROOM -> {
                // Ignore whether CargoHold may be broken
                cargoHold.consumeResource(ResourceType.REPAIR_KIT, 1);
                getRoomByName(command.value).resetHealth();

            }
            case UPGRADE_ROOM -> {
                ShipYard shipYard = navigationRoom.getShipYard();
                if (shipYard == null) {
                    ioHandler.writeLn("Can only upgrade when docked at a ShipYard.");
                    return;
                }

                shipYard.upgrade(getRoomByName(command.value));
            }
            case PURCHASE_ITEM -> {
                PurchaseCommand purchaseCommand = (PurchaseCommand) command;
                Store store = navigationRoom.getStore();
                if (store == null) {
                    ioHandler.writeLn("Can only purchase items at a Store.");
                    return;
                }
                ResourceContainer resource = store.purchase(purchaseCommand.item,
                        purchaseCommand.amount);
                cargoHold.storeResource(resource);
            }
            case SHOW_PORT -> {
                ioHandler.writeLn(navigationRoom.getCurrentPort().toString());
                ioHandler.writeLn(String.join(System.lineSeparator(),
                        navigationRoom.getCurrentPort().getActions()));
            }
            case SHOW_ACTIONS -> {
                ioHandler.writeLn(String.join(System.lineSeparator(), getActions()));
            }

        }

    }
}
