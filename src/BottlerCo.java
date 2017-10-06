public class BottlerCo{
    private static final int NUM_PLANTS = 5;

    /** Spawns the plants where the oranges are processed
     * 
     * @param args
     */
    public static void main(String[] args) {
    	for( int i = 0; i < NUM_PLANTS; i++){
            new Plant(i);
        }
    }
}