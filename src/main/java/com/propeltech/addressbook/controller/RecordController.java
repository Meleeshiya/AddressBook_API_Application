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
            return new ResponseEntity<>("Record not found in Address Book", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Void> addRecord(@RequestBody Record record) {
        recordService.addRecord(record);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
