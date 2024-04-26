package com.chaabiamal.springboot_cassandra_demo.controller;

import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.Status;
import com.chaabiamal.springboot_cassandra_demo.model.TypeStatus;
import com.chaabiamal.springboot_cassandra_demo.repository.StatusRepository;
import com.chaabiamal.springboot_cassandra_demo.service.StatusService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.StatusDTO;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.StatusMapper;
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
@RequestMapping("/amal/Status")

public class StatusController {

    @Autowired
    private StatusService StatusService;

    @Autowired
    private StatusRepository StatusRepository;

    @Autowired
    private StatusMapper StatusMapper;

    @PostMapping("")
    public ResponseEntity<StatusDTO> addKiosk(@Valid @RequestBody StatusDTO statusDTO) throws URISyntaxException {
        Status status = StatusMapper.toEntity(statusDTO);
        TypeStatus[] types = TypeStatus.values();
        int id = -1; // ID par défaut si aucune correspondance n'est trouvée
        for (TypeStatus type : types) {
            if (type.name().equalsIgnoreCase(statusDTO.Value()+"")) {
                id = type.ordinal() + 1; // L'ID est l'indice de l'énumération + 1
                status.setValue(type);
                break;
            }
        }
        if (id == -1) {
            // Si aucune correspondance n'est trouvée, renvoyez une erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statusDTO);
        }
        status.setID(id);
        Status savedStatus = StatusRepository.save(status);
        StatusDTO savedStatusDTO = StatusMapper.toDto(savedStatus);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HEADER_NAME, "A new kiosk is created with identifier " + savedStatusDTO.ID())
                .body(savedStatusDTO);
    }

    /**/
    @GetMapping("{id}")
    public ResponseEntity<StatusDTO> findById(@PathVariable("id") int StatusId) {
        Status kiosk = StatusRepository.findById(StatusId).orElseThrow(
                () -> new ResourceNotFoundException("Kiosk not found" + StatusId));
        StatusDTO StatusDTO = StatusMapper.toDto(kiosk);
        return ResponseEntity.ok().body(StatusDTO);
    }

    @GetMapping("")
    public List<StatusDTO> getStatus() {
        List<Status> Status = StatusRepository.findAll();
        return Status.stream()
                .map(StatusMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteKiosk(@PathVariable(value = "id") int StatusId) {
        StatusRepository.deleteById(StatusId);
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
