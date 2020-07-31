/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.io.*;

/**
 *
 * @author gustavo
 */
public class LexerProfessor1 {
    
    private static final int END_OF_FILE = -1; // contante para fim do arquivo
    private static int lookahead = 0; // armazena o último caractere lido do arquivo	
    public static int n_line = 1; // contador de linhas
    public static int n_column = -1; // contador de linhas
    private RandomAccessFile instance_file; // referencia para o arquivo
    private static TS tabelaSimbolos; // tabela de simbolos
    
    public LexerProfessor(String input_data) {
		
        // Abre instance_file de input_data
	try {
            instance_file = new RandomAccessFile(input_data, "r");
	}
	catch(IOException e) {
            System.out.println("Erro de abertura do arquivo " + input_data + "\n" + e);
            System.exit(1);
	}
	catch(Exception e) {
            System.out.println("Erro do programa ou falha da tabela de simbolos\n" + e);
            System.exit(2);
	}
    }
    
    // Fecha instance_file de input_data
    public void fechaArquivo() {

        try {
            instance_file.close();
        }
	catch (IOException errorFile) {
            System.out.println ("Erro ao fechar arquivo\n" + errorFile);
            System.exit(3);
	}
    }
    
    //Reporta erro para o usuário
    public void sinalizaErro(String mensagem) {
        System.out.println("[Erro Lexico]: " + mensagem + "\n");
    }
    
    //Volta uma posição do buffer de leitura
    public void retornaPonteiro(){

        try {
            // Não é necessário retornar o ponteiro em caso de Fim de Arquivo
            if (lookahead != END_OF_FILE) {
                instance_file.seek(instance_file.getFilePointer() - 1);
            }    
        }
        catch(IOException e) {
            System.out.println("Falha ao retornar a leitura\n" + e);
            System.exit(4);
        }
    }
    
