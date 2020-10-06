package atividadegrafos;

/*
    Erik Kaue Paroline Jose dos Santos - RA: 20960545
    Fernanda Pereira Umberto - RA: 20943426
    Guilherme Rodrigues de Miranda - RA: 20996393
    Matheus Marques de Souza - RA: 20981531
    Milena Basso - RA: 20956610
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AtividadeGrafos {

    public static void main(String[] args) {
        // Dados da primeira linha do arquivo.
        boolean grafoDirecionadoEntrada = false;
        String grafoDirecionado = "";
        // Dados da segunda linha do arquivo.
        boolean numeroVerticesEntrada = false;
        String numeroVertices = "";
        String sCurrentLine;
        // ArrayList que vai guardar o nome dos vértices inseridos no txt.
        ArrayList nomeVertices = new ArrayList<>();
        // ArrayList que vai guardar o relacionamento dos vértices inseridos no txt.
        ArrayList relacionamentoVertices = new ArrayList<>();
        // HashMap para armazenar chave e valor das arestas, ou seja, o primeiro vértice recebe o valor zero, o segundo, um, etc.
        HashMap<String, Integer> nomeVerticeValor = new HashMap<String, Integer>();
        // Leitura do arquivo.
        try ( BufferedReader br = new BufferedReader(new FileReader("grafo.txt"))) {
            while ((sCurrentLine = br.readLine()) != null) {
                // Verifica se já obteve o valor 0 ou 1 que seria o direcionamento do grafo. Se não, adiciona.
                if (grafoDirecionadoEntrada == false) {
                    grafoDirecionadoEntrada = true;
                    grafoDirecionado = sCurrentLine;
                    // Verifica se já obteve os vértices de entrada. Se não, adiciona.
                } else if (numeroVerticesEntrada == false) {
                    numeroVerticesEntrada = true;
                    numeroVertices = sCurrentLine;
                    // Trecho para obter o nome dos vértices.
                } else if (numeroVerticesEntrada == true && grafoDirecionadoEntrada == true && !sCurrentLine.contains(",")) {
                    nomeVertices.add(sCurrentLine);
                    // Trecho para obter o relacionamento dos vértices.
                } else if (numeroVerticesEntrada == true && grafoDirecionadoEntrada == true && sCurrentLine.contains(",")) {
                    relacionamentoVertices.add(sCurrentLine);
                }
            }
            // Exceção para erros de leitura de arquivo.
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            // Inserção das chaves e valores no HashMap.
            int i = 0;
            for (Object aux : nomeVertices) {
                if (!nomeVerticeValor.containsKey(aux)) {
                    nomeVerticeValor.put(aux.toString(), i);
                    i++;
                }
            }
            // Transformação da informação número de vértices para inteiro. 
            int numeroVerticesInteiro = Integer.parseInt(numeroVertices);
            // Se o grafo não é direcionado, entra nesta condição.
            if (grafoDirecionado.equals("0")) {
                // Instancia o objeto do grafo não-orientado.
                GrafoNaoOrientado grafo = new GrafoNaoOrientado(numeroVerticesInteiro);
                // Vetores para auxiliar na inserção dos valores dos relacionamentos entre os vértices (arestas).
                String vetorAuxiliar[] = new String[2];
                String vetorArmazenamento[] = new String[2];
                int j = 0;
                // Split na vírgula para pegar o lado esquerdo e o direito, que seriam as arestas.
                for (Object aux : relacionamentoVertices) {
                    vetorArmazenamento = aux.toString().split(",");
                    vetorAuxiliar[j] = vetorArmazenamento[0];
                    vetorAuxiliar[j + 1] = vetorArmazenamento[1];
                    // Passando por parâmetro a primeira e segunda aresta para cada relacionamento.
                    grafo.adicionaAresta(nomeVerticeValor.get(vetorAuxiliar[0]), nomeVerticeValor.get(vetorAuxiliar[1]));
                }
                // Recebimento do circuito gerado.
                String resultado = grafo.printarCircuitoEuleriano();
                // Substituição dos números para o nome dos vértices (transformação reversa utilizando HashMap).
                for (HashMap.Entry<String, Integer> entry : nomeVerticeValor.entrySet()) {
                    resultado = resultado.replace(entry.getValue().toString(), entry.getKey());
                }
                // Tira os espaços em branco da String caso haja.
                resultado = resultado.trim();
                // Verifica se o circuito começa e termina no mesmo vértice, sem repetir arestas. Se sim, imprime o circuito. Se não, retorna a mensagem de não euleriano.
                if ((resultado.charAt(0)) == resultado.charAt(resultado.length() - 1)) {
                    System.out.println("É euleriano. Circuito encontrado: " + resultado);
                } else {
                    System.out.println("O Grafo fornecido não é Euleriano.");
                }
                // Se o grafo é direcionado, entra nesta condição.
            } else if (grafoDirecionado.equals("1")) {
                // Pegando o valor de origem do vértice.
                String vetorString[] = ((relacionamentoVertices.get(0)).toString()).split(",");
                String verticeInicial = nomeVerticeValor.get(vetorString[0]).toString();
                // Instancia o objeto, passando por parâmetro a quantidade de vértices e o valor de origem dos vértices.
                GrafoOrientado grafo = new GrafoOrientado(numeroVerticesInteiro, Integer.parseInt(verticeInicial));
                // Vetores para auxiliar na inserção dos valores dos relacionamentos entre os vértices (arestas).
                String vetorAuxiliar[] = new String[2];
                String vetorArmazenamento[] = new String[2];
                // Split na vírgula para pegar o lado esquerdo e o direito, que seriam as arestas.
                int j = 0;
                for (Object aux : relacionamentoVertices) {
                    vetorArmazenamento = aux.toString().split(",");
                    vetorAuxiliar[j] = vetorArmazenamento[0];
                    vetorAuxiliar[j + 1] = vetorArmazenamento[1];
                    grafo.adicionaAresta(nomeVerticeValor.get(vetorAuxiliar[0]), nomeVerticeValor.get(vetorAuxiliar[1]));
                }
                // Recebimento do circuito gerado.
                String resultado = grafo.temCicloEuleriano();
                // Caso a mensagem de resultado possua a palavra "não", significa que não é um circuito euleriano.
                if ((resultado.toLowerCase()).contains("não")) {
                    System.out.println(resultado);
                    // Se não, realiza a substituição dos números para o nome dos vértices (transformação reversa utilizando HashMap).
                } else {
                    for (HashMap.Entry<String, Integer> entry : nomeVerticeValor.entrySet()) {
                        resultado = resultado.replace(entry.getValue().toString(), entry.getKey());
                    }
                    System.out.println("É euleriano. Circuito encontrado: " + resultado);
                }
                // Caso seja inserido outro valor para o direcionamento, printa uma mensagem de erro.
            } else {
                System.out.println("Utilize apenas 0 -> grafo não direcionado / 1 -> grafo direcionado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
