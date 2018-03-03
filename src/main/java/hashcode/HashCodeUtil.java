package hashcode;

import java.util.Collection;

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
				(getTempsDAttenteEventuel(vehicle, ride) == 0 ? bonus : 0);
	}


}
