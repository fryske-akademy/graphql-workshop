package org.fryske_akademy.languageapi.servlet;

/*-
 * #%L
 * languageservlet
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

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import org.fryske_akademy.languagemodel.GraphQLSchemaBuilder;
import org.fryske_akademy.languagemodel.ResultsFetcher;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@WebServlet(name = "GraphQLWorkshopServlet", urlPatterns = {"/graphql/*"}, loadOnStartup = 1)
public class GraphQLServlet extends GraphQLHttpServlet {

    @Inject
    private ResultsFetcher resultsFetcher;

    @Inject
    private ErrorHandler errorHandler;

    @Inject
    private GraphQLSchemaBuilder graphQLSchemaBuilder;

    @Override
    protected GraphQLConfiguration getConfiguration() {
        return GraphQLConfiguration.with(createSchema()).with(Arrays.asList(errorHandler)).build();
    }


    private GraphQLSchema createSchema() {
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query",builder -> builder
                        .dataFetcher("search", resultsFetcher)

                ).build();
        return new SchemaGenerator().makeExecutableSchema(graphQLSchemaBuilder.getTypeDefinitionRegistry(),runtimeWiring);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        super.doPost(req, resp);
    }
}
