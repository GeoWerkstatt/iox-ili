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
package ch.interlis.iox_j.wkb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;

import ch.interlis.iom.IomConstants;
import ch.interlis.iom.IomObject;
import ch.interlis.iom_j.itf.impl.jtsext.geom.ArcSegment;
import ch.interlis.iox_j.jts.Iox2jtsException;

/** Utility to convert from INTERLIS to WKB geometry.
 * @author ceis
 * 
 * 
<point binary representation> ::=
	<byte order> <wkbpoint> [ <wkbpoint binary> ]

<linestring binary representation> ::=
	<byte order> <wkblinestring> [ <num> <wkbpoint binary>... ]

<circularstring binary representation> ::=
	<byte order> <wkbcircularstring> [ <num> <wkbpoint binary>... ]

<compoundcurve binary representation> ::=
	<byte order> <wkbcompoundcurve> [ <num> <wkbcurve binary>... ]

<curvepolygon binary representation> ::=
	<byte order> <wkbcurvepolygon> [ <num> <wkbring binary>... ]
	| <polygon binary representation>
	
<polygon binary representation> ::=
	<byte order> <wkbpolygon> [ <num> <wkblinearring binary>... ]
	| <triangle binary representation>

<triangle binary representation> ::=
	<byte order> <wkbtriangle>
	[ <wkbpoint binary> <wkbpoint binary> <wkbpoint binary> ]	

<wkbring binary> ::=
	<linestring binary representation>
	| <circularstring binary representation>
	| <compoundcurve binary representation>

<wkblinearring> ::= <num> <wkbpoint binary>...

<wkbcurve binary> ::=
	<linestring binary representation>
	| <circularstring binary representation>

<wkbpoint binary> ::= <wkbx> <wkby>	
 */
public class Iox2wkb {
    private int outputDimension = 2;
    private ByteArrayOutputStream os = null;
    private boolean asEWKB = true;

    public Iox2wkb(int outputDimension) {
        this(outputDimension, java.nio.ByteOrder.BIG_ENDIAN, true);
    }
    public Iox2wkb(int outputDimension, java.nio.ByteOrder byteOrder) {
        this(outputDimension, byteOrder, true);
    }
    public Iox2wkb(int outputDimension, java.nio.ByteOrder byteOrder, boolean asEWKB) {
        this.outputDimension = outputDimension;
        this.asEWKB=asEWKB;
        os = new ByteArrayOutputStream(byteOrder);

        if (outputDimension < 2 || outputDimension > 3)
          throw new IllegalArgumentException("Output dimension must be 2 or 3");
    }

