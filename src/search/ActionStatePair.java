package search;

import javax.swing.plaf.nimbus.State;

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


}
