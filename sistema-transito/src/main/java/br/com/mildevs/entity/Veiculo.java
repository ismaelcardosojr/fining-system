package br.com.mildevs.entity;

import br.com.mildevs.LeituraValida;
import br.com.mildevs.dao.CondutorDAO;
import br.com.mildevs.dao.VeiculoDAO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class Veiculo {

    @Id
    private String placa;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String marca;

    @OneToOne(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private Condutor condutor;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Multa> multas;

    public Veiculo obterInsercao(boolean chamadoPorCondutorOuMulta) {
        LeituraValida leitor = new LeituraValida();
        Veiculo novoVeiculo = new Veiculo();

        limparConsole();
        System.out.println("(Inserção de Veículo) \n");

        String placa = leitor.lerString("Insira a placa: ");
        novoVeiculo.setPlaca(placa);

        int ano = leitor.lerIntValido("Insira o ano: ");
        novoVeiculo.setAno(ano);

        String modelo = leitor.lerString("Insira o modelo: ");
        novoVeiculo.setModelo(modelo);

        String marca = leitor.lerString("Insira a marca: ");
        novoVeiculo.setMarca(marca);

        if (chamadoPorCondutorOuMulta)
            return novoVeiculo;

        boolean setagemValida = setarCondutor(novoVeiculo);

        if (setagemValida)
            System.out.println("\nVeículo inserido com sucesso!");
        else
            System.out.println("\nVeículo não pôde ser inserido.");

        return null;
    }

    private boolean setarCondutor(Veiculo novoVeiculo) {
        LeituraValida leitor = new LeituraValida();
        CondutorDAO DAO = new CondutorDAO();

        System.out.println("\nO veículo precisa de um condutor.");
        String escolhaUsuario = leitor.lerEscolhaSNValida("\nO condutor a ser vinculado já existe? (S/N) - ");

        if (escolhaUsuario.equalsIgnoreCase("S")) {
            int CNH = leitor.lerIntValido("\nInsira o CNH desse condutor: ");

            Condutor condutorObtido = DAO.consultarCondutor(CNH);

            if (condutorObtido == null)
                return false;

            condutorObtido.setVeiculo(novoVeiculo);

            boolean atualizacaoValida = DAO.atualizarCondutor(condutorObtido);
            if (atualizacaoValida)
                return true;
        } else {
            Condutor condutorCriado = new Condutor().obterInsercao(true);
            condutorCriado.setVeiculo(novoVeiculo);

            boolean atualizacaoValida = DAO.atualizarCondutor(condutorCriado);
            if (atualizacaoValida)
                return true;
        }

        return false;
    }

    public void obterRemocao() {
        LeituraValida leitor = new LeituraValida();
        VeiculoDAO DAO = new VeiculoDAO();

        limparConsole();
        System.out.println("(Remoção de Veículo) \n");

        String placa = leitor.lerString("Insira a placa do veículo a ser consultado: ");

        boolean remocaoValida = DAO.removerVeiculo(placa);

        if (remocaoValida)
            System.out.println("\nVeículo removido com sucesso!");
        else
            System.out.println("\nVeículo não pôde ser removido.");
    }

    public void obterConsulta() {
        LeituraValida leitor = new LeituraValida();
        VeiculoDAO DAO = new VeiculoDAO();

        limparConsole();
        System.out.println("(Consulta de Veículo) \n");

        String placa = leitor.lerString("Insira a placa do veículo a ser consultado: ");

        Veiculo veiculoObtido = DAO.consultarVeiculo(placa);

        if (veiculoObtido == null) {
            System.out.println("\nVeículo não encontrado.");
            return;
        }

        System.out.println("\nVeículo - " + veiculoObtido.getPlaca());
        System.out.println("Ano - " + veiculoObtido.getAno());
        System.out.println("Modelo - " + veiculoObtido.getModelo());
        System.out.println("Marca - " + veiculoObtido.getMarca());
        System.out.println("Condutor - " + veiculoObtido.getCondutor().getCNH());

        String escolhaUsuario = leitor.lerEscolhaSNValida("\nDeseja listar as multas desse veículo? (S/N) - ");

        if (escolhaUsuario.equalsIgnoreCase("S")) {
            new Multa().obterListaPorVeiculo(veiculoObtido.getPlaca(), veiculoObtido);
        }
    }

    public void obterLista() {
        VeiculoDAO DAO = new VeiculoDAO();
        List<Veiculo> veiculos = DAO.listarVeiculos();

        limparConsole();

        if (veiculos.size() == 0) {
            System.out.println("Sistema sem veículos.");
            return;
        }

        System.out.println("(Veículos) \n");

        for (Veiculo esteVeiculo : veiculos) {
            System.out.println("Veículo - " + esteVeiculo.getPlaca());
        }
    }

    public void venderVeiculo() {
        LeituraValida leitor = new LeituraValida();
        CondutorDAO DAO = new CondutorDAO();

        limparConsole();
        System.out.println("(Venda de Veículo) \n");

        int vendedorCNH = leitor.lerIntValido("Insira o CNH do condutor vendedor: ");
        int compradorCNH = leitor.lerIntValido("Insira o CNH do condutor comprador: ");

        Condutor vendedor = DAO.consultarCondutor(vendedorCNH);
        Condutor comprador = DAO.consultarCondutor(compradorCNH);
        Veiculo veiculoVendido = vendedor.getVeiculo();

        vendedor.setVeiculo(null);
        comprador.setVeiculo(veiculoVendido);

        boolean vendaValida = DAO.atualizarCondutor(comprador);

        if (vendaValida)
            System.out.println("\nVeículo " + veiculoVendido.getPlaca() + " vendido com sucesso!");
        System.out.println("\nVeículo " + veiculoVendido.getPlaca() + " não pôde ser vendido!");
    }

    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }
}