/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License. 
 */

package org.drools.benchmarks.turtle.runtime.generator;

/**
 * Base class for test resource generators.
 *
 * All needed configuration has to be in xml file that is passed into constructor.
 * Configuration file contains these items:
 *   - rules template file
 *   -
 *
 * Configurations for resource generators are typically stored in resources/config.
 *
 */
public abstract class ResourceGenerator {
    protected final GeneratorConfiguration config;

    /**
     * Create new instance with specified configuration
     *
     * @param config configuration that contains all needed information
     */
    public ResourceGenerator(final GeneratorConfiguration config) {
        this.config = config;
    }

    public GeneratorConfiguration getConfig() {
        return config;
    }
}
