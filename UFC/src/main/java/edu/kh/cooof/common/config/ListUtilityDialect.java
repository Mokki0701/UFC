package edu.kh.cooof.common.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.processor.IProcessor;

import edu.kh.cooof.common.utility.ListUtility;

@Configuration
public class ListUtilityDialect extends AbstractProcessorDialect implements IExpressionObjectDialect {

	private final ListUtility listUtility;

	@Autowired
    public ListUtilityDialect(ListUtility listUtility) {
        super("ListUtilityDialect", "listutil", 1000);
        this.listUtility = listUtility;
    }

	@Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new ListUtilityExpressionObjectFactory(listUtility);
    }

	@Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        return new HashSet<>(); // 필요에 따라 프로세서를 추가할 수 있습니다.
    }
	
}
