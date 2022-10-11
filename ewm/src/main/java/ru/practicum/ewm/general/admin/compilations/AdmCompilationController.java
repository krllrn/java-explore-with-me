package ru.practicum.ewm.general.admin.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.compilation.CompilationDto;
import ru.practicum.ewm.models.compilation.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
public class AdmCompilationController {
    private final AdmCompilationService admCompilationService;

    @Autowired
    public AdmCompilationController(AdmCompilationService admCompilationService) {
        this.admCompilationService = admCompilationService;
    }

    @PostMapping
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Add new compilation with title: {}", newCompilationDto.getTitle());
        return admCompilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Delete compilation with ID: {}", compId);
        admCompilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEventFromCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("Delete event with ID: {} from compilation with ID: {}", eventId, compId);
        admCompilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void addEventToCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("Add event with ID: {} to compilation with ID: {}", eventId, compId);
        admCompilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("Unpin compilation with ID: {} from main page", compId);
        admCompilationService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void pinCompilation(@PathVariable Long compId) {
        log.info("Pin compilation with ID: {} to main page", compId);
        admCompilationService.pinCompilation(compId);
    }
}
