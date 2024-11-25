package com.example.springboot2718temp.mapper;

import com.example.springboot2718temp.dto.TempDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TempMapper {
    List<TempDTO> findAll();
}
