
import util.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AwesomeWarriorGame {

    public static final String GETS = "Gets";

    /**
     * A list of edges of the graph made from the decisions the warrior can make
     */
    private final List<Edge> edges;

    /**
     * The number of nodes in the graph given by the number of all possible challenges the warrior can encounter
     */
    private final int numChallenges;


    /**
     * Constructor of the class
     * Constructs an AwesomeWarriorGame that can posteriorly be solved using the Bellman Ford algorithm
     * @param numChallenges - number of nodes of the graph made from the decisions the warrior can make
     * @param numDecisions - number of edges of the graph made from the decisions the warrior can make
     */
    public AwesomeWarriorGame(int numChallenges, int numDecisions){
        edges = new ArrayList<>(numDecisions);
        this.numChallenges = numChallenges;
    }


    /**
     * Add an edge to the graph made by the decisions the warrior can make, inverting the weight signal if paysGets
     * equals Gets
     * @param source - source node of the edge
     * @param destination - destination node of the edge
     * @param paysGets - is Pays or Gets helping to invert the weight if necessary
     * @param weight - weight of the edge
     */
    public void addEdge(int source, int destination, String paysGets, int weight){
        weight = paysGets.equals(GETS) ? -weight : weight;
        edges.add(new Edge(source, destination, weight));
    }

    /**
     * Applies the Bellman Ford algorithm returning the final energy the wizard has in the end of the game
     * @param initialChallenge - initial node where the warrior starts the game
     * @param finalChallenge - final node where the wizard is
     * @param initialEnergy - initial energy of the warrior
     * @return - final energy of the warrior in the end of the game
     */
    public int bellmanFord(int initialChallenge, int finalChallenge, int initialEnergy) {
        int[] lengths = new int[numChallenges];
        Arrays.fill(lengths, Integer.MAX_VALUE);

        boolean[] hasPathToFinalChallenge = new boolean[numChallenges];
        Arrays.fill(hasPathToFinalChallenge, false);

        lengths[initialChallenge] = 0;
        hasPathToFinalChallenge[finalChallenge] = true;

        boolean changes = false;

        for(int i = 1; i < numChallenges; i++){
           changes = updateLengths(lengths, hasPathToFinalChallenge);

           if(!changes)
               break;
       }

       int finalEnergy = initialEnergy-lengths[finalChallenge];

       if(changes && hasCycleInPathToFinalChallenge(lengths, hasPathToFinalChallenge)){
           return initialEnergy+1;
       }else if(lengths[finalChallenge]==Integer.MIN_VALUE || finalEnergy<=0){
           return 0;
       }else{
           return finalEnergy;
       }

    }

    /**
     * Updates the lengths of the paths starting from the initial challenge
     * @param len - array with the lengths of the paths starting from the initial challenge
     * @param hasPathToFinalChallenge - array that stores whether a node has a path to the final challenge or not
     * @return true if there was path whose length was updated
     */
    private boolean updateLengths(int[] len, boolean[] hasPathToFinalChallenge){
        boolean changes = false;
        for (Edge edge: edges) {
            int source = edge.getSource();
            int destination = edge.getDestination();

            if(hasPathToFinalChallenge[destination])
                hasPathToFinalChallenge[source]=true;

            if(len[source] < Integer.MAX_VALUE) {
                int newLength = len[source] + edge.getWeight();
                if(newLength < len[destination]){
                    len[destination] = newLength;
                    changes = true;
                }
            }
        }
        return changes;
    }

    /**
     * Checks if there is a cycle in a path to the final challenge
     * @param len - array with the lengths of the paths starting from the initial challenge
     * @param hasPathToFinalChallenge - array that stores whether a node has a path to the final challenge or not
     * @return true if there is a cycle in a path to the final challenge
     */
    private boolean hasCycleInPathToFinalChallenge(int[] len, boolean[] hasPathToFinalChallenge){
        for (Edge edge: edges) {
            int source = edge.getSource();
            int destination = edge.getDestination();

            if(len[source] < Integer.MAX_VALUE) {
                int newLength = len[source] + edge.getWeight();
                if(newLength < len[destination] && hasPathToFinalChallenge[destination]){
                    return true;
                }
            }
        }
        return false;
    }

}
