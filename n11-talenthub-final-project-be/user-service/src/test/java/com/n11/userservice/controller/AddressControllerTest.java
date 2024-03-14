package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.userservice.dto.address.AddressResponse;
import com.n11.userservice.dto.address.AddressSaveRequest;
import com.n11.userservice.dto.address.AddressUpdateRequest;
import com.n11.userservice.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService mockAddressService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void shouldSaveValidAddressRequest() throws Exception {

        // given
        AddressSaveRequest request = new AddressSaveRequest("city", "district", "street", "45.42,45.42", 0L);

        String requestAsString = objectMapper.writeValueAsString(request);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/addresses")
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void shouldGetAddressById() throws Exception {

        // given
        AddressResponse addressResponse = new AddressResponse(1L, "city", "district", "street", "45.42,45.42", 0L);

        // when
        when(mockAddressService.getById(0L)).thenReturn(addressResponse);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/addresses/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAddressByUserId() throws Exception {

        // given
        List<AddressResponse> addressResponses = List.of(
                new AddressResponse(0L, "city", "district", "street", "45.42,45.42", 0L));

        // when
        when(mockAddressService.getByUserId(0L)).thenReturn(addressResponses);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/addresses/with-userId/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAddressByIdWhenAddressServiceReturnsNoItems() throws Exception {

        // when
        when(mockAddressService.getByUserId(0L)).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/addresses/with-userId/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllAddresses() throws Exception {

        // given
        List<AddressResponse> addressResponses = List.of(
                new AddressResponse(0L, "city", "district", "street", "45.42,45.42", 0L));


        // when
        when(mockAddressService.getAll()).thenReturn(addressResponses);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/addresses")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void shouldNotGetAnyAddressWhenAddressServiceReturnsNoItem() throws Exception {

        // when
        when(mockAddressService.getAll()).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/addresses")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldUpdateAddress() throws Exception {

        // given
        AddressUpdateRequest request =new AddressUpdateRequest("city", "district", "street", "45.42,45.42");

        String requestAsString = objectMapper.writeValueAsString(request);

        // when
        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/addresses/{id}", 0)
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldDeactivateAddress() throws Exception {

        // given
        AddressResponse addressResponse = new AddressResponse(0L, "city", "district", "street", "45.42,45.42", 0L);

        // when
        when(mockAddressService.deactivate(0L)).thenReturn(addressResponse);

        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/addresses/deactivate/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldDeleteAddress() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/addresses/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(mockAddressService).delete(0L);
    }
}
