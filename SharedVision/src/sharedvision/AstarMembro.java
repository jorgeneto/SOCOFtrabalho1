package sharedvision;

import java.util.ArrayList;

public class AstarMembro implements Comparable<AstarMembro> {

    public double funcao;
    public int soma;
    public ArrayList<Coordenadas> lista;

    public AstarMembro(Coordenadas inicio, Coordenadas fim) {
        this.soma = 0;
        this.lista = new ArrayList<Coordenadas>();
        lista.add(0, inicio);
        this.funcao = calcula(fim);
    }

    public AstarMembro(AstarMembro antigo) {
        this.funcao = new Double(antigo.funcao);
        this.soma = new Integer(antigo.soma);
        this.lista = new ArrayList<Coordenadas>(antigo.lista);
    }

    public double calcula(Coordenadas fim) {
        Coordenadas inicio = lista.get(0);
        funcao = soma + Math.sqrt(Math.pow(fim.getX() - inicio.getX(), 2) + Math.pow(fim.getY() - inicio.getY(), 2));
        return funcao;
    }

    @Override
    public int compareTo(AstarMembro obj) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (this.funcao < obj.funcao) {
            return BEFORE;
        }
        if (this.funcao > obj.funcao) {
            return AFTER;
        }
        return EQUAL;
    }

    @Override
    public String toString() {
        return "funcao=" + funcao + " soma=" + soma + " lista=" + lista;
    }
}
