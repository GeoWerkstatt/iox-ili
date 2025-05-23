package ch.interlis.iox_j.validator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ch.ehi.basics.settings.Settings;
import ch.interlis.ili2c.config.Configuration;
import ch.interlis.ili2c.config.FileEntry;
import ch.interlis.ili2c.config.FileEntryKind;
import ch.interlis.ili2c.metamodel.TransferDescription;
import ch.interlis.iom.IomObject;
import ch.interlis.iom_j.Iom_jObject;
import ch.interlis.iox_j.EndBasketEvent;
import ch.interlis.iox_j.EndTransferEvent;
import ch.interlis.iox_j.ObjectEvent;
import ch.interlis.iox_j.PipelinePool;
import ch.interlis.iox_j.StartBasketEvent;
import ch.interlis.iox_j.StartTransferEvent;
import ch.interlis.iox_j.logging.LogEventFactory;

public class Function23Test {
	private TransferDescription td=null;
	// OID
	private final static String OBJ_OID1 ="o1";
	private final static String OBJ_OID2 ="o2";
	private final static String OBJ_OID3 ="o3";
	private final static String OBJ_OID4 ="o4";
	// MODEL
	private final static String ILI_TOPIC="Function23.Topic";
	// CLASS
	private final static String ILI_CLASSA1=ILI_TOPIC+".ClassA1";
	private final static String ILI_CLASSA2=ILI_TOPIC+".ClassA2";
	private final static String ILI_CLASSA3=ILI_TOPIC+".ClassA3";
	private final static String ILI_CLASSB1=ILI_TOPIC+".ClassB1";
	private final static String ILI_CLASSB2=ILI_TOPIC+".ClassB2";
	private final static String ILI_CLASSB3=ILI_TOPIC+".ClassB3";
	private final static String ILI_CLASSC1=ILI_TOPIC+".ClassC1";
	private final static String ILI_CLASSC2=ILI_TOPIC+".ClassC2";
	private final static String ILI_CLASSC3=ILI_TOPIC+".ClassC3";
	private final static String ILI_CLASSD1=ILI_TOPIC+".ClassD1";
	private final static String ILI_CLASSD2=ILI_TOPIC+".ClassD2";
	private final static String ILI_CLASSD3=ILI_TOPIC+".ClassD3";
	private final static String ILI_CLASSG=ILI_TOPIC+".ClassG";
	private final static String ILI_CLASSH=ILI_TOPIC+".ClassH";
	private final static String ILI_CLASSI=ILI_TOPIC+".ClassI";
	private final static String ILI_CLASSJ=ILI_TOPIC+".ClassJ";
	private final static String ILI_CLASSK=ILI_TOPIC+".ClassK";
	private final static String ILI_CLASSL=ILI_TOPIC+".ClassL";
	private final static String ILI_STRUCTM=ILI_TOPIC+".StructM";
	private final static String ILI_CLASSN=ILI_TOPIC+".ClassN";
	private final static String ILI_CLASSO=ILI_TOPIC+".ClassO";
	private final static String ILI_CLASSP=ILI_TOPIC+".ClassP";
	private final static String ILI_CLASSQ=ILI_TOPIC+".ClassQ";
	private final static String ILI_CLASSR=ILI_TOPIC+".ClassR";
	private final static String ILI_CLASSS=ILI_TOPIC+".ClassS";
	private final static String ILI_CLASST=ILI_TOPIC+".ClassT";
	private final static String ILI_CLASSSA=ILI_TOPIC+".ClassSA";
	private final static String ILI_CLASSTA=ILI_TOPIC+".ClassTA";
    private final static String ILI_CLASSSB=ILI_TOPIC+".ClassSB";
    private final static String ILI_CLASSTB=ILI_TOPIC+".ClassTB";
	private final static String ILI_CLASSU=ILI_TOPIC+".ClassU";
	private final static String ILI_CLASSUA=ILI_TOPIC+".ClassUA";
	private final static String ILI_CLASSV=ILI_TOPIC+".ClassV";
	private final static String ILI_CLASSW=ILI_TOPIC+".ClassW";
	private final static String ILI_CLASSWA=ILI_TOPIC+".ClassWA";
	private final static String ILI_CLASSWB=ILI_TOPIC+".ClassWB";
	private final static String ILI_CLASSX=ILI_TOPIC+".ClassX";
	private final static String ILI_CLASSY=ILI_TOPIC+".ClassY";
	private final static String ILI_CLASSZA=ILI_TOPIC+".ClassZA";
	private final static String ILI_CLASSZB=ILI_TOPIC+".ClassZB";
	private final static String ILI_CLASSZD=ILI_TOPIC+".ClassZD";
	private final static String ILI_CLASSZE=ILI_TOPIC+".ClassZE";
	private final static String ILI_CLASSZF=ILI_TOPIC+".ClassZF";
	private final static String ILI_CLASSZG=ILI_TOPIC+".ClassZG";
	private final static String ILI_CLASSZH=ILI_TOPIC+".ClassZH";
	private final static String ILI_CLASSZI=ILI_TOPIC+".ClassZI";
	private final static String ILI_CLASSZK=ILI_TOPIC+".ClassZK";
	private final static String ILI_CLASSZZ=ILI_TOPIC+".ClassZZ";
	private final static String ILI_CLASSXA=ILI_TOPIC+".ClassXA";
	private final static String ILI_CLASSXB=ILI_TOPIC+".ClassXB";
	// STRUCTURE
	private final static String ILI_STRUCTA=ILI_TOPIC+".StructA";
	private final static String ILI_STRUCTB=ILI_TOPIC+".StructB";
	private final static String ILI_STRUCTF=ILI_TOPIC+".StructF";
	private final static String ILI_STRUCTAP=ILI_TOPIC+".StructAp";
	private final static String ILI_STRUCTBP=ILI_TOPIC+".StructBp";
    private final static String ILI_STRUCTBPP=ILI_TOPIC+".StructBpp";
	// ASSOCIATION
	private final static String ILI_ASSOC_QR1_Q1="q1";
	private final static String ILI_ASSOC_QR1_R1="r1";
	private final static String ILI_ASSOC_ST1_S1="s1";
	private final static String ILI_ASSOC_ST1_T1="t1";
	private final static String ILI_ASSOC_SATA1_S2="s2";
	private final static String ILI_ASSOC_SATA1_T2="t2";
    private final static String ILI_ASSOC_ST3_S3="s3";
    private final static String ILI_ASSOC_ST3_T3="t3";
	// ASSOCIATION CLASS
	private final static String ILI_ASSOC_QR1=ILI_TOPIC+".QR1";
	private final static String ILI_ASSOC_ST1=ILI_TOPIC+".ST1";
	// ATTR
	private final static String OBJ_ATTR1 ="attr1";
	private final static String OBJ_ATTR2 ="attr2";
	private final static String OBJ_ATTR3 ="attr3";
	private final static String OBJ_ATTR4 ="attr4";
	private final static String OBJ_ATTR5 ="attr5";
	private final static String OBJ_ATTR6 ="attr6";
	// START BASKET EVENT
	private final static String BID1="b1";
	private final static String BID2="b2";
    private static final String ILI_ISOFCLASS_TOPIC = "Function23.IsOfClassTest";
    private static final String ILI_ISOFCLASS_STRUCTA = ILI_ISOFCLASS_TOPIC+".StructA";
    private static final String ILI_ISOFCLASS_STRUCTAP = ILI_ISOFCLASS_TOPIC+".StructAp";
    private static final String ILI_ISOFCLASS_STRUCTB = ILI_ISOFCLASS_TOPIC+".StructB";
    private static final String ILI_ISOFCLASS_STRUCTBP = ILI_ISOFCLASS_TOPIC+".StructBp";
    private static final String ILI_ISOFCLASS_STRUCTBPP = ILI_ISOFCLASS_TOPIC+".StructBpp";
    private static final String ILI_ISOFCLASS_CLASSV = ILI_ISOFCLASS_TOPIC+".ClassV";
    private static final String ILI_ISOFCLASS_CLASSU = ILI_ISOFCLASS_TOPIC+".ClassU";
    private static final String ILI_ISOFCLASS_CLASSUA = ILI_ISOFCLASS_TOPIC+".ClassUA";
    private static final String ILI_ISOFCLASS_CLASSA = ILI_ISOFCLASS_TOPIC+".ClassA";
    private static final String ILI_ISOFCLASS_CLASSB = ILI_ISOFCLASS_TOPIC+".ClassB";
    private static final String ILI_ISOFCLASS_CLASSBp = ILI_ISOFCLASS_TOPIC+".ClassBp";
	
