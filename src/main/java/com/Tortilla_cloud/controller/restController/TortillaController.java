package com.Tortilla_cloud.controller.restController;

import com.Tortilla_cloud.model.Tortilla;
import com.Tortilla_cloud.repository.TortillaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/tortillas")
@CrossOrigin(origins = "*")
public class TortillaController {

    private final TortillaRepository tortillaRepository;

    public TortillaController(TortillaRepository tortillaRepository) {
        this.tortillaRepository = tortillaRepository;
    }

    //get all tortillas
    @GetMapping
    public Iterable<Tortilla> allTortillas(){
        log.info("Fetching all tortillas");
        return tortillaRepository.findAll();
    }

    // get tortillas by id
    @GetMapping("/{id}")
    public ResponseEntity<Tortilla> tortillaById(@PathVariable Long id){
        Optional<Tortilla> OptTortilla = tortillaRepository.findById(id);
        if (OptTortilla.isPresent()){
            log.info("Fetched Tortilla with Id: {} " , id);
            return ResponseEntity.ok(OptTortilla.get());
        }
        log.warn("Tortilla with Id {} not found " , id);
        return ResponseEntity.notFound().build();
    }

    //post , create a new tortilla
    @PostMapping
    public ResponseEntity<Tortilla> saveTortilla(@Valid @RequestBody Tortilla tortilla){
        log.info("Creating new Tortilla: {}", tortilla.getName());
        Tortilla saved = tortillaRepository.save(tortilla);
        return new ResponseEntity<>(saved , HttpStatus.CREATED);
    }

    //put , update an existing tortilla
    @PutMapping("/{id}")
    public ResponseEntity<Tortilla> updateTortilla(@Valid @RequestBody Tortilla tortilla ,
                                                   @PathVariable Long id){
        if (!tortillaRepository.existsById(id)) {
            log.warn("Tortilla with id {} not found for update" , id);
            return ResponseEntity.notFound().build();
        }
        tortilla.setId(id);
        log.info("Updating Tortilla with id: {}" , id);
        return ResponseEntity.ok(tortillaRepository.save(tortilla));
    }
    //delete the existing tortilla
    @DeleteMapping("/{id}")
    public void deleteTortilla(@PathVariable Long id){
        if (tortillaRepository.existsById(id)) {
            log.info("Deleting Tortilla with id: {}" , id);
            tortillaRepository.deleteById(id);
        }else {
            log.warn("Tortilla with id {} not found for deletion " , id);
        }
    }
}
