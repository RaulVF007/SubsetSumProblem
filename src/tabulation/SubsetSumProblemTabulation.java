package tabulation;

import java.util.Arrays;
import java.util.List;

public class SubsetSumProblemTabulation {
    
    /*Recurrencia:    
        Casos bases:
        t(numEnteros, sum) = True:   if sum = 0
        t(numEnteros, sum) = False:   if sum < 0 or numEnteros < 0
        Caso recursivo:
        t(numEnteros - 1, sum - V[numEnteros]) or t(numEnteros - 1, sum): Cualquier otro caso
    */
    
    /*Ejemplo: conjuntoNumeros: [5,3,6,4], sum = 10    -> Resultado = [6,4]*/
    
    public boolean subsetSum(int[] conjuntoNumeros, int numEnteros, int sum, List <Integer> subconjunto) {
        boolean[][] table = new boolean[numEnteros + 1][sum + 1];
        return tabulationSubset(conjuntoNumeros, numEnteros, sum, table, subconjunto);
    }
    
    private boolean tabulationSubset(int[] conjuntoNumeros, int numEnteros, int sum, boolean[][] table, 
            List <Integer> subconjunto) {
        // Rellenamos columna cuando sum es 0
	for (int i = 0; i <= numEnteros; i++)
            table[i][0] = true;
	// Rellenamos cuando sum es mayor que 0
	for (int i = 1; i <= numEnteros; i++) {
            // Consideramos en este bucle que sum va desde 1 hasta su valor impuesto por sum
            for (int j = 1; j <= sum; j++) { //O(sum)
		//Filtramos elementos que superen el valor de sum
		if (conjuntoNumeros[i - 1] > j) {
                    table[i][j] = table[i - 1][j];
		}
		else {
                    table[i][j] = table[i - 1][j] || table[i - 1][j - conjuntoNumeros[i - 1]];
                }
            }
	}
        if(table[numEnteros][sum])
            rellenarSubconjunto(conjuntoNumeros, numEnteros, sum, table, subconjunto);
	return table[numEnteros][sum];
    }
    
    private void rellenarSubconjunto(int[] conjuntoNumeros, int numEnteros, int sum, boolean[][] table, 
            List <Integer> subconjunto) {
        int j = sum;
        for(int i = numEnteros; i > 0 && j > 0; i--) {
            if(table[i - 1][j]) { //Operación crítica
                continue;
            } else {
                j -= conjuntoNumeros[i - 1];
                subconjunto.add(conjuntoNumeros[i - 1]); 
            } 
        }
    }
}

