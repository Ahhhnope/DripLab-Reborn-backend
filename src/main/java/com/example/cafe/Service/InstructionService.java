package com.example.cafe.Service;

import com.example.cafe.Entity.Drink.Instruction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstructionService {
    List<Instruction> getAll();
    Instruction getById(Integer id);
    Instruction save(Instruction instruction);
    void delete(Integer id);
}
