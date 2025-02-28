/*
 * Copyright (c)  2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.siddhi.extension.execution.map;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ParameterOverload;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.Map;

/**
 * putAll(HashMap , HashMap)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , HashMap)
 * Return Type(s): HashMap
 */
@Extension(
        name = "putAll",
        namespace = "map",
        description = "This returns the updated 'to.map' map after copying all of the mappings from the " +
                "specified 'from.map.' map."
                + " If there are duplicate keys, 'from.map' overwrites the values into the 'to.map.' map.",
        parameters = {
                @Parameter(name = "to.map",
                        description = "The map into which the mappings need to copied.",
                        type = DataType.OBJECT,
                        dynamic = true,
                        optional = false
                ),
                @Parameter(name = "from.map",
                        description = "The map from which the mappings are copied.",
                        type = DataType.OBJECT,
                        dynamic = true,
                        optional = false
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"to.map", "from.map"})
        },
        returnAttributes = @ReturnAttribute(description = "A hashMap is returned.", type = DataType.OBJECT),
        examples = @Example(
                syntax = "putAll(toMap , fromMap)",
                description = "This returns the updated map named 'toMap' after adding each mapping from 'fromMap.'")
)
public class PutAllFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:putAll() function, " +
                    "required 2 parameters, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data == null) {
            throw new SiddhiAppRuntimeException("Data can not be null.");
        }
        Map<Object, Object> map1 = (Map<Object, Object>) data[0];
        Map<Object, Object> map2 = (Map<Object, Object>) data[1];
        map1.putAll(map2);
        return map1;
    }

    @Override
    protected Object execute(Object data, State state) {
        //Since the map:putAll() function takes in 2 parameters, this method does not get called.
        // Hence, not implemented.
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
