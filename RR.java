/**
 * RR scheduling algorithm.
 */
 
import java.util.*;

public class RR implements Algorithm
{
    private List<Task> queue;
    private Task currentTask;
    private double sum_wait_time;
    private double sum_response_time;
    private double sum_turn_time;
    private int task_count;

    public RR(List<Task> queue) {
        this.queue = queue;
    }

    public List<Task> sort(List<Task> queue){
        //sort queue
        Collections.sort(queue, new Comparator<Task>(){
             public int compare(Task t1, Task t2){
                 if(t1.getPriority() == t2.getPriority())
                     return 0;
                 return t1.getPriority() < t2.getPriority() ? -1 : 1;
             }
        });
        Collections.reverse(queue);
        return queue;
    }

    public void schedule() {
        System.out.println("RR Scheduling \n");
        task_count = queue.size();

        queue = sort(queue);

        while (!queue.isEmpty()) {
            // currentTask = pickNextTask();
            if(queue.size()==1){
                currentTask = pickNextTask();
                int wait = CPU.getTime();
                sum_wait_time += wait;

                int response = CPU.getTime();
                sum_response_time += response;
                CPU.run(currentTask, currentTask.getBurst());
                // we can get the current CPU time
                int turn = CPU.getTime();
                sum_turn_time += turn;
            }
            else if(queue.get(0).getPriority() != queue.get(1).getPriority()){
                currentTask = pickNextTask();
                int wait = CPU.getTime();
                sum_wait_time += wait;

                int response = CPU.getTime();
                sum_response_time += response;
                CPU.run(currentTask, currentTask.getBurst());
                // we can get the current CPU time
                int turn = CPU.getTime();
                sum_turn_time += turn;
            }
            else{
                // task with same priority
                // they should both get a slice of time ten
                List<Task> rr_queue = new ArrayList<Task>();
                int[] rr_start;
                int pr = queue.get(0).getPriority();
                while(queue.get(0).getPriority() == pr){
                    Task t = queue.remove(0);
                    rr_queue.add(t);
                }
                // rr_queue is a list with tasks all with same priority
                int rr_count = rr_queue.size();
                rr_start = new int[rr_count];
                int[] rr_burst;
                rr_burst = new int[rr_count];

                // make duplicate of burst time
                for(int i=0; i<rr_queue.size(); i++){
                    rr_burst[i] = rr_queue.get(i).getBurst();
                }

                // on first interation count the response time
                for(int i=0; i<rr_queue.size(); i++){
                    //give each task a slice, also record starting time
                    rr_start[i] = CPU.getTime();
                    Task t = rr_queue.get(i);
                    int rest_time = run_task(t,10);
                    if(rest_time == 0){
                        // task is done
                        sum_response_time += rr_start[i];
                        sum_turn_time += CPU.getTime();
                        sum_wait_time += (CPU.getTime()-rr_burst[i]);
                    }
                    t.setBurst(rest_time);
                }
                while(!all_done(rr_queue)){
                    for(int i=0; i<rr_queue.size(); i++){
                        //give each task a slice, also record starting time
                        Task t = rr_queue.get(i);
                        if(t.getBurst()==0){
                            continue;
                        }
                        int rest_time = run_task(t,10);
                        if(rest_time == 0){
                            // task is done
                            sum_response_time += rr_start[i];
                            sum_turn_time += CPU.getTime();
                            sum_wait_time += (CPU.getTime()-rr_burst[i]);
                        }
                        t.setBurst(rest_time);
                    }
                }
            }
        }
    }

    public boolean all_done(List<Task> queue){
        boolean all_done = true;
        for(int i=0; i<queue.size(); i++){
            if(queue.get(i).getBurst()>0){
                all_done = false;
            }
        }
        return all_done;
    }

    public int run_task(Task task, int slice){
        if(task.getBurst()>=slice){
            // System.out.println(t);
            CPU.run(currentTask, slice);
        }else{
            // System.out.println(t);
            CPU.run(currentTask, task.getBurst());
        }
        return (task.getBurst()-slice) > 0 ? (task.getBurst()-slice) : 0;
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
