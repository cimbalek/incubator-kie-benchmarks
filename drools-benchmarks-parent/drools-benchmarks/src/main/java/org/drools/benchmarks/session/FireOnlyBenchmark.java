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

package org.drools.benchmarks.session;

import org.drools.benchmarks.common.AbstractBenchmark;
import org.drools.benchmarks.common.DRLProvider;
import org.drools.benchmarks.common.providers.RulesWithJoinsProvider;
import org.drools.benchmarks.common.util.BuildtimeUtil;
import org.drools.benchmarks.common.util.RuntimeUtil;
import org.drools.benchmarks.common.model.A;
import org.drools.benchmarks.common.model.B;
import org.drools.kiesession.session.StatefulKnowledgeSessionImpl;
import org.kie.api.conf.EventProcessingOption;
import org.kie.internal.conf.ParallelExecutionOption;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 2000)
@Measurement(iterations = 1000)
public class FireOnlyBenchmark extends AbstractBenchmark {

    @Param({"32", "64", "192"})
    private int rulesNr;

    @Param({"10", "100"})
    private int factsNr;

    @Param({"sequential", "parallel_evaluation", "fully_parallel"})
    private String parallel;

    @Param({"true", "false"})
    private boolean cep;

    @Setup
    public void setupKieBase() {
        final DRLProvider drlProvider = new RulesWithJoinsProvider(1, cep, true);
        kieBase = BuildtimeUtil.createKieBaseFromDrl(drlProvider.getDrl(rulesNr),
                                                     ParallelExecutionOption.determineParallelExecution(parallel),
                                                     cep ? EventProcessingOption.STREAM : EventProcessingOption.CLOUD );
    }

    @Setup(Level.Iteration)
    @Override
    public void setup() {
        kieSession = RuntimeUtil.createKieSession(kieBase);
        StatefulKnowledgeSessionImpl session = (StatefulKnowledgeSessionImpl) kieSession;
        A a = new A( rulesNr + 1 );
        session.insert( a );
        for ( int i = 0; i < factsNr; i++ ) {
            session.insert( new B( rulesNr + i + 3 ) );
        }
    }

    @Benchmark
    public int test() {
        return kieSession.fireAllRules();
    }
}