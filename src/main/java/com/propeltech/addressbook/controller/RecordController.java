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
        return recordService.viewRecord(email);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> addRecord(@RequestBody Record record) {
        return recordService.addRecord(record);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEntry(@RequestBody Record record) {
        return recordService.editRecord(record);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Object> deleteEntry(@PathVariable String email) {
        return recordService.deleteRecord(email);
    }
}
