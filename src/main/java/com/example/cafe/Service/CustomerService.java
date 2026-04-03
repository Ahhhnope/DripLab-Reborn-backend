package com.example.cafe.Service;

import com.example.cafe.DTO.CustomerDTO;
import com.example.cafe.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final ModelMapper modelMapper;

    public List<CustomerDTO> getAll() {
        return customerRepo.findAll().stream().map(c -> modelMapper.map(c, CustomerDTO.class)).toList();
    }

    public void delete(Integer id) { customerRepo.deleteById(id); }
}
