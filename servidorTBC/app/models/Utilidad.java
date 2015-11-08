package models;
import java.util.concurrent.ThreadLocalRandom;


public final class Utilidad  {

    private Utilidad(){

    }
    
	public static double[] coordenadasNuevas(){
        double randomLat = ThreadLocalRandom.current().nextDouble(4.46, 4.82);
        double randomLong = ThreadLocalRandom.current().nextDouble(-74.21, -73.80);
        double[] res = new double[2];
        res[0] = randomLat;
        res[1] = randomLong;
        return res;
    }
}