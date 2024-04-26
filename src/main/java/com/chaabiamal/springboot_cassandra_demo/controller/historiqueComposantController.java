package com.chaabiamal.springboot_cassandra_demo.controller;
import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.repository.historiqueComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.historiqueComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.historiqueComposantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;
@RestController
@RequestMapping("/amal/historiqueComposants")

public class historiqueComposantController {
    @Autowired
    private final historiqueComposantService composantService;
    @Autowired
    private historiqueComposantRepository composantRepository;

    @Autowired
    private historiqueComposantMapper composantMapper;

    @Autowired
    public historiqueComposantController(historiqueComposantService composantService) {
        this.composantService = composantService;
    }
    @PostMapping("")
    public ResponseEntity<historiqueComposantDTO> addHistoriqueComposant(@Valid @RequestBody historiqueComposantDTO historiqueComposantDTO) throws URISyntaxException {
        historiqueComposant historiqueComposant = composantMapper.toEntity(historiqueComposantDTO);
        historiqueComposant.setId(UUID.randomUUID());
        historiqueComposant savedHistoriqueComposant = composantRepository.save(historiqueComposant);
        historiqueComposantDTO savedHistoriqueComposantDTO = composantMapper.toDto(savedHistoriqueComposant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HEADER_NAME, "A new historiqueComposant is created with identifier " + savedHistoriqueComposantDTO.id())
                .body(savedHistoriqueComposantDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<historiqueComposantDTO> findById(@PathVariable("id") UUID historiqueComposantId) {
        historiqueComposant historiqueComposant = composantRepository.findById(historiqueComposantId).orElseThrow(
                () -> new ResourceNotFoundException("HistoriqueComposant not found" + historiqueComposantId));
        historiqueComposantDTO historiqueComposantDTO = composantMapper.toDto(historiqueComposant);
        return ResponseEntity.ok().body(historiqueComposantDTO);
    }

    @GetMapping("")
    public List<historiqueComposantDTO> getHistoriqueComposants() {
        List<historiqueComposant> historiqueComposants = composantRepository.findAll();
        return historiqueComposants.stream()
                .map(composantMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoriqueComposant(@PathVariable("id") UUID historiqueComposantId) {
        composantService.delete(historiqueComposantId);
        return ResponseEntity.ok().build();
    }
}
