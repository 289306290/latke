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
package org.b3log.latke.intercept;

import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Lifecycle;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertNotNull;

/**
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Sep 29, 2018
 */
final public class BeforeMethodTestCase {

    /**
     * Bean manager.
     */
    private BeanManager beanManager;

    public static final List<Class<?>> packageClasses = Arrays.asList(A.class, B.class);

    @BeforeTest
    @SuppressWarnings("unchecked")
    public void beforeTest() {
        System.out.println("before BeforeMethodTestCase");

        Latkes.initRuntimeEnv();
        beanManager = BeanManager.getInstance();
        Lifecycle.startApplication(packageClasses);
        final A a = beanManager.getReference(A.class);
        assertNotNull(a);

        final B b = beanManager.getReference(B.class);
        assertNotNull(b);
    }

    /**
     * This method will be run after the test. Shutdown Latke IoC container.
     */
    @AfterTest
    public void afterTest() {
        System.out.println("afterTest BeforeMethodTestCase");
        Lifecycle.endApplication();
    }

    @Test
    public void beforeMethod0() {
        System.out.println("beforeMethod0");
        final A a = beanManager.getReference(A.class);

        a.oneMethod("TestArg");
    }
}
