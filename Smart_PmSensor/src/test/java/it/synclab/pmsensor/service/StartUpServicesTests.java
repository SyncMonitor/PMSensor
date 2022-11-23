package it.synclab.pmsensor.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.synclab.pmsensor.model.AmbientInfos;
import it.synclab.pmsensor.model.AmbientInfosList;
import it.synclab.pmsensor.model.Humidity;
import it.synclab.pmsensor.model.ParticularMatter10;
import it.synclab.pmsensor.model.ParticularMatter25;
import it.synclab.pmsensor.model.Temperature;
import it.synclab.pmsensor.service.AmbientInfosService;
import it.synclab.pmsensor.service.StartUpServices;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartUpServicesTests {
    @Autowired
    private StartUpServices sus;

    @Autowired
    private AmbientInfosService ais;
 
    AmbientInfosList aList=new AmbientInfosList();
    List<AmbientInfos> list = new ArrayList<>();
    AmbientInfos ambinfo=new AmbientInfos();
    int size=0;

    
    @Before
    public void init() throws ParseException {
        ambinfo.setId(size+1000L);
        ambinfo.setDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("20-11-2022 17:00:00"));
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date timestamp = new Date(ts.getTime());
        ParticularMatter25 pm25=new ParticularMatter25(size+1001L, "Padova Galleria Spagna", "45.388653", "11.928344", timestamp, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("20-11-2022 17:00:00"), Double.parseDouble("13.5"), ambinfo);
        ParticularMatter10 pm10=new ParticularMatter10(size+1002L, "Padova Galleria Spagna", "45.388653", "11.928344", timestamp, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("20-11-2022 17:00:00"), Double.parseDouble("20.2"), ambinfo);
        Temperature temp=new Temperature(size+1003L, "Padova Galleria Spagna", "45.388653", "11.928344", timestamp, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("20-11-2022 17:00:00"), Double.parseDouble("10.2"), ambinfo);
        Humidity hum=new Humidity(size+1004L, "Padova Galleria Spagna", "45.388653", "11.928344", timestamp, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("20-11-2022 17:00:00"), Double.parseDouble("99.9"), ambinfo);
        ambinfo.setPMatter25(pm25);
        ambinfo.setPMatter10(pm10);
        ambinfo.setTemp(temp);
        ambinfo.setHumidity(hum);
        size=ais.getAllAmbientInfos().size();
        list.add(ambinfo);
        aList.setAil(list);

    }

    @Test
    public void saveLastDataTest(){
        sus.saveLastData(aList);
        Assert.assertEquals(size+1, ais.getAllAmbientInfos().size());
    }

    @Test
    public void deleteAmbientInfosByIdTest(){
        sus.saveLastData(aList);
        ais.deleteAmbientInfosById(ambinfo.getId());
        Assert.assertEquals(size, ais.getAllAmbientInfos().size());
    }

}