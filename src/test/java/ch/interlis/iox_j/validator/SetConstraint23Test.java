package ch.interlis.iox_j.validator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ch.ehi.basics.logging.EhiLogger;
import ch.ehi.basics.settings.Settings;
import ch.interlis.ili2c.config.Configuration;
import ch.interlis.ili2c.config.FileEntry;
import ch.interlis.ili2c.config.FileEntryKind;
import ch.interlis.ili2c.metamodel.TransferDescription;
import ch.interlis.iom.IomObject;
import ch.interlis.iom_j.Iom_jObject;
import ch.interlis.iox.IoxLogEvent;
import ch.interlis.iox_j.EndBasketEvent;
import ch.interlis.iox_j.EndTransferEvent;
import ch.interlis.iox_j.ObjectEvent;
import ch.interlis.iox_j.StartBasketEvent;
import ch.interlis.iox_j.StartTransferEvent;
import ch.interlis.iox_j.logging.LogEventFactory;

public class SetConstraint23Test {
	
	private static final String TEST_OUT="src/test/data/validator/";
	private TransferDescription td=null;
	// oid
	private final static String OID1 ="o1";
	private final static String OID2 ="o2";
	private final static String OID3 ="o3";
	private final static String OID4 ="o4";
	// bid
	private final static String BID1="b1";
	// topicA
	private final static String TOPICA="SetConstraint23.TopicA";
	private static final String TOPICA_CLASS1=TOPICA+".Class1";
	private final static String TOPICA_CLASS2=TOPICA+".Class2";
	private static final String TOPICA_CLASS3=TOPICA+".Class3";
	private static final String TOPICA_CLASS4=TOPICA+".Class4";
	// association
	private final static String TOPICA_ASSOC_ASSOC1=TOPICA+".assoc1";
	private final static String TOPICA_ASSOC_ASSOC1_R1="r1";
	private final static String TOPICA_ASSOC_ASSOC1_R2="r2";
	// topicB
	private final static String TOPICB="SetConstraint23.TopicB";
	private static final String TOPICB_CLASS1=TOPICB+".Class1";
		
    private final static String TOPICC="SetConstraint23.TopicC";
    private static final String TOPICC_CLASS1=TOPICC+".Class1";
    private static final String TOPICC_CLASS2=TOPICC+".Class2";
    
	@Before
	public void setUp() throws Exception {
		// ili-datei lesen
		Configuration ili2cConfig=new Configuration();
		FileEntry fileEntry=new FileEntry(TEST_OUT+"SetConstraint23.ili", FileEntryKind.ILIMODELFILE);
		ili2cConfig.addFileEntry(fileEntry);
		td=ch.interlis.ili2c.Ili2c.runCompiler(ili2cConfig);
		assertNotNull(td);
	}

