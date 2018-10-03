import java.util.concurrent.ForkJoinPool;

public class Simulation implements Runnable {
    static final ForkJoinPool pool = new ForkJoinPool();
    final Object lock = new Object();
    SunData sunD;
    boolean play = true;
    boolean end = false;
    int year;


    public Simulation(SunData s){
        sunD = s;
        year=0;
    }

@Override
    public void run(){
        while (!end){
            while (play){
                Grow.setLand(sunD.sunmap);
                pool.invoke(new Grow(sunD.trees,0, sunD.trees.length));
                sunD.sunmap.resetShade();
                year++;
                TreeGrow.yearL.setText("year: "+year);

            }
            synchronized (lock){
                try{
                    System.out.println("paused");
                    lock.wait();
                    System.out.println("play");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public void pause(){
        play=false;
    }

    public void stop(){
        play = false;
        end=true;
        synchronized (lock){
            lock.notifyAll();
        }
    }

    public void resume(){
        play = true;
        synchronized (lock){
            lock.notifyAll();
        }
    }

}
