/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.qpid.jms.spring.boot.autoconfigure;

import javax.jms.ConnectionFactory;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto Configuration class for Apache Qpid JMS
 */
@Configuration
@AutoConfigureBefore(JmsAutoConfiguration.class)
@ConditionalOnClass({ConnectionFactory.class, JmsConnectionFactory.class})
@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties(QpidJMSProperties.class)
public class QpidJMSAutoConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(QpidJMSAutoConfiguration.class);

    @Autowired
    private QpidJMSProperties properties;

    @Bean
    public JmsConnectionFactory connectionFactory() {

        try {
            JmsConnectionFactory factory = new JmsConnectionFactory(properties.getRemoteURL());

            factory.setUsername(properties.getUsername());
            factory.setPassword(properties.getPassword());

            return factory;
        } catch (Exception ex) {
            LOG.error("Exception while createing Qpid JMS Connection Factory.", ex);
            throw new IllegalStateException("Failed to create the Qpid JMS ConnectionFactory, " +
                "make sure the client Jar is on the Classpath.", ex);
        }
    }
}
