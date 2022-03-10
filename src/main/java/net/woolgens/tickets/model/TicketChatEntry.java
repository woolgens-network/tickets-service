package net.woolgens.tickets.model;

import lombok.Data;

import java.util.Map;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
public class TicketChatEntry {

    private String type;
    private String value;
    private Map<Long, String> history;
    private TicketUser issuer;
}
