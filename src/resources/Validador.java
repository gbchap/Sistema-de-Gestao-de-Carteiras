package resources;

public class Validador {

    /**
     * tira todos os caracteres que nao sao numeros do documento
     */
    public static String limparDocumento(String doc) {
        if (doc == null) return "";
        return doc.replaceAll("[^0-9]", "");
    }

    /**
     * códigos de erro que criei para as mensagens de validação de documentos
     * 0: Sucesso
     * -1: Vazio
     * -2: Muito curto
     * -3: Tamanho intermediário inválido
     * -4: Muito longo
     * -5: Contém caracteres inválidos (letras ou símbolos)
     */

    public static int validarDocumento(String doc) {
        if (doc == null || doc.trim().isEmpty()) return -1;
        // O Regex aceita: números, pontos, hífens e barras (pq são usados em CPF/CNPJ)
        if (!doc.matches("[0-9.\\-/]+")) {
            return -5; 
        }
        String limpo = limparDocumento(doc);
        if (limpo.matches("(\\d)\\1{10,13}")) {
                return -7; // Código para "Documento com dígitos repetidos/falso"
            }
        if (limpo.length() < 11) return -2;
        if (limpo.length() > 11 && limpo.length() < 14) return -3;
        if (limpo.length() > 14) return -4;

        return 0; // quando da bom
    }

    public static boolean isCPF(String doc) {
        return limparDocumento(doc).length() == 11;
    }
}