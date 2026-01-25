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
        if (!doc.matches("[0-9.\\-/]+")) {
            return -5; 
        }
        String limpo = limparDocumento(doc);
        if (limpo.matches("(\\d)\\1{10,13}")) {
                return -7; 
            }
        if (limpo.length() < 11) return -2;
        if (limpo.length() > 11 && limpo.length() < 14) return -3;
        if (limpo.length() > 14) return -4;

        return 0; 
    }

    public static boolean isCPF(String doc) {
        return limparDocumento(doc).length() == 11;
    }
    public static int validarTelefone(String tel) {
        if (tel == null || tel.trim().isEmpty()) return -1; 
        
        // Regex: Aceita apenas números (de 10 a 11 dígitos)
        if (!tel.matches("\\d{10,11}")) {
            return -50; 
        }
        return 0;
    }

    public static int validarEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) return -10;
        if (endereco.length() < 15) return -51; 
        return 0;
    }
    

    // --- VALIDAÇÃO DE NOMES / TEXTOS ---
 
    public static int validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) return -10;
        if (texto.trim().length() < 3) return -11; // Nomes com menos de 3 letras
        return 0;
    }

    // --- VALIDAÇÃO DE VALORES MONETÁRIOS (Patrimônio, Preço, etc) ---

    public static int validarValorPositivo(double valor) {
        if (valor <= 0) return -20;
        return 0;
    }

    // --- VALIDAÇÃO DE OPÇÕES DE MENU ---

    public static int validarOpcaoMenu(int opcao, int min, int max) {
        if (opcao < min || opcao > max) return -30;
        return 0;
    }
    /// --- VALIDAÇÃO da entrada para cadastro de Ativos ---
    public static int validarPreco(double preco) {
        if (preco < 0) return -20; 
        return 0;
    }

    public static int validarTicker(String ticker) {
        if (ticker == null || ticker.trim().length() < 3) return -31; 
        if (!ticker.matches("[A-Z0-9]+")) return -32;
        return 0;
    }

    
}