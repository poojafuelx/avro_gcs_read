/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.storage;

import com.google.api.gax.paging.Page;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;


import java.io.*;
import java.nio.channels.Channels;


public class QuickstartSample {
  public static void main(String... args) throws Exception {


    Storage storage = StorageOptions.getDefaultInstance().getService();

    //Page<Bucket> buckets = storage.list();
    Bucket bucket = storage.get("pooja-dev");
    System.out.println("Bucket Name:"+bucket.getName());

   // Schema schema = new Schema.Parser().parse(new File("/Users/poojad/Rawlog.avsc"));

    DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();

    String bucketName = "pooja-dev";
    String blobName = "rawlog_2018_09_03_06_0-20-pane-0-ON_TIME.avro";


    // [START readerFromStrings]
    try (ReadChannel reader = storage.reader(bucketName, blobName)) {
        InputStream inputStream = Channels.newInputStream(reader);

      DataFileStream<GenericRecord> dataFileReader = new DataFileStream<GenericRecord>(inputStream, datumReader);
      Schema schema = dataFileReader.getSchema();
      System.out.println(schema);

      GenericRecord consumedDatum = null;
      int i = 0;
      while (dataFileReader.hasNext()) {
        consumedDatum = dataFileReader.next(consumedDatum);
        //i = i + 1;
        System.out.println("JSON representation of Avro message: " + consumedDatum.toString() );
        //System.out.println("Number Of Records in AVRO file " + i );

      }
      //System.out.println("Number Of Records in AVRO file " + i );
      dataFileReader.close();

       /**
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream));

         String newLine = System.getProperty("line.separator");
         String line;
         while ((line = reader1.readLine()) != null) {
             System.out.println(line);

          }*/
         }

      /**
       Page<Blob> blobs = bucket.list();

       for (Blob blob : blobs.iterateAll()) {
       System.out.println(blob.getName());
       }

       */
      /**
       BlobId blobId = BlobId.of("pooja-dev", "test");
       BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/csv").build();
       Blob blob = storage.create(blobInfo,"bid,cid,gid".getBytes(UTF_8));
       */


  }
}
// [END storage_quickstart]
