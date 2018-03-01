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
			String line;
			while ((line = br.readLine()) != null) {
				log.info(line);
				// TODO remplir l'objet Situation
			}
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		}
		log.info("---------- End of read ----------");
		return situation;
	}
}
