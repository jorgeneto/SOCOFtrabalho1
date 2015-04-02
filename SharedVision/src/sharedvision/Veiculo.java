package sharedvision;

public class Veiculo extends Thread {
    
    private Coordenadas coordenadas;
    private int inicio;
    private int fim;
    // (podem ser substituidos por um objecto)
    private int [][] mapa;
    //via
    
    public Veiculo(Coordenadas c, int inicio, int fim, int [][] mapa){
        this.coordenadas = c;
        this.inicio = inicio;
        this.fim = fim;
        this.mapa = mapa;
    } 
}
