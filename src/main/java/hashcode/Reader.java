package hashcode;

import hashcode.domain.Cache;
import hashcode.domain.Endpoint;
import hashcode.domain.Request;
import hashcode.domain.Video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Reader {

	public static Situation readFile(String filename) {
		System.out.println("---------- Reading file " + filename + " ----------");
		Situation situation = new Situation();
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("files/" + filename + ".in");
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

		    return parsing2017(br);

		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		System.out.println("---------- End of read ----------");
		return situation;
	}

    private static Situation parsing2017(BufferedReader in) throws IOException {
        Situation situation = new Situation();
        int nbVideos;
        int nbEndpoints;
        int nbRequest;

        String str = in.readLine();

        String[] split = str.split(" ");
        nbVideos = Integer.valueOf(split[0]);
        nbEndpoints = Integer.valueOf(split[1]);
        nbRequest = Integer.valueOf(split[2]);
        for ( int i = 0 ; i < Integer.valueOf(split[3]) ; i++ ) {
            situation.getCacheList().put(i, new Cache(i,Integer.valueOf(split[4])));
        }

        situation.getVideoList().addAll(traiteLigneVideos(in.readLine().split(" ")));
        situation.getEndpointList().putAll(traiteLignesEndPoint(situation.getCacheList(), in,nbEndpoints));
        situation.getRequestList().addAll(traiteLignesRequest(situation.getEndpointList(), situation.getVideoList(), in, nbRequest));

        return situation;
    }


    private static Collection<? extends Request> traiteLignesRequest(Map<Integer, Endpoint> mapEndpoints, List<Video> lstVideo, BufferedReader in, int nbRequest) throws IOException {
        List<Request> lstRequest = new ArrayList<>();
        for ( int i = 0 ; i < nbRequest ; i ++ ){
            String[] str = in.readLine().split(" ");
            lstRequest.add(new Request(mapEndpoints.get(Integer.valueOf(str[1])), Integer.valueOf(str[2]), lstVideo.get(Integer.valueOf(str[0]))));
        }
        return lstRequest;
    }

    private static Map<? extends Integer,? extends Endpoint> traiteLignesEndPoint(Map<Integer, Cache> mapCache, BufferedReader in, int nbEndpoints) throws IOException {
        Map<Integer, Endpoint> result = new HashMap<>();
        for ( int i = 0 ; i < nbEndpoints ; i++ ) {
            String[] str = in.readLine().split(" ");
            Map<Cache, Integer> cacheConnexion = new HashMap<>();
            Endpoint endpoint = new Endpoint(i, cacheConnexion, Integer.valueOf(str[0]));
            for ( int j = 0 ; j < Integer.valueOf(str[1]) ; j++ ) {
                String[] strConnexion = in.readLine().split(" ");
                cacheConnexion.put(mapCache.get(Integer.valueOf(strConnexion[0])), Integer.valueOf(strConnexion[1]));
            }
            result.put(i, endpoint);
        }
        return result;
    }

    private static Collection<? extends Video> traiteLigneVideos(String[] split) {
        List<Video> result = new ArrayList<>();
        for ( int i = 0 ; i < split.length ; i++ ) {
            result.add(new Video(i, Integer.valueOf(split[i])));
        }
        return result;
    }
}
