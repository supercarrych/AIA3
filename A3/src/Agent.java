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
        Cell cell = findCell(0, 0,allCells);
        probeCell(cell);
        cell = findCell(boardLength / 2, boardLength / 2,allCells);
        probeCell(cell);
        System.out.println("Agent board view at t1");
        A3main.printBoard(agentBoard);
    }

    /**
     * Method which uncovers cell passed as a parameter. It gets the information about the Cell at that position from
     * the game instance. It updates the lists and prints out the appropriate message.
     */
    public void probeCell(Cell cell) {
        Cell myCell = findCell(cell.getX(), cell.getY(),allCells);
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
            agentBoard[cell.getY()][cell.getX()]='-';
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
     * Method which returns the Cell object from the input Cells list with coordinates passed as parameters
     */
    public Cell findCell(int x, int y,ArrayList<Cell> cellArrayList) {
        for (int i = 0; i < cellArrayList.size(); i++) {
            if (cellArrayList.get(i).getX() == x && cellArrayList.get(i).getY() == y) {
                return cellArrayList.get(i);
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
                agentBoard[j][i] = '?';
            }
        }
    }

    /**
     * probe non-blocked covered cells in order (left to right on each row; top row to bottom row).
     */
    public void orderMove() {
        Cell cell = unProbedCells.get(0);
        probeCell(cell);
    }

    /**
     * Method which picks a cell based on the single point strategy
     */
    public void SPSMove() {
        Cell myCell = null;
        String action = "order";
        // iterate all unprobed cell to find situations of AFN or AMN
        for (Cell cell : unProbedCells) {
            if (checkAFN(cell)) {
                action = "AFN";
                myCell = cell;
                break;
            } else if (checkAMN(cell)) {
                action = "AMN";
                myCell = cell;
                break;
            }
        }
        // if no unexamined cell is in an AMN or AFN situation, make random move.
        if (action.equals("order")) {
            orderMove();
        } else if (action.equals("AFN")) {
            //System.out.println("AFN found, probing");
            probeCell(myCell);
        } else {
            //System.out.println("AMN found, marking");
            markCell(myCell);
        }
    }

    public char[][] getAgentBoard() {
        return agentBoard;
    }

    public void uncoverAllClearNeighbours() {
        // list holding cells that are adjacent to a cell with clue 0. These cells can be probed
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        for (Cell cell : probedCells) {
            if (cell.getValue() == '0') {
                if (cell.getX() > 0 && cell.getY() > 0) {
                    // check if already probed
                    Cell adjacentCell = findCell(cell.getX() - 1, cell.getY() - 1,unProbedCells);
                    if (adjacentCell != null) {
                        adjacentCells.add(adjacentCell);
                    }
                }
                if (cell.getX() > 0) {
                    Cell adjacentCell = findCell(cell.getX() - 1, cell.getY(),unProbedCells);
                    if (adjacentCell != null) {
                        adjacentCells.add(adjacentCell);
                    }
                }
                if (cell.getY() > 0) {
                    Cell adjacentCell = findCell(cell.getX(), cell.getY() - 1,unProbedCells);
                    if (adjacentCell != null) {
                        adjacentCells.add(adjacentCell);
                    }
                }
                if (cell.getY() < boardLength - 1 && cell.getY() < boardLength - 1) {
                    Cell adjacentCell = findCell(cell.getX() + 1, cell.getY() + 1,unProbedCells);
                    if (adjacentCell != null) {
                        adjacentCells.add(adjacentCell);
                    }
                }
                if (cell.getX() < boardLength - 1) {
                    Cell adjacentCell = findCell(cell.getX() + 1, cell.getY(),unProbedCells);
                    if (adjacentCell != null) {
                        adjacentCells.add(adjacentCell);
                    }
                }
                if (cell.getY() < boardLength - 1) {
                    Cell adjacentCell = findCell(cell.getX(), cell.getY() + 1,unProbedCells);
                    if (adjacentCell != null) {
                        adjacentCells.add(adjacentCell);
                    }
                }

            }
        }
        // probe all the cells in the probe cell list. Done outside the loop to prevent ConcurrentModificationException
        for (Cell adjacentCell : adjacentCells) {
            if (!hasBeenExamined(adjacentCell)) {
                System.out.println("Uncovering free neighbour");
                probeCell(adjacentCell);
            }
        }
    }

    // TODO: 21/03/2023 findFreeCell




}
