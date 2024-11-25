package com.example.springboot2718temp.service;

import com.example.springboot2718temp.dto.TempDTO;
import com.example.springboot2718temp.mapper.TempMapper;
import com.example.springboot2718temp.service2.Temp2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TempService {
    private final TempMapper tempMapper;
    private final Temp2Service temp2Service;

    private final String SELECT_DATA_SAMPLE1 = "AAABBC";
    public List<TempDTO> getData() {
        return tempMapper.findAll();
    }

    public List<TempDTO> api(String txCode) {
        if(txCode.equals(SELECT_DATA_SAMPLE1)){
            String a = "a";
            Integer b = 2;
            return temp2Service.getData();

        }
        return null;
    }
}
