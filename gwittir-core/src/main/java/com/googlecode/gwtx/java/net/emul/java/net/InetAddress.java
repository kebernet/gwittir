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

package java.net;


import com.google.gwt.core.client.GWT;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * The Internet Protocol (IP) address class. This class encapsulates an IP
 * address and provides name and reverse name resolution functions. The address
 * is stored in network order, but as a signed (rather than unsigned) integer.
 */
public class InetAddress extends Object implements Serializable {

	final static byte[] any_bytes = { 0, 0, 0, 0 };

	final static byte[] localhost_bytes = { 127, 0, 0, 1 };

	//static InetAddress ANY = new Inet4Address(any_bytes);
    
	//final static InetAddress LOOPBACK = new Inet4Address(localhost_bytes, "localhost");
    
	private static final long serialVersionUID = 3286316764910316507L;

	String hostName;
	
	int family = 2;

	byte[] ipaddress;

	/**
	 * Constructs an InetAddress.
	 */
	InetAddress() {
		super();
	}

	/**
	 * Constructs an InetAddress, representing the <code>address</code> and
	 * <code>hostName</code>.
	 * 
	 * @param address
	 *            network address
	 */
	InetAddress(byte[] address) {
		super();
		this.ipaddress = address;
	}

	/**
	 * Constructs an InetAddress, representing the <code>address</code> and
	 * <code>hostName</code>.
	 * 
	 * @param address
	 *            network address
	 */
	InetAddress(byte[] address, String hostName) {
		super();
		this.ipaddress = address;
		this.hostName = hostName;
	}

	/**
	 * Compares this <code>InetAddress</code> against the specified object.
	 * 
	 * @param obj
	 *            the object to be tested for equality
	 * @return boolean true, if the objects are equal
	 */
    public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (GWT.getTypeName(obj) != GWT.getTypeName(this)) {
			return false;
		}

