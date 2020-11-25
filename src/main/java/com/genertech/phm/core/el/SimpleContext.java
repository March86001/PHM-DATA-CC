package com.genertech.phm.core.el;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.javax.el.ELContext;
import org.activiti.engine.impl.javax.el.ELResolver;
import org.activiti.engine.impl.javax.el.FunctionMapper;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.javax.el.VariableMapper;
import org.activiti.engine.impl.juel.SimpleResolver;


/**
 * 
 * @author hadoop
 *
 */
public class SimpleContext extends ELContext {
	
	/**
	 * 是否解析ApplicationContext中的bean
	 */
	private boolean containsBeans = false;
	
	//修改了格式, 不再需要prefix
	class Functions extends FunctionMapper {
		Map<String, Method> map = Collections.emptyMap();

		@Override
		public Method resolveFunction(String prefix, String localName) {
			return map.get(localName);
		}

		public void setFunction(String localName, Method method) {
			if (map.isEmpty()) {
				map = new HashMap<String, Method>();
			}
			map.put(localName, method);
		}
	}

	class Variables extends VariableMapper {
		Map<String, ValueExpression> map = Collections.emptyMap();

		@Override
		public ValueExpression resolveVariable(String variable) {
			if(map.containsKey(variable))	
				return map.get(variable);
			else if(containsBeans)
				return CommonELResolver.getBeanValueExpression(variable);
			else
				return null;
		}

		@Override
		public ValueExpression setVariable(String variable, ValueExpression expression) {
			if (map.isEmpty()) {
				map = new HashMap<String, ValueExpression>();
			}
			return map.put(variable, expression);
		}
	}

	private Functions functions;
	private Variables variables;
	private ELResolver resolver;

	/**
	 * Create a context.
	 */
	public SimpleContext() {
		this(null);
	}

	/**
	 * Create a context, use the specified resolver.
	 */
	public SimpleContext(ELResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Define a function.
	 */
	public void setFunction(String localName, Method method) {
		if (functions == null) {
			functions = new Functions();
		}
		functions.setFunction(localName, method);
	}

	/**
	 * Define a variable.
	 */
	public ValueExpression setVariable(String name, ValueExpression expression) {
		if (variables == null) {
			variables = new Variables();
		}
		return variables.setVariable(name, expression);
	}

	/**
	 * Get our function mapper.
	 */
	@Override
	public FunctionMapper getFunctionMapper() {
		if (functions == null) {
			functions = new Functions();
		}
		return functions;
	}

	/**
	 * Get our variable mapper.
	 */
	@Override
	public VariableMapper getVariableMapper() {
		if (variables == null) {
			variables = new Variables();
		}
		return variables;
	}

	/**
	 * Get our resolver. Lazy initialize to a {@link SimpleResolver} if necessary.
	 */
	@Override
	public ELResolver getELResolver() {
		if (resolver == null) {
			resolver = new SimpleResolver();
		}
		return resolver;
	}

	/**
	 * Set our resolver.
	 * 
	 * @param resolver
	 */
	public void setELResolver(ELResolver resolver) {
		this.resolver = resolver;
	}

	public boolean isContainsBeans() {
		return containsBeans;
	}

	public void setContainsBeans(boolean containsBeans) {
		this.containsBeans = containsBeans;
	}

}
