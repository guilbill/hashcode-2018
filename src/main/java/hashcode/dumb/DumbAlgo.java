package hashcode.dumb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import hashcode.HashCodeUtil;
import hashcode.IAlgo;
import hashcode.Resultat;
import hashcode.Situation;
import hashcode.domain.Ride;
import hashcode.domain.Vehicle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DumbAlgo implements IAlgo {
	
	@Override
	public Resultat trouveMeilleurResultat(Situation situation) {
		Resultat result = new Resultat();

		List<Vehicle> lstVehicle = new ArrayList<>();
		for (int i = 0; i < situation.getNbVehicles(); i++) {
			Vehicle vehicle = new Vehicle(i);
			lstVehicle.add(vehicle);
			result.getMapResult().put(vehicle, new ArrayList<>());
		}

		while (!lstVehicle.isEmpty()) {
			Iterator<Vehicle> itVehicle = lstVehicle.iterator();
			
			while (itVehicle.hasNext()) {
				Vehicle vehicle = itVehicle.next();
				List<Ride> lstRides = situation.getRides().stream().filter(r -> !r.isDejaPris())
						.collect(Collectors.toList());
				log.info("Vehicules restants: {}, Rides restants: {}", lstVehicle.size(), lstRides.size());

				Ride ride = donneRideLePlusProche(situation, vehicle, lstRides);
				// Ride ride = donneRideAuMeilleurScore(situation.getBonus(), vehicle,
				// lstRides);
				if (ride == null) {
					itVehicle.remove();
					break;
				}
				vehicle.setStep(vehicle.getStep() + HashCodeUtil.getDistanceVehicleRide(vehicle, ride));
				if (vehicle.getStep() <= ride.getEarliestStart()) {
					vehicle.setStep(ride.getEarliestStart());
				}
				vehicle.setStep(vehicle.getStep() + ride.getDistance());
				if (vehicle.getStep() <= situation.getNbSteps()) {
					result.getMapResult().get(vehicle).add(ride);
					ride.setDejaPris(true);
					vehicle.setCurrentRow(ride.getRowEnd());
					vehicle.setCurrentColumn(ride.getColumnEnd());
				} else {
					lstRides.remove(ride);
					if (lstRides.isEmpty()) {
						itVehicle.remove();
						break;
					}
				}
			}

		}
		return result;
	}

	private Ride donneRideLePlusProche(Situation situation, Vehicle vehicle, Collection<Ride> rides) {
		SortedSet<Ride> rideTries = new TreeSet<>((o1, o2) -> {
			if (o1.equals(o2)) {
				return 0;
			}
			int distanceo1 = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o1);
			int distanceo2 = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o2);
			if (distanceo1 == distanceo2) {
//				int distanceOthers1 = HashCodeUtil.getMinRideRideDistance(o1, rides,
//						situation.getNbRows() + situation.getNbColumns());
//				int distanceOthers2 = HashCodeUtil.getMinRideRideDistance(o2, rides,
//						situation.getNbRows() + situation.getNbColumns());
//				if (distanceOthers1 == distanceOthers2) {
					 int scoreo1 = HashCodeUtil.calculeScore(situation.getBonus(), vehicle, o1);
					 int scoreo2 = HashCodeUtil.calculeScore(situation.getBonus(), vehicle, o2);
					 return Integer.compare(scoreo2, scoreo1);
//				}
//				return Integer.compare(distanceOthers1, distanceOthers2);
			} else {
				return Integer.compare(distanceo1, distanceo2);
			}
		});
		for (Ride ride : rides) {
			int distancePourAllerAuDepart = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, ride);
			if (!ride.isDejaPris()
					&& vehicle.getStep() + distancePourAllerAuDepart + ride.getDistance() < ride.getLatestFinish()) {
				rideTries.add(ride);
			}
		}
		return rideTries.isEmpty() ? null : rideTries.first();
	}

	private Ride donneRideAuMeilleurScore(int bonus, Vehicle vehicle, Collection<Ride> rides) {
		SortedSet<Ride> rideTries = new TreeSet<>((o1, o2) -> {
			int scoreo1 = HashCodeUtil.calculeScore(bonus, vehicle, o1);
			int scoreo2 = HashCodeUtil.calculeScore(bonus, vehicle, o2);
			if (scoreo1 == scoreo2) {
				return Integer.compare(HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o1),
						HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o2));
			} else {
				return Integer.compare(scoreo2, scoreo1);
			}
		});
		for (Ride ride : rides) {
			int distancePourAllerAuDepart = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, ride);
			if (!ride.isDejaPris()
					&& vehicle.getStep() + distancePourAllerAuDepart + ride.getDistance() < ride.getLatestFinish()) {
				rideTries.add(ride);
			}
		}
		return rideTries.isEmpty() ? null : rideTries.first();
	}

}
