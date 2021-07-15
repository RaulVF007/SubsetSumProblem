/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import memoization.SubsetSumProblemMemoization;
import tabulation.SubsetSumProblemTabulation;

/**
 *
 * @author Raul Vega
 */
public class Main {
    
    private static long startTime = System.nanoTime();
    
    private static SubsetSumProblemMemoization subsetMemo = new SubsetSumProblemMemoization();
    private static SubsetSumProblemTabulation subsetTabu = new SubsetSumProblemTabulation();
    
    private static int[] conjuntoNumeros;
    private static int sum, numEnteros;
    
    private static List <Integer> listaNumEnteros = new ArrayList<>();
    private static List <Integer> listaSum = new ArrayList<>();
    private static List <Integer> listaConjuntoNumeros = new ArrayList<>();
    
    private static List <Integer> subconjuntoTabu = new ArrayList<>();
    private static List <Integer> subconjuntoMemo = new ArrayList<>();;
   
    public static void main (String[] args) throws IOException{
        for (int i = 0; i < args.length; i++) {
            switch(args[i]){
                //solo puede tener la opcion -f o -d, no ambas
                case "-f":  //fichero
                    leerFichero(args[i + 1]);
                    i++;
                    break;
                    
                case "-d":  //directorios
                    leerDirectorio(args[i + 1]);
                    i++;
                    break;
                    
                case "-sm": //memoization 
                    boolean resultMemo;
                    System.out.println("\n-------------------------");
                    System.out.println("MEMOIZATION");
                    System.out.println("-------------------------");
                    System.out.println("\nSe debe obtener un subconjunto que sume: " +sum);
                    //Cogemos los parámetros almacenados en las listas respectivas
                    for (int j = 0; j < listaNumEnteros.size(); j++) {
                        numEnteros = listaNumEnteros.get(j);
                        sum = listaSum.get(j);
                        conjuntoNumeros = new int[numEnteros];
                        Integer[] temp = listaConjuntoNumeros.toArray(new Integer[numEnteros]);
                        for (int n = 0; n < numEnteros; ++n) {
                            conjuntoNumeros[n] = temp[n];
                        }
                        resultMemo = subsetMemo.subsetSum(conjuntoNumeros, numEnteros - 1, sum, subconjuntoMemo);
                        
                        if(resultMemo){
                            System.out.println("\nSe ha obtenido el siguiente resultado -> " +subconjuntoMemo);
                            System.out.println("Valor de sum -> " +sum);
                        }else{
                            System.out.println("\nNo se ha encontrado solución con los parámetros pasados");
                        }
                    }
                    break;
                    
                case "-st": //tabulation 
                    System.out.println("\n-------------------------");
                    System.out.println("TABULATION");
                    System.out.println("-------------------------");
                    boolean resultTabu;
                    System.out.println("\nSe debe obtener un subconjunto que sume: " +sum);
                    //Cogemos los parámetros almacenados en las listas respectivas
                    for (int j = 0; j < listaNumEnteros.size(); j++) {
                        numEnteros = listaNumEnteros.get(j);
                        sum = listaSum.get(j);
                        conjuntoNumeros = new int[numEnteros];
                        Integer[] temp = listaConjuntoNumeros.toArray(new Integer[numEnteros]);
                        for (int n = 0; n < numEnteros; ++n) {
                            conjuntoNumeros[n] = temp[n];
                        }
                        resultTabu = subsetTabu.subsetSum(conjuntoNumeros, numEnteros, sum, subconjuntoTabu);
                        
                        if(resultTabu){
                        System.out.println("\nSe ha obtenido el siguiente resultado -> " +subconjuntoTabu);
                        System.out.println("Valor de sum -> " +sum);
                        }else{
                            System.out.println("\nNo se ha encontrado solución con los parámetros pasados");
                        }
                    }
                    break;  
                    
                case "-t": 
                    double result = (double) (tiempoEjecucion() / 10e6);
                    System.out.print("Tiempo de ejecución: " + result +" milisegundos");
                    break;  
                    
                case "-h":
                    ayuda();
                    break;  
                case "-check":  //comparar -sm and -st
                    System.out.println("\nSe debe obtener el mismo subconjunto para Memoization y Tabulation que sume: " +sum);
                    check();
                    break;
                    
                default:
                    System.out.println("Parámetro incorrecto");
                    ayuda();
                    break;
            }
        }
    }
    
