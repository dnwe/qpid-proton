/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ibm.mqlight.api.impl.network;

import java.util.concurrent.atomic.AtomicBoolean;

import com.ibm.mqlight.api.Promise;
import com.ibm.mqlight.api.impl.Component;
import com.ibm.mqlight.api.logging.Logger;
import com.ibm.mqlight.api.logging.LoggerFactory;

public class NetworkWritePromiseImpl implements Promise<Boolean> {
  
    private static final Logger logger = LoggerFactory.getLogger(NetworkWritePromiseImpl.class);
  
    private final AtomicBoolean complete = new AtomicBoolean(false);
    private final Component component;
    private final long amount;
    private final Object context;
    
    public NetworkWritePromiseImpl(Component component, long amount, Object context) {
        final String methodName = "<init>";
        logger.entry(this, methodName, component, context);
      
        this.component = component;
        this.amount = amount;
        this.context = context;
        
        logger.exit(this, methodName);
    }

    @Override
    public boolean isComplete() {
        return complete.get();
    }

    @Override
    public void setSuccess(Boolean drained) throws IllegalStateException {
        final String methodName = "setSuccess";
        logger.entry(this, methodName, drained);
      
        if (complete.getAndSet(true)) {
            final IllegalStateException ex  = new IllegalStateException("Promise already completed");
            logger.throwing(this, methodName, ex);
            throw ex;
        } else {
            component.tell(new WriteResponse(context, amount, drained), Component.NOBODY);
        }
        
        logger.exit(this, methodName);
    }

    @Override
    public void setFailure(Exception exception) throws IllegalStateException {
        final String methodName = "setFailure";
        logger.entry(this, methodName, exception);
      
        // TODO: can never fail?
        if (complete.getAndSet(true)) {
            final IllegalStateException ex  = new IllegalStateException("Promise already completed");
            logger.throwing(this, methodName, ex);
            throw ex;
        }
        
        logger.exit(this, methodName);
    }

}
