package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistaGrafica();

        mapa.addVeiculo(0, new Coordenadas(4, 5), new Coordenadas(4, 12));
        mapa.addVeiculo(1, new Coordenadas(3, 12), new Coordenadas(3, 5));

        mapa.vistaCarros();
    }

}
