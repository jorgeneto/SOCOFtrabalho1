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
//        mapa.addVeiculo(0, new Coordenadas(8, 0), new Coordenadas(11, 0), false);
//        mapa.addVeiculo(1, new Coordenadas(11, 0), new Coordenadas(8, 0), false);
//        mapa.addVeiculo(2, new Coordenadas(12, 19), new Coordenadas(0, 0), false);
        mapa.addVeiculo(0, new Coordenadas(19, 19), new Coordenadas(0, 0), true);

        mapa.addVeiculoNormal(new Coordenadas(10, 19), new Coordenadas(0, 0));
        
        mapa.vistaCarros();

//           Teste t = new Teste();
//        t.interfaceTeclado();
    }

}
