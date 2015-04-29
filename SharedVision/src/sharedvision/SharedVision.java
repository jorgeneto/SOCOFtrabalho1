package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistaGrafica();
        mapa.vistaCarros();
        
//        mapa.addVeiculo(0, new Coordenadas(0, 10), new Coordenadas(0, 0), false);
//        mapa.addVeiculo(1, new Coordenadas(0, 19), new Coordenadas(0, 0), false);
        
//        mapa.addVeiculoNormal(new Coordenadas(10, 19), new Coordenadas(0, 0));
    }

}
