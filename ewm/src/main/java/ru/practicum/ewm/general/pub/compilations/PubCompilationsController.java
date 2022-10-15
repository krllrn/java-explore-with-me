package ru.practicum.ewm.general.pub.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.compilation.CompilationDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
public class PubCompilationsController {
    private final PubCompilationsService pubCompilationsService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                                @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Map<String, Object> parameters = Map.of(
                "pinned", pinned,
                "from", from,
                "size", size
        );
        log.info("Get comps with parameters: {}, {}, {}", pinned, from, size);
        return pubCompilationsService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompById(@PathVariable Long compId) {
        log.info("Get comp by id: {}", compId);
        return pubCompilationsService.getCompById(compId);
    }
}
