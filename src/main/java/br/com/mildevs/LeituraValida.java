package br.com.mildevs;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LeituraValida {

    public LeituraValida() {
    }

    public int lerIntValido(String mensagemInsira) {
        while (true) {
            Scanner leitor = new Scanner(System.in);

            try {
                System.out.print(mensagemInsira);
                return leitor.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\n !> O valor deve ser inteiro <! \n");
            }
        }
    }

    public double lerDoubleValido(String mensagemInsira) {
        while (true) {
            Scanner leitor = new Scanner(System.in);

            try {
                System.out.print(mensagemInsira);
                return leitor.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("\n !> O valor deve ser númerico <! \n");
            }
        }
    }

    public int lerEscolhaMenuValida(String mensagemInsira, int totalOpcoes) {
        while (true) {
            int inputUsuario = lerIntValido(mensagemInsira);

            if (inputUsuario >= 1 && inputUsuario <= totalOpcoes) {
                return inputUsuario;
            }

            System.out.println("\n !> O valor deve ser de 1 a " + totalOpcoes + " <! \n");
        }
    }

    public String lerEscolhaSNValida(String mensagemInsira) {
        while (true) {
            String escolhaUsuario = lerString(mensagemInsira);

            if (escolhaUsuario.equalsIgnoreCase("S") || escolhaUsuario.equalsIgnoreCase("N")) {
                return escolhaUsuario;
            }

            System.out.println("\n !> A escolha deve ser SIM ou NÃO <!");
        }
    }

    public LocalDate lerDataEmissaoValida(String mensagemInsira) {
        while (true) {
            try {
                String[] data = lerString(mensagemInsira).split("-");

                int ano = Integer.parseInt(data[0]);
                int mes = Integer.parseInt(data[1]);
                int dia = Integer.parseInt(data[2]);

                return LocalDate.of(ano, mes, dia);
            } catch (java.time.DateTimeException e) {
            } catch (java.lang.NumberFormatException e) {
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {}

            System.out.println("\n !> A data é inválida. Seu modelo precisa ser: AAAA-MM-DD <! \n");
        }
    }

    public String lerString(String mensagemInsira) {
        System.out.print(mensagemInsira);
        return new Scanner(System.in).nextLine();
    }
}
