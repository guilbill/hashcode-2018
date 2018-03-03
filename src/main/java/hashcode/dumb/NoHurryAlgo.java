package hashcode.dumb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hashcode.HashCodeUtil;
import hashcode.IAlgo;
import hashcode.Resultat;
import hashcode.Situation;
import hashcode.domain.Ride;
import hashcode.domain.Vehicle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoHurryAlgo implements IAlgo {

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
			lstVehicle.stream().sorted(Comparator.comparing(Vehicle::getStep));

			Vehicle vehicle = lstVehicle.get(0);
			List<Ride> lstRides = situation.getRides().stream().filter(r -> !r.isDejaPris() && HashCodeUtil.rideFaisable(vehicle, r, situation.getNbSteps()))
					.collect(Collectors.toList());
			if (lstRides.isEmpty()) {
				lstVehicle.remove(vehicle);
				continue;
			}

			Ride ride = HashCodeUtil.donneRideLePlusProche(situation, vehicle, lstRides);
			if (ride == null) {
				lstVehicle.remove(vehicle);
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

		List<Ride> lstRides = situation.getRides().stream().filter(r -> !r.isDejaPris())
				.collect(Collectors.toList());
		log.info("rides restants: {}/{}, bonus: {}/{}, score: {}, steps perdus: {}", lstRides.size(), situation.getNbRides(), bonus, situation.getNbRides(), score, stepsPerdus);

		return result;
	}

}
