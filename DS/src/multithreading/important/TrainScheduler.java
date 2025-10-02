package multithreading.important;

import java.util.*;

class Train {
    int arrival;
    int departure;

    public Train(int arrival, int departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    @Override
    public String toString() {
        return "[" + arrival + ", " + departure + "]";
    }
}

public class TrainScheduler {

    public static int minPlatformsRequired(List<Train> trains) {
        // Sort trains by arrival time
        trains.sort(Comparator.comparingInt(t -> t.arrival));

        // Min-heap to store the earliest departure time among trains occupying platforms
        PriorityQueue<Integer> platformQueue = new PriorityQueue<>();

        for (Train train : trains) {
            // If a platform is free (earliest departure ≤ current arrival), reuse it
            if (!platformQueue.isEmpty() && platformQueue.peek() <= train.arrival) {
                platformQueue.poll();  // Free that platform
            }
            // Allocate current train to a platform (new or reused)
            platformQueue.offer(train.departure);
        }

        // Number of platforms needed = size of the priority queue
        return platformQueue.size();
    }

    public static void main(String[] args) {
        List<Train> trains = Arrays.asList(
                new Train(900, 910),
                new Train(940, 1200),
                new Train(950, 1120),
                new Train(1100, 1130),
                new Train(1500, 1900),
                new Train(1800, 2000)
        );

        int platforms = minPlatformsRequired(trains);
        System.out.println("Minimum number of platforms required: " + platforms);
    }
}

