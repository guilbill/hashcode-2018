package hashcode.dumb;

import hashcode.HashCodeUtil;
import hashcode.IAlgo;
import hashcode.Resultat;
import hashcode.Situation;
import hashcode.domain.Ride;
import hashcode.domain.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class DumbAlgo implements IAlgo {
    @Override
    public Resultat trouveMeilleurResultat(Situation situation) {
        Resultat result = new Resultat();
        for ( int i = 0 ; i < situation.getNbVehicles(); i++ ) {
            Vehicle vehicle = new Vehicle(i);
            result.getMapResult().put(vehicle, new ArrayList<>());
            List<Ride> lstRides = situation.getRides().stream()
                    .filter(r -> !r.isDejaPris()).collect(Collectors.toList());
            int step = 0;
            while (true) {
                Ride ride = donneRideLePlusProche(step, vehicle, lstRides);
                if ( ride == null ) {
                    break;
                }
                step += HashCodeUtil.getDistanceVehicleRide(vehicle, ride);
                if (step <= ride.getEarliestStart()) {
                    step = ride.getEarliestStart();
                }
                step += ride.getDistance();
                if (step <= situation.getNbSteps()) {
                    result.getMapResult().get(vehicle).add(ride);
                    ride.setDejaPris(true);
                    vehicle.setCurrentRow(ride.getRowEnd());
                    vehicle.setCurrentColumn(ride.getColumnEnd());
                } else {
                    lstRides.remove(ride);
                    if ( lstRides.isEmpty() ) {
                        break;
                    }
                }
            }

        }
        return result;
    }

    private Ride donneRideLePlusProche(int step, Vehicle vehicle, Collection<Ride> rides) {
        SortedSet<Ride> rideTries = new TreeSet<>(
                (o1, o2) -> {
                    int distanceo1 = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o1, step);
                    int distanceo2 = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o2, step);
                    if ( distanceo1 == distanceo2 ) {
                        // tri par distance
                        return Integer.compare(o2.getDistance(), o1.getDistance());
                    } else {
                        return Integer.compare(distanceo1, distanceo2);
                    }
                }
        );
        for ( Ride ride : rides ) {
            int distancePourAllerAuDepart = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, ride, step);
            if (!ride.isDejaPris() &&
                    step + distancePourAllerAuDepart + ride.getDistance() < ride.getLatestFinish()) {
                rideTries.add(ride);
            }
        }
        return rideTries.isEmpty() ? null : rideTries.first();
    }


}
