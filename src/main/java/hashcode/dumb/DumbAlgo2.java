package hashcode.dumb;

import hashcode.HashCodeUtil;
import hashcode.IAlgo;
import hashcode.Resultat;
import hashcode.Situation;
import hashcode.domain.Ride;
import hashcode.domain.Vehicle;

import java.util.*;

public class DumbAlgo2 implements IAlgo {
    @Override
    public Resultat trouveMeilleurResultat(Situation situation) {
        Resultat result = new Resultat();

        List<Vehicle> lstVehicle = new ArrayList<>();
        for (int i = 0  ; i < situation.getNbVehicles() ; i++ ) {
            lstVehicle.add(new Vehicle(i));
        }

        for (Ride r : situation.getRides() ) {

        }

        return result;
    }


}
