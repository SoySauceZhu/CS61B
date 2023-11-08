public class NBody {


    public static double readRadius(String file) {
        /*
        Read radius of the universe
         */
        In in = new In(file);

        int first = in.readInt();

        return in.readDouble();
    }
}
