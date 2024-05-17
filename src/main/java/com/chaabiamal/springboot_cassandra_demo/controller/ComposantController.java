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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
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

    //****************************************************************************************************************
    @Autowired
    private  historiqueComposantService historiquecomposantService;
    @Autowired
    private historiqueComposantRepository historiquecomposantRepository;

    @Autowired
    private historiqueComposantMapper historiquecomposantMapper;

    @Autowired
    private final RestTemplate restTemplate;



    @Autowired
    public ComposantController(ComposantService composantService, RestTemplate restTemplate) {
        this.composantService = composantService;
        this.restTemplate = restTemplate;
    }
//*******************************************

    @GetMapping("")
    public List<ComposantDTO> getComposant() {
        List<Composant> composants = composantRepository.findAll();

        if (composants == null) {
            return Collections.emptyList(); // Retourne une liste vide
        }

        return composants.stream()
                .map(composantMapper::toDto) // Utilisation de la référence de méthode
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


    @GetMapping("{id}/statusid/{laststatusID}")
    public ResponseEntity<ComposantDTO> findByIdCheckStatus(@PathVariable("id") UUID composantId, @PathVariable("laststatusID")int status) {
        if(0>=status || status>=6){
            /*Composant composant = composantRepository.findLastStatusByStatusID(status).orElseThrow(
                    () -> new ResourceNotFoundException("id status not found et invaliiiiid: " + status));*/
            System.out.println("id status not found et invaliiiiid: " + status);
            return null;
        }
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));
        ComposantDTO composantDTO=null;
        if(composant.getStatusId()!=status){
            System.out.println("status has changed");

            //**********************************************
            historiqueComposant historiquecomposant = new historiqueComposant(); // Create a new instance
            // Set properties of historiquecomposant
            historiquecomposant.setId(UUID.randomUUID());
            historiquecomposant.setComposantId(composantId);
            historiquecomposant.setDatetime(LocalDateTime.now());
            historiquecomposant.setStatus(composant.getLastStatus());
            historiquecomposant.setValue(composant.getValue());
            historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiquecomposant);
            historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);
            //***********************
            composant.setLastStatus(composantRepository.findLastStatusByStatusID(status));// if u want prevois set composant.getstatusId()
            composant.setLastStatusChangeTime(composant.getLastStatusChangeTime());
            composant.setStatusId(status);
            composant.setLastStatusChangeTime(LocalDateTime.now());
            Composant savedComposant = composantRepository.save(composant);
            composantDTO = composantMapper.toDto(savedComposant);

            //***********************


        }else {
            System.out.println("status not change");
            composantDTO = composantMapper.toDto(composant);
        }
        return ResponseEntity.ok().body(composantDTO);
    }
    //*********************************************************************************************** A tester **********************************************************************88
    @GetMapping("{id}/value/{valueComposant}")
    public ResponseEntity<String> findByIdCheckaddInfo(@PathVariable("id") UUID composantId,
                                                       @PathVariable("valueComposant") int value) {
           return composantService.findByIdCheckaddInfo(composantId,value);
            }
//*****

    @GetMapping("{id}/valusjson")
    public ResponseEntity<String> findByIdCheckaddInfo2(@PathVariable("id") UUID composantId,
                                                        @Valid @RequestBody ComposantDTO composantDTO) {
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));

        String valueString = composantDTO.value(); // Obtenez la valeur du pourcentage sous forme de chaîne
        // Supprimer le caractère '%' de la valeur
        valueString = valueString.substring(0, valueString.length() - 1);

        try {
            // Convertir la valeur en pourcentage en entier
            int value = Integer.parseInt(valueString);

            // Vérifier si la valeur est inférieure à 20% ou supérieure à 80%
            if (value <= 20 || value >= 80) {
                //**************************************** si inf 20 & sup 80 *******************************************************
                historiqueComposant historiquecomposant = new historiqueComposant(); // Create a new instance
                // Set properties of historiquecomposant
                historiquecomposant.setId(UUID.randomUUID());
                historiquecomposant.setComposantId(composantId);
                historiquecomposant.setDatetime(LocalDateTime.now());
                historiquecomposant.setStatus(composant.getLastStatus());
                historiquecomposant.setValue(composant.getValue());
                //ajouter champs additionalInfo;
                historiqueComposant savedHistoriqueComposant = historiquecomposantRepository.save(historiquecomposant);
                historiqueComposantDTO savedHistoriqueComposantDTO = historiquecomposantMapper.toDto(savedHistoriqueComposant);

                //***********************
                composant.setValue(value + "%");
                composantRepository.save(composant); // Mettre à jour la valeur du composant
                if (value <= 20) {
                    return ResponseEntity.ok("La valeur du composant est inférieure à 20%");
                } else {
                    return ResponseEntity.ok("La valeur du composant est supérieure à 80%");
                }
            } else {
                composant.setValue(value + "%");
                composantRepository.save(composant); // Mettre à jour la valeur du composant
                return ResponseEntity.ok("La valeur du composant est dans la plage acceptable entre 20% et 80%");
            }

        } catch (NumberFormatException e) {
            // En cas d'erreur lors de la conversion ou si la valeur n'est pas un pourcentage valide
            return ResponseEntity.badRequest().body("La valeur du composant n'est pas un pourcentage valide");
        }
    }
/**/


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

    @GetMapping("")
    public ResponseEntity<List<ComposantDTO>> getAllComposants() {
        List<ComposantDTO> allComposants = new ArrayList<>();
        int page = 0;
        int size = 5; // Taille de la page souhaitée

        while (true) {
            ResponseEntity<List<ComposantDTO>> response = restTemplate.exchange(
                    "http://localhost:8081/api/composants/paged?page={page}&size={size}",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ComposantDTO>>() {},
                    page,
                    size
            );

            List<ComposantDTO> composants = response.getBody();
            if (composants != null && !composants.isEmpty()) {
                allComposants.addAll(composants);
                page++;
            } else {
                break;
            }
        }

        return ResponseEntity.ok(allComposants);
    }
}

