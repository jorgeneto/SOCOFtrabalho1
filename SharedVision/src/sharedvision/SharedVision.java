package sharedvision;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistaGrafica();

        Veiculo carro0 = new Veiculo(mapa, 0, new Coordenadas(13, 16), new Coordenadas(7, 6));
        Veiculo carro1 = new Veiculo(mapa, 1, new Coordenadas(1, 0), new Coordenadas(15, 0));
        Veiculo carro2 = new Veiculo(mapa, 2, new Coordenadas(19, 0), new Coordenadas(15, 15));
        Veiculo carro3 = new Veiculo(mapa, 3, new Coordenadas(19, 19), new Coordenadas(0, 0));
        Veiculo carro4 = new Veiculo(mapa, 4, new Coordenadas(0, 19), new Coordenadas(7, 6));

        mapa.addVeiculo(carro0);
        mapa.addVeiculo(carro1);
        mapa.addVeiculo(carro2);
        mapa.addVeiculo(carro3);
        mapa.addVeiculo(carro4);
    }

}
