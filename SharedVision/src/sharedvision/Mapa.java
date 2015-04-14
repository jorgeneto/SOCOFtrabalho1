package sharedvision;

import Ajuda.Ajuda;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mapa {

    // Cima = 100.  Estrada = 00.  Molhado 1.     Estrada para cima molhada = 100 + 000 + 1 = 101
    private final int C_E = 100, E_E = 200, B_E = 300, D_E = 400, crE = 500;// Estrada
    private final int C_M = 101, E_M = 201, B_M = 301, D_M = 401, crM = 501;// Estrada Molhada
    private final int C_P = 110, E_P = 210, B_P = 310, D_P = 410, crP = 510;// Paralelo
    private final int C_W = 111, E_W = 211, B_W = 311, D_W = 411, crW = 511;// Paralelo Molhado
    private final int C_N = 102, E_N = 202, B_N = 302, D_N = 402, crN = 502;// Neve
    private final int C_G = 103, E_G = 203, B_G = 303, D_G = 403, crG = 503;// Gelo

    private int[][] mapa = {
        /*0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19*/
        {B_M, E_M, E_M, crM, crM, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M},// 0
        {B_M, E_M, E_M, crM, crM, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M, E_M},// 1
        {B_E, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000},// 2
        {B_E, 000, 000, 500, 500, E_E, E_E, E_E, crE, crE, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E},// 3
        {B_E, 000, 000, 500, 500, D_E, D_E, D_E, crE, crE, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, B_E, C_E},// 4
        {B_E, 000, 000, B_E, C_E, 000, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, B_E, C_E},// 5
        {B_E, 000, 000, B_E, C_E, 000, crE, crE, crE, crE, E_E, E_E, E_E, E_E, E_E, crE, crE, 000, B_E, C_E},// 6
        {B_E, 000, 000, B_E, C_E, 000, crE, 000, crE, crE, D_E, D_E, D_E, D_E, D_E, crE, crE, 000, B_E, C_E},// 7
        {B_E, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, B_E, C_E, 000, B_E, C_E},// 8
        {crE, D_E, D_E, crE, crE, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, E_E, C_E},// 9
        {crE, D_E, D_E, crE, crE, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, D_E, C_E},// 10
        {C_E, 000, 000, B_E, C_E, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, 000, B_P, C_P},// 11
        {C_E, 000, 000, B_E, C_E, 000, 000, B_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, 000, B_P, C_P},// 12
        {C_E, 000, 000, B_E, C_E, 000, 000, B_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, B_P, C_P, 000, B_P, C_P},// 13
        {C_E, 000, 000, B_E, C_E, 000, 000, B_P, C_P, 000, 000, 000, 000, 000, 000, B_P, C_P, 000, B_P, C_P},// 14
        {C_E, 000, 000, B_E, C_E, 000, 000, crP, crP, E_P, E_P, E_P, E_P, E_P, E_P, crP, crP, E_P, E_P, C_P},// 15
        {C_E, 000, 000, B_E, C_E, 000, 000, crP, crP, D_P, D_P, D_P, D_P, D_P, D_P, crP, crP, D_P, D_P, C_P},// 16
        {C_E, 000, 000, B_E, C_E, 000, 000, B_P, C_P, 000, 000, 000, 000, 000, 000, B_P, C_P, 000, 000, C_P},// 17
        {C_E, 000, 000, crE, crE, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, E_P, C_P, 000, 000, C_P},// 18
        {C_E, E_E, E_E, crE, crE, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, D_P, crP, D_P, D_P, C_P},// 19
    };

    private JLabel[][] mapaGrafico = new JLabel[20][20];
    private ArrayList<Veiculo> veiculos;

    public Mapa() {
        veiculos = new ArrayList<>();
    }

    public void addVeiculo(Veiculo v) {
        veiculos.add(v);

        new Thread(v).start();
    }

    public void redesenhar() {
        JLabel anterior, atual;
        for (Veiculo v : veiculos) {
            anterior = mapaGrafico[v.getAnterior().getX()][v.getAnterior().getY()];
            if (anterior.getIcon() != new ImageIcon("./img/carro_preto_desce.png")) {
                if (anterior.getIcon() != new ImageIcon("./img/carro_preto_sobe.png")) {
                    if (anterior.getIcon() != new ImageIcon("./img/carro_preto_direita.png")) {
                        if (anterior.getIcon() != new ImageIcon("./img/carro_preto_esquerda.png")) {
                            anterior.setIcon(escolheImagem(mapa[v.getAnterior().getX()][v.getAnterior().getY()]));
                            anterior.revalidate();
                            anterior.repaint();
                        }
                    }
                }
            }

            atual = mapaGrafico[v.getAtual().getX()][v.getAtual().getY()];
            if (v.getAtual().getX() > v.getAnterior().getX()) {
                atual.setIcon(new ImageIcon("./img/carro_preto_desce.png"));
            } else {
                atual.setIcon(new ImageIcon("./img/carro_preto_sobe.png"));
            }
            if (v.getAtual().getY() > v.getAnterior().getY()) {
                atual.setIcon(new ImageIcon("./img/carro_preto_direita.png"));
            }
            if (v.getAtual().getY() < v.getAnterior().getY()) {
                atual.setIcon(new ImageIcon("./img/carro_preto_esquerda.png"));
            }

            atual.revalidate();
            atual.repaint();
        }
    }

    public int[][] getMapa() {
        return mapa;
    }

    @Override
    public String toString() {
        String aux = "1- carrega com o segundo butao nesta couxa de output\n";
        aux += "2- carrega em setings\n";
        aux += "3- altera a font para courier new\n";
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

    private Icon escolheImagem(int mapa) {
        Icon img;
        switch (mapa) {
            case 0:
                img = new ImageIcon("./img/terra1.png");
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
            default:
                img = new ImageIcon("./img/estrada.png");
                break;
        }
        return img;
    }

    public void vistaGrafica() {
        //1. Create the frame.
        JFrame frame = new JFrame("Mapa");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        JPanel painel_butoes = new JPanel(new GridLayout(mapa.length, mapa[0].length));

        Icon img;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {

                img = escolheImagem(mapa[i][j]);

                mapaGrafico[i][j] = new JLabel(img);
                mapaGrafico[i][j].setPreferredSize(new Dimension(32, 32));

                painel_butoes.add(mapaGrafico[i][j]);

            }
        }
        frame.add(painel_butoes, BorderLayout.CENTER);

        //4. Size the frame.
        frame.pack();

        //5. Show it.
        frame.setVisible(true);
    }

}
