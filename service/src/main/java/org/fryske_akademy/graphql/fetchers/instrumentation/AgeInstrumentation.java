package org.fryske_akademy.graphql.fetchers.instrumentation;

/*-
 * #%L
 * languageservice
 * %%
 * Copyright (C) 2021 Fryske Akademy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import graphql.ExecutionResult;
import graphql.execution.FetchedValue;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationFieldCompleteParameters;
import org.fryske_akademy.gqlworkshopmodel.Person;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgeInstrumentation extends SimpleInstrumentation {

    @Override
    public InstrumentationContext<ExecutionResult> beginFieldComplete(InstrumentationFieldCompleteParameters parameters) {
        Object f = parameters.getFetchedValue();
        Object o = (f instanceof FetchedValue) ? ((FetchedValue)f).getFetchedValue() : null;
        if (o instanceof Person) {
            Person p = (Person) o;
            if (p.getAge()==null) {
                System.out.println(String.format("missing age for %s!",p.getName()));
            }
        }
        return super.beginFieldComplete(parameters);
    }
}
