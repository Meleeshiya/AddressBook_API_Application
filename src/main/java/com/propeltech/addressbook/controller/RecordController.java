package com.propeltech.addressbook.controller;

import com.propeltech.addressbook.entity.Record;
import com.propeltech.addressbook.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("api/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/getall")
    public ResponseEntity<Object> getAll() {
        List<Record> entries = recordService.listAllRecords();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getRecordByEmail(@PathVariable String email) {
        Record record = recordService.viewRecord(email);
        if (record != null) {
            return new ResponseEntity<>(record, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Record not found in Address Book.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> addRecord(@RequestBody Record record) {
        ResponseEntity<Object> validationResponse = recordService.validateRecord(record);
        if (validationResponse != null) {
            return validationResponse;
        }

        Record savedRecord = recordService.addRecord(record);
        if (savedRecord != null) {
            return new ResponseEntity<>("The record has been successfully added to the Address Book.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("The email address you provided is already in use. Please use a different email address.", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEntry(@RequestBody Record record) {
        Record updatedRecord = recordService.editRecord(record);
        if (updatedRecord != null) {
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to edit: Record could not be found in the Address Book.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Object> deleteEntry(@PathVariable String email) {
        boolean isDeleted = recordService.deleteRecord(email);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Unable to delete: Record could not be found in the Address Book", HttpStatus.NOT_FOUND);
        }
    }
}
