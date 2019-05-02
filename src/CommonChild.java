import java.util.Scanner;

public class CommonChild {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){

        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();
        int result = commonChild(s1, s2);
        System.out.println(result);
        scanner.close();
    }

    static int commonChild(String s1, String s2) {

        int dpArray[][] = new int[s1.length()+1][s2.length()+1];

        for(int i=1; i<=s1.length(); i++){
            for(int j=1; j<=s2.length(); j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dpArray[i][j] = dpArray[i-1][j-1]+1;
                }
                else{
                    dpArray[i][j] = Math.max(dpArray[i-1][j], dpArray[i][j-1]);
                }
            }
        }

        return dpArray[s1.length()][s2.length()];
    }

}

