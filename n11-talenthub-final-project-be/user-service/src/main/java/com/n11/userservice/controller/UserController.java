package com.n11.userservice.controller;

import com.n11.userservice.common.base.BaseRestResponse;
import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.dto.user.UserSaveRequest;
import com.n11.userservice.dto.user.UserUpdateRequest;
import com.n11.userservice.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseRestResponse<UserResponse>> save(@RequestBody @Valid UserSaveRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(userService.save(request)), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseRestResponse<UserResponse>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(BaseRestResponse.of(userService.getById(id)), OK);
    }

    @GetMapping
    public ResponseEntity<BaseRestResponse<List<UserResponse>>> getAll() {
        return new ResponseEntity<>(BaseRestResponse.of(userService.getAll()), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseRestResponse<UserResponse>> update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        return new ResponseEntity<>(BaseRestResponse.of(userService.update(id, request)), OK);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<BaseRestResponse<UserResponse>> deactivate(@PathVariable Long id) {
        return new ResponseEntity<>(BaseRestResponse.of(userService.deactivate(id)), OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
