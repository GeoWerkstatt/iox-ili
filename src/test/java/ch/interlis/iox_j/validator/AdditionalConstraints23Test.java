package ch.interlis.iox_j.validator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ch.ehi.basics.logging.EhiLogger;
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
import ch.interlis.iox_j.StartBasketEvent;
import ch.interlis.iox_j.StartTransferEvent;
import ch.interlis.iox_j.logging.LogEventFactory;

public class AdditionalConstraints23Test {
    private TransferDescription td=null;
	// OID
	private final static String OID1 ="o1";
	private final static String OID2 ="o2";
	// MODEL.TOPIC
	private final static String TOPIC="AdditionalConstraints23.Topic";
	// CLASS
	private final static String CLASSA=TOPIC+".ClassA";
	private final static String CLASSB=TOPIC+".ClassB";
	private final static String CLASSC=TOPIC+".ClassC";
	private final static String CLASSD=TOPIC+".ClassD";
	private final static String CLASSE=TOPIC+".ClassE";
	private final static String CLASSF=TOPIC+".ClassF";
	private final static String CLASSG=TOPIC+".ClassG";
	private final static String CLASSH=TOPIC+".ClassH";
	private final static String CLASSI=TOPIC+".ClassI";
	private final static String CLASSJ=TOPIC+".ClassJ";
    private final static String CLASSK=TOPIC+".ClassK";
    private static final String CLASSK_ATTR_K = "attrK";
    private final static String CLASSL=TOPIC+".ClassL";
    private static final String CLASSL_ATTR_L = "attrL";
    private final static String ASSOC_K2L_N2N=TOPIC+".k2l_n2n";
    private static final String ASSOC_K2L_N2N_ROLEL_N2N = "roleL_n2n";
    private static final String ASSOC_K2L_N2N_ROLEK_N2N = "roleK_n2n";
    private static final String ASSOC_K2L_N21_ROLEK_N21 = "roleK_n21";
    // STRUCT
	private final static String STRUCTA=TOPIC+".StructA";	
	private final static String STRUCTB=TOPIC+".StructB";
	// BID
	private final static String BID1="b1";
	
	@Before
	public void setUp() throws Exception {
		// ili-datei lesen
		Configuration ili2cConfig=new Configuration();
		FileEntry fileEntry=new FileEntry("src/test/data/validator/AdditionalConstraints23.ili", FileEntryKind.ILIMODELFILE);
		ili2cConfig.addFileEntry(fileEntry);
		td=ch.interlis.ili2c.Ili2c.runCompiler(ili2cConfig);
		assertNotNull(td);
	}

