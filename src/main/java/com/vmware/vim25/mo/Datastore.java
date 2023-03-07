/*================================================================================
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

import com.vmware.vim25.DatastoreCapability;
import com.vmware.vim25.DatastoreHostMount;
import com.vmware.vim25.DatastoreInfo;
import com.vmware.vim25.DatastoreMountPathDatastorePair;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidDatastore;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.PlatformConfigFault;
import com.vmware.vim25.ResourceInUse;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.StorageIORMInfo;
import com.vmware.vim25.StoragePlacementResult;
import com.vmware.vim25.TaskInProgress;

import java.rmi.RemoteException;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 *
 * @author Steve JIN (http://www.doublecloud.org)
 */

public class Datastore extends ManagedEntity {
    public Datastore(ServerConnection serverConnection, ManagedObjectReference mor) {
        super(serverConnection, mor);
    }

    public HostDatastoreBrowser getBrowser() {
        return (HostDatastoreBrowser) getManagedObject("browser");
    }

    public DatastoreCapability getCapability() {
        return (DatastoreCapability) getCurrentProperty("capability");
    }

    public DatastoreHostMount[] getHost() {
        return (DatastoreHostMount[]) getCurrentProperty("host");
    }

    public DatastoreInfo getInfo() {
        return (DatastoreInfo) getCurrentProperty("info");
    }

    /**
     * @since SDK4.1
     */
    public StorageIORMInfo getIormConfiguration() {
        return (StorageIORMInfo) getCurrentProperty("iormConfiguration");
    }

    public DatastoreSummary getSummary() {
        return (DatastoreSummary) getCurrentProperty("summary");
    }

    public VirtualMachine[] getVms() {
        return getVms("vm");
    }

    /**
     * @since SDK5.0
     */
    public StoragePlacementResult datastoreEnterMaintenanceMode() throws RemoteException {
        return getVimService().datastoreEnterMaintenanceMode(getMOR());
    }

    /**
     * @since SDK5.0
     */
    public Task datastoreExitMaintenanceMode_Task() throws RemoteException {
        ManagedObjectReference taskMor = getVimService().datastoreExitMaintenanceMode_Task(getMOR());
        return new Task(getServerConnection(), taskMor);
    }

    public void destroyDatastore() throws RemoteException {
        getVimService().destroyDatastore(getMOR());
    }

    public void refreshDatastore() throws RemoteException {
        getVimService().refreshDatastore(getMOR());
    }

    /**
     * @since SDK4.0
     */
    public void refreshDatastoreStorageInfo() throws RemoteException {
        getVimService().refreshDatastoreStorageInfo(getMOR());
    }

    public void renameDatastore(String newName) throws RemoteException {
        getVimService().renameDatastore(getMOR(), newName);
    }

    /**
     * @since SDK4.1
     */
    public Task updateVirtualMachineFiles_Task(DatastoreMountPathDatastorePair[] mountPathDatastoreMapping) throws RemoteException {
        ManagedObjectReference mor = getVimService().updateVirtualMachineFiles_Task(getMOR(), mountPathDatastoreMapping);
        return new Task(getServerConnection(), mor);
    }
}
