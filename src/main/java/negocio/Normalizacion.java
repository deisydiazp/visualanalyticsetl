/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author danie
 */
public class Normalizacion {

    private static int i = 0;
    //MLS
    private static double[][] infoPesoNiño = new double[][]{//MLS
        {0.695063204132922,     0.989540186915888,  0.170019033650001}, //24
        {0.79,                  1.1285046728972,    0.184003706050782}, //25
        {0.895257972097185,     1.24518878504673,   0.195641654443751}, //26
        {1.01362660417056,      1.33513644859813,   0.204463534027344}, //27
        {1.14836625019534,      1.39389158878505,   0.21},              //28
        {1.30273726414675,      1.41699813084112,   0.211864293571875}, //29
        {1.48,                  1.4,                0.21},              //30
        {1.6817070228522,       1.34118130841121,   0.204587964798002}, //31
        {1.90257974228806,      1.24978691588785,   0.196427730459004}, //32
        {2.13563177901417,      1.13780186915888,   0.186473513721005}, //33
        {2.37387675373713,      1.01721121495327,   0.175679531322004}, //34
        {2.61032828716354,      0.9,                0.165},             //35
        {2.838,                 0.795880373831776,  0.155255402929992}, //36
        {3.05230924639719,      0.705472897196262,  0.146731289034983}, //37
        {3.25828831428217,      0.62712523364486,   0.139579473674979}, //38
        {3.46337322502606,      0.559185046728972,  0.133951772209983}, //39
        {3.675,                 0.5,                0.13},              //40
        {4.4709,                0.2297,             0.1340}, //mes 1 - semanas 41, 42, 43, 44
        {5.5675,                0.1970,             0.1239}, //mes 2 - semanas 45, 46, 47, 48 
        {6.3762,                0.1738,             0.1173}, //mes 3 - semanas 49, 50, 51, 52
        {7.0023,                0.1553,             0.1132}, //mes 4 - semanas 53, 54, 55, 56
        {7.5105,                0.1395,             0.1108}, //mes 5 - semanas 57, 58, 59, 60
        {7.9340,                0.1257,             0.1096}, //mes 6 - semanas 61, 62, 63, 64
        {8.2970,                0.1134,             0.1090}, //mes 7 - semanas 65, 66, 67, 68
        {8.6151,                0.1021,             0.1088}, //mes 8 - semanas 69, 70, 71, 72
        {8.9014,                0.0917,             0.1088}, //mes 9 - semanas 73, 74, 75, 76
        {9.1649,                0.0820,             0.1089}, //mes 10 - semanas 77, 78, 79, 80
        {9.4122,                0.0730,             0.1091}, //mes 11 - semanas 81, 82, 83, 84
        {9.6479,                0.0644,             0.1093}, //mes 12 - semanas 85, 86, 87, 88
        {9.8749,                0.0563,             0.1095}, //mes 13 - semanas 89, 90, 91, 92
        {10.0953,               0.0487,             0.1098}, //mes 14 - semanas 93, 94, 95, 96
        {10.3108,               0.0413,             0.1101}, //mes 15 - semanas 97, 98, 99, 100
        {10.5228,               0.0343,             0.1104}, //mes 16 - semanas 101, 102, 103, 104
        {10.7319,               0.0275,             0.1108}, //mes 17 - semanas 105, 106, 107, 108
        {10.9385,               0.0211,             0.1112}, //mes 18 - semanas 109, 110, 111, 112
        {11.1430,               0.0148,             0.1116}, //mes 19 - semanas 113, 114, 115, 116
        {11.3462,               0.0087,             0.1121}, //mes 20 - semanas 117, 118, 119, 120
        {11.5486,               0.0029,             0.1126}, //mes 21 - semanas 121, 122, 123, 124
        {11.7504,               -0.0028,            0.1131}, //mes 22 - semanas 125, 126, 127, 128
        {11.9514,               -0.0083,            0.1137}, //mes 23 - semanas 129, 130, 131, 131
        {12.1515,               -0.0137,            0.1143}, //mes 24 - semanas 132, 134, 135, 136
       
          
    };