	//#########################################################//
	//######## SUCCESS ADDITIONAL CONSTRAINTS #################//
	//#########################################################//	
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein PlausibilityContraint true ergibt.
	@Test
	public void plausibilityConstraintTrue_Ok(){
		Iom_jObject obj1=new Iom_jObject(CLASSF, OID1);
		obj1.setattrvalue("attr1", "7");
		obj1.setattrvalue("attr2", "5");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelG;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}	
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein MandatoryConstraint true ergibt.
	@Test
	public void mandatoryConstraintTrue_Ok(){
		Iom_jObject obj1=new Iom_jObject(CLASSB, OID1);
		obj1.setattrvalue("attr1", "5");
		obj1.setattrvalue("attr2", "5");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelC;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein MandatoryConstraint InEqualation true ergibt.
	@Test
	public void mandatoryConstraint_InEqualTrue_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(CLASSE, OID1);
		IomObject coordValue3=iomObjA.addattrobj("attr3", "COORD");
		coordValue3.setattrvalue("C1", "480000.000");
		coordValue3.setattrvalue("C2", "70000.000");
		coordValue3.setattrvalue("C3", "5000.000");
		IomObject coordValue4=iomObjA.addattrobj("attr4", "COORD");
		coordValue4.setattrvalue("C1", "480000.000");
		coordValue4.setattrvalue("C2", "88888.000");
		coordValue4.setattrvalue("C3", "5000.000");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelC;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn Daten in 2 unterschiedlichen VIEW's ausserhalb des Models
	// in MandatoryConstraints true ergeben.
	@Test
	public void mandatoryConstraint_2ModelsBothTrue_Ok(){
		Iom_jObject iomObjA=new Iom_jObject(CLASSE, OID1);
		IomObject coordValue3=iomObjA.addattrobj("attr3", "COORD");
		coordValue3.setattrvalue("C1", "480000.000");
		coordValue3.setattrvalue("C2", "70000.000");
		coordValue3.setattrvalue("C3", "5000.000");
		IomObject coordValue4=iomObjA.addattrobj("attr4", "COORD");
		coordValue4.setattrvalue("C1", "480000.000");
		coordValue4.setattrvalue("C2", "88888.000");
		coordValue4.setattrvalue("C3", "5000.000");
		Iom_jObject iomObjB=new Iom_jObject(CLASSB, OID2);
		iomObjB.setattrvalue("attr1", "5");
		iomObjB.setattrvalue("attr2", "5");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelD;AdditionalModelC;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new ObjectEvent(iomObjB));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein SetConstraint true ist.
	@Test
	public void setConstraint_BagOfStruct_Ok(){
		Iom_jObject iomObjStruct=new Iom_jObject(STRUCTA, null);
		Iom_jObject iomObjStruct2=new Iom_jObject(STRUCTA, null);
		Iom_jObject iomObj=new Iom_jObject(CLASSC, OID1);
		iomObj.addattrobj("Numbers", iomObjStruct);
		iomObj.addattrobj("Numbers", iomObjStruct2);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelF;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjStruct));
		validator.validate(new ObjectEvent(iomObjStruct2));
		validator.validate(new ObjectEvent(iomObj));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}	
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// eine existenceContraint true ergibt.
	@Test
	public void existenceConstraintTrue_Ok() throws Exception{
		Iom_jObject conditionObj=new Iom_jObject(CLASSH, OID1);
		conditionObj.setattrvalue("superAttr", "lars");
		Iom_jObject obj1=new Iom_jObject(CLASSG, OID2);
		obj1.setattrvalue("subAttr", "lars");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelB;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(conditionObj));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// Es wird getestet ob die eigen erstellte Fehlermeldung ausgegeben wird, wenn die Value des Subattrs nicht in der View gefunden werden kann und validationConfig msg nicht leer ist.
	@Test
	public void uniquenessConstraint_AttrsNotEqual_Ok() throws Exception {
		Iom_jObject obj1=new Iom_jObject(CLASSA, OID1);
		obj1.setattrvalue("attr1", "Ralf");
		obj1.setattrvalue("attr2", "20");
		Iom_jObject obj2=new Iom_jObject(CLASSA, OID2);
		obj2.setattrvalue("attr1", "Anna");
		obj2.setattrvalue("attr2", "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelE");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new ObjectEvent(obj2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	
	//#########################################################//
	//########### FAIL ADDITIONAL CONSTRAINTS #################//
	//#########################################################//
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein PlausibilityContraint false ergibt.
	@Test
	public void plausibilityConstraintFale_False(){
		Iom_jObject obj1=new Iom_jObject(CLASSF, OID1);
		obj1.setattrvalue("attr1", "5");
		obj1.setattrvalue("attr2", "7");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelG;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Plausibility Constraint AdditionalModelG.AdditionalTopicG.AdditionalClassG.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein MandatoryConstraint false ergibt.
	@Test
	public void mandatoryConstraint_NotEqual_Fail(){
		Iom_jObject obj1=new Iom_jObject(CLASSB, OID1);
		obj1.setattrvalue("attr1", "5");
		obj1.setattrvalue("attr2", "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelC;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint AdditionalModelC.AdditionalTopicC.AdditionalClassC.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
    @Test
    public void mandatoryConstraint_NotEqual_DuplicateModel_Fail(){
        Iom_jObject obj1=new Iom_jObject(CLASSB, OID1);
        obj1.setattrvalue("attr1", "5");
        obj1.setattrvalue("attr2", "10");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelC;AdditionalConstraints23");
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC,BID1));
        validator.validate(new ObjectEvent(obj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint AdditionalModelC.AdditionalTopicC.AdditionalClassC.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }
	

	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn die VIEW ausserhalb des Models nicht gefunden wird.
	@Test
	public void mandatoryConstraint_ConfigConstraintModelNameNotExist_False(){
		Iom_jObject iomObjA=new Iom_jObject(CLASSB, OID1);
		iomObjA.setattrvalue("attr1", "5");
		iomObjA.setattrvalue("attr2", "10");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalConstraints23Zusatz99999999");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("required additional model AdditionalConstraints23Zusatz99999999 not found", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein MandatoryConstraint false ergibt.
	@Test
	public void mandatoryConstraint_CoordsAreEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(CLASSE, OID1);
		IomObject coordValue3=iomObjA.addattrobj("attr3", "COORD");
		coordValue3.setattrvalue("C1", "480000.000");
		coordValue3.setattrvalue("C2", "70000.000");
		coordValue3.setattrvalue("C3", "5000.000");
		IomObject coordValue4=iomObjA.addattrobj("attr4", "COORD");
		coordValue4.setattrvalue("C1", "480000.000");
		coordValue4.setattrvalue("C2", "70000.000");
		coordValue4.setattrvalue("C3", "5000.000");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelD");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint AdditionalModelD.AdditionalTopicD.AdditionalClassD.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in 2 VIEW's ausserhalb des Models
	// ein MandatoryConstraint aus einer VIEW mit einer Coord Equalation false ergibt.
	@Test
	public void mandatoryConstraint_2Models_CoordIsEqual_Fail(){
		Iom_jObject iomObjA=new Iom_jObject(CLASSE, OID1);
		IomObject coordValue3=iomObjA.addattrobj("attr3", "COORD");
		coordValue3.setattrvalue("C1", "480000.000");
		coordValue3.setattrvalue("C2", "70000.000");
		coordValue3.setattrvalue("C3", "5000.000");
		IomObject coordValue4=iomObjA.addattrobj("attr4", "COORD");
		coordValue4.setattrvalue("C1", "480000.000");
		coordValue4.setattrvalue("C2", "70000.000");
		coordValue4.setattrvalue("C3", "5000.000");
		Iom_jObject iomObjB=new Iom_jObject(CLASSB, OID2);
		iomObjB.setattrvalue("attr1", "5");
		iomObjB.setattrvalue("attr2", "5");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelD;AdditionalModelC");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjA));
		validator.validate(new ObjectEvent(iomObjB));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Mandatory Constraint AdditionalModelD.AdditionalTopicD.AdditionalClassD.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn in einer VIEW ausserhalb des Models
	// ein SetConstraint false ergibt.
	@Test
	public void setConstraint_BagOfStructWrongNumber_Fail(){
		Iom_jObject iomObjStruct=new Iom_jObject(STRUCTA, null);
		Iom_jObject iomObjStruct2=new Iom_jObject(STRUCTA, null);
		Iom_jObject iomObjStruct3=new Iom_jObject(STRUCTA, null);
		Iom_jObject iomObj=new Iom_jObject(CLASSC, OID1);
		iomObj.addattrobj("Numbers", iomObjStruct);
		iomObj.addattrobj("Numbers", iomObjStruct2);
		iomObj.addattrobj("Numbers", iomObjStruct3);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelF");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjStruct));
		validator.validate(new ObjectEvent(iomObjStruct2));
		validator.validate(new ObjectEvent(iomObjStruct3));
		validator.validate(new ObjectEvent(iomObj));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Set Constraint AdditionalModelF.AdditionalTopicF.AdditionalClassF.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob ein Fehler ausgegeben wird, wenn die Value des Subattrs nicht in der View gefunden werden kann.
	@Test
	public void existenceConstraint_AttrsNotEqual_Fail() throws Exception{
		Iom_jObject conditionObj=new Iom_jObject(CLASSH, OID1);
		conditionObj.setattrvalue("superAttr", "lars");
		Iom_jObject obj1=new Iom_jObject(CLASSG, OID2);
		obj1.setattrvalue("subAttr", "Andreas");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelB");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(conditionObj));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Existence constraint AdditionalModelB.AdditionalTopicB.AdditionalClassB.Constraint1 is violated! The value of the attribute subAttr of AdditionalConstraints23.Topic.ClassG was not found in the condition class.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob die eigen erstellte Fehlermeldung ausgegeben wird, wenn die Value des Subattrs nicht in der View gefunden werden kann und validationConfig msg nicht leer ist.
	@Test
	public void uniquenessConstraint_AttrsNotEqual_Fail() throws Exception {
		Iom_jObject obj1=new Iom_jObject(CLASSA, OID1);
		obj1.setattrvalue("attr1", "Ralf");
		obj1.setattrvalue("attr2", "20");
		Iom_jObject obj2=new Iom_jObject(CLASSA, OID2);
		obj2.setattrvalue("attr1", "Ralf");
		obj2.setattrvalue("attr2", "20");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelE");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new ObjectEvent(obj2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Unique constraint AdditionalModelE.AdditionalTopicE.AdditionalClassE.Constraint1 is violated! Values Ralf, 20 already exist in Object: o1", logger.getErrs().get(0).getEventMsg());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei die Funktion: areArea,
	// ueber eine additional constraint via set constraint ausgefuehrt werden kann.
	// 1 object. Objects=ALL, SurfaceBAG=UNDEFINED, SurfaceAttr=Geometrie.
	@Test
	public void mandatoryConstraint_FunctionAreArea_Ok(){
		Iom_jObject function1=new Iom_jObject(CLASSI, OID1);
		// Geometrie 1
		function1.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelH");
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(function1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertEquals(0, logger.getErrs().size());
	}
	
	// Es wird getestet ob eine Fehlermeldung ausgegeben wird, wenn bei die Funktion: areArea,
	// ueber eine additional constraint via set constraint ausgefuehrt werden kann und die Objekte verschiedene Values enthalten.
	// 1 object. Objects=ALL, SurfaceBAG=>> Numbers, SurfaceAttr=>> AdditionalConstraints23.Topic.StructD->Surface.
	@Test
	public void mandatoryConstraint_FunctionAreArea_Fail(){
		Iom_jObject iomObjStruct=new Iom_jObject(STRUCTB, null);
		// Geometrie 1
		iomObjStruct.addattrobj("Surface", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));
		// Geometrie 2
		Iom_jObject iomObjStruct2=new Iom_jObject(STRUCTB, null);
		iomObjStruct2.addattrobj("Surface", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("484000.000", "70000.000"),
						IomObjectHelper.createCoord("484000.000", "72500.000"),
						IomObjectHelper.createCoord("480500.000", "70500.000"),
						IomObjectHelper.createCoord("484000.000", "70000.000"))));

		Iom_jObject objSurfaceSuccess=new Iom_jObject(CLASSD, OID1);
		objSurfaceSuccess.addattrobj("Numbers", iomObjStruct);
		objSurfaceSuccess.addattrobj("Numbers", iomObjStruct2);
		objSurfaceSuccess.setattrvalue("Art", "a");

		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelI");
        modelConfig.setConfigValue(ValidationConfig.PARAMETER, ValidationConfig.DISABLE_AREAREAS_MESSAGES, ValidationConfig.TRUE);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(iomObjStruct));
		validator.validate(new ObjectEvent(iomObjStruct2));
		validator.validate(new ObjectEvent(objSurfaceSuccess));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		LogCollectorAssertions.AssertAllEventMessages(logger.getErrs(),
				"Set Constraint AdditionalModelI.AdditionalTopicI.AdditionalClassI.Constraint1 is not true.");
	}
	
	// Es wird getestet ob eine Intersection Fehlermeldung ausgegeben wird,
	// wenn sich die Boundaries der Surface selbst ueberschneiden.
	@Test
	public void mandatoryConstraint_OverlappedSurface_False(){
	    EhiLogger.getInstance().setTraceFilter(false);
		Iom_jObject function1=new Iom_jObject(CLASSI, OID1);
		// Geometrie 1
		function1.addattrobj("Geometrie", IomObjectHelper.createPolygonFromBoundaries(
				// outer boundary
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "250000.000"),
						IomObjectHelper.createCoord("600000.000", "250000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000")),
				// inner boundary
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("500000.000", "150000.000"),
						IomObjectHelper.createCoord("500000.000", "230000.000"),
						IomObjectHelper.createCoord("600000.000", "230000.000"),
						IomObjectHelper.createCoord("500000.000", "150000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelH");
        modelConfig.setConfigValue(ValidationConfig.PARAMETER, ValidationConfig.DISABLE_AREAREAS_MESSAGES, ValidationConfig.TRUE);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(function1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		LogCollectorAssertions.AssertAllEventMessages(logger.getErrs(),
				"Intersection coord1 (571428.571, 207142.857), tids o1, o1",
				"Intersection coord1 (586666.667, 230000.000), tids o1, o1");
	}
	
	// Prueft die Konfiguration: constraint validation.
	// Die Konfiguration ist nicht gesetzt.
	// Es wird eine Fehlermeldung erwartet.
	@Test
	public void plausibilityConstraintFail_ConstraintDisableSet_NotSet_False(){
		Iom_jObject obj1=new Iom_jObject(CLASSF, OID1);
		obj1.setattrvalue("attr1", "5");
		obj1.setattrvalue("attr2", "7");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelG;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Plausibility Constraint AdditionalModelG.AdditionalTopicG.AdditionalClassG.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Prueft die Konfiguration: constraint validation.
	// Die Konfiguration ist Eingeschaltet.
	// Es wird eine Fehlermeldung erwartet.
	@Test
	public void plausibilityConstraintFail_ConstraintDisableSet_ON_False(){
		Iom_jObject obj1=new Iom_jObject(CLASSF, OID1);
		obj1.setattrvalue("attr1", "5");
		obj1.setattrvalue("attr2", "7");
		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.CONSTRAINT_VALIDATION, ValidationConfig.ON);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelG;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Plausibility Constraint AdditionalModelG.AdditionalTopicG.AdditionalClassG.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Prueft die Konfiguration: constraint validation.
	// Die Konfiguration ist Ausgeschaltet.
	// Es wird erwartet dass keine Fehlermeldung ausgegeben wird.
	@Test
	public void plausibilityConstraintFail_ConstraintDisableSet_OFF_Ok(){
		Iom_jObject obj1=new Iom_jObject(CLASSF, OID1);
		obj1.setattrvalue("attr1", "5");
		obj1.setattrvalue("attr2", "7");
		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.CONSTRAINT_VALIDATION, ValidationConfig.OFF);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelG;");
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC,BID1));
		validator.validate(new ObjectEvent(obj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	private static void setRef(IomObject iomObj,String roleName, String refid) {
        iomObj.addattrobj(roleName, "REF").setobjectrefoid(refid);
	}
    @Test
    public void assoc_N2N_ObjectPathWithRolename() {
        Iom_jObject objK=new Iom_jObject(CLASSK, "o1");
        objK.setattrvalue(CLASSK_ATTR_K, "valueK");
        Iom_jObject objL=new Iom_jObject(CLASSL, "o2");
        objL.setattrvalue(CLASSL_ATTR_L, "valueL");
        Iom_jObject linkObj1=new Iom_jObject(ASSOC_K2L_N2N,null);
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEK_N2N,objK.getobjectoid());
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEL_N2N,objL.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelK_Assoc;");
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC,BID1));
        validator.validate(new ObjectEvent(objK));
        validator.validate(new ObjectEvent(objL));
        validator.validate(new ObjectEvent(linkObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }	
    @Test
    public void assoc_N2N_ObjectPathWithRolename_Fail() {
        Iom_jObject objK=new Iom_jObject(CLASSK, "o1");
        objK.setattrvalue(CLASSK_ATTR_K, "valueK");
        Iom_jObject objL=new Iom_jObject(CLASSL, "o2");
        objL.setattrvalue(CLASSL_ATTR_L, "valueK"); // violates mandatory constraint
        Iom_jObject linkObj1=new Iom_jObject(ASSOC_K2L_N2N,null);
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEK_N2N,objK.getobjectoid());
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEL_N2N,objL.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelK_Assoc;");
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC,BID1));
        validator.validate(new ObjectEvent(objK));
        validator.validate(new ObjectEvent(objL));
        validator.validate(new ObjectEvent(linkObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint AdditionalModelK_Assoc.Topic.View_n2n.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }   
    @Test
    public void assoc_N21_ObjectPathWithRolename() {
        Iom_jObject objK=new Iom_jObject(CLASSK, "o1");
        objK.setattrvalue(CLASSK_ATTR_K, "valueK");
        Iom_jObject objL=new Iom_jObject(CLASSL, "o2");
        setRef(objL,ASSOC_K2L_N21_ROLEK_N21,objK.getobjectoid());
        Iom_jObject linkObj1=new Iom_jObject(ASSOC_K2L_N2N,null);
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEK_N2N,objK.getobjectoid());
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEL_N2N,objL.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelK_Assoc;");
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC,BID1));
        validator.validate(new ObjectEvent(objK));
        validator.validate(new ObjectEvent(objL));
        validator.validate(new ObjectEvent(linkObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }   
    @Test
    public void assoc_N21_ObjectPathWithRolename_Fail() {
        Iom_jObject objK=new Iom_jObject(CLASSK, "o1");
        objK.setattrvalue(CLASSK_ATTR_K, "a");  // violates mandatory constraint
        Iom_jObject objL=new Iom_jObject(CLASSL, "o2");
        setRef(objL,ASSOC_K2L_N21_ROLEK_N21,objK.getobjectoid());
        Iom_jObject linkObj1=new Iom_jObject(ASSOC_K2L_N2N,null);
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEK_N2N,objK.getobjectoid());
        setRef(linkObj1,ASSOC_K2L_N2N_ROLEL_N2N,objL.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelK_Assoc;");
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC,BID1));
        validator.validate(new ObjectEvent(objK));
        validator.validate(new ObjectEvent(objL));
        validator.validate(new ObjectEvent(linkObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint AdditionalModelK_Assoc.Topic.View_n21.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }   
    @Test
    public void assoc_class_N2N_ObjectPathWithRolename_Fail() {
        Iom_jObject objK=new Iom_jObject(CLASSK, "o1");
        Iom_jObject objL=new Iom_jObject(CLASSL, "o2");
        //Iom_jObject linkObj1=new Iom_jObject(ASSOC_K2L_N2N,null);  // missing link violates constraint
        //setRef(linkObj1,ASSOC_K2L_N2N_ROLEK_N2N,objK.getobjectoid());
        //setRef(linkObj1,ASSOC_K2L_N2N_ROLEL_N2N,objL.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.ADDITIONAL_MODELS, "AdditionalModelK_Assoc;");
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC,BID1));
        validator.validate(new ObjectEvent(objK));
        validator.validate(new ObjectEvent(objL));
        //validator.validate(new ObjectEvent(linkObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Mandatory Constraint AdditionalModelK_Assoc.Topic.View_class_n2n.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }   
}