	@Before
	public void setUp() throws Exception {
		// ili-datei lesen
		Configuration ili2cConfig=new Configuration();
		FileEntry fileEntry=new FileEntry("src/test/data/validator/Function23.ili", FileEntryKind.ILIMODELFILE);
		ili2cConfig.addFileEntry(fileEntry);
		td=ch.interlis.ili2c.Ili2c.runCompiler(ili2cConfig);
		assertNotNull(td);
	}

	//#########################################################//
	//######## SUCCESS FUNCTIONS ##############################//
	//#########################################################//
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: len, die Laenge stimmt. 
	@Test
	public void len_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSA1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdefghij");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: len die laenge der Konstanten mit der Laenge des Textes uebereinstimmt.
	@Test
	public void lenWithText_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSA2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "1");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenTrim die Laenge des Textes stimmt.
	@Test
	public void lenTrimAttr1_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSA3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdefghij");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenM die Laenge des Textes mit einem Zeilenumbruch stimmt.
	@Test
	public void lenM_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSB1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdef\nhij");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenM die Laenge einer Konstanten mit der Laenge des Textes uebereinstimmt.
	@Test
	public void lenMLenConstant_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSB2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "1");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenMTrim die Laenge mit einem Zeilenumbruch strimmt.
	@Test
	public void lenMTrim_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSB3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdef\nhij");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim die Laengen der Texte uebereinstimmen.
	@Test
	public void trim_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSC1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdefghij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdefghij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim die Konstante mit der Laenge des Textes uebereinstimmt.
	@Test
	public void trimConstant_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSC2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdefghij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim Stimmen die Laengen der Texte ueberein.
	@Test
	public void trimTrimAttr1_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSC3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdefghij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdefghij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trimM die Text uebereinstimmen.
	@Test
	public void trimM_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSD1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdef\nhij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdef\nhij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trimM, die Konstante mit dem Text uebereinstimmt.
	@Test
	public void trimMConstant_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSD2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdef\nhij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim, die beiden Texte uebereinstimmen.
	@Test
	public void trimTrimAttr_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSD3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdef\nhij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdef\nhij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}

	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isEnumSubVal, das Attribute 2 eine Hierarchie-Stufe unter dem Attribute 1 ist.
	@Test
	public void isEnumSubVal_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSG, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "mehr");
		iomObjA.setattrvalue(OBJ_ATTR2, "mehr.vier");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: inEnumRange das attr2 zwischen attr1 und attr3 sich befindet.
	@Test
	public void inEnumRange_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSH, OBJ_OID1);
		iomObjA.setattrvalue("attr01", "drei");
		iomObjA.setattrvalue("attr02", "zwei");
		iomObjA.setattrvalue("attr03", "vier");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: inEnumRange das attr2 sich innerhalb von attr1 und attr3 befindet, welche ueber eine Unter-Hierarchie gehen.
	@Test
	public void inEnumRangeSubEnum_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSI, OBJ_OID1);
		iomObjA.setattrvalue("attr11", "zwei.zwei");
		iomObjA.setattrvalue("attr12", "eins.zwei");
		iomObjA.setattrvalue("attr13", "drei.zwei");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	@Test
	public void elementCountBag3_Ok(){
		Iom_jObject iomObjM=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM2=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM3=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjN=new Iom_jObject(ILI_CLASSN, OBJ_OID2);
		iomObjN.addattrobj("attrbag1", iomObjM);
		iomObjN.addattrobj("attrbag1", iomObjM2);
		iomObjN.addattrobj("attrbag1", iomObjM3);
		iomObjN.setattrvalue("attr2", "3");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjN));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: elementCount der Bag mit dem attr2 uebereinstimmt.
	@Test
	public void elementCountBag0_Fail(){
		Iom_jObject iomObjN=new Iom_jObject(ILI_CLASSN, OBJ_OID2);
		iomObjN.setattrvalue("attr2", "1");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjN));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint Function23.Topic.ClassN.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: elementCount die Anzahl der Listen mit der Anzahl von attr2 uebereinstimmt.
	@Test
	public void elementCountList5_Ok(){
		Iom_jObject iomObjM=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM2=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM3=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM4=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM5=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjO=new Iom_jObject(ILI_CLASSO, OBJ_OID2);
		iomObjO.addattrobj("attrlist1", iomObjM);
		iomObjO.addattrobj("attrlist1", iomObjM2);
		iomObjO.addattrobj("attrlist1", iomObjM3);
		iomObjO.addattrobj("attrlist1", iomObjM4);
		iomObjO.addattrobj("attrlist1", iomObjM5);
		iomObjO.setattrvalue("attr2", "5");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjO));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: elementCount die Liste mit dem Attribute2 uebereinstimmt.
	@Test
	public void elementCountList0_Ok(){
		Iom_jObject iomObjO=new Iom_jObject(ILI_CLASSO, OBJ_OID2);
		iomObjO.setattrvalue("attr2", "0");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjO));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: objectCount All die richtige Menge der Objekte der Klasse, welche gesucht wird, ausgegeben wird.
	@Test
	public void objectCountALL_Ok(){
		// obj1
		Iom_jObject iomObjQ1=new Iom_jObject(ILI_CLASSQ, OBJ_OID1);
		iomObjQ1.setattrvalue("Art", "a"); // 1. Art=a
		// obj2
		Iom_jObject iomObjQ2=new Iom_jObject(ILI_CLASSQ, OBJ_OID2);
		iomObjQ2.setattrvalue("Art", "a"); // 2. Art=a
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjQ1));
		validator.validate(new ObjectEvent(iomObjQ2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isOfClass die gesuchte Klasse die Super-Klasse oder eine referenzierte Klasse ist. 
	@Test
	public void isOfClassWithRef_Ok(){
		String objTargetId=OBJ_OID1;
		Iom_jObject iomObjS1=new Iom_jObject(ILI_ISOFCLASS_STRUCTA, null);
		Iom_jObject iomObjAP=new Iom_jObject(ILI_ISOFCLASS_STRUCTAP, null);
		Iom_jObject o1Ref=new Iom_jObject("REF", null);
		o1Ref.setobjectrefoid(objTargetId);
		Iom_jObject iomObjU=new Iom_jObject(ILI_ISOFCLASS_CLASSU, OBJ_OID1);
		iomObjU.addattrobj("attrU1", iomObjAP);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjU));
		validator.validate(new ObjectEvent(iomObjS1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isOfClass die gesuchte Klasse, mit dem Resultat der Funktion: myClass uebereinstimmt.
	@Test
	public void isOfClassWithRefEqualToMyClass_Ok(){
		String objTargetId=OBJ_OID1;
		Iom_jObject iomObjAP=new Iom_jObject(ILI_ISOFCLASS_STRUCTAP, null);
		Iom_jObject o1Ref=new Iom_jObject("REF", null);
		o1Ref.setobjectrefoid(objTargetId);
		Iom_jObject iomObjU=new Iom_jObject(ILI_ISOFCLASS_CLASSUA, OBJ_OID1);
		iomObjU.addattrobj("attrU1", iomObjAP);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjU));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isSubClass die Konstante Klasse eine SubKlasse von ClassW ist.
	@Test
	public void isSubClassConstants_Ok(){
		Iom_jObject iomObjW=new Iom_jObject(ILI_CLASSW, OBJ_OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjW));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	@Test
	public void myClass_Ok(){
		Iom_jObject iomStructBP=new Iom_jObject(ILI_STRUCTBP, null);
		Iom_jObject iomObjY=new Iom_jObject(ILI_CLASSY, OBJ_OID1);
		iomObjY.addattrobj("attrY1", iomStructBP);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjY));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertEquals(0,logger.getErrs().size());
	}

    @Test
    public void myClass_WrongClass_Fail(){
        Iom_jObject iomStructB=new Iom_jObject(ILI_STRUCTB, null);
        Iom_jObject iomObjY=new Iom_jObject(ILI_CLASSY, OBJ_OID1);
        iomObjY.addattrobj("attrY1", iomStructB);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjY));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertEquals(1,logger.getErrs().size());
        assertEquals("Mandatory Constraint Function23.Topic.ClassY.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isSubClass die referenzierte Klasse mit der Klasse der Funktion: myClass uebereinstimmt.
	@Test
	public void isSubClassWithRefAndMyClass_Ok(){
		String objTargetId=OBJ_OID1;
		Iom_jObject iomObjS1=new Iom_jObject(ILI_STRUCTB, null);
		Iom_jObject iomObjAP=new Iom_jObject(ILI_STRUCTBP, null);
		Iom_jObject o1Ref=new Iom_jObject("REF", null);
		o1Ref.setobjectrefoid(objTargetId);
		Iom_jObject iomObjU=new Iom_jObject(ILI_CLASSWA, OBJ_OID1);
		iomObjU.addattrobj("attrWA1", iomObjAP);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjU));
		validator.validate(new ObjectEvent(iomObjS1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}

	//Es wird getestet, ob keine Fehlermeldung ausgegeben wird, wenn bei der Funktion objectCount(Role) die Anzahl der Objekte, welche von SA zu TA via Role referenzieren, 1 ist.
	@Test
	public void objectCount_1toN_ref_Ok() {
		Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSA, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
		Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTA, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
		iomObjS1.addattrobj(ILI_ASSOC_SATA1_T2, "REF").setobjectrefoid(iomObjT1.getobjectoid());

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjS1));
		validator.validate(new ObjectEvent(iomObjT1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());

		assertTrue(logger.getErrs().size()==0);
	}
    @Test
    public void objectCount_1toN_ref_0Object_Fail() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSA, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTA, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint Function23.Topic.ClassSA.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }
    @Test
    public void objectCount_1toN_ref_lowerCaseTID_UUID_Ok() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSA, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTA, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
        iomObjS1.addattrobj(ILI_ASSOC_SATA1_T2, "REF").setobjectrefoid(iomObjT1.getobjectoid().toUpperCase());

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_1toN_ref_upperCaseTID_UUID_Ok() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSA, "F4AFA88E-0F2E-467C-98D7-D5ABA6F61D5E");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTA, "F85940F1-93BF-4A7B-AC66-AC6FCAD22C58");
        iomObjS1.addattrobj(ILI_ASSOC_SATA1_T2, "REF").setobjectrefoid(iomObjT1.getobjectoid().toLowerCase());

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_1toN_oppend_Ok() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSB, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTB, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
        iomObjS1.addattrobj(ILI_ASSOC_ST3_T3, "REF").setobjectrefoid(iomObjT1.getobjectoid());

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_1toN_oppend_lowerCaseTID_UUID_Ok() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSB, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTB, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
        iomObjS1.addattrobj(ILI_ASSOC_ST3_T3, "REF").setobjectrefoid(iomObjT1.getobjectoid().toUpperCase());

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_1toN_oppend_upperCaseTID_UUID_Ok() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSB, "F4AFA88E-0F2E-467C-98D7-D5ABA6F61D5E");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTB, "F85940F1-93BF-4A7B-AC66-AC6FCAD22C58");
        iomObjS1.addattrobj(ILI_ASSOC_ST3_T3, "REF").setobjectrefoid(iomObjT1.getobjectoid().toLowerCase());

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_1toN_oppend_0Object_Fail() {
        Iom_jObject iomObjS1 = new Iom_jObject(ILI_CLASSSB, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        Iom_jObject iomObjT1 = new Iom_jObject(ILI_CLASSTB, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());

        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint Function23.Topic.ClassTB.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }

	// Es wird getestet ob keine Fehlermeldung ausgegeben wird, wenn bei der Funktion: objectCount(Role) die Anzahl der Objekte welche von S zu T via Role referenzieren, 2 ist.
	@Test
	public void objectCount_NtoN_Ok(){
		// erstes S->T
		Iom_jObject iomObjS1=new Iom_jObject(ILI_CLASSS, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
		Iom_jObject iomObjT1=new Iom_jObject(ILI_CLASST, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
		Iom_jObject iomObjST1=new Iom_jObject(ILI_ASSOC_ST1, null);
		iomObjST1.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
		iomObjST1.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT1.getobjectoid());
		// zweites S->T
		Iom_jObject iomObjT2=new Iom_jObject(ILI_CLASST, "55778f13-6d93-4a53-9f85-526865bf59a4");
		Iom_jObject iomObjST2=new Iom_jObject(ILI_ASSOC_ST1, null);
		iomObjST2.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
		iomObjST2.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT2.getobjectoid());
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjS1));
		validator.validate(new ObjectEvent(iomObjT1));
		validator.validate(new ObjectEvent(iomObjT2));
		validator.validate(new ObjectEvent(iomObjST1));
		validator.validate(new ObjectEvent(iomObjST2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
    @Test
    public void objectCount_NtoN_lowerCaseTID_UUID_Ok(){
        // erstes S->T
        Iom_jObject iomObjS1=new Iom_jObject(ILI_CLASSS, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        Iom_jObject iomObjT1=new Iom_jObject(ILI_CLASST, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
        Iom_jObject iomObjST1=new Iom_jObject(ILI_ASSOC_ST1, null);
        iomObjST1.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
        iomObjST1.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT1.getobjectoid().toUpperCase());
        // zweites S->T
        Iom_jObject iomObjT2=new Iom_jObject(ILI_CLASST, "55778f13-6d93-4a53-9f85-526865bf59a4");
        Iom_jObject iomObjST2=new Iom_jObject(ILI_ASSOC_ST1, null);
        iomObjST2.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
        iomObjST2.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT2.getobjectoid().toUpperCase());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new ObjectEvent(iomObjT2));
        validator.validate(new ObjectEvent(iomObjST1));
        validator.validate(new ObjectEvent(iomObjST2));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_NtoN_upperCaseTID_UUID_Ok(){
        // erstes S->T
        Iom_jObject iomObjS1=new Iom_jObject(ILI_CLASSS, "F4AFA88E-0F2E-467C-98D7-D5ABA6F61D5E");
        Iom_jObject iomObjT1=new Iom_jObject(ILI_CLASST, "F85940F1-93BF-4A7B-AC66-AC6FCAD22C58");
        Iom_jObject iomObjST1=new Iom_jObject(ILI_ASSOC_ST1, null);
        iomObjST1.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
        iomObjST1.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT1.getobjectoid().toLowerCase());
        // zweites S->T
        Iom_jObject iomObjT2=new Iom_jObject(ILI_CLASST, "55778F13-6D93-4A53-9F85-526865BF59A4");
        Iom_jObject iomObjST2=new Iom_jObject(ILI_ASSOC_ST1, null);
        iomObjST2.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
        iomObjST2.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT2.getobjectoid().toLowerCase());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new ObjectEvent(iomObjT1));
        validator.validate(new ObjectEvent(iomObjT2));
        validator.validate(new ObjectEvent(iomObjST1));
        validator.validate(new ObjectEvent(iomObjST2));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: areArea die richtige Anzahl der Areas von der Geometrie: AREA stammt.
	// 2 objects. Objects with Class implementation
	@Test
	public void areArea_ObjectsWithClass_Ok(){
		Iom_jObject objSurfaceSuccess=new Iom_jObject(ILI_CLASSZB, OBJ_OID1);
		// Geometrie 1
		objSurfaceSuccess.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		// Geometrie 2
		Iom_jObject objSurfaceSuccess2 = new Iom_jObject(ILI_CLASSZB, OBJ_OID2);
		objSurfaceSuccess2.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("484000.000", "70000.000"),
						IomObjectHelper.createCoord("484000.000", "72500.000"),
						IomObjectHelper.createCoord("488000.000", "70500.000"),
						IomObjectHelper.createCoord("484000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new ObjectEvent(objSurfaceSuccess2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}	
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: areArea das Erste Constraint true ist, und das zweite false.
	// 2 objects == second mandatory: class in Objects = fail
	@Test
	public void areArea_EmptyPolygonSet_Ok(){
		Iom_jObject objSurfaceSuccess=new Iom_jObject(ILI_CLASSZD, OBJ_OID1);
		// Geometrie 1
		objSurfaceSuccess.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		// Geometrie 2
		Iom_jObject objSurfaceSuccess2 = new Iom_jObject(ILI_CLASSZD, OBJ_OID2);
		objSurfaceSuccess2.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("484000.000", "70000.000"),
						IomObjectHelper.createCoord("484000.000", "72500.000"),
						IomObjectHelper.createCoord("488000.000", "70500.000"),
						IomObjectHelper.createCoord("484000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new ObjectEvent(objSurfaceSuccess2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: areArea die Geometrien einer AREA im Argument Objects die angegebene Klasse diese Anforderung erfuellt. 
	// 2 objects. Objects with Class implementation
	@Test
	public void areAreaObjectsOfSpecificClass_Ok(){
		Iom_jObject objSurfaceSuccess=new Iom_jObject(ILI_CLASSZA, OBJ_OID1);
		objSurfaceSuccess.setattrvalue("Art", "c");
		// Geometrie 1
		objSurfaceSuccess.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		// Geometrie 2
		Iom_jObject objSurfaceSuccess2 = new Iom_jObject(ILI_CLASSZA, OBJ_OID2);
		objSurfaceSuccess2.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("484000.000", "70000.000"),
						IomObjectHelper.createCoord("484000.000", "72500.000"),
						IomObjectHelper.createCoord("488000.000", "70500.000"),
						IomObjectHelper.createCoord("484000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new ObjectEvent(objSurfaceSuccess2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	//#########################################################//
	//######## FAIL FUNCTIONS #################################//
	//#########################################################//
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isEnumSubVal, die subValue nicht mit der hoeheren Hierarchie nicht uebereinstimmt.
	@Test
	public void isEnumSubVal_MehrVierIsNotSubValOfEins_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSG, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "eins");
		iomObjA.setattrvalue(OBJ_ATTR2, "mehr.vier");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassG.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	

	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: inEnumRange, keine Ordnung besteht.
	@Test
	public void inEnumRange_EnumerationNotOrdered_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSL, OBJ_OID1);
		iomObjA.setattrvalue("attr01", "drei");
		iomObjA.setattrvalue("attr02", "zwei");
		iomObjA.setattrvalue("attr03", "vier");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassL.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}

	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: inEnumRange, das ZielAttribute nicht zwischen dem min Attribute und dem max Attribute liegt.
	@Test
	public void inEnumRange_EnumWithSubEnumNotBetween_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSI, OBJ_OID1);
		iomObjA.setattrvalue("attr12", "zwei.zwei");
		iomObjA.setattrvalue("attr11", "eins.zwei");
		iomObjA.setattrvalue("attr13", "drei.zwei");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassI.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: inEnumRange die Enumeration verschieden ist.
	@Test
	public void inEnumRange_EnumerationsNotSame_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSK, OBJ_OID1);
		iomObjA.setattrvalue("attr11", "zwei");
		iomObjA.setattrvalue("attr21", "eins.zwei");
		iomObjA.setattrvalue("attr31", "drei.zwei");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassK.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: len, die Anzahl der Zeichen nicht gleich lang sind.
	@Test
	public void len_numberOrCharNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSA1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdefghij");
		iomObjA.setattrvalue(OBJ_ATTR2, "9");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassA1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: len, die laenge der Strings nicht der Laenge des attr2 entspricht.
	@Test
	public void len_StringLenthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSA2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "2");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassA2.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: len die Laenge zu lang ist.
	@Test
	public void len_TrimLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSA3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdefghijk");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassA3.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenM, die Laengen nicht uebereinstimmen.
	@Test
	public void lenM_lengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSB1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abdef\nhij");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassB1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenM, die Laenge der Konstanten nicht uebereinstimmen.
	@Test
	public void lenM_ConstantLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSB2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "2");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassB2.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: lenM, die Laenge der attr in der Funktion: trim stehenden laenge mit der Laenge in attr2 nicht uebereinstimmt.
	@Test
	public void lenM_TrimLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSB3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, "abcdef\nhi");
		iomObjA.setattrvalue(OBJ_ATTR2, "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassB3.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim, die Laengen der beiden Strings nicht uebereinstimmen.
	@Test
	public void trim_LengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSC1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdefghi ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdefghij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassC1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim, die Laenge der Konstanten und die Laenge von attr2 nicht uebereinstimmen.
	@Test
	public void trim_ConstantLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSC2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdefghi");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassC2.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trim, der getrimmte Text: attr1 und der attr2 Text nicht uebereinstimmt.
	@Test
	public void trim_TrimedTextLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSC3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abdefghij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdefghij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassC3.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trimM, der getrimmte Text in attr1 nicht mit dem Text in attr2 nicht uebereinstimmt.
	@Test
	public void trimM_MTrimmedLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSD1, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdef\nhij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "bcdef\nhij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassD1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trimM, die Laenge in der Konstante mit der Laenge in attr2 nicht uebereinstimmt.
	@Test
	public void trimM_ConstantLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSD2, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdef\\\\nhj");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassD2.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: trimM, die Laenge der Texte nicht uebereinstimmen.
	@Test
	public void trimM_TrimmedLengthNotEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSD3, OBJ_OID1);
		iomObjA.setattrvalue(OBJ_ATTR1, " abcdef\nij ");
		iomObjA.setattrvalue(OBJ_ATTR2, "abcdef\nhij");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassD3.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: inEnumRange das ZielAttribute nicht zwischen min und max enum in der Enumeration sich befindet.
	@Test
	public void inEnumRange_1IsNotBetween2And4_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSH, OBJ_OID1);
		iomObjA.setattrvalue("attr01", "eins");
		iomObjA.setattrvalue("attr02", "zwei");
		iomObjA.setattrvalue("attr03", "vier");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassH.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: elementCount, der Count des BAG nicht mit dem attr2 uebereinstimmt.
	@Test
	public void elementCountBag_NotEqual_Fail(){
		Iom_jObject iomObjM=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM2=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM3=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjN=new Iom_jObject(ILI_CLASSN, OBJ_OID2);
		iomObjN.addattrobj("attrbag1", iomObjM);
		iomObjN.addattrobj("attrbag1", iomObjM2);
		iomObjN.addattrobj("attrbag1", iomObjM3);
		iomObjN.setattrvalue("attr2", "5");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjN));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassN.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: elementCount die Anzahl der Listen nicht mit der Value von attr2 uebereinstimmt.
	@Test
	public void elementCountList_NotEqual_Fail(){
		Iom_jObject iomObjM=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM2=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM3=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM4=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjM5=new Iom_jObject(ILI_STRUCTM, null);
		Iom_jObject iomObjO=new Iom_jObject(ILI_CLASSO, OBJ_OID2);
		iomObjO.addattrobj("attrlist1", iomObjM);
		iomObjO.addattrobj("attrlist1", iomObjM2);
		iomObjO.addattrobj("attrlist1", iomObjM3);
		iomObjO.addattrobj("attrlist1", iomObjM4);
		iomObjO.addattrobj("attrlist1", iomObjM5);
		iomObjO.setattrvalue("attr2", "6");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjO));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassO.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: objectCount(All), die Anzahl der Objekte nicht stimmt.
	@Test
	public void objectCountALL_ObjectCountNotEqual_Fail(){
		Iom_jObject iomObjQ1=new Iom_jObject(ILI_CLASSQ, OBJ_OID1);
		iomObjQ1.setattrvalue("Art", "a");
		Iom_jObject iomObjQ2=new Iom_jObject(ILI_CLASSQ, OBJ_OID2);
		iomObjQ2.setattrvalue("Art", "b");
		Iom_jObject iomObjR1=new Iom_jObject(ILI_CLASSR, OBJ_OID3);
		Iom_jObject iomObjR2=new Iom_jObject(ILI_CLASSR, OBJ_OID4);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjQ1));
		validator.validate(new ObjectEvent(iomObjR1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Set Constraint Function23.Topic.ClassQ.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
    // Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: objectCount(Role) die Anzahl der Objekte welche von S zu T via Role referenzieren 1!=2 ist.
	@Test
	public void objectCount_NtoN_1Object_Fail(){
		// erstes S->T
		Iom_jObject iomObjS1=new Iom_jObject(ILI_CLASSS, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
		Iom_jObject iomObjT1=new Iom_jObject(ILI_CLASST, "f85940f1-93bf-4a7b-ac66-ac6fcad22c58");
		Iom_jObject iomObjST1=new Iom_jObject(ILI_ASSOC_ST1, null);
		iomObjST1.addattrobj(ILI_ASSOC_ST1_S1, "REF").setobjectrefoid(iomObjS1.getobjectoid());
		iomObjST1.addattrobj(ILI_ASSOC_ST1_T1, "REF").setobjectrefoid(iomObjT1.getobjectoid());
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjS1));
		validator.validate(new ObjectEvent(iomObjT1));
		validator.validate(new ObjectEvent(iomObjST1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassS.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
    // Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: objectCount(Role) die Anzahl der Objekte welche von S zu T via Role referenzieren 1!=2 ist.
    @Test
    public void objectCount_NtoN_0Object_Fail(){
        // erstes S->T
        Iom_jObject iomObjS1=new Iom_jObject(ILI_CLASSS, "f4afa88e-0f2e-467c-98d7-d5aba6f61d5e");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjS1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint Function23.Topic.ClassS.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }
	
	
    @Test
    public void isOfClass_Ok(){
        Iom_jObject iomStructBP=new Iom_jObject(ILI_ISOFCLASS_STRUCTBP, null);
        Iom_jObject iomObjV=new Iom_jObject(ILI_ISOFCLASS_CLASSV, OBJ_OID1);
        iomObjV.addattrobj("attrV1", iomStructBP);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjV));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertEquals(0,logger.getErrs().size());
    }
    @Test
    @Ignore("ilivalidator#336")
    public void isOfClass_EmbeddedRole_Ok(){
        Iom_jObject iomObjA=new Iom_jObject(ILI_ISOFCLASS_CLASSA, OBJ_OID1);
        Iom_jObject iomObjB=new Iom_jObject(ILI_ISOFCLASS_CLASSB, OBJ_OID2);
        Iom_jObject iomObjBp=new Iom_jObject(ILI_ISOFCLASS_CLASSBp, OBJ_OID3);
        iomObjBp.addattrobj("a", "REF").setobjectrefoid(iomObjA.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjA));
        validator.validate(new ObjectEvent(iomObjB));
        validator.validate(new ObjectEvent(iomObjBp));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertEquals(0,logger.getErrs().size());
    }
    @Test
    @Ignore("ilivalidator#336")
    public void isOfClass_EmbeddedRole_Fail(){
        Iom_jObject iomObjA=new Iom_jObject(ILI_ISOFCLASS_CLASSA, OBJ_OID1);
        Iom_jObject iomObjB=new Iom_jObject(ILI_ISOFCLASS_CLASSB, OBJ_OID2);
        Iom_jObject iomObjBp=new Iom_jObject(ILI_ISOFCLASS_CLASSBp, OBJ_OID3);
        iomObjB.addattrobj("a", "REF").setobjectrefoid(iomObjA.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjA));
        validator.validate(new ObjectEvent(iomObjB));
        validator.validate(new ObjectEvent(iomObjBp));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertEquals(1,logger.getErrs().size());
    }
    @Test
    public void isOfClass_subClassOk(){
        Iom_jObject iomStructBPP=new Iom_jObject(ILI_ISOFCLASS_STRUCTBPP, null);
        Iom_jObject iomObjV=new Iom_jObject(ILI_ISOFCLASS_CLASSV, OBJ_OID1);
        iomObjV.addattrobj("attrV1", iomStructBPP);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
        validator.validate(new ObjectEvent(iomObjV));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertEquals(0,logger.getErrs().size());
    }
	@Test
	public void isOfClass_Object_Fail(){
		Iom_jObject iomStructB=new Iom_jObject(ILI_ISOFCLASS_STRUCTB, null);
		Iom_jObject iomObjV=new Iom_jObject(ILI_ISOFCLASS_CLASSV, OBJ_OID1);
		iomObjV.addattrobj("attrV1", iomStructB);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_ISOFCLASS_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjV));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertEquals(1,logger.getErrs().size());
		assertEquals("Mandatory Constraint Function23.IsOfClassTest.ClassV.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isSubClass, die beiden Konstanten Klassen nicht uebereinstimmen.
	@Test
	public void isSubClassConstants_SubClassNotValid_Fail(){
		Iom_jObject iomObjW=new Iom_jObject(ILI_CLASSX, OBJ_OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjW));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassX.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: isSubClass die MyClass ueber eine falsche Referenz fuehrt.
	@Test
	public void isSubClass_MyClassNotValid_Fail(){
		String objTargetId=OBJ_OID1;
		Iom_jObject iomObjS1=new Iom_jObject(ILI_STRUCTA, null);
		Iom_jObject iomObjAP=new Iom_jObject(ILI_STRUCTAP, null);
		Iom_jObject o1Ref=new Iom_jObject("REF", null);
		o1Ref.setobjectrefoid(objTargetId);
		Iom_jObject iomObjU=new Iom_jObject(ILI_CLASSWB, OBJ_OID1);
		iomObjU.addattrobj("attrWA1", iomObjAP);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjU));
		validator.validate(new ObjectEvent(iomObjS1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint Function23.Topic.ClassWB.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: areArea, die Linien sich ueberschneiden.
	// 2 objects == 2 mandatoryConstraint fails.
	@Test
	public void areArea_ObjectsWithClass_IntersectionAreas_Fail(){
		Iom_jObject objSurfaceSuccess=new Iom_jObject(ILI_CLASSZB, OBJ_OID1);
		// Geometrie 1
		objSurfaceSuccess.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		// Geometrie 2
		Iom_jObject objSurfaceSuccess2 = new Iom_jObject(ILI_CLASSZB, OBJ_OID2);
		objSurfaceSuccess2.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("484000.000", "70000.000"),
						IomObjectHelper.createCoord("484000.000", "72500.000"),
						IomObjectHelper.createCoord("480500.000", "70500.000"),
						IomObjectHelper.createCoord("484000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER, ValidationConfig.DISABLE_AREAREAS_MESSAGES, ValidationConfig.TRUE);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new ObjectEvent(objSurfaceSuccess2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==2);
		// Intersection Fail taucht auch auf.
		assertEquals("Mandatory Constraint Function23.Topic.ClassZB.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: areArea, der referenzierte BAG nicht existiert.
	// 1 object. Objects=ALL, SurfaceBAG=BAG count, SurfaceAttr=surface of other structure
	@Test
	public void areArea_SurfaceBAGAttrNameInExists_Fail(){
		Iom_jObject iomObjStruct=new Iom_jObject(ILI_STRUCTF, null);
		// Geometrie 1
		iomObjStruct.addattrobj("Surface", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		Iom_jObject objSurfaceSuccess=new Iom_jObject(ILI_CLASSZF, OBJ_OID1);
		objSurfaceSuccess.setattrvalue("Art", "a");

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjStruct));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Attribute Numbers has wrong number of values", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Warnungsmeldung ausgegeben wird, wenn die Funktion noch nicht implementiert wurde.
	// Die Funktion wurde zwar gefunden, sie ist jedoch nicht implementiert, deshalb die Warnung Function(Name) wurde noch nicht implementiert.
	@Test
	@Ignore("ilivalidator#250")
	public void function_notYetImplemented_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(ILI_CLASSZH, OBJ_OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getWarn().size()==1);
		assertEquals("Function INTERLIS.convertUnit is not yet implemented.", logger.getWarn().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei der Funktion: areArea, die Anzahl der Objects im ersten Argument nicht mit den Geometrien uebereinstimmt.
	// 2 objects. Objects with Class implementation
	@Test
	public void areArea_WrongCount_Fail(){
		Iom_jObject objSurfaceSuccess=new Iom_jObject(ILI_CLASSZA, OBJ_OID1);
		objSurfaceSuccess.setattrvalue("Art", "b");
		Iom_jObject objSurfaceSuccess3=new Iom_jObject(ILI_CLASSZA, OBJ_OID3);
		objSurfaceSuccess3.setattrvalue("Art", "b");
		// Geometrie 1
		objSurfaceSuccess.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		// Geometrie 2
		Iom_jObject objSurfaceSuccess2 = new Iom_jObject(ILI_CLASSZA, OBJ_OID2);
		objSurfaceSuccess2.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("484000.000", "70000.000"),
						IomObjectHelper.createCoord("484000.000", "72500.000"),
						IomObjectHelper.createCoord("488000.000", "70500.000"),
						IomObjectHelper.createCoord("484000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC,BID1));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new ObjectEvent(objSurfaceSuccess2));
		validator.validate(new ObjectEvent(objSurfaceSuccess3));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}

	// Separate areArea-Constraints duerfen sich gegenseitig nicht beeinflussen. Hat es mehrere areArea-Constraints muss jeder separat
	// ausgewertet werden und Regelverletzung fuer jedes involvierte Objekt sowie fehlgeschlagene Bidingungen als Fehlermeldung loggen.
	@Test
	public void areAreas_Caching() {
		// two instances of ClassZA with overlapping geometries (violate areArea constraint)
		// and an instance of ClassZB that satisfies its areArea constraint
		Iom_jObject objectZA1 = new Iom_jObject(ILI_CLASSZA, OBJ_OID1);
		objectZA1.setattrvalue("Art", "a");
		objectZA1.addattrobj("Geometrie", IomObjectHelper.createRectangleGeometry("500000", "70000", "600000", "80000"));

		Iom_jObject objectZA2 = new Iom_jObject(ILI_CLASSZA, OBJ_OID2);
		objectZA2.setattrvalue("Art", "a");
		objectZA2.addattrobj("Geometrie", IomObjectHelper.createRectangleGeometry("550000", "75000", "650000", "85000"));

		Iom_jObject objectZB = new Iom_jObject(ILI_CLASSZB, OBJ_OID3);
		objectZB.addattrobj("Geometrie", IomObjectHelper.createRectangleGeometry("700000", "70000", "800000", "80000"));

		// setup validation
		ValidationConfig modelConfig = new ValidationConfig();
		LogCollector logger = new LogCollector();
		LogEventFactory errFactory = new LogEventFactory();
		Settings settings = new Settings();

		// run validation
		Validator validator = new Validator(td, modelConfig, logger, errFactory, settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC, BID1));
		validator.validate(new ObjectEvent(objectZA1));
		validator.validate(new ObjectEvent(objectZA2));
		validator.validate(new ObjectEvent(objectZB));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());

		// assert logged errors
		assertEquals(3, logger.getErrs().size());
		assertEquals("Intersection coord1 (550000.000, 80000.000), tids o1/Geometrie[1], o2/Geometrie[1]", logger.getErrs().get(0).getEventMsg());
		assertEquals("Intersection coord1 (600000.000, 75000.000), tids o1/Geometrie[1], o2/Geometrie[1]", logger.getErrs().get(1).getEventMsg());
		assertEquals("Set Constraint Function23.Topic.ClassZA.Constraint1 is not true.", logger.getErrs().get(2).getEventMsg());
	}

	// Es wird getestet ob die areAreas Funktion bei vielen Objekten genug schnell berechnet wird.
	@Test
	public void areAreas_Caching_Performance() {
		// create test objects that satisfy the areAreas constraint
		Iom_jObject[] testObjects = new Iom_jObject[10000];
		for (int i = 0; i < testObjects.length; i++) {
			int x = (i % 20) + 500000;
			int y = (i / 20) + 70000;

			Iom_jObject objectZA = new Iom_jObject(ILI_CLASSZA, String.format("o%d", i));
			objectZA.setattrvalue("Art", "a");
			objectZA.addattrobj("Geometrie", IomObjectHelper.createRectangleGeometry(String.valueOf(x), String.valueOf(y), String.valueOf(x + 1), String.valueOf(y + 1)));
			testObjects[i] = objectZA;
		}

		// setup validation
		ValidationConfig modelConfig = new ValidationConfig();
		LogCollector logger = new LogCollector();
		LogEventFactory errFactory = new LogEventFactory();
		PipelinePool pipelinePool = new PipelinePool();
		Settings settings = new Settings();
		Validator validator = new Validator(td, modelConfig, logger, errFactory, pipelinePool, settings);
		validator.setAutoSecondPass(false);

		// run validation
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC, BID1));

		for (Iom_jObject testObject : testObjects) {
			validator.validate(new ObjectEvent(testObject));
		}

		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());

		long startTime = System.nanoTime();
		validator.doSecondPass();
		long elapsedTime = System.nanoTime() - startTime;

		// asserts
		assertEquals(0, logger.getErrs().size());
		assertTrue(elapsedTime < 30000000000L); // 30 seconds
	}

	// Es wird getestet ob AREA bei vielen Objekten genug schnell berechnet wird.
	@Test
	public void area_Performance() {
		// create test objects that satisfy the areAreas constraint
		Iom_jObject[] testObjects = new Iom_jObject[10000];
		for (int i = 0; i < testObjects.length; i++) {
			int x = (i % 20) + 500000;
			int y = (i / 20) + 70000;

			Iom_jObject objectZK = new Iom_jObject(ILI_CLASSZK, String.format("zk_area_o%d", i));
			objectZK.addattrobj("Geometrie", IomObjectHelper.createRectangleGeometry(String.valueOf(x), String.valueOf(y), String.valueOf(x + 1), String.valueOf(y + 1)));
			testObjects[i] = objectZK;
		}

		// setup validation
		ValidationConfig modelConfig = new ValidationConfig();
		LogCollector logger = new LogCollector();
		LogEventFactory errFactory = new LogEventFactory();
		PipelinePool pipelinePool = new PipelinePool();
		Settings settings = new Settings();
		Validator validator = new Validator(td, modelConfig, logger, errFactory, pipelinePool, settings);
		validator.setAutoSecondPass(false);

		// run validation
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(ILI_TOPIC, BID1));

		for (Iom_jObject testObject : testObjects) {
			validator.validate(new ObjectEvent(testObject));
		}

		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());

		long startTime = System.nanoTime();
		validator.doSecondPass();
		long elapsedTime = System.nanoTime() - startTime;

		// asserts
		assertEquals(0, logger.getErrs().size());
		assertTrue(elapsedTime < 30000000000L); // 30 seconds
	}
}
