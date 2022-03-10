package net.woolgens.tickets.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
@MongoEntity(collection = "tickets_categories")
public class TicketCategory {

    @BsonId
    private String id;
    private String name;
    private String description;
    private List<TicketInput> inputs;
    private boolean active;
}
