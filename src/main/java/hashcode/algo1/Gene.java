package hashcode.algo1;

import hashcode.domain.Cache;
import hashcode.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by mfreche on 23/02/17.
 */
@AllArgsConstructor
@Data
public class Gene {

    Cache cache;
    List<Video> videoList;
}
