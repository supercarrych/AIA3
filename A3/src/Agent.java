import java.util.ArrayList;

public class Agent {
    // instance of Game class
    private Game game;
    // represents the agent's board view which is different from the game's board view
    private char[][] agentBoard;
    // knowledge base
    // store all cells
    private ArrayList<Cell> allCells;
    // list store all the unProbed cells of the board
    private ArrayList<Cell> unProbedCells;
    // list store all the probe cells of the board. A cell marked as a danger/tornado is also considered probed
    private ArrayList<Cell> probedCells;
    // list store all the uncovered cells of the board
    private ArrayList<Cell> uncoveredCells;
    // list store all the cells marked as tornado cells
    private ArrayList<Cell> tornadoCells;
    // store the length of the board
    private int boardLength;
    // holds the number of Cells with a value of 0 whose neighbours have not been probed yet.
    private int cellsWithFreeNeighbours;
    // used for parsing the string representation of the knowledge base into a logical formula

    public Agent(Game game) {
        this.game = game;
        // the board length is the only piece of information the agent gets from the game instance
        this.boardLength = this.game.getBoard().length;
        this.allCells = new ArrayList<>();
        this.unProbedCells = new ArrayList<>();
        this.tornadoCells = new ArrayList<>();
        this.agentBoard = new char[boardLength][boardLength];
        this.probedCells = new ArrayList<>();
        this.uncoveredCells = new ArrayList<>();
        this.cellsWithFreeNeighbours = 0;
        // initialize the board
        initializeBoard();
        // initialize the lists
        initializeCells();
        // probe the cells -> top left-hand corner & centre cells
//        probeCells();
    }

    /**
     *
     * Two starting clues are given to the agent:
     * the agent can safely probe the cell at the top left-hand corner
     * and the cell in the centre of the board at the start of the game
     */
    private void probeCells() {
        System.out.println("Probing cells");
        Cell cell = findCell(0, 0);
        probeCell(cell);
        cell = findCell(boardLength / 2, boardLength / 2);
        probeCell(cell);
        System.out.println("Agent board view at t1");
        A3main.printBoard(agentBoard);
    }

    /**
     * Method which uncovers cell passed as a parameter. It gets the information about the Cell at that position from
     * the game instance. It updates the lists and prints out the appropriate message.
     */
    public void probeCell(Cell cell) {
        Cell myCell = findCell(cell.getX(), cell.getY());
        //get cell from game
        Cell perceivedCell = game.uncoverCell(cell.getX(), cell.getY());
        cell.setValue(perceivedCell.getValue());
        //set cell value in allCells
        myCell.setValue(perceivedCell.getValue());
        unProbedCells.remove(cell);
        probedCells.add(cell);
        uncoveredCells.add(cell);
        agentBoard[cell.getY()][cell.getX()] = cell.getValue();
        if (cell.getValue() == 't') {
            System.out.println("tornado " + cell);
        }
        // if the value is 0, increment free neighbours. Tells program that there are free neighbours to be probed
        else if (cell.getValue() == '0') {
            cellsWithFreeNeighbours++;
            System.out.println("probe " + cell);

        }
        else {
            System.out.println("probe " + cell);
        }
        //System.out.println();
    }
    /**
     * Method which returns the Cell object from the allCells list with coordinates passed as parameters
     */
    public Cell findCell(int x, int y) {
        for (int i = 0; i < allCells.size(); i++) {
            if (allCells.get(i).getX() == x && allCells.get(i).getY() == y) {
                return allCells.get(i);
            }
        }
        return null;
    }

    private void initializeCells() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                // left to right on each row; top row to bottom row
                Cell cell = new Cell(j, i, '?');
                unProbedCells.add(cell);
                allCells.add(cell);
            }
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                agentBoard[i][j] = '?';
            }
        }
        A3main.printBoard(agentBoard);
    }

    /**
     * probe non-blocked covered cells in order (left to right on each row; top row to bottom row).
     */
    public void orderMove() {
        Cell cell = unProbedCells.get(0);
        probeCell(cell);
    }

    public char[][] getAgentBoard() {
        return agentBoard;
    }
}
