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

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.JavaUtils;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.ql.io.IOConstants;
//import org.apache.hadoop.hive.ql.io.filters.BloomFilterIO;
import org.apache.hadoop.hive.ql.io.orc.CompressionCodec.Modifier;
import org.apache.hadoop.hive.ql.io.orc.OrcFile.CompressionStrategy;
import org.apache.hadoop.hive.ql.io.orc.OrcFile.EncodingStrategy;
import org.apache.hadoop.hive.ql.io.orc.OrcProto.RowIndexEntry;
import org.apache.hadoop.hive.ql.io.orc.OrcProto.StripeStatistics;
import org.apache.hadoop.hive.ql.io.orc.OrcProto.Type;
import org.apache.hadoop.hive.ql.io.orc.OrcProto.UserMetadataItem;
import org.apache.hadoop.hive.ql.util.JavaDataModel;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.UnionObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BinaryObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.ByteObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DateObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.FloatObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveCharObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveDecimalObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveVarcharObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.ShortObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.CharTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.DecimalTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.VarcharTypeInfo;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import org.apache.nifi.stream.io.ByteCountingOutputStream;

/**
 * An ORC file writer. The file is divided into stripes, which is the natural
 * unit of work when reading. Each stripe is buffered in memory until the
 * memory reaches the stripe size and then it is written out broken down by
 * columns. Each column is written by a TreeWriter that is specific to that
 * type of column. TreeWriters may have children TreeWriters that handle the
 * sub-types. Each of the TreeWriters writes the column's data as a set of
 * streams.
 * <p>
 * This class is synchronized so that multi-threaded access is ok. In
 * particular, because the MemoryManager is shared between writers, this class
 * assumes that checkMemory may be called from a separate thread.
 */
public class OrcFlowFileWriter implements Writer, MemoryManager.Callback {


    public OrcFlowFileWriter(OutputStream flowFileOutputStream,
                             Path path,
                             Configuration conf,
                             ObjectInspector inspector,
                             long stripeSize,
                             CompressionKind compress,
                             int bufferSize,
                             int rowIndexStride,
                             MemoryManager memoryManager,
                             boolean addBlockPadding,
                             OrcFile.Version version,
                             OrcFile.WriterCallback callback,
                             EncodingStrategy encodingStrategy,
                             CompressionStrategy compressionStrategy,
                             float paddingTolerance,
                             long blockSizeValue,
                             String bloomFilterColumnNames,
                             double bloomFilterFpp) throws IOException {

    }

    @VisibleForTesting
    int getEstimatedBufferSize(int bs) {
        return 0;
    }

    int getEstimatedBufferSize(String colNames, int bs) {
        return 0;
    }

    public static CompressionCodec createCodec(CompressionKind kind) {
        return null;
    }

    @Override
    public synchronized boolean checkMemory(double newScale) throws IOException {
        return false;
    }

    @VisibleForTesting
    public OutputStream getStream() throws IOException {
        return null;
    }

    @Override
    public synchronized void addUserMetadata(String name, ByteBuffer value) {
    }

    @Override
    public void addRow(Object row) throws IOException {
    }

    public void addRowBatch(VectorizedRowBatch batch) throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public long getRawDataSize() {
        return 0;
    }

    @Override
    public long getNumberOfRows() {
        return 0;
    }

    @Override
    public long writeIntermediateFooter() throws IOException {
        return 0;
    }

    @Override
    public void appendStripe(byte[] stripe, int offset, int length,
                             StripeInformation stripeInfo,
                             OrcProto.StripeStatistics stripeStatistics) throws IOException {

    }


    @Override
    public void appendUserMetadata(List<UserMetadataItem> userMetadata) {     }
}
