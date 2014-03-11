/*
 * Copyright 2010 JBoss Inc
 *
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
 */

package org.drools.core.base;

import java.lang.reflect.Field;

import org.drools.core.WorkingMemory;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.StatefulKnowledgeSessionImpl;
import org.junit.Test;
import org.kie.internal.KnowledgeBaseFactory;

import static org.junit.Assert.*;

public class DelegateJavaFactHandlerTest {

    @Test
    public void test1Entry() throws Exception {
        final DelegateJavaFactHandler handler = new DelegateJavaFactHandler();

        final Field field = handler.getClass().getDeclaredField( "entries" );
        field.setAccessible( true );

        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm1 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;

        handler.register( wm1 );

        JavaFactRegistryEntry[] entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 1,
                      entries.length );

        assertTrue( handler.isRegistered( wm1 ) );

        assertEquals( 1,
                      handler.listWorkingMemories().length );
        assertSame( wm1,
                    handler.listWorkingMemories()[0].getWorkingMemory() );

        kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm2 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;

        assertFalse( handler.isRegistered( wm2 ) );

        handler.unregister( wm1 );
        assertFalse( handler.isRegistered( wm1 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertNull( entries );
    }

    @Test
    public void test2Entries() throws Exception {
        final DelegateJavaFactHandler handler = new DelegateJavaFactHandler();

        final Field field = handler.getClass().getDeclaredField( "entries" );
        field.setAccessible( true );

        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm1 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;
        kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm2 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;
        handler.register( wm1 );
        handler.register( wm2 );

        JavaFactRegistryEntry[] entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 2,
                      entries.length );

        assertTrue( handler.isRegistered( wm1 ) );
        assertTrue( handler.isRegistered( wm2 ) );

        assertEquals( 2,
                      handler.listWorkingMemories().length );
        assertSame( wm1,
                    handler.listWorkingMemories()[0].getWorkingMemory() );
        assertSame( wm2,
                    handler.listWorkingMemories()[1].getWorkingMemory() );

        handler.unregister( wm1 );
        assertFalse( handler.isRegistered( wm1 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 1,
                      entries.length );

        handler.unregister( wm2 );
        assertFalse( handler.isRegistered( wm2 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertNull( entries );

        // check  revererse
        handler.register( wm1 );
        handler.register( wm2 );

        handler.unregister( wm2 );
        assertFalse( handler.isRegistered( wm2 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 1,
                      entries.length );

        handler.unregister( wm1 );
        assertFalse( handler.isRegistered( wm1 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertNull( entries );
    }

    @Test
    public void test3Entries() throws Exception {
        final DelegateJavaFactHandler handler = new DelegateJavaFactHandler();

        final Field field = handler.getClass().getDeclaredField( "entries" );
        field.setAccessible( true );

        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm1 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;
        kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm2 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;
        kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        InternalWorkingMemory wm3 = ((StatefulKnowledgeSessionImpl)kBase.newStatefulKnowledgeSession()).session;
        handler.register( wm1 );
        handler.register( wm2 );
        handler.register( wm3 );

        JavaFactRegistryEntry[] entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 3,
                      entries.length );

        assertTrue( handler.isRegistered( wm1 ) );
        assertTrue( handler.isRegistered( wm2 ) );
        assertTrue( handler.isRegistered( wm3 ) );

        assertEquals( 3,
                      handler.listWorkingMemories().length );
        assertSame( wm1,
                    handler.listWorkingMemories()[0].getWorkingMemory() );
        assertSame( wm2,
                    handler.listWorkingMemories()[1].getWorkingMemory() );
        assertSame( wm3,
                    handler.listWorkingMemories()[2].getWorkingMemory() );

        handler.unregister( wm2 );
        assertFalse( handler.isRegistered( wm2 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 2,
                      entries.length );

        handler.unregister( wm1 );
        assertFalse( handler.isRegistered( wm1 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertEquals( 1,
                      entries.length );

        handler.unregister( wm3 );
        assertFalse( handler.isRegistered( wm3 ) );
        entries = (JavaFactRegistryEntry[]) field.get( handler );
        assertNull( entries );
    }
}
