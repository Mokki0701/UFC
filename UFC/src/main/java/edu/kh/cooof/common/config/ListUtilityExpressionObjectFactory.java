package edu.kh.cooof.common.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import edu.kh.cooof.common.utility.ListUtility;

public class ListUtilityExpressionObjectFactory implements IExpressionObjectFactory {

	private static final String LIST_UTILS_EXPRESSION_OBJECT_NAME = "lists";
    private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;

    static {
        Set<String> allExpressionObjectNames = new HashSet<>();
        allExpressionObjectNames.add(LIST_UTILS_EXPRESSION_OBJECT_NAME);
        ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);
    }

    private final ListUtility listUtility;

    public ListUtilityExpressionObjectFactory(ListUtility listUtility) {
        this.listUtility = listUtility;
    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if (LIST_UTILS_EXPRESSION_OBJECT_NAME.equals(expressionObjectName)) {
            return listUtility;
        }
        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return true;
    }

}
	