    private static double[][] infoTallaNiño = new double[][]{
        {31.6218387692308,      1,                  0.0647404307692308},
        {33.01,                 1,                  0.066},
        {34.3879753846154,      1,                  0.0665206153846154},
        {35.7601907692308,      1,                  0.0663692307692308},
        {37.1324184615385,      1,                  0.0656575384615385},
        {38.5104307692308,      1,                  0.0644972307692308},
        {39.9, 1, 0.063},
        {41.3019126153846, 1, 0.0612665846153846},
        {42.6970116923077, 1, 0.0593539076923077},
        {44.0611544615385, 1, 0.0573079384615385},
        {45.3701981538462, 1, 0.0551746461538462},
        {46.6, 1, 0.053},
        {47.7335741538462, 1, 0.0508290461538462},
        {48.7825624615385, 1, 0.0487031384615385},
        {49.7657636923077, 1, 0.0466627076923077},
        {50.7019766153846, 1, 0.0447481846153846},
        {51.61, 1, 0.043},
        {54.7244,1,0.03557}, //41
        {58.4249,1,0.03424}, //42
        {61.4292,1,0.03328}, //43
        {63.886,1,0.03257}, //44
        {65.9026,1,0.03204}, //45
        {67.6236,1,0.03165}, //46
        {69.1645,1,0.03139}, //47
        {70.5994,1,0.03124}, //48
        {71.9687,1,0.03117}, //49
        {73.2812,1,0.03118}, //50
        {74.5388,1,0.03125}, //51
        {75.7488,1,0.03137}, //52
        {76.9186,1,0.03154}, //53
        {78.0497,1,0.03174}, //54
        {79.1458,1,0.03197}, //55
        {80.2113,1,0.03222}, //56
        {81.2487,1,0.0325}, //57
        {82.2587,1,0.03279}, //58
        {83.2418,1,0.0331}, //59
        {84.1996,1,0.03342}, //60
        {85.1348,1,0.03376}, //61
        {86.0477,1,0.0341}, //62
        {86.941,1,0.03445}, //63
        {87.8161,1,0.03479}, //64    
    };

    private static double[][] infoPCNiño = new double[][]{
        {22.2375272727273, 1, 0.0572032},
        {23.2142045454545, 1, 0.05645},
        {24.1857454545455, 1, 0.0556608},
        {25.1511227272727, 1, 0.0548284},
        {26.1093090909091, 1, 0.0539456},
        {27.0592772727273, 1, 0.0530052},
        {28, 1, 0.052},
        {28.9286181818182, 1, 0.050926},
        {29.8349454545455, 1, 0.049792},
        {30.7069636363636, 1, 0.04861},
        {31.5326545454545, 1, 0.047392},
        {32.3, 1, 0.04615},
        {33.0006545454545, 1, 0.044896},
        {33.6409636363636, 1, 0.043642},
        {34.2309454545454, 1, 0.0424},
        {34.7806181818182, 1, 0.041182},
        {35.3, 1, 0.04},
        {37.2759,1,0.03133}, //41
        {39.1285,1,0.02997}, //42
        {40.5135,1,0.02918}, //43
        {41.6317,1,0.02868}, //44
        {42.5576,1,0.02837}, //45
        {43.3306,1,0.02817}, //46
        {43.9803,1,0.02804}, //47
        {44.53,1,0.02796}, //48
        {44.9998,1,0.02792}, //49
        {45.4051,1,0.0279}, //50
        {45.7573,1,0.02789}, //51
        {46.0661,1,0.02789}, //52
        {46.3395,1,0.02789}, //53
        {46.5844,1,0.02791}, //54
        {46.806,1,0.02792}, //55
        {47.0088,1,0.02795}, //56
        {47.1962,1,0.02797}, //57
        {47.3711,1,0.028}, //58
        {47.5357,1,0.02803}, //59
        {47.6919,1,0.02806}, //60
        {47.8408,1,0.0281}, //61
        {47.9833,1,0.02813}, //62
        {48.1201,1,0.02817}, //63
        {48.2515,1,0.02821}, //64
    };

