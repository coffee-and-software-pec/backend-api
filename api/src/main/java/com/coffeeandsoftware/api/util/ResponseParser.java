package com.coffeeandsoftware.api.util;

public class ResponseParser {

    // Gambiarra para procurar o trecho da response que diz
    // se existe profanidades no código. Se a lista de
    // profanidades na response estiver vazia (que acontece
    // quando o caractere "[" que abre a lista for seguido
    // de um caractere "]") entao a lista é vazia e não tem
    // profanidades no texto.
    public static boolean hasProfanities(String response) {
        int searchScopeStart = response.indexOf("profanity");
        String reducedScope = response.substring(searchScopeStart);
        int newScopeStart = reducedScope.indexOf("[");
        String newScope = reducedScope.substring(newScopeStart);
        if (newScope.indexOf("]") == 1) return false;
        return true;
    }
}
