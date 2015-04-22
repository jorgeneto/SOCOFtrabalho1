package sharedvision;

import java.util.ArrayList;
import java.util.Collections;

public class Astar {

    int[][] mapa;
    int vercao_mapa;
    int mapaSize;

    public Astar(int[][] mapa, int vercao_mapa) {
        this.mapa = mapa;
        this.vercao_mapa = vercao_mapa;
        this.mapaSize = mapa.length;
    }

    public ArrayList<Coordenadas> caminho(Coordenadas inicio, Coordenadas fim) {
        //System.out.println("fim " + fim);
        ArrayList<AstarMembro> caminhos = new ArrayList<AstarMembro>();
        AstarMembro primeiro = new AstarMembro(inicio, fim);
        caminhos.add(0, primeiro);
        return caminho(caminhos, fim);
    }

    private ArrayList<Coordenadas> caminho(ArrayList<AstarMembro> caminhos, Coordenadas fim) {
        while (true) {
//            System.out.println("size = " + caminhos.size());
            if (caminhos.size() < 1) {
                return null;
            }
            if (caminhos.get(0).lista.get(0).getX() == fim.getX() && caminhos.get(0).lista.get(0).getY() == fim.getY()) {
                ArrayList<Coordenadas> caminho = new ArrayList<Coordenadas>();
                for (Coordenadas cord : caminhos.get(0).lista) {
                    caminho.add(0, cord);
                }
                return caminho;
            } else {
                Coordenadas intermedio = caminhos.get(0).lista.get(0);
//                System.out.println("    atual " + intermedio + "    mapa=" + mapa[intermedio.getX()][intermedio.getY()] + "    switch=" + mapa[intermedio.getX()][intermedio.getY()] / 100);
                Coordenadas proximo;
                switch (mapa[intermedio.getX()][intermedio.getY()] / 100) {
                    case 1:
//                        System.out.println("        case 1");
                        proximo = new Coordenadas(intermedio.getX() - 1, intermedio.getY());
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!caminhos.get(0).lista.contains(proximo)) {
                                caminhos.get(0).lista.add(0, proximo);
                                caminhos.get(0).soma += 1;
                                caminhos.get(0).calcula(fim);
                            } else {
                                caminhos.remove(0);
                            }
                        }
                        break;
                    case 2:
//                        System.out.println("        case 2");
                        proximo = new Coordenadas(intermedio.getX(), intermedio.getY() - 1);
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!caminhos.get(0).lista.contains(proximo)) {
                                caminhos.get(0).lista.add(0, proximo);
                                caminhos.get(0).soma += 1;
                                caminhos.get(0).calcula(fim);
                            } else {
                                caminhos.remove(0);
                            }
                        }
                        break;
                    case 3:
//                        System.out.println("        case 3");
                        proximo = new Coordenadas(intermedio.getX() + 1, intermedio.getY());
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!caminhos.get(0).lista.contains(proximo)) {
                                caminhos.get(0).lista.add(0, proximo);
                                caminhos.get(0).soma += 1;
                                caminhos.get(0).calcula(fim);
                            } else {
                                caminhos.remove(0);
                            }
                        }
                        break;
                    case 4:
//                        System.out.println("        case 4");
                        proximo = new Coordenadas(intermedio.getX(), intermedio.getY() + 1);
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!caminhos.get(0).lista.contains(proximo)) {
                                caminhos.get(0).lista.add(0, proximo);
                                caminhos.get(0).soma += 1;
                                caminhos.get(0).calcula(fim);
                            } else {
                                caminhos.remove(0);
                            }
                        }
                        break;
                    case 5:
                        boolean adicionouPeloMenosUm = false;
//                        System.out.println("        case 5");
                        AstarMembro atual = new AstarMembro(caminhos.get(0));
                        proximo = new Coordenadas(intermedio.getX() - 1, intermedio.getY());
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!atual.lista.contains(proximo)) {
                                atual.lista.add(0, proximo);
                                atual.soma += 1;
                                atual.calcula(fim);
                                caminhos.add(atual);
                                adicionouPeloMenosUm = true;
                            }
                        }
                        atual = new AstarMembro(caminhos.get(0));
                        proximo = new Coordenadas(intermedio.getX(), intermedio.getY() - 1);
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!atual.lista.contains(proximo)) {
                                atual.lista.add(0, proximo);
                                atual.soma += 1;
                                atual.calcula(fim);
                                caminhos.add(atual);
                                adicionouPeloMenosUm = true;
                            }
                        }
                        atual = new AstarMembro(caminhos.get(0));
                        proximo = new Coordenadas(intermedio.getX() + 1, intermedio.getY());
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!atual.lista.contains(proximo)) {
                                atual.lista.add(0, proximo);
                                atual.soma += 1;
                                atual.calcula(fim);
                                caminhos.add(atual);
                                adicionouPeloMenosUm = true;
                            }
                        }
                        proximo = new Coordenadas(intermedio.getX(), intermedio.getY() + 1);
                        if (proximo.getX() > -1 && proximo.getX() < mapaSize && proximo.getY() > -1 && proximo.getY() < mapaSize) {
                            if (!caminhos.get(0).lista.contains(proximo)) {
                                caminhos.get(0).lista.add(0, proximo);
                                caminhos.get(0).soma += 1;
                                caminhos.get(0).calcula(fim);
                                adicionouPeloMenosUm = false;
                            }
                        }
                        if (adicionouPeloMenosUm) {
                            caminhos.remove(0);
                        }
                        break;
                    default:
                        //System.out.println("              apaga= " + caminhos.get(0));
                        caminhos.remove(0);
                        break;
                }

                Collections.sort(caminhos);
//                for (AstarMembro caminho : caminhos) {
//                    System.out.println("                " + caminho);
//                }
                //return caminho(caminhos, fim);
            }
        }
    }

    // serve para ver a versão do mapa
    public int getVercao_mapa() {
        return vercao_mapa;
    }

    // permite atualizar o mapa caso necessário
    public void novoMapa(int[][] mapa, int vercao_mapa) {
        this.mapa = mapa;
        this.vercao_mapa = vercao_mapa;
    }
}
