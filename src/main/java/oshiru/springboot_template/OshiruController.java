package oshiru.springboot_template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Transactional
public class OshiruController {

    OshiruController() {
        log.info("**Oshiru** OshiruController initialized");
    }

    @GetMapping("/test")
    public String test() {
        log.info("**Oshiru** test");

        return "Called!!";
    }
}
