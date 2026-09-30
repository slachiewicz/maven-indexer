/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.index;

import java.nio.file.Path;

import org.apache.maven.di.Injector;
import org.apache.maven.di.Key;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Replaces Sisu's {@code InjectedTest}: builds a Maven 4 DI {@link Injector} from the
 * {@code META-INF/maven/org.apache.maven.api.di.Inject} index on the classpath and injects the test instance.
 *
 * @author cstamas
 */
public class AbstractTestSupport {
    private Injector injector;

    @BeforeEach
    public void setUp() throws Exception {
        injector = Injector.create().discover(getClass().getClassLoader());
        injector.injectInstance(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        injector = null;
    }

    public final <T> T lookup(Class<T> type) {
        return injector.getInstance(type);
    }

    public final <T> T lookup(Class<T> type, String name) {
        return injector.getInstance(Key.of(type, name));
    }

    public final String getBasedir() {
        return System.getProperty("basedir", Path.of("").toAbsolutePath().toString());
    }

    protected Path getTestPath(String path) {
        return Path.of(getBasedir(), path);
    }
}
