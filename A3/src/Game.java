import java.util.ArrayList;

public class Game {
    private World world;
    private char[][] board;
    // list holding all the Cell objects
    private ArrayList<Cell> allCells;
    // list holding the cells which have not been uncovered
    private ArrayList<Cell> coveredCells;
    // holds whether game is over
    private boolean gameOver;
    // holds whether game has been won
    private boolean gameWon;

    /**
     * Class constructor
     * @param world contains the game map.
     */
    public Game(World world) {
        this.world = world;
        // this will hold the actual world view
        this.board = world.map;
        this.gameOver = false;
        this.gameWon = false;
        this.allCells = new ArrayList<>();
        this.coveredCells = new ArrayList<>();
        initializeCells();
    }

    /**
     * Method which populates the allCells and coveredCells array. In the beginning all the cells are covered.
     * The actual view of the Obscured Sweeper world
     */
    private void initializeCells() {
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board.length ; j++) {
                Cell cell = new Cell(j, i, board[i][j]);
                allCells.add(cell);
                coveredCells.add(cell);
            }
        }
    }

    /**
     * Method which returns information about the cell probed. Provides ability to 'tell agent about perceptions'
     * @return Cell object uncovered containing details about coordinates and value
     */
    public Cell uncoverCell(int x, int y) {
        for (int i = 0; i < allCells.size(); i++) {
            if (allCells.get(i).getX() == x && allCells.get(i).getY() == y) {
                Cell cell = allCells.get(i);
                // remove cell from list holding the covered cells
                coveredCells.remove(cell);
                // if the cell uncovered is a tornado, game is over
                if (cell.getValue() == 't') {
                    gameOver = true;
                }
                // if checkGameWon returns true
                else if (checkGameWon()) {
                    gameOver = true;
                    gameWon = true;
                }
                return cell;
            }
        }
        // cell has already been uncovered, return null
        return null;
    }

    /**
     * Method which checks whether all the remaining covered cells are tornadoes.
     * Provides ability to 'determine if game is over'
     * @return true if all the remaining covered cells are tornadoes
     */
    public boolean checkGameWon() {
        if (coveredCells.size() == 0) {
            return true;
        }
        else {
            for (int i = 0; i < coveredCells.size(); i++) {
                Cell cell = coveredCells.get(i);
                // If all but tornadoes cells are probed without a game over, the agent wins the game.
                if (board[cell.getY()][cell.getX()] != 't') {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Simple getter
     * @return board
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Simplge getter
     * @return boolean value of whether the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }


    /**
     * Simple getter
     * @return boolean value of whether the game has been won
     */
    public boolean isGameWon() {
        return gameWon;
    }
}
