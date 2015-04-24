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
    private final int C_O = 190, E_O = 290, B_O = 390, D_O = 490, crO = 590, Obs = 999;// Obstaculo

    private int id;
    private Coordenadas anterior;
    private Coordenadas atual;
    private Coordenadas fim;
    private ArrayList<Coordenadas> caminho;
    private ArrayList<Veiculo> veiculosProximos;

    private Mapa mapaObj;

    public Veiculo(Mapa mapaObj, int id, Coordenadas inicio, Coordenadas fim) {
        this.id = id;
        this.anterior = inicio;
        this.atual = inicio;
        this.fim = fim;
        this.mapaObj = mapaObj;

        this.veiculosProximos = new ArrayList<>();
        //this.addObserver(o);
    }

    public void adicionaObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void update(Observable o, Object m) {
        Mensagem mensagem = (Mensagem) m;
        switch (mensagem.getTipoMensagem()) {
            case Terminou:
                veiculosProximos.remove(mensagem.getVeiculo());
                break;
            case Movimento:
                if (Math.abs(mensagem.getPerigoCoord().getX() - atual.getX()) < 7) {
                    if (Math.abs(mensagem.getPerigoCoord().getY() - atual.getY()) < 7) {
                        System.out.println("Carro " + this + " recebe " + mensagem);
//                        for (int i = 0; i < caminho.size(); i++) {
//                            if (mensagem.getPerigoCoord().getX() == caminho.get(i).getX()) {
                        if (!veiculosProximos.contains(mensagem.getVeiculo())) {
                            System.out.println("Carro " + this + "adiciona Carro " + mensagem.getVeiculo());
                            mapaObj.printJanelaCarros(this, "Carro " + this + "adiciona Carro " + mensagem.getVeiculo());
                            veiculosProximos.add(mensagem.getVeiculo());
                        }
//                            }
//                        }
                    }
                }
                break;
            case PerdaDeControlo:
                break;
            case Obstaculo:
                if (Math.abs(mensagem.getPerigoCoord().getX() - atual.getX()) < 7) {
                    if (Math.abs(mensagem.getPerigoCoord().getY() - atual.getY()) < 7) {
                        System.out.println("Carro " + this + " recebe " + mensagem);
                        if (!veiculosProximos.contains(mensagem.getVeiculo())) {
                            System.out.println("Carro " + this + "adiciona Carro " + mensagem.getVeiculo());
                            mapaObj.printJanelaCarros(this, "Carro " + this + "adiciona Carro " + mensagem.getVeiculo());
                            veiculosProximos.add(mensagem.getVeiculo());
                        }
                    }
                }
                break;
            case ProximidadeDeIntersecao:
                break;
            case PerigoColisaoFrontal:
                break;
        }
    }

    public void enviaMensagem(Mensagem.TipoMensagem tipo, Coordenadas coordenadas) {
        this.setChanged();
        this.notifyObservers(new Mensagem(tipo, coordenadas, this));
    }

    private boolean procuraCaminho() {
        try {
            Astar astar = new Astar(mapaObj.getMapa(), 0);
            caminho = new ArrayList<>();
            caminho = astar.caminho(atual, fim);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ASTAR estourou por isso o caminho e de 0,4 ate 0,0");
            caminho = new ArrayList<>();
            caminho.add(new Coordenadas(0, 4));
            caminho.add(new Coordenadas(0, 3));
            caminho.add(new Coordenadas(0, 2));
            caminho.add(new Coordenadas(0, 1));
            caminho.add(new Coordenadas(0, 0));
            fim = new Coordenadas(0, 0);
            //return false;
        }
        if (caminho == null) {
            return false;
        } else {
            String aux = new Ajuda().printCaminho(caminho);
            mapaObj.printJanelaCarros(this, "caminho= " + aux);
            return true;
        }
    }

    public Coordenadas getAnterior() {
        return anterior;
    }

    public Coordenadas getAtual() {
        return atual;
    }

    public int getId() {
        return id;
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

        simulaVeiculoAndar();
    }

    public boolean posicaoValida(Coordenadas coord) {
        if (coord.getX() >= mapaObj.getMapa().length || coord.getY() >= mapaObj.getMapa().length || coord.getX() < 0 || coord.getY() < 0) {
            return false;
        }
        if (coord.getX() == anterior.getX() && coord.getY() == anterior.getY()) {
            return false;
        }

        switch ((mapaObj.getMapa()[coord.getX()][coord.getY()]) / 100) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    public void encontraObstaculo() {
        Coordenadas aux = new Coordenadas(atual.getX(), atual.getY());

        //System.err.println("    x" + atual.getX() + " y" + atual.getY());
        if (posicaoValida(atual)) {
            if (procuraCaminho()) {
                mapaObj.redesenhar(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        atual = new Coordenadas(aux.getX() + 1, aux.getY());
        //System.err.println("    x" + atual.getX() + " y" + atual.getY());
        if (posicaoValida(atual)) {
            if (procuraCaminho()) {
                mapaObj.redesenhar(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        atual = new Coordenadas(aux.getX() - 1, aux.getY());
        //System.err.println("    x" + atual.getX() + " y" + atual.getY());
        if (posicaoValida(atual)) {
            if (procuraCaminho()) {
                mapaObj.redesenhar(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        atual = new Coordenadas(aux.getX(), aux.getY() + 1);
        //System.err.println("    x" + atual.getX() + " y" + atual.getY());
        if (posicaoValida(atual)) {
            if (procuraCaminho()) {
                mapaObj.redesenhar(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        atual = new Coordenadas(aux.getX(), aux.getY() - 1);
        //System.err.println("    x" + atual.getX() + " y" + atual.getY());
        if (posicaoValida(atual)) {
            if (procuraCaminho()) {
                mapaObj.redesenhar(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        atual = new Coordenadas(aux.getX(), aux.getY());
        //System.err.println("Veiculo " + id + " FICOU SEM CAMINHOS");
        mapaObj.printJanelaCarros(this, "Veiculo " + id + " FICOU SEM CAMINHOS");
        enviaMensagem(Mensagem.TipoMensagem.Obstaculo, aux);
        enviaMensagem(Mensagem.TipoMensagem.Terminou, aux);
        mapaObj.removeVeiculo(this);
        // tornar o veiculo num obstaculo
        mapaObj.addObstaculo(aux);
    }

    private void simulaVeiculoAndar() {
        Coordenadas proximo;
        int distSeguranca = 0;

        while (!(atual.getX() == fim.getX() && atual.getY() == fim.getY())) {
            if (mapaObj.getEstadoParado()) {
                new Ajuda().sleepDuracao(1000);
            } else {
                // se estou numa estrada, paralelo, neve ou gelo
                distSeguranca = distSeguranca(mapaObj.getMapa()[atual.getX()][atual.getY()]);

                // verifica se tem veiculos no caminho
                boolean podeAndar = true;
                int menor = (caminho.size() < distSeguranca) ? caminho.size() : distSeguranca;
                for (int i = 0; i < menor; i++) {
                    proximo = caminho.get(i);

                    //Caso a próxima posição seja um obstáculo, então envia-se um aviso aos outros carros
                    if (i == 0) {
                        switch (mapaObj.getMapa()[proximo.getX()][proximo.getY()]) {
                            case C_O:
                            case E_O:
                            case B_O:
                            case D_O:
                            case crO:
                                mapaObj.addObstaculo(proximo);
                                enviaMensagem(Mensagem.TipoMensagem.Obstaculo, proximo);
                                mapaObj.printJanelaCarros(this, "Obstáculo encontrado em " + proximo);
                                podeAndar = false;
                                encontraObstaculo();
                                return;
                            case Obs:
                                encontraObstaculo();
                                return;
                        }
                    }

                    for (Veiculo veiculo : veiculosProximos) {
                        if (veiculo.getAtual().getX() == proximo.getX() && veiculo.getAtual().getY() == proximo.getY()) {
                            podeAndar = false;
                        }
                    }
                }
                if (podeAndar) {
                    // anda
                    anterior = atual;
                    atual = caminho.remove(0);
                    System.out.println("Carro " + id + " anda para " + atual);
                    mapaObj.redesenhar(this);
                    enviaMensagem(Mensagem.TipoMensagem.Movimento, anterior);
                    new Ajuda().sleep_entre(500, 1000);
                } else {
                    // nao pode andar
                    new Ajuda().sleep_entre(200, 400);
                }
            }
        }

        enviaMensagem(Mensagem.TipoMensagem.Terminou, atual);
        new Ajuda().sleep_entre(1000, 2000);
        mapaObj.removeVeiculo(this);
    }

    @Override
    public String toString() {
        return "id=" + id + " coordAtual=" + atual;
    }
}
