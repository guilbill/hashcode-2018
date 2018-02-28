package hashcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {

	public static void readFile(String filename) {
		System.out.println("---------- Reading file " + filename + " ----------");
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("files/" + filename + ".in");
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		System.out.println("---------- End of read ----------");
	}
}
