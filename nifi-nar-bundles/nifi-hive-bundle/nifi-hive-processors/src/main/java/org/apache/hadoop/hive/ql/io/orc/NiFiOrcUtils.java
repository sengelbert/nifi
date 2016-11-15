/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.io.orc;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.util.Utf8;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
//import org.apache.hadoop.hive.ql.io.filters.BloomFilterIO;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.SettableStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.typeinfo.ListTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.MapTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.apache.hadoop.hive.serde2.typeinfo.UnionTypeInfo;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_ORC_DEFAULT_BLOCK_PADDING;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_ORC_DEFAULT_BLOCK_SIZE;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_ORC_DEFAULT_ROW_INDEX_STRIDE;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_ORC_WRITE_FORMAT;

/**
 * Utility methods for ORC support (conversion from Avro, conversion to Hive types, e.g.
 */
public class NiFiOrcUtils {

    public static Object convertToORCObject(TypeInfo typeInfo, Object o) {
        return null;
    }


    /**
     * Create an object of OrcStruct given a TypeInfo and a list of objects
     *
     * @param typeInfo The TypeInfo object representing the ORC record schema
     * @param objs     ORC objects/Writables
     * @return an OrcStruct containing the specified objects for the specified schema
     */
    public static OrcStruct createOrcStruct(TypeInfo typeInfo, Object... objs) {
        return null;
    }

    public static String normalizeHiveTableName(String name) {
        return name.replaceAll("[\\. ]", "_");
    }

    public static String generateHiveDDL(Schema avroSchema, String tableName) {
        return null;
    }


    public static TypeInfo getOrcField(Schema fieldSchema) throws IllegalArgumentException {
        return null;

    }

    public static Schema.Type getAvroSchemaTypeOfObject(Object o) {
        return null;
    }

    public static TypeInfo getPrimitiveOrcTypeFromPrimitiveAvroType(Schema.Type avroType) throws IllegalArgumentException {
        return null;
    }

    public static String getHiveTypeFromAvroType(Schema avroSchema) {
        return null;
    }


    public static OrcFlowFileWriter createWriter(OutputStream flowFileOutputStream,
                                                 Path path,
                                                 Configuration conf,
                                                 TypeInfo orcSchema,
                                                 long stripeSize,
                                                 CompressionKind compress,
                                                 int bufferSize) throws IOException {

        return null;
    }

}