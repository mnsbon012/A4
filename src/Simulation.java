import java.util.concurrent.ForkJoinPool;

public class Simulation implements Runnable {
    static final ForkJoinPool pool = new ForkJoinPool();
//    final Object lock = new Object();
    SunData sunD;
    boolean play = true;
    boolean end = false;
//    boolean pause=false;
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
//                pool.invoke(new Grow(sunD.trees,0, sunD.trees.length));
//                sunD.sunmap.resetShade();
                for(Tree t: sunD.trees){
                    t.sungrow(sunD.sunmap);
                }
                year++;
                Grow.land.resetShade();
                TreeGrow.yearL.setText("year: "+year);

            }
        }
    }

    public void pause(){
        play=false;
//        pause = true;
    }

    public void end(){
        play = false;
        end=true;
    }

    public void resume(){
//        pause = false;
        play = true;
        //run();
    }

    public void reset(){
        boolean tempP = play;
        play=false;
        for(Tree t:sunD.trees){
            t.setExt(0.4f);
        }
        year = 0;
        TreeGrow.yearL.setText("year: "+year);
        play=tempP;     // if paused and reset will still be paused
    }

}
