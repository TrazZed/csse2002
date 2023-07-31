package srg.ship;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that defines a basic room for the ship, which are Damageable objects.
 */
public class Room implements Damageable {

    /**
     * The DamageRate of the Room
     */
    private int damageRate;
    /**
     * The current Health of the Room
     */
    private int health;
    /**
     * The Maximum Health of the Room
     */
    private int maxHealth;
    /**
     * The tier of the Room
     */
    private RoomTier tier;

    /**
     * Constructs a room with a basic tier. Starts at maximum health
     */
    public Room() {
        this.tier = RoomTier.BASIC;
        this.damageRate = this.tier.damageMultiplier * DAMAGE_RATE;
        this.maxHealth = this.tier.healthMultiplier * HEALTH_MULTIPLIER;
        this.health = this.maxHealth;
    }

    /**
     * Constructs a room with the specified tier. Starts at maximum health
     *
     * @param tier the tier level of the room
     */
    public Room(RoomTier tier) {
        this.tier = tier;
        this.damageRate = this.tier.damageMultiplier * DAMAGE_RATE;
        this.maxHealth = this.tier.healthMultiplier * HEALTH_MULTIPLIER;
        this.health = this.maxHealth;
    }

    /**
     * Upgrades the room based on its current tier. Possible upgrades are
     *
     * BASIC --> AVERAGE
     * AVERAGE --> PRIME
     *
     * PRIME rooms do not get upgraded
     */
    public void upgrade() {
        //Upgrades tier based on the current tier
        switch (this.tier) {
            case BASIC:
                this.tier = RoomTier.AVERAGE;
                break;
            case AVERAGE:
                this.tier = RoomTier.PRIME;
                break;
        }
        //Update all the stats and reset to maximum health
        this.damageRate = this.tier.damageMultiplier * DAMAGE_RATE;
        this.maxHealth = this.tier.healthMultiplier * HEALTH_MULTIPLIER;
        this.health = this.maxHealth;
    }

    /**
     * Change the rooms damage rate to a new one
     *
     * @require newDamageRate >= 0
     * @param newDamageRate the new damage rate
     */
    public void setDamageRate(int newDamageRate) {
        this.damageRate = newDamageRate;
    }

    /**
     * Determine the current percentage health of the room, as an integer rounded down
     *
     * @return the percentage health of the room rounded down
     */
    public int getHealth() {
        //Determine current health as a percentage
        return (this.health * 100) / this.maxHealth;
    }

    /**
     * Damages the room by one unit of damageRate
     */
    public void damage() {
        this.health -= this.damageRate;
    }

    /**
     * Resets the health of the room back to maximum
     */
    public void resetHealth() {
        this.health = this.maxHealth;
    }

    /**
     * Gets the status of the room tier
     *
     * @return the room tier
     */
    public RoomTier getTier() {
        return this.tier;
    }

    /**
     * Returns a String detailing the Room and its attributes such as Tier, Health,
     * and if it needs repairing. Of the form
     *
     * ROOM: TYPE(TIER) health: HEALTH%, needs repair: NEEDS_REPAIR
     *
     * @return the String detailing the Room
     */
    @Override
    public String toString() {
        String roomType = this.getClass().getSimpleName();
        String roomDetails = "ROOM: " + roomType + "(" + this.getTier() + ") health: ";
        roomDetails += this.getHealth() + "%, needs repair: " + this.needsRepair();
        return roomDetails;
    }

    /**
     * Gets the list of actions possible to perform from this room.
     *
     * @return the list of possible actions
     */
    public List<String> getActions() {
        return new ArrayList<String>();
    }
}
