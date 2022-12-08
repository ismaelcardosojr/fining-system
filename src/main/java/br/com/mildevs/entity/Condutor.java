package br.com.mildevs.entity;

import br.com.mildevs.LeituraValida;
import br.com.mildevs.dao.CondutorDAO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Condutor {

    @Id
    private int CNH;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "orgao_emissor", nullable = false)
    private String orgaoEmissor;

    @Column(nullable = false)
    private int pontuacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placa_veiculo_fk", referencedColumnName = "placa")
    private Veiculo veiculo;

    public Condutor obterInsercao(boolean chamadoPorVeiculo) {
        LeituraValida leitor = new LeituraValida();
        Condutor novoCondutor = new Condutor();

        limparConsole();
        System.out.println("(Inserção de Condutor) \n");

        int CNH = leitor.lerIntValido("Insira o CNH: ");
        novoCondutor.setCNH(CNH);

        LocalDate dataEmissao = leitor.lerDataEmissaoValida("Insira a data de emissão (AAAA-MM-DD): ");
        novoCondutor.setDataEmissao(dataEmissao);

        String orgaoEmissor = leitor.lerString("Insira o orgão emissor: ");
        novoCondutor.setOrgaoEmissor(orgaoEmissor);

        int pontuacao = leitor.lerIntValido("Insira a pontuação: ");
        novoCondutor.setPontuacao(pontuacao);

        if (chamadoPorVeiculo)
            return novoCondutor;

        setarVeiculo(novoCondutor);

        boolean insercaoValida = new CondutorDAO().persistirCondutor(novoCondutor);

        if (insercaoValida)
            System.out.println("\nCondutor inserido com sucesso!");
        else
            System.out.println("\nCondutor não pôde ser inserido.");

        return null;
    }

    private void setarVeiculo(Condutor novoCondutor) {
        LeituraValida leitor = new LeituraValida();

        String escolhaUsuario = leitor.lerEscolhaSNValida("\nDeseja vincular um veículo a este condutor? (S/N) - ");

        if (escolhaUsuario.equalsIgnoreCase("S")) {
            Veiculo veiculoCriado = new Veiculo().obterInsercao(true);
            novoCondutor.setVeiculo(veiculoCriado);
        }
    }

    public void obterRemocao() {
        LeituraValida leitor = new LeituraValida();
        CondutorDAO DAO = new CondutorDAO();

        limparConsole();
        System.out.println("(Remoção de Condutor) \n");

        int CNH = leitor.lerIntValido("Insira o CNH do condutor a ser removido: ");

        boolean remocaoValida = DAO.removerCondutor(CNH);

        if (remocaoValida)
            System.out.println("\nCondutor removido com sucesso!");
        else
            System.out.println("\nCondutor não pôde ser removido.");
    }

    public void obterConsulta() {
        LeituraValida leitor = new LeituraValida();
        CondutorDAO DAO = new CondutorDAO();

        limparConsole();
        System.out.println("(Consulta de Condutor) \n");

        int CNH = leitor.lerIntValido("Insira o CNH do condutor a ser consultado: ");

        Condutor condutorObtido = DAO.consultarCondutor(CNH);

        if (condutorObtido == null) {
            System.out.println("\nCondutor não encontrado.");
            return;
        }

        System.out.println("\nCondutor - " + condutorObtido.getCNH());
        System.out.println("Data de emissão - " + condutorObtido.getDataEmissao());
        System.out.println("Orgão emissor - " + condutorObtido.getOrgaoEmissor());
        System.out.println("Pontuação - " + condutorObtido.getPontuacao());

        if (condutorObtido.getVeiculo() == null)
            System.out.println("Veículo - Não vinculado");
        else
            System.out.println("Veículo - " + condutorObtido.getVeiculo().getPlaca());
    }

    public void obterLista() {
        CondutorDAO DAO = new CondutorDAO();
        List<Condutor> condutores = DAO.listarCondutores();

        limparConsole();

        if (condutores.size() == 0) {
            System.out.println("Sistema sem condutores.");
            return;
        }

        System.out.println("(Condutores) \n");

        for (Condutor esteCondutor : condutores) {
            System.out.println("Condutor - " + esteCondutor.getCNH());
        }
    }

    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int getCNH() {
        return CNH;
    }

    public void setCNH(int CNH) {
        this.CNH = CNH;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}