package sharedvision;

import Ajuda.Ajuda;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Veiculo extends Observable implements Runnable, Observer {

    // Cima = 100.  Estrada = 00.  Molhado 1.     Estrada para cima molhada = 100 + 000 + 1 = 101
    private final int C_E = 100, E_E = 200, B_E = 300, D_E = 400, crE = 500;// Estrada
    private final int C_M = 101, E_M = 201, B_M = 301, D_M = 401, crM = 501;// Estrada Molhada
    private final int C_P = 110, E_P = 210, B_P = 310, D_P = 410, crP = 510;// Paralelo
    private final int C_W = 111, E_W = 211, B_W = 311, D_W = 411, crW = 511;// Paralelo Molhado
    private final int C_N = 102, E_N = 202, B_N = 302, D_N = 402, crN = 502;// Neve
    private final int C_G = 103, E_G = 203, B_G = 303, D_G = 403, crG = 503;// Gelo

    private int id;
    private Coordenadas anterior;
    private Coordenadas atual;
    private Coordenadas fim;
    private ArrayList<Coordenadas> caminho;
    private ArrayList<Veiculo> carrosProximos;

    private int[][] mapa;
    private Mapa mapaObj;

    public Veiculo(Mapa mapaObj, int id, Coordenadas inicio, Coordenadas fim, Observer o) {
        this.id = id;
        this.anterior = inicio;
        this.atual = inicio;
        this.fim = fim;
        this.mapa = mapaObj.getMapa();
        this.mapaObj = mapaObj;

        this.carrosProximos = new ArrayList<>();
        this.addObserver(o);
    }
    public Veiculo(Mapa mapaObj, int id, Coordenadas inicio, Coordenadas fim) {
        this.id = id;
        this.anterior = inicio;
        this.atual = inicio;
        this.fim = fim;
        this.mapa = mapaObj.getMapa();
        this.mapaObj = mapaObj;

        this.carrosProximos = new ArrayList<>();
       
    }
    private void procuraCaminho() {
        try {
            Astar astar = new Astar(mapa, 0);
            caminho = new ArrayList<>();
            caminho = astar.caminho(atual, fim);
            new Ajuda().printCaminho(caminho);
        } catch (Exception e) {
            //System.out.println("ASTAR estourou por isso o caminho e de 0,4 ate 0,0");
            caminho = new ArrayList<>();
            caminho.add(new Coordenadas(0, 4));
            caminho.add(new Coordenadas(0, 3));
            caminho.add(new Coordenadas(0, 2));
            caminho.add(new Coordenadas(0, 1));
            caminho.add(new Coordenadas(0, 0));
            fim = new Coordenadas(0, 0);
        }
    }

    public Coordenadas getAnterior() {
        return anterior;
    }

    public Coordenadas getAtual() {
        return atual;
    }

    @Override
    public void update(Observable o, Object arg) {
         System.out.println("Carro "+this+" acabou de correr");
    }
     public void done(){
            this.setChanged(); // protected method
            this.notifyObservers();
      }

    private int distSeguranca(int mapa) {
        int distSeguranca = 0;
        switch (mapa) {
            case C_E:
            case E_E:
            case B_E:
            case D_E:
            case crE:
                distSeguranca = 1;
                break;
            case C_M:
            case E_M:
            case B_M:
            case D_M:
            case crM:
                distSeguranca = 2;
                break;
            case C_P:
            case E_P:
            case B_P:
            case D_P:
            case crP:
                distSeguranca = 3;
                break;
            case C_W:
            case E_W:
            case B_W:
            case D_W:
            case crW:
                distSeguranca = 4;
                break;
            case C_N:
            case E_N:
            case B_N:
            case D_N:
            case crN:
                distSeguranca = 5;
                break;
            case C_G:
            case E_G:
            case B_G:
            case D_G:
            case crG:
                distSeguranca = 6;
                break;
        }
        return distSeguranca;
    }

    @Override
    public void run() {
        procuraCaminho();
        Coordenadas proximo;
        int distSeguranca = 0;

        while (!(atual.getX() == fim.getX() && atual.getY() == fim.getY())) {
            // se estou numa estrada, paralelo, neve ou gelo
            distSeguranca = distSeguranca(mapa[atual.getX()][atual.getY()]);

            boolean podeAndar = true;
            int menor = (caminho.size() < distSeguranca) ? caminho.size() : distSeguranca;
            for (int i = 0; i < menor; i++) {
                proximo = caminho.get(i);
                for (Veiculo carro : carrosProximos) {
                    if (carro.getAtual().getX() == proximo.getX() && carro.getAtual().getY() == proximo.getY()) {
                        podeAndar = false;
                    }
                }
            }
            if (podeAndar) {
                anterior = atual;
                atual = caminho.remove(0);
                System.err.println("Carro " + id + " anda para " + atual);
                mapaObj.redesenhar();
            } else {
                // nao pode andar
            }

            // simula o carro a andar
            new Ajuda().sleep_entre(500, 1000);
        }        
       
        this.done();
        new Ajuda().sleep_entre(1000, 2000);
        mapaObj.removeVeiculo(this);
        
    }

}
