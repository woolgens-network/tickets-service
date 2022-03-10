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
public class TicketInput {

    private String id;
    private String label;
    private String hint;
    private String type;
    private boolean required;
    private Map<String, Object> meta;
}
