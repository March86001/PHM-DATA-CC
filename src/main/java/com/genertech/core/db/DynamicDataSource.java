package com.genertech.core.db;

/**
 * 保持数据源key
 * @author liuqiang
 */

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDbType();
	}

}
