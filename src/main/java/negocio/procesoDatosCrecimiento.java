/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dto.TipoDato;
import static dto.TipoDato.STRING;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Deisy
 */
public class procesoDatosCrecimiento {
    
    public static final String dbServer = "mongodb://localhost:27017/visual_analytics";
    //public static final String dbServer = "mongodb://analiticafundacioncanguro.com:27017/visual_analytics";
    
    public static final String dbName ="visual_analytics";
    public static MongoDatabase conexionDB;
    
    private static Map<String,TipoDato> mapVariablesBD;
     
    static {
        
        //inicializa variables fijas que se encuentran en base de datos
        mapVariablesBD=new HashMap();
        mapVariablesBD.put("CODE", TipoDato.INTEGER);
        mapVariablesBD.put("ANOCAT", TipoDato.INTEGER);
        mapVariablesBD.put("sexo", TipoDato.INTEGER);
        mapVariablesBD.put("peso.sem40", TipoDato.DOUBLE);
        mapVariablesBD.put("peso.mes3", TipoDato.DOUBLE);
        mapVariablesBD.put("peso.mes6", TipoDato.DOUBLE);
        mapVariablesBD.put("peso.mes9", TipoDato.DOUBLE);
        mapVariablesBD.put("peso.mes12", TipoDato.DOUBLE);
        mapVariablesBD.put("talla.sem40", TipoDato.DOUBLE);
        mapVariablesBD.put("talla.mes3", TipoDato.DOUBLE);
        mapVariablesBD.put("talla.mes6", TipoDato.DOUBLE);
        mapVariablesBD.put("talla.mes9", TipoDato.DOUBLE);
        mapVariablesBD.put("talla.mes12", TipoDato.DOUBLE);
        mapVariablesBD.put("pc.sem40", TipoDato.DOUBLE);
        mapVariablesBD.put("pc.mes3", TipoDato.DOUBLE);
        mapVariablesBD.put("pc.mes6", TipoDato.DOUBLE);
        mapVariablesBD.put("pc.mes9", TipoDato.DOUBLE);
        mapVariablesBD.put("pc.mes12", TipoDato.DOUBLE);
        mapVariablesBD.put("alimentacion.sem40", TipoDato.INTEGER);
        mapVariablesBD.put("alimentacion.mes3", TipoDato.INTEGER);
        mapVariablesBD.put("alimentacion.mes6", TipoDato.INTEGER);
        mapVariablesBD.put("alimentacion.mes9", TipoDato.INTEGER);
        mapVariablesBD.put("alimentacion.mes12", TipoDato.INTEGER);
        mapVariablesBD.put("edadmama", TipoDato.INTEGER);
        mapVariablesBD.put("nivelmama", TipoDato.INTEGER);
        mapVariablesBD.put("nivelpapa", TipoDato.INTEGER);
        mapVariablesBD.put("pesopapa", TipoDato.DOUBLE);
        mapVariablesBD.put("pesomama", TipoDato.DOUBLE);
        mapVariablesBD.put("tallapapa", TipoDato.INTEGER);
        mapVariablesBD.put("tallamama", TipoDato.INTEGER);
        mapVariablesBD.put("primipara", TipoDato.INTEGER);
        mapVariablesBD.put("numerocontrolprenatal", TipoDato.INTEGER);
        mapVariablesBD.put("embarazodeseado", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUpesoFenton", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpesowho.sem40", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpesowho.mes3", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpesowho.mes6", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpesowho.mes9", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpesowho.mes12", TipoDato.INTEGER);
        mapVariablesBD.put("Nuttallawho.sem40", TipoDato.INTEGER);
        mapVariablesBD.put("Nuttallawho.mes3", TipoDato.INTEGER);
        mapVariablesBD.put("Nuttallawho.mes6",  TipoDato.INTEGER);
        mapVariablesBD.put("Nuttallawho.mes9", TipoDato.INTEGER);
        mapVariablesBD.put("Nuttallawho.mes12", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpcwho.sem40", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpcwho.mes3", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpcwho.mes6", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpcwho.mes9", TipoDato.INTEGER);
        mapVariablesBD.put("Nutpcwho.mes12", TipoDato.INTEGER);
        mapVariablesBD.put("edadgestacionalalaentrada", TipoDato.INTEGER_STRING);
        mapVariablesBD.put("pesoalaentrada", TipoDato.DOUBLE);
        mapVariablesBD.put("pesoalnacer", TipoDato.DOUBLE);
        mapVariablesBD.put("tallaalnacer", TipoDato.DOUBLE);
        mapVariablesBD.put("pcalnacer", TipoDato.DOUBLE);
        mapVariablesBD.put("edadgestacional", TipoDato.INTEGER_STRING);
        mapVariablesBD.put("percapitasalariominimo", TipoDato.DOUBLE);
        
        mapVariablesBD.put("centro", TipoDato.INTEGER);
        mapVariablesBD.put("codigooriginal", TipoDato.INTEGER);
        mapVariablesBD.put("estadovital", TipoDato.INTEGER);
        mapVariablesBD.put("muerteprimerano", TipoDato.INTEGER);
        mapVariablesBD.put("Embarazomultiple", TipoDato.INTEGER);
        mapVariablesBD.put("edadgestacional2", TipoDato.DOUBLE);
        mapVariablesBD.put("toxemia", TipoDato.INTEGER);
        mapVariablesBD.put("UCI", TipoDato.INTEGER);
        mapVariablesBD.put("cesarea", TipoDato.INTEGER);
        mapVariablesBD.put("classificacionlubchenco", TipoDato.INTEGER);
        mapVariablesBD.put("TotalDiasHospitalizacion", TipoDato.INTEGER);
        mapVariablesBD.put("trimestre", TipoDato.INTEGER);
        mapVariablesBD.put("oftalmologiafinal", TipoDato.INTEGER);
        mapVariablesBD.put("ROP", TipoDato.INTEGER);
        mapVariablesBD.put("resoptometria", TipoDato.INTEGER);
        mapVariablesBD.put("audiometria", TipoDato.INTEGER);
        mapVariablesBD.put("edadalaentrada", TipoDato.INTEGER);
        mapVariablesBD.put("tallaalaentrada", TipoDato.DOUBLE);
        mapVariablesBD.put("PCalaentrada", TipoDato.DOUBLE);
        mapVariablesBD.put("edadgestacionalalaentrada2", TipoDato.DOUBLE);
        mapVariablesBD.put("PesosalidaPC", TipoDato.DOUBLE);
        mapVariablesBD.put("edadsalidaPC", TipoDato.INTEGER);
        mapVariablesBD.put("edadgestasalPC", TipoDato.DOUBLE);
        mapVariablesBD.put("Infanib3m", TipoDato.INTEGER);
        mapVariablesBD.put("Infanib6m", TipoDato.INTEGER);
        mapVariablesBD.put("Infanib9m", TipoDato.INTEGER);
        mapVariablesBD.put("Infanib12m", TipoDato.INTEGER);
        mapVariablesBD.put("CD6", TipoDato.DOUBLE);
        mapVariablesBD.put("CD12", TipoDato.DOUBLE);
        mapVariablesBD.put("mes40", TipoDato.INTEGER);
        mapVariablesBD.put("mes3", TipoDato.INTEGER);
        mapVariablesBD.put("mes6", TipoDato.INTEGER);
        mapVariablesBD.put("mes9", TipoDato.INTEGER);
        mapVariablesBD.put("mes12", TipoDato.INTEGER);
        mapVariablesBD.put("alisalida", TipoDato.INTEGER);
        mapVariablesBD.put("Indexnutricion40sem", TipoDato.INTEGER);
        mapVariablesBD.put("indexnutricion12meses", TipoDato.INTEGER);
        mapVariablesBD.put("oxigenoentrada", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUtalla", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUPC", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUpesoFentonentrada", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUtallaentrada", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUPCentrada", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUfentonpesotalla", TipoDato.INTEGER);
        mapVariablesBD.put("RCIUfentonpesotallaPC", TipoDato.INTEGER);
        mapVariablesBD.put("CD66", TipoDato.DOUBLE);
        mapVariablesBD.put("CD122", TipoDato.DOUBLE);
        mapVariablesBD.put("tipoRCIU", TipoDato.INTEGER);
        mapVariablesBD.put("tipoRCIU2", TipoDato.INTEGER);

        mapVariablesBD.put("educmadre", TipoDato.INTEGER);	
        mapVariablesBD.put("algoLM40sem", TipoDato.INTEGER);	
        mapVariablesBD.put("algoLM3m", TipoDato.INTEGER);	
        mapVariablesBD.put("algoLM6m", TipoDato.INTEGER);	
        mapVariablesBD.put("RSM6", TipoDato.DOUBLE);	
        mapVariablesBD.put("RSM12", TipoDato.DOUBLE);	
        mapVariablesBD.put("SSalud", TipoDato.INTEGER);
    }    
    