		// now check if their byte arrays match...
		byte[] objIPaddress = ((InetAddress) obj).ipaddress;
		for (int i = 0; i < objIPaddress.length; i++) {
			if (objIPaddress[i] != this.ipaddress[i]) {
                return false;
            }
		}
		return true;
	}

	/**
	 * Returns the IP address of this <code>InetAddress</code> as an array.
	 * The elements are in network order (the highest order address byte is in
	 * the zero-th element).
	 * 
	 * @return byte[] the address as a byte array
	 */
	public byte[] getAddress() {
		return ipaddress;
	}

	/**
	 * Answer the IP addresses of a named host. The host name may either be a
	 * machine name or a dotted string IP address. If the host name is empty or
	 * null, an UnknownHostException is thrown. If the host name is a dotted IP
	 * string, an array with the corresponding single InetAddress is returned.
	 * 
	 * @param host
	 *            the hostName to be resolved to an address
	 * 
	 * @return InetAddress[] an array of addresses for the host
	 * @throws UnknownHostException
	 *             if the address lookup fails
	 */
	public static InetAddress[] getAllByName(String host)
            throws UnknownHostException {
        throw new UnknownHostException(host);
    }

	/**
	 * Answers the address of a host, given a host string name. The host string
	 * may be either a machine name or a dotted string IP address. If the
	 * latter, the hostName field will be determined upon demand.
	 * 
	 * @param host
	 *            the hostName to be resolved to an address
	 * @return InetAddress the InetAddress representing the host
	 * 
	 * @throws UnknownHostException
	 *             if the address lookup fails
	 */
	public static InetAddress getByName(String host)
			throws UnknownHostException {
        throw new UnknownHostException(host);
	}

	/**
	 * Answer the dotted string IP address representing this address.
	 * 
	 * @return String the corresponding dotted string IP address
	 */
	public String getHostAddress() {
        throw new UnsupportedOperationException();
	}

	/**
	 * Answer the host name.
	 * 
	 * @return String the corresponding string name
	 */
	public String getHostName() {
        throw new UnsupportedOperationException();
    }

	/**
	 * Answers canonical name for the host associated with the internet address
	 * 
	 * @return String string containing the host name
	 */
	public String getCanonicalHostName() {
        throw new UnsupportedOperationException();
    }

	/**
	 * Answer the local host, if allowed by the security policy. Otherwise,
	 * answer the loopback address which allows this machine to be contacted.
	 * 
	 * @return InetAddress the InetAddress representing the local host
	 * @throws UnknownHostException
	 *             if the address lookup fails
	 */
	public static InetAddress getLocalHost() throws UnknownHostException {
        throw new UnsupportedOperationException();
	}

	/**
	 * Answer true if the InetAddress is an IP multicast address.
	 * 
	 * @return boolean true, if the address is in the multicast group
	 */
	public boolean isMulticastAddress() {
		return ((ipaddress[0] & 255) >>> 4) == 0xE;
	}

    public String toString() {
		return (hostName == null ? "" : hostName) + "/" + getHostAddress();
	}


	/**
	 * Answers true if the address is a link local address.
	 * 
	 * Valid IPv6 link local addresses are FE80::0 through to
	 * FEBF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF
	 * 
	 * There are no valid IPv4 link local addresses.
	 * 
	 * @return boolean
	 */
	public boolean isLinkLocalAddress() {
		return false;
	}

	/**
	 * Answers true if the address is a site local address.
	 * 
	 * Valid IPv6 link local addresses are FEC0::0 through to
	 * FEFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF
	 * 
	 * There are no valid IPv4 site local addresses.
	 * 
	 * @return boolean
	 */
	public boolean isSiteLocalAddress() {
		return false;
	}

	/**
	 * Answers true if the address is a global multicast address.
	 * 
	 * Valid IPv6 link global multicast addresses are FFxE:/112 where x is a set
	 * of flags, and the additional 112 bits make up the global multicast
	 * address space
	 * 
	 * Valid IPv4 global multicast addresses are between: 224.0.1.0 to
	 * 238.255.255.255
	 * 
	 * @return boolean
	 */
	public boolean isMCGlobal() {
		return false;
	}

	/**
	 * Answers true if the address is a node local multicast address.
	 * 
	 * Valid IPv6 node local multicast addresses are FFx1:/112 where x is a set
	 * of flags, and the additional 112 bits make up the node local multicast
	 * address space
	 * 
	 * There are no valid IPv4 node local multicast addresses.
	 * 
	 * @return boolean
	 */
	public boolean isMCNodeLocal() {
		return false;
	}

	/**
	 * Answers true if the address is a link local multicast address.
	 * 
	 * Valid IPv6 link local multicast addresses are FFx2:/112 where x is a set
	 * of flags, and the additional 112 bits make up the node local multicast
	 * address space
	 * 
	 * Valid IPv4 link-local addresses are between: 224.0.0.0 to 224.0.0.255
	 * 
	 * @return boolean
	 */
	public boolean isMCLinkLocal() {
		return false;
	}

	/**
	 * Answers true if the address is a site local multicast address.
	 * 
	 * Valid IPv6 site local multicast addresses are FFx5:/112 where x is a set
	 * of flags, and the additional 112 bits make up the node local multicast
	 * address space
	 * 
	 * Valid IPv4 site-local addresses are between: 239.252.0.0 to
	 * 239.255.255.255
	 * 
	 * @return boolean
	 */
	public boolean isMCSiteLocal() {
		return false;
	}

	/**
	 * Answers true if the address is a organization local multicast address.
	 * 
	 * Valid IPv6 organization local multicast addresses are FFx8:/112 where x
	 * is a set of flags, and the additional 112 bits make up the node local
	 * multicast address space
	 * 
	 * Valid IPv4 organization-local addresses are between: 239.192.0.0 to
	 * 239.251.255.255
	 * 
	 * @return boolean
	 */
	public boolean isMCOrgLocal() {
		return false;
	}

	/**
	 * Method isAnyLocalAddress.
	 * 
	 * @return boolean
	 */
	public boolean isAnyLocalAddress() {
		return false;
	}
	
	   
    /**
     * Tries to see if the InetAddress is reachable. This method first tries to
     * use ICMP(ICMP ECHO REQUEST). When first step fails, the TCP connection on
     * port 7 (Echo) shall be lauched.
     * 
     * @param timeout
     *            timeout in milliseconds
     * @return true if address is reachable
     * @throws IOException
     *             if I/O operation meets error
     * @throws IllegalArgumentException
     *             if timeout is less than zero
     */
    public boolean isReachable(int timeout) throws IOException {
        throw new UnsupportedOperationException();
    }

	/**
	 * Answers the InetAddress corresponding to the array of bytes. In the case
	 * of an IPv4 address there must be exactly 4 bytes and for IPv6 exactly 16
	 * bytes. If not, an UnknownHostException is thrown.
	 * 
	 * The IP address is not validated by a name service.
	 * 
	 * The high order byte is <code>ipAddress[0]</code>.
	 *
	 * @param 		ipAddress	either a 4 (IPv4) or 16 (IPv6) byte array
	 * @return 		the InetAddress
	 *
	 * @throws		UnknownHostException
	 */
	public static InetAddress getByAddress(byte[] ipAddress)
			throws UnknownHostException {
        throw new UnsupportedOperationException();
	}

	/**
	 * Answers the InetAddress corresponding to the array of bytes, and the
	 * given hostname. In the case of an IPv4 address there must be exactly 4
	 * bytes and for IPv6 exactly 16 bytes. If not, an UnknownHostException is
	 * thrown.
	 * 
	 * The host name and IP address are not validated.
	 * 
	 * The hostname either be a machine alias or a valid IPv6 or IPv4 address
	 * format.
	 * 
	 * The high order byte is <code>ipAddress[0]</code>.
	 *
	 * @param 		hostName	string representation of hostname or ip address
	 * @param 		ipAddress	either a 4 (IPv4) or 16 (IPv6) byte array
	 * @return 		the InetAddress
	 *
	 * @throws 		UnknownHostException
	 */
	public static InetAddress getByAddress(String hostName, byte[] ipAddress)
			throws UnknownHostException {
        throw new UnsupportedOperationException();
	}
}
