package com.dxt;

import com.dxt.EchannelServerApplication;
import com.dxt.dao.ScanMesParseDao;
import com.dxt.service.ScanMes;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EchannelServerApplication.class)
public class MyXMLReader2DOM {
   @Autowired
   private ScanMesParseDao scanMesParseDao;
    @Test
    public void text() {
        System.out.println(scanMesParseDao);
          try {
                File f = new File("src/10000002162000280001BUS6101920200309A00038.txt");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                 Document doc = builder.parse(f);
                 NodeList nl = doc.getElementsByTagName("Data");
                 Map map=new HashMap();
                for (int i = 0; i < nl.getLength(); i++) {

                    map.put("iccid",doc.getElementsByTagName("ICCID").item(i).getFirstChild().getNodeValue());
                    map.put("imsi",doc.getElementsByTagName("IMSI").item(i).getFirstChild().getNodeValue());
                    map.put("pin1",doc.getElementsByTagName("PIN1").item(i).getFirstChild().getNodeValue());
                    map.put("puk1",doc.getElementsByTagName("PUK1").item(i).getFirstChild().getNodeValue());
                    map.put("pin2",doc.getElementsByTagName("PIN2").item(i).getFirstChild().getNodeValue());
                    map.put("puk2",doc.getElementsByTagName("PUK2").item(i).getFirstChild().getNodeValue());
                    map.put("adm",doc.getElementsByTagName("ADM").item(i).getFirstChild().getNodeValue());
                    map.put("uimid",doc.getElementsByTagName("UIMID").item(i).getFirstChild().getNodeValue());
                    map.put("imsi_g",doc.getElementsByTagName("IMSI_G").item(0).getFirstChild().getNodeValue());
                    map.put("smsp",doc.getElementsByTagName("SMSP").item(i).getFirstChild().getNodeValue());
                    map.put("imsi_lte",doc.getElementsByTagName("IMSI_LTE").item(i).getFirstChild().getNodeValue());
                    map.put("file_name","10000002162000280001BUS6101920200309A00038");

                    scanMesParseDao.insetMesInfoIntoParseMes(map);
//                    System.out.println("dxt00000"+i+"ICCID:"+ doc.getElementsByTagName("ICCID").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"IMSI:"+ doc.getElementsByTagName("IMSI").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"PIN1:"+ doc.getElementsByTagName("PIN1").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"PUK1:"+ doc.getElementsByTagName("PUK1").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"PIN2:"+ doc.getElementsByTagName("PIN2").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"PUK2:"+ doc.getElementsByTagName("PUK2").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"ADM:"+ doc.getElementsByTagName("ADM").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"UIMID:"+ doc.getElementsByTagName("UIMID").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"IMSI_G:"+ doc.getElementsByTagName("IMSI_G").item(0).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"SMSP:"+ doc.getElementsByTagName("SMSP").item(i).getFirstChild().getNodeValue());
//                    System.out.println("dxt00000"+i+"IMSI_LTE:"+ doc.getElementsByTagName("IMSI_LTE").item(i).getFirstChild().getNodeValue());
//                    System.out.println("---------------------------------------------------------------");
                    map.clear();
                }


               } catch (Exception e) {
                    e.printStackTrace();
               }
         }
}
