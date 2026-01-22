package resources;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validador {
    public static int validarData(String dataStr) {
        if (dataStr == null || !dataStr.matches("\\d{2}/\\d{2}/\\d{4}")) return -40; // Formato inválido

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, dtf);
            if (data.isAfter(LocalDate.now())) return -41; // Data no futuro
            return 0;
        } catch (DateTimeParseException e) {
            return -40;
        }
    }
    /*
     tira todos os caracteres que nao sao numeros do documento
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

    // --- VALIDAÇÃO DE NOMES / TEXTOS ---
    /**
     * Erros: 0 (Ok), -10 (Vazio ou apenas espaços), -11 (Muito curto)
     */
    public static int validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) return -10;
        if (texto.trim().length() < 3) return -11; // Nomes com menos de 3 letras
        return 0;
    }

    // --- VALIDAÇÃO DE VALORES MONETÁRIOS (Patrimônio, Preço, etc) ---
    /**
     * Erros: 0 (Ok), -20 (Valor negativo ou zero)
     */
    public static int validarValorPositivo(double valor) {
        if (valor <= 0) return -20;
        return 0;
    }

    // --- VALIDAÇÃO DE OPÇÕES DE MENU ---
    /**
     * Erros: 0 (Ok), -30 (Opção fora do intervalo permitido)
     */
    public static int validarOpcaoMenu(int opcao, int min, int max) {
        if (opcao < min || opcao > max) return -30;
        return 0;
    }

    
}