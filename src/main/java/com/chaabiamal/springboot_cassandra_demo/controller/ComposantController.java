package com.chaabiamal.springboot_cassandra_demo.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.chaabiamal.springboot_cassandra_demo.Exception.ResourceNotFoundException;
import com.chaabiamal.springboot_cassandra_demo.model.Composant;
import com.chaabiamal.springboot_cassandra_demo.model.historiqueComposant;
import com.chaabiamal.springboot_cassandra_demo.repository.ComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.repository.historiqueComposantRepository;
import com.chaabiamal.springboot_cassandra_demo.service.ComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.dto.ComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.dto.historiqueComposantDTO;
import com.chaabiamal.springboot_cassandra_demo.service.historiqueComposantService;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.ComposantMapper;
import com.chaabiamal.springboot_cassandra_demo.service.mapper.historiqueComposantMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;

@RestController

@RequestMapping("/amal/composants")
public class ComposantController {
    @Autowired
    private final ComposantService composantService;
    @Autowired
    private ComposantRepository composantRepository;

    @Autowired
    private ComposantMapper composantMapper;
    @Autowired
    public ComposantController(ComposantService composantService) {
        this.composantService = composantService;
    }
    //****************************************************************************************************************
    @Autowired
    private  historiqueComposantService historiquecomposantService;
    @Autowired
    private historiqueComposantRepository historiquecomposantRepository;

    @Autowired
    private historiqueComposantMapper historiquecomposantMapper;

    @GetMapping("")
    public List<ComposantDTO> getComposant() {
        List<Composant> composants = composantRepository.findAll();

        return composants.stream()
                .map(composant -> {
                    ComposantDTO composantDTO = composantMapper.toDto(composant);
                    return composantDTO;
                })
                .collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public ResponseEntity<ComposantDTO> findById(@PathVariable("id") UUID composantId) {
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));

