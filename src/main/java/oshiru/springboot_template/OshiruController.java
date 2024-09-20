package oshiru.springboot_template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import oshiru.springboot_template.logging.Logger;
import oshiru.springboot_template.logging.LoggerFactory;

@RestController
public class OshiruController {
    private static final Logger log = LoggerFactory.getLogger(OshiruController.class);

    private final OshiruService oshiruService;

    OshiruController(OshiruService oshiruService) {
        this.oshiruService = oshiruService;
        log.debug("**Oshiru** OshiruController initialized");
    }

    @GetMapping("/test")
    public String test() {
        log.debug("at Controller");

        this.oshiruService.test();

        return "Called!!";
    }

    @GetMapping("/causeError")
    public String causeError() {
        log.debug("at Controller");

        this.oshiruService.causeError();

        return "never reach here";
    }
}
