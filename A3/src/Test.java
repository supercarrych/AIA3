import java.util.ArrayList;
import java.util.Objects;

/**
 * Class with a main method that is used to compare the performance of the different agent types. The wolrd is set up
 * in the same way as in A2main.java with some small differences to allow a comparison to be made.
 */
public class Test {

    private static Agent agent;
    private static Game game;


    /**
     * Main method of the class
     * @param args can pass the number of times the 30 worlds are played by each agent. Default is 5
     */
    public static void main(String[] args) {
        int orderMoveWon = 0;
        int SPSMoveWon = 0;
        int makeDNFMoveWon = 0;
        int makeCNFMoveWon = 0;
        int orderMoveFail = 0;
        int SPSMoveFail = 0;
        int makeDNFMoveFail = 0;
        int makeCNFMoveFail = 0;

        // Adding the agent types in a list to be iterated later on
        ArrayList<String> agentTypes = new ArrayList<>();
        agentTypes.add("P1");
        agentTypes.add("P2");
        agentTypes.add("P3");
        agentTypes.add("P4");

        //Adding the world names  in a list to be iterated later on
        ArrayList<String> worlds = new ArrayList<>();
        worlds.add("SMALL");
        worlds.add("MEDIUM");
        worlds.add("LARGE");


            for (int i = 0; i < 10; i++) {
                for (String agentType : agentTypes) {
                    for (String world : worlds) {
                        // append the version of the world i.e. S1, S2, S3 etc....
                        world = world + i;
                        System.out.println("World: " + world + " Agent: " + agentType);
                        String result = A3main.play(agentType, world);
                        System.out.println(result);
                        // if game is won, repending on the agent type, increment variable
                        if (result.equals("won")) {
                            if (Objects.equals(agentType, "P1")) {
                                orderMoveWon++;
                            } else if (Objects.equals(agentType, "P2")) {
                                SPSMoveWon++;
                            } else if (Objects.equals(agentType, "P3")) {
                                makeDNFMoveWon++;
                            } else if (Objects.equals(agentType, "P4")) {
                                makeCNFMoveWon++;
                            }
                        }
                    }
                }
            }


        // print out the results
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("\torderMove won: " + orderMoveWon + "/" + (30) + " - " + Math.round(((Integer.valueOf(orderMoveWon).floatValue()/(30)) * 100)) + "% win rate");
        System.out.println("\tSPSMove won: " + SPSMoveWon + "/" + (30) + " - " + Math.round(((Integer.valueOf(SPSMoveWon).floatValue()/(30)) * 100))+ "% win rate");
        System.out.println("\tDNFMove won: " + makeDNFMoveWon + "/" + (30) + " - " + Math.round(((Integer.valueOf(makeDNFMoveWon).floatValue()/(30)) * 100)) + "% win rate");
        System.out.println("\tCNFMove won: " + makeCNFMoveWon + "/" + (30) + " - " + Math.round(((Integer.valueOf(makeCNFMoveWon).floatValue()/(30)) * 100)) + "% win rate");
        System.out.println("------------------------------------------ ");
    }
}