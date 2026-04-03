package com.example.cafe.Controller;

import com.example.cafe.DTO.WorkerDTO;
import com.example.cafe.Service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workers")
public class WorkerController {
    private final WorkerService workerService;

    @GetMapping
    public List<WorkerDTO> getAll() {
        return workerService.getAll();
    }

    @PostMapping("/add")
    public WorkerDTO add(@RequestBody WorkerDTO dto) {
        return workerService.save(dto);
    }

    @DeleteMapping("/remove/{id}")
    public void delete(@PathVariable Integer id) {
        workerService.delete(id);
    }
}
