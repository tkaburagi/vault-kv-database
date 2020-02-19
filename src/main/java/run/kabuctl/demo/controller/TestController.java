package run.kabuctl.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.*;
import run.kabuctl.demo.BeanUtil;
import run.kabuctl.demo.entity.Secrets;
import run.kabuctl.demo.repo.UserJpaRepository;

import javax.annotation.processing.Generated;

@RestController
public class TestController {

    @Autowired
    VaultTemplate vaultTemplate;

    @Autowired
    UserJpaRepository userJpaRepository;

    @GetMapping (value = "/api/v1/get-all")
    Object getGenericData() throws Exception {
        return userJpaRepository.findAll();
    }
}
