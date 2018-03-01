package hashcode;

import java.util.*;

import hashcode.domain.Ride;
import hashcode.domain.Vehicle;
import lombok.Data;

@Data
public class Situation {

	private int nbRows;
	private int nbColumns;
	private int nbVehicles;
	private int nbRides;
	private int bonus;
	private int nbSteps;

	private List<Ride> rides = new ArrayList<>();


}
