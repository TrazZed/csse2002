package lms.io;

import lms.exceptions.FileFormatException;
import lms.exceptions.UnsupportedActionException;
import lms.grid.Coordinate;
import lms.grid.GameGrid;
import lms.grid.GridComponent;
import lms.logistics.Item;
import lms.logistics.Path;
import lms.logistics.Transport;
import lms.logistics.belts.Belt;
import lms.logistics.container.Producer;
import lms.logistics.container.Receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A class responsible for loading and parsing a text file into a GameGrid
 */
public class GameLoader {

    /**
     * Parses through and reads a text file to construct a GameGrid of the grid represented in the
     * text file.
     *
     * @param reader the Reader that is reading the file
     * @return the GameGrid represented in the text file
     * @throws IOException if there is an error from the reader
     * @throws FileFormatException if the file is not in the correct format
     */
    public static GameGrid load(Reader reader) throws IOException, FileFormatException {
        BufferedReader buffer = new BufferedReader(reader);
        //Check if first line is integer and set range and gridSize
        int range;
        try {
            range = Integer.parseInt(buffer.readLine());
        } catch (NumberFormatException e) {
            throw new FileFormatException();
        }
        //Check to see if next line has 5 _
        checkUnderscores(buffer);
        //Check if next two lines are integers and set number of producers and receivers
        int numProducers;
        int numReceivers;
        try {
            numProducers = Integer.parseInt(buffer.readLine());
            numReceivers = Integer.parseInt(buffer.readLine());
        } catch (NumberFormatException e) {
            throw new FileFormatException();
        }
        //Check to see if next line has 5 _
        checkUnderscores(buffer);
        //Next two sections for items, create arrays of appropriate lengths
        Item[] itemsProducer = addItemsToList(numProducers, buffer);
        checkUnderscores(buffer);
        Item[] itemsReceiver = addItemsToList(numReceivers, buffer);
        checkUnderscores(buffer);
        GameGrid game = new GameGrid(range);
        //Read the Hexagon on the following lines (range of gridSize)
        int numOfPaths = constructGrid(range, buffer, game, itemsReceiver, itemsProducer);
        checkUnderscores(buffer);
        //Construct all the paths
        determinePaths(buffer, game);
        //Check that the paths are valid
        checkValidPaths(numOfPaths, game);
        return game;
    }

    /**
     * A helper method that retrieves the Node associated with the specified
     * id
     *
     * @param idText the id for the node to find, as a String
     * @param game the GameGrid to retrieve from
     * @return the Transport node with the specified id, or null if it doesn't exist
     * @throws FileFormatException if there is an error in file formatting
     */
    private static Transport getNodeFromId(String idText, GameGrid game)
            throws FileFormatException {
        Transport node;
        int id = Integer.parseInt(idText);
        for (GridComponent component : game.getGrid().values()) {
            if (component instanceof Transport && ((Transport) component).getId() == id) {
                node = (Transport) component;
                return node;
            }
        }
        //If ID specified isn't on the grid, throw exception
        throw new FileFormatException();
    }

    /**
     * A helper method to check the underscore line to separate the sections
     *
     * @param buffer the BufferedReader for the file
     * @throws IOException if there is an error from the reader
     * @throws FileFormatException if the file is not in the correct format
     */
    private static void checkUnderscores(BufferedReader buffer)
            throws IOException, FileFormatException {
        String line = buffer.readLine();
        if (!(line.substring(0, 5).equals("_____"))) {
            throw new FileFormatException();
        }
    }

    /**
     * A helper method to add items to a list from the file
     *
     * @param number the number of expected items
     * @param buffer the BufferedReader for the file
     * @return the array of Items
     * @throws FileFormatException if the file is not in the correct format
     */
    private static Item[] addItemsToList(int number, BufferedReader buffer)
            throws FileFormatException {
        Item[] items = new Item[number];
        //Add items
        for (int i = 0; i < number; i++) {
            try {
                items[i] = new Item(buffer.readLine());
            } catch (Exception e) {
                throw new FileFormatException();
            }
        }
        return items;
    }

    /**
     * Determines the starting coordinate for the row by using the origin and row
     * number.
     *
     * @param row the row currently on, middle row is 0, top is -range, bottom is range
     * @param range the range of the grid
     * @return the Coordinate at the start of the row
     */
    private static Coordinate determineStartCoordinate(int row, int range) {
        Coordinate coord = new Coordinate(0, 0, 0);
        int count = 0;
        for (int j = 0; j < row; j++) {
            coord = coord.getBottomLeft();
            count++;
        }
        for (int j = 0; j > row; j--) {
            coord = coord.getTopLeft();
            count++;
        }
        while (count < range) {
            coord = coord.getLeft();
            count++;
        }
        return coord;
    }

