package oshiru.springboot_template;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import oshiru.springboot_template.logging.Logger;
import oshiru.springboot_template.logging.LoggerFactory;

@Service
public class OshiruService {
    private static final Logger log = LoggerFactory.getLogger(OshiruService.class);

    @Transactional
    public void test() {
        log.debug("at Service");
    }
}
