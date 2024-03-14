package com.n11.userservice.service.impl;

import com.n11.userservice.dto.address.AddressResponse;
import com.n11.userservice.dto.address.AddressSaveRequest;
import com.n11.userservice.dto.address.AddressUpdateRequest;
import com.n11.userservice.entity.Address;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.enums.Status;
import com.n11.userservice.mapper.AddressMapper;
import com.n11.userservice.repository.AddressRepository;
import com.n11.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository mockAddressRepository;
    @Mock
    private AddressMapper mockAddressMapper;
    @Mock
    private UserService mockUserService;

    private AddressServiceImpl addressServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        addressServiceImplUnderTest = new AddressServiceImpl(mockAddressRepository, mockAddressMapper, mockUserService);
    }

    @Test
    void shouldSaveAddress() {

        // given
        AddressSaveRequest request = new AddressSaveRequest("city", "district", "street", "location", 0L);

        AddressResponse expectedResult = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setDistrict("district");
        address.setStatus(Status.ACTIVE);

        User user = new User();
        address.setUser(user);

        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setEmail("email");

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setDistrict("district");
        address1.setStatus(Status.ACTIVE);

        User user2 = new User();
        address1.setUser(user2);

        AddressResponse addressResponse = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        // when
        when(mockAddressMapper.mapAddressSaveRequestToAddress(
                new AddressSaveRequest("city", "district", "street", "location", 0L))).thenReturn(address);

        when(mockUserService.getUserById(0L)).thenReturn(user1);

        when(mockAddressRepository.save(any(Address.class))).thenReturn(address1);

        when(mockAddressMapper.mapAddressToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse result = addressServiceImplUnderTest.save(request);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetAddressById() {

        // given
        AddressResponse expectedResult = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setDistrict("district");
        address1.setStatus(Status.ACTIVE);

        User user = new User();
        address1.setUser(user);

        Optional<Address> address = Optional.of(address1);

        AddressResponse addressResponse = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        // when
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        when(mockAddressMapper.mapAddressToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse result = addressServiceImplUnderTest.getById(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }



    @Test
    void shouldGetAllAddresses() {

        // given
        List<AddressResponse> expectedResult = List.of(
                new AddressResponse(0L, "city", "district", "street", "location", 0L));

        Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setDistrict("district");
        address.setStatus(Status.ACTIVE);

        List<Address> addressList = List.of(address);

        // when
        when(mockAddressRepository.findAll()).thenReturn(addressList);

        when(mockAddressMapper.mapAddressListToAddressResponseList(addressList)).thenReturn(expectedResult);

        List<AddressResponse> result = addressServiceImplUnderTest.getAll();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }





    @Test
    void shouldUpdateAddress() {

        // given
        AddressUpdateRequest request = new AddressUpdateRequest("city", "district", "street", "location");

        AddressResponse expectedResult = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setDistrict("district");
        address1.setStatus(Status.ACTIVE);

        User user = new User();
        address1.setUser(user);

        Optional<Address> address = Optional.of(address1);

        Address address2 = new Address();
        address2.setId(0L);
        address2.setCity("city");
        address2.setDistrict("district");
        address2.setStatus(Status.ACTIVE);

        User user1 = new User();
        address2.setUser(user1);

        Address address3 = new Address();
        address3.setId(0L);
        address3.setCity("city");
        address3.setDistrict("district");
        address3.setStatus(Status.ACTIVE);

        User user2 = new User();
        address3.setUser(user2);

        AddressResponse addressResponse = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        // when
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        when(mockAddressMapper.mapAddressUpdateRequestToAddress(any(Address.class),
                eq(new AddressUpdateRequest("city", "district", "street", "location")))).thenReturn(address2);

        when(mockAddressRepository.save(any(Address.class))).thenReturn(address3);

        when(mockAddressMapper.mapAddressToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse result = addressServiceImplUnderTest.update(0L, request);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldDeleteAddress() {

        // given
        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setDistrict("district");
        address1.setStatus(Status.ACTIVE);

        User user = new User();
        address1.setUser(user);

        Optional<Address> address = Optional.of(address1);

        // when
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        addressServiceImplUnderTest.delete(0L);

        // then
        verify(mockAddressRepository).delete(any(Address.class));
    }



    @Test
    void shouldDeactivateAddress() {

        // given
        AddressResponse expectedResult = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setDistrict("district");
        address1.setStatus(Status.ACTIVE);

        User user = new User();
        address1.setUser(user);

        Address address2 = new Address();
        address2.setId(0L);
        address2.setCity("city");
        address2.setDistrict("district");
        address2.setStatus(Status.ACTIVE);

        User user1 = new User();
        address2.setUser(user1);

        Optional<Address> address = Optional.of(address1);

        AddressResponse addressResponse = new AddressResponse(0L, "city", "district", "street", "location", 0L);

        // when
        when(mockAddressRepository.findById(0L)).thenReturn(address);

        when(mockAddressRepository.save(any(Address.class))).thenReturn(address2);

        when(mockAddressMapper.mapAddressToAddressResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse result = addressServiceImplUnderTest.deactivate(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldGetAddressesByUserId() {

        // given
        List<AddressResponse> expectedResult = List.of(
                new AddressResponse(0L, "city", "district", "street", "location", 0L));

        Address address = new Address();
        address.setId(0L);
        address.setCity("city");
        address.setDistrict("district");
        address.setStatus(Status.ACTIVE);

        List<Address> addressList = List.of(address);

        // when
        when(mockAddressRepository.findAddressesByUserId(0L)).thenReturn(addressList);

        when(mockAddressMapper.mapAddressListToAddressResponseList(addressList)).thenReturn(expectedResult);

        List<AddressResponse> result = addressServiceImplUnderTest.getByUserId(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }




    @Test
    void shouldGetAddressEntityById() {

        // given
        Address address1 = new Address();
        address1.setId(0L);
        address1.setCity("city");
        address1.setDistrict("district");
        address1.setStatus(Status.ACTIVE);

        User user = new User();
        address1.setUser(user);

        // when
        when(mockAddressRepository.findById(0L)).thenReturn(Optional.of(address1));

        Address result = addressServiceImplUnderTest.getAddressById(0L);

        // then
        assertThat(result).isEqualTo(address1);
    }


}
