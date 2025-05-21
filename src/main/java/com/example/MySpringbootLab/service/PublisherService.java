package com.example.MySpringbootLab.service;

import com.example.MySpringbootLab.entity.Publisher;
import com.example.MySpringbootLab.entity.dto.PublisherDTO;
import com.example.MySpringbootLab.exception.BusinessException;
import com.example.MySpringbootLab.exception.ErrorCode;
import com.example.MySpringbootLab.repository.BookRepository;
import com.example.MySpringbootLab.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public List<PublisherDTO.Response> getAllPublishers(){
        return publisherRepository.findAll()
                .stream()
                .map(PublisherDTO.Response::fromEntity)
                .toList();
    }

    public PublisherDTO.Response getPublisherById(Long id){
        Publisher publisher =  publisherRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));

        return PublisherDTO.Response.fromEntity(publisher);
    }

    public PublisherDTO.Response getPublisherByName(String name){
        Publisher publisher =  publisherRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "name", name));

        return PublisherDTO.Response.fromEntity(publisher);
    }


    @Transactional
    public PublisherDTO.Response createPublisher(PublisherDTO.Request request){
        if (publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getName());
        }

        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .address(request.getAddress())
                .build();

        Publisher savedPublisher = publisherRepository.save(publisher);
        return PublisherDTO.Response.fromEntity(savedPublisher);

    }

    @Transactional
    public PublisherDTO.Response updatePublisher(Long id, PublisherDTO.Request request){
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id));

        if(!publisher.getName().equals(request.getName()) && publisherRepository.existsByName(request.getName())){
            throw new BusinessException(ErrorCode.PUBLISHER_NAME_DUPLICATE, request.getName());
        }

        publisher.setName(request.getName());
        publisher.setAddress(request.getAddress());
        
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return PublisherDTO.Response.fromEntity((updatedPublisher));
    }

    @Transactional
    public void deletePublisher(Long id){
        if(!publisherRepository.existsById(id)){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Publisher", "id", id);
        }
        publisherRepository.deleteById(id);
    }
}
