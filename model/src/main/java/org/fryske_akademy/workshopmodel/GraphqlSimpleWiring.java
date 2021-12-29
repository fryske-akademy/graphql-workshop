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

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.TypeResolver;
import graphql.schema.idl.InterfaceWiringEnvironment;
import graphql.schema.idl.UnionWiringEnvironment;
import graphql.schema.idl.WiringFactory;

public class GraphqlSimpleWiring implements WiringFactory {

    @Override
    public boolean providesTypeResolver(InterfaceWiringEnvironment environment) {
        return true;
    }

    @Override
    public TypeResolver getTypeResolver(InterfaceWiringEnvironment environment) {
        return new SimpleTypeResolver();
    }

    @Override
    public boolean providesTypeResolver(UnionWiringEnvironment environment) {
        return true;
    }

    @Override
    public TypeResolver getTypeResolver(UnionWiringEnvironment environment) {
        return new SimpleTypeResolver();
    }

    public static final GraphqlSimpleWiring GRAPHQL_SIMPLE_WIRING = new GraphqlSimpleWiring();

    private static class SimpleTypeResolver implements TypeResolver {
        @Override
        public GraphQLObjectType getType(TypeResolutionEnvironment env) {
            String targetTypeName = env.getObject().getClass().getSimpleName();
            GraphQLType schemaType = env.getSchema().getType(targetTypeName);
            if (!(schemaType instanceof GraphQLObjectType)) {
                throw new RuntimeException(
                        String.format("schemaType %s (found for Java type %s) is not a GraphQLObjectType",schemaType,targetTypeName));
            }
            return (GraphQLObjectType) schemaType;        }

    }
}
