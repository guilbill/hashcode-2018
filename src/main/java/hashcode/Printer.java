package hashcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hashcode.domain.Ride;
import hashcode.domain.Vehicle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Printer {

	private static final String FILE_SUFFIX = ".out.%03d";

	public static void printFile(String filename, Resultat resultat) {
		log.info("---------- Printing file " + filename + " ----------");
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(findFreeFile(filename)), "utf-8"))) {
			writeResult(writer, resultat);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		log.info("---------- End of print ----------");
	}

    private static void writeResult(Writer writer, Resultat resultat) {
	    resultat.getMapResult().entrySet().stream()
                .map(e -> e.getValue().size() + " " + e.getValue().stream().map(Ride::getIndex).map(String::valueOf).collect(Collectors.joining(" ")))
                .forEach(s -> {
                    try {
                        writer.write(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static File findFreeFile(String filename) throws IOException {
		int index = 1;
		File file = new File("output/" + filename + String.format(FILE_SUFFIX, index));
		while (file.exists()) {
			index++;
			file = new File("output/" + filename + String.format(FILE_SUFFIX, index));
		}
		return file;
	}
}
