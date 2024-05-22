package race;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

class RaceCarRunnable extends Car implements Runnable {
    private int passed;
    private int distance;
    private boolean isFinish;
    private CountDownLatch latch;

    public RaceCarRunnable(String name, int maxSpeed, int distance, CountDownLatch latch) {
        super(name, maxSpeed);
        this.distance = distance;
        this.latch = latch;
    }

    public int getPassed() {
        return passed;
    }

    public boolean isFinish() {
        return isFinish;
    }

    private int getRandomSpeed() {
        Random random = new Random();
        return random.nextInt(maxSpeed / 2) + maxSpeed / 2;
    }

    @Override
    public void run() {
        try {
            while (!isFinish) {
                Thread.sleep(1000);
                int speed = getRandomSpeed();
                passed += speed;
                if (passed >= distance) {
                    passed = distance;
                    isFinish = true;
                    latch.countDown();
                }
                System.out.println(name + " => speed: " + speed + "; progress: " + passed + "/" + distance);
            }
            System.out.println(this.name + " FINISHED !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
