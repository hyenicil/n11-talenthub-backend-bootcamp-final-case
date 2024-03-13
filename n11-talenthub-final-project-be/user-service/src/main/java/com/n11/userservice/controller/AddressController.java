package com.n11.userservice.controller;

import com.n11.userservice.common.base.BaseRestResponse;
import com.n11.userservice.dto.address.AddressResponse;
import com.n11.userservice.dto.address.AddressSaveRequest;
import com.n11.userservice.dto.address.AddressUpdateRequest;
import com.n11.userservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Mehmet Akif Tanisik
 */
@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Validated
public class  AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<BaseRestResponse<AddressResponse>> save(@RequestBody @Valid AddressSaveRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(addressService.save(request)), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseRestResponse<AddressResponse>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(BaseRestResponse.of(addressService.getById(id)), OK);
    }

    @GetMapping("/with-userId/{userId}")
    public ResponseEntity<BaseRestResponse<List<AddressResponse>>> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(BaseRestResponse.of(addressService.getByUserId(userId)), OK);
    }

    @GetMapping
    public ResponseEntity<BaseRestResponse<List<AddressResponse>>> getAll() {
        return new ResponseEntity<>(BaseRestResponse.of(addressService.getAll()), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseRestResponse<AddressResponse>> update(@PathVariable Long id, @RequestBody @Valid AddressUpdateRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(addressService.update(id, request)), OK);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<BaseRestResponse<AddressResponse>> deactivate(@PathVariable Long id) {
        return new ResponseEntity<>(BaseRestResponse.of(addressService.deactivate(id)), OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
