package net.woolgens.tickets.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
@MongoEntity(collection = "tickets_status")
public class TicketStatus {

    @BsonId
    private String id;
    private String label;
    private String color;
    private boolean willClose;
}
