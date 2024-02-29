package srg.resources;

/**
 * A class representing a container that stores a certain type of resource
 */
public class ResourceContainer {
    /**
     * Maximum amount of items a ResourceContainer can hold, 10 resources
     */
    public static final int MAXIMUM_CAPACITY = 10;
    /**
     * The type of Resource being stored in the ResourceContainer
     */
    private ResourceType type;
    /**
     * The amount of resource being stored in the ResourceContainer
     */
    private int amount;

    /**
     * Initialises the ResourceContainer with the specified amount of resources.
     *
     * @require amount >= 1 && amount <= MAXIMUM_CAPACITY
     * @param type the type of resource to be added
     * @param amount the amount of resource to be added
     * @throws IllegalArgumentException if the resource added is fuel, and it is not a FuelContainer
     */
    public ResourceContainer(ResourceType type, int amount) throws IllegalArgumentException {
        //If ResourceType is fuel and the ResourceContainer is not a FuelContainer
        //throw an exception
        if (type == ResourceType.FUEL && !(this instanceof FuelContainer)) {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.amount = amount;
    }

    /**
     * Check if the resource container can store the given resource
     * (i.e. check if it is fuel or not)
     *
     * @param type the type of resource to check
     * @return true if the container can store the resource, false if it cannot
     */
    public boolean canStore(ResourceType type) {
        //Checks if ResourceType is not fuel
        if (type == ResourceType.FUEL) {
            return false;
        }
        return true;
    }

    /**
     * Get the amount of resource that is stored in this ResourceContainer
     *
     * @return the amount of resource stored in the ResourceContainer
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Change the amount of resource the ResourceContainer has
     *
     * @require amount >= 1 && amount <= MAXIMUM_CAPACITY
     * @param amount the new amount of resources
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Check what resource is being stored in this ResourceContainer
     *
     * @return the resource stored in this ResourceContainer
     */
    public ResourceType getType() {
        return this.type;
    }

    /**
     * Return a string detailing what the ResourceContainer stores and how much. Of the form
     *
     * TYPE: AMOUNT
     *
     * @return the string detailing the ResourceContainer
     */
    public String toString() {
        return this.type + ": " + this.amount;
    }

    /**
     * Returns a string containing the type of resource
     *
     * @return the string containing the type of resource
     */
    public String getShortName() {
        return this.type.toString();
    }
}