    private static double[][] infoPesoNiña = new double[][]{
        {0.648618774611161, 0.685757949599865, 0.170863497669411},
        {0.742, 0.95, 0.19},
        {0.843889929119857, 1.13104601353441, 0.206473126712096},
        {0.956156195499164, 1.23381002903851, 0.21914751397976},
        {1.08237749731854, 1.26898297828677, 0.227585337891377},
        {1.22613253275862, 1.24725579305368, 0.231348774535329},
        {1.391, 1.17931940511371, 0.23},
        {1.57915637122693, 1.07586474624133, 0.223451197391954},
        {1.7871692146381, 0.947582748211019, 0.213014577890471},
        {2.0102038724358, 0.805164342797256, 0.200352359693011},
        {2.24342568682233, 0.659300461774514, 0.187126760997034},
        {2.482, 0.52068203691727, 0.175},
        {2.71980412067619, 0.4, 0.165282083720088},
        {2.94556322357865, 0.30554808778432, 0.157874174458355},
        {3.14734691523645, 0.236031256980414, 0.152325223336578},
        {3.32777150399179, 0.187757269285603, 0.148184181476534},
        {3.504, 0.157033886397212, 0.145},
        {4.1873,0.1714,0.13724}, //41
        {5.1282,0.0962,0.13}, //42
        {5.8458,0.0402,0.12619}, //43
        {6.4237,-0.005,0.12402}, //44
        {6.8985,-0.043,0.12274}, //45
        {7.297,-0.0756,0.12204}, //46
        {7.6422,-0.1039,0.12178}, //47
        {7.9487,-0.1288,0.12181}, //48
        {8.2254,-0.1507,0.12199}, //49
        {8.48,-0.17,0.12223}, //50
        {8.7192,-0.1872,0.12247}, //51
        {8.9481,-0.2024,0.12268}, //52
        {9.1699,-0.2158,0.12283}, //53
        {9.387,-0.2278,0.12294}, //54
        {9.6008,-0.2384,0.12299}, //55
        {9.8124,-0.2478,0.12303}, //56
        {10.0226,-0.2562,0.12306}, //57
        {10.2315,-0.2637,0.12309}, //58
        {10.4393,-0.2703,0.12315}, //59
        {10.6464,-0.2762,0.12323}, //60
        {10.8534,-0.2815,0.12335}, //61
        {11.0608,-0.2862,0.1235}, //62
        {11.2688,-0.2903,0.12369}, //63
        {11.4775,-0.2941,0.1239} //64  
    };

    private static double[][] infoTallaNiña = new double[][]{
        {30.9325963636364, 1, 0.062648},
        {32.2791477272727, 1, 0.064125},
        {33.6329672727273, 1, 0.065312},
        {34.9955086363636, 1, 0.066151},
        {36.3682254545455, 1, 0.066584},
        {37.7525713636364, 1, 0.066553},
        {39.15, 1, 0.066},
        {40.5579309090909, 1, 0.064892},
        {41.9576472727273, 1, 0.063296},
        {43.3263981818182, 1, 0.061304},
        {44.6414327272727, 1, 0.059008},
        {45.88, 1, 0.0565},
        {47.0252727272727, 1, 0.053872},
        {48.0841181818182, 1, 0.051216},
        {49.0693272727273, 1, 0.048624},
        {49.9936909090909, 1, 0.046188},
        {50.87, 1, 0.044},
        {53.6872,1,0.0364}, //41
        {57.0673,1,0.03568}, //42
        {59.8029,1,0.0352}, //43
        {62.0899,1,0.03486}, //44
        {64.0301,1,0.03463}, //45
        {65.7311,1,0.03448}, //46
        {67.2873,1,0.03441}, //47
        {68.7498,1,0.0344}, //48
        {70.1435,1,0.03444}, //49
        {71.4818,1,0.03452}, //50
        {72.771,1,0.03464}, //51
        {74.015,1,0.03479}, //52
        {75.2176,1,0.03496}, //53
        {76.3817,1,0.03514}, //54
        {77.5099,1,0.03534}, //55
        {78.6055,1,0.03555}, //56
        {79.671,1,0.03576}, //57
        {80.7079,1,0.03598}, //58
        {81.7182,1,0.0362}, //59
        {82.7036,1,0.03643}, //60
        {83.6654,1,0.03666}, //61
        {84.604,1,0.03688}, //62
        {85.5202,1,0.03711}, //63
        {86.4153,1,0.03734}, //64
    };

