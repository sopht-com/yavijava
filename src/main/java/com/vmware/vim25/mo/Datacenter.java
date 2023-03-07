/*================================================================================
Copyright (c) 2012 Steve Jin. All Rights Reserved.
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vmware.vim25.mo;

import com.vmware.vim25.DatacenterConfigInfo;
import com.vmware.vim25.DatacenterConfigSpec;
import com.vmware.vim25.GatewayConnectFault;
import com.vmware.vim25.GatewayHostNotReachable;
import com.vmware.vim25.GatewayNotFound;
import com.vmware.vim25.GatewayNotReachable;
import com.vmware.vim25.GatewayOperationRefused;
import com.vmware.vim25.GatewayToHostAuthFault;
import com.vmware.vim25.GatewayToHostTrustVerifyFault;
import com.vmware.vim25.HostConnectFault;
import com.vmware.vim25.HostConnectInfo;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.InvalidArgument;
import com.vmware.vim25.InvalidLogin;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineConfigOptionDescriptor;
import com.vmware.vim25.mo.util.MorUtil;

import java.rmi.RemoteException;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 *
 * @author Steve JIN (http://www.doublecloud.org)
 */

public class Datacenter extends ManagedEntity {

    public Datacenter(ServerConnection sc, ManagedObjectReference mor) {
        super(sc, mor);
    }

    public Folder getVmFolder() {
        return (Folder) this.getManagedObject("vmFolder");
    }

    public Folder getHostFolder() {
        return (Folder) this.getManagedObject("hostFolder");
    }

    public Datastore[] getDatastores() {
        return getDatastores("datastore");
    }

    /**
     * @since SDK5.1
     */
    public DatacenterConfigInfo getConfiguration() {
        return (DatacenterConfigInfo) getCurrentProperty("configuration");
    }

    /**
     * @since 4.0
     */
    public Folder getDatastoreFolder() {
        return (Folder) getManagedObject("datastoreFolder");
    }

    /**
     * @since 4.0
     */
    public Folder getNetworkFolder() {
        return (Folder) getManagedObject("networkFolder");
    }

    public Network[] getNetworks() {
        return getNetworks("network");
    }

    /**
     * old signature for back compatibility with 2.5 and 4.0
     */
    public Task powerOnMultiVM_Task(VirtualMachine[] vms) throws RemoteException {
        return powerOnMultiVM_Task(vms, null);
    }

    /**
     * @since SDK4.1
     */
    public Task powerOnMultiVM_Task(VirtualMachine[] vms, OptionValue[] option) throws RemoteException {
        if (vms == null) {
            throw new IllegalArgumentException("vms must not be null.");
        }
        ManagedObjectReference[] mors = MorUtil.createMORs(vms);
        ManagedObjectReference tmor = getVimService().powerOnMultiVM_Task(getMOR(), mors, option);
        return new Task(getServerConnection(), tmor);
    }

    /**
     * @since SDK5.1
     */
    public Task reconfigureDatacenter_Task(DatacenterConfigSpec spec, boolean modify) throws RemoteException {
        ManagedObjectReference tmor = getVimService().reconfigureDatacenter_Task(getMOR(), spec, modify);
        return new Task(getServerConnection(), tmor);
    }

    public HostConnectInfo queryConnectionInfo(String hostname, int port, String username, String password,
                                               String sslThumbprint) throws RemoteException {
        return getVimService().queryConnectionInfo(getMOR(), hostname, port, username, password, sslThumbprint);
    }

    /**
     * @since SDK5.1
     */
    public VirtualMachineConfigOptionDescriptor[] queryDatacenterConfigOptionDescriptor() throws RemoteException {
        return getVimService().queryDatacenterConfigOptionDescriptor(getMOR());
    }

    /**
     * This method provides a way of getting basic information about a host without adding it to a datacenter. This
     * method is similar to QueryConnectionInfo, but it takes a HostConnectSpec as argument, instead of list of
     * parameters.
     *
     * @param spec The connection spec for the host to be queried. It must contain values for all parameters required by QueryConnectionInfo See QueryConnectionInfo or a list of thrown expections.
     * @return HostConnectInfo
     * @throws GatewayConnectFault
     * @throws GatewayHostNotReachable
     * @throws GatewayNotFound
     * @throws GatewayNotReachable
     * @throws GatewayOperationRefused
     * @throws GatewayToHostAuthFault
     * @throws GatewayToHostTrustVerifyFault
     * @throws HostConnectFault
     * @throws InvalidArgument
     * @throws InvalidLogin
     * @throws RuntimeFault
     * @throws RemoteException
     * @since 6.0
     */
    public HostConnectInfo queryConnectionInfoViaSpec(HostConnectSpec spec) throws GatewayConnectFault, GatewayHostNotReachable, GatewayNotFound, GatewayNotReachable, GatewayOperationRefused, GatewayToHostAuthFault, GatewayToHostTrustVerifyFault, HostConnectFault, InvalidArgument, InvalidLogin, RuntimeFault, RemoteException {
        return getVimService().queryConnectionInfoViaSpec(getMOR(), spec);
    }
}
