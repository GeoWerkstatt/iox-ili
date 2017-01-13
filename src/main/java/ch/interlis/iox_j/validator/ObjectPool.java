package ch.interlis.iox_j.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.Holder;

import ch.interlis.ili2c.metamodel.AssociationDef;
import ch.interlis.ili2c.metamodel.RoleDef;
import ch.interlis.ili2c.metamodel.Viewable;
import ch.interlis.ili2c.metamodel.ViewableTransferElement;
import ch.interlis.iom.IomObject;
import ch.interlis.iox.IoxLogging;
import ch.interlis.iox_j.logging.LogEventFactory;

public class ObjectPool {
	private IoxLogging errs=null;
	private LogEventFactory errFact=null;
	private boolean doItfOidPerTable;
	private HashMap<String,Object> tag2class;
	Map<String, Map<ObjectPoolKey, IomObject>> collectionOfBaskets = new HashMap<String, Map<ObjectPoolKey, IomObject>>();
	
	public ObjectPool(boolean doItfOidPerTable, IoxLogging errs, LogEventFactory errFact, HashMap<String,Object> tag2class){
		this.doItfOidPerTable = doItfOidPerTable;
		this.errFact = errFact;
		this.errs = errs;
		this.tag2class = tag2class;
	}
	public static String getAssociationId(IomObject iomObj, AssociationDef assocDef) {
		if(assocDef==null){
			throw new IllegalArgumentException("assocDef==null");
		}
		String tag=assocDef.getScopedName(null);
		String tid=null;
		Iterator<ViewableTransferElement> rolei=assocDef.getAttributesAndRoles2();
		String sep="";
		tid="";
		while(rolei.hasNext()){
			ViewableTransferElement prop=rolei.next();
			if(prop.obj instanceof RoleDef && !prop.embedded){
				String roleName=((RoleDef) prop.obj).getName();
				IomObject refObj=iomObj.getattrobj(roleName, 0);
				String ref=null;
				if(refObj!=null){
					ref=refObj.getobjectrefoid();
				}
				if(ref!=null){
					tid=tid+sep+ref;
					sep=":";
				}else{
			 		throw new IllegalStateException("REF required ("+tag+"/"+roleName+")");
				}
			}
		}
		return tid;
	}

	public void addObject(IomObject iomObj, HashMap<String,Object> tag2class, String currentBasketId){
		String oid = iomObj.getobjectoid();
		Object modelEle = tag2class.get(iomObj.getobjecttag());
		if(oid==null){
			oid=getAssociationId(iomObj, (AssociationDef)modelEle);
		}
		Viewable classValue = (Viewable) modelEle;
		ObjectPoolKey key = null;
		if(doItfOidPerTable){
			key=new ObjectPoolKey(oid, classValue, currentBasketId);
		} else {
			key=new ObjectPoolKey(oid, null, currentBasketId);
		}
		Map<ObjectPoolKey, IomObject> collectionOfObjects =null;
		if(collectionOfBaskets.containsKey(currentBasketId)){
			collectionOfObjects=collectionOfBaskets.get(currentBasketId);
		} else {
			collectionOfObjects=new HashMap<ObjectPoolKey, IomObject>();
			collectionOfBaskets.put(currentBasketId, collectionOfObjects);
		}
		if(collectionOfObjects.containsKey(key)){
			IomObject objectValue = collectionOfObjects.get(key);
			Object modelElement=tag2class.get(objectValue.getobjecttag());
			Viewable classValueOfKey= (Viewable) modelElement;
			errs.addEvent(errFact.logErrorMsg("The OID {0} of object '{1}' already exists in {2}.", oid, iomObj.toString(), classValueOfKey.toString()));
		} else {
			collectionOfObjects.put(key,iomObj);
		}
	}
	
	public Map getObjectsOfBasketId(String basketId){
		return collectionOfBaskets.get(basketId);
	}
	
	public Set<String> getBasketIds(){
		return collectionOfBaskets.keySet();
	}

	public IomObject getObject(String oid, ArrayList<Viewable> classes, Holder<String> retBasketId) {
		for(String basketId : collectionOfBaskets.keySet()){
			Map<ObjectPoolKey, IomObject> collectionOfObjects = collectionOfBaskets.get(basketId);
			if(doItfOidPerTable){
				for(Viewable aClass : classes){
					IomObject object = collectionOfObjects.get(new ObjectPoolKey(oid, aClass, basketId));
					if(object != null){
						retBasketId.value=basketId;
						return object;
					}
				}
			} else {
				for(Viewable aClass : classes){		
					IomObject object = collectionOfObjects.get(new ObjectPoolKey(oid, null, basketId));
					if(object != null){
						retBasketId.value=basketId;
						return object;
					}
				}
			}
		}
		retBasketId.value=null;
		return null;
	}
}