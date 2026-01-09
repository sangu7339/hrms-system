package com.vbz.hrms.config;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
public class MongoGridFsConfig {

    @Bean
    public GridFSBucket gridFSBucket(MongoDatabaseFactory factory) {
        return GridFSBuckets.create(factory.getMongoDatabase());
    }
}