	// prueft ob die setConstraint Bedingung erfuellt wird,
	// wenn die Anzahl der erstellten Objekte, mit der Anzahl der Bedingung uebereinstimmt. 
	@Test
	public void objectCount_IsEqualToConditionCount_Ok(){
		Iom_jObject iomObj1=new Iom_jObject(TOPICA_CLASS1, OID1);
		Iom_jObject iomObj2=new Iom_jObject(TOPICA_CLASS2, OID2);
		// association 1
		Iom_jObject iomObjAssoc1=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(iomObj1.getobjectoid());
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(iomObj2.getobjectoid());
		Iom_jObject iomObj4=new Iom_jObject(TOPICA_CLASS2, OID4);
		// association 2
		Iom_jObject iomObjAssoc2=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
		iomObjAssoc2.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(iomObj1.getobjectoid());
		iomObjAssoc2.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(iomObj4.getobjectoid());
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICA,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new ObjectEvent(iomObj2));
		validator.validate(new ObjectEvent(iomObjAssoc1));
		validator.validate(new ObjectEvent(iomObj4));
		validator.validate(new ObjectEvent(iomObjAssoc2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==0);
	}
    @Test
    public void objectCount_IsEqualToConditionCount_Fail(){
        Iom_jObject iomObj1=new Iom_jObject(TOPICA_CLASS1, OID1);
        Iom_jObject iomObj2=new Iom_jObject(TOPICA_CLASS2, OID2);
        // association 1
        Iom_jObject iomObjAssoc1=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
        iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(iomObj1.getobjectoid());
        iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(iomObj2.getobjectoid());
        Iom_jObject iomObj3=new Iom_jObject(TOPICA_CLASS1, OID3);
        Iom_jObject iomObj4=new Iom_jObject(TOPICA_CLASS2, OID4);
        // association 2
        Iom_jObject iomObjAssoc2=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
        iomObjAssoc2.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(iomObj3.getobjectoid());
        iomObjAssoc2.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(iomObj4.getobjectoid());
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPICA,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new ObjectEvent(iomObj2));
        validator.validate(new ObjectEvent(iomObjAssoc1));
        validator.validate(new ObjectEvent(iomObj3));
        validator.validate(new ObjectEvent(iomObj4));
        validator.validate(new ObjectEvent(iomObjAssoc2));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Set Constraint SetConstraint23.TopicA.Class1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }
	
    @Test
    public void objectCount_Ok(){
        Iom_jObject iomObj1=new Iom_jObject(TOPICC_CLASS1, OID1);
        iomObj1.setattrvalue("attr1", "a");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPICC,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void objectCount_Fail(){
        Iom_jObject iomObj1=new Iom_jObject(TOPICC_CLASS2, OID1);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPICC,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        assertEquals(1,logger.getErrs().size());
        final IoxLogEvent err = logger.getErrs().get(0);
        assertEquals("Set Constraint SetConstraint23.TopicC.Class1.Constraint1 is not true.", err.getEventMsg());
        assertNull(err.getSourceObjectXtfId());
        assertNull(err.getSourceObjectTag());
    }
    
	@Test
	public void preCondition_ObjectCount_Ok(){
		Iom_jObject iomObj1=new Iom_jObject(TOPICB_CLASS1, OID1);
		iomObj1.setattrvalue("attr1", "a");
		Iom_jObject iomObj2=new Iom_jObject(TOPICB_CLASS1, OID2);
		iomObj2.setattrvalue("attr1", "a");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICB,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new ObjectEvent(iomObj2));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==0);
	}
	
    @Test
    public void preCondition_ObjectCount_NoObjects_Ok(){
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPICB,BID1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        assertTrue(logger.getErrs().size()==0);
    }

	@Test
	public void preCondition_ObjectCount_Fail(){
		Iom_jObject iomObj1=new Iom_jObject(TOPICB_CLASS1, OID1);
		iomObj1.setattrvalue("attr1", "b");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICB,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Set Constraint SetConstraint23.TopicB.Class1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// prueft die Funktion: areArea via set constraint.
	// 1 object. Objects=ALL, SurfaceBAG=UNDEFINED, SurfaceAttr=Geometrie.
	@Test
	public void functionAreArea_Surface_Ok(){
		Iom_jObject function1=new Iom_jObject(TOPICA_CLASS3, OID1);
		// Geometrie 1
		function1.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICA,BID1));
		validator.validate(new ObjectEvent(function1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// prueft die Funktion: areArea via set constraint.
	// 3 objects. Objects=ALL, SurfaceBAG=UNDEFINED, SurfaceAttr=Geometrie.
	@Test
	public void functionAreArea_SerevalSurfaces_Ok(){
		EhiLogger.getInstance().setTraceFilter(false);
		Iom_jObject function1=new Iom_jObject(TOPICA_CLASS3, OID1);
		// Geometrie 1
		function1.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("480000.000", "70000.000"),
						IomObjectHelper.createCoord("483000.000", "70000.000"),
						IomObjectHelper.createCoord("480000.000", "73000.000"),
						IomObjectHelper.createCoord("480000.000", "70000.000"))));

		Iom_jObject function2=new Iom_jObject(TOPICA_CLASS3, OID2);
		// Geometrie 1
		function2.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("500000.000", "70000.000"),
						IomObjectHelper.createCoord("503000.000", "70000.000"),
						IomObjectHelper.createCoord("500000.000", "73000.000"),
						IomObjectHelper.createCoord("500000.000", "70000.000"))));

		Iom_jObject function3=new Iom_jObject(TOPICA_CLASS3, OID3);
		// Geometrie 1
		function3.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
				IomObjectHelper.createMultiplePolylineBoundary(
						IomObjectHelper.createCoord("550000.000", "70000.000"),
						IomObjectHelper.createCoord("553000.000", "70000.000"),
						IomObjectHelper.createCoord("550000.000", "73000.000"),
						IomObjectHelper.createCoord("550000.000", "70000.000"))));

		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICA,BID1));
		validator.validate(new ObjectEvent(function1));
		validator.validate(new ObjectEvent(function2));
		validator.validate(new ObjectEvent(function3));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
    @Test
    public void areAreasInSetConstraint_Ok(){
        EhiLogger.getInstance().setTraceFilter(false);
        Iom_jObject class4Obj1=new Iom_jObject(TOPICA_CLASS4, OID1);
        class4Obj1.setattrvalue("attr4", "a");
        // Geometrie 1
        class4Obj1.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
                IomObjectHelper.createMultiplePolylineBoundary(
                        IomObjectHelper.createCoord("483000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "80000.000"),
                        IomObjectHelper.createCoord("483000.000", "80000.000"),
                        IomObjectHelper.createCoord("483000.000", "75000.000"))));

        Iom_jObject class4Obj2=new Iom_jObject(TOPICA_CLASS4, OID2);
        class4Obj2.setattrvalue("attr4", "a");
        // Geometrie 1
        class4Obj2.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
                IomObjectHelper.createMultiplePolylineBoundary(
                        IomObjectHelper.createCoord("486000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "80000.000"),
                        IomObjectHelper.createCoord("490000.000", "80000.000"),
                        IomObjectHelper.createCoord("490000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "75000.000"))));

        Iom_jObject class4Obj3=new Iom_jObject(TOPICA_CLASS4, OID3);
        class4Obj3.setattrvalue("attr4", "b");
        // Geometrie 1
        class4Obj3.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
                IomObjectHelper.createMultiplePolylineBoundary(
                        IomObjectHelper.createCoord("488000.000", "75000.000"),
                        IomObjectHelper.createCoord("488000.000", "80000.000"),
                        IomObjectHelper.createCoord("494000.000", "80000.000"),
                        IomObjectHelper.createCoord("494000.000", "75000.000"),
                        IomObjectHelper.createCoord("488000.000", "75000.000"))));

        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPICA,BID1));
        validator.validate(new ObjectEvent(class4Obj1));
        validator.validate(new ObjectEvent(class4Obj2));
        validator.validate(new ObjectEvent(class4Obj3));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
    
    @Test
    public void areAreasInSetConstraint_Fail(){
        EhiLogger.getInstance().setTraceFilter(false);
        Iom_jObject class4Obj1=new Iom_jObject(TOPICA_CLASS4, OID1);
        class4Obj1.setattrvalue("attr4", "a");
        // Geometrie 1
        class4Obj1.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
                IomObjectHelper.createMultiplePolylineBoundary(
                        IomObjectHelper.createCoord("483000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "80000.000"),
                        IomObjectHelper.createCoord("483000.000", "80000.000"),
                        IomObjectHelper.createCoord("483000.000", "75000.000"))));

        Iom_jObject class4Obj2=new Iom_jObject(TOPICA_CLASS4, OID2);
        class4Obj2.setattrvalue("attr4", "a");
        // Geometrie 1
        class4Obj2.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
                IomObjectHelper.createMultiplePolylineBoundary(
                        IomObjectHelper.createCoord("486000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "80000.000"),
                        IomObjectHelper.createCoord("490000.000", "80000.000"),
                        IomObjectHelper.createCoord("490000.000", "75000.000"),
                        IomObjectHelper.createCoord("486000.000", "75000.000"))));

        Iom_jObject class4Obj3=new Iom_jObject(TOPICA_CLASS4, OID3);
        class4Obj3.setattrvalue("attr4", "a");
        // Geometrie 1
        class4Obj3.addattrobj("Geometrie3", IomObjectHelper.createPolygonFromBoundaries(
                IomObjectHelper.createMultiplePolylineBoundary(
                        IomObjectHelper.createCoord("488000.000", "75000.000"), // this line overlaps OID2
                        IomObjectHelper.createCoord("488000.000", "80000.000"),
                        IomObjectHelper.createCoord("494000.000", "80000.000"),
                        IomObjectHelper.createCoord("494000.000", "75000.000"),
                        IomObjectHelper.createCoord("488000.000", "75000.000"))));

        ValidationConfig modelConfig=new ValidationConfig();
        modelConfig.setConfigValue(ValidationConfig.PARAMETER, ValidationConfig.DISABLE_AREAREAS_MESSAGES, ValidationConfig.TRUE);
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPICA,BID1));
        validator.validate(new ObjectEvent(class4Obj1));
        validator.validate(new ObjectEvent(class4Obj2));
        validator.validate(new ObjectEvent(class4Obj3));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertEquals(1, logger.getErrs().size());
        assertEquals("Set Constraint SetConstraint23.TopicA.Class4.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
    }
	
	// Prueft die Konfiguration: constraint validation.
	// Die Konfiguration ist nicht gesetzt.
	// Es wird eine Fehlermeldung erwartet.
	@Test
	public void lessThanConditionCount_ConstraintDisableSet_NotSet_False(){
		Iom_jObject iomObj1=new Iom_jObject(TOPICA_CLASS1, OID1);
		Iom_jObject iomObj2=new Iom_jObject(TOPICA_CLASS2, OID2);
		// association 1
		Iom_jObject iomObjAssoc1=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(OID1);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(OID2);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICA,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new ObjectEvent(iomObj2));
		validator.validate(new ObjectEvent(iomObjAssoc1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Set Constraint SetConstraint23.TopicA.Class1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Prueft die Konfiguration: constraint validation.
	// Die Konfiguration ist Eingeschaltet.
	// Es wird eine Fehlermeldung erwartet.
	@Test
	public void lessThanConditionCount_ConstraintDisableSet_ON_False(){
		Iom_jObject iomObj1=new Iom_jObject(TOPICA_CLASS1, OID1);
		Iom_jObject iomObj2=new Iom_jObject(TOPICA_CLASS2, OID2);
		// association 1
		Iom_jObject iomObjAssoc1=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(OID1);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(OID2);
		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.CONSTRAINT_VALIDATION, ValidationConfig.ON);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICA,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new ObjectEvent(iomObj2));
		validator.validate(new ObjectEvent(iomObjAssoc1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Set Constraint SetConstraint23.TopicA.Class1.Constraint1 is not true.", logger.getErrs().get(0).getEventMsg());
	}
	
	// Prueft die Konfiguration: constraint validation.
	// Die Konfiguration ist Ausgeschaltet.
	// Es wird erwartet dass keine Fehlermeldung ausgegeben wird.
	@Test
	public void lessThanConditionCount_ConstraintDisableSet_OFF_Ok(){
		Iom_jObject iomObj1=new Iom_jObject(TOPICA_CLASS1, OID1);
		Iom_jObject iomObj2=new Iom_jObject(TOPICA_CLASS2, OID2);
		// association 1
		Iom_jObject iomObjAssoc1=new Iom_jObject(TOPICA_ASSOC_ASSOC1, null);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R1, "REF").setobjectrefoid(OID1);
		iomObjAssoc1.addattrobj(TOPICA_ASSOC_ASSOC1_R2, "REF").setobjectrefoid(OID2);
		ValidationConfig modelConfig=new ValidationConfig();
		modelConfig.setConfigValue(ValidationConfig.PARAMETER,ValidationConfig.CONSTRAINT_VALIDATION, ValidationConfig.OFF);
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPICA,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new ObjectEvent(iomObj2));
		validator.validate(new ObjectEvent(iomObjAssoc1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
}