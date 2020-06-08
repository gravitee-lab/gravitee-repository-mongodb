/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.repository.mongodb.common;

import io.gravitee.repository.mongodb.management.mapper.GraviteeDozerMapper;
import io.gravitee.repository.mongodb.management.mapper.GraviteeMapper;
import io.gravitee.repository.mongodb.management.transaction.NoTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import java.net.URI;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public abstract class AbstractRepositoryConfiguration extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment environment;

    @Override
    protected String getDatabaseName() {
        String uri = environment.getProperty("management.mongodb.uri");
        if (uri != null && ! uri.isEmpty()) {
            return URI.create(uri).getPath().substring(1);
        }

        return environment.getProperty("management.mongodb.dbname", "gravitee");
    }

    @Bean
    public GraviteeMapper graviteeMapper() {
        return new GraviteeDozerMapper();
    }

    @Bean
    public AbstractPlatformTransactionManager graviteeTransactionManager() {
        return new NoTransactionManager();
    }
}
