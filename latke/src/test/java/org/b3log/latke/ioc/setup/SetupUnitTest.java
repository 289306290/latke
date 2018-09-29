/*
 * Copyright (c) 2009-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.latke.ioc.setup;

import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.b3log.latke.ioc.bean.Bean;
import org.b3log.latke.ioc.config.BeanModule;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Jun 20, 2013
 */
final public class SetupUnitTest {

    /**
     * Bean manager.
     */
    private BeanManager beanManager;

    private Bean3 bean3;

    @BeforeTest
    public void beforeTest() {
        System.out.println("before SetupUnitTest");

        Latkes.initRuntimeEnv();

        beanManager = BeanManager.getInstance();

        final Set<Class<?>> beanClasses = new HashSet<>();
        beanClasses.add(Bean1.class);
        beanClasses.add(Bean2.class);
        beanClasses.add(Bean3.class);
        final BeanModule setupModule = new BeanModule("SetupModule", beanClasses);
        Lifecycle.startApplication(null, setupModule);

        final Bean<?> bean = beanManager.getBean("bean3");
        bean3 = (Bean3) beanManager.getReference(bean);
    }

    /**
     * This method will be run after the test. Shutdown Latke IoC container.
     */
    @AfterTest
    public void afterTest() {
        System.out.println("afterTest SetupUnitTest");
        Lifecycle.endApplication();
    }

    @Test
    public void bean3Say() {
        System.out.println("bean3Say");
        assertEquals(bean3.say(), "Bean3");
        assertEquals(bean3.bean1.say(), "Bean1");
        assertEquals(bean3.bean2.say(), "Bean2");
    }
}
