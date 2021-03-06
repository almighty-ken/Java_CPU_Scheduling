/**
 * SJF scheduling algorithm.
 */
 
import java.util.*;

public class SJF implements Algorithm
{
    private List<Task> queue;
    private Task currentTask;
    private double sum_wait_time;
    private double sum_response_time;
    private double sum_turn_time;
    private int task_count;

    public SJF(List<Task> queue) {
        this.queue = queue;
    }

    public void schedule() {
        System.out.println("SJF Scheduling \n");
        task_count = queue.size();

        //sort queue
        Collections.sort(queue, new Comparator<Task>(){
             public int compare(Task t1, Task t2){
                 if(t1.getBurst() == t2.getBurst())
                     return 0;
                 return t1.getBurst() < t2.getBurst() ? -1 : 1;
             }
        });

        while (!queue.isEmpty()) {
            currentTask = pickNextTask();

            int wait = CPU.getTime();
            sum_wait_time += wait;

            int response = CPU.getTime();
            sum_response_time += response;

            
            // System.out.println(currentTask);
            CPU.run(currentTask, currentTask.getBurst());

            // we can get the current CPU time
            int turn = CPU.getTime();
            sum_turn_time += turn;

        }
    }

    public Task pickNextTask() {
        Task t = queue.remove(0);
        return t;
    }

    public double getAverageWaitTime() {
        return sum_wait_time/task_count;
    }
    
    public double getAverageResponseTime() {
        return sum_response_time/task_count;
    }

    public double getAverageTurnaroundTime() {
        return sum_turn_time/task_count;
    }
}
