/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kelia5;

/**
 *
 * @author aluno
 */
public class Kelia5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int A = 9;
        int B = 50;
        int cod = 4;
        int resultado = 0;

        if (cod == 1) {
            resultado = (A + B) / 2;

        }

        if (cod == 2) {
            if (A < B) {
                int maior = A;
            } else {
                int maior = B;
                resultado = maior;
            }
        }
        
        if (cod == 3) {
            resultado = A * B;
        }
        
        if (cod == 4) {
            if (B != 0) {
                resultado = A / B;
            }
        }
        
        System.out.println("O resultado é: " + resultado);

    }
}
