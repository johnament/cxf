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
package org.apache.cxf.jaxrs.reactor.client;

import org.apache.cxf.jaxrs.client.SyncInvokerImpl;

import javax.ws.rs.client.RxInvokerProvider;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.ExecutorService;

@Provider
public class FluxRxInvokerProvider implements RxInvokerProvider<FluxRxInvoker> {
    @Override
    public boolean isProviderFor(Class<?> invokerType) {
        return FluxRxInvoker.class.isAssignableFrom(invokerType);
    }

    @Override
    public FluxRxInvoker getRxInvoker(SyncInvoker syncInvoker, ExecutorService executorService) {
        return new FluxRxInvokerImpl(((SyncInvokerImpl)syncInvoker).getWebClient(), executorService);
    }
}
