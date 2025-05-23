/* This file is part of the iox-ili project.
 * For more information, please see <http://www.eisenhutinformatik.ch/iox-ili/>.
 *
 * Copyright (c) 2006 Eisenhut Informatik AG
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */
package ch.interlis.iom_j.xtf.impl;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import ch.ehi.basics.logging.EhiLogger;
import ch.interlis.ili2c.generator.XSD24Generator;
import ch.interlis.iom.*;
import ch.interlis.iox.IoxException;
import ch.interlis.iom_j.Iom_jObject;
import ch.interlis.iom_j.ViewableProperties;
import ch.interlis.iom_j.ViewableProperty;
import ch.interlis.iom_j.xtf.OidSpace;
import ch.interlis.iom_j.xtf.XtfModel;
import ch.interlis.iom_j.xtf.XtfStartTransferEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

	/** Writer of an INTERLIS 2 transfer file.
	 * @author ceis
	 */
    public class Xtf24WriterAlt extends AbstractXtfWriterAlt
    {
    	// TODO move ele names to XSD Generator
        private static final String MODEL_ELE = "model";
		private static final String COMMENT_ELE = "comment";
		private static final String SENDER_ELE = "sender";
		private static final String MODELS_ELE = "models";
		private static final String DATASECTION_ELE = "datasection";
		private static final String TRANSFER_ELE = "transfer";
		private static final String HEADERSECTION_ELE = "headersection";
		
        private ViewableProperties mapping = null;
        private java.util.HashMap nameMapping=null;
        public static final String xsiNs="http://www.w3.org/2001/XMLSchema-instance";
        // shortcut
        private static final String iliNs=XSD24Generator.INTERLIS_XMLNS;
        private static final String geomNs=XSD24Generator.GEOM_XMLNS;        
        /** Creates a new writer.
         * @param buffer Writer to write to
         * @param mapping1 model of data to write
         * @throws IoxException
         */
        public Xtf24WriterAlt(java.io.OutputStreamWriter buffer,ViewableProperties mapping1)
		throws IoxException
        { 
            mapping = mapping1;
            this.nameMapping=mapping1.getXtf24nameMapping();
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			// TODO outputFactory.setProperty(com.ctc.wstx.api.WstxOutputProperties.P_OUTPUT_ESCAPE_CR,Boolean.FALSE);
			//if (useRepairing) {
			outputFactory.setProperty("javax.xml.stream.isRepairingNamespaces", Boolean.TRUE);
			//}
			try{
				xout = outputFactory.createXMLStreamWriter(new java.io.BufferedWriter(buffer));
				// buffer.getEncoding() may return a historial name like "UTF8", translate this 
				// to a canonical one like "UTF-8"
				String encoding=java.nio.charset.Charset.forName(buffer.getEncoding()).name();
				xout.writeStartDocument(encoding,"1.0");
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}

        }
        @Override
        public void writeStartTransfer(String sender, String comment,XtfModel models[])
		throws IoxException
		{
        	writeStartTransfer(sender, comment, models, null);
		}
        @Override
        public void writeStartTransfer(String sender, String comment,XtfModel models[],XtfStartTransferEvent e)
		throws IoxException
        {
			try{
				//xout.setNamespace(iliNs);
				xout.writeStartElement(iliNs,TRANSFER_ELE);
				xout.writeNamespace("ili",iliNs);
				xout.writeNamespace("geom", XSD24Generator.GEOM_XMLNS);
				xout.writeNamespace("xsi", xsiNs);
				if(models==null || models.length==0){
					throw new IoxException("no models given");
				}
				for(int i=0;i<models.length;i++){
					String modelName=models[i].getName();
					xout.writeNamespace(modelName, getXmlNs(modelName));
				}
				newline();
				// HEADERSECTION
				{
					xout.writeStartElement(iliNs,HEADERSECTION_ELE);
					if(sender==null || sender.length()==0){
						sender="IOX";
					}
					if(true){
						xout.writeStartElement(iliNs,MODELS_ELE);
						if(models==null || models.length==0){
							throw new IoxException("no models given");
						}
						for(int i=0;i<models.length;i++){
							XtfModel model=models[i];
							writeElementStringOptional(iliNs,MODEL_ELE,model.getName());							
						}
						xout.writeEndElement();
					}
					writeElementStringOptional(iliNs,SENDER_ELE,sender);
					writeElementStringOptional(iliNs,COMMENT_ELE,comment);
					xout.writeEndElement();
					newline();
				}

				// DATASECTION
				xout.writeStartElement(iliNs,DATASECTION_ELE);
				
				newline();
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}

        }
        @Override
        public void writeEndTransfer()
		throws IoxException
        {
			try{
				xout.writeEndElement(); // DATASECTION
				newline();
				xout.writeEndElement(); // TRANSFER
				xout.writeEndDocument();
				xout.flush();
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
            xout=null;
        }
        @Override
        public void writeStartBasket(String type, String bid)
		throws IoxException
        {
            writeStartBasket(type,bid,IomConstants.IOM_COMPLETE, IomConstants.IOM_FULL,null,null,null,null);
        }
        @Override
        public void writeStartBasket(String type,String bid,int consistency, int kind,String startstate,String endstate, String[] topicv,String domains)
		throws IoxException
        {
			try{
				xout.writeStartElement(getXmlNs(type),getXmlName(type));
				xout.writeAttribute(iliNs,XSD24Generator.BID_ATTR, makeOid(bid));
				writeAttributeStringOptional(iliNs,XSD24Generator.KIND_ATTR, encodeBasketKind(kind));
				if (kind != IomConstants.IOM_FULL)
				{
					if(kind==IomConstants.IOM_UPDATE){
						xout.writeAttribute(iliNs,XSD24Generator.STARTSTATE_ATTR, startstate);
					}
					xout.writeAttribute(iliNs,XSD24Generator.ENDSTATE_ATTR, endstate);
				}
				writeAttributeStringOptional(iliNs,XSD24Generator.CONSISTENCY_ATTR, encodeConsistency(consistency));
                writeAttributeStringOptional(iliNs,"domains", domains);
				newline();
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}

        }
        @Override
        public void writeEndBasket()
		throws IoxException
        {
			try{
				xout.writeEndElement();
				newline();
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
        }
        @Override
        public void writeObject(IomObject obj)
		throws IoxException
        {
			try{
				String type=obj.getobjecttag();
				xout.writeStartElement(getXmlNs(type),getXmlName(type));
				writeAttributeStringOptional(iliNs,XSD24Generator.TID_ATTR, makeOid(obj.getobjectoid()));
				writeAttributeStringOptional(iliNs,XSD24Generator.OPERATION_ATTR, encodeOperation(obj.getobjectoperation()));
				writeObjAttrs(obj);
				xout.writeEndElement();
				newline();
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
        }
        private void writeObjAttrs(IomObject obj)
		throws IoxException
        {
            String tag = obj.getobjecttag();
            // class known?
            if (mapping!=null && mapping.existsClass(tag))
            {
                ViewableProperty[] attrv = mapping.getClassVProperties(tag);
                for (int i = 0; i < attrv.length; i++)
                {
                    writeObjAttr(obj, attrv[i]);
                }
            }
            else
            {
                // unknown class; dump all attributes in undefined order
                // new unknown class?
                if (!unkClsv.contains(tag))
                {
                    EhiLogger.logError("unknown class " + tag);
                    // add it to list of unknown classes
                    unkClsv.add(tag);
                }
                for (int i = 0; i < obj.getattrcount(); i++)
                {
                    writeObjAttr(obj, new ViewableProperty(obj.getattrname(i)));
                }
            }
        }
        private void writeObjAttr(IomObject obj, ViewableProperty attr)
		throws IoxException
        {
        	String xmlns_attr=getXmlNs(obj.getobjecttag());
        	String baseAttrInClass=attr.getBaseDefInClass();
        	if(baseAttrInClass!=null){
            	xmlns_attr=getXmlNs(baseAttrInClass);
        	}
        	String iliAttrName=attr.getName();
			try{
				int valueCount=obj.getattrvaluecount(iliAttrName);
				if(valueCount>0){
					String val=obj.getattrprim(iliAttrName,0);
					// not a primitive?
					if(val==null){
						IomObject child=obj.getattrobj(iliAttrName,0);
						if (child != null)
						{
							// some special cases
							if (child.getobjecttag().equals(Iom_jObject.COORD))
							{
								// COORD
								xout.writeStartElement(xmlns_attr,iliAttrName);
								writeCoord(child);
								xout.writeEndElement(/*attr*/);
								if (valueCount > 1)
								{
									throw new IoxException("max one COORD value allowed ("+iliAttrName+")");
								}
							}
							else if (child.getobjecttag().equals(Iom_jObject.MULTICOORD)) {
								xout.writeStartElement(xmlns_attr, iliAttrName);
								writeMultiCoord(child);
								xout.writeEndElement(/*attr*/);
								if (valueCount > 1)
								{
									throw new IoxException("max one MULTICOORD value allowed (" + iliAttrName + ")");
								}
							}
							else if (child.getobjecttag().equals(Iom_jObject.POLYLINE))
							{
								// POLYLINE
								String xmlAttrName=iliAttrName;
								if(iliAttrName.startsWith("_itf_geom_")){ // FIXME use constant from ModelUtilities in ITF writer
									xmlAttrName="_geometry";
								}
								xout.writeStartElement(xmlns_attr,xmlAttrName);
								writePolyline(child,false);
								xout.writeEndElement(/*attr*/);
								if (valueCount > 1)
								{
									throw new IoxException("max one POLYLINE value allowed ("+iliAttrName+")");
								}
							}
							else if (child.getobjecttag().equals(Iom_jObject.MULTIPOLYLINE))
							{
								xout.writeStartElement(xmlns_attr, iliAttrName);
								writeMultiPolyline(child);
								xout.writeEndElement();
								if (valueCount > 1)
								{
									throw new IoxException("max one MULTIPOLYLINE value allowed ("+iliAttrName+")");
								}
							}
							else if (child.getobjecttag().equals(Iom_jObject.MULTISURFACE))
							{
								// MULTISURFACE
								xout.writeStartElement(xmlns_attr,iliAttrName);
								writeSurface(child, attr.isMultiSurfaceOrAreaType());
								xout.writeEndElement(/*attr*/);
								if (valueCount > 1)
								{
									throw new IoxException("max one MULTISURFACE value allowed ("+iliAttrName+")");
								}
							}
							else
							{
								// normal case
								String aref = child.getobjectrefoid();
								boolean isRef = aref != null ? true : false;
								// Reference-attribute or Role or EmbeddedLink?
								if (isRef)
								{
									String orderpos = null;
									if (child.getobjectreforderpos() > 0)
									{
										orderpos = Long.toString(child.getobjectreforderpos());
									}
									xout.writeStartElement(xmlns_attr,iliAttrName);
									xout.writeAttribute(iliNs,XSD24Generator.REF_ATTR, makeOid(aref));
									writeAttributeStringOptional(iliNs,XSD24Generator.ORDER_POS_ATTR, orderpos);
									if (child.getattrcount() > 0)
									{
										String structType=child.getobjecttag();
										xout.writeStartElement(getXmlNs(structType),getXmlName(structType));
										writeObjAttrs(child);
										xout.writeEndElement(/*child*/);
									}
									xout.writeEndElement(/*attr*/);
									if (valueCount > 1)
									{
									    if(child.getattrcount() > 0) {
	                                        throw new IoxException("max one reference value allowed ("+iliAttrName+")");
									    }else {
									        // BAG/LIST OF ReferenceType
					                        for (int i = 1; i < obj.getattrvaluecount(iliAttrName); i++) {
					                            child=obj.getattrobj(iliAttrName,i);
				                                aref = child.getobjectrefoid();
			                                    xout.writeStartElement(xmlns_attr,iliAttrName);
			                                    xout.writeAttribute(iliNs,XSD24Generator.REF_ATTR, makeOid(aref));
			                                    xout.writeEndElement(/*attr*/);
					                        }
									        
									    }
									}
								}
								else
								{
									// struct
									xout.writeStartElement(xmlns_attr,iliAttrName);
									int valuei = 0;
									while (true)
									{
										String structType=child.getobjecttag();
										xout.writeStartElement(getXmlNs(structType),getXmlName(structType));
										writeObjAttrs(child);
										xout.writeEndElement(/*child*/);
										valuei++;
										if (valuei >= valueCount)
										{
											break;
										}
										child = obj.getattrobj(iliAttrName, valuei);
									}
									xout.writeEndElement(/*attr*/);
								}
							}
						}

					}else{
						for (int i = 0; i < obj.getattrvaluecount(iliAttrName); i++) {
							val = obj.getattrprim(iliAttrName, i);
							writeElementStringOptional(xmlns_attr,iliAttrName,val);
						}
					}
				}
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
        }
        /** writes a coord value or a coord segment.
         */
        private void writeCoord(IomObject obj)
		throws IoxException
        {
        /*
             <geom:coord>
                 <geom:c1>NumericConst</geom:c1>
                 <geom:c2>NumericConst</geom:c2>
                 [ <geom:c3>NumericConst</geom:c3> ]
             </geom:coord>
        */
		try{
			xout.writeStartElement(geomNs, "coord");
			String c1=obj.getattrprim(Iom_jObject.COORD_C1,0);
			writeElementStringOptional(geomNs,"c1",c1);
			String c2=obj.getattrprim(Iom_jObject.COORD_C2,0);
			if(c2!=null){
				writeElementStringOptional(geomNs,"c2",c2);
				String c3=obj.getattrprim(Iom_jObject.COORD_C3,0);
				if(c3!=null){
					writeElementStringOptional(geomNs,"c3",c3);
				}
			}
			xout.writeEndElement(/*COORD*/);
		}catch(XMLStreamException ex){
			throw new IoxException(ex);
		}

        }

        private void writeMultiCoord(IomObject obj)
                throws IoxException
        {
        /*
             <geom:multicoord>
             (* CoordValue *)
             </geom:multicoord>.
        */
            try {
                xout.writeStartElement(geomNs,"multicoord");
                for (int coordi = 0; coordi < obj.getattrvaluecount(Iom_jObject.MULTICOORD_COORD); coordi++) {
                    IomObject coord = obj.getattrobj(Iom_jObject.MULTICOORD_COORD, coordi);
                    writeCoord(coord);
                }
                xout.writeEndElement();
            }catch(XMLStreamException ex){
                throw new IoxException(ex);
            }
        }

        /** writes a arc segment value.
         */
        private void writeArc(IomObject obj)
		throws IoxException
        {
        /*
             object: ARC
               C1
                 103.0
               C2
                 403.0
               A1
                 104.0
               A2
                 404.0
	        <COORD><C1>103.0</C1><C2>403.0</C2><A1>104.0</A1><A2>404.0</A2></COORD>
        */
		try{
			xout.writeStartElement(geomNs,"arc");
			String a1=obj.getattrprim(Iom_jObject.ARC_A1,0);
			writeElementStringOptional(geomNs,"a1",a1);
			String a2=obj.getattrprim(Iom_jObject.ARC_A2,0);
			writeElementStringOptional(geomNs,"a2",a2);
			String c1=obj.getattrprim(Iom_jObject.COORD_C1,0);
			writeElementStringOptional(geomNs,"c1",c1);
			String c2=obj.getattrprim(Iom_jObject.COORD_C2,0);
			writeElementStringOptional(geomNs,"c2",c2);
			String c3=obj.getattrprim(Iom_jObject.COORD_C3,0);
			if(c3!=null){
				writeElementStringOptional(geomNs,"c3",c3);
			}
			String r=obj.getattrprim(Iom_jObject.ARC_R,0);
			if(r!=null){
				writeElementStringOptional(geomNs,"a",r);
			}
			xout.writeEndElement(/*ARC*/);
		}catch(XMLStreamException ex){
			throw new IoxException(ex);
		}

        }
        /** writes a polyline value.
         */
        private void writePolyline(IomObject obj,boolean noPolylineTag)
		throws IoxException
        {
        /*
             object: POLYLINE [INCOMPLETE]
               lineattr
                 object: Model.Topic.LineAttr
                   attr00
                     11
               sequence // if incomplete; multi sequence values
                 object: SEGMENTS
                   segment
                     object: COORD
                       C1
                         102.0
                       C2
                         402.0
                   segment
                     object: ARC
                       C1
                         103.0
                       C2
                         403.0
                       A1
                         104.0
                       A2
                         404.0
                   segment
                     object: Model.SplineParam
                       SegmentEndPoint
                         object: COORD
                           C1
                             103.0
                           C2
                             403.0
                       p0
                         1.0
                       p1
                         2.0

		        <POLYLINE>
			        <LINEATTR>
				        <Model.Topic.LineAttr>
					        <attr00>11</attr00>
				        </Model.Topic.LineAttr>
			        </LINEATTR>
			        <COORD>
				        <C1>101.0</C1>
				        <C2>401.0</C2>
			        </COORD>
			        <COORD>
				        <C1>102.0</C1>
				        <C2>402.0</C2>
			        </COORD>
			        <Model.SplineParam>
				        <SegmentEndPoint>
					        <COORD>
						        <C1>103.0</C1>
						        <C2>403.0</C2>
					        </COORD>
				        </SegmentEndPoint>
				        <p0>1.0</p0>
				        <p1>2.0</p1>
			        </Model.SplineParam>
		        </POLYLINE>
        */
		try{
			if(!noPolylineTag){
				xout.writeStartElement(geomNs,"polyline");
			}
			boolean clipped=obj.getobjectconsistency()==IomConstants.IOM_INCOMPLETE;
			for(int sequencei=0;sequencei<obj.getattrvaluecount(Iom_jObject.POLYLINE_SEQUENCE);sequencei++){
				if(clipped){
					xout.writeStartElement(iliNs,"CLIPPED"); // FIXME use multi type
				}else{
					// an unclipped polyline should have only one sequence element
					if(sequencei>0){
						throw new IllegalArgumentException("unclipped polyline with multi 'sequence' elements");
					}
				}
				IomObject sequence=obj.getattrobj(Iom_jObject.POLYLINE_SEQUENCE,sequencei);
				for(int segmenti=0;segmenti<sequence.getattrvaluecount(Iom_jObject.SEGMENTS_SEGMENT);segmenti++){
					IomObject segment=sequence.getattrobj(Iom_jObject.SEGMENTS_SEGMENT,segmenti);
					if(segment.getobjecttag().equals(Iom_jObject.COORD)){
						// COORD
						writeCoord(segment);
					}else if(segment.getobjecttag().equals(Iom_jObject.ARC)){
						// ARC
						writeArc(segment);
					}else{
						// custom line form
						String structType=segment.getobjecttag();
						xout.writeStartElement(getXmlNs(structType),getXmlName(structType));
						writeObjAttrs(segment);
						xout.writeEndElement(/*segment*/);
					}

				}
				if(clipped){
					xout.writeEndElement(/*CLIPPED*/);
				}
			}
			if(!noPolylineTag){
				xout.writeEndElement(/*POLYLINE*/);
			}
		}catch(XMLStreamException ex){
			throw new IoxException(ex);
		}
        }

		/** writes a mulipolyline value.
		 */
		private void writeMultiPolyline(IomObject obj)
		throws IoxException
		{
			try {
				xout.writeStartElement(geomNs,"multipolyline");
				for(int polylinei=0;polylinei<obj.getattrvaluecount(Iom_jObject.MULTIPOLYLINE_POLYLINE);polylinei++){
					IomObject polyline=obj.getattrobj(Iom_jObject.MULTIPOLYLINE_POLYLINE,polylinei);
					writePolyline(polyline, false);
				}
				xout.writeEndElement();
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
		}

        /** writes a surface value.
         */
        private void writeSurface(IomObject obj, boolean isMultiSurfaceOrAreaType)
		throws IoxException
        {
        /*
             object: MULTISURFACE [INCOMPLETE]
               surface // if incomplete; multi surface values
                 object: SURFACE
                   boundary
                     object: BOUNDARY
                       polyline
                         object: POLYLINE

        	
	        <SURFACE>
		        <BOUNDARY>
			        <POLYLINE .../>
			        <POLYLINE .../>
		        </BOUNDARY>
		        <BOUNDARY>
			        <POLYLINE .../>
			        <POLYLINE .../>
		        </BOUNDARY>
	        </SURFACE>
        */
		try{
			int surfaceCount = obj.getattrvaluecount(Iom_jObject.MULTISURFACE_SURFACE);
			boolean isIncomplete=obj.getobjectconsistency()==IomConstants.IOM_INCOMPLETE;

			if (surfaceCount > 1 && !(isMultiSurfaceOrAreaType || isIncomplete)){
				throw new IllegalArgumentException("surface with multiple 'surface' elements");
			}

			boolean doMultisurface = isMultiSurfaceOrAreaType || surfaceCount > 1;
			if (doMultisurface) {
				xout.writeStartElement(geomNs, "multisurface");
			}

			for(int surfacei=0;surfacei<surfaceCount;surfacei++){
				xout.writeStartElement(geomNs,"surface");
				IomObject surface=obj.getattrobj(Iom_jObject.MULTISURFACE_SURFACE,surfacei);
				for(int boundaryi=0;boundaryi<surface.getattrvaluecount(Iom_jObject.SURFACE_BOUNDARY);boundaryi++){
					IomObject boundary=surface.getattrobj(Iom_jObject.SURFACE_BOUNDARY,boundaryi);
					xout.writeStartElement(geomNs,boundaryi==0?"exterior":"interior");
					for(int polylinei=0;polylinei<boundary.getattrvaluecount(Iom_jObject.BOUNDARY_POLYLINE);polylinei++){
						IomObject polyline=boundary.getattrobj(Iom_jObject.BOUNDARY_POLYLINE,polylinei);
						writePolyline(polyline,false);
					}
					xout.writeEndElement(/*BOUNDARY*/);
				}
				xout.writeEndElement(/*SURFACE*/);
			}

			if (doMultisurface) {
				xout.writeEndElement();
			}
		}catch(XMLStreamException ex){
			throw new IoxException(ex);
		}
        }

        private void writeElementStringOptional(String ns,String name, String value)
		throws IoxException
        {
			try{
				if(value!=null){
					String v=value.trim();
					if(v.length()>0){
						xout.writeStartElement(ns,name);
						xout.writeCharacters(v);
						xout.writeEndElement();
					}
				}
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
        }
        private void writeAttributeStringOptional(String ns,String name, String value)
		throws IoxException
        {
			try{
				if (value != null)
				{
					String v = value.trim();
					if (v.length() > 0)
					{
						if(ns!=null){
							xout.writeAttribute(ns,name, v);
						}else{
							xout.writeAttribute(name, v);
						}
					}
				}
			}catch(XMLStreamException ex){
				throw new IoxException(ex);
			}
        }
        /** gets the xml representaion of a consistency value.
         */
        private static String encodeConsistency(int consistency)
        {
	        String ret=null;
	        switch(consistency){
                case IomConstants.IOM_INCOMPLETE:
                ret=XSD24Generator.CONSISTENCY_ATTR_INCOMPLETE;
		        break;
            case IomConstants.IOM_INCONSISTENT:
            	// ignore it
                ret=null;
		        break;
            case IomConstants.IOM_ADAPTED:
                // ignore it
                ret=null;
		        break;
            case IomConstants.IOM_COMPLETE:
	        default:
                ret=null;
		        break;
	        }
	        return ret;
        }

        /** gets the xml representaion of a basket-kind value.
         */
        private static String encodeBasketKind(int kind)
        {
	        String ret=null;
	        switch(kind){
	        case IomConstants.IOM_UPDATE:
                ret=XSD24Generator.KIND_ATTR_UPDATE;
		        break;
            case IomConstants.IOM_INITIAL:
                ret=XSD24Generator.KIND_ATTR_INITIAL;
		        break;
            case IomConstants.IOM_FULL:
	        default:
                ret=null;
		        break;
	        }
	        return ret;
        }

        /** gets the xml representaion of a operation value.
         */
        private static String encodeOperation(int ops)
        {
	        String ret=null;
	        switch(ops){
                case IomConstants.IOM_OP_UPDATE:
                ret=XSD24Generator.OPERATION_ATTR_UPDATE;
		        break;
            case IomConstants.IOM_OP_DELETE:
                ret=XSD24Generator.OPERATION_ATTR_DELETE;
		        break;
            case IomConstants.IOM_OP_INSERT:
	        default:
                ret=null;
		        break;
	        }
	        return ret;
        }
		private String getXmlNs(String type)
		{
			int pos=type.indexOf('.');
			String ret=XSD24Generator.XTF24_XMLNSBASE+"/"+(pos>=0?type.substring(0,pos):type);
			return ret;
		}
		private String getXmlName(String type)
		{
			if(!nameMapping.containsKey(type)){
				throw new IllegalArgumentException(type);
			}
			String ret=(String)nameMapping.get(type);
			return ret;
		}
        private String makeOid(String value)
        {
            if (value != null)
            {
                String v = value.trim();
                if (v.length() > 0)
                {
            		return v;
                }
            }
            return null;
        }
    }
