public class Orange {
	/**Sets up the basic functioning of the oranges.
	 *
	 */
    public enum State {
        Fetched(15),
        Peeled(38),
        Squeezed(29),
        Bottled(17),
        Processed(1);

        private static final int finalIndex = State.values().length - 1;

        final int timeToComplete;

        State(int timeToComplete) {
            this.timeToComplete = timeToComplete;
        }

        State getNext() {
            int currIndex = this.ordinal();
            if (currIndex >= finalIndex) {
                throw new IllegalStateException("Already at final state");
            }
            return State.values()[currIndex + 1];
        }
    }

    private State state;

    /**Basic constructor.
     * 
     */
    public Orange() {
        state = State.Fetched;
        doWork();
    }

    /** Generic get method for state.
     * 
     * @return
     */
    public State getState() {
        return state;
    }

    /** Checks to see if the orange still needs processing and processes it if it does.
     * 
     */
    public void runProcess() {
        if (state == State.Processed) {
            throw new IllegalStateException("This orange has already been processed");
        }
        doWork();
        state = state.getNext();
    }

    /** Sleeps the thread for the amount of time necessary to do the work.
     * 
     */
    private void doWork() {
        try {
            Thread.sleep(state.timeToComplete);
        } catch (InterruptedException e) {
            System.err.println("Incomplete orange processing, juice may be bad");
        }
    }
}