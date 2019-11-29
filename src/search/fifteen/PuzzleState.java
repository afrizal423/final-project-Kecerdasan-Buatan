package search.fifteen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import search.Action;
import search.ActionStatePair;
import search.State;

/**
 * The State that implements the Twentyfour-Puzzle.
 */
public final class PuzzleState implements State {

    /** The board configuration of this state */
    public final int[][] tiles;
    /** The action "to slide the empty space to the left" */
    public static Action MOVE_LEFT  = new Action("LEFT");
    /** The action "to slide the empty space to the right" */
    public static Action MOVE_RIGHT = new Action("RIGHT");
    /** The action "to slide the empty space upwards" */
    public static Action MOVE_UP    = new Action("UP");
    /** The action "to slide the empty space downwards" */
    public static Action MOVE_DOWN  = new Action("DOWN");
    /** The order in which the actions are tested */
    private static Action[] actionSequence={MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN};
    
    /**
     * The constructor initialises the board to the goal configuration.
     * From there all sequences of legal actions will render a puzzle that can be solved. 
     */
    public PuzzleState() {
        this.tiles=new int[4][4];
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                this.tiles[i][j]=i*4+j+1;
            }
        }
        tiles[3][3]=0; // empty
    }
    
    /**
     * A new state is created but the board configuration of the supplied state is copied.
     * @param state the state to copy
     */
    public PuzzleState(PuzzleState state) {
        this.tiles=new int[5][5];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                this.tiles[i][j]=state.tiles[i][j];
            }
        }
    }

    /**
     * A new state is created but the board configuration of the supplied state is copied.
     * @param state the 4x4 representation of state to copy
     */
    public PuzzleState(int[][] state) {
        this.tiles=new int[4][4];
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                this.tiles[i][j]=state[i][j];
            }
        }
    }

    /**
     * Create a new state by looking at another state and performing an action.
     * @param origin the original state
     * @param action the action which is taken
     * @throws RuntimeException if the action is invalid
     */
    public PuzzleState(PuzzleState origin, Action action) {
        this(origin);
        performAction(this,action);
    }
    
    public static void performAction(PuzzleState origin, Action action) {
        int rowEmpty=0, colEmpty=0;
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (origin.tiles[i][j]==0) {
                    rowEmpty=i;
                    colEmpty=j;
                    break;
                }
            }
        }
        // Check which action is taken, check if it is "valid", if so carry out the action (change the board)
        if (action==MOVE_UP && rowEmpty!=0) { 
            origin.tiles[rowEmpty][colEmpty]=origin.tiles[rowEmpty-1][colEmpty]; // fill empty spot with moved tile
            origin.tiles[rowEmpty-1][colEmpty]=0; // new empty spot
        } else if (action==MOVE_DOWN && rowEmpty!=3) {
            origin.tiles[rowEmpty][colEmpty]=origin.tiles[rowEmpty+1][colEmpty];
            origin.tiles[rowEmpty+1][colEmpty]=0;
        } else if (action==MOVE_LEFT && colEmpty!=0) {
            origin.tiles[rowEmpty][colEmpty]=origin.tiles[rowEmpty][colEmpty-1];
            origin.tiles[rowEmpty][colEmpty-1]=0;
        } else if (action==MOVE_RIGHT && colEmpty!=3) {
            origin.tiles[rowEmpty][colEmpty]=origin.tiles[rowEmpty][colEmpty+1];
            origin.tiles[rowEmpty][colEmpty+1]=0;
        } else {
            throw new RuntimeException("Illegal move"); // illegal move
        }
    }
    
    /**
     * Check if this state is a goal state:
     * |01 02 03 04|
     * |05 06 07 08|
     * |09 10 11 12|
     * |13 14 15 00|
     * @return true if the state is a goal, false otherwise
     */
    public boolean goal() {
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (tiles[i][j]!=i*4+j+1) {
                    if (i==3 && j==3)
                        return true;
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The successor function that generates all valid ActionStatePairs from the current state.
     * @return an array of all valid Action State pairs
     */
    public ActionStatePair[] successor() {
        //ArrayList list=new ArrayList<ActionStatePair>();
        ArrayList list=new ArrayList();
        for (int a=0; a<actionSequence.length; a++) {
            try {
                State state=new PuzzleState(this, actionSequence[a]);
                list.add(new ActionStatePair(actionSequence[a], state));
            } catch (RuntimeException e) {
                ; // illegal move
            } 
        }
        ActionStatePair[] pairs=new ActionStatePair[list.size()];
        Iterator iter=list.iterator();
        for (int i=0; iter.hasNext(); i++) {
            pairs[i]=(ActionStatePair)iter.next();
        }
        return pairs;
    }
    
    /**
     * Determine the cost of taking the specified move from this state.
     * @return the path cost
     */
    public double pathcost(Action action) {
        return 1;
    }
    
    /**
     * An example of an admissable heuristic function (the least number of tiles that need to be moved)
     * @param one State to be compared
     * @param two State to be compared
     * @return the distance between the two specified states
     */ 
    public static int distance(int[][] one, int[][] two) {
        int dist=0;
        for (int r1=0; r1<one.length; r1++) {
            for (int c1=0; c1<one[r1].length; c1++) {
                if (one[r1][c1]!=two[r1][c1])
                    dist++;
            }
        }
        return dist;
    }

    /**
     * This method is called if states are checked for equality on basis of the tile configuration 
     * (e.g. if checking for previous instances in the queue)
     * @param obj the object to compare to
     * @return true if the states are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass())) {
            PuzzleState state=(PuzzleState)obj;
            for (int r=0; r<tiles.length; r++) {    
                for (int c=0; c<tiles[r].length; c++) {
                    if (state.tiles[r][c]!=tiles[r][c])
                        return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * A printable string of the state - displays the tile configuration using newlines.
     * @return a string displaying the tile configuration of the state
     */
    public String toString() {
        DecimalFormat nf=new DecimalFormat("00");
        StringBuffer sb=new StringBuffer();
        for (int r=0; r<tiles.length; r++) {
            for (int c=0; c<tiles[r].length; c++) {
                sb.append(" "+nf.format(tiles[r][c]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
