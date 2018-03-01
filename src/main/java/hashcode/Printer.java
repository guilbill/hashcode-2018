package hashcode;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Printer {

	public static void printFile(String filename, Resultat resultat) {
		System.out.println("---------- Printing file " + filename + " ----------");
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("output/" + filename + ".out"), "utf-8"))) {
			writer.write("something"); // TODO parcourir les lignes de resultat et les écrire
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("---------- End of print ----------");
	}
}
