package race;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class Race {
    public static void main(String[] args) throws InterruptedException {
        List<RaceCarRunnable> cars = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(3);

        cars.add(new RaceCarRunnable("race.Car 1", 200, 1000, latch));
        cars.add(new RaceCarRunnable("race.Car 2", 220, 1000, latch));
        cars.add(new RaceCarRunnable("race.Car 3", 240, 1000, latch));

        List<Thread> threads = new ArrayList<>();
        for (RaceCarRunnable car : cars) {
            threads.add(new Thread(car));
        }

        startRace(threads);
        latch.await();
    }

    public static void startRace(List<Thread> cars) {
        new Thread(() -> {
            try {
                System.out.println("race.Race starting in:");
                for (int i = 3; i > 0; i--) {
                    System.out.println(i + "...");
                    Thread.sleep(500);
                }
                System.out.println("GO!!!");
                for (Thread car : cars) {
                    car.start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
