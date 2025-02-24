import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserStencil {

    public static void stencil(double[] vetor, int iteracoes, int numThreads) throws InterruptedException {
        // Usamos um array de uma posição para armazenar os vetores
        double[][] vetores = new double[2][vetor.length];
        vetores[0] = Arrays.copyOf(vetor, vetor.length); // Vetor original
        vetores[1] = Arrays.copyOf(vetor, vetor.length); // Vetor auxiliar

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Phaser phaser = new Phaser(numThreads);

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

                    phaser.arriveAndAwaitAdvance(); // Sincroniza com as outras threads
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Copia o resultado de volta para o vetor original
        System.arraycopy(vetores[iteracoes % 2], 0, vetor, 0, vetor.length);
    }

    public static void main(String[] args) throws InterruptedException {
        double[] vetor = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        int iteracoes = 1000;
        int numThreads = 4;
        stencil(vetor, iteracoes, numThreads);
        System.out.println("Resultado final: " + Arrays.toString(vetor));
    }
}