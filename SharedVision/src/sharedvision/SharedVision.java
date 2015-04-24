package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistaGrafica();

        mapa.addVeiculo(0, new Coordenadas(13, 16), new Coordenadas(7, 6));
//        mapa.addVeiculo(1, new Coordenadas(1, 0), new Coordenadas(15, 0));
//        mapa.addVeiculo(2, new Coordenadas(19, 0), new Coordenadas(15, 15));
//        mapa.addVeiculo(3, new Coordenadas(19, 19), new Coordenadas(0, 0));
//        mapa.addVeiculo(4, new Coordenadas(0, 19), new Coordenadas(7, 6));

        mapa.vistaCarros();
    }

}
