package com.example.cafe.Controller;

import com.example.cafe.DTO.CustomerDTO;
import com.example.cafe.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.getAll();
    }

    @DeleteMapping("/remove/{id}")
    public void delete(@PathVariable Integer id) {
        customerService.delete(id);
    }
}