    /* TODO:
    //[1]   Voce devera se preocupar quando incremetar as linhas e colunas,
    //      assim como quando decrementar ou reseta-las.
    //[2]   Toda vez que voce encontrar um lexema completo, voce deve retornar
    //      um objeto new Token(Tag, "lexema", linha, coluna). Cuidado com as
    //      palavras reservadas que ja sao codastradas na TS. Essa consulta
    //      voce devera fazer somente quando encontrar um Identificador.
    //[3]   Se o caractere lido nao casar com nenhum caractere esperado,
    //      apresentar a mensagem de erro na linha e coluna correspondente.
    
    */
    // Obtém próximo token
    public Token proxToken() {

	StringBuilder lexema = new StringBuilder();
	int estado = 1;
	char c;
		
	while(true) {
            c = '\u0000'; // null char
            
            // avanca caractere
            try {
                lookahead = instance_file.read(); 
		if(lookahead != END_OF_FILE) {
                    c = (char) lookahead;
                    n_column++;
                }
            }
            catch(IOException e) {
                System.out.println("Erro na leitura do arquivo");
                System.exit(3);
            }
            
            // movimentacao do automato
            switch(estado) {
                case 1:
                    if (lookahead == END_OF_FILE)
                        return new Token(Tag.EOF, "EOF", n_line, n_column);
                    else if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                        // Permance no estado = 1
                        if(c == '\n')  {
                            n_line++;
                            n_column = -1;                               
                        }
                        else if(c == '\t') {
                           n_column += 3;
                        }
                    }
                    else if (Character.isLetter(c)){
                        lexema.append(c);
                        estado = 14;
                    }
                    else if (Character.isDigit(c)) {
                        lexema.append(c);
                        estado = 12;
                    }
                    else if (c == '<') {
                        estado = 6;
                    }
                    else if (c == '>') {
                        estado = 9;
                    }
                    else if (c == '=') {
                        estado = 2;
                    }
                    else if (c == '!') {
                        estado = 4;
                    }
                    else if (c == '/') {
                        estado = 16;
                    }
                    else if (c == '*') {
                        estado = 18;
                        return new Token(Tag.OP_MUL, "*", n_line, n_column);
                    }
                    else if(c == '+') {
                        estado = 19;
                        return new Token(Tag.OP_AD, "+", n_line, n_column);
                    }
                    else if(c == '-') {
                        estado = 20;
                        return new Token(Tag.OP_MIN, "-", n_line, n_column);
                    }
                    else if(c == ';') {
                        estado = 21;
                        return new Token(Tag.SMB_SEM, ";", n_line, n_column);
                    }
                    else if(c == '(') {
                        estado = 22;
                        return new Token(Tag.SMB_OPA, "(", n_line, n_column);
                    }
                    else if(c == ')') {
                        estado = 23;
                        return new Token(Tag.SMB_CPA, ")", n_line, n_column);
                    } else if(c == ','){
                        estado = 40;
                        return new Token(Tag.SMB_COM, ",", n_line, n_column);
                    } else if(c == '{'){
                        estado = 50;
                        return new Token(Tag.SMB_OBC, "{", n_line, n_column);
                    } else if(c == '}'){
                        estado = 60;
                        return new Token(Tag.SMB_CBC, "}", n_line, n_column);
                    }else if(c == '"') {
                        estado = 24;
                    }
                    else {
                        sinalizaErro("Caractere invalido " + c + " na linha " + n_line + " e coluna " + n_column);
//                        return null;
                    }
                    break;
                case 2:
                    if (c == '=') { // Estado 3
                        estado = 3;
                        return new Token(Tag.OP_EQ, "==", n_line, n_column);
                    }
                    else {
                        retornaPonteiro();
                        return new Token(Tag.OP_ASS, "=", n_line, n_column);
                    }
		case 4:
                    if (c == '=') { // Estado 5
                        estado = 5;
			return new Token(Tag.OP_NE, "!=", n_line, n_column);
                    }
                    else {
                        retornaPonteiro();
                        sinalizaErro("Token incompleto para o caractere ! na linha " + n_line + " e coluna " + n_column);
			return null;
                    }
                case 6:
                    if (c == '=') { // Estado 7
                        estado = 7;
			return new Token(Tag.OP_LE, "<=", n_line, n_column);
                    }
                    else { // Estado 8
                        estado = 8;
			retornaPonteiro();
			return new Token(Tag.OP_LT, "<", n_line, n_column);
                    }
                case 9:
                    if (c == '=') { // Estado 10
                        estado = 10;
                        return new Token(Tag.OP_GE, ">=", n_line, n_column);
                    }
                    else { // Estado 11
                        estado = 11;
                        retornaPonteiro();
                        return new Token(Tag.OP_GT, ">", n_line, n_column);
                    }
                case 12:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                        // Permanece no estado 12
                    }
                    else if (c == '.') {
                        lexema.append(c);
                        estado = 26;
                    }
                    else { // Estado 13
                        estado = 13;
                        retornaPonteiro();						
			return new Token(Tag.num_const, lexema.toString(), n_line, n_column);
                    }
                    break;
		case 14:
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        lexema.append(c);
			// Permanece no estado 14
                    }
                    else { // Estado 15
                        estado = 15;
			retornaPonteiro();  
                        Token token = tabelaSimbolos.retornaToken(lexema.toString());
                        
                        if (token == null) {
                            return new Token(Tag.ID, lexema.toString(), n_line, n_column);
                        }
                        return token;
                    }
                    break;
                case 16:
                    if (c == '/') {
                        estado = 17;
                    } else if(c == '*'){
                      estado = 18;  
                    } else {
                        retornaPonteiro();
			return new Token(Tag.OP_DIV, lexema.toString(), n_line, n_column);
                    }
                    break;
                case 17:
                    if (c == '\n') {
                        
                    } 
                    // Se vier outro, permanece no estado 17
                    break;
                case 18:
                    if(c == '*'){
                        estado = 19;
                    } else {
                        lexema.append(c);
                    }
                    break;
                case 19:
                    if(c == '/'){
                        
                    }  else {
                        lexema.append(c);
                    } 
                case 24:
                    if (c == '"') {
                        estado = 25;
                        return new Token(Tag.LIT, lexema.toString(), n_line, n_column);
                    }
                    else if (lookahead == END_OF_FILE) {
                        sinalizaErro("String deve ser fechada com \" antes do fim de arquivo");
			return null;
                    }
                    else { // Se vier outro, permanece no estado 24
                        lexema.append(c);
                    }
                    break;
                case 26:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                        estado = 27;
                    }
                    else {
                        sinalizaErro("Padrao para double invalido na linha " + n_line + " coluna " + n_column);
			return null;
                    }
                    break;
                case 27:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                    }
                    else {
                        retornaPonteiro();						
			return new Token(Tag.num_const, lexema.toString(), n_line, n_column);
                    }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LexerProfessor lexer = new LexerProfessor("C:\\Users\\kelia\\Documents\\NetBeansProjects\\lexer_exemplo\\Lexer\\testePasc.txt"); // parametro do Lexer: Um programa de acordo com a gramatica
	Token token;
        tabelaSimbolos = new TS();
        
        n_line = 1;

	// Enquanto não houver erros ou não for fim de arquivo:
	do {
            token = lexer.proxToken();
            
            // Imprime token
	    if(token != null) {
                System.out.println("Token: " + token.toString() + "\t Linha: " + n_line + "\t Coluna: " + n_column);
                
                // Verificar se existe o lexema na tabela de símbolos
                
            }
	     
	} while(token != null && token.getClasse() != Tag.EOF);
	lexer.fechaArquivo();
        
        //// Imprimir a tabela de simbolos
        //System.out.println("");
        //System.out.println("Tabela de simbolos:");
        //System.out.println(tabelaSimbolos.toString());
    }
    
}
