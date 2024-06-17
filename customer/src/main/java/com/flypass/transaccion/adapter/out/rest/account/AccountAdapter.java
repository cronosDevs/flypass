package com.flypass.transaccion.adapter.out.rest.account;


import com.flypass.transaccion.application.port.out.AccountPort;
import com.flypass.transaccion.domain.Account;
import com.flypass.transaccion.exception.domain.BadRequestException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountAdapter implements AccountPort {

    private final WebClient.Builder webClientBuilder;

    @Value("${account.path}")
    private String accountBaseUrl;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(accountBaseUrl).build();
    }

    @Override
    public List<Account> findAccountByClienteId(Long clienteId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/accounts")
                        .queryParam("clienteId", clienteId)
                        .build())
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList()
                .onErrorMap(ex -> new BadRequestException("Accounts not found"))
                .block();
    }

}
