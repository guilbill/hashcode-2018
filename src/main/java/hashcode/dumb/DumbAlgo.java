package hashcode.dumb;

import hashcode.HashCodeUtil;
import hashcode.IAlgo;
import hashcode.Resultat;
import hashcode.Situation;
import hashcode.domain.Ride;
import hashcode.domain.Vehicle;

import java.util.ArrayList;

public class DumbAlgo implements IAlgo {
    @Override
    public Resultat trouveMeilleurResultat(Situation situation) {
        Resultat result = new Resultat();
        for ( int i = 0 ; i < situation.getNbVehicles(); i++ ) {
            Vehicle vehicle = new Vehicle();
            result.getMapResult().put(vehicle, new ArrayList<>());
            int step = 0;
            for ( Ride ride : situation.getRides() ) {
                if ( !ride.isDejaPris() ) {
                    step += HashCodeUtil.distance(
                            vehicle.getCurrentRow(),
                            vehicle.getCurrentColumn(),
                            ride.getRowStart(),
                            ride.getColumnStart()
                    );
                    if (step <= ride.getEarliestStart()) {
                        step = ride.getEarliestStart();
                    }
                    step += ride.getDistance();
                    if (step <= situation.getNbSteps()) {
                        result.getMapResult().get(vehicle).add(ride);
                        ride.setDejaPris(true);
                    } else {
                        break;
                    }
                }
            }



        }
        return result;
    }
}
