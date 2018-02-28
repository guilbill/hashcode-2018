package hashcode;

import hashcode.domain.Cache;
import hashcode.domain.Video;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Data
public class Resultat {

    Map<Cache, List<Video>> resultats;

}
