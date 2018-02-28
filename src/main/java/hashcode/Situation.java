package hashcode;

import hashcode.domain.Cache;
import hashcode.domain.Endpoint;
import hashcode.domain.Request;
import hashcode.domain.Video;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Situation {

    Map<Integer, Cache> cacheList = new HashMap<>();
    Map<Integer,Endpoint> endpointList = new HashMap<>();
    List<Request> requestList = new ArrayList<>();
    List<Video> videoList = new ArrayList<>();

}
