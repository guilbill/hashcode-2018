package hashcode;

import java.util.ArrayList;
import java.util.List;

import hashcode.domain.Ride;
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
