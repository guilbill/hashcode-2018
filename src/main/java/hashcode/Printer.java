package hashcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Printer {

	private static final String FILE_SUFFIX = ".out.%03d";

	public static void printFile(String filename, Resultat resultat) {
		log.info("---------- Printing file " + filename + " ----------");
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(findFreeFile(filename)), "utf-8"))) {
			writer.write("something"); // TODO parcourir les lignes de resultat et les écrire
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		log.info("---------- End of print ----------");
	}

	private static File findFreeFile(String filename) {
		int index = 1;
		File file = new File("output/" + filename + String.format(FILE_SUFFIX, index));
		while (file.exists()) {
			index++;
			file = new File("output/" + filename + String.format(FILE_SUFFIX, index));
		}
		return file;
	}
}
