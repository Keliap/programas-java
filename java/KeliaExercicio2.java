/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kelia.exercicio2;

import java.util.Scanner;

/**
 *
 * @author aluno
 */
public class KeliaExercicio2 {
        
    private String nome;
    private String CPF;
    
    
    public static void main(String[] args) {
       double preco;
       int opcaoPag;
       
       Scanner leitor = new Scanner(System.in);
        System.out.println("Digite o preço do produto:  ");
        preco = leitor.nextDouble();
        System.out.println("Digite a opção desejada:  ");
        opcaoPag = leitor.nextInt();
        
        
       
       switch(opcaoPag) {
           case 1:
               System.out.println("\n Opção 1 Selecionada: ");
               System.out.println("\n O valor é: " +(preco - (preco*0.1)) );
               break;
           case 2:
               System.out.println("\n Opção 2 Selecionada: ");
               System.out.println("\n O valor é: " +(preco - (preco*0.05)) );
               break;
            case 3:
               System.out.println("\n Opção 3 Selecionada: ");
               System.out.println("\n O valor é: " + preco);
               break;
             case 4:
               System.out.println("\n Opção 4 Selecionada: ");
               System.out.println("\n O valor é: " +(preco*1.1));
               break;
       }
    }
    
}
