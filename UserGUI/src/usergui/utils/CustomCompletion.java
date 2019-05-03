package usergui.utils;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.JTextComponent;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.js.JavaScriptCompletionProvider;
import org.fife.rsta.ac.js.JavaScriptHelper;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.JavaScriptFunctionDeclaration;
import org.fife.rsta.ac.js.ast.JavaScriptVariableDeclaration;
import org.fife.rsta.ac.js.ast.type.TypeDeclaration;
import org.fife.rsta.ac.js.ast.type.ecma.v5.TypeDeclarationsECMAv5;
import org.fife.rsta.ac.js.engine.RhinoJavaScriptEngine;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.modes.JavaScriptTokenMaker;

import admingui.completion_classes.E;
import admingui.completion_classes.F;
import admingui.completion_classes.O;
import admingui.completion_classes.S;

/**
 * @author bryan
 *
 *         This class is used for creating the custom completions for the
 *         subjects, objects, enviroment attributes and JavaScript Functions.
 */
public class CustomCompletion extends JavaScriptLanguageSupport {

	private static final String ENGINE = RhinoJavaScriptEngine.RHINO_ENGINE;

	public CustomCompletion() {
		JavaScriptTokenMaker.setJavaScriptVersion("1.7");
		setECMAVersion(TypeDeclarationsECMAv5.class.getName(), getJarManager());
	}

	@Override
	protected JavaScriptCompletionProvider createJavaScriptCompletionProvider() {
		return new JavaScriptCompletionProvider(new MySourceCompletionProvider(), getJarManager(), this);
	}

	public void install(RSyntaxTextArea textArea) {
		// remove javascript support and replace with Rhino support
		LanguageSupport support = (LanguageSupport) textArea.getClientProperty("org.fife.rsta.ac.LanguageSupport");
		if (support != null) {
			support.uninstall(textArea);
		}

		super.install(textArea);

	}

	/**
	 * @author bryan
	 * 
	 * Creates and adds the custom completions
	 *
	 */
	private class MySourceCompletionProvider extends SourceCompletionProvider {

		private ArrayList<Completion> myCompletions = new ArrayList<Completion>();

		public MySourceCompletionProvider() {
			super(ENGINE, false);

			addGlobalJavaScriptVariable("s", S.class, this);
			addGlobalJavaScriptVariable("o", O.class, this);
			addGlobalJavaScriptVariable("e", E.class, this);
			addGlobalJavaScriptVariable("f", F.class, this);

			try {
				createMyCompletions();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		/**
		 * Creates your own completions
		 * 
		 * @throws IOException
		 */
		private void createMyCompletions() throws IOException {

		}

		/**
		 * Add a global variable
		 * 
		 * @param name Name of the variable
		 * @param clazz Class of the variable
		 * @param provider The SourceCompletionProvider
		 */
		public void addGlobalJavaScriptVariable(String name, Class<?> clazz, SourceCompletionProvider provider) {
			TypeDeclaration type = JavaScriptHelper.findOrMakeTypeDeclaration(clazz.getName(), provider);

			JavaScriptVariableDeclaration jsDec = new JavaScriptVariableDeclaration(name, Integer.MAX_VALUE, provider,
					null);

			jsDec.setTypeDeclaration(type);
			provider.getVariableResolver().addSystemVariable(jsDec);
		}

		/**
		 * Adds a global function
		 * 
		 * @param name Name of the Funtion
		 * @param clazz Class of the Function
		 * @param provider The SourceCompletionProvider
		 */
		@SuppressWarnings("unused")
		public void addGlobalJavaScriptFunction(String name, Class<?> clazz, SourceCompletionProvider provider) {
			TypeDeclaration type = JavaScriptHelper.findOrMakeTypeDeclaration(clazz.getName(), provider);

			JavaScriptFunctionDeclaration jsDec = new JavaScriptFunctionDeclaration(name, Integer.MAX_VALUE, null,
					type);

			provider.getVariableResolver().addLocalFunction(jsDec);
		}

		public String getAlreadyEnteredText(JTextComponent comp) {

			String text = super.getAlreadyEnteredText(comp);
			if (text != null && text.length() > 0) // only add the
				completions.addAll(myCompletions);
			return text;
		}
	}
}