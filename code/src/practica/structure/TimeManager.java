package practica.structure;

public class TimeManager {

    private static long timeIni;

    public static void initTimer() {
        timeIni = System.currentTimeMillis();
    }

    public static float getTimeSecs() {
        return (System.currentTimeMillis() - timeIni) / 1000.0f;
    }
}
