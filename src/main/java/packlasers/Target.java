package packlasers;

public class Target {
    private final Posicion posicion;
    private boolean meAlcanzaron;

    public Target(int x, int y) {
        this.posicion = new Posicion(x, y);
        this.meAlcanzaron = false;
    }

    public boolean getMeAlcanzaron() {
        return meAlcanzaron;
    }

    public void fuiAlcanzado(){
        this.meAlcanzaron = true;
    }

    public void reiniciarTarget(){
        this.meAlcanzaron = false;
    }

    public Posicion getPosicion() {
        return posicion;
    }
}
