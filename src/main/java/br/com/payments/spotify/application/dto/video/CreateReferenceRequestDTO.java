package br.com.payments.spotify.application.dto.video;

import br.com.payments.spotify.application.dto.PayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReferenceRequestDTO
{
    private Long userId;
    private BigDecimal totalAmount;
    private List<ItemDTO> itens;
    private PayerDTO payer;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDTO{
        private String id;
        private String title;
        private BigDecimal price;
    }
}
