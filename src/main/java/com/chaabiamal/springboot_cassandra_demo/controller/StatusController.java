package com.chaabiamal.springboot_cassandra_demo.controller;

import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.Status;
import com.chaabiamal.springboot_cassandra_demo.repository.StatusRepository;
import com.chaabiamal.springboot_cassandra_demo.service.StatusService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.dto.StatusDTO;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;
import org.springframework.http.MediaType;
@RestController
@CrossOrigin("*")
@RequestMapping("/amal/Status")
public class StatusController{

    @Autowired
    private StatusService kioskService;

    @Autowired
    private StatusRepository kioskRepository;

    @Autowired
    private StatusMapper kioskMapper;

    @PostMapping("")
    public ResponseEntity<StatusDTO> addStatus(@Valid @RequestBody StatusDTO statusDTO) throws URISyntaxException {
        Status kiosk = kioskMapper.toEntity(statusDTO);
        kiosk.setId(UUID.randomUUID());
        Status savedKiosk = kioskRepository.save(kiosk);
        StatusDTO savedKioskDTO = kioskMapper.toDto(savedKiosk);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HEADER_NAME, "A new Status is created with identifier " + savedKioskDTO.id())
                .body(savedKioskDTO);
    }

    /**/
    @GetMapping("{id}")
    public ResponseEntity<StatusDTO> findById(@PathVariable("id") UUID kioskId) {
        Status kiosk = kioskRepository.findById2(kioskId);
        StatusDTO kioskDTO = kioskMapper.toDto(kiosk);
        return ResponseEntity.ok().body(kioskDTO);
    }

    @GetMapping("")
    public List<StatusDTO> getKiosks() {
        List<Status> kiosks = kioskRepository.findAll();
        return kiosks.stream()
                .map(kioskMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteKiosk(@PathVariable(value = "id")UUID kioskId) {
        kioskRepository.deleteByid(kioskId);
        return ResponseEntity.ok().build();
    }
//***************************************************************************

@PatchMapping("{id}")
public ResponseEntity<StatusDTO> partialUpdateStatus(@PathVariable("id") UUID statusId, @RequestBody StatusDTO statusDTO) {
    Optional<StatusDTO> updatedStatusOptional = kioskService.partialUpdate(statusId, statusDTO);
    if (updatedStatusOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    StatusDTO updatedStatusDTO = updatedStatusOptional.get();
    return ResponseEntity.ok().body(updatedStatusDTO);
}
}
