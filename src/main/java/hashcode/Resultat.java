package hashcode;

import hashcode.domain.Ride;
import hashcode.domain.Vehicle;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Resultat {

    Map<Vehicle, List<Ride>> mapResult = new HashMap<>();

}
