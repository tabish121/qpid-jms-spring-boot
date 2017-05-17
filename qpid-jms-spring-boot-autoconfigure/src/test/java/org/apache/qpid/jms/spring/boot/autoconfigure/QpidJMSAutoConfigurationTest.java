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

import static org.junit.Assert.assertEquals;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * Test that Qpid JMS Auto Configuration works.
 */
public class QpidJMSAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void customNativeConnectionFactory() {
        load(EmptyConfiguration.class,
             "spring.qpidjms.remoteURL=amqp://127.0.0.1:5672",
             "spring.qpidjms.username=foo",
             "spring.qpidjms.password=bar");

        JmsTemplate jmsTemplate = this.context.getBean(JmsTemplate.class);
        JmsConnectionFactory connectionFactory =
            this.context.getBean(JmsConnectionFactory.class);

        assertEquals(jmsTemplate.getConnectionFactory(), connectionFactory);
        assertEquals("amqp://127.0.0.1:5672", connectionFactory.getRemoteURI());
        assertEquals("foo", connectionFactory.getUsername());
        assertEquals("bar", connectionFactory.getPassword());
    }

    @Configuration
    static class EmptyConfiguration {}

    private void load(Class<?> config, String... environment) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        EnvironmentTestUtils.addEnvironment(applicationContext, environment);
        applicationContext.register(config);
        applicationContext.register(QpidJMSAutoConfiguration.class, JmsAutoConfiguration.class);
        applicationContext.refresh();
        this.context = applicationContext;
    }
}
