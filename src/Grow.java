import java.util.concurrent.RecursiveAction;

public class Grow extends RecursiveAction {
    int lo;
    int hi;
    static final int SEQUENTIAL_CUTOFF = 1000;
    Tree trees[];
    //    float sunExposure[][];
    static Land land;

    Grow(Tree[] t, int l, int h) {
        trees = t;
        lo = l;
        hi = h;
    }

    public static void setLand(Land s) {
        land = s;
    }

    @Override
    protected void compute() {
        if ((hi - lo) <= SEQUENTIAL_CUTOFF) {
            for (float s = 18.0f; s > 0.0f; s -= 2.0f) {
                seqGrow(trees, lo, hi, s, s + 2.0f);
            }
        } else {
            Grow left = new Grow(trees, lo, (lo + hi) / 2);
            Grow right = new Grow(trees, (lo + hi) / 2, hi);
            left.fork();
            right.compute();
            left.join();
        }
    }

    private void seqGrow(Tree t[], int lo, int hi, float minr, float maxr) {

        for (Tree tree : t) {
            int startX = tree.getStartX();
            int startY = tree.getStartY();
            int endX = tree.getEndX(land.getWidth());
            int endY = tree.getEndY(land.getHeight());
            int area = tree.getArea(land);
            Object boom[] = new Object[area];

            if (tree.inrange(minr, maxr)) {
                int p=0;
                for (int y = startY; y <= endY; y++) {
                    for (int x=startX; x<=endX;x++) {
                        boom[p]=land.locks(x,y);
                        p++;
                        //tree.sungrow(land);
                    }
                }
//                    }
//                }
//               tree.sungrow(land);
                //land.shadow(tree);

            }
            setLocks(tree, boom, 0);
        }
    }

    private void setLocks(Tree t, Object lock[], int p){
        if (p>=lock.length){
            t.sungrow(land);
        }
        else {
            synchronized (lock[p]){
                setLocks(t, lock, p);
                p++;
            }
        }
    }
}
