package lms.logistics;

import java.util.function.Consumer;

/**
 * Maintains a doubly linked list to maintain the links for each node.
 * Has previous and next item.
 * The path can't have an empty node, as it will throw an illegal
 * argument exception.
 * @version 1.0
 * @ass2
 */
public class Path {

    /**
     * The transport node for the path
     */
    private Transport node;

    /**
     * The path to be set as the previous path for the current path
     */
    private Path previous;

    /**
     * The path to be set as the next path for the curent path
     */
    private Path next;

    /**
     * Constructs a new Path with the same transport node, previous path and
     * next path as the specified one
     *
     * @param path the path to replicate the information from
     * @throws IllegalArgumentException if the path's node is null
     */
    public Path(Path path) throws IllegalArgumentException {
        if (path.getNode() == null) {
            throw new IllegalArgumentException();
        }
        this.node = path.getNode();
        this.next = path.getNext();
        this.previous = path.getPrevious();
    }

    /**
     * Constructs a new Path with the specified Transport node, and sets the
     * previous and next Path objects in the path to null
     *
     * @param node the Transport node for the new Path
     * @throws IllegalArgumentException if the node is null
     */
    public Path(Transport node) throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.next = null;
        this.previous = null;
    }

    /**
     * Creates a new Path with the specified Transport node, and sets the
     * previous and next path to the ones specified.
     *
     * @param node the Transport node for the new Path
     * @param previous the previous Path for the new Path
     * @param next the next Path for the new Path
     * @throws IllegalArgumentException if the node is null
     */
    public Path(Transport node, Path previous, Path next) throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.next = next;
        this.previous = previous;
    }

    /**
     * Return the Transport node of the Path
     *
     * @return the Transport node of the Path
     */
    public Transport getNode() {
        return this.node;
    }

    /**
     * Return the previous Path from this Path
     *
     * @return the previous Path
     */
    public Path getPrevious() {
        return this.previous;
    }

    /**
     * Return the next Path from this Path
     *
     * @return the next Path
     */
    public Path getNext() {
        return this.next;
    }

    /**
     * Set the previous Path for this Path as specified, and this path as the next
     * for the Path specified.
     *
     * @param path the Path to be set as the previous Path
     */
    public void setPrevious(Path path) {
        this.previous = path;
        if (path.getNext() == null || !(path.getNext().equals(this))) {
            path.setNext(this);
        }
    }

    /**
     * Set the next Path for this Path as specified, and this path as the previous
     * for the Path specified.
     *
     * @param path the Path to be set as the next Path
     */
    public void setNext(Path path) {
        this.next = path;
        if (path.getPrevious() == null || !(path.getPrevious().equals(this))) {
            path.setPrevious(this);
        }
    }

    /**
     * Returns the head of the path, which is the first element.
     *
     * @return the head Path
     */
    public Path head() {
        //Check if this path is the start
        if (this.getPrevious() == null) {
            return this;
        }
        //Look at the previous path and check again
        return this.getPrevious().head();
    }

    /**
     * Returns the tail of the path, which is the last element
     *
     * @return the tail Path
     */
    public Path tail() {
        //Check if this path is the end
        if (this.getNext() == null) {
            return this;
        }
        //Look at the next path and check again
        return this.getNext().tail();
    }

    /**
     * This method takes a Transport Consumer,
     * using the Consumer&lt;T&gt; functional interface from java.util.
     * It finds the tail of the path and calls
     * Consumer&lt;T&gt;'s accept() method with the tail node as an argument.
     * Then it traverses the Path until the head is reached,
     * calling accept() on all nodes.
     *
     * This is how we call the tick method for all the different transport items.
     *
     * @param consumer Consumer&lt;Transport&gt;
     * @see java.util.function.Consumer
     * @provided
     */
    public void applyAll(Consumer<Transport> consumer) {
        Path path = tail(); // IMPORTANT: go backwards to aid tick
        do {
            consumer.accept(path.node);
            path = path.previous;
        } while (path != null);
    }

    /**
     * A string that provides a list of Path nodes from a Producer, along to a
     * Receiver. Of the form
     *
     * START -> <NODE-ID> -> <NODE-ID> -> ... -> END
     *
     * @return
     */
    @Override
    public String toString() {
        Path currentPath = this.head();
        String pathDetails = "START -> ";
        //Run until the next path doesn't exist, i.e. is the last path
        while (currentPath.getNext() != null) {
            pathDetails += currentPath.getNode().toString() + " -> ";
            currentPath = currentPath.getNext();
        }
        pathDetails += currentPath.getNode().toString() + " -> ";
        pathDetails += "END";
        return pathDetails;
    }

    /**
     * Checks if another Object is equal, i.e. is also a Path and has the same
     * Transport node
     *
     * @param o the Object to be compared to
     * @return true if the Object is equal to the Path, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Path && ((Path) o).getNode().equals(this.getNode())) {
            return true;
        }
        return false;
    }
}
