package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistaGrafica();

        // teste distancia de seguranca
        mapa.addVeiculo(0, new Coordenadas(0, 0), new Coordenadas(19, 0));
        mapa.addVeiculo(1, new Coordenadas(19, 0), new Coordenadas(0, 0));
        
//        mapa.addVeiculo(0, new Coordenadas(3, 4), new Coordenadas(0, 0));
//        mapa.addVeiculo(1, new Coordenadas(19, 18), new Coordenadas(12, 0));
        
        mapa.vistaCarros();
    }

}
