import java.util.*;

public class IntervalScheduling {

    private List<Job> jobs;

    public static void main(String[] args) {
        IntervalScheduling obj = new IntervalScheduling();
        obj.getUserInputs();
//        obj.basicIntervalScheduling();
//        obj.totalIntervalScheduling();
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

        System.out.println("Maximum non overlapping jobs that can be scheduled are:: " + selectedJobs.size());
        for (Job job : selectedJobs) {
            System.out.println("Start:" + job.startTime + ", End:" + job.endTime);
        }
    }

    public void totalIntervalScheduling() {
        Collections.sort(jobs, new SortByStartTime());

        int noOfProcessesNeeded = 0;
        int[] processAllocation = new int[jobs.size()];
        int[] processorEndTime = new int[jobs.size()];

        for (int i = 0; i < jobs.size(); i++) {
            processorEndTime[i] = -1;
            processAllocation[i] = -1;
        }

        for (int i = 0; i < jobs.size(); i++) {
            if (noOfProcessesNeeded == 0) {
                processAllocation[i] = noOfProcessesNeeded;
                processorEndTime[noOfProcessesNeeded] = jobs.get(i).endTime;
                noOfProcessesNeeded++;
            } else {
                int currentProcessor = -1;
                for (int j = 0; j < noOfProcessesNeeded; j++) {
                    if (processorEndTime[j] <= jobs.get(i).startTime) {
                        currentProcessor = j;
                        break;
                    }
                }
                if (currentProcessor == -1) {
                    processAllocation[i] = noOfProcessesNeeded;
                    processorEndTime[noOfProcessesNeeded] = jobs.get(i).endTime;
                    noOfProcessesNeeded++;
                } else {
                    processAllocation[i] = currentProcessor;
                    processorEndTime[i] = jobs.get(i).endTime;
                }
            }
        }
        System.out.println("No of processors needed to schedule all the jobs is: " + noOfProcessesNeeded);

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
            int j;
            for (j = 0; j<i; j++) {
                if (jobs.get(i).startTime >= jobs.get(j).endTime) {
                    compatibility[i] = j;
                    break;
                }
            }
        }

        int[] dpArray = new int[jobs.size()+1];
        for (int i = 1; i < jobs.size()+1; i++){
            dpArray[i] = max(jobs.get(i-1).costProfit + (compatibility[i-1]>=0?jobs.get(compatibility[i-1]).costProfit:0), dpArray[i-1]);
        }

        System.out.println("Maximum benefit/profit we can get from scheduling the jobs is: " + dpArray[jobs.size()]);
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
}
