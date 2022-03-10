package net.woolgens.tickets.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
@MongoEntity(collection = "tickets_tickets")
public class Ticket {

    @BsonId
    private String id;

    private String title;
    private TicketUser issuer;
    private List<TicketUser> assignee;
    private String category;
    private String status;
    private long timestamp;
    private boolean open;
    private Map<String, String> content;
    private List<TicketChatEntry> entries;

}
