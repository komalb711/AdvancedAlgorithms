import java.util.Scanner;

public class DistinctSubsequences {
    String source, target;

    public static void main(String[] args) {
        DistinctSubsequences obj = new DistinctSubsequences();
        obj.getUserInput();
        int count = obj.numDistinctSequences();
        System.out.println("Number of distinct subsequences:" + count);
    }

    public void getUserInput(){
        Scanner scan = new Scanner(System.in);
        source = scan.next();
        target = scan.next();
    }

    public int numDistinctSequences() {
        int[][] dpArray = new int[target.length()+1][source.length()+1];

        for(int i=0; i<source.length(); i++){
            dpArray[0][i] = 1;
        }

        for(int i=1; i<dpArray.length; i++){
            for(int j=1; j<dpArray[i].length; j++){
                dpArray[i][j] = dpArray[i][j-1];
                if(source.charAt(j-1) == target.charAt(i-1)){
                    dpArray[i][j] += dpArray[i-1][j-1];
                }
            }
        }
        return dpArray[target.length()][source.length()];
    }
}
