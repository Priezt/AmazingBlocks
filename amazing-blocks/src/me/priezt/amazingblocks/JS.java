package me.priezt.amazingblocks;

import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.Scriptable;

public class JS {
	public static void run(String jsCode){
		Context rhino = Context.enter();
		rhino.setOptimizationLevel(-1);
		try{
			Scriptable scope = rhino.initStandardObjects();
			rhino.evaluateString(scope, jsCode, "ScriptAPI", 1, null);
		}finally{
			rhino.exit();
		}
	}
}
