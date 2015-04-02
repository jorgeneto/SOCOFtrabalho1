package Ajuda;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import sharedvision.Coordenadas;

public class Ajuda {

    public Ajuda() {
    }

    public int random_entre(int LimiteInferior, int LimiteSuperior) {
        if (LimiteInferior < LimiteSuperior) {
            return new Random().nextInt(LimiteSuperior - LimiteInferior) + LimiteInferior + 1;
        } else {
            return new Random().nextInt(LimiteInferior - LimiteSuperior) + LimiteSuperior + 1;
        }
    }

    public void sleep_entre(int LimiteInferior, int LimiteSuperior) {
        try {
            sleep(random_entre(LimiteInferior, LimiteSuperior));
        } catch (InterruptedException ex) {
        }
    }

    public void printCaminho(ArrayList<Coordenadas> caminho) {
        System.out.println("[");
        Coordenadas antigo = caminho.get(0);
        String aux = "";
        for (Coordenadas elemento : caminho) {
            if (antigo.getX() > elemento.getX()) {
                aux = "↑";
            } else if (antigo.getX() < elemento.getX()) {
                aux = "↓";
            } else if (antigo.getY() > elemento.getY()) {
                aux = "←";
            } else if (antigo.getY() < elemento.getY()) {
                aux = "→";
            }
            System.out.println(aux + " " + elemento);
            antigo = elemento;
        }
        System.out.println("]");
    }
}
