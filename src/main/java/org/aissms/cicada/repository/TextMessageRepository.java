package org.aissms.cicada.repository;

import java.util.List;

import org.aissms.cicada.entity.TextMessage;
import org.springframework.data.repository.CrudRepository;

public interface TextMessageRepository extends CrudRepository<TextMessage, Long>{
    
    List<TextMessage> findAllBySenderAndRecver(String sender, String recver);
}
