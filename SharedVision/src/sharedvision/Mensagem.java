package sharedvision;

public class Mensagem {

    public enum TipoMensagem {

        Terminou, Movimento, PerdaDeControlo, Obstaculo, ProximidadeDeIntersecao, PerigoColisaoFrontal, VeiculoNormal;

        @Override
        public String toString() {
            switch (this) {
                case Terminou:
                    return "Terminou";
                case Movimento:
                    return "Movimento";
                case PerdaDeControlo:
                    return "PerdaDeControlo";
                case Obstaculo:
                    return "Obstaculo";
                case ProximidadeDeIntersecao:
                    return "ProximidadeDeIntersecao";
                case PerigoColisaoFrontal:
                    return "PerigoColisaoFrontal";
                case VeiculoNormal:
                    return "VeiculoNormal";
            }
            return "  falhou  ";
        }
    }

    private TipoMensagem tipoMensagem = TipoMensagem.Movimento;
    private Coordenadas perigoCoord;
    private Veiculo veiculo;
    private Coordenadas atual;

    public Mensagem(TipoMensagem tipoMensagem, Coordenadas perigoCoord, Veiculo veiculo) {
        this.tipoMensagem = tipoMensagem;
        this.perigoCoord = perigoCoord;
        this.veiculo = veiculo;
    }

    public Mensagem(Coordenadas atual) {
        this.tipoMensagem = TipoMensagem.VeiculoNormal;
        this.atual = atual;
    }

    public TipoMensagem getTipoMensagem() {
        return tipoMensagem;
    }

    public Coordenadas getPerigoCoord() {
        return perigoCoord;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Coordenadas getAtual() {
        return atual;
    }

    @Override
    public String toString() {
        return "tipoMensagem=" + tipoMensagem + " perigoCoord=" + perigoCoord + " veiculo: " + veiculo;
    }
}
