import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierStencil {

    public static void stencil(double[] vetor, int iteracoes, int numThreads) throws InterruptedException {
        // Usamos um array de uma posição para armazenar os vetores
        double[][] vetores = new double[2][vetor.length];
        vetores[0] = Arrays.copyOf(vetor, vetor.length); // Vetor original
        vetores[1] = Arrays.copyOf(vetor, vetor.length); // Vetor auxiliar

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Barreira cíclica para sincronizar as threads no final de cada iteração
        CyclicBarrier barreira = new CyclicBarrier(numThreads, () -> {
            // Troca os vetores para a próxima iteração
            double[] temp = vetores[0];
            vetores[0] = vetores[1];
            vetores[1] = temp;
        });

        for (int t = 0; t < numThreads; t++) {
            final int inicio = 1 + t * ((vetor.length - 2) / numThreads);
            final int fim = (t == numThreads - 1) ? vetor.length - 1 : inicio + ((vetor.length - 2) / numThreads);

            executor.submit(() -> {
                for (int it = 0; it < iteracoes; it++) {
                    int idxVetorAtual = it % 2; // Índice do vetor atual (alterna entre 0 e 1)
                    int idxVetorAuxiliar = (it + 1) % 2; // Índice do vetor auxiliar

                    for (int i = inicio; i < fim; i++) {
                        vetores[idxVetorAuxiliar][i] = (vetores[idxVetorAtual][i - 1] + vetores[idxVetorAtual][i + 1]) / 2.0;
                    }

                    try {
                        barreira.await(); // Espera todas as threads terminarem a iteração
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Copia o resultado de volta para o vetor original
        System.arraycopy(vetores[iteracoes % 2], 0, vetor, 0, vetor.length);
        System.out.println(Arrays.toString(vetores[1]));
    }

    public static void main(String[] args) throws InterruptedException {
        double[] vetor = {1, 5, 2, 4, 5, 9, 1, 8, 9, 1};
        int iteracoes = 10000;
        int numThreads = 4;
        stencil(vetor, iteracoes, numThreads);
        System.out.println("Resultado final: " + Arrays.toString(vetor));
    }
}