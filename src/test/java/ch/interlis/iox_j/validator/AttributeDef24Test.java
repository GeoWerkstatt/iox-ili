package ch.interlis.iox_j.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ch.ehi.basics.settings.Settings;
import ch.interlis.ili2c.config.Configuration;
import ch.interlis.ili2c.config.FileEntry;
import ch.interlis.ili2c.config.FileEntryKind;
import ch.interlis.ili2c.metamodel.TransferDescription;
import ch.interlis.iom_j.Iom_jObject;
import ch.interlis.iox_j.EndBasketEvent;
import ch.interlis.iox_j.EndTransferEvent;
import ch.interlis.iox_j.ObjectEvent;
import ch.interlis.iox_j.StartBasketEvent;
import ch.interlis.iox_j.StartTransferEvent;
import ch.interlis.iox_j.logging.LogEventFactory;

public class AttributeDef24Test {
	private TransferDescription td=null;
	// OID
	private final static String OID1 ="o1";
	// MODEL TOPIC
	private final static String TOPIC_ATTRMULTIPLICITY="AttributeDef24.AttrMultiplicity";
	private final static String TOPIC_VIEWTOPIC="AttributeDef24.ViewTopic";
	// CLASS EXTENDED
	// ATTRMULTIPLICITY CLASSES
	private final static String DIRECT_CLASSA=TOPIC_ATTRMULTIPLICITY+".ClassA";
    private final static String DIRECT_CLASSA2=TOPIC_ATTRMULTIPLICITY+".ClassA2";
    private final static String DIRECT_CLASSA3=TOPIC_ATTRMULTIPLICITY+".ClassA3";
    private final static String DIRECT_CLASSAP=TOPIC_ATTRMULTIPLICITY+".ClassAp";
    private final static String DIRECT_CLASSAP2=TOPIC_ATTRMULTIPLICITY+".ClassAp2";
	private final static String DIRECT_CLASSB=TOPIC_ATTRMULTIPLICITY+".ClassB";
    private final static String DIRECT_CLASSB2=TOPIC_ATTRMULTIPLICITY+".ClassB2";
	private final static String DIRECT_CLASSC=TOPIC_ATTRMULTIPLICITY+".ClassC";
	private final static String DIRECT_CLASSD=TOPIC_ATTRMULTIPLICITY+".ClassD";
	private final static String DIRECT_CLASSE=TOPIC_ATTRMULTIPLICITY+".ClassE";
	private final static String DIRECT_CLASSF=TOPIC_ATTRMULTIPLICITY+".ClassF";
	// ATTRs in VIEWs
	private final static String VIEWTOPIC_VIEW1=TOPIC_VIEWTOPIC+".View1";
	private final static String VIEWTOPIC_VIEW2=TOPIC_VIEWTOPIC+".View2";
	// BID
	private final static String BID1="b1";
	
	@Before
	public void setUp() throws Exception {
		// ili-datei lesen
		Configuration ili2cConfig=new Configuration();
		FileEntry fileEntry=new FileEntry("src/test/data/validator/AttributeDef24.ili", FileEntryKind.ILIMODELFILE);
		ili2cConfig.addFileEntry(fileEntry);
		td=ch.interlis.ili2c.Ili2c.runCompiler(ili2cConfig);
		assertNotNull(td);
	}
	
