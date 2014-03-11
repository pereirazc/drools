package org.drools.core.impl;

import org.drools.core.FactException;
import org.drools.core.FactHandle;
import org.drools.core.KnowledgeBaseEventManager;
import org.drools.core.RuleBaseConfiguration;
import org.drools.core.StatefulSession;
import org.drools.core.base.ClassFieldAccessorCache;
import org.drools.core.common.AbstractWorkingMemory;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.common.RuleBasePartitionId;
import org.drools.core.factmodel.traits.TraitRegistry;
import org.drools.core.reteoo.EntryPointNode;
import org.drools.core.reteoo.LeftTupleSource;
import org.drools.core.reteoo.Rete;
import org.drools.core.reteoo.ReteooBuilder;
import org.drools.core.rule.*;
import org.drools.core.spi.FactHandleFactory;
import org.drools.core.spi.PropagationContext;
import org.drools.core.util.TripleStore;
import org.kie.api.KieBase;
import org.kie.api.io.Resource;
import org.kie.internal.KnowledgeBase;
import org.drools.core.rule.Package;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface InternalKnowledgeBase extends KnowledgeBase, KieBase, KnowledgeBaseEventManager {

    String getId();

    RuleBasePartitionId createNewPartitionId();

    RuleBaseConfiguration getConfiguration();

    void readLock();
    void readUnlock();

    void lock();
    void unlock();

    int nextWorkingMemoryCounter();

    int getWorkingMemoryCounter();

    FactHandleFactory newFactHandleFactory();

    FactHandleFactory newFactHandleFactory(int id, long counter) throws IOException;

    Map<String, Class<?>> getGlobals();

    int getNodeCount();

    void executeQueuedActions();

    ReteooBuilder getReteooBuilder();

    void registerAddedEntryNodeCache(EntryPointNode node);
    Set<EntryPointNode> getAddedEntryNodeCache();

    void registeRremovedEntryNodeCache(EntryPointNode node);
    Set<EntryPointNode> getRemovedEntryNodeCache();

    Rete getRete();

    ClassLoader getRootClassLoader();

    void assertObject(FactHandle handle,
                      Object object,
                      PropagationContext context,
                      InternalWorkingMemory workingMemory) throws FactException;

    void retractObject(FactHandle handle,
                       PropagationContext context,
                       AbstractWorkingMemory workingMemory) throws FactException;

    void disposeStatefulSession(StatefulSession statefulSession);

    TripleStore getTripleStore();

    TraitRegistry getTraitRegistry();

    Class<?> registerAndLoadTypeDefinition( String className, byte[] def ) throws ClassNotFoundException;

    Package getPackage(String name);
    void addPackages(Package[] pkgs );
    void addPackage(Package pkg);
    void addPackages( final Collection<Package> newPkgs );
    Map<String, Package> getPackagesMap();

    ClassFieldAccessorCache getClassFieldAccessorCache();

    InternalWorkingMemory[] getWorkingMemories();

    void invalidateSegmentPrototype(LeftTupleSource tupleSource);

    void addRule( Package pkg, Rule rule ) throws InvalidPatternException;
    void removeRule( Package pkg, Rule rule ) throws InvalidPatternException;

    void removeObjectsGeneratedFromResource(Resource resource);

    TypeDeclaration getTypeDeclaration( Class<?> clazz );
    Collection<TypeDeclaration> getTypeDeclarations();
}
