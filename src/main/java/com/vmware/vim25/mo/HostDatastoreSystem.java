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

import com.vmware.vim25.AlreadyExists;
import com.vmware.vim25.DatastoreNotWritableOnHost;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.HostConfigFault;
import com.vmware.vim25.HostDatastoreSystemCapabilities;
import com.vmware.vim25.HostDatastoreSystemVvolDatastoreSpec;
import com.vmware.vim25.HostNasVolumeSpec;
import com.vmware.vim25.HostScsiDisk;
import com.vmware.vim25.HostUnresolvedVmfsResignatureSpec;
import com.vmware.vim25.HostUnresolvedVmfsVolume;
import com.vmware.vim25.InaccessibleDatastore;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.NotFound;
import com.vmware.vim25.ResourceInUse;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VmfsAmbiguousMount;
import com.vmware.vim25.VmfsDatastoreCreateSpec;
import com.vmware.vim25.VmfsDatastoreExpandSpec;
import com.vmware.vim25.VmfsDatastoreExtendSpec;
import com.vmware.vim25.VmfsDatastoreOption;
import com.vmware.vim25.mo.util.MorUtil;

import java.rmi.RemoteException;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 *
 * @author Steve JIN (http://www.doublecloud.org)
 */

public class HostDatastoreSystem extends ManagedObject {

    public HostDatastoreSystem(ServerConnection serverConnection, ManagedObjectReference mor) {
        super(serverConnection, mor);
    }

    public HostDatastoreSystemCapabilities getCapabilities() {
        return (HostDatastoreSystemCapabilities) getCurrentProperty("capabilities");
    }

    public Datastore[] getDatastores() {
        return getDatastores("datastore");
    }

    public void configureDatastorePrincipal(String userName, String password) throws RemoteException {
        getVimService().configureDatastorePrincipal(getMOR(), userName, password);
    }

    public Datastore createLocalDatastore(String name, String path) throws RemoteException {
        ManagedObjectReference mor = getVimService().createLocalDatastore(getMOR(), name, path);
        return new Datastore(getServerConnection(), mor);
    }

    public Datastore createNasDatastore(HostNasVolumeSpec spec) throws RemoteException {
        ManagedObjectReference mor = getVimService().createNasDatastore(getMOR(), spec);
        return new Datastore(getServerConnection(), mor);
    }

    public Datastore createVmfsDatastore(VmfsDatastoreCreateSpec spec) throws RemoteException {
        ManagedObjectReference mor = getVimService().createVmfsDatastore(getMOR(), spec);
        return new Datastore(getServerConnection(), mor);
    }

    /**
     * @since 4.0
     */
    public Datastore expandVmfsDatastore(Datastore datastore, VmfsDatastoreExpandSpec spec) throws RemoteException {
        ManagedObjectReference mor = getVimService().expandVmfsDatastore(getMOR(), datastore.getMOR(), spec);
        return new Datastore(getServerConnection(), mor);
    }

    public Datastore extendVmfsDatastore(Datastore datastore, VmfsDatastoreExtendSpec spec) throws RemoteException {
        if (datastore == null) {
            throw new IllegalArgumentException("datastore must not be null.");
        }
        ManagedObjectReference mor = getVimService().extendVmfsDatastore(getMOR(), datastore.getMOR(), spec);
        return new Datastore(getServerConnection(), mor);
    }

    public HostScsiDisk[] queryAvailableDisksForVmfs(Datastore datastore) throws RemoteException {
        return getVimService().queryAvailableDisksForVmfs(getMOR(), datastore == null ? null : datastore.getMOR());
    }

    //SDK4.1 signature for back compatibility
    public VmfsDatastoreOption[] queryVmfsDatastoreCreateOptions(String devicePath) throws RemoteException {
        return getVimService().queryVmfsDatastoreCreateOptions(getMOR(), devicePath);
    }

    //SDK5.0 signature
    public VmfsDatastoreOption[] queryVmfsDatastoreCreateOptions(String devicePath, int vmfsMajorVersion) throws RemoteException {
        return getVimService().queryVmfsDatastoreCreateOptions(getMOR(), devicePath, vmfsMajorVersion);
    }

    //SDK2.5 signature for back compatibility
    public VmfsDatastoreOption[] queryVmfsDatastoreExtendOptions(Datastore datastore, String devicePath) throws RemoteException {
        return queryVmfsDatastoreExtendOptions(datastore, devicePath, null);
    }

    //SDK4.0 signature
    public VmfsDatastoreOption[] queryVmfsDatastoreExtendOptions(Datastore datastore, String devicePath, Boolean suppressExpandCandidates) throws RemoteException {
        if (datastore == null) {
            throw new IllegalArgumentException("datastore must not be null.");
        }
        return getVimService().queryVmfsDatastoreExtendOptions(getMOR(), datastore.getMOR(), devicePath, suppressExpandCandidates);
    }

    /**
     * @since 4.0
     */
    public VmfsDatastoreOption[] queryVmfsDatastoreExpandOptions(Datastore datastore) throws RemoteException {
        return getVimService().queryVmfsDatastoreExpandOptions(getMOR(), datastore.getMOR());
    }

    /**
     * @since 4.0
     */
    public HostUnresolvedVmfsVolume[] queryUnresolvedVmfsVolumes() throws RemoteException {
        return getVimService().queryUnresolvedVmfsVolumes(getMOR());
    }

    public void removeDatastore(Datastore datastore) throws RemoteException {
        if (datastore == null) {
            throw new IllegalArgumentException("datastore must not be null.");
        }
        getVimService().removeDatastore(getMOR(), datastore.getMOR());
    }

    /**
     * @since 4.0
     */
    public Task resignatureUnresolvedVmfsVolume_Task(HostUnresolvedVmfsResignatureSpec resolutionSpec) throws RemoteException {
        ManagedObjectReference taskMor = getVimService().resignatureUnresolvedVmfsVolume_Task(getMOR(), resolutionSpec);
        return new Task(getServerConnection(), taskMor);
    }

    public void updateLocalSwapDatastore(Datastore datastore) throws RemoteException {
        getVimService().updateLocalSwapDatastore(getMOR(), datastore == null ? null : datastore.getMOR());
    }

    /**
     * Create a Virtual-Volume based datastore
     *
     * @param spec Specification for creating a Virtual-Volume based datastore.
     * @return The newly created datastore.
     * @throws DuplicateName
     * @throws HostConfigFault
     * @throws InvalidName
     * @throws NotFound
     * @throws RuntimeFault
     * @throws RemoteException
     * @since 6.0
     */
    public Datastore createVvolDatastore(HostDatastoreSystemVvolDatastoreSpec spec) throws DuplicateName, HostConfigFault, InvalidName, NotFound, RuntimeFault, RemoteException {
        ManagedObjectReference dsMor = getVimService().createVvolDatastore(getMOR(), spec);
        return new Datastore(getServerConnection(), dsMor);
    }

    /**
     * Remove one or more datastores. This is an asynchronous, batch operation of removeDatastore. Please see
     * {@link #removeDatastore(Datastore) RemoveDatastore} for operational details. Note: This API currently supports
     * removal of only NFS datastores.
     *
     * @param datastore Each element specifies one datastore to be removed.
     * @return Task to monitor
     * @throws HostConfigFault
     * @throws RuntimeFault
     * @throws RemoteException
     */
    public Task removeDatastoreEx_Task(Datastore[] datastore) throws HostConfigFault, RuntimeFault, RemoteException {
        ManagedObjectReference taskMor = getVimService().removeDatastoreEx_Task(getMOR(), MorUtil.createMORs(datastore));
        return new Task(getServerConnection(), taskMor);
    }
}
