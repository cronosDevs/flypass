package com.flypass.transaccion.application.service;


import com.flypass.transaccion.application.port.out.AccountPort;
import com.flypass.transaccion.application.port.out.CustomerPort;
import com.flypass.transaccion.domain.Account;
import com.flypass.transaccion.domain.Customer;
import com.flypass.transaccion.exception.domain.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerPort customerPort;

    @Mock
    private AccountPort accountPort;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = createTestCustomer();

        when(customerPort.findByNumeroIdentificacion(anyString())).thenReturn(Optional.empty());
        when(customerPort.createCustomer(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);
        assertTrue(createdCustomer.enable());
        verify(customerPort, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testCreateCustomerMinor() {
        Customer customer = createTestCustomerWithBirthDate(LocalDate.now().minusYears(16));

        assertThrows(BadRequestException.class, () -> customerService.createCustomer(customer));
    }

    @Test
    void testCreateCustomerAlreadyExists() {
        Customer customer = createTestCustomer();

        when(customerPort.findByNumeroIdentificacion(anyString())).thenReturn(Optional.of(customer));

        assertThrows(BadRequestException.class, () -> customerService.createCustomer(customer));
    }

    @Test
    void testUpdateCustomer() {
        Customer existingCustomer = createTestCustomer();
        Customer updatedDetails = createTestCustomerWithDifferentDetails();

        when(customerPort.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(customerPort.createCustomer(any(Customer.class))).thenReturn(existingCustomer);

        Customer updatedCustomer = customerService.updateCustomer(1L, updatedDetails);

        assertNotNull(updatedCustomer);
        verify(customerPort, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        Customer existingCustomer = createTestCustomer();

        when(customerPort.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(accountPort.findAccountByClienteId(anyLong())).thenReturn(Collections.emptyList());
        when(customerPort.createCustomer(any(Customer.class))).thenReturn(existingCustomer);

        assertDoesNotThrow(() -> customerService.deleteCustomer(1L));
        verify(customerPort, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testDeleteCustomerWithAccounts() {
        Customer existingCustomer = createTestCustomer();
        List<Account> accounts = List.of(Account.builder().build());

        when(customerPort.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(accountPort.findAccountByClienteId(anyLong())).thenReturn(accounts);

        assertThrows(BadRequestException.class, () -> customerService.deleteCustomer(1L));
    }

    @Test
    void testGetCustomer() {
        Customer customer = createTestCustomer();

        when(customerPort.findById(anyLong())).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomer(1L);

        assertNotNull(foundCustomer);
        assertEquals(customer, foundCustomer);
        verify(customerPort, times(1)).findById(anyLong());
    }

    @Test
    void testCheckMinor() {
        Customer minorCustomer = createTestCustomerWithBirthDate(LocalDate.now().minusYears(16));

        assertThrows(BadRequestException.class, () -> customerService.createCustomer(minorCustomer));
    }

    @Test
    void testCheckExistence() {
        Customer customer = createTestCustomer();

        when(customerPort.findByNumeroIdentificacion(anyString())).thenReturn(Optional.of(customer));

        assertThrows(BadRequestException.class, () -> customerService.createCustomer(customer));
    }

    @Test
    void testFindById() {
        Customer customer = createTestCustomer();

        when(customerPort.findById(anyLong())).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomer(1L);

        assertNotNull(foundCustomer);
        verify(customerPort, times(1)).findById(anyLong());
    }

    @Test
    void testFindByIdWithException() {
        when(customerPort.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> customerService.getCustomer(1L));
    }

    private Customer createTestCustomer() {
        return Customer.builder()
                .id(1L)
                .numeroIdentificacion("12345")
                .nombres("Test")
                .apellidos("User")
                .correoElectronico("test@example.com")
                .fechaNacimiento(Date.from(LocalDate.now().minusYears(20).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .enable(true)
                .build();
    }

    private Customer createTestCustomerWithBirthDate(LocalDate birthDate) {
        return Customer.builder()
                .id(1L)
                .numeroIdentificacion("12345")
                .nombres("Test")
                .apellidos("User")
                .correoElectronico("test@example.com")
                .fechaNacimiento(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .enable(true)
                .build();
    }

    private Customer createTestCustomerWithDifferentDetails() {
        return Customer.builder()
                .id(1L)
                .numeroIdentificacion("12345")
                .nombres("Updated")
                .apellidos("User")
                .correoElectronico("updated@example.com")
                .fechaNacimiento(Date.from(LocalDate.now().minusYears(20).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .enable(true)
                .build();
    }

}
