import java.util.Scanner;

public class CoinChange {
    int[] coins;
    int amount;

    public static void main(String[] args) {
        CoinChange obj = new CoinChange();
        obj.takeUserInput();
        int minCoinsNeeded = obj.findMinCoinsNeeded2();
        System.out.println("Minimum number of coins needed:" + minCoinsNeeded);
    }

    public void takeUserInput(){
        Scanner scan = new Scanner(System.in);
        int count = scan.nextInt();
        coins = new int[count];

        for(int i=0; i<count; i++){
            coins[i] = scan.nextInt();
        }

        amount = scan.nextInt();
    }

    public int findMinCoinsNeeded1() {
        int[][] dpArray = new int[coins.length+1][amount+1];

        int MAX = 999999999;

        for(int j=1; j<=amount;j++){
            dpArray[0][j] = MAX;
        }

        for(int i=1; i<=coins.length;i++){
            for(int j=1; j<=amount;j++){
                if(coins[i-1]<=j){
                    dpArray[i][j] = Math.min(dpArray[i-1][j], 1+dpArray[i][j-coins[i-1]]);
                } else{
                    dpArray[i][j] = dpArray[i-1][j];
                }
            }
        }

        return dpArray[coins.length][amount] == MAX ? -1 : dpArray[coins.length][amount] ;
    }

    public int findMinCoinsNeeded2() {
        int[] dpArray = new int[amount+1];

        int MAX = 999999999;

        for(int j=1; j<=amount;j++){
            dpArray[j] = MAX;
        }

        for (int coin : coins) {
            for (int j = 1; j <= amount; j++) {
                if (coin <= j) {
                    dpArray[j] = Math.min(dpArray[j], 1 + dpArray[j - coin]);
                } else {
                    dpArray[j] = dpArray[j];
                }
            }
        }

        return dpArray[amount] == MAX ? -1 : dpArray[amount] ;
    }

}
