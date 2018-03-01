package hashcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
			int nb1 = getInt(firstLine, 0);
			int nb2 = getInt(firstLine, 1);

			// TODO remplir l'objet Situation
			for (int i = 0; i < nb1; i++) {
				String[] elements = br.readLine().split(" ");

			}

			String line;
			while ((line = br.readLine()) != null) {
				String[] elements = line.split(" ");
				log.info(line);
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
