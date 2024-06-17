package com.flypass.account.adapter.out.rest.customer;


import com.flypass.account.application.port.out.CustomerPort;
import com.flypass.account.domain.Customer;
import com.flypass.account.exception.domain.BadRequestException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerAdapter implements CustomerPort {

    private final WebClient.Builder webClientBuilder;

    @Value("${customer.path}")
    private String customerBaseUrl;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(customerBaseUrl).build();
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return webClient.get()
                .uri("/customers/{id}", id)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorMap(ex -> new BadRequestException("Customer not found"))
                .blockOptional();
    }

}
