
import java.util.Arrays;

public class SequencialStencil {

    public static void stencil(double[] vetor, int iteracoes) {
        double[] vetorAuxiliar = new double[vetor.length];
        System.arraycopy(vetor, 0, vetorAuxiliar, 0, vetor.length);

        for (int it = 0; it < iteracoes; it++) {
            for (int i = 1; i < vetor.length - 1; i++) {
                vetorAuxiliar[i] = (vetor[i - 1] + vetor[i + 1]) / 2.0;
            }
            // Troca os vetores para a próxima iteração
            double[] temp = vetor;
            vetor = vetorAuxiliar;
            vetorAuxiliar = temp;
        }
    }

    public static void main(String[] args) {
        double[] vetor = {1, 2, 6, 2, 9, 3, 4, 1, 8, 1};
        int iteracoes = 1000;
        stencil(vetor, iteracoes);
        System.out.println("Resultado final: " + Arrays.toString(vetor));
    }
}