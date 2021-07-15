package memoization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubsetSumProblemMemoization {
    
    /*Recurrencia:    
        Casos bases:
        t(numEnteros, sum) = True:   if sum = 0
        t(numEnteros, sum) = False:   if sum < 0 or numEnteros < 0
        Caso recursivo:
        t(numEnteros - 1, sum - V[numEnteros]) or t(numEnteros - 1, sum): Cualquier otro caso
    */
    
    public boolean subsetSum(int[] conjuntoNumeros, int numEnteros, int sum,
        List <Integer> subconjunto) {
        Map <String, Boolean> tabla = new HashMap();
        return memoizationSubset(conjuntoNumeros, numEnteros, sum, tabla, subconjunto);
    }
    
     private boolean memoizationSubset(int[] conjuntoNumeros, int numEnteros, int sum, 
            Map <String, Boolean> tabla, List <Integer> subconjunto) {
        if(sum == 0){
            return true; 
        }
        if(sum < 0 || numEnteros < 0) {
            return false; 
        }
        String clave = numEnteros + " - " + sum;
        if(!(tabla.containsKey(clave))) {
            subconjunto.add(conjuntoNumeros[numEnteros]);
            boolean valido = memoizationSubset(conjuntoNumeros, numEnteros - 1, sum - conjuntoNumeros[numEnteros], tabla, subconjunto); 
            if (valido){
                return true;
            }
            subconjunto.remove(subconjunto.size() -1);
            boolean noValido = memoizationSubset(conjuntoNumeros, numEnteros - 1, sum, tabla, subconjunto); 
            tabla.put(clave, valido || noValido); 
        }
        return tabla.get(clave); 
    }
}