	public static String bytesToHex(byte[] bytes)
	  {
	    StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < bytes.length; i++) {
	      byte b = bytes[i];
	      buf.append(toHexDigit((b >> 4) & 0x0F));
	      buf.append(toHexDigit(b & 0x0F));
	    }
	    return buf.toString();
	  }

	  private static char toHexDigit(int n)
	  {
	    if (n < 0 || n > 15)
	      throw new IllegalArgumentException("Nibble value out of range: " + n);
	    if (n <= 9)
	      return (char) ('0' + n);
	    return (char) ('A' + (n - 10));
	  }
	
	/** Converts a COORD to a JTS Coordinate.
	 * @param value INTERLIS COORD structure.
	 * @return JTS Coordinate.
	 * @throws Iox2wkbException
	 */
	public byte[] coord2wkb(IomObject value) 
	throws Iox2wkbException 
	{
		if(value==null){
			return null;
		}
	    try {
	        os.reset();
	        writeByteOrder();
	        writeGeometryType(WKBConstants.wkbPoint);
			writeCoord(value);
	      }
	      catch (IOException ex) {
	        throw new RuntimeException("Unexpected IO exception: " + ex.getMessage());
	      }
	      return os.toByteArray();
	}
	public byte[] multicoord2wkb(IomObject obj)
	throws Iox2wkbException
	{
		if(obj==null){
			return null;
		}
	    try {
			writeByteOrder();
			writeGeometryType(WKBConstants.wkbMultiPoint);
			int coordc=obj.getattrvaluecount(Wkb2iox.ATTR_COORD);
			os.writeInt(coordc);

			for(int coordi=0;coordi<coordc;coordi++){
				IomObject coord=obj.getattrobj(Wkb2iox.ATTR_COORD,coordi);
                Iox2wkb helper=new Iox2wkb(outputDimension,os.order(),asEWKB);
				os.write(helper.coord2wkb(coord));
			}
		} catch (IOException e) {
	        throw new RuntimeException("Unexpected IO exception: " + e.getMessage());
		}
		return os.toByteArray();
	}
	private void writeCoord(IomObject value) throws Iox2wkbException {
		String c1=value.getattrvalue("C1");
		String c2=value.getattrvalue("C2");
		String c3=value.getattrvalue("C3");
		double xCoord;
		try{
			xCoord = Double.parseDouble(c1);
		}catch(Exception ex){
			throw new Iox2wkbException("failed to read C1 <"+c1+">",ex);
		}
		double yCoord;
		try{
			yCoord = Double.parseDouble(c2);
		}catch(Exception ex){
			throw new Iox2wkbException("failed to read C2 <"+c2+">",ex);
		}
		double zCoord=0.0;
		if(outputDimension==3){
			if(c3!=null){
				try{
					zCoord = Double.parseDouble(c3);
				}catch(Exception ex){
					throw new Iox2wkbException("failed to read C3 <"+c3+">",ex);
				}
			}else{
				throw new Iox2wkbException("missing C3");
			}
		}
		writeCoord(xCoord,yCoord,zCoord);
	}

	private static Coordinate arcSupportingCoord2JTS(IomObject value) throws Iox2wkbException {
		String a1=value.getattrvalue("A1");
		String a2=value.getattrvalue("A2");
		double arcPt_re;
			try{
				arcPt_re = Double.parseDouble(a1);
			}catch(Exception ex){
				throw new Iox2wkbException("failed to read A1 <"+a1+">",ex);
			}
			double arcPt_ho;
			try{
				arcPt_ho = Double.parseDouble(a2);
			}catch(Exception ex){
				throw new Iox2wkbException("failed to read A2 <"+a2+">",ex);
			}
			return new Coordinate(arcPt_re, arcPt_ho);
	}

	/** Converts a COORD to a JTS Coordinate.
	 * @param value INTERLIS COORD structure.
	 * @return JTS Coordinate.
	 * @throws Iox2jtsException
	 */
	private static com.vividsolutions.jts.geom.Coordinate coord2JTS(IomObject value) 
	throws Iox2wkbException 
	{
		if(value==null){
			return null;
		}
		String c1=value.getattrvalue("C1");
		String c2=value.getattrvalue("C2");
		String c3=value.getattrvalue("C3");
		double xCoord;
		try{
			xCoord = Double.parseDouble(c1);
		}catch(Exception ex){
			throw new Iox2wkbException("failed to read C1 <"+c1+">",ex);
		}
		double yCoord;
		try{
			yCoord = Double.parseDouble(c2);
		}catch(Exception ex){
			throw new Iox2wkbException("failed to read C2 <"+c2+">",ex);
		}
		com.vividsolutions.jts.geom.Coordinate coord=null;
		if(c3==null){
			coord=new com.vividsolutions.jts.geom.Coordinate(xCoord, yCoord);
		}else{
			double zCoord;
			try{
				zCoord = Double.parseDouble(c3);
			}catch(Exception ex){
				throw new Iox2wkbException("failed to read C3 <"+c3+">",ex);
			}
			coord=new com.vividsolutions.jts.geom.Coordinate(xCoord, yCoord,zCoord);
		}
		return coord;
	}
	/** Converts a POLYLINE to a JTS CoordinateList.
	 * @param polylineObj INTERLIS POLYLINE structure
	 * @param isSurfaceOrArea true if called as part of a SURFACE conversion.
	 * @param p maximum stroke to use when removing ARCs
	 * @return JTS CoordinateList
	 * @throws Iox2wkbException
	 */
    public byte[] polyline2wkb(IomObject polylineObj,boolean isSurfaceOrArea,boolean asCompoundCurve,double p)
    throws Iox2wkbException
    {
        if(polylineObj==null){
            return null;
        }
        IomObject polylineObjs[]=new IomObject[] {polylineObj};
        return polyline2wkb(polylineObjs, isSurfaceOrArea, asCompoundCurve, p);
    }
	public byte[] polyline2wkb(IomObject polylineObjs[],boolean isSurfaceOrArea,boolean asCompoundCurve,double p)
	throws Iox2wkbException
	{

		List<PolylineCoordList> segments =  SegmentIoxPolylines(polylineObjs, asCompoundCurve, p, false);
		try {
			os.reset();
			if (asCompoundCurve) {
				writeByteOrder();
				writeGeometryType(WKBConstants.wkbCompoundCurve);
				os.writeInt(segments.size());
			}
			for (PolylineCoordList segment : segments){
				if (asCompoundCurve || !isSurfaceOrArea) {
					writeByteOrder();
					writeGeometryType(segment.getWkbType());
				}
				os.writeInt(segment.size());
				for (Coordinate c : segment) {
					writeCoord(c);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IO exception: " + e.getMessage());
		}

		return os.toByteArray();
	}

	private List<PolylineCoordList> SegmentIoxPolylines(IomObject polylineObjs[], boolean asCompoundCurve, double p, boolean repairTouchingLine)
	throws Iox2wkbException {
		List<PolylineCoordList> resultSegments = new ArrayList<PolylineCoordList>();
		int nextRingId = 0;

		for (IomObject polylineObj : polylineObjs) {
			if (polylineObj == null) {
				continue;
			}

			for(int sequencei=0; sequencei<polylineObj.getattrvaluecount("sequence"); sequencei++) {
				IomObject sequence=polylineObj.getattrobj("sequence",sequencei);
				int segmentc=sequence.getattrvaluecount("segment");

				PolylineCoordList currentSegment = new PolylineCoordList();
				currentSegment.setRingId(nextRingId++);
				resultSegments.add(currentSegment);


				for(int segmenti=0; segmenti<segmentc; segmenti++){
					IomObject segment = sequence.getattrobj("segment", segmenti);
					Coordinate segmentCoordinate = coord2JTS(segment);

					// Arc / Straight switch needed?
					if(asCompoundCurve && ((segment.getobjecttag().equals("COORD") && !currentSegment.trySetWkbType(WKBConstants.wkbLineString))
							|| (segment.getobjecttag().equals("ARC") && !currentSegment.trySetWkbType(WKBConstants.wkbCircularString))))
					{
						Coordinate lastPoint = currentSegment.get(currentSegment.size()-1);
						PolylineCoordList nextSegment = new PolylineCoordList();
						nextSegment.setRingId(currentSegment.getRingId());
						nextSegment.add(lastPoint);
						resultSegments.add(nextSegment);
						currentSegment = nextSegment;
					}

					if (repairTouchingLine && currentSegment.contains(segmentCoordinate) && currentSegment.getPosition(segmentCoordinate) != 0){
						PolylineCoordList splitLine = currentSegment.splitTailAt(segmentCoordinate);
						splitLine.setRingId(nextRingId++);
						if (splitLine.getWkbType() == WKBConstants.wkbCircularString){
							splitLine.add(arcSupportingCoord2JTS(segment));
						}
						splitLine.add(segmentCoordinate);
						resultSegments.add(splitLine);
					}

					if(segment.getobjecttag().equals("COORD") && currentSegment.trySetWkbType(WKBConstants.wkbLineString)) {
						currentSegment.add(segmentCoordinate);
					}
					else if (segment.getobjecttag().equals("ARC") && asCompoundCurve && currentSegment.trySetWkbType(WKBConstants.wkbCircularString))
					{
						currentSegment.add(arcSupportingCoord2JTS(segment));
						currentSegment.add(segmentCoordinate);
					}
					else if(segment.getobjecttag().equals("ARC") && currentSegment.trySetWkbType(WKBConstants.wkbLineString)){
						if(p==0.0){
							currentSegment.add(arcSupportingCoord2JTS(segment));
							currentSegment.add(segmentCoordinate);
						} else {
							Coordinate lastCoordinate = currentSegment.get(currentSegment.size() - 1);
							ArcSegment arc = new ArcSegment(lastCoordinate, arcSupportingCoord2JTS(segment), segmentCoordinate, p);
							for (Coordinate c: arc.getCoordinates()) {
								currentSegment.add(c);
							}
						}
					}
					else {
						throw new Iox2wkbException("custom line form not supported");
					}
				}
			}
		}

		return resultSegments;
	}

	/** Converts a SURFACE to a JTS Polygon.
	 * @param obj INTERLIS SURFACE structure
	 * @param strokeP maximum stroke to use when removing ARCs
	 * @return JTS Polygon
	 * @throws Iox2wkbException
	 */
	@Deprecated
	public byte[] surface2wkb(IomObject obj,boolean asCurvePolygon,double strokeP)
	throws Iox2wkbException
	{
		return surface2wkb(obj, asCurvePolygon, strokeP, true);
	}

	public byte[] surface2wkb(IomObject obj,boolean asCurvePolygon,double strokeP, boolean repairTouchingLine) //SurfaceOrAreaType type)
	throws Iox2wkbException
	{
		if(obj==null){
			return null;
		}

		if (obj.getobjectconsistency() == IomConstants.IOM_INCOMPLETE) {
			throw new Iox2wkbException("clipped surface not supported");
		}

		List<IomObject> polylines = new ArrayList<IomObject>();
		for (int surfacei = 0; surfacei < obj.getattrvaluecount("surface"); surfacei++) {
			if (surfacei > 0) {
				throw new Iox2wkbException("unclipped surface with multi 'surface' elements");
			}
			IomObject surface = obj.getattrobj("surface", surfacei);
			int boundaryc = surface.getattrvaluecount("boundary");

			for (int boundaryi = 0; boundaryi < boundaryc; boundaryi++) {
				IomObject boundary = surface.getattrobj("boundary", boundaryi);
				int polylinec = boundary.getattrvaluecount("polyline");
				for (int polylinei = 0; polylinei < polylinec; polylinei++){
					IomObject polyline = boundary.getattrobj("polyline", polylinei);
					if (polyline.getattrobj("lineattr", 0) != null) {
						throw new Iox2wkbException("Lineattributes not supported");
					}
					if (polyline.getobjectconsistency() == IomConstants.IOM_INCOMPLETE) {
						throw new Iox2wkbException("clipped polyline not supported");
					}
					polylines.add(polyline);
				}
			}
		}

		List<PolylineCoordList> segments = new Iox2wkb(outputDimension,os.order(),asEWKB)
				.SegmentIoxPolylines(polylines.toArray(new IomObject[0]), asCurvePolygon, strokeP, repairTouchingLine);

		Map<Integer, List<PolylineCoordList>> ringGroupes = groupSegmentsByRingId(segments);
	    try {
			os.reset();
			writeByteOrder();
			writeGeometryType(asCurvePolygon ? WKBConstants.wkbCurvePolygon : WKBConstants.wkbPolygon);
			os.writeInt(ringGroupes.keySet().size());

			for (Map.Entry<Integer, List<PolylineCoordList>> entry: ringGroupes.entrySet()) {
				if (asCurvePolygon){
					writeByteOrder();
					writeGeometryType(WKBConstants.wkbCompoundCurve);
					os.writeInt(entry.getValue().size());
				}
				for (PolylineCoordList segment : entry.getValue()){
					if (asCurvePolygon) {
						writeByteOrder();
						writeGeometryType(segment.getWkbType());
					}
					os.writeInt(segment.size());
					for (Coordinate c : segment) {
						writeCoord(c);
					}
				}
			}
		} catch (IOException e) {
	        throw new RuntimeException("Unexpected IO exception: " + e.getMessage());
		}
		return os.toByteArray();
	}

	private HashMap<Integer, List<PolylineCoordList>> groupSegmentsByRingId(List<PolylineCoordList> segments) {
		HashMap<Integer, List<PolylineCoordList>> result = new HashMap<Integer, List<PolylineCoordList>>();

		for (PolylineCoordList segment : segments){
			if (!result.containsKey(segment.getRingId())) {
				List<PolylineCoordList> list = new ArrayList<PolylineCoordList>();
				list.add(segment);

				result.put(segment.getRingId(), list);
			} else {
				result.get(segment.getRingId()).add(segment);
			}
		}
		return result;
	}

	@Deprecated
	public byte[] multisurface2wkb(IomObject obj,boolean asCurvePolygon,double strokeP)
	throws Iox2wkbException
	{
		return multisurface2wkb(obj, asCurvePolygon, strokeP, true);
	}

	public byte[] multisurface2wkb(IomObject obj,boolean asCurvePolygon,double strokeP, boolean repairTouchingLine) //SurfaceOrAreaType type)
	throws Iox2wkbException
	{
		if(obj==null){
			return null;
		}
	    try {
			writeByteOrder();
			writeGeometryType(asCurvePolygon ?  WKBConstants.wkbMultiSurface : WKBConstants.wkbMultiPolygon);

			int surfacec=obj.getattrvaluecount("surface");
			os.writeInt(surfacec);

			for(int surfacei=0;surfacei<surfacec;surfacei++){
				IomObject surface=obj.getattrobj("surface",surfacei);
				IomObject iomSurfaceClone=new ch.interlis.iom_j.Iom_jObject("MULTISURFACE",null);
				iomSurfaceClone.addattrobj("surface",surface);
                Iox2wkb helper=new Iox2wkb(outputDimension,os.order(),asEWKB);
				os.write(helper.surface2wkb(iomSurfaceClone,asCurvePolygon,strokeP, repairTouchingLine));
			}
		} catch (IOException e) {
	        throw new RuntimeException("Unexpected IO exception: " + e.getMessage());
		}
		return os.toByteArray();
	}

	public byte[] multiline2wkb(IomObject obj,boolean asCurve,double strokeP)
	throws Iox2wkbException
	{
		if(obj==null){
			return null;
		}

		List<IomObject> polylines = new ArrayList<IomObject>();
		int polylinec=obj.getattrvaluecount(Wkb2iox.ATTR_POLYLINE);

		for(int polylinei=0;polylinei<polylinec;polylinei++){
			IomObject polyline = obj.getattrobj("polyline",polylinei);
			if (polyline.getobjectconsistency() == IomConstants.IOM_INCOMPLETE) {
				throw new Iox2wkbException("clipped polyline not supported");
			}
			polylines.add(polyline);
		}

		List<PolylineCoordList> segments = new Iox2wkb(outputDimension,os.order(),asEWKB)
				.SegmentIoxPolylines(polylines.toArray(new IomObject[0]), asCurve, strokeP, false);

		Map<Integer, List<PolylineCoordList>> lineGroupes = groupSegmentsByRingId(segments);
	    try {
			os.reset();
			writeByteOrder();
			writeGeometryType(asCurve ? WKBConstants.wkbMultiCurve : WKBConstants.wkbMultiLineString);
			os.writeInt(lineGroupes.keySet().size());

			for (Map.Entry<Integer, List<PolylineCoordList>> entry : lineGroupes.entrySet()) {
				if (asCurve) {
					writeByteOrder();
					writeGeometryType(WKBConstants.wkbCompoundCurve);
					os.writeInt(entry.getValue().size());
				}
				for (PolylineCoordList segment : entry.getValue()) {
					writeByteOrder();
					writeGeometryType(segment.getWkbType());
					os.writeInt(segment.size());
					for (Coordinate c : segment) {
						writeCoord(c);
					}
				}
			}
		} catch (IOException e) {
	        throw new RuntimeException("Unexpected IO exception: " + e.getMessage());
		}
		return os.toByteArray();
	}

	  private void writeByteOrder() throws IOException
	  {
	    if (os.order().equals(java.nio.ByteOrder.LITTLE_ENDIAN)){
	      os.write(WKBConstants.wkbNDR);
	    }else{
	      os.write(WKBConstants.wkbXDR);
	    }
	  }

	  private void writeGeometryType(int geometryType)
	      throws IOException
	  {
        int flagIncludeZ = asEWKB? WKBConstants.ewkbIncludesZ : WKBConstants.wkbIncludesZ;
        int flag3D = (outputDimension == 3) ? flagIncludeZ : 0;
        int typeInt = geometryType + flag3D;
	    os.writeInt(typeInt);
	  }

	  private void writeCoord(Coordinate coordinate){
		writeCoord(coordinate.x, coordinate.y, coordinate.z);
	  }

	  private void writeCoord(double xCoord,double yCoord,double zCoord)
	  {
		    os.writeDouble(xCoord);
		    os.writeDouble(yCoord);
		    if(outputDimension==3){
			    os.writeDouble(zCoord);
		    }
	  }
}
