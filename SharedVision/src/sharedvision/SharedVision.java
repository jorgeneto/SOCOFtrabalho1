package sharedvision;

import Ajuda.Ajuda;
import java.util.ArrayList;

public class SharedVision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        System.out.println(mapa);

        mapa.vistagrafica();

        Astar teste = new Astar(mapa.getMapa(), 0);
        ArrayList<Coordenadas> caminho = teste.caminho(new Coordenadas(19, 3), new Coordenadas(19, 2));
        new Ajuda().printCaminho(caminho);

        caminho = teste.caminho(new Coordenadas(19, 19), new Coordenadas(0, 0));
        new Ajuda().printCaminho(caminho);
    }

}
