package br.com.payments.spotify.application.exception;

public class PaymentData extends RuntimeException {
    public PaymentData(String message) {
        super(message);
    }

    public PaymentData ()
    {
        super("Ocorreu um erro na captura dos dados do seu pagamento");
    }
}
