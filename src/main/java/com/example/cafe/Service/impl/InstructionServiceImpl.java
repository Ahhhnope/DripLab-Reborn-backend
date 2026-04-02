package com.example.cafe.Service.impl;

import com.example.cafe.Entity.Drink.Instruction;
import com.example.cafe.Repository.Drink.InstructionRepository;
import com.example.cafe.Service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructionServiceImpl implements InstructionService {
    private final InstructionRepository instructionRepo;

    @Override
    public List<Instruction> getAll() {
        return instructionRepo.findAll();
    }

    @Override
    public Instruction getById(Integer id) {
        return instructionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Instruction not found: " + id));
    }

    @Transactional
    @Override
    public Instruction save(Instruction instruction) {
        return instructionRepo.save(instruction);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        instructionRepo.deleteById(id);
    }
}
