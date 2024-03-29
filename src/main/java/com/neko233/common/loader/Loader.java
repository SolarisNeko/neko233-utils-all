package com.neko233.common.loader;

import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class Loader {

    static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";

    private static boolean ignoreTCL = false;
    private static boolean HAS_GET_CLASS_LOADER_PERMISSION = false;

    static {

        HAS_GET_CLASS_LOADER_PERMISSION = (Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                try {
                    AccessController.checkPermission(new RuntimePermission("getClassLoader"));
                    return true;
                } catch (SecurityException var2) {
                    return false;
                }
            }
        });
    }

    public Loader() {
    }

    public static Set<URL> getResources(String resource, ClassLoader classLoader) throws IOException {
        Set<URL> urlSet = new HashSet();
        Enumeration<URL> urlEnum = classLoader.getResources(resource);

        while (urlEnum.hasMoreElements()) {
            URL url = (URL) urlEnum.nextElement();
            urlSet.add(url);
        }

        return urlSet;
    }

    public static URL getResource(String resource, ClassLoader classLoader) {
        try {
            return classLoader.getResource(resource);
        } catch (Throwable var3) {
            return null;
        }
    }

    public static URL getResourceBySelfClassLoader(String resource) {
        return getResource(resource, getClassLoaderOfClass(Loader.class));
    }

    public static ClassLoader getTCL() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static ClassLoader getClassLoaderOfObject(Object o) {
        if (o == null) {
            throw new NullPointerException("Argument cannot be null");
        } else {
            return getClassLoaderOfClass(o.getClass());
        }
    }

    public static ClassLoader getClassLoaderAsPrivileged(final Class<?> clazz) {
        return !HAS_GET_CLASS_LOADER_PERMISSION ? null : (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return clazz.getClassLoader();
            }
        });
    }

    public static ClassLoader getClassLoaderOfClass(Class<?> clazz) {
        ClassLoader cl = clazz.getClassLoader();
        return cl == null ? ClassLoader.getSystemClassLoader() : cl;
    }

    public static Class<?> loadClass(String clazz) throws ClassNotFoundException {
        if (ignoreTCL) {
            return Class.forName(clazz);
        } else {
            try {
                return getTCL().loadClass(clazz);
            } catch (Throwable var2) {
                return Class.forName(clazz);
            }
        }
    }
}
