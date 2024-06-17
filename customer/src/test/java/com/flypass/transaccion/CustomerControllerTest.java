package com.flypass.transaccion;

import com.flypass.transaccion.adapter.in.web.CustomerController;
import com.flypass.transaccion.adapter.in.web.mapper.CustomerMapper;
import com.flypass.transaccion.adapter.in.web.model.CreateCustomerCommand;
import com.flypass.transaccion.adapter.in.web.model.CustomerResponseCommand;
import com.flypass.transaccion.adapter.in.web.model.UpdateCustomerCommand;
import com.flypass.transaccion.application.port.in.CustomerUseCase;
import com.flypass.transaccion.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerUseCase customerUseCase;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand();
        Customer customer = Customer.builder().build();
        CustomerResponseCommand customerResponseCommand = CustomerResponseCommand.builder().build();

        when(customerMapper.customerCommandToCustomer(any(CreateCustomerCommand.class))).thenReturn(customer);
        when(customerUseCase.createCustomer(any(Customer.class))).thenReturn(customer);
        when(customerMapper.customerToCustomerResponseCommand(any(Customer.class))).thenReturn(customerResponseCommand);

        ResponseEntity<CustomerResponseCommand> responseEntity = customerController.createCustomer(createCustomerCommand);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerResponseCommand, responseEntity.getBody());
    }

    @Test
    void testUpdateCustomer() {
        UpdateCustomerCommand updateCustomerCommand = new UpdateCustomerCommand();
        Customer customer = Customer.builder().build();
        CustomerResponseCommand customerResponseCommand = CustomerResponseCommand.builder().build();

        when(customerMapper.updateCustomerCommandToCustomer(any(UpdateCustomerCommand.class))).thenReturn(customer);
        when(customerUseCase.updateCustomer(anyLong(), any(Customer.class))).thenReturn(customer);
        when(customerMapper.customerToCustomerResponseCommand(any(Customer.class))).thenReturn(customerResponseCommand);

        ResponseEntity<CustomerResponseCommand> responseEntity = customerController.updateCustomer(updateCustomerCommand, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerResponseCommand, responseEntity.getBody());
    }

    @Test
    void testGetCustomer() {
        CustomerResponseCommand customerResponseCommand = CustomerResponseCommand.builder().build();

        when(customerUseCase.getCustomer(anyLong())).thenReturn(Customer.builder().build());
        when(customerMapper.customerToCustomerResponseCommand(any(Customer.class))).thenReturn(customerResponseCommand);

        ResponseEntity<CustomerResponseCommand> responseEntity = customerController.getCustomer(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerResponseCommand, responseEntity.getBody());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerUseCase).deleteCustomer(anyLong());

        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerUseCase, times(1)).deleteCustomer(anyLong());
    }
}