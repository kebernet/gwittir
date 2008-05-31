/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.util;

import com.google.gwt.core.client.GWT;

/**
 * STUB: ResourceBundle is an abstract class which is the superclass of classes which
 * provide locale specific resources. A bundle contains a number of named
 * resources, where the names are Strings. A bundle may have a parent bundle,
 * when a resource is not found in a bundle, the parent bundle is searched for
 * the resource.
 * 
 * @see Properties
 * @see PropertyResourceBundle
 * @since 1.1
 */
public abstract class ResourceBundle {
	
	/**
	 * The parent of this ResourceBundle.
	 */
	protected ResourceBundle parent;

	private Locale locale;

	static class MissingBundle extends ResourceBundle {
        public Enumeration getKeys() {
			return null;
		}

        public Object handleGetObject(String name) {
			return null;
		}
	}

	/**
	 * Constructs a new instance of this class.
	 * 
	 */
	public ResourceBundle() {
		/*empty*/
	}

	/**
	 * Finds the named resource bundle for the default locale.
	 * 
	 * @param bundleName
	 *            the name of the resource bundle
	 * @return ResourceBundle
	 * 
	 * @exception MissingResourceException
	 *                when the resource bundle cannot be found
	 */
	public static final ResourceBundle getBundle(String bundleName)
			throws MissingResourceException {
		return getBundleImpl(bundleName, Locale.getDefault());
	}

	/**
	 * Finds the named resource bundle for the specified locale.
	 * 
	 * @param bundleName
	 *            the name of the resource bundle
	 * @param locale
	 *            the locale
	 * @return ResourceBundle
	 * 
	 * @exception MissingResourceException
	 *                when the resource bundle cannot be found
	 */
	public static final ResourceBundle getBundle(String bundleName,
			Locale locale) {
		return getBundleImpl(bundleName, locale);
	}

	private static ResourceBundle getBundleImpl(String bundleName, Locale locale) throws MissingResourceException {
		if (bundleName != null) {
			if (!locale.equals(Locale.getDefault())) {
				String localeName = locale.toString();
				if (localeName.length() > 0) {
                    localeName = "_" + localeName; //$NON-NLS-1$
                }
			}
			String localeName = Locale.getDefault().toString();
			if (localeName.length() > 0) {
                localeName = "_" + localeName; //$NON-NLS-1$
            }
			throw new MissingResourceException(null, bundleName + '_' + locale , ""); //$NON-NLS-1$
		}
        throw new NullPointerException();
	}

	/**
	 * Answers the names of the resources contained in this ResourceBundle.
	 * 
	 * @return an Enumeration of the resource names
	 */
	public abstract Enumeration getKeys();

	/**
	 * Gets the Locale of this ResourceBundle.
	 * 
	 * @return the Locale of this ResourceBundle
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Answers the named resource from this ResourceBundle.
	 * 
	 * @param key
	 *            the name of the resource
	 * @return the resource object
	 * 
	 * @exception MissingResourceException
	 *                when the resource is not found
	 */
	public final Object getObject(String key) {
		ResourceBundle last, theParent = this;
		do {
			Object result = theParent.handleGetObject(key);
			if (result != null) {
                return result;
            }
			last = theParent;
			theParent = theParent.parent;
		} while (theParent != null);
		throw new MissingResourceException(null, GWT.getTypeName(last), key);
	}

	/**
	 * Answers the named resource from this ResourceBundle.
	 * 
	 * @param key
	 *            the name of the resource
	 * @return the resource string
	 * 
	 * @exception MissingResourceException
	 *                when the resource is not found
	 */
	public final String getString(String key) {
		return (String) getObject(key);
	}

	/**
	 * Answers the named resource from this ResourceBundle.
	 * 
	 * @param key
	 *            the name of the resource
	 * @return the resource string array
	 * 
	 * @exception MissingResourceException
	 *                when the resource is not found
	 */
	public final String[] getStringArray(String key) {
		return (String[]) getObject(key);
	}

	/**
	 * Answers the named resource from this ResourceBundle, or null if the
	 * resource is not found.
	 * 
	 * @param key
	 *            the name of the resource
	 * @return the resource object
	 */
	protected abstract Object handleGetObject(String key);

	/**
	 * Sets the parent resource bundle of this ResourceBundle. The parent is
	 * searched for resources which are not found in this resource bundle.
	 * 
	 * @param bundle
	 *            the parent resource bundle
	 */
	protected void setParent(ResourceBundle bundle) {
		parent = bundle;
	}
}
