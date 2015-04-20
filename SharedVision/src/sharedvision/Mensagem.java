package sharedvision;

public class Mensagem {

    /**
     * Movimento = mensagem normal que simboliza o movimento PerdaDeControlo
     * =sinaliza que me torno num obstaculo Obstaculo = sinaliza que encontrou
     * um obstaculo ProximidadeDeIntersecao = sinaliza que vai para intersecao
     * PerigoColisaoFrontal = vai bater frontalmente
     */
    public enum TipoMensagem {

        Movimento, PerdaDeControlo, Obstaculo, ProximidadeDeIntersecao, PerigoColisaoFrontal;

        @Override
        public String toString() {
            switch (this) {
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
            }
            return "  falhou  ";
        }
    }

    private TipoMensagem tipoMensagem = TipoMensagem.Movimento;
    private Coordenadas perigoCoord;
    private Veiculo veiculo;

    public Mensagem(TipoMensagem tipoMensagem, Coordenadas perigoCoord, Veiculo veiculo) {
        this.tipoMensagem = tipoMensagem;
        this.perigoCoord = perigoCoord;
        this.veiculo = veiculo;
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

    @Override
    public String toString() {
        return "tipoMensagem=" + tipoMensagem + " perigoCoord=" + perigoCoord + " veiculo: " + veiculo;
    }
}
