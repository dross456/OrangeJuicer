public class Plant implements Runnable {    
    //Number of workers aside from the plant
    private static final int NUM_WORKERS = 3;
    //The number of oranges per bottle
    private static final int ORANGES_PER_BOTTLE = 4;
    //The BottlerCo that spawned the plant
    private BottlerCo c;
    //The AssemblyLine that the plant will add it's fetched oranges to
    private AssemblyLine line = new AssemblyLine();
    //The plants ID
    private int plantID;
    //The number of oranges the plant has fetched
    private int provided = 0;
    //The number of oranges processed by the plants workers
    private int orangesProcessed;
    //The Number of workers who have finished their last orange
    private volatile int clockedOut = 0;
    
    /**Basic constructor requiring it's ID past to it.
     * 
     * @param num
     */
    public Plant(BottlerCo corp, int num) {
    	c = corp;
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
		for(int i = 0; i < NUM_WORKERS; i++){
            new Worker(c, this, line, getPlantID(), i);
        }
        
		while(c.isWorkday()){
			this.fetchOrange(new Orange());
            provided++;
		}

        
        //
        while(clockedOut<NUM_WORKERS) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
        }
        
        // Summarize the results
        System.out.println("Plant " + getPlantID() + " total provided/processed = " + provided + "/" + this.getProcessedOranges());
        System.out.println("Plant " + getPlantID() + " created " + this.getBottles() + " bottles and wasted " + this.getWaste() + " oranges");
		c.closePlant(this);
	}

	/**Creates and fetches new oranges putting them on the assembly line.
	 * 
	 * @param o
	 */
    public void fetchOrange(Orange o) {
    	o.runProcess();
    	line.addOrange(o);
    }

    /**Returns number of oranges that were processed.
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
    
    /**Returns number of oranges that were processed but didn't get used in bottles.
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
    	//System.out.println("Plant" + getPlantID() + " " + clockedOut + " workers clocked out");
    }

	/**
	 * @return the plantID
	 */
	public int getPlantID() {
		return plantID;
	}
	
	/**
	 * @return the number of oranges provided
	 */
	public int getProvided() {
		return provided;
	}
	
	/**
	 * @return the number of oranges processed
	 */
	public int getOrangesProcessed() {
		return orangesProcessed;
	}
	
}