import java.util.Scanner;

class FolhaPagamento {
    String nome;
    float salarioBase;
    float horaExtra;
    float insalubridade;
    float bonificacao;
    float inss;
    float impostoRenda;
    float planoSaude;
    float salarioLiquido;
}

public class Main {
    static FolhaPagamento calcularSalario(int departamento, float horasTrabalhadas, String nome) {
        final float valorHoraAdmin = 22.0f;
        final float valorHoraProducao = 12.0f;
        final float valorHoraExtra = 2.0f;
        final float insalubridade = 0.15f;
        final float bonus20 = 0.03f;
        final float bonus30 = 0.05f;
        final float bonus40 = 0.1f;
        final float inss = 0.07f;
        final float impostoRenda = 0.12f;
        final float planoSaude = 20.0f;

        FolhaPagamento folha = new FolhaPagamento();

        float salarioBase = (departamento == 1) ? valorHoraAdmin : valorHoraProducao;
        folha.salarioBase = salarioBase * horasTrabalhadas;

        float horasAdicionais = (horasTrabalhadas > 40) ? horasTrabalhadas - 40 : 0;
        folha.horaExtra = valorHoraExtra * 2 * horasAdicionais;

        folha.insalubridade = (departamento == 2) ? folha.salarioBase * insalubridade : 0;

        if (departamento == 1) {
            if (horasTrabalhadas > 20 && horasTrabalhadas <= 30) {
                folha.bonificacao = folha.salarioBase * bonus20;
            } else if (horasTrabalhadas > 30 && horasTrabalhadas < 40) {
                folha.bonificacao = folha.salarioBase * bonus30;
            } else if (horasTrabalhadas >= 40) {
                folha.bonificacao = folha.salarioBase * bonus40;
            }
        }

        float salarioBruto = folha.salarioBase + folha.horaExtra + folha.insalubridade + folha.bonificacao;

        folha.inss = salarioBruto * inss;
        folha.impostoRenda = salarioBruto * impostoRenda;

        folha.salarioLiquido = salarioBruto - folha.inss - folha.impostoRenda - planoSaude;

        folha.nome = nome;

        return folha;
    }

    public static void main(String[] args) {
        final int numFuncionarios = 5;

        Scanner scanner = new Scanner(System.in);

        String[] nomes = new String[numFuncionarios];
        int[] departamentos = new int[numFuncionarios];
        float[] horasTrabalhadas = new float[numFuncionarios];

        for (int i = 0; i < numFuncionarios; ++i) {
            System.out.print("Informe o nome do funcionario " + (i + 1) + ": ");
            nomes[i] = scanner.nextLine();

            System.out.print("Informe o departamento (1 = Administrativo / 2 = Producao) do funcionario " + (i + 1) + ": ");
            departamentos[i] = scanner.nextInt();

            System.out.print("Informe a quantidade de horas trabalhadas pelo funcionario " + (i + 1) + " no mes: ");
            horasTrabalhadas[i] = scanner.nextFloat();

            scanner.nextLine();
        }

        System.out.printf("%-5s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", "Ordem", "Nome", "Salario base", "Hora Extra", "Insalubridade", "Bonificacao", "INSS", "IR", "Plano Saude", "Salario Liquido");

        for (int i = 0; i < numFuncionarios; ++i) {
            FolhaPagamento folha = calcularSalario(departamentos[i], horasTrabalhadas[i], nomes[i]);

            System.out.printf("%-5d%-20s%-15.2f%-15.2f%-15.2f%-15.2f%-15.2f%-15.2f%-15.2f%-15.2f\n", i + 1, folha.nome, folha.salarioBase, folha.horaExtra, folha.insalubridade, folha.bonificacao, folha.inss, folha.impostoRenda, folha.planoSaude, folha.salarioLiquido);
        }

        scanner.close();
    }
}
