package org.sinnergia.sinnergia.spring;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SinnergiaSpringApplication.class)
public @interface TestConfig {

}
