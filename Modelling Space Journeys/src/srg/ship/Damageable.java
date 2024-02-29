package srg.ship;

/**
 * An interface to represent how Damageable something is
 */
public interface Damageable {
    /**
     * The Damage Rate for the object
     */
    static final int DAMAGE_RATE = 5;
    /**
     * The Health Multiplier for the object
     */
    static final int HEALTH_MULTIPLIER = 5;
    /**
     * The Repair Threshold for the object
     */
    static final int REPAIR_THRESHOLD = 30;

    /**
     * Return the current health of the Damageable object
     * @return health of Damageable object
     */
    int getHealth();

    /**
     * Determine whether the Damageable object needs repairing, i.e.
     * less than or equal to 30% health
     *
     * @return true if the Damageable object needs repairing, false if it does not
     */
    default boolean needsRepair() {
        if (this.getHealth() <= 30) {
            return true;
        }
        return false;
    }

    /**
     * Applies damage to the Damageable object
     */
    void damage();

    /**
     * Determine whether the Damageable object is broken, i.e. less than or equal to 0% health
     * @return true if the Damageable object is broken, false if it is not
     */
    default boolean isBroken() {
        if (this.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Resets the health of the Damageable object to maximum
     */
    void resetHealth();

    /**
     * Changes the damage rate of the Damageable object
     *
     * @require newDamageRate >= 0
     * @param newDamageRate the new damage rate
     */
    void setDamageRate(int newDamageRate);

}
