package atividadegrafos;

/*
    Erik Kaue Paroline Jose dos Santos - RA: 20960545
    Fernanda Pereira Umberto - RA: 20943426
    Guilherme Rodrigues de Miranda - RA: 20996393
    Matheus Marques de Souza - RA: 20981531
    Milena Basso - RA: 20956610
 */
import java.util.ArrayList;

public class GrafoNaoOrientado {

    // Quantidade de vértices.
    private int tamanhoVertices;
    // Lista duplamente ligada para guardar as arestas de adjacência.
    private ArrayList<Integer>[] arestasAdjacentes;

    // Construtor recebendo os valores necessários para iniciar o processamento do grafo.
    GrafoNaoOrientado(int quantidadeVertices) {
        this.tamanhoVertices = quantidadeVertices;
        inicializacaoGrafo();
    }

    // Método que realiza a inicialização do grafo, setando em cada posição do vetor arestasAdjacentes um ArrayList.
    private void inicializacaoGrafo() {
        arestasAdjacentes = new ArrayList[tamanhoVertices];
        for (int i = 0; i < tamanhoVertices; i++) {
            arestasAdjacentes[i] = new ArrayList<>();
        }
    }

    // Adicionar as arestas no grafo.
    public void adicionaAresta(Integer verticeOrigem, Integer verticeDestino) {
        arestasAdjacentes[verticeOrigem].add(verticeDestino);
        arestasAdjacentes[verticeDestino].add(verticeOrigem);
    }

    // Remover arestas no grafo.
    private void removeAresta(Integer verticeOrigem, Integer verticeDestino) {
        arestasAdjacentes[verticeOrigem].remove(verticeDestino);
        arestasAdjacentes[verticeDestino].remove(verticeOrigem);
    }

    // Método utilizado para printar o circuito euleriano.
    public String printarCircuitoEuleriano() {
        String impressaoCaminho = "";
        Integer auxiliarImpressao = 0;
        for (int i = 0; i < tamanhoVertices; i++) {
            if (arestasAdjacentes[i].size() % 2 == 1) {
                auxiliarImpressao = i;
                break;
            }
        }
        impressaoCaminho = printarCircuitoEulerianoAuxiliar(auxiliarImpressao, impressaoCaminho);
        return impressaoCaminho;
    }

    // Método utilizado recursivamente para adicionar os elementos à String de retorno que guardará o circuito completo.
    private String printarCircuitoEulerianoAuxiliar(Integer verticeOrigem, String stringRetorno) {
        for (int i = 0; i < arestasAdjacentes[verticeOrigem].size(); i++) {
            Integer verticeDestino = arestasAdjacentes[verticeOrigem].get(i);
            if (verificaProximaAresta(verticeOrigem, verticeDestino)) {
                stringRetorno += (verticeOrigem + "-" + verticeDestino + " ");
                removeAresta(verticeOrigem, verticeDestino);
                stringRetorno = printarCircuitoEulerianoAuxiliar(verticeDestino, stringRetorno);
            }
        }
        return stringRetorno;
    }

    // Verifica se o tamanho da aresta adjacente na posição do vértice de origem é igual a 1. Se sim, retorna true.
    private boolean verificaProximaAresta(Integer verticeOrigem, Integer verticeDestino) {
        // Se o verticeOrigem é o único adjacente do verticeDestino (ou seja, igual a 1), retorna true. 
        if (arestasAdjacentes[verticeOrigem].size() == 1) {
            return true;
        }
        // Se existem múltiplos adjacentes, então não existe uma aresta conectando verticeOrigem e verticeDestino. 
        boolean[] foiVisitado = new boolean[this.tamanhoVertices];
        // Contagem do número de vértices que podem ser alcançados a partir de verticeOrigem.
        int contadorAuxiliar1 = buscaEmProfundidade(verticeOrigem, foiVisitado);
        removeAresta(verticeOrigem, verticeDestino);
        // Refaz a contagem depois de remover a aresta de verticeOrigem e verticeDestino.
        foiVisitado = new boolean[this.tamanhoVertices];
        int contadorAuxiliar2 = buscaEmProfundidade(verticeOrigem, foiVisitado);
        // Adiciona a aresta novamente ao grafo.
        adicionaAresta(verticeOrigem, verticeDestino);
        // Se o resultado do contadorAuxiliar1 for maior que contadorAuxiliar2, significa que não é uma próxima aresta válida. Caso contrário, retorna true.
        if (contadorAuxiliar1 > contadorAuxiliar2) {
            return false;
        } else {
            return true;
        }
    }

    // Busca em profundidade no grafo, baseado nos vértices ainda não visitados no grafo.
    private int buscaEmProfundidade(Integer verticeDestino, boolean[] foiVisitado) {
        foiVisitado[verticeDestino] = true;
        int count = 1;
        for (int adj : arestasAdjacentes[verticeDestino]) {
            if (!foiVisitado[adj]) {
                count = count + buscaEmProfundidade(adj, foiVisitado);
            }
        }
        return count;
    }
}
