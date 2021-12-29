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

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.DefaultGraphQLErrorHandler;
import org.fryske_akademy.workshopmodel.ErrorHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DatafetchingErrorHandler extends DefaultGraphQLErrorHandler {

    @Inject
    private ErrorHandler errorHandler;

    @Override
    protected void logError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            errorHandler.handle("Error fetching data",
                    ((ExceptionWhileDataFetching) error).getException());
        } else {
            errorHandler.handle("Error executing query " + error.getClass().getSimpleName() + " " +
                    error.getMessage(),null);
        }

    }
}
