package atividadegrafos;

import java.util.Iterator;
import java.util.LinkedList;

/*
    Erik Kaue Paroline Jose dos Santos - RA: 20960545
    Fernanda Pereira Umberto - RA: 20943426
    Guilherme Rodrigues de Miranda - RA: 20996393
    Matheus Marques de Souza - RA: 20981531
    Milena Basso - RA: 20956610
 */
public class GrafoOrientado {

    // Número de vértices.
    private int tamanhoVertices;
    // Lista duplamente ligada para guardar as arestas de adjacência.
    private LinkedList<Integer> arestasAdjacentes[];
    // Vetor para guardar o grau dos vértices.
    private int grauVertices[];
    // O número que representa o primeiro vértice.
    private int verticeInicial;
    // String global para guardar a concatenação do circuito.
    private String impressaoCircuito;

    // Construtor recebendo os valores necessários para iniciar o processamento do grafo.
    GrafoOrientado(int verticeOrigem, int verticeInicial) {
        tamanhoVertices = verticeOrigem;
        arestasAdjacentes = new LinkedList[verticeOrigem];
        grauVertices = new int[tamanhoVertices];
        this.verticeInicial = verticeInicial;
        for (int i = 0; i < verticeOrigem; ++i) {
            arestasAdjacentes[i] = new LinkedList();
            grauVertices[i] = 0;
        }
    }

    // Adicionar as arestas no grafo.
    public void adicionaAresta(int verticeOrigem, int verticeDestino) {
        arestasAdjacentes[verticeOrigem].add(verticeDestino);
        grauVertices[verticeDestino]++;
    }

    // Método recursivo que realiza a busca em profundidade e realiza o armazenamento dos valores dos circuitos na String impressaoCircuito.
    private String buscaEmProfundidade(int verticeOrigem, boolean visitados[]) {
        visitados[verticeOrigem] = true;
        int n;
        Iterator<Integer> i = arestasAdjacentes[verticeOrigem].iterator();
        impressaoCircuito = Integer.toString(verticeOrigem);
        while (i.hasNext()) {
            n = i.next();
            impressaoCircuito += "-" + Integer.toString(n) + " ";

            if (visitados[n]) {
                impressaoCircuito += Integer.toString(n);
            }
            if (!visitados[n]) {
                impressaoCircuito += buscaEmProfundidade(n, visitados);
            }
        }
        return impressaoCircuito;
    }

    // Método que realiza a troca do sentido do direcionamento das arestas.
    private GrafoOrientado pegaGrafoReverso() {
        GrafoOrientado g = new GrafoOrientado(tamanhoVertices, verticeInicial);
        for (int v = 0; v < tamanhoVertices; v++) {
            Iterator<Integer> i = arestasAdjacentes[v].listIterator();
            while (i.hasNext()) {
                g.arestasAdjacentes[i.next()].add(v);
                (g.grauVertices[v])++;
            }
        }
        return g;
    }

    // Método para verificar se o grafo é fortemente conexo (grafo no qual todos os vértices conseguem chegar aos outros vértices).
    private boolean grafoFortementeConexo() {
        boolean visitados[] = new boolean[tamanhoVertices];
        for (int i = 0; i < tamanhoVertices; i++) {
            visitados[i] = false;
        }
        buscaEmProfundidade(verticeInicial, visitados);
        for (int i = 0; i < tamanhoVertices; i++) {
            if (visitados[i] == false) {
                return false;
            }
        }
        GrafoOrientado gr = pegaGrafoReverso();
        for (int i = 0; i < tamanhoVertices; i++) {
            visitados[i] = false;
        }
        gr.buscaEmProfundidade(verticeInicial, visitados);
        for (int i = 0; i < tamanhoVertices; i++) {
            if (visitados[i] == false) {
                return false;
            }
        }
        return true;
    }

    // Método que fará a verificação se existe um circuito euleriano.
    public String temCicloEuleriano() {
        // Se não é fortemente conexo, não tem um circuito euleriano.
        if (grafoFortementeConexo() == false) {
            return "O Grafo fornecido não é Euleriano";
        }
        for (int i = 0; i < tamanhoVertices; i++) {
            // Se o tamanho da aresta adjacente for diferente do grau do vértice, não tem um circuito euleriano.
            if (arestasAdjacentes[i].size() != grauVertices[i]) {
                return "O Grafo fornecido não é Euleriano";
            }
        }
        // Se tiver passado por todos os outros casos e não retornar, existe um circuito euleriano.
        StringBuilder stringCircuito = new StringBuilder(impressaoCircuito);
        // Deleta a última posição do retorno da String, por se tratar de um elemento não necessário na impressão.
        stringCircuito = stringCircuito.deleteCharAt(stringCircuito.length() - 1);
        String circuitoRetorno = stringCircuito.toString();
        // Utilização do método trim() para remover qualquer espaço a mais.
        circuitoRetorno = circuitoRetorno.trim();
        return circuitoRetorno;
    }
}
