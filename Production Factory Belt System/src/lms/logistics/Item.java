package lms.logistics;

/**
 * A class representing an item that can be produced by a Producer, carried along Belts and
 * sent into a Receiver.
 */
public class Item {
    /**
     * The name of the Item
     */
    private String name;

    /**
     * Initialise the item with a name, throws exception if name is null or empty
     *
     * @param name the name of the item
     * @throws IllegalArgumentException if name is null or empty
     */
    public Item(String name) throws IllegalArgumentException {
        this.name = name;
        if (this.name == "" || this.name == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Check if two items are equal (both are items and have the same name)
     *
     * @param o the object to be compared to
     * @return true if they are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Item && o.toString().equals(this.toString())) {
            return true;
        }
        return false;
    }

    /**
     * Implements a hashcode for items based on the name of it
     *
     * @return the hashcode for the item
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Returns the name of the item as a string
     *
     * @return the name of the item as a string
     */
    @Override
    public String toString() {
        return this.name;
    }
}
