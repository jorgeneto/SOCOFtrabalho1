package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistagrafica();

        //Veiculo carro1 = new Veiculo(mapa.getMapa(), 1, new Coordenadas(9, 0), new Coordenadas(0, 0));
        Veiculo carro2 = new Veiculo(mapa.getMapa(), 2, new Coordenadas(19, 19), new Coordenadas(15, 15));

        //new Thread(carro1).start();
        new Thread(carro2).start();

//        Astar teste = new Astar(mapa.getMapa(), 0);
//        ArrayList<Coordenadas> caminho = teste.caminho(new Coordenadas(19, 3), new Coordenadas(19, 2));
//        new Ajuda().printCaminho(caminho);
//
//        caminho = teste.caminho(new Coordenadas(19, 19), new Coordenadas(0, 0));
//        new Ajuda().printCaminho(caminho);
    }

}
