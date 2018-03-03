package hashcode;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import hashcode.domain.Ride;
import hashcode.domain.Vehicle;

public class HashCodeUtil {

	public static int distance(int rowStart, int colStart, int rowEnd, int colEnd ) {
		return Math.abs(rowEnd-rowStart)+Math.abs(colEnd-colStart);
	}

	public static int getMinRideRideDistance(Ride ride, Collection<Ride> all, int max) {
		int minDistance = max;
		for (Ride other : all) {
			if (!other.equals(ride)) {
				minDistance = Math.min(minDistance, getDistanceRideRide(ride, other));
			}
		}
		return minDistance;
	}

	private static int getDistanceRideRide(Ride ride1, Ride ride2) {
		return HashCodeUtil.distance(ride1.getRowEnd(), ride1.getColumnEnd(), ride2.getRowStart(),
				ride2.getColumnStart());
	}

	public static int getDistanceVehicleRide(Vehicle vehicle, Ride ride) {
		return HashCodeUtil.distance(
				vehicle.getCurrentRow(),
				vehicle.getCurrentColumn(),
				ride.getRowStart(),
				ride.getColumnStart()
				);
	}

	public static int getDistanceVehicleRidePlusAttente(Vehicle vehicle, Ride ride) {
		return getDistanceVehicleRide(vehicle, ride)
				+ getTempsDAttenteEventuel(vehicle, ride);
	}

	public static int getTempsDAttenteEventuel(Vehicle vehicle, Ride ride) {
		return Math.max(0, // eventuel temps d'attente
				getAttente(vehicle, ride));
	}

	/**
	 * Si négatif on est à la bourre
	 */
	public static int getAttente(Vehicle vehicle, Ride ride) {
		return ride.getEarliestStart() -
				( vehicle.getStep() + getDistanceVehicleRide(vehicle, ride));
	}

	public static int calculeScore(int bonus, Vehicle vehicle, Ride ride) {
		return ride.getDistance() +
				(getAttente(vehicle, ride) >= 0 ? bonus : 0);
	}

	public static boolean rideFaisable(Vehicle vehicle, Ride ride, int nbSteps) {
		int vehicleNextStep = vehicle.getStep() + HashCodeUtil.getDistanceVehicleRide(vehicle, ride);
		if (vehicleNextStep <= ride.getEarliestStart()) {
			vehicleNextStep = ride.getEarliestStart();
		}
		vehicleNextStep = vehicleNextStep + ride.getDistance();

		return vehicleNextStep <= ride.getLatestFinish() && vehicleNextStep <= nbSteps;
	}

	public static Ride donneRideLePlusProche(Situation situation, Vehicle vehicle, Collection<Ride> rides) {
		SortedSet<Ride> rideTries = new TreeSet<>((o1, o2) -> {
			if (o1.equals(o2)) {
				return 0;
			}
			int distanceo1 = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o1);
			int distanceo2 = HashCodeUtil.getDistanceVehicleRidePlusAttente(vehicle, o2);
			if (distanceo1 == distanceo2) {
				// int distanceOthers1 = HashCodeUtil.getMinRideRideDistance(o1, rides,
				// situation.getNbRows() + situation.getNbColumns());
				// int distanceOthers2 = HashCodeUtil.getMinRideRideDistance(o2, rides,
				// situation.getNbRows() + situation.getNbColumns());
				// if (distanceOthers1 == distanceOthers2) {
				// int scoreo1 = HashCodeUtil.calculeScore(situation.getBonus(), vehicle, o1);
				// int scoreo2 = HashCodeUtil.calculeScore(situation.getBonus(), vehicle, o2);
				// return Integer.compare(scoreo2, scoreo1);
				// }
				// return Integer.compare(distanceOthers1, distanceOthers2);

				int attente1 = HashCodeUtil.getTempsDAttenteEventuel(vehicle, o1);
				int attente2 = HashCodeUtil.getTempsDAttenteEventuel(vehicle, o2);
				if (attente1 == attente2) {
					return Integer.compare(o1.getLatestFinish(), o2.getLatestFinish());
				}
				return Integer.compare(attente1, attente2);
			} else {
				return Integer.compare(distanceo1, distanceo2);
			}
		});
		for (Ride ride : rides) {
			rideTries.add(ride);
		}
		return rideTries.isEmpty() ? null : rideTries.first();
	}
}
