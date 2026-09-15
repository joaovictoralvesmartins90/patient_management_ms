package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event){
        //A anotação vai escutar por todos os eventos do tópico "patient"
        try{
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            //lógica de analytics
            log.info("Received patient info: ID={}, NAME={}, EMAIL={}"
                    ,patientEvent.getPatientId()
                    ,patientEvent.getName()
                    ,patientEvent.getEmail());
        }catch (InvalidProtocolBufferException ex){
            log.error("Error deserializing event {}", ex.getMessage());
        }
    }
}
