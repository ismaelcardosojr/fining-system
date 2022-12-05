package br.com.mildevs;

import br.com.mildevs.entity.Condutor;
import br.com.mildevs.entity.Multa;
import br.com.mildevs.entity.Veiculo;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Programa {

    public static int menuPrincipal() {
        LeituraValida leitor = new LeituraValida();

        System.out.println("(Sistema de Trânsito) \n");

        System.out.println("Qual entidade deseja manusear? \n");
        System.out.println("1 - Condutor");
        System.out.println("2 - Veículo");
        System.out.println("3 - Multa");
        System.out.println("4 - Sair \n");

        return leitor.lerEscolhaMenuValida("Insira sua escolha: ", 4);
    }

    public static int menuFuncionalidade(String nomeEntidade, boolean chamadaPorMenuVeiculo) {
        LeituraValida leitor = new LeituraValida();
        int totalFuncionalidades = 5;

        limparConsole(false);
        System.out.println("(Menu de Funcionalidades) \n");

        System.out.println("Qual ação deseja realizar com " + nomeEntidade + "? \n");
        System.out.println("1 - Inserir");
        System.out.println("2 - Remover");
        System.out.println("3 - Consultar");
        System.out.println("4 - Listar");

        if (chamadaPorMenuVeiculo) {
            System.out.println("5 - Vender");
            System.out.println("6 - Voltar");

            totalFuncionalidades++;
        } else {
            System.out.println("5 - Voltar");
        }

        System.out.print("\n");
        return leitor.lerEscolhaMenuValida("Insira sua escolha: ", totalFuncionalidades);
    }

    public static boolean menuCondutor() {
        Condutor condutor = new Condutor();

        int funcionalidadeEscolhida = menuFuncionalidade("um condutor", false);

        switch (funcionalidadeEscolhida) {
            case 1:
                condutor.obterInsercao(false);
                return false;
            case 2:
                condutor.obterRemocao();
                return false;
            case 3:
                condutor.obterConsulta();
                return false;
            case 4:
                condutor.obterLista();
                return false;
            default:
                return true;
        }
    }

    public static boolean menuVeiculo() {
        Veiculo veiculo = new Veiculo();

        int funcionalidadeEscolhida = menuFuncionalidade("um veículo", true);

        switch (funcionalidadeEscolhida) {
            case 1:
                veiculo.obterInsercao(false);
                return false;
            case 2:
                veiculo.obterRemocao();
                return false;
            case 3:
                veiculo.obterConsulta();
                return false;
            case 4:
                veiculo.obterLista();
                return false;
            case 5:
                veiculo.venderVeiculo();
                return false;
            default:
                return true;
        }
    }

    public static boolean menuMulta() {
        Multa multa = new Multa();

        int funcionalidadeEscolhida = menuFuncionalidade("uma multa", false);

        switch (funcionalidadeEscolhida) {
            case 1:
                multa.obterInsercao(false);
                return false;
            case 2:
                multa.obterRemocao();
                return false;
            case 3:
                multa.obterConsulta();
                return false;
            case 4:
                multa.obterLista();
                return false;
            default:
                return true;
        }
    }

    public static void limparConsole(boolean precisaEsperar) {
        if (precisaEsperar) {
            System.out.print("\nAperte qualquer tecla para voltar ao menu principal: ");
            new Scanner(System.in).nextLine();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        limparConsole(false);

        while (true) {
            boolean usuarioQuerVoltar = false;
            int entidadeEscolhida = menuPrincipal();

            switch (entidadeEscolhida) {
                case 1:
                    usuarioQuerVoltar = menuCondutor();
                    break;
                case 2:
                    usuarioQuerVoltar = menuVeiculo();
                    break;
                case 3:
                    usuarioQuerVoltar = menuMulta();
                    break;
                case 4:
                    System.exit(0);
            }

            if (usuarioQuerVoltar) limparConsole(false);
            else limparConsole(true);
        }
    }
}
