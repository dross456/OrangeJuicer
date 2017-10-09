public class Worker implements Runnable {
	//The BottlerCo that spawned the worker's plant
	private BottlerCo c;
	//The Plant that spawned this worker
	private Plant p;
	//The Assembly line that this worker will get oranges from to process
	private AssemblyLine line;
	//The ID of the plant that spawned this worker
	private int plantID;
	//The ID of this worker
	private int iD;
	
	/**Creates a new worker thread to process oranges from the assembly line that it's plant passed it.
	 * 
	 * @param plant
	 * @param l
	 * @param pNum
	 * @param num
	 */
	public Worker(BottlerCo corp, Plant plant, AssemblyLine l, int pNum, int num){
		c = corp;
		p = plant;
		line = l;
		plantID = pNum;
		iD = num;
        new Thread(this, "Worker[" + pNum + ":" + num + "]").start();
        System.out.println(plantID + ":" + iD + " started");
	}
	
	/**Announces that the thread has started. 
	 * Keeps track of weather or not it is time to stop running and them announces that it has stopped.
	 * 
	 */
	public void run() {
        System.out.println(plantID + ":" + iD + " started");
		while(c.isWorkday()){
			processOrange(line.getOrange());
		}
		//System.out.println("Worker " + plantID + ":" + iD + " clocking out");   
		p.incrementClockedOut();
	}
	
	/**Processes an Orange from the line
	 * 
	 * @param o
	 */
	public void processOrange(Orange o) {
        while (o.getState() != Orange.State.Bottled) {
            o.runProcess();
        }
        p.incrementProcessed();
        //Prints completion of an orange for logging sake
        //System.out.println(plantID + ":" + iD);
    }
}