	// wenn ein optionaler Wert gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrOptional_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA, OID1);
		iomObj1.setattrvalue("attrOptional", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
    @Test
    public void class_attr0_2_With2Value_Ok() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA2, OID1);
        iomObj1.addattrvalue("attrOptional", "test");
        iomObj1.addattrvalue("attrOptional", "test");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void class_attr0_2_WrongTextValue_Fail() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA2, OID1);
        iomObj1.addattrvalue("attrOptional", "test");
        iomObj1.addattrvalue("attrOptional", "TestValueToLong");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Attribute attrOptional[1] is length restricted to 10", logger.getErrs().get(0).getEventMsg());
    }
    @Test
    public void class_attr0_2_With2NumValue_Ok() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA3, OID1);
        iomObj1.addattrvalue("attrOptional", "1");
        iomObj1.addattrvalue("attrOptional", "1.2");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
    @Test
    public void class_attr0_2_WrongNumericValue_Fail() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA3, OID1);
        iomObj1.addattrvalue("attrOptional", "1");
        iomObj1.addattrvalue("attrOptional", "false");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("value <false> is not a number in attribute attrOptional[1]", logger.getErrs().get(0).getEventMsg());
    }
    @Test
    public void class_attr0_2_With3Value_Fail() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA2, OID1);
        iomObj1.addattrvalue("attrOptional", "test");
        iomObj1.addattrvalue("attrOptional", "test");
        iomObj1.addattrvalue("attrOptional", "test");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Attribute attrOptional has wrong number of values", logger.getErrs().get(0).getEventMsg());
    }
	
	// wenn ein optionaler Wert nicht gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrOptional_WithoutValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
    // wenn ein optionaler Wert nicht gesetzt wird,
    // soll keine Fehlermeldung ausgegeben werden.
    @Test
    public void class_attr0_2_WithoutValue_Ok() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSA2, OID1);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
	
	// wenn einem Wert eines Attributes innerhalb einer Klasse,
	// welche auf eine Klasse EXTENDED die einen OPTIONALEN Attributewert enthaelt,
	// ein MANDATORY zugewiesen wird, soll bei einer Definition eines Wertes,
	// keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrMandatory_ExtendedOptional_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSAP, OID1);
		iomObj1.setattrvalue("attrOptional", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
    @Test
    public void class_attr0_2_Extended1_1_With1Value_Ok() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSAP2, OID1);
        iomObj1.addattrvalue("attrOptional", "test");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
	
	// wenn einem Wert eines Attributes innerhalb einer Klasse,
	// welche auf eine Klasse EXTENDED die einen OPTIONALEN Attributewert enthaelt,
	// ein MANDATORY zugewiesen wird, soll eine Fehlermeldung ausgegeben werden,
	// wenn kein Attributewert erstellt wird.
	@Test
	public void class_attrMandatory_ExtendedOptional_WithoutValue_Fail() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSAP, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Attribute attrOptional requires a value", logger.getErrs().get(0).getEventMsg());
	}
    @Test
    public void class_attr0_2_Extended1_1_With2Value_Fail() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSAP2, OID1);
        iomObj1.addattrvalue("attrOptional", "test");
        iomObj1.addattrvalue("attrOptional", "test");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Attribute attrOptional has wrong number of values", logger.getErrs().get(0).getEventMsg());
    }
    @Test
    public void class_attr0_2_Extended1_1_WithoutValue_Fail() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSAP2, OID1);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Attribute attrOptional requires a value", logger.getErrs().get(0).getEventMsg());
    }
	
	// wenn ein MANDATORY Wert gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrMandatory_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSB, OID1);
		iomObj1.setattrvalue("attrMandatory", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
    @Test
    public void class_attr1_2_WithValue_Ok() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSB2, OID1);
        iomObj1.setattrvalue("attrMandatory", "test");
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        // Asserts
        assertTrue(logger.getErrs().size()==0);
    }
	
	// wenn bei einem MANDATORY Wert, kein Wert erstellt wird,
	// soll eine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrMandatory_WithoutValue_Fail() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSB, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Attribute attrMandatory requires a value", logger.getErrs().get(0).getEventMsg());
	}
    @Test
    public void class_attr1_2_WithoutValue_Fail() {
        Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSB2, OID1);
        ValidationConfig modelConfig=new ValidationConfig();
        LogCollector logger=new LogCollector();
        LogEventFactory errFactory=new LogEventFactory();
        Settings settings=new Settings();
        Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
        validator.validate(new StartTransferEvent());
        validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
        validator.validate(new ObjectEvent(iomObj1));
        validator.validate(new EndBasketEvent());
        validator.validate(new EndTransferEvent());
        assertTrue(logger.getErrs().size()==1);
        assertEquals("Attribute attrMandatory has wrong number of values", logger.getErrs().get(0).getEventMsg());
    }
	
	// besteht innerhalb einer Klasse eine referenz auf eine DOMAIN,
	// zu einem OPTIONALEN Wert, der erstellt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrOptional_DomainOptional_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSC, OID1);
		iomObj1.setattrvalue("attrOptional", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// besteht innerhalb einer Klasse eine referenz auf eine DOMAIN,
	// zu einem OPTIONALEN Wert, der nicht erstellt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrOptional_DomainOptional_WithoutValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSC, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==0);
	}
	
	// wenn einem Attributewert MANDATORY zugewiesen wird,
	// dieser auf eine DOMAIN referenziert, darin auf einen OPTIONALEN Wert,
	// welcher gesetzt wird, soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrMandatory_DomainOptional_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSD, OID1);
		iomObj1.setattrvalue("attrMandatory", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// wenn einem Attributewert MANDATORY zugewiesen wird,
	// dieser auf eine DOMAIN referenziert, darin auf einen OPTIONALEN Wert,
	// welcher nicht gesetzt wird, soll eine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrMandatory_DomainOptional_WithoutValue_Fail() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSD, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Attribute attrMandatory requires a value", logger.getErrs().get(0).getEventMsg());
	}
	
	// besteht innerhalb einer Klasse eine referenz auf eine DOMAIN,
	// zu einem MANDATORY Wert, der erstellt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrOptional_DomainMandatory_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSE, OID1);
		iomObj1.setattrvalue("attrMandatory", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// besteht innerhalb einer Klasse eine referenz auf eine DOMAIN,
	// zu einem MANDATORY Wert, der nicht erstellt wird,
	// soll eine Fehlermeldung ausgegeben werden.
	@Ignore("Mandatory inside Domain is not yet implemented.")
	@Test
	public void class_attrOptional_DomainMandatory_WithoutValue_Fail() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSE, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Attribute attrOptional requires a value", logger.getErrs().get(0).getEventMsg());
	}
	
	// wenn ein Attribute auf eine Domain referenziert und das Attribute,
	// innerhalb der Domain auf ein OPTIONALES Attribute Extended,
	// zudem an MANDATORY zugewiesen wird und der Wert gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void class_attrOptional_ExtendedDomainMandatory__WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSF, OID1);
		iomObj1.setattrvalue("attrMandatory", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// wenn ein Attribute auf eine Domain referenziert und das Attribute,
	// innerhalb der Domain auf ein OPTIONALES Attribute Extended,
	// zudem an MANDATORY zugewiesen wird und der Wert nicht gesetzt wird,
	// soll eine Fehlermeldung ausgegeben werden.
	@Ignore("Mandatory inside Domain is not yet implemented.")
	@Test
	public void class_attrOptional_ExtendeDomainMandatory_WithoutValue_Fail() {
		Iom_jObject iomObj1=new Iom_jObject(DIRECT_CLASSF, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_ATTRMULTIPLICITY,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==1);
		assertEquals("Attribute attrMandatory requires a value", logger.getErrs().get(0).getEventMsg());
	}
	
	// wenn in einer View, eine indirekte referenz ueber die Basisklasse
	// auf einen OPTIONALEN Wert erstellt wird und dieser gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void view_allOfOptional_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(VIEWTOPIC_VIEW1, OID1);
		iomObj1.setattrvalue("attrOptional", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_VIEWTOPIC,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// wenn in einer View, eine indirekte referenz ueber die Basisklasse
	// auf einen OPTIONALEN Wert erstellt wird und dieser nicht gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void view_allOfOptional_WithoutValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(VIEWTOPIC_VIEW1, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_VIEWTOPIC,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// wenn in einer View, eine indirekte referenz ueber die Basisklasse
	// auf einen OPTIONALEN Wert via Base-> erstellt wird und dieser gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void view_attrOptional_WithValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(VIEWTOPIC_VIEW2, OID1);
		iomObj1.setattrvalue("attrOptionalZusatz", "test");
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_VIEWTOPIC,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
	
	// wenn in einer View, eine indirekte referenz ueber die Basisklasse
	// auf einen OPTIONALEN Wert via Base-> erstellt wird und dieser nicht gesetzt wird,
	// soll keine Fehlermeldung ausgegeben werden.
	@Test
	public void view_attrOptional_WithoutValue_Ok() {
		Iom_jObject iomObj1=new Iom_jObject(VIEWTOPIC_VIEW2, OID1);
		ValidationConfig modelConfig=new ValidationConfig();
		LogCollector logger=new LogCollector();
		LogEventFactory errFactory=new LogEventFactory();
		Settings settings=new Settings();
		Validator validator=new Validator(td, modelConfig,logger,errFactory,settings);
		validator.validate(new StartTransferEvent());
		validator.validate(new StartBasketEvent(TOPIC_VIEWTOPIC,BID1));
		validator.validate(new ObjectEvent(iomObj1));
		validator.validate(new EndBasketEvent());
		validator.validate(new EndTransferEvent());
		// Asserts
		assertTrue(logger.getErrs().size()==0);
	}
}
