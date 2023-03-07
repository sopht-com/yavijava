/*================================================================================
Copyright (c) 2011 VMware, Inc. All Rights Reserved.

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

import com.vmware.vim25.IscsiMigrationDependency;
import com.vmware.vim25.IscsiPortInfo;
import com.vmware.vim25.IscsiStatus;
import com.vmware.vim25.ManagedObjectReference;

import java.rmi.RemoteException;

/**
 * provides interfaces for mapping VMkernel NIC to iSCSI Host Bus Adapter
 * The name is not consistent, and should have been HostIscsiManager, I think
 *
 * @author Steve Jin (http://www.doublecloud.org)
 * @since SDK5.0
 */

public class IscsiManager extends ManagedObject {

    public IscsiManager(ServerConnection serverConnection, ManagedObjectReference mor) {
        super(serverConnection, mor);
    }

    public void bindVnic(String iScsiHbaName, String vnicDevice) throws RemoteException {
        getVimService().bindVnic(getMOR(), iScsiHbaName, vnicDevice);
    }

    public IscsiPortInfo[] queryBoundVnics(String iScsiHbaName) throws RemoteException {
        return getVimService().queryBoundVnics(getMOR(), iScsiHbaName);
    }

    public IscsiPortInfo[] queryCandidateNics(String iScsiHbaName) throws RemoteException {
        return getVimService().queryCandidateNics(getMOR(), iScsiHbaName);
    }

    public IscsiMigrationDependency queryMigrationDependencies(String[] pnicDevice) throws RemoteException {
        return getVimService().queryMigrationDependencies(getMOR(), pnicDevice);
    }

    public IscsiStatus queryPnicStatus(String pnicDevice) throws RemoteException {
        return getVimService().queryPnicStatus(getMOR(), pnicDevice);
    }

    public IscsiStatus queryVnicStatus(String vnicDevice) throws RemoteException {
        return getVimService().queryVnicStatus(getMOR(), vnicDevice);
    }

    public void unbindVnic(String iScsiHbaName, String vnicDevice, boolean force) throws RemoteException {
        getVimService().unbindVnic(getMOR(), iScsiHbaName, vnicDevice, force);
    }

}
