package com.example.cafe.Service;

import com.example.cafe.DTO.WorkerDTO;
import com.example.cafe.Entity.Worker;
import com.example.cafe.Repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final ModelMapper modelMapper;

    public List<WorkerDTO> getAll() {
        return workerRepository.findAll().stream()
                .map(w -> modelMapper.map(w, WorkerDTO.class)).toList();
    }

    public WorkerDTO save(WorkerDTO dto) {
        Worker worker = modelMapper.map(dto, Worker.class);
        return modelMapper.map(workerRepository.save(worker), WorkerDTO.class);
    }

    public void delete(Integer id) {
        workerRepository.deleteById(id);
    }
}