    /**
     * A helper method to determine the paths between each node from the file
     *
     * @param buffer the BufferedReader for the file
     * @param game the GameGrid being implemented
     * @throws IOException if there is an error from the reader
     * @throws FileFormatException if there is an error in the file formatting
     */
    private static void determinePaths(BufferedReader buffer, GameGrid game)
            throws IOException, FileFormatException {
        String line = buffer.readLine();
        Transport transportFirst;
        Transport transportSecond;
        try {
            while (line != null) {
                String[] ids = line.split("[-,]+");
                transportFirst = getNodeFromId(ids[0], game);
                if (transportFirst instanceof Belt) {
                    for (int i = 1; i < line.length(); i++) {
                        //If the form ID-,ID
                        if (line.charAt(i) == '-' && line.charAt(i + 1) == ',') {
                            transportSecond = getNodeFromId(ids[1], game);
                            if (transportSecond instanceof Producer) {
                                throw new FileFormatException();
                            }
                            transportSecond.setInput(transportFirst.getPath());
                        } else if (line.charAt(i) == '-' && transportFirst.getInput() == null) {
                            //If the form ID-ID
                            transportSecond = getNodeFromId(ids[1], game);
                            if (transportSecond instanceof Receiver) {
                                throw new FileFormatException();
                            }
                            transportSecond.setOutput(transportFirst.getPath());
                        } else if (line.charAt(i) == ',' && transportFirst.getOutput() == null
                                && i != line.length() - 1) {
                            //If the form ID,ID
                            transportSecond = getNodeFromId(ids[2], game);
                            if (transportSecond instanceof Producer) {
                                throw new FileFormatException();
                            }
                            transportSecond.setInput(transportFirst.getPath());
                        }
                    }
                }
                if (transportFirst instanceof Producer) {
                    if (line.charAt(1) == '-') {
                        transportSecond = getNodeFromId(ids[1], game);
                        if (transportSecond instanceof Producer) {
                            throw new FileFormatException();
                        }
                        transportSecond.setInput(transportFirst.getPath());
                    }
                    if (line.length() >= 3 && line.charAt(2) == ',') {
                        throw new FileFormatException();
                    }
                    if (line.length() >= 4 && line.charAt(3) == ',') {
                        throw new FileFormatException();
                    }
                }
                if (transportFirst instanceof Receiver) {
                    if (line.charAt(1) == '-') {
                        transportSecond = getNodeFromId(ids[1], game);
                        if (transportSecond instanceof Receiver) {
                            throw new FileFormatException();
                        }
                        transportSecond.setOutput(transportFirst.getPath());
                    }
                    if (line.length() >= 3 && line.charAt(2) == ',') {
                        throw new FileFormatException();
                    }
                    if (line.length() >= 4 && line.charAt(3) == ',') {
                        throw new FileFormatException();
                    }
                }
                line = buffer.readLine();
            }
        } catch (UnsupportedActionException e) {
            throw new FileFormatException();
        }

    }

    /**
     * A helper method to check whether all paths are valid, i.e. the head of the path is a Producer
     * and the tail of the path is a Receiver. As well, check that Producers do not have a previous
     * path, and Receivers do not have a following path.
     *
     * @param numOfPaths the number of paths in the game
     * @param game the GameGrid being implemented
     * @throws FileFormatException if a path's head isn't a Producer or tail isn't a Receiver
     */
    private static void checkValidPaths(int numOfPaths, GameGrid game)
            throws FileFormatException {
        Path currentPath;
        for (int i = 1; i < numOfPaths; i++) {
            currentPath = getNodeFromId(Integer.toString(i), game).getPath();
            if (!(currentPath.head().getNode() instanceof Producer)
                    || !(currentPath.tail().getNode() instanceof Receiver)) {
                throw new FileFormatException();
            }
            if (currentPath.getNode() instanceof Producer && currentPath.getPrevious() != null) {
                throw new FileFormatException();
            }
            if (currentPath.getNode() instanceof Receiver && currentPath.getNext() != null) {
                throw new FileFormatException();
            }
            if (currentPath.getNode() instanceof Belt
                    && (currentPath.getNext() == null || currentPath.getPrevious() == null)) {
                throw new FileFormatException();
            }
        }
    }

    /**
     * A helper method to construct the grid from the text file
     *
     * @param range the range of the grid
     * @param buffer the BufferedReader for the file
     * @param game the GameGrid being implemented
     * @param itemsReceiver the list of Items for the Receiver
     * @param itemsProducer the list of Items for the Producer
     * @return an integer of the number of paths created plus one
     * @throws IOException if there is an error from the reader
     * @throws FileFormatException if there is an error with the file formatting
     */
    private static int constructGrid(int range, BufferedReader buffer,
                           GameGrid game, Item[] itemsReceiver,
                                     Item[] itemsProducer) throws IOException, FileFormatException {
        int id = 1;
        int receiverCount = 0;
        int producerCount = 0;
        int maxCells;
        int cellNumber;
        Coordinate coord;
        String hexLine;
        for (int i = -range; i <= range; i++) {
            hexLine = buffer.readLine();
            maxCells = 2 * range + 1 - Math.abs(i);
            cellNumber = 0;
            String[] splitLine = hexLine.split(" ");
            //Starting from origin, determine the starting coordinate for
            //the hexagon on each row
            coord = determineStartCoordinate(i, range);
            for (String hex : splitLine) {
                //If too many cells, throw exception
                if (cellNumber >= maxCells) {
                    throw new FileFormatException();
                }
                //If Receiver, Producer or Belt, assign integer and items
                if (hex.equals("r")) {
                    game.setCoordinate(coord, new Receiver(id, itemsReceiver[receiverCount]));
                    id++;
                    receiverCount++;
                    coord = coord.getRight();
                    cellNumber++;
                } else if (hex.equals("p")) {
                    game.setCoordinate(coord, new Producer(id, itemsProducer[producerCount]));
                    id++;
                    producerCount++;
                    coord = coord.getRight();
                    cellNumber++;
                } else if (hex.equals("b")) {
                    game.setCoordinate(coord, new Belt(id));
                    id++;
                    coord = coord.getRight();
                    cellNumber++;
                } else if (hex.equals("o")) {
                    game.setCoordinate(coord, () -> "o");
                    coord = coord.getRight();
                    cellNumber++;
                } else if (hex.equals("w")) {
                    game.setCoordinate(coord, () -> "w");
                    coord = coord.getRight();
                    cellNumber++;
                } else if (!(hex.equals(" ") || hex.equals(""))) {
                    throw new FileFormatException();
                }
            }
        }
        return id;
    }
}