    private static double[][] infoPCNiña = new double[][]{
        {21.8051418181818, 1, 0.0564424629807678},
        {22.7344886363636, 1, 0.0561634631481783},
        {23.6658763636364, 1, 0.0557913862637346},
        {24.5997131818182, 1, 0.0553076169170657},
        {25.5364072727273, 1, 0.0546935396978009},
        {26.4763668181818, 1, 0.0539305391955693},
        {27.42, 1, 0.053},
        {28.3650145454545, 1, 0.051891922111093},
        {29.2983163636364, 1, 0.0506307671703318},
        {30.2041109090909, 1, 0.0492496122295705},
        {31.0666036363636, 1, 0.0477815343406635},
        {31.87, 1, 0.0462596105554651},
        {32.6025236363636, 1, 0.0447169179258294},
        {33.2684709090909, 1, 0.0431865335036108},
        {33.8761563636364, 1, 0.0417015343406635},
        {34.4338945454545, 1, 0.0402949974888418},
        {34.95, 1, 0.039},
        {36.5463,1,0.0321}, //41
        {38.2521,1,0.03168}, //42
        {39.5328,1,0.0314}, //43
        {40.5817,1,0.03119}, //44
        {41.459,1,0.03102}, //45
        {42.1995,1,0.03087}, //46
        {42.829,1,0.03075}, //47
        {43.3671,1,0.03063}, //48
        {43.83,1,0.03053}, //49
        {44.2319,1,0.03044}, //50
        {44.5844,1,0.03035}, //51
        {44.8965,1,0.03027}, //52
        {45.1752,1,0.03019}, //53
        {45.4265,1,0.03012}, //54
        {45.6551,1,0.03006}, //55
        {45.865,1,0.02999}, //56
        {46.0598,1,0.02993}, //57
        {46.2424,1,0.02987}, //58
        {46.4152,1,0.02982}, //59
        {46.5801,1,0.02977}, //60
        {46.7384,1,0.02972}, //61
        {46.8913,1,0.02967}, //62
        {47.0391,1,0.02962}, //63
        {47.1822,1,0.02957}, //64
    };

    private static double[][] infoPesoNiñoMes = {//MLS
        {6.3762,    0.1738, 0.11727},   //mes
        {7.934,     0.1257, 0.10958},   //mes6
        {8.9014,    0.0917, 0.10881},   //mes9
        {9.6479,    0.0644, 0.10925}    //mes12
    };

    private static double[][] infoTallaNiñoMes = {
        {61.4292, 1, 0.03328},
        {67.6236, 1, 0.03165},
        {71.9687, 1, 0.03117},
        {75.7488, 1, 0.03137}
    };

    private static double[][] infoPCNiñoMes = {
        {40.5135, 1, 0.02918},
        {43.3306, 1, 0.02817},
        {44.9998, 1, 0.02792},
        {46.0661, 1, 0.02789}
    };

    private static double[][] infoPesoNiñaMes = {
        {5.8458, 0.0402, 0.12619},
        {7.297, -0.0756, 0.12204},
        {8.2254, -0.1507, 0.12199},
        {8.9481, -0.2024, 0.12268}
    };

    private static double[][] infoTallaNiñaMes = {
        {59.8029, 1, 0.0352},
        {65.7311, 1, 0.03448},
        {70.1435, 1, 0.03444},
        {74.015, 1, 0.03479}
    };

    private static double[][] infoPCNiñaMes = {
        {39.5328, 1, 0.0314},
        {42.1995, 1, 0.03087},
        {43.83, 1, 0.03053},
        {44.8965, 1, 0.03027}
    };

    public static void main(String[] args) {
        //normalizarMedidasAtropometricas();
        //normalizarPesoAlaEntrada();
        //normalizarPesoAlNacer();
    }

