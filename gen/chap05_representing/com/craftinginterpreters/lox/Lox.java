package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
  static boolean hadError = false;
  public static void main(String[] args) throws IOException {
    Token plus = new Token(TokenType.PLUS, "+", null, 0);
    Token minus = new Token(TokenType.MINUS, "-", null, 0);
    Token times = new Token(TokenType.STAR, "*", null, 0);
    Expr ast = new Expr.Binary(
            new Expr.Grouping(
                new Expr.Binary(
                  new Expr.Literal(1),
                  plus,
                  new Expr.Literal(2)
                )
            ),
            times,
            new Expr.Grouping(
                new Expr.Binary(
                    new Expr.Literal(4),
                    minus,
                    new Expr.Literal(3)
                )
            )
    );
    RpnVisitor rpnVisitor = new RpnVisitor();
    List<String> rpnLexemes = ast.accept(rpnVisitor);
    System.out.println(String.join(" ", rpnLexemes));
  }
  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));

    // Indicate an error in the exit code.
    if (hadError) System.exit(65);
  }
  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (;;) { // [repl]
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) break;
      run(line);
      hadError = false;
    }
  }
  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    // For now, just print the tokens.
    for (Token token : tokens) {
      System.out.println(token);
    }
  }
  static void error(int line, String message) {
    report(line, "", message);
  }

  private static void report(int line, String where,
                             String message) {
    System.err.println(
        "[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }
}
