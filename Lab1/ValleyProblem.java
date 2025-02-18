import java.util.Scanner;

public class ValleyProblem {
    public static void main(String[] args) {
        String road = new String();
        Scanner scan = new Scanner(System.in);
        road = scan.nextLine();
        int roadint = 0;
        int nrv = 0;
        char ch;
        Boolean v1 = false, v2 = false;

        for(int i = 0;i < road.length();i++){
            ch = road.charAt(i);
            if(ch == 'D'){
                if(roadint == 0) v1 = true;
                roadint--;
            }
            else if(ch == 'U'){
                roadint++;
                if(v1 && (roadint == 0)) v2 = true;
            }
            if(v1 && v2) {
                nrv++;
                v1 = false;
                v2 = false;
            }
        }
        System.out.println("Numarul de vai este: " + nrv);

    }
}