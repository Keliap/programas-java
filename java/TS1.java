/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.util.HashMap;

/**
 *
 * @author gustavo
 */
public class TS1 {
    
    private HashMap<Token, InfIdentificador> tabelaSimbolos; // Tabela de símbolos do ambiente

    public TS1() {
        tabelaSimbolos = new HashMap();

        // Inserindo as palavras reservadas
        Token word;
        word = new Token(Tag.KW, "program", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "if", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "else", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "while", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "write", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "read", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "num", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "char", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "not", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "or", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
        
        word = new Token(Tag.KW, "and", 0, 0);
        this.tabelaSimbolos.put(word, new InfIdentificador());
    }
    
    public void put(Token w, InfIdentificador i) {
        tabelaSimbolos.put(w, i);
    }

    // Retorna um identificador de um determinado token
    public InfIdentificador getIdentificador(Token w) {
        InfIdentificador infoIdentificador = (InfIdentificador) tabelaSimbolos.get(w);
        return infoIdentificador;
    }

    // Pesquisa na tabela de símbolos se há algum tokem com determinado lexema
    // vamos usar esse metodo somente para diferenciar ID e KW
    public Token retornaToken(String lexema) {
        for (Token token : tabelaSimbolos.keySet()) {
            if (token.getLexema().equals(lexema)) {
                return token;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        String saida = "";
        int i = 1;
        for (Token token : tabelaSimbolos.keySet()) {
            saida += ("posicao " + i + ": \t" + token.toString()) + "\n";
            i++;
        }
        return saida;
    }
 }