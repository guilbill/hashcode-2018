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
		int stepsPerdus = 0;
		int bonus = 0;
		int score = 0;

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
				List<Ride> lstRides = situation.getRides().stream().filter(r -> !r.isDejaPris() && HashCodeUtil.rideFaisable(vehicle, r, situation.getNbSteps()))
						.collect(Collectors.toList());
				if (lstRides.isEmpty()) {
					itVehicle.remove();
					continue;
				}
				
//				log.info("Vehicules restants: {}", lstVehicle.size());

				Ride ride = HashCodeUtil.donneRideLePlusProche(situation, vehicle, lstRides);
				// Ride ride = donneRideAuMeilleurScore(situation.getBonus(), vehicle, lstRides);
				if (ride == null) {
					itVehicle.remove();
					continue;
				}
				
				int vehicleNextStep = vehicle.getStep() + HashCodeUtil.getDistanceVehicleRide(vehicle, ride);
				if (vehicleNextStep <= ride.getEarliestStart()) {
					stepsPerdus += ride.getEarliestStart() - vehicleNextStep;
					bonus++;
					vehicleNextStep = ride.getEarliestStart();
				}
				vehicleNextStep = vehicleNextStep + ride.getDistance();
				
				score += HashCodeUtil.calculeScore(situation.getBonus(), vehicle, ride);
				vehicle.setStep(vehicleNextStep);
				result.getMapResult().get(vehicle).add(ride);
				ride.setDejaPris(true);
				vehicle.setCurrentRow(ride.getRowEnd());
				vehicle.setCurrentColumn(ride.getColumnEnd());
			}
		}
		
		List<Ride> lstRides = situation.getRides().stream().filter(r -> !r.isDejaPris())
				.collect(Collectors.toList());
		log.info("rides restants: {}/{}, bonus: {}/{}, score: {}, steps perdus: {}", lstRides.size(), situation.getNbSteps(), bonus, situation.getNbSteps(), score, stepsPerdus);
		
		return result;
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
