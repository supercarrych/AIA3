import java.util.Objects;

//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 github taken
public class A3main {

    public static void main(String[] args) {

        boolean verbose = args.length > 2 && args[2].equals("verbose"); //prints the formulas for SAT if true
        //prints the formulas for SAT if true

        // read input from command line
        // Agent type
        System.out.println("-------------------------------------------\n");
        System.out.println("Agent " + args[0] + " plays " + args[1] + "\n");

        // World

        World world = World.valueOf(args[1]);

        char[][] p = world.map;
        printBoard(p);
        System.out.println("Start!");
        Game game = new Game(world);
        Agent agent = new Agent(game);
        //Two starting clues are given to the agent:
        if (!Objects.equals(args[0], "P1")) {
            agent.probeCells();
        }
        int index = 0;
        while (!game.isGameOver()) {
            if (verbose) {
                printBoard(agent.getAgentBoard());
            }
            switch (args[0]) {
                case "P1" -> agent.orderMove();
                case "P2" -> agent.SPSMove();
                case "P3" -> agent.makeDNFMove();
                case "P4" -> agent.makeCNFMove();
                case "P5" ->
                    //TODO: Part 5
                        System.out.println(2);
            }
            //Agent not terminated
            if (!game.isGameOver() && !game.isGameWon()) {
                index++;
            }
            if (index == 100) {
                break;
            }
        }
        if (agent.unProbedCells.size() != 0 && !args[0].equals("P1")) {
            if (game.isGameWon()) {
                int length = agent.unProbedCells.size();
                for (int i = 0; i < length; i++) {
                    if (args[0].equals("P3")) {
                        agent.makeDNFMove();
                    } else {
                        agent.makeCNFMove();
                    }

                }
            }
        }
        System.out.println("Final map");
        printBoard(agent.getAgentBoard());

        if (game.isGameWon()) {
            System.out.println("\nResult: Agent alive: all solved\n");
        } else if (game.isGameOver()) {
            System.out.println("\nResult: Agent dead: found mine\n");
        } else {
            System.out.println("\nResult: Agent not terminated\n");
        }
    }


    //prints the board in the required format - PLEASE DO NOT MODIFY
    public static void printBoard(char[][] board) {
        System.out.println();
        // first line
        for (int l = 0; l < board.length + 5; l++) {
            System.out.print(" ");// shift to start
        }
        for (int j = 0; j < board[0].length; j++) {
            System.out.print(j);// x indexes
            if (j < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
        // second line
        for (int l = 0; l < board.length + 3; l++) {
            System.out.print(" ");
        }
        for (int j = 0; j < board[0].length; j++) {
            System.out.print(" -");// separator
        }
        System.out.println();
        // the board
        for (int i = 0; i < board.length; i++) {
            for (int l = i; l < board.length - 1; l++) {
                System.out.print(" ");// fill with left-hand spaces
            }
            if (i < 10) {
                System.out.print(" ");
            }

            System.out.print(i + "/ ");// index+separator
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");// value in the board
            }
            System.out.println();
        }
        System.out.println();
    }

}