    public static double normalizarPesoAlNacer(String sexo, int edadgestacional, double pesoalnacer){
   
        Double z = 0.0;

        if(edadgestacional < 41){
            int sem=edadgestacional;
            Double x = pesoalnacer;
       
            if (sexo.compareTo("2") == 0) {
                //peso
                if (x != null) {
                    z = (Math.pow(x/1000 / infoPesoNiña[sem - 24][0], infoPesoNiña[sem - 24][1]) - 1) / (infoPesoNiña[sem - 24][1] * infoPesoNiña[sem - 24][2]);
                }
            } else if (sexo.compareTo("1") == 0) {
                //peso
                if (x != null) {
                    z = (Math.pow(x/1000 / infoPesoNiño[sem - 24][0], infoPesoNiño[sem - 24][1]) - 1) / (infoPesoNiño[sem - 24][1] * infoPesoNiño[sem - 24][2]);
                }
            }
        }

        if(edadgestacional > 40){
            int sem = edadgestacional-41;
            Double x = pesoalnacer;
       
            int grupo = sem/4;
            
            if (sexo.compareTo("2") == 0) {
                //peso
                if (x != null) {
                    z = (Math.pow(x/1000 / infoPesoNiña[17 + grupo][0], infoPesoNiña[17 + grupo][1]) - 1) / (infoPesoNiña[17 + grupo][1] * infoPesoNiña[17 + grupo][2]);
                }
            } else if (sexo.compareTo("1") == 0) {
                //peso
                if (x != null) {
                    z = (Math.pow(x/1000 / infoPesoNiño[17 + grupo][0], infoPesoNiño[17 + grupo][1]) - 1) / (infoPesoNiño[17 + grupo][1] * infoPesoNiño[17 + grupo][2]);
                }
            }
        }
        return z;
    }
    
    
    public static void normalizarMedidasAtropometricas(Document docData){
       
            String sexo = docData.get("sexo").toString();
            Document docPeso = new Document();
            Document docTalla = new Document();
            Document docPc = new Document();
            for (int sem = 24; sem <= 40; sem++) {
                Double z = null;

                if (sexo.compareTo("2") == 0) {
                    //peso

                    Double x = (((Document) docData.get("peso")).get("sem" + sem) != null ? Double.parseDouble(((Document) docData.get("peso")).get("sem" + sem).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x/1000 / infoPesoNiña[sem - 24][0], infoPesoNiña[sem - 24][1]) - 1) / (infoPesoNiña[sem - 24][1] * infoPesoNiña[sem - 24][2]);
                        docPeso.append("sem" + sem, z);
                    }
                    //talla
                    x = (((Document) docData.get("talla")).get("sem" + sem) != null ? Double.parseDouble(((Document) docData.get("talla")).get("sem" + sem).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x / infoTallaNiña[sem - 24][0], infoTallaNiña[sem - 24][1]) - 1) / (infoTallaNiña[sem - 24][1] * infoTallaNiña[sem - 24][2]);
                        docTalla.append("sem" + sem, z);
                    }
                    //pc
                    x = (((Document) docData.get("pc")).get("sem" + sem) != null ? Double.parseDouble(((Document) docData.get("pc")).get("sem" + sem).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x / infoPCNiña[sem - 24][0], infoPCNiña[sem - 24][1]) - 1) / (infoPCNiña[sem - 24][1] * infoPCNiña[sem - 24][2]);
                        docPc.append("sem" + sem, z);
                    }
                } else if (sexo.compareTo("1") == 0) {
                    //peso
                    Double x = (((Document) docData.get("peso")).get("sem" + sem) != null ? Double.parseDouble(((Document) docData.get("peso")).get("sem" + sem).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x/1000 / infoPesoNiño[sem - 24][0], infoPesoNiño[sem - 24][1]) - 1) / (infoPesoNiño[sem - 24][1] * infoPesoNiño[sem - 24][2]);
                        docPeso.append("sem" + sem, z);
                    }
                    //talla
                    x = (((Document) docData.get("talla")).get("sem" + sem) != null ? Double.parseDouble(((Document) docData.get("talla")).get("sem" + sem).toString()) : null);
                    //docData.append("talla_normalizado.sem" + sem, new Document());
                    if (x != null) {
                        z = (Math.pow(x / infoTallaNiño[sem - 24][0], infoTallaNiño[sem - 24][1]) - 1) / (infoTallaNiño[sem - 24][1] * infoTallaNiño[sem - 24][2]);
                        docTalla.append("sem" + sem, z);
                    }
                    //pc
                    x = (((Document) docData.get("pc")).get("sem" + sem) != null ? Double.parseDouble(((Document) docData.get("pc")).get("sem" + sem).toString()) : null);
                    //docData.append("pc_normalizado.sem" + sem, new Document());
                    if (x != null) {
                        z = (Math.pow(x / infoPCNiño[sem - 24][0], infoPCNiño[sem - 24][1]) - 1) / (infoPCNiño[sem - 24][1] * infoPCNiño[sem - 24][2]);
                        docPc.append("sem" + sem, z);
                    }
                }
            }
        
            for (int mes = 1; mes <= 4; mes++) {
                Double z = null;
                int mes1=mes*3;
                if (sexo.compareTo("2") == 0) {
                    //peso

                    Double x = (((Document) docData.get("peso")).get("mes" + mes1) != null ? Double.parseDouble(((Document) docData.get("peso")).get("mes" + mes1).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x/1000 / infoPesoNiñaMes[mes-1][0], infoPesoNiñaMes[mes-1][1]) - 1) / (infoPesoNiñaMes[mes-1][1] * infoPesoNiñaMes[mes-1][2]);
                        docPeso.append("mes" + mes1, z);
                    }
                    //talla
                    x = (((Document) docData.get("talla")).get("mes" + mes1) != null ? Double.parseDouble(((Document) docData.get("talla")).get("mes" + mes1).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x / infoTallaNiñaMes[mes - 1][0], infoTallaNiñaMes[mes - 1][1]) - 1) / (infoTallaNiñaMes[mes - 1][1] * infoTallaNiñaMes[mes - 1][2]);
                        docTalla.append("mes" + mes1, z);
                    }
                    //pc
                    x = (((Document) docData.get("pc")).get("mes" + mes1) != null ? Double.parseDouble(((Document) docData.get("pc")).get("mes" + mes1).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x / infoPCNiñaMes[mes - 1][0], infoPCNiñaMes[mes - 1][1]) - 1) / (infoPCNiñaMes[mes - 1][1] * infoPCNiñaMes[mes - 1][2]);
                        docPc.append("mes" + mes1, z);
                    }
                } else if (sexo.compareTo("1") == 0) {
                    //peso
                    Double x = (((Document) docData.get("peso")).get("mes" + mes1) != null ? Double.parseDouble(((Document) docData.get("peso")).get("mes" + mes1).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x/1000 / infoPesoNiñoMes[mes - 1][0], infoPesoNiñoMes[mes - 1][1]) - 1) / (infoPesoNiñoMes[mes - 1][1] * infoPesoNiñoMes[mes - 1][2]);
                        docPeso.append("mes" + mes1, z);
                    }
                    //talla
                    x = (((Document) docData.get("talla")).get("mes" + mes1) != null ? Double.parseDouble(((Document) docData.get("talla")).get("mes" + mes1).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x / infoTallaNiñoMes[mes - 1][0], infoTallaNiñoMes[mes - 1][1]) - 1) / (infoTallaNiñoMes[mes - 1][1] * infoTallaNiñoMes[mes - 1][2]);
                        docTalla.append("mes" + mes1, z);
                    }
                    //pc
                    x = (((Document) docData.get("pc")).get("mes" + mes1) != null ? Double.parseDouble(((Document) docData.get("pc")).get("mes" + mes1).toString()) : null);
                    if (x != null) {
                        z = (Math.pow(x / infoPCNiñoMes[mes - 1][0], infoPCNiñoMes[mes - 1][1]) - 1) / (infoPCNiñoMes[mes - 1][1] * infoPCNiñoMes[mes - 1][2]);
                        docPc.append("mes" + mes1, z);
                    }
                }
            }
      
            if(!docPeso.isEmpty()){
                docData.append("peso_normalizado",docPeso);
            }
            
            if(!docTalla.isEmpty()){
                docData.append("talla_normalizado",docTalla);
            }
            
            if(!docPc.isEmpty()){
                docData.append("pc_normalizado",docPc);
            }

    }
    
    
    
}
