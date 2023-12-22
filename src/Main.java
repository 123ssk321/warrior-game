
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String[] args) {
        try(BufferedReader in =  new BufferedReader(new InputStreamReader(System.in))){
            String[] metadata = in.readLine().trim().split(" ");
            int numChallenges = Integer.parseInt(metadata[0]);
            int numDecisions = Integer.parseInt(metadata[1]);

            AwesomeWarriorGame awesomeWarriorGame = new AwesomeWarriorGame(numChallenges, numDecisions);

            String[] edge;
            int source;
            String paysGets;
            int weight;
            int destination;
            for(int i = 0; i < numDecisions; i++){
                edge = in.readLine().trim().split(" ");
                source = Integer.parseInt(edge[0]);
                paysGets = edge[1];
                weight = Integer.parseInt(edge[2]);
                destination = Integer.parseInt(edge[3]);

                awesomeWarriorGame.addEdge(source, destination, paysGets, weight);
            }

            metadata = in.readLine().trim().split(" ");
            int initialChallenge = Integer.parseInt(metadata[0]);
            int finalChallenge = Integer.parseInt(metadata[1]);
            int initialEnergy = Integer.parseInt(metadata[2]);

            int finalEnergy = awesomeWarriorGame.bellmanFord(initialChallenge, finalChallenge, initialEnergy);
            String result =  finalEnergy >= initialEnergy? "Full of energy":String.valueOf(finalEnergy);
            System.out.println(result);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