    private static void leerFichero(String fichero) throws FileNotFoundException, IOException {
        String str;
        FileReader f = new FileReader(fichero);
        BufferedReader buffer = new BufferedReader(f);
        str = buffer.readLine();
        String[] parts = str.split(" "); //genera un array con la primera linea del documento compuesto por numEnteros y sum
        
        numEnteros = Integer.parseInt(parts[0]);
        sum = Integer.parseInt(parts[1]);
        
        int i = 0;
        conjuntoNumeros = new int [numEnteros];
        while((str = buffer.readLine())!= null && i<numEnteros){
            conjuntoNumeros[i] = Integer.parseInt(str);
            i++;
        }
        /*Almacenaremos todos los parámetros recogidos a través de leer diferentes
        ficheros en listas para poder ejecutar los métodos de Tabulation y 
        Memoization cuando se llame a un directorio con varios ficheros*/
        listaSum.add(sum);
        listaNumEnteros.add(numEnteros);
        for (int j = 0; j < conjuntoNumeros.length; j++) {
            listaConjuntoNumeros.add(conjuntoNumeros[j]);
        }
        buffer.close();
        f.close();
    }
    
    private static void leerDirectorio (String directorio) throws IOException {
        File carpeta = new File(directorio);
        String[] archivos = carpeta.list();
        
        //se recorren todos los archivos llamando a fileReader para almacenar
        //el array de cada uno de los archivos
        System.out.print("\nNombre de los ficheros: \n");
        for (int i = 0; i < archivos.length; i++) {
            System.out.print(archivos[i] + " | ");
            System.out.println("");
            leerFichero(directorio + "\\" + archivos[i]);
        }
    }

    private static long tiempoEjecucion() {
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static void ayuda() {
        System.out.println("SubsetSubProblem.java [-h] [-f [FILE]] [-d [DIRECTORY]] [-sm] [-st] [-t] [-check]");
        System.out.println("-h: obtener ayuda");
        System.out.println("-f FILE: usar un fichero en ..\\input-files\\...");
        System.out.println("-d DIRECTORY: usar todos los ficheros del directorio ..\\input-files\\");
        System.out.println("-sm: calcular la mejor solución con memoization");
        System.out.println("-st: calcular la mejor solución con tabulation");
        System.out.println("-t: obtener tiempo de ejecución");
        System.out.println("-check: se comparan las soluciones de memoization y tabulation");
    }    

    private static void check() {
        boolean resultTabu;
        boolean resultMemo;
        
        /*Almacenaremos todos los parámetros recogidos a través de leer diferentes
        ficheros en listas para poder ejecutar los métodos de Tabulation y 
        Memoization cuando se llame a un directorio con varios ficheros*/
        for (int j = 0; j < listaNumEnteros.size(); j++) {
            numEnteros = listaNumEnteros.get(j);
            sum = listaSum.get(j);
            conjuntoNumeros = new int[numEnteros];
            Integer[] temp = listaConjuntoNumeros.toArray(new Integer[numEnteros]);
            for (int n = 0; n < numEnteros; ++n) {
                conjuntoNumeros[n] = temp[n];
            }
            resultTabu = subsetTabu.subsetSum(conjuntoNumeros, numEnteros, sum, subconjuntoTabu);
            int sumTabu = sum;
            resultMemo = subsetMemo.subsetSum(conjuntoNumeros, numEnteros -1, sum, subconjuntoMemo);
            int sumMemo = sum;
        
            if (resultTabu && resultMemo){
               boolean finResult = equalLists(subconjuntoTabu, subconjuntoMemo);
               if(finResult){
                   System.out.println("\nSe han obtenido los mismos resultados -> ");
                   System.out.println("Memoization: " +subconjuntoMemo +", valor de sum: " +sumMemo);
                   System.out.println("Tabulation: " +subconjuntoTabu +", valor de sum: " +sumTabu);

               }else{
                   System.out.println("\nNo se han obtenido los mismos resultados -> ");
                   System.out.println("Memoization: " +subconjuntoMemo +", valor de sum: " +sumMemo);
                   System.out.println("Tabulation: " +subconjuntoTabu +", valor de sum: " +sumTabu);
               }
            }else if(! (resultTabu && resultMemo)){
                System.out.println("\nNinguno ha conseguido solución para el problema presentado");
            }else if (!resultTabu && resultMemo){
                System.out.println("\nSe ha obtenido solución con Memoization y no con Tabulation");
            }else{
                System.out.println("\nSe ha obtenido solución con Tabulation y no con Memoization");
            }
        }
    }
    
   
    private static boolean equalLists(List<Integer> tabu, List<Integer> memo){     
        if (tabu == null && memo == null){
            return true;
        }
        
        if((tabu == null && memo != null) 
          || tabu != null && memo == null
          || tabu.size() != memo.size()){
            return false;
        }
        //Usamos copias para no perder el orden original, ya que usando el 
        //método sort se ordenan de menor a mayor
        tabu = new ArrayList<Integer>(tabu); 
        memo = new ArrayList<Integer>(memo);   

        Collections.sort(tabu);
        Collections.sort(memo);      
        return tabu.equals(memo);
    }
}
