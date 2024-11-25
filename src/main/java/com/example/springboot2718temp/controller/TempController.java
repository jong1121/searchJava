package com.example.springboot2718temp.controller;

import com.example.springboot2718temp.dto.TempDTO;
import com.example.springboot2718temp.service.TempService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TempController {
    private final TempService tempService;

    @GetMapping
    public String index() {
        return "temp/index";
    }

    @PostMapping("/getData")
    @ResponseBody
    public ResponseEntity<List<TempDTO>> getData (){
        return ResponseEntity.ok(tempService.getData());
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity api(@CookieValue String txCode) {
        return ResponseEntity.ok(tempService.api(txCode));
    }


}
