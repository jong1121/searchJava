package com.example.springboot2718temp.service2;

import com.example.springboot2718temp.dto.TempDTO;
import com.example.springboot2718temp.mapper.TempMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Temp2Service {
    private final TempMapper tempMapper;

    public List<TempDTO> getData() {
        return tempMapper.findAll();
    }
}
