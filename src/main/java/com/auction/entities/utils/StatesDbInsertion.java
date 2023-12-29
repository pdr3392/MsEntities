package com.auction.entities.utils;

import com.auction.entities.repositories.OlxStatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class StatesDbInsertion {
    private OlxStatesRepository olxStatesRepository;
    private DataSource dataSource;
    private ResourceLoader resourceLoader;

    @Autowired
    public void DatabaseInitializerUtil(OlxStatesRepository olxStatesRepository, DataSource dataSource, ResourceLoader resourceLoader) {
        this.olxStatesRepository = olxStatesRepository;
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
    }

    public void initializeOlxStates() throws Exception {
        if (olxStatesRepository.count() == 0) {
            ScriptUtils.executeSqlScript(dataSource.getConnection(),
                    resourceLoader.getResource("classpath:data.sql")
            );
        }
    }
}