    private static String validarVariables(List<String> listaVariablesSubir) {
        
        String cadMensaje = "";
        
        for(String nombre : listaVariablesSubir){
            if(!mapVariablesBD.containsKey(nombre))
                cadMensaje += nombre + "\n";
        }
        
        return cadMensaje;
    }
     
    public static void calcularOutliers(Date fechaCreacion){
        String[] listaSemanas = {"sem24","sem25","sem26","sem27","sem28","sem29","sem30","sem31","sem32","sem33","sem34","sem35","sem36","sem37","sem38","sem39"
                                ,"sem40","mes3","mes6","mes9","mes12"};
        
        conexionDB = new MongoClient(new MongoClientURI(dbServer)).getDatabase(dbName);
        MongoCollection mongoCollection = conexionDB.getCollection("medidas_crecimiento");
        
        
        //peso
        Bson group = new Document(new Document("$group", new Document("_id","estadisticas")
            .append("stdDev_sem24", new Document("$stdDevPop","$peso.sem24")).append("prom_sem24", new Document("$avg","$peso.sem24"))  
            .append("stdDev_sem25", new Document("$stdDevPop","$peso.sem25")).append("prom_sem25", new Document("$avg","$peso.sem25"))  
            .append("stdDev_sem26", new Document("$stdDevPop","$peso.sem26")).append("prom_sem26", new Document("$avg","$peso.sem26"))  
            .append("stdDev_sem27", new Document("$stdDevPop","$peso.sem27")).append("prom_sem27", new Document("$avg","$peso.sem27"))  
            .append("stdDev_sem28", new Document("$stdDevPop","$peso.sem28")).append("prom_sem28", new Document("$avg","$peso.sem28"))  
            .append("stdDev_sem29", new Document("$stdDevPop","$peso.sem29")).append("prom_sem29", new Document("$avg","$peso.sem29"))  
            .append("stdDev_sem30", new Document("$stdDevPop","$peso.sem30")).append("prom_sem30", new Document("$avg","$peso.sem30"))  
            .append("stdDev_sem31", new Document("$stdDevPop","$peso.sem31")).append("prom_sem31", new Document("$avg","$peso.sem31"))  
            .append("stdDev_sem32", new Document("$stdDevPop","$peso.sem32")).append("prom_sem32", new Document("$avg","$peso.sem32"))  
            .append("stdDev_sem33", new Document("$stdDevPop","$peso.sem33")).append("prom_sem33", new Document("$avg","$peso.sem33"))  
            .append("stdDev_sem34", new Document("$stdDevPop","$peso.sem34")).append("prom_sem34", new Document("$avg","$peso.sem34"))  
            .append("stdDev_sem35", new Document("$stdDevPop","$peso.sem35")).append("prom_sem35", new Document("$avg","$peso.sem35"))  
            .append("stdDev_sem36", new Document("$stdDevPop","$peso.sem36")).append("prom_sem36", new Document("$avg","$peso.sem36"))  
            .append("stdDev_sem37", new Document("$stdDevPop","$peso.sem37")).append("prom_sem37", new Document("$avg","$peso.sem37"))  
            .append("stdDev_sem38", new Document("$stdDevPop","$peso.sem38")).append("prom_sem38", new Document("$avg","$peso.sem38"))  
            .append("stdDev_sem39", new Document("$stdDevPop","$peso.sem39")).append("prom_sem39", new Document("$avg","$peso.sem39"))  
            .append("stdDev_sem40", new Document("$stdDevPop","$peso.sem40")).append("prom_sem40", new Document("$avg","$peso.sem40"))  
            .append("stdDev_mes3", new Document("$stdDevPop","$peso.mes3")).append("prom_mes3", new Document("$avg","$peso.mes3"))  
            .append("stdDev_mes6", new Document("$stdDevPop","$peso.mes6")).append("prom_mes6", new Document("$avg","$peso.mes6"))  
            .append("stdDev_mes9", new Document("$stdDevPop","$peso.mes9")).append("prom_mes9", new Document("$avg","$peso.mes9"))  
            .append("stdDev_mes12", new Document("$stdDevPop","$peso.mes12")).append("prom_mes12", new Document("$avg","$peso.mes12"))  
        ));
        
        AggregateIterable<Document> docEstadisticas = mongoCollection.aggregate(Arrays.asList(group));
        
        double desviacion;
        double promedio;
        
        for (Document doc : docEstadisticas) {
           
            for(int i=0; i<listaSemanas.length; i++){
                desviacion = (double) doc.get("stdDev_"+listaSemanas[i]);
                promedio = (double) doc.get("prom_"+listaSemanas[i]);
                
                Bson filter = new Document("peso."+listaSemanas[i], new Document("$gt",4*desviacion+promedio)).append("fechaCreacion", new Document("$gte",fechaCreacion));
                Bson set = new Document("$set", new Document("peso.outlier",true));
                mongoCollection.updateMany(filter, set);
                
                filter = new Document("peso."+listaSemanas[i], new Document("$lt",-4*desviacion+promedio)).append("fechaCreacion", new Document("$gte",fechaCreacion));
                set = new Document("$set", new Document("peso.outlier",true));
                mongoCollection.updateMany(filter, set);
            }
  
        }
        
        
        //talla
        group = new Document(new Document("$group", new Document("_id","estadisticas")
            .append("stdDev_sem24", new Document("$stdDevPop","$talla.sem24")).append("prom_sem24", new Document("$avg","$talla.sem24"))  
            .append("stdDev_sem25", new Document("$stdDevPop","$talla.sem25")).append("prom_sem25", new Document("$avg","$talla.sem25"))  
            .append("stdDev_sem26", new Document("$stdDevPop","$talla.sem26")).append("prom_sem26", new Document("$avg","$talla.sem26"))  
            .append("stdDev_sem27", new Document("$stdDevPop","$talla.sem27")).append("prom_sem27", new Document("$avg","$talla.sem27"))  
            .append("stdDev_sem28", new Document("$stdDevPop","$talla.sem28")).append("prom_sem28", new Document("$avg","$talla.sem28"))  
            .append("stdDev_sem29", new Document("$stdDevPop","$talla.sem29")).append("prom_sem29", new Document("$avg","$talla.sem29"))  
            .append("stdDev_sem30", new Document("$stdDevPop","$talla.sem30")).append("prom_sem30", new Document("$avg","$talla.sem30"))  
            .append("stdDev_sem31", new Document("$stdDevPop","$talla.sem31")).append("prom_sem31", new Document("$avg","$talla.sem31"))  
            .append("stdDev_sem32", new Document("$stdDevPop","$talla.sem32")).append("prom_sem32", new Document("$avg","$talla.sem32"))  
            .append("stdDev_sem33", new Document("$stdDevPop","$talla.sem33")).append("prom_sem33", new Document("$avg","$talla.sem33"))  
            .append("stdDev_sem34", new Document("$stdDevPop","$talla.sem34")).append("prom_sem34", new Document("$avg","$talla.sem34"))  
            .append("stdDev_sem35", new Document("$stdDevPop","$talla.sem35")).append("prom_sem35", new Document("$avg","$talla.sem35"))  
            .append("stdDev_sem36", new Document("$stdDevPop","$talla.sem36")).append("prom_sem36", new Document("$avg","$talla.sem36"))  
            .append("stdDev_sem37", new Document("$stdDevPop","$talla.sem37")).append("prom_sem37", new Document("$avg","$talla.sem37"))  
            .append("stdDev_sem38", new Document("$stdDevPop","$talla.sem38")).append("prom_sem38", new Document("$avg","$talla.sem38"))  
            .append("stdDev_sem39", new Document("$stdDevPop","$talla.sem39")).append("prom_sem39", new Document("$avg","$talla.sem39"))  
            .append("stdDev_sem40", new Document("$stdDevPop","$talla.sem40")).append("prom_sem40", new Document("$avg","$talla.sem40"))  
            .append("stdDev_mes3", new Document("$stdDevPop","$talla.mes3")).append("prom_mes3", new Document("$avg","$talla.mes3"))  
            .append("stdDev_mes6", new Document("$stdDevPop","$talla.mes6")).append("prom_mes6", new Document("$avg","$talla.mes6"))  
            .append("stdDev_mes9", new Document("$stdDevPop","$talla.mes9")).append("prom_mes9", new Document("$avg","$talla.mes9"))  
            .append("stdDev_mes12", new Document("$stdDevPop","$talla.mes12")).append("prom_mes12", new Document("$avg","$talla.mes12"))  
        ));
        
        docEstadisticas = mongoCollection.aggregate(Arrays.asList(group));
        
        desviacion = 0;
        promedio = 0;
       
        for (Document doc : docEstadisticas) {
           
            for(int i=0; i<listaSemanas.length; i++){
                desviacion = (double) doc.get("stdDev_"+listaSemanas[i]);
                promedio = (double) doc.get("prom_"+listaSemanas[i]);
                
                Bson filter = new Document("talla."+listaSemanas[i], new Document("$gt",4*desviacion+promedio)).append("fechaCreacion", new Document("$gte",fechaCreacion));
                Bson set = new Document("$set", new Document("talla.outlier",true));
                mongoCollection.updateMany(filter, set);
                
                filter = new Document("talla."+listaSemanas[i], new Document("$lt",-4*desviacion+promedio)).append("fechaCreacion", new Document("$gte",fechaCreacion));
                set = new Document("$set", new Document("talla.outlier",true));
                mongoCollection.updateMany(filter, set);
            }
  
        }
        
        //pc
        group = new Document(new Document("$group", new Document("_id","estadisticas")
            .append("stdDev_sem24", new Document("$stdDevPop","$pc.sem24")).append("prom_sem24", new Document("$avg","$pc.sem24"))  
            .append("stdDev_sem25", new Document("$stdDevPop","$pc.sem25")).append("prom_sem25", new Document("$avg","$pc.sem25"))  
            .append("stdDev_sem26", new Document("$stdDevPop","$pc.sem26")).append("prom_sem26", new Document("$avg","$pc.sem26"))  
            .append("stdDev_sem27", new Document("$stdDevPop","$pc.sem27")).append("prom_sem27", new Document("$avg","$pc.sem27"))  
            .append("stdDev_sem28", new Document("$stdDevPop","$pc.sem28")).append("prom_sem28", new Document("$avg","$pc.sem28"))  
            .append("stdDev_sem29", new Document("$stdDevPop","$pc.sem29")).append("prom_sem29", new Document("$avg","$pc.sem29"))  
            .append("stdDev_sem30", new Document("$stdDevPop","$pc.sem30")).append("prom_sem30", new Document("$avg","$pc.sem30"))  
            .append("stdDev_sem31", new Document("$stdDevPop","$pc.sem31")).append("prom_sem31", new Document("$avg","$pc.sem31"))  
            .append("stdDev_sem32", new Document("$stdDevPop","$pc.sem32")).append("prom_sem32", new Document("$avg","$pc.sem32"))  
            .append("stdDev_sem33", new Document("$stdDevPop","$pc.sem33")).append("prom_sem33", new Document("$avg","$pc.sem33"))  
            .append("stdDev_sem34", new Document("$stdDevPop","$pc.sem34")).append("prom_sem34", new Document("$avg","$pc.sem34"))  
            .append("stdDev_sem35", new Document("$stdDevPop","$pc.sem35")).append("prom_sem35", new Document("$avg","$pc.sem35"))  
            .append("stdDev_sem36", new Document("$stdDevPop","$pc.sem36")).append("prom_sem36", new Document("$avg","$pc.sem36"))  
            .append("stdDev_sem37", new Document("$stdDevPop","$pc.sem37")).append("prom_sem37", new Document("$avg","$pc.sem37"))  
            .append("stdDev_sem38", new Document("$stdDevPop","$pc.sem38")).append("prom_sem38", new Document("$avg","$pc.sem38"))  
            .append("stdDev_sem39", new Document("$stdDevPop","$pc.sem39")).append("prom_sem39", new Document("$avg","$pc.sem39"))  
            .append("stdDev_sem40", new Document("$stdDevPop","$pc.sem40")).append("prom_sem40", new Document("$avg","$pc.sem40"))  
            .append("stdDev_mes3", new Document("$stdDevPop","$pc.mes3")).append("prom_mes3", new Document("$avg","$pc.mes3"))  
            .append("stdDev_mes6", new Document("$stdDevPop","$pc.mes6")).append("prom_mes6", new Document("$avg","$pc.mes6"))  
            .append("stdDev_mes9", new Document("$stdDevPop","$pc.mes9")).append("prom_mes9", new Document("$avg","$pc.mes9"))  
            .append("stdDev_mes12", new Document("$stdDevPop","$pc.mes12")).append("prom_mes12", new Document("$avg","$pc.mes12"))  
        ));
        
        docEstadisticas = mongoCollection.aggregate(Arrays.asList(group));
        
        desviacion = 0;
        promedio = 0;
       
        for (Document doc : docEstadisticas) {
           
            for(int i=0; i<listaSemanas.length; i++){
                desviacion = (double) doc.get("stdDev_"+listaSemanas[i]);
                promedio = (double) doc.get("prom_"+listaSemanas[i]);
                
                Bson filter = new Document("pc."+listaSemanas[i], new Document("$gt",4*desviacion+promedio)).append("fechaCreacion", new Document("$gte",fechaCreacion));
                Bson set = new Document("$set", new Document("pc.outlier",true));
                mongoCollection.updateMany(filter, set);
                
                filter = new Document("pc."+listaSemanas[i], new Document("$lt",-4*desviacion+promedio)).append("fechaCreacion", new Document("$gte",fechaCreacion));
                set = new Document("$set", new Document("pc.outlier",true));
                mongoCollection.updateMany(filter, set);
            }
  
        }
    }
    
