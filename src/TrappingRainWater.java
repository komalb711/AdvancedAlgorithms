import java.util.Scanner;

public class TrappingRainWater {

    int[] heights;

    public static void main(String[] args) {
        TrappingRainWater obj = new TrappingRainWater();
        obj.takeUserInput();
        int waterTrapped = obj.trap();
        System.out.println("Water Trapped:" + waterTrapped);
    }

    public void takeUserInput(){
        Scanner scan = new Scanner(System.in);
        int size = scan.nextInt();
        heights = new int[size];
        for(int i=0; i<size; i++){
            heights[i] = scan.nextInt();
        }
    }

        public int trap() {
            if(heights == null|| heights.length<3) return 0;
            int size = heights.length;
            int trappedWater = 0;

            int[] leftMax = new int[size];
            leftMax[0] = heights[0];

            for(int i=1; i<size;i++){
                leftMax[i] = Math.max(leftMax[i-1], heights[i]);
            }

            int[] rightMax = new int[size];
            rightMax[size-1] = heights[size-1];
            for(int i=size-2; i>=0; i--){
                rightMax[i] = Math.max(rightMax[i+1], heights[i]);
            }

            for(int i=1; i<size-1; i++){
                trappedWater += Math.min(leftMax[i], rightMax[i]) - heights[i];
            }
            return trappedWater;
        }
}
