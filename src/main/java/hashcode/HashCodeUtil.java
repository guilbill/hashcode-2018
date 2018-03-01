package hashcode;

public class HashCodeUtil {

    public static int distance(int rowStart, int colStart, int rowEnd, int colEnd ) {
        return Math.abs(rowEnd-rowStart)+Math.abs(colEnd-colStart);
    }
}
