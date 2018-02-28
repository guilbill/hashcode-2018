package hashcode.algo1;

import hashcode.Resultat;
import hashcode.Situation;
import hashcode.domain.Cache;
import hashcode.domain.Endpoint;
import hashcode.domain.Request;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by mfreche on 23/02/17.
 */
public class ResultEvaluator {

    public static int evaluateResult(Situation situation, Resultat resultat) {
        int totalScore = 0;
        int totalNbReq = 0;
        for ( Request req : situation.getRequestList() ) {
            Endpoint endpoint = req.getFrom();
            int latenceDirecte = endpoint.getLatencyToDataCenter();
            Map.Entry<Cache, Integer> plusRapide = endpoint.getCacheConnexions().entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue))
                    .findFirst().orElse(null);
            int economie = 0;
            if ( plusRapide != null ) {
                economie = latenceDirecte - plusRapide.getValue();
            }
            totalScore += economie * req.getNbRequests();
            totalNbReq += req.getNbRequests();
        }
        return (int)(((double)totalScore / (double)totalNbReq) * 1000d);

    }

}
