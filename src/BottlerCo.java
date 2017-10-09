public class BottlerCo{
	//How long to run the juice processing
    private static final long PROCESSING_TIME = 5000;
    //The number of plants to spawn
    private static final int NUM_PLANTS = 5;
    //Whether or not Workers should continue processing oranges
    public volatile boolean workDay = true;
    //number of plants finished
    private volatile int closedDown = 0;
    //When to tell the plants to close down
    private long endTime;
    //The total number of Oranges provided
    private int totalProvided = 0;
    //The total number of Oranges Processed
    private int totalProcessed = 0;
    //The total number of bottles made
    private int totalBottled = 0;
    //The total number of oranges wasted
    private int totalWasted = 0;
    

    /**Spawns the plants where the oranges are processed
     * 
     * @param args
     */
    BottlerCo(){
    	WorkDay();
    }
    public static void main(String[] args) {
    	new BottlerCo();
    	
    	
    	
    }
    
    /**Spawns the plants that fetch oranges and spawn workers.
     * Keeps track of if it's still time to work.
     * Reports the results from the plants operations.
     * 
     */
    public void WorkDay() {
    	for( int i = 0; i < NUM_PLANTS; i++){
            new Plant(this, i);
        }
    	
    	//Give the plants time to do work
        endTime = System.currentTimeMillis() + PROCESSING_TIME;
        
        while (System.currentTimeMillis() < endTime) {
        	try {
				Thread.sleep(10);
			} catch (InterruptedException ignored) {}
        }
        workDay =false;
        
        while(closedDown < NUM_PLANTS) {
        	try {
				Thread.sleep(10);
			} catch (InterruptedException ignored) {}
        }
        
     //Summarize the results
        System.out.println("BottlerCo total provided/processed = " + totalProvided + "/" + totalProcessed);
        System.out.println("BottlerCo created " + totalBottled + " bottles and wasted " + totalWasted + " oranges"); 
        

    }

    /**
     * 
     * @return If plants and workers are to continue working.
     */
	public boolean isWorkDay() {		
		return workDay;
	}
	
	/**Records all necessary data from the a plants completed operation.
	 * 
	 * @param p
	 */
    public synchronized void closePlant(Plant p) {
    	closedDown++;
    	System.out.println("Plant " + p.getPlantID() + " shuting down" + "");
    	System.out.println(closedDown + " plants closed down");
    	totalProvided += p.getProvided();
    	totalProcessed += p.getOrangesProcessed();
    	totalBottled += p.getBottles();
    	totalWasted += p.getWaste();
    }

    
}