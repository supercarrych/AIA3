import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;


import java.util.*;

public class Agent {

    int NBCLAUSES = 1000; // number of clauses
    int MAXVAR = 1000; //max number of variables
    // instance of Game class
    private Game game;
    // represents the agent's board view which is different from the game's board view
    private char[][] agentBoard;
    // knowledge base
    // store all cells
    private ArrayList<Cell> allCells;
    // list store all the unProbed cells of the board
    public ArrayList<Cell> unProbedCells;
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
     * Two starting clues are given to the agent:
     * the agent can safely probe the cell at the top left-hand corner
     * and the cell in the centre of the board at the start of the game
     */
    public void probeCells() {
        System.out.println("Probing cells");
        Cell cell = findCell(0, 0, allCells);
        probeCell(cell);
        cell = findCell(boardLength / 2, boardLength / 2, allCells);
        probeCell(cell);
        System.out.println("Agent board view at t1");
        A3main.printBoard(agentBoard);
    }

    /**
     * Method which uncovers cell passed as a parameter. It gets the information about the Cell at that position from
     * the game instance. It updates the lists and prints out the appropriate message.
     */
    public void probeCell(Cell cell) {
        Cell myCell = findCell(cell.getX(), cell.getY(), allCells);
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
            agentBoard[cell.getY()][cell.getX()] = '-';
            System.out.println("tornado " + cell);
        }
        // if the value is 0, increment free neighbours. Tells program that there are free neighbours to be probed
        else if (cell.getValue() == '0') {
            cellsWithFreeNeighbours++;
            System.out.println("probe " + cell);
        } else {
            System.out.println("probe " + cell);
        }
        //System.out.println();
    }

    /**
     * Method which returns the Cell object from the input Cells list with coordinates passed as parameters
     */
    public Cell findCell(int x, int y, ArrayList<Cell> cellArrayList) {
        for (Cell cell : cellArrayList) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
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
        //free neighbours that clue is 0
        freeNeighbours();
    }