    public static String insertarDatos(File archivoCargar, JTextArea txtMensaje){
        conexionDB = new MongoClient(new MongoClientURI(dbServer)).getDatabase(dbName);
        MongoCollection mongoCollection = conexionDB.getCollection("medidas_crecimiento");
        
        BufferedReader br = null;
        String cadMensaje = "";
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(archivoCargar));
            List<String> listaVariablesSubir = new ArrayList<String>(); 
            List<Document> listaInsertar = new ArrayList<>();
            List<String> listaErroresCodigos = new ArrayList<>();
            int i = 0;
            Date fechaCreacion = new Date();
            
            while ((line = br.readLine()) != null) {
                
                String[] registro = line.split(cvsSplitBy);
           
                i++;
                if(i==1){
                    listaVariablesSubir = Arrays.asList(registro);
                    cadMensaje = validarVariables(listaVariablesSubir);
                   
                    if(!cadMensaje.equals("")){
                        return "- Los siguientes campos no existen:\n" + cadMensaje;
                    }
                    else
                        continue;
                }
             
                int codigo = Integer.parseInt(registro[0]);
                System.out.println("---> " + codigo);
                List<Document> lstCodigo = (List<Document>) mongoCollection.find(new Document("CODE", codigo)).into(new ArrayList<Document>());
                if(lstCodigo.size() > 0){
                    listaErroresCodigos.add(String.valueOf(codigo));
                    continue;
                }
                            
                Document insertDocument = new Document();
                txtMensaje.setText(txtMensaje.getText()+"-----> "+ codigo+"\n");
                
                Map<String, Document> listaNodos = new HashMap<String, Document>();
                listaNodos.put("peso",new Document());
                listaNodos.put("talla",new Document());
                listaNodos.put("pc",new Document());
                listaNodos.put("alimentacion",new Document());
                listaNodos.put("Nutpesowho",new Document());
                listaNodos.put("Nuttallawho",new Document());
                listaNodos.put("Nutpcwho",new Document());
                                
                Map<String, String> listaVariables = new HashMap<String, String>();
                
                for(int j = 0; j < registro.length; j++){
                    
                    if(registro[j].equals(""))
                        continue;
                    
                    Object valorVariable = convertirTipo(listaVariablesSubir.get(j),registro[j]); 
                    if(listaVariablesSubir.get(j).split("\\.").length > 1){
                        String nombreNodo = listaVariablesSubir.get(j).split("\\.")[0];
                        String nombreVariable =listaVariablesSubir.get(j).split("\\.")[1];
                        
                        listaNodos.get(nombreNodo).append(nombreVariable, valorVariable);
                    }
                    else{
                        insertDocument.append(listaVariablesSubir.get(j), valorVariable);
                    }
                    if(!valorVariable.toString().isEmpty())
                        listaVariables.put(listaVariablesSubir.get(j), valorVariable.toString());
                    
                    System.out.println("--> " + listaVariablesSubir.get(j) + " --> " + valorVariable);
                }
                
                listaNodos.get("peso").append("error", listaNodos.get("peso").size()<4 ? true : false);
                listaNodos.get("talla").append("error", listaNodos.get("talla").size()<4 ? true : false);
                listaNodos.get("pc").append("error", listaNodos.get("pc").size()<4 ? true : false);
                
                for (String key : listaNodos.keySet()) {
                    if(listaNodos.get(key).isEmpty())
                        continue;
                    insertDocument.append(key, listaNodos.get(key));
                }
                
                if(listaVariables.containsKey("edadgestacional") && listaVariables.containsKey("sexo")){
                    if(listaVariables.containsKey("pesoalnacer")){
                        listaNodos.get("peso").append("sem"+ listaVariables.get("edadgestacional"), Double.valueOf(listaVariables.get("pesoalnacer")));
                        double pnNormalizado = Normalizacion.normalizarPesoAlNacer(listaVariables.get("sexo"), Integer.parseInt(listaVariables.get("edadgestacional")), Double.valueOf(listaVariables.get("pesoalnacer")));
                        insertDocument.append("pesoalnacer_Normalizado", pnNormalizado);
                    }    
                    if(listaVariables.containsKey("tallaalnacer"))
                        listaNodos.get("talla").append("sem"+listaVariables.get("edadgestacional"), Double.valueOf(listaVariables.get("tallaalnacer")));
                    
                    if(listaVariables.containsKey("pcalnacer"))
                        listaNodos.get("pc").append("sem"+listaVariables.get("edadgestacional"), Double.valueOf(listaVariables.get("pcalnacer")));
                
                     Normalizacion.normalizarMedidasAtropometricas(insertDocument);
                }
                
                if(listaVariables.containsKey("edadgestacionalalaentrada") && listaVariables.containsKey("sexo")){
                    if(listaVariables.containsKey("pesoalaentrada")){
                        double pnNormalizado = Normalizacion.normalizarPesoAlNacer(listaVariables.get("sexo"), Integer.parseInt(listaVariables.get("edadgestacionalalaentrada")), Double.valueOf(listaVariables.get("pesoalaentrada")));
                        insertDocument.append("pesoalaentrada_Normalizado", pnNormalizado);
                    }  
                }

                
                insertDocument.append("fechaCreacion", fechaCreacion);
                listaInsertar.add(insertDocument);        
            }
            
