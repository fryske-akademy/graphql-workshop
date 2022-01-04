package org.fryske_akademy.workshopmodel;

/*-
 * #%L
 * languagemodel
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

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;

@ApplicationScoped
public class GraphQLSchemaBuilder {

    private final String schema;

    private final TypeDefinitionRegistry typeDefinitionRegistry;

    private GraphQLSchema graphQLSchema;

    public GraphQLSchemaBuilder() {
        try (
                InputStream stream = GraphQLSchemaBuilder.class.getResourceAsStream("/graphql/gqlworkshopapi.graphqls");
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            int c;
            while ((c = stream.read())!=-1) out.write(c);
            schema = out.toString(StandardCharsets.UTF_8);
            typeDefinitionRegistry = new SchemaParser().parse(schema);

        } catch (IOException e) {
            throw new IllegalStateException("could not load graphql schema",e);
        }
    }

    public String getSchema() {
        return schema;
    }

    /**
     * Use this in {@link graphql.schema.idl.SchemaGenerator#makeExecutableSchema(TypeDefinitionRegistry, RuntimeWiring)}.
     * @return
     */
    public TypeDefinitionRegistry getTypeDefinitionRegistry() {
        return typeDefinitionRegistry;
    }

    /**
     * You may want to use this to execute graphql queries programmatically, see {@link graphql.GraphQL#newGraphQL(GraphQLSchema)}.
     * @return
     */
    public GraphQLSchema getGraphQLSchema() {
        return graphQLSchema;
    }

    /**
     * Call this method once your executable schema is ready
     * @param graphQLSchema
     */
    public void setGraphQLSchema(GraphQLSchema graphQLSchema) {
        this.graphQLSchema = graphQLSchema;
    }
}
