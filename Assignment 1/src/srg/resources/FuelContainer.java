package srg.resources;

/**
 * A subclass of a ResourceContainer used to store fuel.
 */
public class FuelContainer extends ResourceContainer {

    /**
     * The maximum capacity of the FuelContainer
     */
    public static final int MAXIMUM_CAPACITY = 1000;
    /**
     * The grade of fuel being stored in the FuelContainer
     */
    private FuelGrade grade;

    /**
     * Initialises the FuelContainer with a specific fuel grade and amount
     *
     * @require amount >= 1 && amount <= MAXIMUM_CAPACITY
     * @param grade the grade of fuel to add
     * @param amount the amount of fuel to add
     */
    public FuelContainer(FuelGrade grade, int amount) {
        super(ResourceType.FUEL, amount);
        this.grade = grade;
    }

    /**
     * Determines the grade of the fuel being stored
     *
     * @return the grade of fuel being stored
     */
    public FuelGrade getFuelGrade() {
        return this.grade;
    }

    /**
     * Check whether the FuelContainer can store a specific resource type
     * i.e. check if the type of resource is a fuel
     *
     * @param type the type of resource to check
     * @return true if it can store it, false if it cannot
     */
    @Override
    public boolean canStore(ResourceType type) {
        //Check if ResourceType is a fuel
        if (type == ResourceType.FUEL) {
            return true;
        }
        return false;
    }

    /**
     * Create a string detailing the FuelContainer with information about the amount of
     * fuel stored, and the grade of fuel stored. This if of the form
     *
     * FUEL: AMOUNT - GRADE
     *
     * @return a String detailing the FuelContainer
     */
    @Override
    public String toString() {
        return "FUEL: " + this.getAmount() + " - " + this.getFuelGrade();
    }

    /**
     * Returns a string containing the type of resource
     *
     * @return the string containing the type of resource
     */
    public String getShortName() {
        return this.getFuelGrade().toString();
    }
}
