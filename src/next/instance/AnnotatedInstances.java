package next.instance;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import next.instance.classwrapper.ClassWrapper;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class AnnotatedInstances {

	private InstanceMap instanceMap;

	@Override
	public String toString() {
		return "InstanceManager [instanceMap=" + instanceMap + "]";
	}

	private List<Class<?>> fieldAnnotations;
	private List<Class<?>> classAnnotations;

	public AnnotatedInstances() {
		instanceMap = new InstanceMap();
		fieldAnnotations = new ArrayList<Class<?>>();
		classAnnotations = new ArrayList<Class<?>>();
	}

	public void addFieldAnnotation(Class<?>... ano) {
		for (int i = 0; i < ano.length; i++)
			fieldAnnotations.add(ano[i]);
	}

	public void addClassAnnotation(Class<?>... ano) {
		for (int i = 0; i < ano.length; i++)
			classAnnotations.add(ano[i]);
	}

	@SuppressWarnings("unchecked")
	public void makeInstances() {
		Reflections ref = new Reflections("", new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner());
		fieldAnnotations.forEach(ano -> {
			ref.getFieldsAnnotatedWith((Class<? extends Annotation>) ano).forEach(type -> {
				ClassWrapper classWrapper = new ClassWrapper(type.getType());
				classWrapper.injectAutoWired(instanceMap);
				instanceMap.put(type.getType(), classWrapper);
			});
		});
		classAnnotations.forEach(ano -> {
			ref.getTypesAnnotatedWith((Class<? extends Annotation>) ano).forEach(type -> {
				ClassWrapper classWrapper = new ClassWrapper(type);
				classWrapper.injectAutoWired(instanceMap);
				instanceMap.put(type, classWrapper);
			});
		});
	}

	public Object getInstance(Class<?> type) {
		return instanceMap.get(type);
	}

}
