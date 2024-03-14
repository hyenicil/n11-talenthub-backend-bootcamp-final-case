package com.n11.userservice.service.impl;

import com.n11.userservice.dto.address.AddressResponse;
import com.n11.userservice.dto.address.AddressSaveRequest;
import com.n11.userservice.dto.address.AddressUpdateRequest;
import com.n11.userservice.entity.Address;
import com.n11.userservice.entity.User;
import com.n11.userservice.exception.AddressNotFoundException;
import com.n11.userservice.mapper.AddressMapper;
import com.n11.userservice.repository.AddressRepository;
import com.n11.userservice.service.AddressService;
import com.n11.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.n11.userservice.common.error.ErrorMessages.*;
import static com.n11.userservice.entity.enums.Status.INACTIVE;

/**
 * @author Mehmet Akif Tanisik
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserService userService;


    @Override
    public AddressResponse save(AddressSaveRequest request) {

        Address address = addressMapper.mapAddressSaveRequestToAddress(request);

        User user = userService.getUserById(request.userId());

        address.setUser(user);

        return addressMapper.mapAddressToAddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse getById(Long id) {

        Address address = getAddressById(id);

        return addressMapper.mapAddressToAddressResponse(address);
    }

    @Override
    public List<AddressResponse> getAll() {

        List<Address> addressList = addressRepository.findAll();

        return addressMapper.mapAddressListToAddressResponseList(addressList);
    }

    @Override
    public AddressResponse update(Long id, AddressUpdateRequest request) {

        Address address = getAddressById(id);

        Address updatedAddress = addressMapper.mapAddressUpdateRequestToAddress(address, request);

        return addressMapper.mapAddressToAddressResponse(addressRepository.save(updatedAddress));
    }

    @Override
    public void delete(Long id) {

        Address address = getAddressById(id);

        addressRepository.delete(address);
    }

    @Override
    public AddressResponse deactivate(Long id) {

        Address address = getAddressById(id);
        address.setStatus(INACTIVE);

        return addressMapper.mapAddressToAddressResponse(addressRepository.save(address));
    }

    @Override
    public List<AddressResponse> getByUserId(Long userId) {

        List<Address> addressList = addressRepository.findAddressesByUserId(userId);

        return addressMapper.mapAddressListToAddressResponseList(addressList);
    }

    public Address getAddressById(Long id){
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(ADDRESS_NOT_FOUND));
    }
}
