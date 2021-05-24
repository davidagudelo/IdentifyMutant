package co.com.xmen.usecase;

import co.com.xmen.model.dynamo.HumanGateway;
import co.com.xmen.model.response.HumantResult;
import org.assertj.core.util.VisibleForTesting;
import reactor.core.publisher.Mono;

import java.util.List;

//Clase  de negocio con la logica para el analisis de DNA
public class UseCasesVerify {

    private final HumanGateway humanGateway;

    public UseCasesVerify(HumanGateway humanGateway) {
        this.humanGateway = humanGateway;
    }

    //Verificamos si el adn es humano o mutante y guarda la respuesta en dynamo
    public Mono<Boolean> verifyHuman(List<String> dna) {
        char[][] matrix=  listToMatriz(dna);
        return  verifyMutant(matrix).flatMap(s -> humanGateway.saveRecord(dna,s));
    }

    //Lista los resultados de la tabla
    public Mono<HumantResult> list() {
        return humanGateway.getlist();
    }

    //Se encarga de convertir la lista a una matriz de carcteres
    public char[][]  listToMatriz(List<String> dna) {
        //se declara la matriz
        char[][] matrix = new char[dna.size()][dna.get(0).length()];
        //recorremos la lista
        for (int i = 0; i < dna.size(); i++) {
            //se convierte la lista en una matriz de caracteres
            char[] charArray = dna.get(i).toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                matrix[i][j] = charArray[j];
            }
        }
        return matrix;
    }

    //Valida si el ADN es mutante o no
    public Mono<Boolean> verifyMutant(char[][] d) {
        int result =0;
        //Recorremos la matriz
        for (int x=0; x < d.length; x++) {
            for (int y=0; y < d[x].length; y++) {
                char valor = d[x][y];
                //Se valida la cadena de ADN en los 3 vectores (Horizontal - Vertical - Oblicuo)
                result = result + validarHorizontal(x, y, d, valor) ;
                result =  result +validarVertical(x, y, d, valor) ;
                result = result + validarOblicua(x, y, d, valor);
            }
        }
        //validamos la cantidad de cadenas q tiene
        if (result > 1){
            return Mono.just(true);
        }else
        {
            return Mono.just(false);
        }
    }

    public static int validarHorizontal(int x, int y, char[][] matrix, char valor){
        Boolean result = true;
        int contador = 0;
        int ret= 0;
        //Recoremos las combinaciones de manera horizontal
        while (y < matrix[x].length-1 && result){
            y ++;
            result = valor == matrix[x][y];
            if (result){
                contador++;
                if(contador == 3){
                    ret++;
                }
            }
        }

        return ret;
    }

    public static int validarVertical(int x, int y, char[][] matrix, char valor) {
        Boolean result = true;
        int contador = 0;
        int ret = 0;
        //Recoremos las combinaciones de manera vertical
        while (x < matrix.length - 1 && result) {
            x++;
            result = valor == matrix[x][y];
            if (result) {
                contador++;
                if (contador == 3) {
                    ret++;
                }
            }


        }
        return ret;
    }

    public static int validarOblicua(int x, int y, char[][] matrix, char valor) {
        Boolean result = true;
        int contador = 0;
        int ret = 0;
        //Recoremos las combinaciones de manera oblicua
        while (x < matrix.length-1 && y < matrix[x].length-1 && result){
            x ++;
            y ++;
            result = valor == matrix[x][y];
            if (result) {
                contador++;
                if (contador == 3) {
                    ret++;
                }
            }
        }
        return ret;
    }


}

