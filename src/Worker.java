public class Worker implements Runnable {
	private Plant p;
	private AssemblyLine line;
	private int plantID;
	private int iD;
	
	/** Creates a new worker thread to process oranges from the assembly line that it's plant passed it.
	 * 
	 * @param plant
	 * @param l
	 * @param pNum
	 * @param num
	 */
	Worker(Plant plant, AssemblyLine l, int pNum, int num){
		p = plant;
		line = l;
		plantID = pNum;
		iD = num;
        new Thread(this, "Worker[" + pNum + ":" + num + "]").start();
        System.out.println(plantID + ":" + iD + " started");
	}
	
	/** Announces that the thread has started. 
	 * Keeps track of weather or not it is time to stop running and them announces that it has stopped.
	 * 
	 */
	public void run() {
        System.out.println(plantID + ":" + iD + " started");
		while(p.timeToWork==true){
			processOrange(line.getOrange());
		}
		System.out.println("Worker " + plantID + ":" + iD + " clocking out");   
		p.incrementClockedOut();
	}
	
	/** Processes an Orange from the line
	 * 
	 * @param o
	 */
	public void processOrange(Orange o) {
        while (o.getState() != Orange.State.Bottled) {
            o.runProcess();
            // o.nextState();
        }
        p.incrementProcessed();
        //Prints completion of an orange for logging sake
        System.out.println(plantID + ":" + iD);
    }
}