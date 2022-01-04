package org.fryske_akademy.graphql.fetchers;

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

import graphql.schema.DataFetchingEnvironment;
import org.fryske_akademy.gqlworkshopmodel.Greetings;
import org.fryske_akademy.gqlworkshopmodel.HelloOrg;
import org.fryske_akademy.gqlworkshopmodel.HelloPers;
import org.fryske_akademy.workshopmodel.GreetingsFetcher;

import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.fryske_akademy.gqlworkshopmodel.OrgType;

@RequestScoped
public class GreetingsFetcherImpl implements GreetingsFetcher {

    private List<String> persons = List.of("hilda", "piet", "klaas");

    private List<String> organisations = List.of("company", "shop", "factory", "fryslân");

    private interface Name {String name();}

    private static class Pers implements Name{ private final String name;

        private Pers(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }

    private static class Org implements Name { private final String name;

        private Org(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }

    @Override
    public List<Greetings> get(DataFetchingEnvironment environment) {
        String name = environment.getArgument("name");
        boolean ageRequested = environment.getSelectionSet().contains("**age");
        return Stream.concat(persons.stream().map(p-> new Pers(p)),organisations.stream().map(o->new Org(o)))
                .filter(s -> {
                    String n = s.name();
                        boolean hit = n.contains(name);
                        return hit;
                }
                )
                .map(s -> s instanceof Pers ?
                        HelloPers.builder()
                                .setGreeting("hello person")
                                .setName(s.name())
                                .setAge(ageRequested ? getAge((Pers) s) : null)
                                .build() :
                        HelloOrg.builder()
                                .setGreeting("hello organization")
                                .setName(s.name())
                                .setType(OrgType.unknown)
                                .build()).collect(Collectors.toList());

    }

    private Integer getAge(Pers p) {
        return Integer.valueOf(25);
    }
}
