package org.drools.compiler.compiler;

import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.utils.ServiceRegistryImpl;


public class BPMN2ProcessFactory {

    private static BPMN2ProcessProvider provider;

    public static void configurePackageBuilder(KnowledgeBuilder kBuilder) {
        getBPMN2ProcessProvider().configurePackageBuilder(kBuilder);
    }

    public static synchronized void setBPMN2ProcessProvider(BPMN2ProcessProvider provider) {
        BPMN2ProcessFactory.provider = provider;
    }

    public static synchronized BPMN2ProcessProvider getBPMN2ProcessProvider() {
        if (provider == null) {
            loadProvider();
        }
        return provider;
    }

    @SuppressWarnings("unchecked")
    private static void loadProvider() {
        ServiceRegistryImpl.getInstance().addDefault( BPMN2ProcessProvider.class,  "org.jbpm.bpmn2.BPMN2ProcessProviderImpl" );
        setBPMN2ProcessProvider(ServiceRegistryImpl.getInstance().get( BPMN2ProcessProvider.class ) );
    }

}
