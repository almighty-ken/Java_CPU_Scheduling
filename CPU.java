/**
 * "Virtual" CPU
 *
 * This virtual CPU also maintains system time.
 *
 * @author Greg Gagne - March 2016
 */
 
public class CPU
{
    // the multiplier for simulating executing the task
    public final static int MULTIPLIER = 10;

    // notion of time by the CPU
    private static int time = 0;

    /**
     * Run the specified task for the specified slice of time.
     */
    public static void run(Task task, int slice) {
        System.out.println("Will run " + task);

        // simulate executing a task by sleeping for the time slice
        try {
            Thread.sleep(slice * MULTIPLIER);
        }
        catch (InterruptedException ie) {
            System.err.println(ie);
        }

        // update the current time
        time += slice;
    }

    /**
     * Returns the current notion of time
     */
    public static int getTime() {
        return time;
    }
}
