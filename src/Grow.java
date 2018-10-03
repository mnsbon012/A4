import java.util.concurrent.RecursiveAction;

public class Grow extends RecursiveAction {
    int lo;
    int hi;
    static final int SEQUENTIAL_CUTOFF= 1000;
    Tree trees[];
//    float sunExposure[][];
    static Land land;

    Grow (Tree[] t, int l, int h){
        trees=t;
        lo=l;
        hi=h;
    }

    public static void setLand(Land s){
        land = s;
    }

    @Override
    protected void compute(){
        if ((hi-lo)<=SEQUENTIAL_CUTOFF){
            for (float s=18.0f;s>0.0f;s-=2.0f) {
                seqGrow(trees, lo, hi, s, s + 2.0f);
            }
        }
        else{
            Grow left = new Grow(trees, lo, (lo+hi)/2);
            Grow right = new Grow(trees, (lo+hi)/2, hi);
            left.fork();
            right.compute();
            left.join();
        }
    }

    private void seqGrow(Tree t[], int lo, int hi,float minr, float maxr) {

        for (Tree tree : t) {
            int startX = tree.getStartX();
            int startY = tree.getStartY();
            int endX = tree.getEndX(land.getWidth());
            int endY = tree.getEndY(land.getHeight());
            int area = tree.getArea(land);

            if (tree.inrange(minr, maxr)) {
                tree.sungrow(land);
                //land.shadow(tree);
            }
        }
    }
            //int p =0;
            //Object Trees[] = new Object[area];

//            for (int y = startY; y<=endY; y++){
//                for (int x=startX; x<=endX; x++){
//
//                    Trees[p]= land.locked(x,y);
//                    p++;
//                }
//            }
//            lockRun(tree, Trees, Trees.length-1, minr, maxr);
//        }
//    }
//
//    private void lockRun(Tree t, Object Trees[], int p,float minr,float maxr){
//        if (p>=0){
//            synchronized (Trees[p]){
//                p--;
//                lockRun(t,Trees, p, minr,maxr);
//            }
//        }
//        else{
//            if (t.inrange(minr,maxr)){
//                t.sungrow(land);
//                land.shadow(t);
//            }
//
//        }
//    }


}

