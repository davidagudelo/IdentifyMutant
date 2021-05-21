package co.com.xmen.usecase.verify;

import co.com.xmen.model.dynamo.HumanGateway;
import co.com.xmen.model.response.HumantResult;
import reactor.core.publisher.Mono;

import java.util.List;


public class UseCasesVerify {

    private final HumanGateway humanGateway;

    public UseCasesVerify(HumanGateway humanGateway) {
        this.humanGateway = humanGateway;
    }

    public Mono<Boolean> verifyHuman(List<String> dna) {
       return listToMatriz(dna).flatMap(d -> verifyMutant(d))
               .flatMap(s -> humanGateway.saveRecord(dna,s));
    }


    private Mono<char[][]>  listToMatriz(List<String> dna) {
        char[][] matrix = new char[dna.size()][dna.get(0).length()];
        for (int i = 0; i < dna.size(); i++) {
            char[] charArray = dna.get(i).toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                matrix[i][j] = charArray[j];
            }
        }
        return Mono.just(matrix);
    }

    private Mono<Boolean> verifyMutant(char[][] d) {
        int result =0;
        for (int x=0; x < d.length; x++) {
            for (int y=0; y < d[x].length; y++) {
                char valor = d[x][y];

                result = result + ValidarHorizontal(x, y, d, valor);
                result = result + ValidarVertical(x, y, d, valor);
                result = result + ValidarOblicua(x, y, d, valor);
            }
        }
        if (result > 2){
            System.out.println(result);
            return Mono.just(true);
        }else
        {
            return Mono.just(false);
        }
    }

    public static int ValidarHorizontal(int x, int y, char[][] matrix, char valor){
        Boolean result = true;
        int contador = 0;
        int ret= 0;
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


    public static int ValidarVertical(int x, int y, char[][] matrix, char valor) {
        Boolean result = true;
        int contador = 0;
        int ret = 0;
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

    public static int ValidarOblicua(int x, int y, char[][] matrix, char valor) {
        Boolean result = true;
        int contador = 0;
        int ret = 0;
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

    public Mono<HumantResult> list() {
       return humanGateway.getlist();
    }
}

