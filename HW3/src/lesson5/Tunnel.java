package lesson5;

import java.lang.module.ModuleReader;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    public final Semaphore SemTun = new Semaphore(main.CARS_COUNT/2, true);
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {

        try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
            SemTun.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        SemTun.release();

        }
    }

