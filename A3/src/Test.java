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
        int orderMoveWin = 0;
        int SPSMoveWin = 0;
        int makeDNFMoveWin = 0;
        int makeCNFMoveWin = 0;
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
                        // append the version of the world i.e. SMALL0, MEDIUM0, LARGE0 etc....
                        world = world + i;
                        System.out.println("World: " + world + " Agent: " + agentType);
                        String result = A3main.play(agentType, world);
                        System.out.println(result);
                        // if game is won, repending on the agent type, increment variable
                        if (result.equals("won")) {
                            if (Objects.equals(agentType, "P1")) {
                                orderMoveWin++;
                            } else if (Objects.equals(agentType, "P2")) {
                                SPSMoveWin++;
                            } else if (Objects.equals(agentType, "P3")) {
                                makeDNFMoveWin++;
                            } else if (Objects.equals(agentType, "P4")) {
                                makeCNFMoveWin++;
                            }
                        }
                        if (result.equals("T")) {
                            if (Objects.equals(agentType, "P1")) {
                                orderMoveFail++;
                            } else if (Objects.equals(agentType, "P2")) {
                                SPSMoveFail++;
                            } else if (Objects.equals(agentType, "P3")) {
                                makeDNFMoveFail++;
                            } else if (Objects.equals(agentType, "P4")) {
                                makeCNFMoveFail++;
                            }
                        }
                    }
                }
            }


        // print out the results
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("\torderMove win: " + orderMoveWin + "/" + 30 + " - " + Math.round(((Integer.valueOf(orderMoveWin).floatValue()/(30)) * 100)) + "%");
        System.out.println("\tSPSMove win: " + SPSMoveWin + "/" + 30 + " - " + Math.round(((Integer.valueOf(SPSMoveWin).floatValue()/(30)) * 100))+ "%");
        System.out.println("\tDNFMove win: " + makeDNFMoveWin + "/" + 30 + " - " + Math.round(((Integer.valueOf(makeDNFMoveWin).floatValue()/(30)) * 100)) + "%");
        System.out.println("\tCNFMove win: " + makeCNFMoveWin + "/" + 30 + " - " + Math.round(((Integer.valueOf(makeCNFMoveWin).floatValue()/(30)) * 100)) + "%");
        System.out.println("------------------------------------------ ");
        System.out.println("\torderMove no win and no loss: " + orderMoveFail + "/" + 30 + " - " + Math.round(((Integer.valueOf(orderMoveFail).floatValue()/(30)) * 100)) + "%");
        System.out.println("\tSPSMove no win and no loss: " + SPSMoveFail + "/" + 30 + " - " + Math.round(((Integer.valueOf(SPSMoveFail).floatValue()/(30)) * 100))+ "%");
        System.out.println("\tDNFMove no win and no loss: " + makeDNFMoveFail + "/" + 30 + " - " + Math.round(((Integer.valueOf(makeDNFMoveFail).floatValue()/(30)) * 100)) + "%");
        System.out.println("\tCNFMove no win and no loss: " + makeCNFMoveFail + "/" + 30 + " - " + Math.round(((Integer.valueOf(makeCNFMoveFail).floatValue()/(30)) * 100)) + "%");
        System.out.println("------------------------------------------ ");
    }
}