package hashcode;

import hashcode.domain.Cache;
import hashcode.domain.Video;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class Printer {

	public static void printFile(String filename, Resultat resultat) {
		System.out.println("---------- Printing file " + filename + " ----------");
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("output/" + filename + ".out"), "utf-8"))) {

		    formatResulat2017(writer, resultat);

		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("---------- End of print ----------");
	}

    private static void formatResulat2017(BufferedWriter writer, Resultat result) throws IOException {
        Map<Cache, List<Video>> resultat = result.getResultats();
        writer.write(String.valueOf(resultat.size()));
        for ( Map.Entry<Cache,List<Video>> entry : resultat.entrySet() ) {
            writer.newLine();
            writer.write(String.valueOf(entry.getKey().getId()));
            for ( Video v : entry.getValue() ) {
                writer.write(" "+v.getId());
            }
        }
    }
}
