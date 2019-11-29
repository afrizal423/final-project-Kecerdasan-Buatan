package search;

/**
 * A standard Action class. All we need in the search package is really a simple "marker"
 */
public class Action {
    // something to print out if required
    private String label=null;

    /**
     * Initialise an Action.
     */
    public Action() {
        label="?";
    }

    /**
     * Initialise an Action.
     * @param label a text string that can be printed out if required
     */
    public Action(String label) {
        this.label=label;
    }

    /**
     * @return a text string describing the action (not necessarily unique)
     */
    public String toString() {
        return label;
    }
}
