package com.cluster.data.service.error;

import com.cluster.data.annotation.Validator;
import com.cluster.data.repository.DealRepository;
import com.cluster.data.service.dto.DealDTO;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Currency;
import java.util.List;

@Validator
public class DealValidator {

    public List<ErrorValidator> validateNewDeal(DealDTO dealDTO) {
        ValidationBuilder validation = new ValidationBuilder();
        if(dealDTO == null) {
            validation.addError("error.deal.null");
        }else {
            if(!StringUtils.hasText(dealDTO.getFromCurrency()) || !isValidISOCurrencyCode(dealDTO.getFromCurrency())) {
                validation.addError("error.deal.currency.from.not.valid", dealDTO.getFromCurrency());
            }
            if(!StringUtils.hasText(dealDTO.getToCurrency()) || !isValidISOCurrencyCode(dealDTO.getToCurrency())) {
                validation.addError("error.deal.currency.to.not.valid", dealDTO.getToCurrency());
            }
            if(validation.isClean() && dealDTO.getToCurrency().equals(dealDTO.getFromCurrency())) {
                validation.addError("error.deal.currency.same", dealDTO.getToCurrency());
            }
            if(dealDTO.getTimestamp() == null) {
                validation.addError("error.deal.timestamp.not.valid");
            }
            if(dealDTO.getAmount() == null) {
                validation.addError("error.deal.amount.not.valid");
            }
        }
        return validation.build();
    }

    public ErrorValidator addListingError(int index) {
        ErrorValidator errorValidator = new ErrorValidator();
        errorValidator.setErrorKey("error.index");
        errorValidator.setArguments(new Object[]{index + 1});
        return errorValidator;
    }

    private boolean isValidISOCurrencyCode(String currencyCode) {
        return Currency.getAvailableCurrencies().stream().anyMatch(c -> c.getCurrencyCode().equals(currencyCode));
    }
}
