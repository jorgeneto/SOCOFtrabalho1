package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistaGrafica();

        mapa.addVeiculo(0, new Coordenadas(18, 5), new Coordenadas(0, 0));
        mapa.addVeiculo(1, new Coordenadas(12, 14), new Coordenadas(0, 0));

        mapa.vistaCarros();
    }

}
