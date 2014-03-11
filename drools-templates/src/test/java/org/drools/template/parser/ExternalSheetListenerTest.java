package org.drools.template.parser;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.command.Command;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.Calendars;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.ViewChangedEventListener;
import org.kie.api.time.SessionClock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExternalSheetListenerTest {

    private TemplateDataListener esl;

    private Map<Row, List<StringCell>> assertedRows = new HashMap<Row, List<StringCell>>();

    private List<StringCell> currentRow = new ArrayList<StringCell>();

    @Before
    public void setUp() throws Exception {
        esl = new TemplateDataListener(2, 2, new TestTemplateContainer(),
                                       new TestTemplateRuleBase(), new TestGenerator());

    }

    @Test
    public void testRenderDrl() {
        String drl = esl.renderDRL();
        assertEquals("Test Template Header\nTest Generated DRL\n", drl);
    }

    @Test
    public void testRowHandling() {
        esl.newRow(0, 3);
        esl.newCell(0, 0, "row0col0", 0);
        esl.newCell(0, 1, "row0col1", 0);
        esl.newCell(0, 2, "row0col2", 0);
        esl.newRow(1, 3);
        esl.newCell(1, 0, "row1col0", 0);
        esl.newCell(1, 1, "row1col1", 0);
        esl.newCell(1, 2, "row1col2", 0);
        esl.newRow(2, 3);
        esl.newCell(2, 0, "row2col0", 0);
        esl.newCell(2, 1, "row2col1", 0);
        esl.newCell(2, 2, "row2col2", 0);
        esl.finishSheet();
        assertEquals(2, assertedRows.size());
        for (Map.Entry<Row, List<StringCell>> entry : assertedRows.entrySet()) {
            Row row = entry.getKey();
            List<StringCell> cells = entry.getValue();
            // first column is not part of the decision table
            int i = 1;
            for (StringCell cell : cells) {
                assertEquals("row" + row.getRowNumber() + "col" + i, cell.getValue());
                assertEquals("Pattern " + i, cell.getColumn().getName());
                i++;
            }
        }
    }

    @Test
    public void testRowHandlingBlankRows() {
        esl.newRow(0, 3);
        esl.newCell(0, 0, "row0col0", 0);
        esl.newCell(0, 1, "row0col1", 0);
        esl.newCell(0, 2, "row0col2", 0);
        esl.newRow(1, 3);
        esl.newCell(1, 0, "row1col0", 0);
        esl.newCell(1, 1, "row1col1", 0);
        esl.newCell(1, 2, "row1col2", 0);
        esl.newRow(2, 3);
        esl.newCell(2, 0, "row2col0", 0);
        esl.newCell(2, 1, "row2col1", 0);
        esl.newCell(2, 2, "row2col2", 0);
        esl.newRow(3, 3);
        esl.newCell(3, 0, "", 0);
        esl.newCell(3, 1, "", 0);
        esl.newCell(3, 2, "", 0);
        esl.newRow(4, 3);
        esl.newCell(4, 0, "", 0);
        esl.newCell(4, 1, "", 0);
        esl.newCell(4, 2, "", 0);

        esl.finishSheet();
        assertEquals(2, assertedRows.size());
    }

    private class TestTemplateRuleBase implements TemplateRuleBase {

        public KieSession newStatefulSession() {
            return new KieSession() {

                @Override
                public int getId() {
                    throw new UnsupportedOperationException(".getId -> TODO");

                }

                @Override
                public void dispose() {
                    throw new UnsupportedOperationException(".dispose -> TODO");

                }

                @Override
                public void destroy() {
                    throw new UnsupportedOperationException(".destroy -> TODO");

                }

                @Override
                public <T> T execute(Command<T> command) {
                    throw new UnsupportedOperationException(".execute -> TODO");

                }

                @Override
                public <T extends SessionClock> T getSessionClock() {
                    throw new UnsupportedOperationException(".getSessionClock -> TODO");

                }

                @Override
                public void setGlobal(String identifier, Object value) {
                    throw new UnsupportedOperationException(".setGlobal -> TODO");

                }

                @Override
                public Object getGlobal(String identifier) {
                    throw new UnsupportedOperationException(".getGlobal -> TODO");

                }

                @Override
                public Globals getGlobals() {
                    throw new UnsupportedOperationException(".getGlobals -> TODO");

                }

                @Override
                public Calendars getCalendars() {
                    throw new UnsupportedOperationException(".getCalendars -> TODO");

                }

                @Override
                public Environment getEnvironment() {
                    throw new UnsupportedOperationException(".getEnvironment -> TODO");

                }

                @Override
                public KieBase getKieBase() {
                    throw new UnsupportedOperationException(".getKieBase -> TODO");

                }

                @Override
                public void registerChannel(String name, Channel channel) {
                    throw new UnsupportedOperationException(".registerChannel -> TODO");

                }

                @Override
                public void unregisterChannel(String name) {
                    throw new UnsupportedOperationException(".unregisterChannel -> TODO");

                }

                @Override
                public Map<String, Channel> getChannels() {
                    throw new UnsupportedOperationException(".getChannels -> TODO");

                }

                @Override
                public KieSessionConfiguration getSessionConfiguration() {
                    throw new UnsupportedOperationException(".getSessionConfiguration -> TODO");

                }

                @Override
                public void halt() {
                    throw new UnsupportedOperationException(".halt -> TODO");

                }

                @Override
                public org.kie.api.runtime.rule.Agenda getAgenda() {
                    throw new UnsupportedOperationException(".getAgenda -> TODO");

                }

                @Override
                public EntryPoint getEntryPoint(String name) {
                    throw new UnsupportedOperationException(".getEntryPoint -> TODO");

                }

                @Override
                public Collection<? extends EntryPoint> getEntryPoints() {
                    throw new UnsupportedOperationException(".getEntryPoints -> TODO");

                }

                @Override
                public org.kie.api.runtime.rule.QueryResults getQueryResults(String query, Object... arguments) {
                    throw new UnsupportedOperationException(".getQueryResults -> TODO");

                }

                @Override
                public LiveQuery openLiveQuery(String query, Object[] arguments, ViewChangedEventListener listener) {
                    throw new UnsupportedOperationException(".openLiveQuery -> TODO");

                }

                @Override
                public String getEntryPointId() {
                    throw new UnsupportedOperationException(".getEntryPointId -> TODO");

                }

                @Override
                public org.kie.api.runtime.rule.FactHandle insert(Object fact) {
                    if (fact instanceof Row) {
                        assertedRows.put((Row) fact, currentRow);
                        currentRow = new ArrayList<StringCell>();
                    } else if (fact instanceof StringCell) {
                        currentRow.add((StringCell) fact);
                    }
                    return null;
                }

                @Override
                public void retract(org.kie.api.runtime.rule.FactHandle handle) {
                    throw new UnsupportedOperationException(".retract -> TODO");

                }

                @Override
                public void delete(org.kie.api.runtime.rule.FactHandle handle) {
                    throw new UnsupportedOperationException(".delete -> TODO");

                }

                @Override
                public void update(org.kie.api.runtime.rule.FactHandle handle, Object object) {
                    throw new UnsupportedOperationException(".update -> TODO");

                }

                @Override
                public org.kie.api.runtime.rule.FactHandle getFactHandle(Object object) {
                    throw new UnsupportedOperationException(".getFactHandle -> TODO");

                }

                @Override
                public Object getObject(org.kie.api.runtime.rule.FactHandle factHandle) {
                    throw new UnsupportedOperationException(".getObject -> TODO");

                }

                @Override
                public Collection<? extends Object> getObjects() {
                    throw new UnsupportedOperationException(".getObjects -> TODO");

                }

                @Override
                public Collection<? extends Object> getObjects(ObjectFilter filter) {
                    throw new UnsupportedOperationException(".getObjects -> TODO");

                }

                @Override
                public <T extends org.kie.api.runtime.rule.FactHandle> Collection<T> getFactHandles() {
                    throw new UnsupportedOperationException(".getFactHandles -> TODO");

                }

                @Override
                public <T extends org.kie.api.runtime.rule.FactHandle> Collection<T> getFactHandles(ObjectFilter filter) {
                    throw new UnsupportedOperationException(".getFactHandles -> TODO");

                }

                @Override
                public long getFactCount() {
                    throw new UnsupportedOperationException(".getFactCount -> TODO");

                }

                @Override
                public KieRuntimeLogger getLogger() {
                    throw new UnsupportedOperationException(".getLogger -> TODO");

                }

                @Override
                public void addEventListener(ProcessEventListener listener) {
                    throw new UnsupportedOperationException(".addEventListener -> TODO");

                }

                @Override
                public void removeEventListener(ProcessEventListener listener) {
                    throw new UnsupportedOperationException(".removeEventListener -> TODO");

                }

                @Override
                public Collection<ProcessEventListener> getProcessEventListeners() {
                    throw new UnsupportedOperationException(".getProcessEventListeners -> TODO");

                }

                @Override
                public ProcessInstance startProcess(String processId) {
                    throw new UnsupportedOperationException(".startProcess -> TODO");

                }

                @Override
                public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
                    throw new UnsupportedOperationException(".startProcess -> TODO");

                }

                @Override
                public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
                    throw new UnsupportedOperationException(".createProcessInstance -> TODO");

                }

                @Override
                public ProcessInstance startProcessInstance(long processInstanceId) {
                    throw new UnsupportedOperationException(".startProcessInstance -> TODO");

                }

                @Override
                public void signalEvent(String type, Object event) {
                    throw new UnsupportedOperationException(".signalEvent -> TODO");

                }

                @Override
                public void signalEvent(String type, Object event, long processInstanceId) {
                    throw new UnsupportedOperationException(".signalEvent -> TODO");

                }

                @Override
                public Collection<ProcessInstance> getProcessInstances() {
                    throw new UnsupportedOperationException(".getProcessInstances -> TODO");

                }

                @Override
                public ProcessInstance getProcessInstance(long processInstanceId) {
                    throw new UnsupportedOperationException(".getProcessInstance -> TODO");

                }

                @Override
                public ProcessInstance getProcessInstance(long processInstanceId, boolean readonly) {
                    throw new UnsupportedOperationException(".getProcessInstance -> TODO");

                }

                @Override
                public void abortProcessInstance(long processInstanceId) {
                    throw new UnsupportedOperationException(".abortProcessInstance -> TODO");

                }

                @Override
                public org.kie.api.runtime.process.WorkItemManager getWorkItemManager() {
                    throw new UnsupportedOperationException(".getWorkItemManager -> TODO");

                }

                @Override
                public void addEventListener(RuleRuntimeEventListener listener) {
                    throw new UnsupportedOperationException(".addEventListener -> TODO");

                }

                @Override
                public void removeEventListener(RuleRuntimeEventListener listener) {
                    throw new UnsupportedOperationException(".removeEventListener -> TODO");

                }

                @Override
                public Collection<RuleRuntimeEventListener> getRuleRuntimeEventListeners() {
                    throw new UnsupportedOperationException(".getRuleRuntimeEventListeners -> TODO");

                }

                @Override
                public void addEventListener(org.kie.api.event.rule.AgendaEventListener listener) {
                    throw new UnsupportedOperationException(".addEventListener -> TODO");

                }

                @Override
                public void removeEventListener(org.kie.api.event.rule.AgendaEventListener listener) {
                    throw new UnsupportedOperationException(".removeEventListener -> TODO");

                }

                @Override
                public Collection<org.kie.api.event.rule.AgendaEventListener> getAgendaEventListeners() {
                    throw new UnsupportedOperationException(".getAgendaEventListeners -> TODO");

                }

                @Override
                public int fireAllRules() {
                    throw new UnsupportedOperationException(".fireAllRules -> TODO");

                }

                @Override
                public int fireAllRules(int max) {
                    throw new UnsupportedOperationException(".fireAllRules -> TODO");

                }

                @Override
                public int fireAllRules(org.kie.api.runtime.rule.AgendaFilter agendaFilter) {
                    throw new UnsupportedOperationException(".fireAllRules -> TODO");

                }

                @Override
                public int fireAllRules(org.kie.api.runtime.rule.AgendaFilter agendaFilter, int max) {
                    throw new UnsupportedOperationException(".fireAllRules -> TODO");

                }

                @Override
                public void fireUntilHalt() {
                    throw new UnsupportedOperationException(".fireUntilHalt -> TODO");

                }

                @Override
                public void fireUntilHalt(org.kie.api.runtime.rule.AgendaFilter agendaFilter) {
                    throw new UnsupportedOperationException(".fireUntilHalt -> TODO");

                }
            };
        }
    }

    private class TestGenerator implements Generator {

        public void generate(String templateName, Row row) {
        }

        public String getDrl() {
            return "Test Generated DRL";
        }

    }

    private class TestTemplateContainer implements TemplateContainer {

        public void addColumn(Column c) {
        }

        public void addTemplate(RuleTemplate template) {
        }

        public Column[] getColumns() {
            return new Column[]{new StringColumn("Pattern 1"),
                                new StringColumn("Pattern 2"), new StringColumn("Pattern 3")};
        }

        public String getHeader() {
            return "Test Template Header";
        }

        public Map<String, RuleTemplate> getTemplates() {
            return null;
        }

        public void setHeader(String head) {
        }

        public void setTemplates(Map<String, RuleTemplate> templates) {

        }

        public Column getColumn(String name) {
            return null;
        }
    }

}
