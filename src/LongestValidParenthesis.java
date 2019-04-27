import java.util.Scanner;
import java.util.Stack;

public class LongestValidParenthesis {
    String expression;
    int maxLength;


    public static void main(String[] args) {
        LongestValidParenthesis obj = new LongestValidParenthesis();
        obj.getUserInput();
        obj.findLongestValidParenthesis();
        System.out.println("Longest Valid Parenthesis:" + obj.maxLength);
    }

    public void getUserInput(){
        Scanner scan = new Scanner(System.in);
        expression = scan.next();
        maxLength = 0;
    }

    public void findLongestValidParenthesis(){
        Stack<Integer> stack = new Stack<>();
        maxLength = 0;

        stack.push(-1);

        for(int i=0; i<expression.length(); i++){
            char brace = expression.charAt(i);

            if(brace == '('){
                stack.push(i);
            }
            else{
                stack.pop();
                if(!stack.isEmpty()){
                    maxLength = Math.max(maxLength, i-stack.peek());
                }
                else{
                    stack.push(i);
                }
            }
        }
    }
}
