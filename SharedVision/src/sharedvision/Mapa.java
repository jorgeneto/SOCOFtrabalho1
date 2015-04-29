package sharedvision;

import Ajuda.Ajuda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Mapa implements KeyListener {

    // Cima = 100.  Estrada = 00.  Molhado 1.     Estrada para cima molhada = 100 + 000 + 1 = 101
    private final int C_E = 100, E_E = 200, B_E = 300, D_E = 400, crE = 500;// Estrada
    private final int C_M = 101, E_M = 201, B_M = 301, D_M = 401, crM = 501;// Estrada Molhada
    private final int C_P = 110, E_P = 210, B_P = 310, D_P = 410, crP = 510;// Paralelo
    private final int C_W = 111, E_W = 211, B_W = 311, D_W = 411, crW = 511;// Paralelo Molhado
    private final int C_N = 102, E_N = 202, B_N = 302, D_N = 402, crN = 502;// Neve
    private final int C_G = 103, E_G = 203, B_G = 303, D_G = 403, crG = 503;// Gelo
    private final int C_O = 190, E_O = 290, B_O = 390, D_O = 490, crO = 590, Obs = 999;// Obstaculo

    private int[][] mapa = {
        /*0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19*/
        {crN, E_N, E_N, crM, crM, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, crM},// 0
        {B_N, E_N, E_N, crM, crM, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M},// 1
        {B_N, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, C_E},// 2
        {B_N, 000, 000, crE, crE, E_E, E_E, E_E, crE, crE, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, crE},// 3
        {B_N, 000, 000, crE, crE, D_E, D_E, D_E, crE, crE, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, crE, C_E},// 4
        {B_N, 000, 000, B_E, C_E, 000, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, B_E, C_E},// 5
        {B_N, 000, 000, B_E, C_E, 000, crE, crE, crE, crE, E_E, E_E, E_E, E_E, E_E, crE, crE, 000, B_E, C_E},// 6
        {B_N, 000, 000, B_E, C_E, 000, crE, 000, crE, crE, D_E, D_E, D_E, D_E, D_E, crE, crE, 000, B_E, C_E},// 7
        {B_N, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, B_E, C_E, 000, B_E, C_E},// 8
        {crN, D_N, D_N, crE, crE, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, crE, E_E, E_E, C_E},// 9
        {crG, D_G, D_G, crE, crE, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, crE, D_E, crE, C_E},// 10
        {C_G, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, B_P, C_P},// 11
        {C_G, 000, 000, B_E, C_E, 000, 000, crW, E_W, E_W, E_W, E_W, E_W, E_W, E_W, E_W, crW, 000, B_P, C_P},// 12
        {C_G, 000, 000, B_E, C_E, 000, 000, B_W, D_W, D_W, D_W, D_W, D_W, D_W, D_W, B_W, C_W, 000, B_P, C_P},// 13
        {C_G, 000, 000, B_E, C_E, 000, 000, B_W, C_W, 000, 000, 000, 000, 000, 000, B_W, C_W, 000, B_P, C_P},// 14
        {C_G, 000, 000, B_E, C_E, 000, 000, crP, crP, E_P, E_P, E_P, E_P, E_P, E_P, crP, crP, E_P, crP, crP},// 15
        {C_G, 000, 000, B_E, C_E, 000, 000, crP, crP, D_P, D_P, D_P, D_P, D_P, D_P, crP, crP, D_P, crP, crP},// 16
        {C_G, 000, 000, B_E, C_E, 000, 000, B_P, C_P, 000, 000, 000, 000, 000, 000, B_P, C_P, 000, 000, C_P},// 17
        {C_G, 000, 000, crE, crE, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, C_P, 000, 000, C_P},// 18
        {crG, E_G, E_G, crE, crE, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, crP, D_P, D_P, crP},// 19
    };

    private JLabel[][] mapaGrafico = new JLabel[20][20];
    private AtomicBoolean[][] mapaLock = new AtomicBoolean[20][20];
    private ArrayList<Veiculo> veiculos;
    private ArrayList<VeiculoNormal> veiculosNormais;

    private boolean selecaoAtiva, selecaoFinal = false;
    private final Coordenadas coord1 = new Coordenadas(-1, -1);
    private final Coordenadas coordInicial = new Coordenadas(-1, -1);
    private final Coordenadas coordFinal = new Coordenadas(-1, -1);

    private JButton btn, btnEscolherMapaInicio, btnEscolherMapaFim;
    private JLabel label;
    private boolean estadoParado = false, telecomandado = true;

    public void dizPosDoVeiculo() {
        for (Veiculo v : veiculos) {
            System.err.println("ultimoIndice = " + veiculos.lastIndexOf(v));
        }
    }

    public Mapa() {
        veiculos = new ArrayList<>();
        veiculosNormais = new ArrayList<>();
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa.length; j++) {
                mapaLock[i][j] = new AtomicBoolean(false);
            }
        }
    }

    public synchronized int[][] getMapa() {
        int[][] aux = new int[mapa[0].length][mapa.length];
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                aux[i][j] = mapa[i][j];
            }
        }
        return aux;
    }

    public boolean getEstadoParado() {
        return estadoParado;
    }

    public synchronized void addVeiculoNormal(Coordenadas inicio, Coordenadas fim) {
        VeiculoNormal veiculo;
        if (veiculosNormais.size() == 0) {
            if (telecomandado) {
                veiculo = new VeiculoNormal(this, inicio, fim, true);
                controloVeiculoNormal();
            } else {
                veiculo = new VeiculoNormal(this, inicio, fim, false);
            }

            if (!veiculosNormais.contains(veiculo)) {
                veiculosNormais.add(veiculo);
            }
            for (Veiculo v : veiculos) {
                v.addVeiculoNormal(veiculo);
            }
            new Thread(veiculo).start();
        }
    }

    public synchronized void removeVeiculoNormal(VeiculoNormal veiculo) {
        JLabel atual = mapaGrafico[veiculo.getAtual().getX()][veiculo.getAtual().getY()];
        atual.setIcon(escolheImagem(mapa[veiculo.getAnterior().getX()][veiculo.getAnterior().getY()]));
        atual.revalidate();
        atual.repaint();

        if (veiculosNormais.contains(veiculo)) {
            veiculosNormais.remove(veiculo);
        }
        for (Veiculo v : veiculos) {
            v.removeVeiculoNormal(veiculo);
        }
    }

    public synchronized void addObstaculo(Coordenadas coordenadas) {
        mapa[coordenadas.getX()][coordenadas.getY()] = Obs;
        Icon img = new ImageIcon("");
        switch (new Ajuda().random_entre(0, 3)) {
            case 1:
                img = new ImageIcon("./img/obstaculo1.png");
                break;
            case 2:
                img = new ImageIcon("./img/obstaculo2.png");
                break;
            case 3:
                img = new ImageIcon("./img/obstaculo3.png");
                break;
        }
        mapaGrafico[coordenadas.getX()][coordenadas.getY()].setIcon(img);
        mapaGrafico[coordenadas.getX()][coordenadas.getY()].revalidate();
        mapaGrafico[coordenadas.getX()][coordenadas.getY()].repaint();
    }

    public synchronized void addVeiculo(int id, Coordenadas inicio, Coordenadas fim, boolean sentidoContrarioAtivado) {
        Veiculo novoVeiculo = new Veiculo(this, id, inicio, fim, sentidoContrarioAtivado);
        for (Veiculo veiculo : veiculos) {
            veiculo.adicionaObserver(novoVeiculo);
            novoVeiculo.adicionaObserver(veiculo);
        }
        veiculos.add(novoVeiculo);

        new Thread(novoVeiculo).start();
    }

    public synchronized void removeVeiculo(Veiculo v) {
        // remove os prints da interface
        stringOutput.remove(veiculos.lastIndexOf(v));
        // remove o veiculo
        JLabel atual = mapaGrafico[v.getAtual().getX()][v.getAtual().getY()];
        atual.setIcon(escolheImagem(mapa[v.getAnterior().getX()][v.getAnterior().getY()]));
        atual.revalidate();
        atual.repaint();
        veiculos.remove(v);

        for (Veiculo veiculo : veiculos) {
            veiculo.deleteObserver(v);
        }

        if (veiculos.isEmpty()) {
            //Texto para os botoes
            Object[] options = {"Sim",
                "Não"};
            int janela = JOptionPane.showOptionDialog(frame,
                    "Todos os veículos inteligentes terminaram, deseja fechar ?",
                    "Desligar",
                    JOptionPane.OK_OPTION,
                    JOptionPane.CANCEL_OPTION,
                    new ImageIcon("./img/obstaculo1.png"),
                    options,
                    options[1]);

            if (janela == JOptionPane.OK_OPTION) {
                exit(0);
            } else if (janela == JOptionPane.CANCEL_OPTION) {
                // Code to use when CANCEL is PRESSED.
                // Não faz nada
            }
        }
    }

    public synchronized boolean lockIntersecao(Coordenadas coord) {
        while (mapaLock[coord.getX()][coord.getY()].get() == true) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }

        ArrayList<Coordenadas> processadas = new ArrayList<>();
        ArrayList<Coordenadas> faltaprocessar = new ArrayList<>();
        faltaprocessar.add(coord);
        ArrayList<Coordenadas> validas = new ArrayList<>();
        Coordenadas aux, atual;
        while (faltaprocessar.size() > 0) {
            atual = new Coordenadas(faltaprocessar.get(0).getX(), faltaprocessar.get(0).getY());
            processadas.add(faltaprocessar.remove(0));
            switch (mapa[atual.getX()][atual.getY()]) {
                case crE:
                case crM:
                case crP:
                case crW:
                case crN:
                case crG:
                    validas.add(atual);
                    aux = new Coordenadas(atual.getX() + 1, atual.getY());
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    aux = new Coordenadas(atual.getX() - 1, atual.getY());
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    aux = new Coordenadas(atual.getX(), atual.getY() + 1);
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    aux = new Coordenadas(atual.getX(), atual.getY() - 1);
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    break;
            }
        }
        Collections.sort(validas);
        for (Coordenadas valida : validas) {
            mapaLock[valida.getX()][valida.getY()].set(true);
//            System.err.println("Adquire = " + valida);
        }
        notifyAll();
        return true;
    }

    public synchronized boolean libertaIntersecao(Coordenadas coord) {
        ArrayList<Coordenadas> processadas = new ArrayList<>();
        ArrayList<Coordenadas> faltaprocessar = new ArrayList<>();
        faltaprocessar.add(coord);
        ArrayList<Coordenadas> validas = new ArrayList<>();
        Coordenadas aux, atual;
        while (faltaprocessar.size() > 0) {
            atual = new Coordenadas(faltaprocessar.get(0).getX(), faltaprocessar.get(0).getY());
            processadas.add(faltaprocessar.remove(0));
            switch (mapa[atual.getX()][atual.getY()]) {
                case crE:
                case crM:
                case crP:
                case crW:
                case crN:
                case crG:
                case Obs:
                    validas.add(atual);
                    aux = new Coordenadas(atual.getX() + 1, atual.getY());
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    aux = new Coordenadas(atual.getX() - 1, atual.getY());
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    aux = new Coordenadas(atual.getX(), atual.getY() + 1);
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    aux = new Coordenadas(atual.getX(), atual.getY() - 1);
                    if (!faltaprocessar.contains(aux) && !processadas.contains(aux)) {
                        if (aux.getX() < mapa.length && aux.getY() < mapa.length && aux.getX() >= 0 && aux.getY() >= 0) {
                            faltaprocessar.add(aux);
                        }
                    }
                    break;
            }
        }
        Collections.sort(validas);
        for (Coordenadas valida : validas) {
            mapaLock[valida.getX()][valida.getY()].set(false);
//            System.err.println("Liberta = " + valida);
        }
        notifyAll();
        return true;
    }

    //____________________________________________ INTERFACE GRAFICA ____________________________________________
    public void redesenhar(Coordenadas coord) {
        JLabel anterior;

        //Repor icon que o carro ocupou antes
        anterior = mapaGrafico[coord.getX()][coord.getY()];
        anterior.setIcon(escolheImagem(mapa[coord.getX()][coord.getY()]));
        anterior.setText("");
        anterior.revalidate();
        anterior.repaint();
    }

    public void redesenhar(VeiculoNormal v) {
        JLabel anterior, atual;

        //Repor icon que o carro ocupou antes
        anterior = mapaGrafico[v.getAnterior().getX()][v.getAnterior().getY()];
        anterior.setIcon(escolheImagem(mapa[v.getAnterior().getX()][v.getAnterior().getY()]));
        anterior.setText("");
        anterior.revalidate();
        anterior.repaint();

        //Compara-se os novos X e Y com os anteriores de forma a perceber o novo sentido do carro
        //e redesenha-se o mapa consoante o mesmo
        atual = mapaGrafico[v.getAtual().getX()][v.getAtual().getY()];
        if (v.getAtual().getX() > v.getAnterior().getX()) {
            atual.setIcon(new ImageIcon("./img/carro_desce.png"));
        } else {
            atual.setIcon(new ImageIcon("./img/carro_sobe.png"));
        }
        if (v.getAtual().getY() > v.getAnterior().getY()) {
            atual.setIcon(new ImageIcon("./img/carro_direita.png"));
        }
        if (v.getAtual().getY() < v.getAnterior().getY()) {
            atual.setIcon(new ImageIcon("./img/carro_esquerda.png"));
        }

        atual.revalidate();
        atual.repaint();
    }

    public void redesenhar(Veiculo v) {
        JLabel anterior, atual;

        //Repor icon que o carro ocupou antes
        anterior = mapaGrafico[v.getAnterior().getX()][v.getAnterior().getY()];
        anterior.setIcon(escolheImagem(mapa[v.getAnterior().getX()][v.getAnterior().getY()]));
        anterior.setText("");
        anterior.revalidate();
        anterior.repaint();

        //Compara-se os novos X e Y com os anteriores de forma a perceber o novo sentido do carro
        //e redesenha-se o mapa consoante o mesmo
        atual = mapaGrafico[v.getAtual().getX()][v.getAtual().getY()];
        if (v.getAtual().getX() > v.getAnterior().getX()) {
            atual.setIcon(new ImageIcon("./img/carro_preto_desce.png"));
            atual.setText("" + v.getId());
        } else {
            atual.setIcon(new ImageIcon("./img/carro_preto_sobe.png"));
            atual.setText("" + v.getId());
        }
        if (v.getAtual().getY() > v.getAnterior().getY()) {
            atual.setIcon(new ImageIcon("./img/carro_preto_direita.png"));
            atual.setText("" + v.getId());
        }
        if (v.getAtual().getY() < v.getAnterior().getY()) {
            atual.setIcon(new ImageIcon("./img/carro_preto_esquerda.png"));
            atual.setText("" + v.getId());
        }
        atual.setHorizontalTextPosition(JLabel.CENTER);
        atual.setVerticalTextPosition(JLabel.CENTER);
        atual.setFont(new Font("Arial", Font.BOLD, 20));
        atual.setForeground(Color.red);

        atual.revalidate();
        atual.repaint();
    }

    @Override
    public String toString() {
        String aux = "Mapa:\n ";
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {

                switch (mapa[i][j] / 100) {
                    case 0:
                        aux += " ";
                        break;
                    case 1:
                        aux += "↑";
                        break;
                    case 2:
                        aux += "←";
                        break;
                    case 3:
                        aux += "↓";
                        break;
                    case 4:
                        aux += "→";
                        break;
                    case 5:
                        aux += "+";
                        break;
                }
            }
            aux += "\n";
        }
        return aux;
    }

    //Método usado para importar as imagens da pasta img no mapa pré-definido
    private Icon escolheImagem(int mapa) {
        Icon img;
        switch (mapa) {
            case 0:
                switch (new Ajuda().random_entre(0, 10)) {
                    case 1:
                        img = new ImageIcon("./img/terra_arvore1.png");
                        break;
                    case 2:
                        img = new ImageIcon("./img/terra_arvore1.png");
                        break;
                    case 3:
                        img = new ImageIcon("./img/terra_lago1.png");
                        break;
                    case 4:
                        img = new ImageIcon("./img/terra_casa1.png");
                        break;
                    default:
                        img = new ImageIcon("./img/terra.png");
                        break;
                }
                break;

            case C_E:
                img = new ImageIcon("./img/estrada_sobe.png");
                break;
            case E_E:
                img = new ImageIcon("./img/estrada_esquerda.png");
                break;
            case B_E:
                img = new ImageIcon("./img/estrada_desce.png");
                break;
            case D_E:
                img = new ImageIcon("./img/estrada_direita.png");
                break;
            case crE:
                img = new ImageIcon("./img/estrada.png");
                break;

            case C_P:
                img = new ImageIcon("./img/paralelo_sobe.png");
                break;
            case E_P:
                img = new ImageIcon("./img/paralelo_esquerda.png");
                break;
            case B_P:
                img = new ImageIcon("./img/paralelo_desce.png");
                break;
            case D_P:
                img = new ImageIcon("./img/paralelo_direita.png");
                break;
            case crP:
                img = new ImageIcon("./img/paralelo.png");
                break;
            case C_M:
                switch (new Ajuda().random_entre(0, 5)) {
                    case 1:
                        img = new ImageIcon("./img/molhada1_sobe.png");
                        break;
                    case 2:
                        img = new ImageIcon("./img/molhada2_sobe.png");
                        break;
                    case 3:
                        img = new ImageIcon("./img/molhada3_sobe.png");
                        break;
                    case 4:
                        img = new ImageIcon("./img/molhada4_sobe.png");
                        break;
                    default:
                        img = new ImageIcon("./img/estrada_sobe.png");
                        break;
                }
                break;
            case E_M:
                switch (new Ajuda().random_entre(0, 5)) {
                    case 1:
                        img = new ImageIcon("./img/molhada1_esquerda.png");
                        break;
                    case 2:
                        img = new ImageIcon("./img/molhada2_esquerda.png");
                        break;
                    case 3:
                        img = new ImageIcon("./img/molhada3_esquerda.png");
                        break;
                    case 4:
                        img = new ImageIcon("./img/molhada4_esquerda.png");
                        break;
                    default:
                        img = new ImageIcon("./img/estrada_esquerda.png");
                        break;
                }
                break;
            case B_M:
                switch (new Ajuda().random_entre(0, 5)) {
                    case 1:
                        img = new ImageIcon("./img/molhada1_desce.png");
                        break;
                    case 2:
                        img = new ImageIcon("./img/molhada2_desce.png");
                        break;
                    case 3:
                        img = new ImageIcon("./img/molhada3_desce.png");
                        break;
                    case 4:
                        img = new ImageIcon("./img/molhada4_desce.png");
                        break;
                    default:
                        img = new ImageIcon("./img/estrada_desce.png");
                        break;
                }
                break;
            case D_M:
                switch (new Ajuda().random_entre(0, 5)) {
                    case 1:
                        img = new ImageIcon("./img/molhada1_direita.png");
                        break;
                    case 2:
                        img = new ImageIcon("./img/molhada2_direita.png");
                        break;
                    case 3:
                        img = new ImageIcon("./img/molhada3_direita.png");
                        break;
                    case 4:
                        img = new ImageIcon("./img/molhada4_direita.png");
                        break;
                    default:
                        img = new ImageIcon("./img/estrada_direita.png");
                        break;
                }
                break;
            case crM:
                switch (new Ajuda().random_entre(0, 5)) {
                    case 1:
                        img = new ImageIcon("./img/molhada1.png");
                        break;
                    case 2:
                        img = new ImageIcon("./img/molhada2.png");
                        break;
                    case 3:
                        img = new ImageIcon("./img/molhada3.png");
                        break;
                    case 4:
                        img = new ImageIcon("./img/molhada4.png");
                        break;
                    default:
                        img = new ImageIcon("./img/estrada.png");
                        break;
                }
                break;

            case C_W:
                img = new ImageIcon("./img/paralelo_molhado_sobe.png");
                break;
            case E_W:
                img = new ImageIcon("./img/paralelo_molhado_esquerda.png");
                break;
            case B_W:
                img = new ImageIcon("./img/paralelo_molhado_desce.png");
                break;
            case D_W:
                img = new ImageIcon("./img/paralelo_molhado_direita.png");
                break;
            case crW:
                img = new ImageIcon("./img/paralelo_molhado.png");
                break;

            case C_N:
                img = new ImageIcon("./img/neve_sobe.png");
                break;
            case E_N:
                img = new ImageIcon("./img/neve_esquerda.png");
                break;
            case B_N:
                img = new ImageIcon("./img/neve_desce.png");
                break;
            case D_N:
                img = new ImageIcon("./img/neve_direita.png");
                break;
            case crN:
                img = new ImageIcon("./img/neve.png");
                break;

            case C_G:
                img = new ImageIcon("./img/gelo_sobe.png");
                break;
            case E_G:
                img = new ImageIcon("./img/gelo_esquerda.png");
                break;
            case B_G:
                img = new ImageIcon("./img/gelo_desce.png");
                break;
            case D_G:
                img = new ImageIcon("./img/gelo_direita.png");
                break;
            case crG:
                img = new ImageIcon("./img/gelo.png");
                break;

            // ISTO É PARA APAGAR
            case C_O:
            case E_O:
            case B_O:
            case D_O:
                img = new ImageIcon("./img/obstaculo1.png");
                break;
            case crO:
                img = new ImageIcon("./img/obstaculo2.png");
                break;
            case Obs:
                img = new ImageIcon("./img/obstaculo3.png");
                break;

            default:
                img = new ImageIcon("./img/estrada.png");
                break;
        }
        return img;
    }

    //Método que gera o mapa
    public void vistaGrafica() {
        //1. Cria a janela
        JFrame frame = new JFrame("Mapa");
        //2. Define que o programa acaba quando a janela feixa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //3. Serve de contentor para o mapa
        JPanel painel_butoes = new JPanel(new GridLayout(mapa.length, mapa[0].length));

        Icon img;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {

                img = escolheImagem(mapa[i][j]);

                mapaGrafico[i][j] = new JLabel(img);
                mapaGrafico[i][j].setPreferredSize(new Dimension(32, 32));
                mapaGrafico[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        for (int i = 0; i < mapa.length; i++) {
                            for (int j = 0; j < mapa[0].length; j++) {
                                if (mapaGrafico[i][j].equals(e.getComponent())) {
                                    if (selecaoFinal) {
                                        coordFinal.setCoordenadas(i, j);
                                        nXf.setText("" + coordFinal.getX());
                                        nYf.setText("" + coordFinal.getY());
                                        //System.err.println("FIM " + coordFinal.getX() + " " + coordFinal.getY());
                                        selecaoFinal = false;
                                        return;

                                    } else if (selecaoAtiva) {
                                        coordInicial.setCoordenadas(i, j);
                                        coord1.setCoordenadas(i, j);
                                        nXi.setText("" + coordInicial.getX());
                                        nYi.setText("" + coordInicial.getY());
                                        //System.err.println("INICIO " + coordInicial.getX() + " " + coordInicial.getY());
                                        selecaoAtiva = false;
                                        return;
                                    } else {
                                        coord1.setCoordenadas(i, j);
                                    }

                                }
                            }
                        }

                        //Texto para os botoes
                        Object[] options = {"Sim",
                            "Não"};
                        int janela = JOptionPane.showOptionDialog(frame,
                                "Deseja colocar um obstáculo em X=" + coord1.getX() + " Y=" + coord1.getY() + " ?",
                                "Obstáculo",
                                JOptionPane.OK_OPTION,
                                JOptionPane.CANCEL_OPTION,
                                new ImageIcon("./img/obstaculo1.png"),
                                options,
                                options[1]);

                        if (janela == JOptionPane.OK_OPTION) {
                            switch (mapa[coord1.getX()][coord1.getY()] / 100) {
                                case 1:
                                    mapa[coord1.getX()][coord1.getY()] = C_O;
                                    mapaGrafico[coord1.getX()][coord1.getY()].setIcon(new ImageIcon("./img/obstaculo_racha.png"));
                                    break;
                                case 2:
                                    mapa[coord1.getX()][coord1.getY()] = E_O;
                                    mapaGrafico[coord1.getX()][coord1.getY()].setIcon(new ImageIcon("./img/obstaculo_racha.png"));
                                    break;
                                case 3:
                                    mapa[coord1.getX()][coord1.getY()] = D_O;
                                    mapaGrafico[coord1.getX()][coord1.getY()].setIcon(new ImageIcon("./img/obstaculo_racha.png"));
                                    break;
                                case 4:
                                    mapa[coord1.getX()][coord1.getY()] = D_O;
                                    mapaGrafico[coord1.getX()][coord1.getY()].setIcon(new ImageIcon("./img/obstaculo_racha.png"));
                                    break;
                                case 5:
                                    mapa[coord1.getX()][coord1.getY()] = crO;
                                    mapaGrafico[coord1.getX()][coord1.getY()].setIcon(new ImageIcon("./img/obstaculo_racha.png"));
                                    break;
                            }
                            mapaGrafico[coord1.getX()][coord1.getY()].revalidate();
                            mapaGrafico[coord1.getX()][coord1.getY()].repaint();
                        } else if (janela == JOptionPane.CANCEL_OPTION) {
                            // Code to use when CANCEL is PRESSED.
                            // Não faz nada
                        }
                    }
                });

                painel_butoes.add(mapaGrafico[i][j]);

            }
        }
        frame.add(painel_butoes, BorderLayout.CENTER);
        frame.add(new JLabel("Clique na estrada para poder adicionar obstáculos"), BorderLayout.NORTH);

        //4. Determina o tamanho automatico da janela
        frame.pack();
        //5. Mostra a janela
        frame.setVisible(true);
    }

    private JTextField nID, nXi, nYi, nXf, nYf;
    private ArrayList<JTextArea> stringOutput = new ArrayList<>();
    private ArrayList<JPanel> panelPrint = new ArrayList<>();
    private JFrame frame;
    private boolean sentidoInvertido = false;
    private JButton btnAddNormal, flag, btnControloManual;
    private JPanel painel_veiculo, painel_input, painel_output, painel_principal, painel_criacarros;
    private ArrayList<JButton> btnControlo = new ArrayList<>();

    //Método que gera o mapa
    public void vistaCarros() {

        //1. Cria a janela
        frame = new JFrame("Carros");
        frame.setLocation(32 * mapa.length + 4, 0);
        //2. Define que o programa acaba quando a janela feixa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //3. Serve de contentor para o mapa
        painel_principal = new JPanel();
        painel_principal.setLayout(new BoxLayout(painel_principal, BoxLayout.PAGE_AXIS));

        painel_veiculo = new JPanel(new BorderLayout());

        painel_input = new JPanel(new GridLayout(4, 1));

        btn = new JButton("Adicionar veículo inteligente");
        btn.addActionListener((ActionEvent e) -> {
            if (!nID.getText().equals("") && !nXi.getText().equals("") && !nYi.getText().equals("") && !nXf.getText().equals("") && !nYf.getText().equals("")) {
                addVeiculo(Integer.parseInt(nID.getText()), new Coordenadas(Integer.parseInt(nXi.getText()), Integer.parseInt(nYi.getText())), new Coordenadas(Integer.parseInt(nXf.getText()), Integer.parseInt(nYf.getText())), sentidoInvertido);
                nXi.setText("");
                nXf.setText("");
                nYi.setText("");
                nYf.setText("");
            }
        });
        painel_input.add(btn);

        btnAddNormal = new JButton("Adicionar veículo normal");
        btnAddNormal.addActionListener((ActionEvent e) -> {
            if (!nXi.getText().equals("") && !nYi.getText().equals("") && !nXf.getText().equals("") && !nYf.getText().equals("")) {
                addVeiculoNormal(new Coordenadas(Integer.parseInt(nXi.getText()), Integer.parseInt(nYi.getText())), new Coordenadas(Integer.parseInt(nXf.getText()), Integer.parseInt(nYf.getText())));
                nXi.setText("");
                nXf.setText("");
                nYi.setText("");
                nYf.setText("");
            }
        });
        painel_input.add(btnAddNormal);

        btn = new JButton("Parar Veiculos");
        btn.addActionListener((ActionEvent e) -> {
            if (estadoParado) {
                ((JButton) e.getSource()).setText("Parar Veiculos");
                estadoParado = false;
            } else {
                ((JButton) e.getSource()).setText("Continuar Veiculos");
                estadoParado = true;
            }
        });
        painel_input.add(btn);
        painel_veiculo.add(painel_input, BorderLayout.WEST);

        btnEscolherMapaInicio = new JButton("Ponto inicial");
        btnEscolherMapaInicio.addActionListener((ActionEvent e) -> {
            selecaoAtiva = true;
        });
        btnEscolherMapaFim = new JButton("Ponto final");
        btnEscolherMapaFim.addActionListener((ActionEvent e) -> {
            selecaoFinal = true;
        });

        painel_output = new JPanel(new FlowLayout());
        painel_output.add(new JLabel("ID"));
        painel_output.add(nID = new JTextField(2));
        painel_output.add(btnEscolherMapaInicio);
        painel_output.add(new JLabel("X"));
        painel_output.add(nXi = new JTextField(2));
        painel_output.add(new JLabel("Y"));
        painel_output.add(nYi = new JTextField(2));
        painel_output.add(btnEscolherMapaFim);
        painel_output.add(new JLabel("X"));
        painel_output.add(nXf = new JTextField(2));
        painel_output.add(new JLabel("Y"));
        painel_output.add(nYf = new JTextField(2));
        painel_output.add(flag = new JButton("Normal"));
        flag.addActionListener((ActionEvent e) -> {
            if (sentidoInvertido) {
                ((JButton) e.getSource()).setText("Normal");
                sentidoInvertido = false;
            } else {
                ((JButton) e.getSource()).setText("Inverter");
                sentidoInvertido = true;
            }
        });
        painel_criacarros = new JPanel(new GridLayout(2, 1));
        painel_criacarros.add(painel_output);

        JPanel painel_aux = new JPanel((new FlowLayout()));
        painel_aux.add(btnControloManual = new JButton("Controlo Manual Ligado"));
        btnControloManual.addActionListener((ActionEvent e) -> {
            if (telecomandado) {
                ((JButton) e.getSource()).setText("Controlo Manual Desligado");
                telecomandado = false;
            } else {
                ((JButton) e.getSource()).setText("Controlo Manual Ligado");
                telecomandado = true;
            }
        });
        painel_criacarros.add(painel_aux);
        painel_veiculo.add(painel_criacarros, BorderLayout.CENTER);
        painel_principal.add(painel_veiculo);

        for (Veiculo v : veiculos) {
            JButton b = new JButton("Perder o controlo");
            btnControlo.add(b);
            b.addActionListener((ActionEvent e) -> {
                v.perdaControlo();
            });
        }

        for (int i = 0; i < veiculos.size(); i++) {
            painel_veiculo = new JPanel(new BorderLayout());

            painel_input = new JPanel(new GridLayout(2, 1));
            label = new JLabel("Veiculo " + veiculos.get(i).getId());
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.red);
            painel_input.add(label);
            painel_input.add(btnControlo.get(i));
//            painel_input.add(new JButton("Perder o controlo"));
            painel_veiculo.add(painel_input, BorderLayout.WEST);

            panelPrint.add(new JPanel(new FlowLayout()));
            stringOutput.add(new JTextArea("Mensagems do veiculo " + i));
            stringOutput.get(i).setEditable(false);
            stringOutput.get(i).setBackground(new Color(238, 238, 238));
            panelPrint.get(i).add(stringOutput.get(i));
            painel_veiculo.add(panelPrint.get(i), BorderLayout.CENTER);

            painel_principal.add(painel_veiculo);
        }
        frame.add(painel_principal, BorderLayout.CENTER);

        //4. Determina o tamanho automatico da janela
        frame.pack();
        //5. Mostra a janela
        frame.setVisible(true);
    }

    public void printJanelaCarros(Veiculo v, String print) {
        int id_veiculo = veiculos.lastIndexOf(v);
        if (id_veiculo > -1 && id_veiculo < stringOutput.size()) {
            ArrayList<String> list = new ArrayList<>();
            //Partir o array
            String[] splitArray = stringOutput.get(id_veiculo).getText().split("\n");
            String aux = "";

            //Converter array em lista
            for (String s : splitArray) {
                list.add(s);
            }

            //Se tivermos + mais que 4 mensagens para aparecer
            while (list.size() > 4) {
                //Remover 1º linha (mensagem mais desatualizada)
                list.remove(0);
            }
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size()) {
                    aux += list.get(i) + "\n";
                }
            }
            while (print.length() > 90) {
                aux += print.substring(0, 90) + "\n";
                print = print.substring(40, print.length());
            }
            aux += print + "\n";
            //Metemos o texto sem o 1º elemento + o texto a imprimir na Text Area
            stringOutput.get(id_veiculo).setText(aux);
            stringOutput.get(id_veiculo).revalidate();
            stringOutput.get(id_veiculo).repaint();
        }
        frame.pack();
        frame.revalidate();
        frame.repaint();
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    private void controloVeiculoNormal() {

        JFrame frame = new JFrame("Controlo Manual");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel jp = new JPanel();

        JButton buttonUp = new JButton("↑");
        buttonUp.addKeyListener(this);
        buttonUp.addActionListener((ActionEvent e) -> {
            keyPressed(new KeyEvent(buttonUp, 1, 21, 10, KeyEvent.VK_UP));
        });
        JButton buttonLeft = new JButton("←");
        buttonLeft.addKeyListener(this);
        buttonLeft.addActionListener((ActionEvent e) -> {
            keyPressed(new KeyEvent(buttonLeft, 1, 21, 10, KeyEvent.VK_LEFT));
        });
        JButton buttonDown = new JButton("↓");
        buttonDown.addKeyListener(this);
        buttonDown.addActionListener((ActionEvent e) -> {
            keyPressed(new KeyEvent(buttonDown, 1, 21, 10, KeyEvent.VK_DOWN));
        });
        JButton buttonRight = new JButton("→");
        buttonRight.addKeyListener(this);
        buttonRight.addActionListener((ActionEvent e) -> {
            keyPressed(new KeyEvent(buttonRight, 1, 21, 10, KeyEvent.VK_RIGHT));
        });

        jp.add(buttonUp);
        jp.add(buttonLeft);
        jp.add(buttonDown);
        jp.add(buttonRight);

        frame.add(jp);

        frame.pack();
        //5. Mostra a janela
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (veiculosNormais.size() > 0) {
            if (e.getKeyCode() == e.VK_UP) {
                veiculosNormais.get(0).proximaPosicao(1);
            }
            if (e.getKeyCode() == e.VK_LEFT) {
                veiculosNormais.get(0).proximaPosicao(2);
            }
            if (e.getKeyCode() == e.VK_DOWN) {
                veiculosNormais.get(0).proximaPosicao(3);
            }
            if (e.getKeyCode() == e.VK_RIGHT) {
                veiculosNormais.get(0).proximaPosicao(4);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
}
