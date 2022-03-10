package net.woolgens.tickets.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import net.woolgens.tickets.model.Ticket;
import net.woolgens.tickets.model.TicketStatus;

import javax.enterprise.context.ApplicationScoped;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@ApplicationScoped
public class TicketStatusRepository implements PanacheMongoRepositoryBase<TicketStatus, String> {


}
