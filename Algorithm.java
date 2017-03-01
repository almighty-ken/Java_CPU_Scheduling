/**
 * Interface representing a generic scheduling algorithm.
 *
 * @author Greg Gagne - March 2016
 */

public interface Algorithm
{
    /**
     * Invokes the scheduler
     */
    public abstract void schedule();

    /**
     * Selects the next task using the appropriate scheduling algorithm
     */
    public abstract Task pickNextTask();

    /**
     * Reports average wait time of all tasks
     */
    public abstract double getAverageWaitTime();
    
    /**
     * Reports average turnaround time of all tasks
     */
    public abstract double getAverageTurnaroundTime();
    
    /**
     * Reports average response time of all tasks
     */
    public abstract double getAverageResponseTime();
}
