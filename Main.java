//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Trip{
    String car;
    String startTime;
    String endTime;
    int passengers;

    public Trip(String car, String startTime, String endTime,int passengers){
        this.car=car;
        this.startTime=startTime;
        this.endTime=endTime;
        this.passengers=passengers;
    }
}


public class Main {
    public static void main(String[] args) {
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip("A", "03:00", "16:00", 21));
        trips.add(new Trip("B", "01:00","08:00", 10));
        trips.add(new Trip("C", "09:00", "21:00", 20));
        trips.add(new Trip("D", "22:00", "00:00", 2));
        trips.add(new Trip("E", "02:00", "04:00", 5));
        trips.add(new Trip("F", "07:00", "12:00", 7));
        trips.add(new Trip("G", "15:00", "19:00",10));
        trips.add(new Trip("H", "17:00", "00:00", 10));
        trips.add(new Trip("I", "05:00", "10:00", 7));
        trips.add(new Trip("J", "11:00","14:00", 6));
        trips.add(new Trip("K", "00:00", "06:00", 8));
        trips.add(new Trip("L", "20:00", "23:00", 3));
        trips.add(new Trip("M", "13:00", "18:00", 12));


        Collections.sort(trips, Comparator.comparing(t -> t.endTime));
        int n = trips.size();
        int[] dp = new int[n];
        int[] last = new int[n];

        for(int i=0; i<n;i++){

            last[i]=-1;
            for(int j=0; j<i;j++){
                if(canSchedule(trips.get(j), trips.get(i))){
                    last[i] = j;
                }
            }
        }
        for(int i=0; i<n;i++){
            dp[i] = trips.get(i).passengers;
            if(last[i] != -1){
                dp[i] += dp[last[i]];
            }
        }

        int maxPassengers = 0;
        int maxIndex=0;
        for(int i=0;i<n;i++){
            if(dp[i] > maxPassengers){
                maxPassengers = dp[i];
                maxIndex=i;
            }
        }

        /*
        output preparazio
         */
        StringBuilder output = new StringBuilder();
        output.append("The maximum number of passengers is ").append(maxPassengers).append("\n");
        output.append("The maximum commission possible is R").append(maxPassengers*10).append("\n");
        output.append("The cars driven to earn the maximum commission are: ");
        List<String> cars = new ArrayList<>();
        while(maxIndex != -1){
            cars.add(trips.get(maxIndex).car);
            maxIndex=last[maxIndex];
        }
        Collections.reverse(cars);
        output.append(String.join(", ", cars)).append(".");

        //output file in a glimpse of hope

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"))){
            writer.write(output.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(output.toString());
    }
    private static boolean canSchedule(Trip a, Trip b){
        return !b.startTime.equals(a.startTime) && b.startTime.compareTo(a.endTime) >= 0;
    }
}