/*-------sps--------*/
    /**
     * Method which picks a cell based on the single point strategy
     * scan all cells one by one
     * for each cell that is covered check its adjacent neighbours
     */
    public void SPSMove() {
        //free neighbours that clue is 0
        freeNeighbours();
        String action = "";
        // scan all cells one by one
        Cell currentCell = unProbedCells.get(0);
        assert currentCell != null;
        // for each cell that is covered check its adjacent neighbours
        action = checkAFNorAMN(currentCell);
        if (action.equals("AFN")) {
            System.out.println("AFN found, probing");
            probeCell(currentCell);
        } else if (action.equals("AMN")) {
            System.out.println("AMN found, marking");
            markCell(currentCell);
        }else {
            //do noting
            unProbedCells.remove(currentCell);
            unProbedCells.add(currentCell);
        }

    }

    /**
     * checks its adjacent neighbours whether the Cell is in an AFN situation or AMN situation.
     *
     * @return String AFN or AMN
     */
    private String checkAFNorAMN(Cell cell) {
        ArrayList<Cell> adjacentCells = getNeighbours(cell, allCells);
        System.out.println(adjacentCells);
        for (Cell adjacentCell : adjacentCells) {
            System.out.println("check" + adjacentCell);
            //only if they are uncovered and not marked dangers
            if (adjacentCell.getValue() != '?' && adjacentCell.getValue() != '*') {
                // AFN situation is true if the number of dangers already marked cells around cell equals clue
                if (neighbouringDangers(adjacentCell) == Character.getNumericValue(adjacentCell.getValue())) {
                    return "AFN";
                } else if (neighbouringUnknowns(adjacentCell) == (Character.getNumericValue(adjacentCell.getValue() - neighbouringDangers(adjacentCell)))) {
                    return "AMN";
                }
            }
        }
        return "nothing";
    }


    /**
     * Method which returns the number of flagged cells around the cell passed as a parameter
     */
    public int neighbouringDangers(Cell cell) {
        int nDangers = 0;
        ArrayList<Cell> adjacentCells = getNeighbours(cell, allCells);
        for (Cell adjacentCell : adjacentCells) {
            if (adjacentCell.getValue() == '*') {
                nDangers++;
            }
        }
        return nDangers;
    }

    /**
     * Method which returns the number of unexamined cells around the cell passed as a parameter
     */
    public int neighbouringUnknowns(Cell cell) {
        int nUnknowns = 0;
        ArrayList<Cell> adjacentCells = getNeighbours(cell, allCells);
        for (Cell adjacentCell : adjacentCells) {
            if (adjacentCell.getValue() == '?') {
                nUnknowns++;
            }
        }
        return nUnknowns;
    }

    /**
     * Method which marks the Cell objects passed as a parameter as a danger cell i.e. flag it. Used by the SPX agent.
     *
     * @param cell to be marked as a 'danger'.
     */
    public void markCell(Cell cell) {
        Cell myCell = findCell(cell.getX(), cell.getY(), allCells);
        cell.setValue('*');
        myCell.setValue('*');
        tornadoCells.add(cell);
        probedCells.add(cell);
        unProbedCells.remove(cell);
        agentBoard[cell.getY()][cell.getX()] = cell.getValue();
        System.out.println("mark " + cell.toString());
        System.out.println();
    }


    public char[][] getAgentBoard() {
        return agentBoard;
    }

    public void uncoverAllClearNeighbours() {
        // list holding cells that are adjacent to a cell with clue 0. These cells can be probed
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        for (Cell cell : probedCells) {
            if (cell.getValue() == '0') {
                adjacentCells.addAll(getNeighbours(cell,  unProbedCells));
            }
        }
        // probe all the cells in the probe cell list. Done outside the loop to prevent ConcurrentModificationException
        for (Cell adjacentCell : adjacentCells) {
            System.out.println("Uncovering free neighbour");
            probeCell(adjacentCell);
        }
    }

    /**
     * Method which returns all the neighbouring cells of a cell passed as a parameter
     *
     * @param cell  which cell need to find neighbours
     * @param cells find from this arraylist
     * @return an ArrayList containing the neighbours of the parameter Cell object.
     */
    public ArrayList<Cell> getNeighbours(Cell cell, ArrayList<Cell> cells) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        // upper left cell
        if (cell.getX() > 0 && cell.getY() > 0) {
            Cell adjacentCell = findCell(cell.getX() - 1, cell.getY() - 1, cells);
            if (adjacentCell != null) {
                adjacentCells.add(adjacentCell);
            }
        }


        // left cell
        if (cell.getX() > 0) {
            Cell adjacentCell = findCell(cell.getX() - 1, cell.getY(), cells);
            if (adjacentCell != null) {
                adjacentCells.add(adjacentCell);
            }
        }
        // upper cell
        if (cell.getY() > 0) {
            Cell adjacentCell = findCell(cell.getX(), cell.getY() - 1, cells);
            if (adjacentCell != null) {
                adjacentCells.add(adjacentCell);
            }
        }
        // lower right cell
        if (cell.getX() < boardLength - 1 && cell.getY() < boardLength - 1) {
            Cell adjacentCell = findCell(cell.getX() + 1, cell.getY() + 1, cells);
            if (adjacentCell != null) {
                adjacentCells.add(adjacentCell);
            }
        }


        // right cell
        if (cell.getX() < boardLength - 1) {
            Cell adjacentCell = findCell(cell.getX() + 1, cell.getY(), cells);
            if (adjacentCell != null) {
                adjacentCells.add(adjacentCell);
            }
        }
        // lower cell
        if (cell.getY() < boardLength - 1) {
            Cell adjacentCell = findCell(cell.getX(), cell.getY() + 1, cells);
            if (adjacentCell != null) {
                adjacentCells.add(adjacentCell);
            }
        }

        return adjacentCells;
    }

    /**
     * If the cell contains a value 0 meaning that no adjacent cells contain tornadoes,
     * all the neighbouring cells will also be uncovered.
     */
    public void freeNeighbours() {
        while (cellsWithFreeNeighbours != 0 && !game.isGameWon()) {
            uncoverAllClearNeighbours();
            cellsWithFreeNeighbours--;
        }
    }

    /**
     * Method which returns whether a Cell object has been examined before
     */
    public boolean hasBeenProbed(Cell adjacentCell) {
        for (Cell cell : probedCells) {
            if (cell.getX() == adjacentCell.getX() && cell.getY() == adjacentCell.getY()) {
                return true;
            }
        }
        return false;
    }

    /* --------- SAT -------- */
    /**
     * Method which carries out the SAT move strategy. It calls the methods required to turn the knowledge base into
     * a logical formula string, then calls the methods that encode this into CNF DIMACS and then uses the SAT4J solver
     * to assess whether a Cell is safe to be probed.
     */
    public void makeSATMove() {
        freeNeighbours();
        ISolver solver = SolverFactory.newDefault();
        FormulaFactory f = new FormulaFactory();
        PropositionalParser p = new PropositionalParser(f);
        Cell myCell = null;
        String action = "R";
        // Create the KB from the probed Cells
        String kbString = convertKBU(uncoveredCells);
        System.out.println(kbString);
//        try {
//            // convert the formula to a CNF DIMACS encoding
//            int[][] dimacsClauses = CNFToDIMACS(kbString);
//            // instantiate the solver
//            solver.newVar(MAXVAR);
//            solver.setExpectedNumberOfClauses(NBCLAUSES);
//            for (int j = 0; j < dimacsClauses.length; j++) {
//                VecInt vecInt = new VecInt(dimacsClauses[j]);
//                // add clause to solved
//                solver.addClause(vecInt);
//            }
            // for every unexamined cells check whether the possibility of it containing a tornado is satisfiable.
            // if not then it means that the cell can be probed safely.
//            for (Cell cell : unProbedCells) {
//                String clause = "T" + cell.getX() + cell.getY();
//                if (dimacsGenerator.getLiteralsHashMap().containsKey(clause)) {
//                    int literal = dimacsGenerator.getLiteralsHashMap().get(clause);
//                    int[] literalArray = new int[]{literal};
//                    if (!solver.isSatisfiable(new VecInt(literalArray))) {
//                        myCell = cell;
//                        action = "P";
//                        break;
//                    }
//                }
//            }
//            if (action.equals("P")) {
//                probeCell(myCell);
//            }
//            else if (action.equals("R")) {
//                System.out.println("SAT could not determine, going Random");
//            }
//        } catch (ContradictionException  e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * Method which takes an uncovered Cell object as a parameter, and it evaluates its surroundings in order to construct
     * a logical formula.
     * @param cell
     * @return a String representing a logical formula with information about the parameter's surrounding cells.
     */
    public String createClause(Cell cell) {
        // get all the neighbours of the cell
        ArrayList<Cell> neighbours = getNeighbours(cell,allCells);
        // contains unknown neighbours of parameter cell
        ArrayList<Cell> unknownCells = new ArrayList<>();

        // populate the markedNeighbours and unknownCells lists
        for (Cell currentCell : neighbours) {
          if (currentCell.getValue() == '?') {
                unknownCells.add(currentCell);
            }
        }

        // create the literals of each cell
        ArrayList<String> literals = new ArrayList<>();
        for (Cell unknownCell : unknownCells) {
            literals.add("T" + unknownCell.getX() + unknownCell.getY());
        }


        // number of neighbouring tornado cells
        int nTornadoes = Character.getNumericValue(cell.getValue());
        // number of neighbouring cells that are unknown
        int nUnknowns = unknownCells.size();
        // number of neighbouring cells marked as dangers
        int nMarked = neighbouringDangers(cell);

        // get all the permutations, to be used when adding the negation
        ArrayList<ArrayList<String>> permutedClauses = listPermutations(literals);
        System.out.println("permutedClauses");
        System.out.println(permutedClauses);
        for (int i = 0; i < permutedClauses.size(); i++) {
            ArrayList<String> currentClause = permutedClauses.get(i);
            // nUnknowns - nTornados - nMarked is the number of free/safe cells around cell
            // used to get all possible scenarios
            for (int j = 0; j < nUnknowns - nTornadoes - nMarked; j++) {
                String clause = currentClause.get(j);
                currentClause.remove(clause);
                clause = "~" + clause;
                currentClause.add(0, clause);
            }
        }

        // build the logical formula string
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permutedClauses.size(); i++) {
            ArrayList<String> currentClause = permutedClauses.get(i);
            stringBuilder.append("(");
            for (int j = 0; j < currentClause.size(); j++) {
                String clause = currentClause.get(j);
                stringBuilder.append(clause);
                stringBuilder.append("&");
            }
            // delete trailing &
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");
            stringBuilder.append("|");
        }

        // delete trailing |
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();

    }

    /**
     * Method which creates permutations of strings in a list. Used to generate the encoding of the knowledge base
     * as a string.
     * @param list the list containing the strings to be permuted
     * @return a list of the permutations i.e. a list containing the permutations of the list passed as a parameter
     */
    public ArrayList<ArrayList<String>> listPermutations(ArrayList<String> list) {

        if (list.size() == 0) {
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }

        ArrayList<ArrayList<String>> returnList = new ArrayList<>();

        String firstElement = list.remove(0);

        ArrayList<ArrayList<String>> recursiveReturn = listPermutations(list);
        for (ArrayList<String> li : recursiveReturn) {
            for (int index = 0; index <= li.size(); index++) {
                ArrayList<String> temp = new ArrayList<>(li);
                temp.add(index, firstElement);
                returnList.add(temp);
            }

        }
        return returnList;
    }


    /**
     * Method which taked all uncoveredCells as a parameter, and created a logical formula with all the possibilities
     * of where tornados could possibly be.
     * @param uncoveredCells cells that have been uncovered i.e. probed
     * @return a String representation of the knowledge base.
     */
    public String convertKBU(ArrayList<Cell> uncoveredCells) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < uncoveredCells.size(); i++) {
            Cell cell = uncoveredCells.get(i);
            if (neighbouringUnknowns(cell) > 0) {
                // for each cell, get a single clause
                String clause = createClause(cell);
                if (!Objects.equals(clause, "")) {
                    stringBuilder.append("(");
                    stringBuilder.append(clause);
                    stringBuilder.append(")");
                    stringBuilder.append("&");
                }
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString();
    }




    /**
     * takes a String representation of a CNF formula and returns a 2D int array in DIMACS format
     */
    private int[][] CNFToDIMACS(String cnf) {
        // Split the CNF string into clauses
        String[] clauses = cnf.split("\\s*&\\s*");

        // Determine the maximum variable ID
        int maxVar = 0;
        for (String clause : clauses) {
            String[] literals = clause.split("\\s*\\|\\s*");
            for (String literal : literals) {
                int var = Math.abs(Integer.parseInt(literal));
                if (var > maxVar) {
                    maxVar = var;
                }
            }
        }

        // Initialize the DIMACS array
        int[][] dimacs = new int[clauses.length][];

        // Convert each clause to DIMACS format
        for (int i = 0; i < clauses.length; i++) {
            String[] literals = clauses[i].split("\\s*\\|\\s*");
            int[] clause = new int[literals.length];
            for (int j = 0; j < literals.length; j++) {
                int var = Integer.parseInt(literals[j]);
                if (var < 0) {
                    clause[j] = -(Math.abs(var) + maxVar);
                } else {
                    clause[j] = var;
                }
            }
            dimacs[i] = clause;
        }

        return dimacs;
        }



}
