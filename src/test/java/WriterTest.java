import hashcode.Printer;
import hashcode.Resultat;
import hashcode.domain.Ride;
import hashcode.domain.Vehicle;
import org.junit.Test;

import java.util.*;


public class WriterTest {


    @Test
    public void testWriter() {
        // GIVNE
        Resultat resultat = new Resultat();
        Map<Vehicle, List<Ride>> map = new HashMap<>();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        Ride ride = new Ride();
        ride.setIndex(3);
        Ride ride2 = new Ride();
        ride2.setIndex(5);
        map.put(vehicle, Arrays.asList(ride, ride2));
        resultat.setMapResult(map);

        // WHEN
        Printer.printFile("test", resultat);

        // THEN

        // Balek je controle à la main !!!
    }

}
