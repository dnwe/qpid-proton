/*
 *
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
 *
 */

package org.apache.qpid.proton.example.reactor;

import java.io.IOException;

import org.apache.qpid.proton.Proton;
import org.apache.qpid.proton.engine.BaseHandler;
import org.apache.qpid.proton.engine.Event;
import org.apache.qpid.proton.reactor.Reactor;

// TODO: sort out docs!
/*
 * The proton reactor provides a general purpose event processing
 * library for writing reactive programs. A reactive program is defined
 * by a set of event handlers. An event handler is just any class or
 * object that extends the Handler interface. For convinience, a class
 * can extend BaseHandler and only handle the events that it cares to
 * implement methods for.
 */
public class HelloWorld extends BaseHandler {

    // The reactor init event is produced by the reactor itself when it
    // starts.
    @Override
    public void onReactorInit(Event event) {
        System.out.println("Hello, World!");
    }

    public static void main(String[] args) throws IOException {

        // When you construct a reactor, you can give it a handler that
        // is used, by default.
        Reactor reactor = Proton.reactor(new HelloWorld());

        // When you call run, the reactor will process events. The reactor init
        // event is what kicks off everything else. When the reactor has no
        // more events to process, it exits.
        reactor.run();
    }
}
