package hashcode.algo1;

import hashcode.Situation;

public final class GenetiqueRunnable implements Runnable {

    public int generationCount = 0;
    Population innerPop;
    private Situation tableau;

    public GenetiqueRunnable(Situation tableau, Population pop)  {
        innerPop = pop;
        this.tableau = tableau;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) break;
            innerPop = Algorithm.evolvePopulation(innerPop, tableau, 50);
            generationCount++;
        }
    }
}

