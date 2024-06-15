package com.propeltech.addressbook;

import com.propeltech.addressbook.entity.Record;
import com.propeltech.addressbook.service.RecordServiceImpl;
import com.propeltech.addressbook.util.JsonFileUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AddressBookAPITestCases {

    @Mock
    private JsonFileUtil jsonFileUtil;

    @InjectMocks
    private RecordServiceImpl recordServiceImpl;

    private Record record1;
    private Record record2;
    private Record record3;
    private Record record4;

    @Before
    public void setUp() throws IOException {

        recordServiceImpl = new RecordServiceImpl();

        MockitoAnnotations.initMocks(this);
        record1 = new Record();
        record1.setFirstName("Monica");
        record1.setLastName("Geller");
        record1.setEmail("monica.gel@example.com");
        record1.setPhone("0712765778");

        record2 = new Record();
        record2.setFirstName("Chandler");
        record2.setLastName("Bing");
        record2.setEmail("chand.bing@example.com");
        record2.setPhone("0732768189");

        record3 = new Record();
        record3.setFirstName("Rachel");
        record3.setLastName("Green");
        record3.setEmail("rach.green@example.com");
        record3.setPhone("07361123456");

        List<Record> records = Arrays.asList(record1, record2, record3);

        when(jsonFileUtil.readRecords()).thenReturn(records);
        doNothing().when(jsonFileUtil).writeRecords(anyList());

        recordServiceImpl.init();
    }

    @Test
    public void TestCaseForListAllRecords() {
        List<Record> records = recordServiceImpl.listAllRecords();
        assertEquals(3, records.size());
        assertTrue(records.contains(record1));
        assertTrue(records.contains(record2));
        assertTrue(records.contains(record3));
    }

    @Test
    public void TestCaseForViewRecord() {
        Record record = recordServiceImpl.viewRecord("monica.gel@example.com");
        assertNotNull(record);
        assertEquals("Monica", record.getFirstName());
        assertEquals("Geller", record.getLastName());


        Record nonExistentRecord = recordServiceImpl.viewRecord("noemailfound@example.com");
        assertNull(nonExistentRecord);
    }

    @Test
    public void TestCaseForAddRecord() {
        Record newRecord = new Record();
        newRecord.setFirstName("William");
        newRecord.setLastName("Tuman");
        newRecord.setEmail("will.tumn@example.com");
        newRecord.setPhone("0764546778");

        Record addedRecord = recordServiceImpl.addRecord(newRecord);
        assertNotNull(addedRecord);
        assertEquals(4, recordServiceImpl.listAllRecords().size());
        assertTrue(recordServiceImpl.listAllRecords().contains(newRecord));
    }
}
