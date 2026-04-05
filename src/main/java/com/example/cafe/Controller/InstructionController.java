package com.example.cafe.Controller;

import com.example.cafe.Entity.Drink.Instruction;
import com.example.cafe.Service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/instructions")
public class InstructionController {
    private final InstructionService instructionService;

    @GetMapping
    public ResponseEntity<List<Instruction>> getAllInstructions() {
        return new ResponseEntity<>(instructionService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public Instruction create(@RequestBody Instruction instruction) {
        return instructionService.save(instruction);
    }

    @PutMapping("/update/{id}")
    public Instruction update(@PathVariable Integer id, @RequestBody Instruction instruction) {
        instruction.setId(id); // Ensure we update the right one
        return instructionService.save(instruction);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        instructionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
