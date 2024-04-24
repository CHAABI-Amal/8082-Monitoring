package com.chaabiamal.springboot_cassandra_demo.controller;

import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.Kiosk;
import com.chaabiamal.springboot_cassandra_demo.repository.KioskRepository;
import com.chaabiamal.springboot_cassandra_demo.service.KioskService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.KioskDTO;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.KioskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;
import org.springframework.http.MediaType;
@RestController
@RequestMapping("/amal/kiosks")
public class KioskController {

    @Autowired
    private KioskService kioskService;

    @Autowired
    private KioskRepository kioskRepository;

    @Autowired
    private KioskMapper kioskMapper;

    @PostMapping("")
    public ResponseEntity<KioskDTO> addKiosk(@Valid @RequestBody KioskDTO kioskDTO) throws URISyntaxException {
        Kiosk kiosk = kioskMapper.toEntity(kioskDTO);
        kiosk.setKioskId(UUID.randomUUID());
        Kiosk savedKiosk = kioskRepository.save(kiosk);
        KioskDTO savedKioskDTO = kioskMapper.toDto(savedKiosk);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HEADER_NAME, "A new kiosk is created with identifier " + savedKioskDTO.kioskId())
                .body(savedKioskDTO);
    }
/**/
    @GetMapping("/kiosks/{id}")
    public ResponseEntity<KioskDTO> findById(@PathVariable("id") UUID kioskId) {
        Kiosk kiosk = kioskRepository.findById(kioskId).orElseThrow(
                () -> new ResourceNotFoundException("Kiosk not found" + kioskId));
        KioskDTO kioskDTO = kioskMapper.toDto(kiosk);
        return ResponseEntity.ok().body(kioskDTO);
    }

    @GetMapping("")
    public List<KioskDTO> getKiosks() {
        List<Kiosk> kiosks = kioskRepository.findAll();
        return kiosks.stream()
                .map(kioskMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteKiosk(@PathVariable(value = "id") UUID kioskId) {
        kioskRepository.deleteById(kioskId);
        return ResponseEntity.ok().build();
    }
    /*
    @PutMapping("/kiosks/{id}")
    public ResponseEntity<KioskDTO> updateKiosk(@PathVariable(value = "id") UUID kioskId,
                                                @Valid @RequestBody KioskDTO kioskDTO) {
        if (!kioskRepository.existsById(kioskId)) {
            throw new ResourceNotFoundException("Kiosk not found with id: " + kioskId);
        }
        Kiosk kiosk = kioskMapper.toEntity(kioskDTO);
        kiosk.setKioskId(kioskId);
        Kiosk updatedKiosk = kioskRepository.save(kiosk);
        KioskDTO updatedKioskDTO = kioskMapper.toDto(updatedKiosk);
        return ResponseEntity.ok(updatedKioskDTO);
    }
*/
    /*
    @PatchMapping("/kiosks/{id}")
    public ResponseEntity<KioskDTO> partialUpdateKiosk(@PathVariable(value = "id") UUID kioskId,
                                                       @RequestBody Map<String, Object> updates) {
        Kiosk kiosk = kioskRepository.findById(kioskId)
                .orElseThrow(() -> new ResourceNotFoundException("Kiosk not found with id: " + kioskId));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    kiosk.setName((String) value);
                    break;
                case "code":
                    kiosk.setCode((String) value);
                    break;
                case "description":
                    kiosk.setDescription((String) value);
                    break;
                case "ipAddress":
                    kiosk.setIpAddress((String) value);
                    break;
                case "machineName":
                    kiosk.setMachineName((String) value);
                    break;
                case "online":
                    kiosk.setOnline((Boolean) value);
                    break;
                default:
                    // Gérer les autres champs ou l'ignorer selon le cas
                    break;
            }
        });

        Kiosk updatedKiosk = kioskRepository.save(kiosk);
        KioskDTO updatedKioskDTO = kioskMapper.toDto(updatedKiosk);
        return ResponseEntity.ok(updatedKioskDTO);
    }
*/
}
