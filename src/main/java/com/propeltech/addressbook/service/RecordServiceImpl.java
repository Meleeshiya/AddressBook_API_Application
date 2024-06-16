package com.propeltech.addressbook.service;

import com.propeltech.addressbook.entity.Record;
import com.propeltech.addressbook.util.JsonFileUtil;
import com.propeltech.addressbook.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private JsonFileUtil jsonFileUtil;

    private List<Record> records;

    @PostConstruct
    public void init() {
        try {
            records = new ArrayList<>(jsonFileUtil.readRecords());
        } catch (IOException e) {
            records = new ArrayList<>();
        }
    }

    private void saveRecords() {
        try {
            jsonFileUtil.writeRecords(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Record> listAllRecords() {
        return records;
    }

    @Override
    public ResponseEntity<Object> viewRecord(String email) {
        Record rec  = records.stream()
                .filter(record -> record.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if(rec != null){
            return new ResponseEntity<>(rec, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Record not found in Address Book.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> addRecord(Record record) {
        ResponseEntity<Object> validationResponse = validateRecord(record);
        if (validationResponse != null) {
            return validationResponse;
        }

        boolean isEmailUsed = records.stream().anyMatch(rec -> rec.getEmail().equalsIgnoreCase(record.getEmail()));
        if (isEmailUsed) {
            return new ResponseEntity<>("The email address you provided is already in use. Please use a different email address.", HttpStatus.CONFLICT);
        } else {
            records.add(record);
            saveRecords();
            return new ResponseEntity<>("The record has been successfully added to the Address Book.", HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object>  editRecord(Record record) {
        Optional<Record> existingEntry = records.stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(record.getEmail()))
                .findFirst();
        if (existingEntry.isPresent()) {
            records.remove(existingEntry.get());
            records.add(record);
            saveRecords();
            return new ResponseEntity<>("Record has been updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to edit: Record could not be found in the Address Book.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> deleteRecord(String email) {
        boolean isRemoved = records.removeIf(entry -> entry.getEmail().equalsIgnoreCase(email));
        if (isRemoved) {
            saveRecords();
            return new ResponseEntity<>("Record removed from the Address Book successfully.",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to delete: Record could not be found in the Address Book", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> validateRecord(Record record) {
        if (!Validator.isValidEmail(record.getEmail())) {
            return new ResponseEntity<>("Invalid email format. Please provide a valid email address.", HttpStatus.BAD_REQUEST);
        }
        if (!Validator.isValidPhoneNumber(record.getPhone())) {
            return new ResponseEntity<>("Invalid phone number format. Please provide a 10-digit phone number.", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
