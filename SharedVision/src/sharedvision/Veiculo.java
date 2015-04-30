package sharedvision;

import Ajuda.Ajuda;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private ArrayList<VeiculoNormal> veiculosNormais;
    private boolean sentidoContrarioAtivado = false;
    private AtomicBoolean caminhoObstruido = new AtomicBoolean(false);
    private AtomicBoolean perdeuControlo = new AtomicBoolean(false);

    private Mapa mapaObj;

    public Veiculo(Mapa mapaObj, int id, Coordenadas inicio, Coordenadas fim, boolean sentidoContrarioAtivado) {
        this.id = id;
        this.anterior = inicio;
        this.atual = inicio;
        this.fim = fim;
        this.mapaObj = mapaObj;
        this.sentidoContrarioAtivado = sentidoContrarioAtivado;

        this.veiculosProximos = new ArrayList<>();
        this.veiculosNormais = new ArrayList<>();
    }

    public void addVeiculoNormal(VeiculoNormal veiculo) {
        if (!veiculosNormais.contains(veiculo)) {
            veiculosNormais.add(veiculo);
        }
    }

    public void removeVeiculoNormal(VeiculoNormal veiculo) {
        if (veiculosNormais.contains(veiculo)) {
            veiculosNormais.remove(veiculo);
        }
    }

    public synchronized Coordenadas getAnterior() {
        return new Coordenadas(anterior.getX(), anterior.getY());
    }

    public synchronized Coordenadas getAtual() {
        return new Coordenadas(atual.getX(), atual.getY());
    }

    public int getId() {
        return id;
    }

    public synchronized ArrayList<Coordenadas> getCaminho() {
        return new ArrayList<>(caminho);
    }

    public void perdaControlo() {
        perdeuControlo.set(true);

        mapaObj.printJanelaCarros(this, "Veiculo " + id + " perdeu o controlo");
        enviaMensagem(Mensagem.TipoMensagem.PerdaDeControlo, atual);
        veiculoTermina();
        // tornar o veiculo num obstaculo
        mapaObj.addObstaculo(atual);
    }

    public void adicionaObserver(Observer o) {
        this.addObserver(o);
    }

    public void enviaMensagem(Mensagem.TipoMensagem tipo, Coordenadas coordenadas) {
        for (int i = 0; i < veiculosProximos.size(); i++) {
            try {
                veiculosProximos.get(i).getId();
            } catch (Exception e) {
                veiculosProximos.remove(i);
            }
        }
        this.setChanged();
        this.notifyObservers(new Mensagem(tipo, coordenadas, this));
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
                if (Math.abs(mensagem.getPerigoCoord().getX() - atual.getX()) < 7) {
                    if (Math.abs(mensagem.getPerigoCoord().getY() - atual.getY()) < 7) {
                        mapaObj.printJanelaCarros(this, "Carro " + mensagem.getVeiculo() + " perdeu o controlo em " + mensagem.getPerigoCoord());
                    }
                }
                break;
            case Obstaculo:
                if (Math.abs(mensagem.getPerigoCoord().getX() - atual.getX()) < 7) {
                    if (Math.abs(mensagem.getPerigoCoord().getY() - atual.getY()) < 7) {
//                        System.out.println("Carro " + this + " recebe " + mensagem);
                        if (!veiculosProximos.contains(mensagem.getVeiculo())) {
                            System.out.println("Carro " + this + "adiciona Carro " + mensagem.getVeiculo());
                            mapaObj.printJanelaCarros(this, "Carro " + this + "adiciona Carro " + mensagem.getVeiculo());
                            veiculosProximos.add(mensagem.getVeiculo());
                        }
                        for (Coordenadas cord : caminho) {
                            if (cord.getX() == mensagem.getPerigoCoord().getX() && cord.getY() == mensagem.getPerigoCoord().getY()) {
                                mapaObj.printJanelaCarros(this, "Carro " + mensagem.getVeiculo() + " encontrou obstaculo em " + mensagem.getPerigoCoord());
                                caminhoObstruido.set(true);
                                mapaObj.printJanelaCarros(this, "caminhoObstruido = " + caminhoObstruido.get());
                            }
                        }
                    }
                }
                break;
            case ProximidadeDeIntersecao:
                break;
            case PerigoColisaoFrontal:
                if (Math.abs(mensagem.getPerigoCoord().getX() - atual.getX()) < 7) {
                    if (Math.abs(mensagem.getPerigoCoord().getY() - atual.getY()) < 7) {
                        mapaObj.printJanelaCarros(this, "Perigo de colisão frontal entre " + mensagem.getVeiculo() + " e " + this.id);
                    }
                }
                break;
            case VeiculoNormal:
                break;
        }
    }

    private boolean procuraCaminho() {
        try {
            Astar astar = new Astar(mapaObj.getMapa(), 0);
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
            System.out.println("caminho = [ null ]");
            return false;
        } else {
            new Ajuda().sleepDuracao(200);
            String aux = new Ajuda().printCaminho(caminho);
            mapaObj.printJanelaCarros(this, "caminho= " + aux);
            return true;
        }
    }

    private int distSeguranca(int mapa) {
        int distSeguranca = 0;
        switch (mapa) {
            case C_E:
            case E_E:
            case B_E:
            case D_E:
            case crE:
                distSeguranca = 2;
                break;
            case C_M:
            case E_M:
            case B_M:
            case D_M:
            case crM:
                distSeguranca = 3;
                break;
            case C_P:
            case E_P:
            case B_P:
            case D_P:
            case crP:
                distSeguranca = 4;
                break;
            case C_W:
            case E_W:
            case B_W:
            case D_W:
            case crW:
                distSeguranca = 5;
                break;
            case C_N:
            case E_N:
            case B_N:
            case D_N:
            case crN:
                distSeguranca = 6;
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
        if (!procuraCaminho()) {
            veiculoTermina();
        }

        if (sentidoContrarioAtivado) {
            mapaObj.printJanelaCarros(this, "sentido contrario ativado");
            ArrayList<Coordenadas> caminhoAux = new ArrayList<>();
            fim = new Coordenadas(caminho.get(0).getX(), caminho.get(0).getY());
            for (Coordenadas cord : caminho) {
                caminhoAux.add(0, cord);
            }
            caminho = caminhoAux;
            atual = new Coordenadas(caminho.get(0).getX(), caminho.get(0).getY());
            anterior = atual;
        }
        simulaVeiculoAndar();
    }

    public boolean posicaoValida(Coordenadas coord) {
        if (coord.getX() >= mapaObj.getMapa().length || coord.getY() >= mapaObj.getMapa().length || coord.getX() < 0 || coord.getY() < 0) {
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

    public boolean procuraCaminho(Coordenadas aux) {
        Coordenadas atual_aux = new Coordenadas(atual.getX(), atual.getY());
        atual = new Coordenadas(aux.getX(), aux.getY());
        boolean resultado = procuraCaminho();
        atual = new Coordenadas(atual_aux.getX(), atual_aux.getY());
        return resultado;
    }

    private ArrayList<Coordenadas> casasAverificar(Coordenadas coord) {
        ArrayList<Coordenadas> casas = new ArrayList<>();
        casas.add(coord);
        int mapa[][] = mapaObj.getMapa();
        Coordenadas aux;
        int i = 0;
        while (casas.size() < 4 && i < 10) {
            aux = new Coordenadas(casas.get(0).getX() + 1, casas.get(0).getY());
            if (aux.getX() >= 0 && aux.getX() < mapa.length) {
                if (mapa[aux.getX()][aux.getY()] / 100 == 1) {
                    casas.add(0, aux);
                }
            }
            aux = new Coordenadas(casas.get(0).getX() - 1, casas.get(0).getY());
            if (aux.getX() >= 0 && aux.getX() < mapa.length) {
                if (mapa[aux.getX()][aux.getY()] / 100 == 3) {
                    casas.add(0, aux);
                }
            }
            aux = new Coordenadas(casas.get(0).getX(), casas.get(0).getY() + 1);
            if (aux.getY() >= 0 && aux.getY() < mapa.length) {
                if (mapa[aux.getX()][aux.getY()] / 100 == 2) {
                    casas.add(0, aux);
                }
            }
            aux = new Coordenadas(casas.get(0).getX(), casas.get(0).getY() - 1);
            if (aux.getY() >= 0 && aux.getY() < mapa.length) {
                if (mapa[aux.getX()][aux.getY()] / 100 == 4) {
                    casas.add(0, aux);
                }
            }
            i++;
        }
//        System.out.println("casas =");
//        new Ajuda().printCaminho(casas);
        return casas;
    }

    public void esperaAteEstradaLivre(Coordenadas coordenadas) {
        boolean estradaLivre = false;
        ArrayList<Coordenadas> casas = casasAverificar(coordenadas);
        while (true) {
            estradaLivre = true;
            for (Veiculo veiculo : veiculosProximos) {
                for (Coordenadas coord : casas) {
                    if (coord.getX() == veiculo.getAtual().getX() && coord.getY() == veiculo.getAtual().getY()) {
                        estradaLivre = false;
                    }
                }
            }
            for (VeiculoNormal veiculo : veiculosNormais) {
                for (Coordenadas coord : casas) {
                    if (coord.getX() == veiculo.getAtual().getX() && coord.getY() == veiculo.getAtual().getY()) {
                        estradaLivre = false;
                    }
                }
            }
            if (estradaLivre) {
                return;
            } else {
                new Ajuda().sleepDuracao(200);
            }
        }
    }

    public void encontraObstaculo() {
        Coordenadas aux = new Coordenadas(atual.getX(), atual.getY());

        if (posicaoValida(aux)) {
            if (procuraCaminho(aux)) {
                anterior = new Coordenadas(atual.getX(), atual.getY());
                esperaAteEstradaLivre(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        aux = new Coordenadas(atual.getX() + 1, atual.getY());
        if (posicaoValida(aux)) {
            if (procuraCaminho(aux)) {
                caminho.add(0, aux);
                anterior = new Coordenadas(atual.getX(), atual.getY());
                esperaAteEstradaLivre(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        aux = new Coordenadas(atual.getX() - 1, atual.getY());
        if (posicaoValida(aux)) {
            if (procuraCaminho(aux)) {
                caminho.add(0, aux);
                anterior = new Coordenadas(atual.getX(), atual.getY());
                esperaAteEstradaLivre(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        aux = new Coordenadas(atual.getX(), atual.getY() + 1);
        if (posicaoValida(aux)) {
            if (procuraCaminho(aux)) {
                caminho.add(0, aux);
                anterior = new Coordenadas(atual.getX(), atual.getY());
                esperaAteEstradaLivre(aux);
                simulaVeiculoAndar();
                return;
            }
        }
        aux = new Coordenadas(atual.getX(), atual.getY() - 1);
        if (posicaoValida(aux)) {
            if (procuraCaminho(aux)) {
                caminho.add(0, aux);
                anterior = new Coordenadas(atual.getX(), atual.getY());
                esperaAteEstradaLivre(aux);
                simulaVeiculoAndar();
                return;
            }
        }

        System.err.println("Veiculo " + id + " FICOU SEM CAMINHOS");
        mapaObj.printJanelaCarros(this, "Veiculo " + id + " FICOU SEM CAMINHOS");
        enviaMensagem(Mensagem.TipoMensagem.Obstaculo, atual);
        veiculoTermina();
        // tornar o veiculo num obstaculo
        mapaObj.addObstaculo(atual);
    }

    private boolean desviaColisao(Coordenadas coord) {
        int mapa[][] = mapaObj.getMapa();
        int original = mapa[coord.getX()][coord.getY()];
        ArrayList<Coordenadas> caminhoAux;
        ArrayList<Coordenadas> caminhoAux_2;
        Coordenadas coordAux;
        Astar astar;

        // se as coordenadas do outro veiculo forem iguais as minhas finais
        if (coord.getX() == fim.getX() && coord.getY() == fim.getY()) {
            // se as coordenadas estao no mapa e nao no caminho anterior
            coordAux = new Coordenadas(fim.getX() + 1, fim.getY());
            if (coordAux.getX() >= 0 && coordAux.getX() < mapa.length && !caminho.contains(coordAux)) {
                mapa[coord.getX()][coord.getY()] = Obs;
                astar = new Astar(mapa, 0);
                caminhoAux = astar.caminho(atual, coordAux);

                if (caminhoAux != null) {
                    mapa[coord.getX()][coord.getY()] = original;
                    astar = new Astar(mapa, 0);
                    caminhoAux_2 = astar.caminho(caminhoAux.remove(caminhoAux.size() - 1), coordAux);
                    if (caminhoAux != null) {
                        caminhoAux.addAll(caminhoAux_2);
                        caminho = caminhoAux;
                        return true;
                    }
                }
            }
            coordAux = new Coordenadas(fim.getX() - 1, fim.getY());
            if (coordAux.getX() >= 0 && coordAux.getX() < mapa.length && !caminho.contains(coordAux)) {
                mapa[coord.getX()][coord.getY()] = Obs;
                astar = new Astar(mapa, 0);
                caminhoAux = astar.caminho(atual, coordAux);

                if (caminhoAux != null) {
                    mapa[coord.getX()][coord.getY()] = original;
                    astar = new Astar(mapa, 0);
                    caminhoAux_2 = astar.caminho(caminhoAux.remove(caminhoAux.size() - 1), coordAux);
                    if (caminhoAux != null) {
                        caminhoAux.addAll(caminhoAux_2);
                        caminho = caminhoAux;
                        return true;
                    }
                }
            }
            coordAux = new Coordenadas(fim.getX(), fim.getY() + 1);
            if (coordAux.getY() >= 0 && coordAux.getY() < mapa.length && !caminho.contains(coordAux)) {
                mapa[coord.getX()][coord.getY()] = Obs;
                astar = new Astar(mapa, 0);
                caminhoAux = astar.caminho(atual, coordAux);

                if (caminhoAux != null) {
                    mapa[coord.getX()][coord.getY()] = original;
                    astar = new Astar(mapa, 0);
                    caminhoAux_2 = astar.caminho(caminhoAux.remove(caminhoAux.size() - 1), coordAux);
                    if (caminhoAux != null) {
                        caminhoAux.addAll(caminhoAux_2);
                        caminho = caminhoAux;
                        return true;
                    }
                }
            }
            coordAux = new Coordenadas(fim.getX(), fim.getY() - 1);
            if (coordAux.getY() >= 0 && coordAux.getY() < mapa.length && !caminho.contains(coordAux)) {
                mapa[coord.getX()][coord.getY()] = Obs;
                astar = new Astar(mapa, 0);
                caminhoAux = astar.caminho(atual, coordAux);

                if (caminhoAux != null) {
                    mapa[coord.getX()][coord.getY()] = original;
                    astar = new Astar(mapa, 0);
                    caminhoAux_2 = astar.caminho(caminhoAux.remove(caminhoAux.size() - 1), coordAux);
                    if (caminhoAux != null) {
                        caminhoAux.addAll(caminhoAux_2);
                        caminho = caminhoAux;
                        return true;
                    }
                }
            }
        }

        mapa[coord.getX()][coord.getY()] = Obs;
        try {
            astar = new Astar(mapa, 0);
            caminhoAux = astar.caminho(atual, fim);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ASTAR estourou por isso o caminho e de 0,4 ate 0,0");
            caminhoAux = new ArrayList<>();
            caminhoAux.add(new Coordenadas(0, 4));
            caminhoAux.add(new Coordenadas(0, 3));
            caminhoAux.add(new Coordenadas(0, 2));
            caminhoAux.add(new Coordenadas(0, 1));
            caminhoAux.add(new Coordenadas(0, 0));
            fim = new Coordenadas(0, 0);
            //return false;
        }
        if (caminhoAux == null) {
            return false;
        } else {
            caminho = caminhoAux;
            new Ajuda().sleepDuracao(200);
            String aux = new Ajuda().printCaminho(caminhoAux);
            mapaObj.printJanelaCarros(this, "caminho= " + aux);
            return true;
        }
    }

    private void simulaVeiculoAndar() {
        mapaObj.redesenhar(this);
        Coordenadas proximo;
        int distSeguranca = 0;
        int perigo_colisao = 5;
        boolean podeAndar = false;

        while (!(atual.getX() == fim.getX() && atual.getY() == fim.getY())) {
            if (perdeuControlo.get()) {
                return;
            }
            if (mapaObj.getEstadoParado()) {
                new Ajuda().sleepDuracao(1000);
            } else {
                if (caminhoObstruido.get()) {
                    System.err.println(this + "caminho obstruido");
                    mapaObj.printJanelaCarros(this, "caminho obstruido");
                    if (procuraCaminho()) {
                        System.err.println(this + "caminho alternativo encontrado");
                        mapaObj.printJanelaCarros(this, "caminho alternativo encontrado");
                        caminhoObstruido.set(false);
                    } else {
                        System.err.println(this + " Fiquei sem caminhos");
                        mapaObj.printJanelaCarros(this, "Fiquei sem caminhos");
                        enviaMensagem(Mensagem.TipoMensagem.Obstaculo, atual);
                        veiculoTermina();
                        // tornar o veiculo num obstaculo
                        mapaObj.addObstaculo(atual);
                        return;
                    }
                }

                // se estou numa estrada, paralelo, neve ou gelo
                distSeguranca = distSeguranca(mapaObj.getMapa()[atual.getX()][atual.getY()]);

                // verifica se tem veiculos no caminho
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
                            case Obs:
                                if (mapaObj.getMapa()[fim.getX()][fim.getY()] == Obs) {
                                    mapaObj.printJanelaCarros(this, "Fiquei sem caminhos");
                                    veiculoTermina();
                                    mapaObj.addObstaculo(atual);
                                    return;
                                }
                                mapaObj.addObstaculo(proximo);
                                enviaMensagem(Mensagem.TipoMensagem.Obstaculo, proximo);
                                mapaObj.printJanelaCarros(this, "Obstáculo encontrado em " + proximo);
                                podeAndar = false;
                                encontraObstaculo();
                                return;
                        }
                        // se a minha proxima posicao é uma intersecao e se a atual nao é
                        if (mapaObj.getMapa()[proximo.getX()][proximo.getY()] / 100 == 5 && mapaObj.getMapa()[atual.getX()][atual.getY()] / 100 != 5) {
                            enviaMensagem(Mensagem.TipoMensagem.ProximidadeDeIntersecao, proximo);
                            mapaObj.printJanelaCarros(this, "Proximidade De Intersecao " + proximo);
                            mapaObj.lockIntersecao(proximo);
                            if (mapaObj.getMapa()[proximo.getX()][proximo.getY()] == Obs) {
                                podeAndar = false;
                            }
                            break;
                        }
                        // se a minha proxima posicao é uma intersecao e se a atual tambem é
                        if (mapaObj.getMapa()[proximo.getX()][proximo.getY()] / 100 == 5 && mapaObj.getMapa()[atual.getX()][atual.getY()] / 100 == 5) {
                        }
                        // se a minha proxima posicao nao é uma intersecao e a atual  é
                        if (mapaObj.getMapa()[proximo.getX()][proximo.getY()] / 100 != 5 && mapaObj.getMapa()[atual.getX()][atual.getY()] / 100 == 5) {
                            mapaObj.libertaIntersecao(atual);
                        }
                    }

                    for (Veiculo veiculo : veiculosProximos) {
                        if (veiculo.getAtual().getX() == proximo.getX() && veiculo.getAtual().getY() == proximo.getY()) {
                            podeAndar = false;
                            for (Coordenadas coordenada : veiculo.getCaminho()) {
                                if (coordenada.getX() == atual.getX() && coordenada.getY() == atual.getY()) {
                                    enviaMensagem(Mensagem.TipoMensagem.PerigoColisaoFrontal, atual);
                                    System.out.println("Carro " + id + " Um veiculo vai bater contra mim");
                                    mapaObj.printJanelaCarros(this, "Um veiculo vai bater contra mim");
                                    perigo_colisao--;
                                    if (!desviaColisao(veiculo.getAtual())) {
                                        podeAndar = false;
                                        if (perigo_colisao == 1) {
                                            mapaObj.addObstaculo(atual);
                                            veiculoTermina();
                                            return;
                                        }
                                    } else {

                                    }
                                    break;
                                }
                            }
                        }
                    }
                    int x_dif, y_dif;
                    for (VeiculoNormal veiculo : veiculosNormais) {
                        if (veiculo.getAtual().getX() == proximo.getX() && veiculo.getAtual().getY() == proximo.getY()) {
                            podeAndar = false;
                            for (Coordenadas coordenada : veiculo.getCaminho()) {
                                if (coordenada.getX() == atual.getX() && coordenada.getY() == atual.getY()) {
                                    System.out.println("Carro " + id + " Um veiculo normal vai bater contra mim AHAHAHA!!! :(");
                                    mapaObj.printJanelaCarros(this, "Um veiculo normal vai bater contra mim AHAHAHA!!! :(");
                                    if (!desviaColisao(veiculo.getAtual())) {
                                        mapaObj.addObstaculo(atual);
                                        veiculoTermina();
                                        return;
                                    } else {

                                    }
                                }
                            }
                            x_dif = veiculo.getAtual().getX() - veiculo.getAnterior().getX();
                            y_dif = veiculo.getAtual().getY() - veiculo.getAnterior().getY();
                            for (int j = 0; j < 4; j++) {
                                x_dif += x_dif;
                                y_dif += y_dif;
                                if (veiculo.getAtual().getX() + x_dif == atual.getX() && veiculo.getAtual().getY() + y_dif == atual.getY()) {
                                    System.out.println("Carro " + id + " Um veiculo normal vai bater contra mim AHAHAHA!!! :(");
                                    mapaObj.printJanelaCarros(this, "Um veiculo normal vai bater contra mim AHAHAHA!!! :(");
                                    if (!desviaColisao(veiculo.getAtual())) {
                                        mapaObj.addObstaculo(atual);
                                        veiculoTermina();
                                        return;
                                    } else {

                                    }
                                }
                            }
                        }
                    }
                }
                if (podeAndar) {
                    perigo_colisao = 5;
                    // anda
                    anterior = atual;
                    atual = caminho.remove(0);
                    System.out.println("Carro " + id + " anda para " + atual);
                    mapaObj.redesenhar(this);
                    enviaMensagem(Mensagem.TipoMensagem.Movimento, anterior);
                    new Ajuda().sleep_entre(500, 1000);
                } else {
                    // nao pode andar
                    enviaMensagem(Mensagem.TipoMensagem.Movimento, atual);
                    new Ajuda().sleep_entre(200, 400);
                }
                podeAndar = true;
            }
        }

        new Ajuda().sleep_entre(1000, 2000);
        veiculoTermina();
    }

    @Override
    public String toString() {
        return "id=" + id + " coordAtual=" + atual;
    }

    private void veiculoTermina() {
        // se a minha posicao é um cruzamento
        if (mapaObj.getMapa()[atual.getX()][atual.getY()] / 100 == 5) {
            mapaObj.libertaIntersecao(atual);
        }
        enviaMensagem(Mensagem.TipoMensagem.Terminou, atual);
        mapaObj.removeVeiculo(this);
    }

}
