package com.example.cafe.Service;

import com.example.cafe.Entity.CafeTable;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.CafeTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeTableService {

    private final CafeTableRepository cafeTableRepository;

    public List<CafeTable> getAll() {
        return cafeTableRepository.findAll();
    }

    public CafeTable updateTable(int id, CafeTable cafeTable) {
        CafeTable tableFound = cafeTableRepository.findById(id).orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy bàn id: " + id));

        tableFound.setStatus(cafeTable.getStatus());
        tableFound.setCurrentOrder(cafeTable.getCurrentOrder());

        CafeTable savedTable = cafeTableRepository.save(tableFound);
        return savedTable;
    }

    public CafeTable addCafeTable(CafeTable cafeTable) {

        if (cafeTableRepository.findById(cafeTable.getId()).isPresent()) {
            throw new CustomResourceNotFound("Bàn với số: " + cafeTable.getTableNumber() + " đã tồn tại");
        }

        CafeTable savedTable = cafeTableRepository.save(cafeTable);
        return savedTable;
    }

    public CafeTable deleteCafeTable(int id) {
        CafeTable tableFound = cafeTableRepository.findById(id).orElseThrow(() -> new CustomResourceNotFound("Không tìm thấy bàn id: " + id));

        if (tableFound != null) {
            cafeTableRepository.delete(tableFound);
        }

        return tableFound;
    }
}
