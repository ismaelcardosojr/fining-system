package br.com.mildevs.entity;

import br.com.mildevs.LeituraValida;
import br.com.mildevs.dao.MultaDAO;
import br.com.mildevs.dao.VeiculoDAO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
public class Multa {

    @Id
    private int codigo;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private int pontuacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placa_veiculo_fk", referencedColumnName = "placa")
    private Veiculo veiculo;

    public Multa obterInsercao(boolean chamadoPorVeiculo) {
        LeituraValida leitor = new LeituraValida();
        Multa novaMulta = new Multa();

        limparConsole();
        System.out.println("(Inserção de Multa) \n");

        int codigo = leitor.lerIntValido("Insira o código: ");
        novaMulta.setCodigo(codigo);

        double preco = leitor.lerDoubleValido("Insira o preço: ");
        novaMulta.setPreco(preco);

        int pontuacao = leitor.lerIntValido("Insira a pontuação: ");
        novaMulta.setPontuacao(pontuacao);

        if (chamadoPorVeiculo)
            return novaMulta;

        boolean setagemValida = setarVeiculo(novaMulta);
        boolean insercaoValida = new MultaDAO().persistirMulta(novaMulta);

        if (setagemValida && insercaoValida)
            System.out.println("\nMulta inserida com sucesso!");
        else
            System.out.println("\nMulta não pôde ser inserida.");

        return null;
    }

    private boolean setarVeiculo(Multa novaMulta) {
        LeituraValida leitor = new LeituraValida();
        VeiculoDAO DAO = new VeiculoDAO();

        System.out.println("\nA multa precisa de um veículo existente.");
        String placa = leitor.lerString("\nInsira a placa desse veículo: ");

        Veiculo veiculoObtido = DAO.consultarVeiculo(placa);

        if (veiculoObtido == null)
            return false;

        List<Multa> multas = veiculoObtido.getMultas();
        multas.add(novaMulta);
        veiculoObtido.setMultas(multas);

        novaMulta.setVeiculo(veiculoObtido);

        boolean atualizacaoValida = DAO.atualizarVeiculo(veiculoObtido);

        if (atualizacaoValida)
            return true;
        return false;
    }

    public void obterRemocao() {
        LeituraValida leitor = new LeituraValida();
        MultaDAO DAO = new MultaDAO();

        limparConsole();
        System.out.println("(Remoção de Multa) \n");

        int codigo = leitor.lerIntValido("Insira o código da multa a ser removida: ");

        boolean remocaoValida = DAO.removerMulta(codigo);

        if (remocaoValida)
            System.out.println("\nMulta removida com sucesso!");
        else
            System.out.println("\nMulta não pôde ser removida.");
    }

    public void obterConsulta() {
        LeituraValida leitor = new LeituraValida();
        MultaDAO DAO = new MultaDAO();

        limparConsole();
        System.out.println("(Consulta de Multa) \n");

        int codigo = leitor.lerIntValido("Insira o código da multa a ser consultada: ");

        Multa multaObtida = DAO.consultarMulta(codigo);

        if (multaObtida == null) {
            System.out.println("\nMulta não encontrada.");
            return;
        }

        System.out.println("\nMulta - " + multaObtida.getCodigo());
        System.out.println("Preço - " + multaObtida.getPreco());
        System.out.println("Pontuação - " + multaObtida.getPontuacao());
        System.out.println("Veículo - " + multaObtida.getVeiculo().getPlaca());
    }

    public void obterLista() {
        MultaDAO DAO = new MultaDAO();
        List<Multa> multas = DAO.listarMultas();

        limparConsole();

        if (multas.size() == 0) {
            System.out.println("Sistema sem multas.");
            return;
        }

        System.out.println("(Multas) \n");

        for (Multa esteVeiculo : multas) {
            System.out.println("Multa - " + esteVeiculo.getCodigo());
        }
    }

    public void obterListaPorVeiculo(String placa, Veiculo veiculoEscolhido) {
        MultaDAO DAO = new MultaDAO();
        List<Multa> multas = DAO.listarMultasPorVeiculo(veiculoEscolhido);

        limparConsole();

        if (multas.size() == 0) {
            System.out.println("Veículo " + placa + " sem multas.");
            return;
        }

        System.out.println("(Multas do Veículo " + placa + ")");

        for (Multa esteVeiculo : multas) {
            System.out.println("\nMulta - " + esteVeiculo.getCodigo());
            System.out.println("Preço - " + esteVeiculo.getPreco());
            System.out.println("Pontuação - " + esteVeiculo.getPontuacao());
        }
    }

    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}