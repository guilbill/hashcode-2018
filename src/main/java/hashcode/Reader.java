package hashcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import hashcode.domain.Ride;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Reader {

	public static Situation readFile(String filename) {
		log.info("---------- Reading file " + filename + " ----------");
		Situation situation = new Situation();

		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("files/" + filename + ".in");
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			String[] firstLine = br.readLine().split(" ");
			situation.setNbRows(getInt(firstLine, 0));
			situation.setNbColumns(getInt(firstLine, 1));
			situation.setNbVehicles(getInt(firstLine, 2));
			situation.setNbRides(getInt(firstLine, 3));
			situation.setBonus(getInt(firstLine, 4));
			situation.setNbSteps(getInt(firstLine, 5));

			// Rides
			for (int i = 0; i < situation.getNbRides(); i++) {
				String[] elements = br.readLine().split(" ");
				Ride ride = new Ride();
				ride.setIndex(i);
				ride.setRowStart(getInt(elements, 0));
				ride.setColumnStart(getInt(elements, 1));
				ride.setRowEnd(getInt(elements, 2));
				ride.setColumnEnd(getInt(elements, 3));
				ride.setEarliestStart(getInt(elements, 4));
				ride.setLatestFinish(getInt(elements, 5));
				ride.setDistance(HashCodeUtil.distance(
						ride.getRowStart(),
						ride.getColumnStart(),
						ride.getRowEnd(),
						ride.getColumnEnd()));
				situation.getRides().add(ride);
			}

		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		}

		log.info(situation.toString());
		log.info("---------- End of read ----------");
		return situation;
	}

	private static int getInt(String[] elements, int index) {
		return Integer.parseInt(elements[index]);
	}

	private static double getDouble(String[] elements, int index) {
		return Double.parseDouble(elements[index]);
	}
}
