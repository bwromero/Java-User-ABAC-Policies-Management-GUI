package usergui.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import admingui.completion_classes.S;
import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import usergui.model.JavaScriptFunction;

public class PojoGenerator {

	public void generate(Map<String, String> properties, String object)
			throws NotFoundException, CannotCompileException, IOException {

		ClassPool pool = ClassPool.getDefault();
		pool.insertClassPath("CompletionClasses.jar");
		CtClass cc = pool.get("admingui.completion_classes." + object);

		if (object.equals("F")) {

			for (Entry<String, String> entry : properties.entrySet()) {

				String field = "public " + entry.getValue() + " " + createFieldName(entry.getKey()) + ";";

				if (cc.isFrozen()) {
					cc.defrost();
				}

				CtMethod m = CtMethod.make(field, cc);

				try {
					if (cc.getField(m.getName()).getName().isEmpty()) {
					}
				} catch (NotFoundException e) {
					System.out.println("There's no function with name: " + m.getName() + "let's add it!");
					cc.addMethod(m);
					System.out.println("New function added!");
				}
			}

		} else {
			for (Entry<String, String> entry : properties.entrySet()) {

				String field = "public " + entry.getValue() + " " + createFieldName(entry.getKey()) + ";";

				if (cc.isFrozen()) {
					cc.defrost();
				}

				CtField f = CtField.make(field, cc);

				try {
					if (cc.getField(f.getName()).getName().isEmpty()) {
					}
				} catch (NotFoundException e) {
					System.out.println("There's no field with name: " + f.getName() + "let's add it!");
					cc.addField(f);
					System.out.println("New field added!");
				}
			}
		}

		byte[] b = cc.toBytecode(); // convert the new class to bytecode.
		pool.removeClassPath(new ClassClassPath(S.class));// need to remove the
															// classpath to
															// release

		JarHandler jarHandler = new JarHandler();

		synchronized(this) {
			jarHandler.replaceJarFile("CompletionClasses.jar", b, "admingui/completion_classes/" + object + ".class");
		}
	}

	public void generate(List<JavaScriptFunction> jsFunctions, String object)
			throws NotFoundException, CannotCompileException, IOException {

		ClassPool pool = ClassPool.getDefault();
		pool.insertClassPath("CompletionClasses.jar");
		CtClass cc = pool.get("admingui.completion_classes." + object);

		if (object.equals("F")) {

			for (JavaScriptFunction f : jsFunctions) {

				String field = "public void " + f.getName().trim() + "{}";

				System.out.println(field);

				if (cc.isFrozen()) {
					cc.defrost();
				}

				CtMethod m = CtMethod.make(field, cc);

				try {
					if (cc.getDeclaredMethod(m.getName()).getName().isEmpty()) {
					}
				} catch (NotFoundException e) {
					System.out.println("There's no function with name: " + m.getName() + "let's add it!");
					cc.addMethod(m);
					System.out.println("New function added!");
				}
			}

		}

		byte[] b = cc.toBytecode(); // convert the new class to bytecode.
		pool.removeClassPath(new ClassClassPath(S.class));// need to remove the
															// classpath to
															// release

		JarHandler jarHandler = new JarHandler();

		jarHandler.replaceJarFile("CompletionClasses.jar", b, "admingui/completion_classes/" + object + ".class");
	}

	public String createFieldName(String attributeName) {

		String fieldName = "";

		if (attributeName.contains(" ")) {
			String[] parts = attributeName.split(Pattern.quote(" "));

			for (int i = 0; i < parts.length - 1; i++) {
				fieldName = parts[i].toLowerCase() + parts[i + 1];

			}
			return fieldName;
		}
		return attributeName.toLowerCase();
	}
}