        ComposantDTO composantDTO = composantMapper.toDto(composant); // Utilisation de composantMapper
        return ResponseEntity.ok().body(composantDTO);
    }



    @PostMapping("withId")
    public ResponseEntity<ComposantDTO> addComposantID(@Valid @RequestBody ComposantDTO composantDTO) throws URISyntaxException {
        Composant composant = composantMapper.toEntity(composantDTO); // Utilisation de composantMapper
        Composant savedComposant = composantRepository.save(composant); // Utilisation de composantRepository
        ComposantDTO savedComposantDTO = composantMapper.toDto(savedComposant); // Utilisation de composantMapper

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HEADER_NAME, "A new composant is created with identifier " + savedComposantDTO.id()) // Utilisation de getId
                .body(savedComposantDTO); // Utilisation de savedComposantDTO
    }


    @GetMapping("{id}/{laststatusID}")
    public ResponseEntity<ComposantDTO> findByIdCheckStatus(@PathVariable("id") UUID composantId, @PathVariable("laststatusID")int status) {
        if(0>=status || status>=5){
            /*Composant composant = composantRepository.findLastStatusByStatusID(status).orElseThrow(
                    () -> new ResourceNotFoundException("id status not found et invaliiiiid: " + status));*/
            System.out.println("id status not found et invaliiiiid: " + status);
            return null;
        }
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));
        ComposantDTO composantDTO=null;
        if(composant.getStatus()!=status){
            System.out.println("status has changed");

            //**********************************************
            historiqueComposant historiquecomposant = new historiqueComposant(); // Create a new instance
            // Set properties of historiquecomposant
            historiquecomposant.setId(UUID.randomUUID());
            historiquecomposant.setComposantId(composantId);
            historiquecomposant.setDate(LocalDateTime.now());
            historiquecomposant.setLastStatus(composant.getLastStatus());
            historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiquecomposant);
            historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);
            //***********************
            composant.setLastStatus(composantRepository.findLastStatusByStatusID(composant.getStatus()));
            composant.setLastStatusChangeTime(composant.getLastStatusChangeTime());
            composant.setStatus(status);
            composant.setModifiedDate(LocalDateTime.now());
            Composant savedComposant = composantRepository.save(composant);
            composantDTO = composantMapper.toDto(savedComposant);

            //***********************


        }else {
            System.out.println("status not change");
            composantDTO = composantMapper.toDto(composant);
        }
        return ResponseEntity.ok().body(composantDTO);
    }


   /* @GetMapping("{id}/{addinfo}")
    public ResponseEntity<ComposantDTO> findByIdCheckaddInfo(@PathVariable("id") UUID composantId, @PathVariable("addinfo") String addinfo) {

        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));
        ComposantDTO composantDTO=null;
        if(composant.getStatus()!=status){
            System.out.println("status has changed");

            //**********************************************
            historiqueComposant historiquecomposant = new historiqueComposant(); // Create a new instance
            // Set properties of historiquecomposant
            historiquecomposant.setId(UUID.randomUUID());
            historiquecomposant.setComposantId(composantId);
            historiquecomposant.setDate(LocalDateTime.now());
            historiquecomposant.setLastStatus(composant.getLastStatus());
            historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiquecomposant);
            historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);
            //***********************
            composant.setLastStatus(composantRepository.findLastStatusByStatusID(composant.getStatus()));
            composant.setLastStatusChangeTime(composant.getLastStatusChangeTime());
            composant.setStatus(status);
            composant.setModifiedDate(LocalDateTime.now());
            Composant savedComposant = composantRepository.save(composant);
            composantDTO = composantMapper.toDto(savedComposant);

            //***********************


        }else {
            System.out.println("status not change");
            composantDTO = composantMapper.toDto(composant);
        }
        return ResponseEntity.ok().body(composantDTO);
    }*/
   @PostMapping("")
   public ResponseEntity<ComposantDTO> addComposant(@Valid @RequestBody ComposantDTO composantDTO) throws URISyntaxException {
       Composant composant = composantMapper.toEntity(composantDTO); // Utilisation de composantMapper
       composant.setId(UUID.randomUUID()); // Correction de setKioskId en setId
       Composant savedComposant = composantRepository.save(composant); // Utilisation de composantRepository
       ComposantDTO savedComposantDTO = composantMapper.toDto(savedComposant); // Utilisation de composantMapper

       return ResponseEntity
               .status(HttpStatus.CREATED)
               .header(HEADER_NAME, "A new composant is created with identifier " + savedComposantDTO.id()) // Utilisation de getId
               .body(savedComposantDTO); // Utilisation de savedComposantDTO
   }
    @PatchMapping("{id}")
    public ResponseEntity<ComposantDTO> partialUpdateComposant(@PathVariable("id") UUID composantId, @RequestBody ComposantDTO composantDTO) {
        Optional<ComposantDTO> updatedComposantOptional = composantService.partialUpdate(composantId, composantDTO);
        if (updatedComposantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ComposantDTO updatedComposantDTO = updatedComposantOptional.get();
        return ResponseEntity.ok().body(updatedComposantDTO);
    }
    @GetMapping("{id}/addinfo")
    public ResponseEntity<String> findByIdCheckaddInfo(@PathVariable("id") UUID composantId,
                                                       @Valid @RequestBody ComposantDTO composantDTO) {
        if (composantDTO == null || composantDTO.additionalInfo() == null) {
            return ResponseEntity.badRequest().body("AdditionalInfo is null");
        }

        // Récupérer la valeur de additionalInfo
        String additionalInfo = composantDTO.additionalInfo();

        // Définir les motifs de recherche pour RAM, CPU, C et D
        String[] patterns = {"RAM=\\d+", "CPU=\\d+", "C=\\d+", "D=\\d+"};

        StringBuilder response = new StringBuilder();

        // Pour chaque motif, chercher dans additionalInfo
        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(additionalInfo);

            // Si le motif est trouvé
            while (m.find()) {
                // Récupérer la valeur et convertir en entier
                String[] parts = m.group().split("=");
                int value = Integer.parseInt(parts[1]);

                // Vérifier si la valeur est inférieure à 20 ou supérieure à 80
                if (value < 20) {
                    response.append(parts[0]).append(" usage is less than 20%\n");
                } else if (value > 80) {
                    response.append(parts[0]).append(" usage is more than 80%\n");
                }
               // else response.append(parts[0]).append(" usage is between 20 and 80%\n");
            }
        }
//***********************
        Optional<ComposantDTO> updatedComposantOptional = composantService.partialUpdate(composantId, composantDTO);
        if (updatedComposantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ComposantDTO updatedComposantDTO = updatedComposantOptional.get();
        //**************************
        // Si aucun motif n'est trouvé
        if (response.length() == 0) {

            return ResponseEntity.ok("No event should be stored everything okey usage is between 20 and 80%");
        }
//***********************************************************************************************
        historiqueComposant historiquecomposant = new historiqueComposant(); // Create a new instance
        // Set properties of historiquecomposant
        historiquecomposant.setId(UUID.randomUUID());
        historiquecomposant.setComposantId(composantId);
        historiquecomposant.setDate(LocalDateTime.now());
        historiquecomposant.setLastStatus(composantRepository.findLastStatusBycomposantID(composantId));
        //ajouter champs additionalInfo;
        historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiquecomposant);
        historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);

        //***********************
        // Retourner la réponse

        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("{id}/addinfo/{addinfo}")
    public ResponseEntity<String> findByIdCheckaddInfoonlyOnewithdoublepoint(@PathVariable("id") UUID composantId, @PathVariable("addinfo") String addinfo) {
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));

        Optional<ComposantDTO> composantOptional = composantService.findById(composantId);
        if (composantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String additionalInfo = composantOptional.get().additionalInfo();
        String message = composantService.checkAdditionalInfo(additionalInfo, addinfo);
        return ResponseEntity.ok().body(message);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComposant(@PathVariable("id") UUID composantId) {
        composantService.delete(composantId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ComposantDTO> updateComposant(@PathVariable("id") UUID composantId,
                                                        @Valid @RequestBody ComposantDTO composantDTO) {
        if (!composantService.findOne(composantId).isPresent()) {
            throw new ResourceNotFoundException("Composant not found with id: " + composantId);
        }
        composantDTO.id(); // Mise à jour de l'ID dans le DTO
        ComposantDTO updatedComposantDTO = composantService.update(composantDTO);
        return ResponseEntity.ok(updatedComposantDTO);
    }


}
