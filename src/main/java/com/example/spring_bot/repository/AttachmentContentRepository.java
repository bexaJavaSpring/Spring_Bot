package com.example.spring_bot.repository;

import com.example.spring_bot.entity.Attachment;
import com.example.spring_bot.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {

//    Optional<AttachmentContent> findByAttachment(Attachment attachment);

    Optional<AttachmentContent> findByAttachmentId(Integer attachment_id);

}
