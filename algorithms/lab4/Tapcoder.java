import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by mikha on 29.02.2016.
 */
public class Tapcoder {
    final static int BROWN_BORDER = 2200;
    static int maxChanges = 0;

    public static void fillArray(int[][] d,int[] contestValue,int curContest, int curRating, int curChanges ){
        maxChanges = Math.max(curChanges,maxChanges);
        if(curContest<d.length-1){
            if(curChanges<=d[curContest][curRating]){
                return;
            }else{
                d[curContest][curRating] = curChanges;
            }


            int winRating = curRating + contestValue[curContest];
            int losRating = Math.max(curRating - contestValue[curContest],0);

            fillArray(d,contestValue, curContest+1, losRating, d[curContest][curRating]);

            if(winRating<BROWN_BORDER){
                fillArray(d,contestValue,curContest+1,winRating,d[curContest][curRating]);
            }else{
                if((curContest<d.length-2) && (winRating-contestValue[curContest+1]<BROWN_BORDER)){
                    fillArray(d,contestValue,curContest+2,Math.max(winRating-contestValue[curContest+1],0),d[curContest][curRating]+2);
                }
                if((curContest==d.length-2)){
                    maxChanges = Math.max(maxChanges,d[curContest][curRating]+1);
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("tapcoder.in"));
        PrintWriter out = new PrintWriter(new File("tapcoder.out"));

        int n = in.nextInt();
        int[] contestValue = new int[n];

        for (int i = 0; i < n; i++) {
            contestValue[i] = in.nextInt();
        }

        int startValue = in.nextInt();
        int[][] d = new int[n + 1][BROWN_BORDER];

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < BROWN_BORDER; j++) {
                d[i][j]=-1;
            }
        }

        fillArray(d, contestValue, 0, startValue, 0);
        out.print(maxChanges);


        out.close();
    }
}
