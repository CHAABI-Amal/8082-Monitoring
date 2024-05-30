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
import org.springframework.context.annotation.Bean;
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
@CrossOrigin("*")
@RequestMapping("/amal/composants")
public class ComposantController {

    private final ComposantService composantService;
    @Autowired
    private ComposantRepository composantRepository;

    @Autowired
    private ComposantMapper composantMapper;

    //****************************************************************************************************************

    @Autowired
    private historiqueComposantRepository historiquecomposantRepository;

    @Autowired
    private historiqueComposantMapper historiquecomposantMapper;



    private final RestTemplate restTemplate;
 /**/
@Autowired
    public ComposantController(ComposantService composantService,RestTemplate restTemplate) {
        this.composantService = composantService;
        this.restTemplate = restTemplate;
    }


    //******************************************* pour communiquer avec MS 1 ***************************************************888
    private static final String COMPOSANT_URL_MS = "http://localhost:8081/";
//*******************************************


//**************************************************** trouver 2 composant mn setting *******************************************************************************
    @GetMapping("/find/{nbr}")
    public List<ComposantDTO> fetchStudents(@PathVariable int nbr) {
        return composantService.fetchStudents(nbr);
    }
//*********************************************************************************************** A tester **********************************************************************88

    @GetMapping("/find")
    public List<ComposantDTO> fetchStudents() {
        return composantService.fetchStudents();
    }
//*********************************************************************************************** A tester **********************************************************************88
@GetMapping("/findId/{id}")
public ComposantDTO fetchStudentId(@PathVariable UUID id) {
    // Appeler l'API pour récupérer le composant en tant qu'objet Composant
    Composant composant = restTemplate.exchange(
            COMPOSANT_URL_MS + "amal/composants/" + id,
            HttpMethod.GET, null, Composant.class).getBody();

    // Créer un objet ComposantDTO à partir du composant récupéré
    ComposantDTO composantDTO = composantMapper.toDto(composant);

    // Retourner l'objet ComposantDTO
    return composantDTO;
}


//*********************************************************************************************** A tester **********************************************************************88

