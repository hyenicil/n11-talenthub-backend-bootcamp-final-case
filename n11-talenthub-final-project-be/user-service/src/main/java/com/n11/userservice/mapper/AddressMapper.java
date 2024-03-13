package com.n11.userservice.mapper;

import com.n11.userservice.dto.address.AddressResponse;
import com.n11.userservice.dto.address.AddressSaveRequest;
import com.n11.userservice.dto.address.AddressUpdateRequest;
import com.n11.userservice.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "address.status", constant = "ACTIVE")
    Address mapAddressSaveRequestToAddress(AddressSaveRequest request);

    @Mapping(target = "userId", source = "address.user.id")
    AddressResponse mapAddressToAddressResponse(Address address);

    @Mapping(target = "userId", source = "address.user.id")
    List<AddressResponse> mapAddressListToAddressResponseList(List<Address> addressList);

    Address mapAddressUpdateRequestToAddress(@MappingTarget Address address, AddressUpdateRequest request);
}
