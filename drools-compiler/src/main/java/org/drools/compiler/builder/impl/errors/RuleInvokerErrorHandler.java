package org.drools.compiler.builder.impl.errors;

import org.drools.compiler.lang.descr.BaseDescr;
import org.drools.core.rule.Rule;

public class RuleInvokerErrorHandler extends RuleErrorHandler {

    public RuleInvokerErrorHandler(final BaseDescr ruleDescr,
                                   final Rule rule,
                                   final String message) {
        super(ruleDescr,
              rule,
              message);
    }
}
