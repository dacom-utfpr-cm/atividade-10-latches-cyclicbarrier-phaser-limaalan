import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[] vetorLatch = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        int iteracoes = 10;
        int numThreads = 4;

        try {
            LatchStencil.stencil(vetorLatch, iteracoes, numThreads);
        } catch (InterruptedException ex) {
        }
        System.out.println("Resultado final Latch : " + Arrays.toString(vetorLatch));


        // double[] vetorBarrier = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        // try {
        //     CyclicBarrierStencil.stencil(vetorBarrier, iteracoes, numThreads);
        // } catch (InterruptedException ex) {
        // }
        // System.out.println("Resultado final Cyclic Barrier: " + Arrays.toString(vetorBarrier));

        double[] vetorPhaser = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        try {
            PhaserStencil.stencil(vetorPhaser, iteracoes, numThreads);
        } catch (InterruptedException ex) {
        }
        System.out.println("Resultado final Phaser: " + Arrays.toString(vetorPhaser));



        double[] vetorSequencial = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        SequencialStencil.stencil(vetorSequencial, iteracoes);
        System.out.println("Resultado final sequencial: " + Arrays.toString(vetorSequencial));
    }
}
