/*
 * Copyright 2011 Bill La Forge
 *
 * This file is part of AgileWiki and is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (LGPL) as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * or navigate to the following url http://www.gnu.org/licenses/lgpl-2.1.txt
 *
 * Note however that only Scala, Java and JavaScript files are being covered by LGPL.
 * All other files are covered by the Common Public License (CPL).
 * A copy of this license is also included and can be
 * found as well at http://www.opensource.org/licenses/cpl1.0.txt
 */
package org.agilewiki.jactor.pubsub.subscriber;

import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.pubsub.actorName.JActorName;
import org.agilewiki.jactor.pubsub.publisher.Publisher;

/**
 * Implements Subscriber.
 */
public class JASubscriber
        extends JActorName implements Subscriber {
    /**
     * Create a LiteActor
     *
     * @param mailbox A mailbox which may be shared with other actors.
     */
    public JASubscriber(Mailbox mailbox) {
        super(mailbox);
    }

    /**
     * This actor has been granted a subscription.
     *
     * @param publisher The publisher that has been subscribed to.
     */
    @Override
    public void subscribed(Publisher publisher)
            throws Exception {
    }

    /**
     * This actor's subscription has been dropped.
     *
     * @param publisher The publisher which has dropped the subscription.
     * @param rp        The response processor.
     */
    @Override
    public void unsubscribed(Publisher publisher, RP rp)
            throws Exception {
        rp.processResponse(null);
    }

    /**
     * The application method for processing requests sent to the actor.
     *
     * @param request A request.
     * @param rp      The response processor.
     * @throws Exception Any uncaught exceptions raised while processing the request.
     */
    @Override
    protected void processRequest(Object request, RP rp) throws Exception {
        Class reqcls = request.getClass();

        if (reqcls == Subscribed.class) {
            Publisher publisher = ((Subscribed) request).publisher;
            subscribed(publisher);
            rp.processResponse(null);
            return;
        }

        if (reqcls == Unsubscribed.class) {
            Publisher publisher = ((Unsubscribed) request).publisher;
            unsubscribed(publisher, rp);
            return;
        }

        super.processRequest(request, rp);
    }
}