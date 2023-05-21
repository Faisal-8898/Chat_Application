import java.util.ArrayList;
import java.util.Scanner;

public class test{
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i =0;i<5;i++){
            int x=sc.nextInt();
            arr.add(x);
        }
        System.out.println(count(arr));
    }

    public static <E> int count(ArrayList<E>li){
        int c=0;
        for(int i=0;i<li.size();i++){
            if((Integer)li.get(i)%2==0){
                c++;
            }
        }
        return c;
    }
}