package sharedvision;

public class Coordenadas {

    private int x;
    private int y;

    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        //return "X=" + x + " Y=" + y;
        return x + "," + y;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Coordenadas) {
            if (x == ((Coordenadas) object).getX() && y == ((Coordenadas) object).getY()) {
                return true;
            }
        }
        return false;
    }

}
