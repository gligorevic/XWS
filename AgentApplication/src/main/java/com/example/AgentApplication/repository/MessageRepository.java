package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT DISTINCT m FROM Message m WHERE m.reciever.email = :email")
    List<Message> getMessagesByReciever(@Param("email") String email);

    @Query("SELECT DISTINCT m FROM Message m WHERE m.request.id =:id")
    List<Message> getMessagesByRequest(@Param("id") Long id);
}
