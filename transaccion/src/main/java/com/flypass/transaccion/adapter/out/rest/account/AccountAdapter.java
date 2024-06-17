package com.flypass.transaccion.adapter.out.rest.account;


import com.flypass.transaccion.adapter.out.rest.account.model.ChangeSaldoAccountCommand;
import com.flypass.transaccion.application.port.out.AccountPort;
import com.flypass.transaccion.domain.Account;
import com.flypass.transaccion.exception.domain.BadRequestException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
    public Account changeSaldoByAccountId(Long accountId, ChangeSaldoAccountCommand changeSaldoAccountCommand) {
        return webClient.patch()
                .uri("/accounts/{id}/saldo", accountId)
                .header("Content-Type", "application/json")
                .bodyValue(changeSaldoAccountCommand)
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(ex -> new BadRequestException("No hay suficiente saldo para realizar la transaccion"))
                .block();
    }
}
