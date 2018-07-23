/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.sekuel;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.json.XML;
//import org.json.JSONObject;
//import org.json.XML;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author khanzasoft
 */
public class DUKCAPILJakartaCekNik {
    private final Properties prop = new Properties();
    private final sekuel Sequel=new sekuel();
    private final Properties prop2 = new Properties();
    public String DSC_JENIS_PKRJN,NM_PROP,UMUR,NAMA_LGKP,NO_AKTA_LHR,
            AKTA_LHR,JENIS_PKRJN,TGL_LHR,TMPT_LHR,NM_KEC,NO_KEL,
            NO_KK,NM_KAB,NO_RT,NIK,NO_KAB,NM_KEL,ALAMAT,JENIS_KLMIN,
            NO_RW,NO_PROP,NO_KEC,DSC_STAT_KWN,DSC_STAT_HBKEL,DSC_GOL_DRH,NOBPJS,URL;
     public JsonNode nameNode,nameNode2,root;
    public DUKCAPILJakartaCekNik(){
        super();
    }
    
    public void tampil(String nik) {
    //    BPJSApi api=new BPJSApi();
   
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            HttpHeaders headers = new HttpHeaders();
            if(prop.getProperty("DISKOMINFODUKCAPILJAKARTA").equals("YES"))
            {
                URL = prop.getProperty("DISKOMINFOURLDUKCAPILJAKARTA")+"getNIK?app="+prop.getProperty("DISKOMINFOVAR1DUKCAPILJAKARTA")+"&pget="+prop.getProperty("DISKOMINFOVAR2DUKCAPILJAKARTA")+"&pusr="+prop.getProperty("DISKOMINFOVAR3DUKCAPILJAKARTA")+"&nik=" + nik;
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization","Basic " + Base64.getEncoder().encodeToString((prop.getProperty("DISKOMINFOUSERDUKCAPILJAKARTA")+":"+prop.getProperty("DISKOMINFOPASSDUKCAPILJAKARTA")).getBytes()));
                HttpEntity requestEntity = new HttpEntity(headers);
                RestTemplate rest = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();
                root = mapper.readTree(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode2 = root.path("DATA");
                nameNode = nameNode2.path("ANGGOTA");
            }
            else
            {
                URL = prop.getProperty("URLDUKCAPILJAKARTA")+"?usernm="+prop.getProperty("USERDUKCAPILJAKARTA")+"&pass="+prop.getProperty("PASSDUKCAPILJAKARTA")+"&app=SILaporLahir&pget=Kelahiran&pusr=admin&proc=GETNIK&nik="+nik+"&pkey="+Sequel.cariIsi("select md5(concat('"+prop.getProperty("VAR1DUKCAPILJAKARTA")+"',md5(date_format(current_date(),'%d%m%Y')),'"+prop.getProperty("VAR2DUKCAPILJAKARTA")+"'))");
                headers.setContentType(MediaType.APPLICATION_XML);
                headers.add("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
                HttpEntity requestEntity = new HttpEntity(headers);
                RestTemplate rest = new RestTemplate();
                String data=rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody();
                JSONObject xmlJSONObj = XML.toJSONObject(data);
                String jsonPrettyPrintString = xmlJSONObj.toString(4);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonPrettyPrintString);
                nameNode = root.path("DATA");
            }
             try {
                DSC_JENIS_PKRJN=nameNode.path("DSC_JENIS_PKRJN").asText();
                NM_PROP=nameNode.path("NM_PROP").asText();
            //    UMUR=nameNode.path("UMUR").asText();
                NAMA_LGKP=nameNode.path("NAMA_LGKP").asText();
                NO_AKTA_LHR=nameNode.path("NO_AKTA_LHR").asText();
                AKTA_LHR=nameNode.path("AKTA_LHR").asText();
                JENIS_PKRJN=nameNode.path("JENIS_PKRJN").asText();
                TGL_LHR=nameNode.path("TGL_LHR").asText().substring(6,10)+"-"+nameNode.path("TGL_LHR").asText().substring(3,5)+"-"+nameNode.path("TGL_LHR").asText().substring(0,2);
                TMPT_LHR=nameNode.path("TMPT_LHR").asText();
                NM_KEC=nameNode.path("NM_KEC").asText();
                NO_KEL=nameNode.path("NO_KEL").asText();
                NO_KK=nameNode.path("NO_KK").asText();
                NM_KAB=nameNode.path("NM_KAB").asText();
                NO_RT=nameNode.path("NO_RT").asText();
                NIK=nameNode.path("NIK").asText();
                NO_KAB=nameNode.path("NO_KAB").asText();
                NM_KEL=nameNode.path("NM_KEL").asText();
                ALAMAT=nameNode.path("ALAMAT").asText();
                JENIS_KLMIN=nameNode.path("JENIS_KLMIN").asText().replaceAll("1","LAKI-LAKI").replaceAll("2","PEREMPUAN");
                NO_RW=nameNode.path("NO_RW").asText();
                NO_PROP=nameNode.path("NO_PROP").asText();
                NO_KEC=nameNode.path("NO_KEC").asText();
                DSC_STAT_KWN=nameNode.path("DSC_STAT_KWN").asText().replaceAll("Belum Kawin","BELUM MENIKAH").replaceAll("Sudah Kawin","MENIKAH");
                DSC_STAT_HBKEL=nameNode.path("DSC_STAT_HBKEL").asText();
                DSC_GOL_DRH=nameNode.path("DSC_GOL_DRH").asText().replaceAll("Tidak Tahu","-");
                 NOBPJS=nameNode.path("NOBPJS").asText();
                
            } catch (Exception e) {
                DSC_JENIS_PKRJN="";
                NM_PROP="";
              //  UMUR="";
                NAMA_LGKP="";
                NO_AKTA_LHR="";
                AKTA_LHR="";
                JENIS_PKRJN="";
                TGL_LHR="";
                TMPT_LHR="";
                NM_KEC="";
                NO_KEL="";
                NO_KK="";
                NM_KAB="";
                NO_RT="";
                NIK="";
                NO_KAB="";
                NM_KEL="";
                ALAMAT="";
                JENIS_KLMIN="";
                NO_RW="";
                NO_PROP="";
                NO_KEC="";
                DSC_STAT_KWN="";
                DSC_STAT_HBKEL="";
                DSC_GOL_DRH="";
            }
            
             
            
            
           
          
            
           
                       
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
           
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server DUKCAPIL terputus...!");
            }
        }
       //  System.out.println(URL);
    }
    
}
