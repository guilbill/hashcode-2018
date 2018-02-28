package hashcode;

import hashcode.algo1.Algo1;

public class Main {

	public static void main(String[] args) {
		String[] files = { "sampleFile"/*, "kittens", "me_at_the_zoo", "trending_today","videos_worth_spreading"*/ };
		for (String filename : files) {
			processOneFile(filename);
		}
	}

	private static void processOneFile(String filename) {
		Situation situation = Reader.readFile(filename);
		IAlgo algo = new Algo1(20 * 100);
		Resultat resultat = algo.trouveMeilleurResultat(situation);
		Printer.printFile(filename, resultat);
	}

}
