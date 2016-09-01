package ch.ehi.iox.objpool.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.lang.ref.Reference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.ehi.iox.objpool.ObjectPoolManager;

public class ObjPoolImpl implements Map {

	private HashMap pool=new HashMap();
	private java.lang.ref.ReferenceQueue<ObjPoolEntry> writeQueue=new java.lang.ref.ReferenceQueue<ObjPoolEntry>(); 
	private RandomAccessFile outFile=null;
	private String outFilename=null;
	private ObjectPoolManager recman=null;
	public ObjPoolImpl(ObjectPoolManager objectPoolManager) {
		recman=objectPoolManager;
	}

	@Override
	public void clear() {
		pool.clear();
		if(outFile!=null){
			try {
				outFile.close();
				outFile=null;
				new java.io.File(outFilename).delete();
				outFilename=null;
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		writeQueue=new java.lang.ref.ReferenceQueue<ObjPoolEntry>();
	}

	@Override
	public boolean containsKey(Object key) {
		return pool.containsKey(key);
	}

	@Override
	public boolean containsValue(Object arg0) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Set entrySet() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		ObjPoolEntry entry=(ObjPoolEntry) pool.get(key);
		if(entry==null){
			return null;
		}
		Object ret=getRealObj(entry);
		recman.flushWriteQueues();
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return pool.isEmpty();
	}

	@Override
	public Set keySet() {
		return pool.keySet();
	}

	@Override
	public Object put(Object key, Object value) {
		// serialize value
        ObjectOutputStream     objStream;
        ByteArrayOutputStream  byteStream;
        byteStream = new ByteArrayOutputStream();
        try {
	        objStream  = new ObjectOutputStream (byteStream);
			objStream.writeObject (value);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		ObjPoolEntry entry=new ObjPoolEntry(byteStream.toByteArray(),value,writeQueue);
		// check for values to write
		recman.flushWriteQueues();
		return pool.put(key, entry);
	}

	public void flushWriteQueue() {
		ObjPoolEntry entry = (ObjPoolEntry) writeQueue.poll();
		while(entry!=null){
			if(!entry.isPhantom()){
				// not yet written?
				if(entry.getFilePos()==-1){
					// write to file
					long pos=-1;
					try {
						if(outFile==null){
							// open new file
							outFilename=ObjectPoolManager.getCacheTmpFilename();
							outFile=new RandomAccessFile(outFilename, "rw");
						}
						pos = outFile.length();
						outFile.seek(pos);
						outFile.write(entry.getByteBuffer());
					} catch (IOException e) {
						throw new IllegalStateException(e);
					}
					entry.setFilePos(pos);
				}
				entry.freeByteBuffer();
			}
			// get next entry
			entry = (ObjPoolEntry) writeQueue.poll();
		}
	}

	@Override
	public void putAll(Map arg0) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Object remove(Object key) {
		ObjPoolEntry entry=(ObjPoolEntry) pool.remove(key);
		if(entry==null){
			return null;
		}
		entry.setPhantom();
		Object ret= getRealObj(entry);
		recman.flushWriteQueues();
		return ret;
	}

	private Object getRealObj(ObjPoolEntry entry) {
		Object obj=entry.get();
		if(obj==null){
			byte[] buffer=entry.getByteBuffer();
			if(buffer==null){
				// read object from file
				buffer=new byte[entry.getSize()];
				try {
					outFile.seek(entry.getFilePos());
					outFile.read(buffer);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
				entry.refillBuffer(buffer);
			}
			// deserialize
	        ByteArrayInputStream  byteStream= new ByteArrayInputStream(buffer);
	        try {
	        	ObjectInputStream objStream  = new ObjectInputStream (byteStream);
				obj=objStream.readObject();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}
		return obj;
	}

	@Override
	public int size() {
		return pool.size();
	}

	@Override
	public Collection values() {
		throw new java.lang.UnsupportedOperationException();
	}

}
