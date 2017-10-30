/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.extensions.reactor.test;

import org.apache.cxf.jaxrs.reactor.server.AbstractSubscriber;
import org.apache.cxf.jaxrs.reactor.server.JsonStreamingAsyncSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@Path("/reactor/flux")
public class FluxService {

    @GET
    @Produces("application/json")
    @Path("textJson")
    public Flux<HelloWorldBean> getJson() {
        return Flux.just(new HelloWorldBean());
    }

    @GET
    @Produces("application/json")
    @Path("textJsonImplicitListAsyncStream")
    public void getJsonImplicitListStreamingAsync(@Suspended AsyncResponse ar) {
        Flux.just("Hello", "Ciao")
                .map(HelloWorldBean::new)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new JsonStreamingAsyncSubscriber<>(ar));
    }

    @GET
    @Produces("text/plain")
    @Path("textAsync")
    public void getTextAsync(@Suspended final AsyncResponse ar) {
        Flux.just("Hello, ").map(s -> s + "world!")
                .subscribe(new StringAsyncSubscriber(ar));

    }

    private static class StringAsyncSubscriber extends AbstractSubscriber<String> {
        protected StringAsyncSubscriber(AsyncResponse ar) {
            super(ar);
        }
    }
}