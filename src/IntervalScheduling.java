import java.util.*;

/**
 * Interval Scheduling
 * <p>
 * Input Format:
 * <No. of intervals>
 * <Interval 1 start time> <Interval 1 end time> <Interval 1 value>
 * <Interval 2 start time> <Interval 2 end time> <Interval 2 value>
 * ...
 * <Interval n start time> <Interval n end time> <Interval n value>
 * <p>
 * <p>
 * Variations of the Interval Scheduling:-
     * Basic Interval Scheduling
 * Total Interval Scheduling
 * Weighted Interval Scheduling
 */

public class IntervalScheduling {

    private List<Job> jobs;

    public static void main(String[] args) {
        IntervalScheduling obj = new IntervalScheduling();
        obj.getUserInputs();
        obj.basicIntervalScheduling();
        obj.totalIntervalScheduling();
        obj.weightedIntervalScheduling();
    }


    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        int intervalCount = scan.nextInt();
        jobs = new ArrayList<>();
        for (int i = 0; i < intervalCount; i++) {
            int start = scan.nextInt();
            int end = scan.nextInt();
            int cost = scan.nextInt();
            jobs.add(new Job(start, end, cost));
        }
    }

    public void basicIntervalScheduling() {

        Collections.sort(jobs, new SortByEndTime());

        List<Job> selectedJobs = new ArrayList<>();
        Job currentJob = jobs.get(0);
        selectedJobs.add(currentJob);
        for (Job job : jobs) {
            if (currentJob.endTime <= job.startTime) {
                selectedJobs.add(job);
                currentJob = job;
            }
        }

        System.out.println("\nMaximum non overlapping jobs that can be scheduled are:: " + selectedJobs.size());
        for (Job job : selectedJobs) {
            System.out.println("Start:" + job.startTime + ", End:" + job.endTime);
        }
    }

    public void totalIntervalScheduling() {

        int noOfProcessesNeeded = 0;
        int[] processAllocation = new int[jobs.size()];
        PriorityQueue<Processor> queue= new PriorityQueue<>
                (new SortProcessorByEndTime());

        Collections.sort(jobs, new SortByStartTime());

        for (int i = 0; i < jobs.size(); i++) {
            processAllocation[i] = -1;
        }

        for (int i = 0; i < jobs.size(); i++) {
            if (noOfProcessesNeeded == 0) {
                Processor processor = new Processor(0,jobs.get(i).endTime);
                queue.add(processor);
                processAllocation[i] = 0;
                noOfProcessesNeeded++;
            } else {
                Processor processor = queue.peek();

                if(processor.endTime<=jobs.get(i).startTime){
                    processor = queue.poll();
                }
                else{
                    processor = new Processor(queue.size(),jobs.get(i).endTime);
                    queue.add(processor);
                    noOfProcessesNeeded++;
                }
                processAllocation[i] = processor.id;
            }
        }

        System.out.println("\nNo of processors needed to schedule all the jobs is: " + noOfProcessesNeeded);

        for (int i = 0; i < jobs.size(); i++) {
            System.out.println("Start:" + jobs.get(i).startTime + ", End:" + jobs.get(i).endTime + "=> Process:" + (processAllocation[i] + 1));
        }
    }

    public void weightedIntervalScheduling() {

        Collections.sort(jobs, new SortByEndTime());

        int[] compatibility = new int[jobs.size()];
        for (int i = 0; i < jobs.size(); i++) {
            compatibility[i] = -1;
        }

        for (int i = 1; i < jobs.size(); i++) {
            compatibility[i] = findCompatibleJob(jobs,
                    0, jobs.size(), jobs.get(i).startTime);
        }

        int[] dpArray = new int[jobs.size()+1];
        for (int i = 1; i < jobs.size()+1; i++){
            dpArray[i] = max(
                    jobs.get(i-1).costProfit +
                            (compatibility[i-1]>=0 ?
                                    jobs.get(compatibility[i-1]).costProfit :
                                    0 )
                    , dpArray[i-1]);
        }

        System.out.println("\nMaximum benefit/profit we can get from scheduling the jobs is: " + dpArray[jobs.size()]);
        int i = jobs.size()-1;
        while(i>=0){
            System.out.println("Start:" + jobs.get(i).startTime + ", End:" + jobs.get(i).endTime + ", Cost:" +jobs.get(i).costProfit);
            i = compatibility[i];
        }
    }

    public int max(int value1, int value2) {
        if (value1 > value2)
            return value1;
        return value2;
    }


    class Job {
        int startTime;
        int endTime;
        int costProfit;

        public Job(int start, int end, int cost) {
            startTime = start;
            endTime = end;
            costProfit = cost;
        }
    }

    class SortByStartTime implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.startTime - o2.startTime;
        }
    }

    class SortByEndTime implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.endTime - o2.endTime;
        }
    }

    class Processor{
        int id;
        int endTime;

        public Processor(int id, int endTime){
            this.id = id;
            this.endTime =endTime;
        }
    }

    class SortProcessorByEndTime implements Comparator<Processor> {
        @Override
        public int compare(Processor o1, Processor o2) {
            return o1.endTime - o2.endTime;
        }
    }

    public int findCompatibleJob(List<Job> arr, int left, int right, int key) {
        int mid;
        while (left <= right) {
            mid = (right + left) / 2;
            if (arr.get(mid).endTime <= key && arr.get(mid+1).endTime > key) {
                return mid;
            } else if (arr.get(mid).endTime < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
