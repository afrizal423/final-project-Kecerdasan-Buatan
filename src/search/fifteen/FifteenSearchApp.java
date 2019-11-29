package search.fifteen;

import java.util.ArrayList;
import java.lang.Math;

import search.Action;
import search.Node;

public class FifteenSearchApp {

    /**
     * Test program for search procedures
     * @param args none interpreted as yet
     */
    public static void main(String[] args) {
        // create an initial fifteen puzzle state by first generating the goal config
        //PuzzleState myState=new PuzzleState();

        // Create a random puzzle and memorise the puzzle state.
        PuzzleState myState= randomPuzzle(10);
        PuzzleState myState2 = new PuzzleState(myState);

        // or "shuffle" the tiles around manually a little bit...
        //myState=new PuzzleState(myState, PuzzleState.MOVE_LEFT);
        //myState=new PuzzleState(myState, PuzzleState.MOVE_UP);
        //myState=new PuzzleState(myState, PuzzleState.MOVE_LEFT);
        //myState=new PuzzleState(myState, PuzzleState.MOVE_UP);
        //myState=new PuzzleState(myState, PuzzleState.MOVE_UP);

        // now perform the search from the "shuffled" initial state (fringe is empty), and
        // pull out the actions that were used to generate this goal state from the initial state
        Action[] actions1G = solveH1G(new PuzzleState(myState));
        Action[] actions1A = solveH1A(new PuzzleState(myState));
        Action[] actions2G = solveH2G(new PuzzleState(myState));
        Action[] actions2A = solveH2A(new PuzzleState(myState));

        // List the initial state and results of actions performed.
        System.out.println("Initial state:");
        System.out.println(myState2.toString());

        System.out.println("Solution via H1 with Greedy:-------------");
        for (int i=0; i<actions1G.length; i++) {
            System.out.println((i+1)+": "+actions1G[actions1G.length-1-i]);
            PuzzleState.performAction(myState2,actions1G[actions1G.length-1-i]);
            System.out.println(myState2.toString());
        }

        System.out.println("Solution via H1 with A*:-------------");
        myState2 = new PuzzleState(myState);
        for (int i=0; i<actions1A.length; i++) {
            System.out.println((i+1)+": "+actions1A[actions1A.length-1-i]);
            PuzzleState.performAction(myState2,actions1A[actions1A.length-1-i]);
            System.out.println(myState2.toString());
        }

        System.out.println("Solution via H2 with Greedy:-------------");
        myState2 = new PuzzleState(myState);
        for (int i=0; i<actions2G.length; i++) {
            System.out.println((i+1)+": "+actions2G[actions2G.length-1-i]);
            PuzzleState.performAction(myState2,actions2G[actions2G.length-1-i]);
            System.out.println(myState2.toString());
        }

        System.out.println("Solution via H2 with A*:-------------");
        myState2 = new PuzzleState(myState);
        for (int i=0; i<actions2A.length; i++) {
            System.out.println((i+1)+": "+actions2A[actions2A.length-1-i]);
            PuzzleState.performAction(myState2,actions2A[actions2A.length-1-i]);
            System.out.println(myState2.toString());
        }
        
    }

    /**
     * Example solve
     * @param state initial puzzle state
     */
    public static Action[] solveTree(PuzzleState state){
        // now perform the search from the "shuffled" initial state (fringe is empty)
        Node goal=Node.breadthFirstSearch(state, new ArrayList());
        Action[] actions=goal.getActions();
        
        return actions;
    }

    /**
     * Example solve
     * You must change this function to solve the problem with your own 
     * Greedy implementation with heuristic function 1.
     * @param state initial puzzle state
     */
    public static Action[] solveH1G(PuzzleState state){
        // now perform the search from the "shuffled" initial state (fringe is empty)
        //Node goal = Node.myH1G(state, new ArrayList());
        Node goal=Node.breadthFirstSearch(state, new ArrayList());
        Action[] actions=goal.getActions();
        
        return actions;
    }

    /**
     * Example solve
     * You must change this function to solve the problem with your own 
     * A* implementation with heuristic function 1.
     * @param state initial puzzle state
     */
    public static Action[] solveH1A(PuzzleState state){
        // now perform the search from the "shuffled" initial state (fringe is empty)
        //Node goal = Node.myH1A(state, new ArrayList());
        Node goal=Node.breadthFirstSearch(state, new ArrayList());
        Action[] actions=goal.getActions();
        
        return actions;
    }

     /**
     * Example solve
     * You must change this function to solve the problem with your own 
     * Greedy implementation with heuristic function 2.
     * @param state initial puzzle state
     */
    public static Action[] solveH2G(PuzzleState state){
        // now perform the search from the "shuffled" initial state (fringe is empty)
        //Node goal = Node.myH2G(state, new ArrayList());
        Node goal=Node.breadthFirstSearch(state, new ArrayList());
        Action[] actions=goal.getActions();
        
        return actions;
    }

     /**
     * Example solve
     * You must change this function to solve the problem with your own 
     * A* implementation with heuristic function 2.
     * @param state initial puzzle state
     */
    public static Action[] solveH2A(PuzzleState state){
        // now perform the search from the "shuffled" initial state (fringe is empty)
        //Node goal = Node.myH2A(state, new ArrayList());
        Node goal=Node.breadthFirstSearch(state, new ArrayList());
        Action[] actions=goal.getActions();
        
        return actions;
    }

    /**
     * Generate a solvable random puzzle.
     * @param maxShuffles the number of shuffles to be performed
     */
    public static PuzzleState randomPuzzle(int maxShuffles) {
        PuzzleState myState=new PuzzleState();
        int totalMoves = 0;
        while(totalMoves < maxShuffles){
            double r = Math.random();
            try {
                if(r < 0.25){
                    PuzzleState.performAction(myState, PuzzleState.MOVE_LEFT);
                    //System.out.println("left");
                } else if (r < 0.5) {
                    PuzzleState.performAction(myState, PuzzleState.MOVE_RIGHT);
                    //System.out.println("right");
                } else if (r < 0.75) {
                    PuzzleState.performAction(myState, PuzzleState.MOVE_UP);
                    //System.out.println("up");
                } else {
                    PuzzleState.performAction(myState, PuzzleState.MOVE_DOWN);
                    //System.out.println("down");
                }
                totalMoves++;
            }
            catch (RuntimeException e){
                ; // illegal move
            }
        }
        return myState;
    }

    /**
     * Check if the actions solve the given puzzle.
     * @param myState a problem
     * @param actions an array of Action
     */
    public static boolean checkActions(PuzzleState myState, Action[] actions) {
        // create an initial fiteen puzzle state by first generating the goal config
        for (int i=0; i<actions.length; i++) {
            try {
                PuzzleState.performAction(myState, actions[i]);
            }
            catch (RuntimeException e){
                return false; // illegal move
            }
        }

        // check whether myState is the goal state.
        return myState.goal();
    }
}
