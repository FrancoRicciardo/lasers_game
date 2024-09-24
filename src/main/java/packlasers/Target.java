package packlasers;

public class Target {
    private final int x;
    private final int y;
    private boolean meAlcanzaron;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
        this.meAlcanzaron = false;
    }

    public boolean getMeAlcanzaron() {
        return meAlcanzaron;
    }

    public void fuiAlcanzado(){
        this.meAlcanzaron = true;
    }

    public void noFuiAlcanzado(){
        this.meAlcanzaron = false;
    }

    public int getCoordX() {
        return this.x;
    }

    public int getCoordY() {
        return this.y;
    }
}
