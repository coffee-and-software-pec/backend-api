package com.coffeeandsoftware.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class ResponseParser {

    // Gambiarra para procurar o trecho da response que diz
    // se existe profanidades no código. Se a lista de
    // profanidades na response estiver vazia (que acontece
    // quando o caractere "[" que abre a lista for seguido
    // de um caractere "]") entao a lista é vazia e não tem
    // profanidades no texto.
    public static boolean hasProfanities(String response) throws JsonProcessingException {
        boolean result = false;
        Map map = new ObjectMapper().readValue(response, Map.class);
        Map profanity = (Map) map.get("profanity");
        List matches = (List) profanity.get("matches");
        result = matches.size() > 0;
//        int searchScopeStart = response.indexOf("profanity");
//        String reducedScope = response.substring(searchScopeStart);
//        int newScopeStart = reducedScope.indexOf("[");
//        String newScope = reducedScope.substring(newScopeStart);
//        if (newScope.indexOf("]") == 1) return false;
        return result;
    }
}
