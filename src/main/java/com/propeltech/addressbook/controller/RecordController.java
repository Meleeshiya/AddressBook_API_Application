package com.propeltech.addressbook.controller;

import com.propeltech.addressbook.entity.Record;
import com.propeltech.addressbook.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





}
