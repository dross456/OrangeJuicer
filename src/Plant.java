public class Plant implements Runnable {
    // How long do we want to run the juice processing
    private static final long PROCESSING_TIME = 5 * 1000;
    //Number of workers aside from the plant
    private static final int NUM_WORKERS = 3;
    private static final int ORANGES_PER_BOTTLE = 4;
    private AssemblyLine line = new AssemblyLine();
    private long endTime;
    private int plantID;
    private int provided = 0;
    private int orangesProcessed;
    //Whether or not Workers should continue processing oranges
    public volatile boolean timeToWork = true;
    //The Number of workers who have finished their last orange
    private volatile int clockedOut = 0;
    
    /**Basic constructor requiring it's ID past to it.
     * 
     * @param num
     */
    Plant(int num) {
    	plantID = num;
        orangesProcessed = 0;
            new Thread(this, "Plant[" + num + "]").start();
    }
    
    /**Spawns worker threads.
     * Fetches oranges and puts them on the line for the worker threads.
     * Keeps track of weather it's time to shut down the threads.
     * Prints the results after the work threads has closed.
     * 
     */
	public void run() {
        // Give the plants time to do work
        endTime = System.currentTimeMillis() + PROCESSING_TIME;
		for(int i = 0; i < NUM_WORKERS; i++){
        	this.fetchOrange(new Orange());
            provided++;
            new Worker(this, line, plantID, i);
        }
        
        while (System.currentTimeMillis() < endTime) {
            this.fetchOrange(new Orange());
            provided++;
        }
        timeToWork =false;
        //*
        while(clockedOut<NUM_WORKERS) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
        }//*/
        
        // Summarize the results
        System.out.println("Plant " + plantID + " total provided/processed = " + provided + "/" + this.getProcessedOranges());
        System.out.println("Plant " + plantID + " created " + this.getBottles() +
                           ", wasted " + this.getWaste() + " oranges");
	}

	/** Creates and fetches new oranges putting them on the assembly line.
	 * 
	 * @param o
	 */
    public void fetchOrange(Orange o) {
    	o.runProcess();
    	line.addOrange(o);
    }

    /** Returns number of oranges that were processed.
     * 
     * @return
     */
    public int getProcessedOranges() {
        return orangesProcessed;
    }

    /** Returns number of bottles that were completed.
     * 
     * @return
     */
    public int getBottles() {
        return orangesProcessed / ORANGES_PER_BOTTLE;
    }
    
    /** Returns number of oranges that were processed but didn't get used in bottles.
     * 
     * @return
     */
    public int getWaste() {
        return orangesProcessed % ORANGES_PER_BOTTLE;
    }
    
    /**Records an orange as being processed
     * 
     */
    public void incrementProcessed() {
    	orangesProcessed++;
    }
    
    /**Records a working as having checked out
     * 
     */
    public void incrementClockedOut() {
    	clockedOut++;
    	System.out.println("Plant" + plantID + " " + clockedOut + " workers clocked out");
    }

}