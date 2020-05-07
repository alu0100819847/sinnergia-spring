package org.sinnergia.sinnergia.spring.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.sinnergia.sinnergia.spring.SinnergiaSpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

@ExtendWith(SpringExtension.class)
@SpringBootTest
public @interface TestConfig {

}
