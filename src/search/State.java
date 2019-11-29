package search;

/**
 * The State interface.
 * Implement this to search for goal states.
 */
public interface State {
    
    /**
     * Determines whether the state is a goal state or not
     * @return true if this is a goal state, false otherwise
     */
    public boolean goal();
    
    /**
     * Generates all possible successor actions and respective states that result from this state. 
     * @return an array of successor <action, state> pairs
     */
    public ActionStatePair[] successor();
    
    /**
     * Determines the cost for taking the specified action when in this state. 
     * @param action the action that takes us from this state, must be a legal action
     * @return the cost (non-negative for all legal moves, negative for illegal moves) 
     */
    public double pathcost(Action action);

}
