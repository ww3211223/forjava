package com.nonono.test.mybaits;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * SQL执行拦截器
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ExecutorIntercept implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object obj = invocation.proceed();
        final Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        String sqlId = mappedStatement.getId();
        Object parameterObject = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) boundSql.getParameterObject();
        StringBuffer paramStr = new StringBuffer();
        for (Object item : paramMap.entrySet()) {
            Map.Entry entry = (Map.Entry) item;
            paramStr.append(entry.getKey());
            paramStr.append("#");
            paramStr.append(entry.getValue());
            paramStr.append(" ");
        }

        String sqlStr = boundSql.getSql().replaceAll("\\s+", " ").replace("\n", "");
        System.out.println("");
        System.out.println("--------intercept start--------");
        System.out.println("sqlId:" + sqlId);
        System.out.println("sqlStr:" + sqlStr);
        System.out.println("sqlParam:" + paramStr.toString());
        System.out.println("--------intercept end--------");
        System.out.println("");
        return obj;
    }
}