            if(listaInsertar.size()>0){
                mongoCollection.insertMany(listaInsertar);
                calcularOutliers(fechaCreacion);
                cadMensaje = "- Se han guardado " + listaInsertar.size()+ " registros con éxito! \n\n"; 
            }
            
            if(listaErroresCodigos.size()>0){
                cadMensaje += "- Los siguientes códigos ya se encuentran registrados:\n";
                for(int j = 0; j < listaErroresCodigos.size(); j++){
                    cadMensaje += listaErroresCodigos.get(j)+"\n";
                }
            }
      
        } catch (Exception e) {
            e.printStackTrace();
            cadMensaje="ERROR: "+e.getMessage();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    cadMensaje=e.getMessage();
                }
            }
        }
        
        return cadMensaje;
    }
    
    public static String actualizarDatos(File archivoCargar){
        conexionDB = new MongoClient(new MongoClientURI(dbServer)).getDatabase(dbName);
        MongoCollection mongoCollection = conexionDB.getCollection("medidas_crecimiento");
        
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String cadMensaje = "";
        
        try {

            br = new BufferedReader(new FileReader(archivoCargar));
            List<String> listaVariablesSubir = new ArrayList<>(); 
            List<String> listaErroresCodigos = new ArrayList<>();
            
            int i = 0;
            int cont = 0;
            while ((line = br.readLine()) != null) {
                
                String[] registro = line.split(cvsSplitBy);
           
                i++;
                if(i==1){
                    listaVariablesSubir = Arrays.asList(registro);
                    cadMensaje = validarVariables(listaVariablesSubir);
                   
                    if(!cadMensaje.equals("")){
                        return "- Los siguientes campos no existen:\n" + cadMensaje;
                    }
                    else
                        continue;
                }
             
                int codigo = Integer.parseInt(registro[0]);
                List<Document> lstCodigo = (List<Document>) mongoCollection.find(new Document("CODE", codigo)).into(new ArrayList<Document>());
                if(lstCodigo.isEmpty()){
                    listaErroresCodigos.add(String.valueOf(codigo));
                    continue;
                }
                
                Bson updateDocument = new Document();
                Document docAux = new Document();
                System.out.println("++++++++ codigo" + codigo);
                for(int j = 1; j < registro.length; j++){ //no toma en cuenta el CODE  
                    if(registro[j].equals(""))
                        continue;
                    Object valorVariable = convertirTipo(listaVariablesSubir.get(j),registro[j]); 
                    docAux.append(listaVariablesSubir.get(j), valorVariable);      
                }
                if(!docAux.isEmpty()){
                    updateDocument = new Document("$set", docAux);

                    Bson filtroCod = new Document("CODE",codigo);
                    mongoCollection.updateOne(filtroCod, updateDocument);
                    cont++;
                }
            }
            
            cadMensaje = "- Se han actualizado " + cont + " registros con éxito! \n\n"; 
            
            if(listaErroresCodigos.size()>0){
                cadMensaje += "- Los siguientes códigos no se encuentran registrados:\n";
                for(int j = 0; j < listaErroresCodigos.size(); j++){
                    cadMensaje += listaErroresCodigos.get(j)+"\n";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            cadMensaje=e.getMessage();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    cadMensaje=e.getMessage();
                }
            }
        }
        
        return cadMensaje;
    }

    public static String verVariables(){
        String cadMensaje = "";
        
        for (String key : mapVariablesBD.keySet()) {
            cadMensaje += key + "\n";
        }
        
        return cadMensaje;
    }

    private static Object convertirTipo(String nombreVariable,String valorVariable) {
        TipoDato td = mapVariablesBD.get(nombreVariable);
        Object valorConvertido = null;
        
        if(valorVariable.trim().isEmpty())
            return "";
        
        switch (td) {
            case DOUBLE: 
                    valorConvertido = Double.valueOf(valorVariable);
                    break;
            case INTEGER: 
                    valorConvertido = Integer.valueOf(valorVariable);
                    break;
            case BOOLEAN: 
                    valorConvertido = Boolean.valueOf(valorVariable);
                    break;
            case STRING: 
                    valorConvertido = valorVariable;
                    break;        
            case INTEGER_STRING:
                    double auxDouble = Double.parseDouble(valorVariable);
                    valorConvertido =  (int)auxDouble;
                    break;
        }
        return valorConvertido;
    }


    
}
