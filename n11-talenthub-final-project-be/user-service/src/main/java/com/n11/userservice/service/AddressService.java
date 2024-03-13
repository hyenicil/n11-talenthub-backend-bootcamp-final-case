package com.n11.userservice.service;

import com.n11.userservice.dto.address.AddressResponse;
import com.n11.userservice.dto.address.AddressSaveRequest;
import com.n11.userservice.dto.address.AddressUpdateRequest;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
public interface AddressService {
    AddressResponse save(AddressSaveRequest request);
    AddressResponse getById(Long id);
    List<AddressResponse>  getAll();
    AddressResponse update(Long id, AddressUpdateRequest request);
    void delete(Long id);
    AddressResponse deactivate(Long id);
    List<AddressResponse> getByUserId(Long userId);
}
