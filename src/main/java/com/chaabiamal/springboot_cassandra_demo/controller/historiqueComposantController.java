package com.chaabiamal.springboot_cassandra_demo.controller;
import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.repository.historiqueComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.historiqueComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.historiqueComposantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;

@RestController
@RequestMapping("/amal/historiqueComposants")

public class historiqueComposantController {
    @Autowired
    private final historiqueComposantService historiquecomposantService;
    @Autowired
    private historiqueComposantRepository historiquecomposantRepository;

    @Autowired
    private historiqueComposantMapper historiquecomposantMapper;

    @Autowired
    public historiqueComposantController(historiqueComposantService historiquecomposantService) {
        this.historiquecomposantService = historiquecomposantService;
    }
    @PostMapping("")
    public ResponseEntity<historiqueComposantDTO> addHistoriqueComposant(@Valid @RequestBody historiqueComposantDTO historiqueComposantDTO) throws URISyntaxException {
        historiqueComposant historiqueComposant = historiquecomposantMapper.toEntity(historiqueComposantDTO);
        historiqueComposant.setId(UUID.randomUUID());
        historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiqueComposant);
        historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HEADER_NAME, "A new historiqueComposant is created with identifier " + savedHistoriqueComposantDTO.id())
                .body(savedHistoriqueComposantDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<historiqueComposantDTO> findById(@PathVariable("id") UUID historiqueComposantId) {
        historiqueComposant historiqueComposant = historiquecomposantRepository.findByid(historiqueComposantId).orElseThrow(
                () -> new ResourceNotFoundException("HistoriqueComposant not found" + historiqueComposantId));
        historiqueComposantDTO historiqueComposantDTO = historiquecomposantMapper.toDto(historiqueComposant);
        return ResponseEntity.ok().body(historiqueComposantDTO);
    }

    @GetMapping("/historique/{id}")
    public ResponseEntity<List<historiqueComposantDTO>> findByIdComposant(@PathVariable("id") UUID ComposantId) {
        List<historiqueComposant> historiqueComposants = historiquecomposantRepository.findByidComposant(ComposantId);

        if (historiqueComposants.isEmpty()) {
            // Si aucun historique n'est trouvé, renvoyer une liste vide avec un statut 200
            List<historiqueComposantDTO> historiqueComposantDTOs = new ArrayList<>(); // Utilisation de ArrayList pour une liste vide
            return ResponseEntity.ok().body(historiqueComposantDTOs);
        }

        // Si l'historique est trouvé, mapper les objets en DTO et renvoyer la liste avec un statut 200
        List<historiqueComposantDTO> historiqueComposantDTOs = historiqueComposants.stream()
                .map(historiquecomposantMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(historiqueComposantDTOs);
    }

    @GetMapping("")
    public List<historiqueComposantDTO> getHistoriqueComposants() {
        List<historiqueComposant> historiqueComposants = historiquecomposantRepository.findAll();
        return historiqueComposants.stream()
                .map(historiquecomposantMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHistoriqueComposant(@PathVariable("id") UUID historiqueComposantId) {
        historiquecomposantService.delete(historiqueComposantId);
        return ResponseEntity.ok().build();
    }
}
