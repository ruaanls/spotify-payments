package br.com.payments.spotify.application.exception;

public class PaymentProcess extends RuntimeException {
    public PaymentProcess(String message) {
        super(message);
    }

    public PaymentProcess()
    {
        super("Ocorreu um erro no processamento do seu pagamento, tente novamente sem fazer login e utilizando cartão de testes");
    }
}
