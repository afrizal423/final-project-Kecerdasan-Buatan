/**
 * @author SU Sheng Loong 42397997
 * @author TEE Lip Jian 42430942
 */
package search;

/**
 * Class to combine an action with a state.
 */
public final class ActionStatePair {
    private final Action action;
    private final State state;
   
    /**
     * Construct an Action State pair, 
     * intended meaning: this Action takes you to State
     * @param a the action to carry out
     * @param s the state travelled to
     */
    public ActionStatePair(Action a, State s) {
        action=a;
        state=s;
    }
    
    /**
     * @return Returns the action.
     */
    public Action getAction() {
        return action;
    }
    
    /**
     * @return Returns the state.
     */
    public State getState() {
        return state;
    }
    
    public boolean equals(Object obj){
    	return this.getState().equals(((ActionStatePair)obj).getState());
    }
    
}
