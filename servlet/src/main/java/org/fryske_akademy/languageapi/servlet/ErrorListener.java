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

import graphql.kickstart.servlet.core.GraphQLServletListener;
import org.fryske_akademy.workshopmodel.ErrorHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class ErrorListener implements GraphQLServletListener {

    @Inject
    private ErrorHandler errorHandler;

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
        return new ErrorCallback(request,response);
    }

    public class ErrorCallback implements RequestCallback {
        private final HttpServletRequest request;
        private final HttpServletResponse response;

        public ErrorCallback(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public void onError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
            errorHandler.handle("unexpected exception", throwable);
        }
    }

}