    @GetMapping("/findForSavebyId/{id}")
    public List<ComposantDTO> fetchStudentsSave(@PathVariable UUID id) {
        // Fetch composants by their UUIDs
        ResponseEntity<List<ComposantDTO>> response = restTemplate.exchange(
                COMPOSANT_URL_MS + "amal/composants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ComposantDTO>>() {});

        List<ComposantDTO> composants = response.getBody();
        if (composants == null || composants.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if the response is null or empty
        }

        // Filter composants with the provided UUID
        List<ComposantDTO> filteredComposants = composants.stream()
                .filter(composant -> composant.id().equals(id))
                .collect(Collectors.toList());

        if (filteredComposants.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no composants with the provided UUID are found
        }

        // Save the filtered composants with the same UUIDs
        List<ComposantDTO> savedComposants = new ArrayList<>();
        for (ComposantDTO composantDTO : filteredComposants) {
            ComposantDTO savedComposantDTO = composantService.save(composantDTO);
            savedComposants.add(savedComposantDTO);
        }

        return savedComposants;
    }
    @GetMapping("/findForSavebyId2/{id}")
    public List<ComposantDTO> fetchStudentsSave2(@PathVariable UUID id) {
        // Fetch composants by their UUIDs
        ResponseEntity<List<ComposantDTO>> response = restTemplate.exchange(
                COMPOSANT_URL_MS + "amal/composants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ComposantDTO>>() {});

        List<ComposantDTO> composants = response.getBody();
        if (composants == null || composants.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if the response is null or empty
        }

        // Filter composants with the provided UUID
        List<ComposantDTO> filteredComposants = composants.stream()
                .filter(composant -> composant.id().equals(id))
                .collect(Collectors.toList());

        if (filteredComposants.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no composants with the provided UUID are found
        }

        // Save the filtered composants with the same UUIDs
        List<ComposantDTO> savedComposants = new ArrayList<>();
        for (ComposantDTO composantDTO : filteredComposants) {
            // Create a new ComposantDTO with the same data but a new UUID
            ComposantDTO newComposantDTO = new ComposantDTO(
                    composantDTO.id(), // Generate a new UUID
                    composantDTO.name(),
                    composantDTO.statusId(),
                    composantDTO.value(),
                    composantDTO.lastStatusChangeTime(),
                    composantDTO.lastStatus(),
                    composantDTO.currentStatus(),
                    composantDTO.code(),
                    composantDTO.machineId(),
                    composantDTO.componentTypeId(),
                    composantDTO.model(),
                    composantDTO.isDeleted(),
                    composantDTO.composantCreatedDate(),
                    composantDTO.composantModifiedDate()
            );

            // Save the composant with the new UUID
            ComposantDTO savedComposantDTO = composantService.save(newComposantDTO);
            savedComposants.add(savedComposantDTO);
        }

        return savedComposants;
    }

//*********************************************************************************************** A tester **********************************************************************88

    @GetMapping("/findForSave/{nbr}")
    public List<ComposantDTO> fetchStudentsSave(@PathVariable int nbr) {
        ResponseEntity<List<ComposantDTO>> response = restTemplate.exchange(
                COMPOSANT_URL_MS + "amal/composants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ComposantDTO>>() {}); // Utilisation de ParameterizedTypeReference

        List<ComposantDTO> composants = response.getBody();
        if (composants == null || composants.isEmpty()) {
            return Collections.emptyList(); // Retourne une liste vide si la réponse est nulle ou vide
        }

        // Si le nombre demandé est supérieur à la taille de la liste, retourner la liste complète
        int size = Math.min(nbr, composants.size());

        // Sauvegarder les 'nbr' premiers éléments de la liste dans la base de données
        List<ComposantDTO> savedComposants = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ComposantDTO composantDTO = composants.get(i);

            // Mettre l'UUID de l'élément dans la zone de requête avant de le sauvegarder
            composantDTO.id(); // Remplacer par composantDTO.getId() si l'UUID est fourni dans le DTO
            // Sauvegarder le composant dans la base de données
            ComposantDTO savedComposantDTO = composantService.save(composantDTO);
            savedComposants.add(savedComposantDTO);
        }

        return savedComposants;
    }


    //*********************************************************************************************** A tester **********************************************************************88

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
//*********************************************************************************************** A tester **********************************************************************88

    @GetMapping("{id}")
    public ResponseEntity<ComposantDTO> findById(@PathVariable("id") UUID composantId) {
        Composant composant = composantRepository.findComposantById(composantId).orElseThrow(
                () -> new ResourceNotFoundException("Composant not found with ID: " + composantId));

        ComposantDTO composantDTO = composantMapper.toDto(composant); // Utilisation de composantMapper
        return ResponseEntity.ok().body(composantDTO);
    }

//*********************************************************************************************** A tester **********************************************************************88


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




//*********************************************************************************************** A tester **********************************************************************88

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
    //*********************************************************************************************** A tester **********************************************************************88

    @PatchMapping("{id}")
    public ResponseEntity<ComposantDTO> partialUpdateComposant(@PathVariable("id") UUID composantId, @RequestBody ComposantDTO composantDTO) {
        Optional<ComposantDTO> updatedComposantOptional = composantService.partialUpdate(composantId, composantDTO);
        if (updatedComposantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ComposantDTO updatedComposantDTO = updatedComposantOptional.get();
        return ResponseEntity.ok().body(updatedComposantDTO);
    }
//*********************************************************************************************** A tester **********************************************************************88


    @GetMapping("{id}/statusid/{laststatusID}")
    public ResponseEntity<ComposantDTO> findByIdCheckStatus(@PathVariable("id") UUID composantId, @PathVariable("laststatusID")int status) {

        return composantService.findByIdCheckStatus(composantId,status);
    }
    //*********************************************************************************************** A tester **********************************************************************88
    @GetMapping("{id}/value/{valueComposant}")
    public ResponseEntity<String> findByIdCheckaddInfo(@PathVariable("id") UUID composantId,
                                                       @PathVariable("valueComposant") int value) {
           return composantService.findByIdCheckaddInfo(composantId,value);
            }
//*********************************************************************************************** A tester **********************************************************************88


    @GetMapping("{id}/valusjson")
    public ResponseEntity<String> findByIdCheckaddInfo2(@PathVariable("id") UUID composantId,
                                                        @Valid @RequestBody ComposantDTO composantDTO) {
        return composantService.findByIdCheckaddInfo2(composantId,composantDTO);
    }
//*********************************************************************************************** A tester **********************************************************************88


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComposant(@PathVariable("id") UUID composantId) {
        composantService.delete(composantId);
        return ResponseEntity.ok().build();
    }
    //*********************************************************************************************** A tester **********************************************************************88

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
    //*********************************************************************************************** A tester **********************************************************************88

    @GetMapping("/findstring/{id}")
    public String fetchStudentString(@PathVariable UUID id) {
        Composant composant = restTemplate.exchange(
                COMPOSANT_URL_MS + "amal/composants/" + id,
                HttpMethod.GET, null, Composant.class).getBody();
        System.out.println("Composant from composant " + composant);
        return restTemplate.exchange(
                COMPOSANT_URL_MS + "amal/composants/" + id,
                HttpMethod.GET, null, String.class).getBody();
    }
//*********************************************************************************************** A tester **********************************************************************88

    @GetMapping("/findstirng")
    public String fetchStudentsString() {
        return restTemplate.exchange(
                COMPOSANT_URL_MS + "amal/composants",
                HttpMethod.GET, null, String.class).getBody();
    }
    @GetMapping("/machine-details/{machineId}")
    public ResponseEntity<List<ComposantDTO>> findByIdMachine(@PathVariable("machineId") UUID machineId) {
        List<Composant> Composants = composantRepository.findByidMachine(machineId);

        if (Composants.isEmpty()) {
            // Si aucun historique n'est trouvé, renvoyer une liste vide avec un statut 200
            List<ComposantDTO> historiqueComposantDTOs = new ArrayList<>(); // Utilisation de ArrayList pour une liste vide
            return ResponseEntity.ok().body(historiqueComposantDTOs);
        }

        // Si l'historique est trouvé, mapper les objets en DTO et renvoyer la liste avec un statut 200
        List<ComposantDTO> historiqueComposantDTOs = Composants.stream()
                .map(composantMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(historiqueComposantDTOs);
    }


}

