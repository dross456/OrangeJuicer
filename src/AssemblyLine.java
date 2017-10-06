import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {
	
	private final List<Orange> oranges;
	
	/**Basic constructor.
	 * 
	 */
	AssemblyLine(){
		oranges = new ArrayList<Orange>();
	}
	
	/** Adds an orange to the queue (assemblyline)
	 * 
	 * @param o
	 */
	public synchronized void addOrange(Orange o){
		oranges.add(o);
		if(oranges.size() == 1) {
				notifyAll();
		}
	}

	/** Retrieves an orange from the queue.
	 * 
	 * @return
	 */
	public synchronized Orange getOrange() {
		while (oranges.size() == 0) {
			try {
				wait();
			}catch(InterruptedException ignored) {}
		}
		Orange o = oranges.get(0);
		oranges.remove(0);
		return o;
	}
	
}
