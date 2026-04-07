package com.example.cafe.Service;

import com.example.cafe.DTO.PromoCodeDTO;
import com.example.cafe.Entity.PromoCode;
import com.example.cafe.Exception.CustomResourceNotFound;
import com.example.cafe.Repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final ModelMapper modelMapper;

    public List<PromoCodeDTO> getAll() {
        return promoCodeRepository.findAll().stream().map(pc -> modelMapper.map(pc, PromoCodeDTO.class)).toList();
    }

    public PromoCodeDTO getById(int id) {
        return promoCodeRepository.findById(id).map(pc -> modelMapper.map(pc, PromoCodeDTO.class)).orElseThrow(() -> new CustomResourceNotFound("promo code not found: "+id));
    }

    public PromoCodeDTO save(PromoCodeDTO promoCodeDTO) {
        PromoCode pc = modelMapper.map(promoCodeDTO, PromoCode.class);
        return modelMapper.map(promoCodeRepository.save(pc), PromoCodeDTO.class);
    }

    public void delete(int id) {
        promoCodeRepository.deleteById(id);
    }
}
