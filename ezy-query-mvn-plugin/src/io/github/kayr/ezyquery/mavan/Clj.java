package io.github.kayr.ezyquery.mavan;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class Clj {

    public static final IFn REQ = Clojure.var("clojure.core", "require");


    public static Object call(String ns, String fn, Object arg1) {
        require(ns);
        return Clojure.var(ns, fn).invoke(arg1);
    }

    public static void require(String s) {
        REQ.invoke(Clojure.read(s));
    }

}
