package ru.jungle.creator.tables.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.jungle.creator.tables.repository.TableRepository;

@TestConfiguration
public class TestApplicationRunner {
    @SpyBean(name = "tableRepository")
    private TableRepository tableRepository;
}
