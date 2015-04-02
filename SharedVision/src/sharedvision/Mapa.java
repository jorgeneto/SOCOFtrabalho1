package sharedvision;

import Ajuda.Ajuda;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mapa {

    private final int C_E = 100, E_E = 200, B_E = 300, D_E = 400, crE = 500;// Estrada
    private final int C_P = 110, E_P = 210, B_P = 310, D_P = 410, crP = 510;// Paralelo
    private final int C_M = 101, E_M = 201, B_M = 301, D_M = 401, crM = 501;// Molhada
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

    public Mapa() {
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

                switch (mapa[i][j]) {
                    case 0:
                        aux += " ";
                        break;
                    case C_E:
                        aux += "↑";
                        break;
                    case E_E:
                        aux += "←";
                        break;
                    case B_E:
                        aux += "↓";
                        break;
                    case D_E:
                        aux += "→";
                        break;
                    case crE:
                        aux += "+";
                        break;

                    case C_P:
                        aux += "↑";
                        break;
                    case E_P:
                        aux += "←";
                        break;
                    case B_P:
                        aux += "↓";
                        break;
                    case D_P:
                        aux += "→";
                        break;
                    case crP:
                        aux += "+";
                        break;

                    case C_M:
                        aux += "↑";
                        break;
                    case E_M:
                        aux += "←";
                        break;
                    case B_M:
                        aux += "↓";
                        break;
                    case D_M:
                        aux += "→";
                        break;
                    case crM:
                        aux += "+";
                        break;
                }
            }
            aux += "\n";
        }
        return aux;
    }

    public void vistagrafica() {
        //1. Create the frame.
        JFrame frame = new JFrame("Mapa");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        JPanel painel_butoes = new JPanel(new GridLayout(mapa.length, mapa[0].length));

        Icon img;
        JLabel button;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                switch (mapa[i][j]) {
                    case 0:
                        img = new ImageIcon(getClass().getResource("zz_terra1.png"));
                        break;

                    case C_E:
                        img = new ImageIcon(getClass().getResource("zz_estrada_sobe.png"));
                        break;
                    case E_E:
                        img = new ImageIcon(getClass().getResource("zz_estrada_esquerda.png"));
                        break;
                    case B_E:
                        img = new ImageIcon(getClass().getResource("zz_estrada_desce.png"));
                        break;
                    case D_E:
                        img = new ImageIcon(getClass().getResource("zz_estrada_direita.png"));
                        break;
                    case crE:
                        img = new ImageIcon(getClass().getResource("zz_estrada.png"));
                        break;

                    case C_P:
                        img = new ImageIcon(getClass().getResource("zz_paralelo_sobe.png"));
                        break;
                    case E_P:
                        img = new ImageIcon(getClass().getResource("zz_paralelo_esquerda.png"));
                        break;
                    case B_P:
                        img = new ImageIcon(getClass().getResource("zz_paralelo_desce.png"));
                        break;
                    case D_P:
                        img = new ImageIcon(getClass().getResource("zz_paralelo_direita.png"));
                        break;
                    case crP:
                        img = new ImageIcon(getClass().getResource("zz_paralelo.png"));
                        break;

                    case C_M:
                        switch (new Ajuda().random_entre(0, 5)) {
                            case 1:
                                img = new ImageIcon(getClass().getResource("zz_molhada1_sobe.png"));
                                break;
                            case 2:
                                img = new ImageIcon(getClass().getResource("zz_molhada2_sobe.png"));
                                break;
                            case 3:
                                img = new ImageIcon(getClass().getResource("zz_molhada3_sobe.png"));
                                break;
                            case 4:
                                img = new ImageIcon(getClass().getResource("zz_molhada4_sobe.png"));
                                break;
                            default:
                                img = new ImageIcon(getClass().getResource("zz_estrada_sobe.png"));
                                break;
                        }
                        break;
                    case E_M:
                        switch (new Ajuda().random_entre(0, 5)) {
                            case 1:
                                img = new ImageIcon(getClass().getResource("zz_molhada1_esquerda.png"));
                                break;
                            case 2:
                                img = new ImageIcon(getClass().getResource("zz_molhada2_esquerda.png"));
                                break;
                            case 3:
                                img = new ImageIcon(getClass().getResource("zz_molhada3_esquerda.png"));
                                break;
                            case 4:
                                img = new ImageIcon(getClass().getResource("zz_molhada4_esquerda.png"));
                                break;
                            default:
                                img = new ImageIcon(getClass().getResource("zz_estrada_esquerda.png"));
                                break;
                        }
                        break;
                    case B_M:
                        switch (new Ajuda().random_entre(0, 5)) {
                            case 1:
                                img = new ImageIcon(getClass().getResource("zz_molhada1_desce.png"));
                                break;
                            case 2:
                                img = new ImageIcon(getClass().getResource("zz_molhada2_desce.png"));
                                break;
                            case 3:
                                img = new ImageIcon(getClass().getResource("zz_molhada3_desce.png"));
                                break;
                            case 4:
                                img = new ImageIcon(getClass().getResource("zz_molhada4_desce.png"));
                                break;
                            default:
                                img = new ImageIcon(getClass().getResource("zz_estrada_desce.png"));
                                break;
                        }
                        break;
                    case D_M:
                        switch (new Ajuda().random_entre(0, 5)) {
                            case 1:
                                img = new ImageIcon(getClass().getResource("zz_molhada1_direita.png"));
                                break;
                            case 2:
                                img = new ImageIcon(getClass().getResource("zz_molhada2_direita.png"));
                                break;
                            case 3:
                                img = new ImageIcon(getClass().getResource("zz_molhada3_direita.png"));
                                break;
                            case 4:
                                img = new ImageIcon(getClass().getResource("zz_molhada4_direita.png"));
                                break;
                            default:
                                img = new ImageIcon(getClass().getResource("zz_estrada_direita.png"));
                                break;
                        }
                        break;
                    case crM:
                        switch (new Ajuda().random_entre(0, 5)) {
                            case 1:
                                img = new ImageIcon(getClass().getResource("zz_molhada1.png"));
                                break;
                            case 2:
                                img = new ImageIcon(getClass().getResource("zz_molhada2.png"));
                                break;
                            case 3:
                                img = new ImageIcon(getClass().getResource("zz_molhada3.png"));
                                break;
                            case 4:
                                img = new ImageIcon(getClass().getResource("zz_molhada4.png"));
                                break;
                            default:
                                img = new ImageIcon(getClass().getResource("zz_estrada.png"));
                                break;
                        }
                        break;
                    default:
                        img = new ImageIcon(getClass().getResource("zz_estrada.png"));
                        break;
                }
                button = new JLabel(img);
                button.setPreferredSize(new Dimension(32, 32));

                painel_butoes.add(button);
            }
        }
        frame.add(painel_butoes, BorderLayout.CENTER);

        //4. Size the frame.
        frame.pack();

        //5. Show it.
        frame.setVisible(true);
    }

}
