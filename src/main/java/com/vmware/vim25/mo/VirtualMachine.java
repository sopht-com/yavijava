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
import com.vmware.vim25.AlreadyUpgraded;
import com.vmware.vim25.ConcurrentAccess;
import com.vmware.vim25.CustomizationFault;
import com.vmware.vim25.CustomizationSpec;
import com.vmware.vim25.DiskChangeInfo;
import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.FaultToleranceConfigSpec;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.GuestInfo;
import com.vmware.vim25.HostIncompatibleForRecordReplay;
import com.vmware.vim25.InsufficientResourcesFault;
import com.vmware.vim25.InvalidDatastore;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidPowerState;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.LocalizedMethodFault;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.ManagedObjectNotFound;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.MigrationFault;
import com.vmware.vim25.NoDiskFound;
import com.vmware.vim25.NotFound;
import com.vmware.vim25.NotSupported;
import com.vmware.vim25.RecordReplayDisabled;
import com.vmware.vim25.ResourceConfigSpec;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.SnapshotFault;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.Timedout;
import com.vmware.vim25.ToolsUnavailable;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualMachineCapability;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineDisplayTopology;
import com.vmware.vim25.VirtualMachineFileLayout;
import com.vmware.vim25.VirtualMachineFileLayoutEx;
import com.vmware.vim25.VirtualMachineMksTicket;
import com.vmware.vim25.VirtualMachineMovePriority;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.VirtualMachineSnapshotInfo;
import com.vmware.vim25.VirtualMachineStorageInfo;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.VirtualMachineTicket;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.VmFaultToleranceIssue;
import com.vmware.vim25.VmToolsUpgradeFault;

import java.rmi.RemoteException;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 *
 * @author Steve JIN (http://www.doublecloud.org)
 */

public class VirtualMachine extends ManagedEntity {
    public VirtualMachine(ServerConnection sc, ManagedObjectReference mor) {
        super(sc, mor);
    }

    public VirtualMachineCapability getCapability() {
        return (VirtualMachineCapability) getCurrentProperty("capability");
    }

    public VirtualMachineConfigInfo getConfig() {
        return (VirtualMachineConfigInfo) getCurrentProperty("config");
    }

    public Datastore[] getDatastores() throws RemoteException {
        return getDatastores("datastore");
    }

    public EnvironmentBrowser getEnvironmentBrowser() throws RemoteException {
        return (EnvironmentBrowser) getManagedObject("environmentBrowser");
    }

    public GuestInfo getGuest() {
        return (GuestInfo) getCurrentProperty("guest");
    }

    public ManagedEntityStatus getGuestHeartbeatStatus() {
        return (ManagedEntityStatus) getCurrentProperty("guestHeartbeatStatus");
    }

    public VirtualMachineFileLayout getLayout() {
        return (VirtualMachineFileLayout) getCurrentProperty("layout");
    }

    /**
     * @since SDK4.0
     */
    public VirtualMachineFileLayoutEx getLayoutEx() {
        return (VirtualMachineFileLayoutEx) getCurrentProperty("layoutEx");
    }

    /**
     * @since SDK4.0
     */
    public VirtualMachineStorageInfo getStorage() {
        return (VirtualMachineStorageInfo) getCurrentProperty("storage");
    }

    public Network[] getNetworks() throws RemoteException {
        return getNetworks("network");
    }

    /**
     * @since SDK4.1
     */
    public ManagedEntity getParentVApp() {
        ManagedObjectReference mor = (ManagedObjectReference) getCurrentProperty("parentVApp");
        return new ManagedEntity(getServerConnection(), mor);
    }

    public ResourceConfigSpec getResourceConfig() {
        return (ResourceConfigSpec) getCurrentProperty("resourceConfig");
    }

    public ResourcePool getResourcePool() throws RemoteException {
        return (ResourcePool) getManagedObject("resourcePool");
    }

    /**
     * @since SDK4.1
     */
    public VirtualMachineSnapshot[] getRootSnapshot() {
        ManagedObjectReference[] mors = (ManagedObjectReference[]) getCurrentProperty("rootSnapshot");
        if (mors == null) {
            return new VirtualMachineSnapshot[0];
        }
        VirtualMachineSnapshot[] vmns = new VirtualMachineSnapshot[mors.length];
        for (int i = 0; i < mors.length; i++) {
            vmns[i] = new VirtualMachineSnapshot(getServerConnection(), mors[i]);
        }
        return vmns;
    }

    public VirtualMachineRuntimeInfo getRuntime() {
        return (VirtualMachineRuntimeInfo) getCurrentProperty("runtime");
    }

    public VirtualMachineSnapshotInfo getSnapshot() {
        return (VirtualMachineSnapshotInfo) getCurrentProperty("snapshot");
    }

    public VirtualMachineSnapshot getCurrentSnapShot() throws RemoteException {
        return (VirtualMachineSnapshot) getManagedObject("snapshot.currentSnapshot");
    }

    public VirtualMachineSummary getSummary() {
        return (VirtualMachineSummary) getCurrentProperty("summary");
    }

    /**
     * @deprecated as of SDK4.1. Use acquireTicket instead.
     */
    public VirtualMachineMksTicket acquireMksTicket() throws RemoteException {
        return getVimService().acquireMksTicket(getMOR());
    }

    /**
     * @since SDK4.1
     */
    public VirtualMachineTicket acquireTicket(String ticketType) throws RemoteException {
        return getVimService().acquireTicket(getMOR(), ticketType);
    }

    public void answerVM(String questionId, String answerChoice) throws RemoteException {
        getVimService().answerVM(getMOR(), questionId, answerChoice);
    }

    public void checkCustomizationSpec(CustomizationSpec spec) throws RemoteException {
        getVimService().checkCustomizationSpec(getMOR(), spec);
    }

    public Task cloneVM_Task(Folder folder, String name, VirtualMachineCloneSpec spec) throws RemoteException {
        if (folder == null) {
            throw new IllegalArgumentException("folder must not be null.");
        }
        ManagedObjectReference mor = getVimService().cloneVM_Task(getMOR(), folder.getMOR(), name, spec);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK5.0
     */
    public Task consolidateVMDisks_Task() throws RemoteException {
        ManagedObjectReference taskMor = getVimService().consolidateVMDisks_Task(getMOR());
        return new Task(getServerConnection(), taskMor);
    }

    /**
     * @since SDK4.0
     */
    public Task CreateScreenshot_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().createScreenshot_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    public Task createSnapshot_Task(String name, String description, boolean memory, boolean quiesce) throws RemoteException {
        ManagedObjectReference mor = getVimService().createSnapshot_Task(getMOR(), name, description, memory, quiesce);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     * @deprecated As of vSphere API 6.0, use {@link #createSecondaryVMEx_Task createSecondaryVMEx_Task} instead.
     */
    public Task createSecondaryVM_Task(HostSystem host) throws RemoteException {
        ManagedObjectReference mor = getVimService().createSecondaryVM_Task(getMOR(), host == null ? null : host.getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * Creates a secondary virtual machine to be part of this fault tolerant group.
     * <p>
     * If a host is specified, the secondary virtual machine will be created on it. Otherwise, a host will be selected
     * by the system.
     * <p>
     * If a FaultToleranceConfigSpec is specified, the virtual machine's configuration files and disks will be created
     * in the specified datastores.
     * <p>
     * If the primary virtual machine (i.e., this virtual machine) is powered on when the secondary is created, an
     * attempt will be made to power on the secondary on a system selected host. If the cluster is a DRS cluster, DRS
     * will be invoked to obtain a placement for the new secondary virtual machine. If the DRS recommendation
     * (see {@link com.vmware.vim25.ClusterRecommendation ClusterRecommendation}) is automatic, it will be automatically
     * executed. Otherwise, the recommendation will be returned to the caller of this method and the secondary will
     * remain powered off until the recommendation is approved using
     * {@link com.vmware.vim25.mo.ClusterComputeResource#applyRecommendation applyRecommendation}. Failure to power on the
     * secondary virtual machine will not fail the creation of the secondary. This method creates a Record-Replay FT VM
     * for single vCPU VMs only when vm.useLegacyFt is set to true in vm.config.extraConfig.
     *
     * @param host -
     *             The host where the secondary virtual machine is to be created and powered on. If no host is specified,
     *             a compatible host will be selected by the system. If a host cannot be found for the secondary or the
     *             specified host is not suitable, the secondary will not be created and a fault will be returned.
     * @param spec contains information about the metadata file and vmdk files for a fault tolerant VM pair.
     * @return This method returns a Task object with which to monitor the operation. The info.result property in the Task returns an instance of the FaultToleranceSecondaryOpResult data object, which contains a reference to the created VirtualMachine and the status of powering it on, if attempted.
     * @throws FileFault
     * @throws InsufficientResourcesFault
     * @throws InvalidState
     * @throws ManagedObjectNotFound
     * @throws NotSupported
     * @throws RuntimeFault
     * @throws TaskInProgress
     * @throws VmConfigFault
     * @throws VmFaultToleranceIssue
     * @throws RemoteException
     * @since 6.0
     */
    public Task createSecondaryVMEx_Task(HostSystem host, FaultToleranceConfigSpec spec) throws FileFault, InsufficientResourcesFault, InvalidState, ManagedObjectNotFound, NotSupported, RuntimeFault, TaskInProgress, VmConfigFault, VmFaultToleranceIssue, RemoteException {
        ManagedObjectReference taskMor = getVimService().createSecondaryVMEx_Task(getMOR(), host == null ? null : host.getMOR(), spec);
        return new Task(getServerConnection(), taskMor);
    }

    /**
     * @since 6.0
     */
    public Task createSecondaryVMEx_Task(HostSystem host) throws RemoteException {
        return createSecondaryVMEx_Task(host, null);
    }

    /**
     * @since 6.0
     */
    public Task createSecondaryVMEx_Task(FaultToleranceConfigSpec spec) throws RemoteException {
        return createSecondaryVMEx_Task(null, spec);
    }

    /**
     * @since SDK4.0
     */
    public Task disableSecondaryVM_Task(VirtualMachine vm) throws RemoteException {
        ManagedObjectReference mor = getVimService().disableSecondaryVM_Task(getMOR(), vm.getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task enableSecondaryVM_Task(VirtualMachine vm, HostSystem host) throws RemoteException {
        ManagedObjectReference mor = getVimService().enableSecondaryVM_Task(getMOR(), vm.getMOR(),
                host == null ? null : host.getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK5.0
     */
    public Task estimateStorageForConsolidateSnapshots_Task() throws RemoteException {
        ManagedObjectReference taskMor = getVimService().estimateStorageForConsolidateSnapshots_Task(getMOR());
        return new Task(getServerConnection(), taskMor);
    }

    /**
     * @since SDK4.0
     */
    public HttpNfcLease exportVm() throws RemoteException {
        ManagedObjectReference mor = getVimService().exportVm(getMOR());
        return new HttpNfcLease(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public String extractOvfEnvironment() throws RemoteException {
        return getVimService().extractOvfEnvironment(getMOR());
    }

    /**
     * @since SDK4.0
     */
    public Task makePrimaryVM_Task(VirtualMachine vm) throws RemoteException {
        ManagedObjectReference mor = getVimService().makePrimaryVM_Task(getMOR(), vm.getMOR());
        return new Task(getServerConnection(), mor);
    }

    public Task customizeVM_Task(CustomizationSpec spec) throws RemoteException {
        ManagedObjectReference mor = getVimService().customizeVM_Task(getMOR(), spec);
        return new Task(getServerConnection(), mor);
    }

    public void defragmentAllDisks() throws RemoteException {
        getVimService().defragmentAllDisks(getMOR());
    }

    public void markAsTemplate() throws RemoteException {
        getVimService().markAsTemplate(getMOR());
    }

    public void markAsVirtualMachine(ResourcePool pool, HostSystem host) throws RemoteException {
        if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
        }
        getVimService().markAsVirtualMachine(getMOR(), pool.getMOR(), host == null ? null : host.getMOR());
    }

    public Task migrateVM_Task(ResourcePool pool, HostSystem host, VirtualMachineMovePriority priority,
                               VirtualMachinePowerState state) throws RemoteException {
        ManagedObjectReference mor = getVimService().migrateVM_Task(getMOR(), pool == null ? null : pool.getMOR(), host == null ? null : host.getMOR(), priority, state);
        return new Task(getServerConnection(), mor);
    }

    public void mountToolsInstaller() throws RemoteException {
        getVimService().mountToolsInstaller(getMOR());
    }

    public Task powerOffVM_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().powerOffVM_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    public Task powerOnVM_Task(HostSystem host) throws RemoteException {
        ManagedObjectReference mor = getVimService().powerOnVM_Task(getMOR(), host == null ? null : host.getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task promoteDisks_Task(boolean unlink, VirtualDisk[] disks) throws RemoteException {
        ManagedObjectReference mor = getVimService().promoteDisks_Task(getMOR(), unlink, disks);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public DiskChangeInfo queryChangedDiskAreas(VirtualMachineSnapshot snapshot, int deviceKey, long startOffset, String changeId) throws RemoteException {
        return getVimService().queryChangedDiskAreas(getMOR(),
                snapshot == null ? null : snapshot.getMOR(),
                deviceKey, startOffset, changeId);
    }

    /**
     * @since SDK4.1
     * @deprecated As of 6.0 use {@link #queryFaultToleranceCompatibilityEx}
     */
    public LocalizedMethodFault[] queryFaultToleranceCompatibility() throws RemoteException {
        return getVimService().queryFaultToleranceCompatibility(getMOR());
    }

    /**
     * This API can be invoked to determine whether a virtual machine is compatible for Fault Tolerance. The API only
     * checks for VM-specific factors that impact compatibility for Fault Tolerance. Other requirements for Fault
     * Tolerance such as host processor compatibility, logging nic configuration and licensing are not covered by this
     * API. The query returns a list of faults, each fault corresponding to a specific incompatibility. If a given
     * virtual machine is compatible for Fault Tolerance, then the fault list returned will be empty.
     *
     * @param forLegacyFt checks for legacy record-replay FT compatibility only if this is set to true.
     * @return Localized Method Fault
     * @throws InvalidState
     * @throws VmConfigFault
     * @throws RuntimeFault
     * @throws RemoteException
     * @since 6.0
     */
    public LocalizedMethodFault[] queryFaultToleranceCompatibilityEx(Boolean forLegacyFt) throws InvalidState, VmConfigFault, RuntimeFault, RemoteException {
        return getVimService().queryFaultToleranceCompatibilityEx(getMOR(), forLegacyFt);
    }

    /**
     * @since 6.0
     */
    public LocalizedMethodFault[] queryFaultToleranceCompatibilityEx() throws RemoteException {
        return queryFaultToleranceCompatibilityEx(null);
    }

    /**
     * @since SDK4.0
     */
    public String[] queryUnownedFiles() throws RemoteException {
        return getVimService().queryUnownedFiles(getMOR());
    }

    public void rebootGuest() throws RemoteException {
        getVimService().rebootGuest(getMOR());
    }

    public Task reconfigVM_Task(VirtualMachineConfigSpec spec) throws RemoteException {
        ManagedObjectReference mor = getVimService().reconfigVM_Task(getMOR(), spec);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.1
     */
    public Task reloadVirtualMachineFromPath_Task(String configurationPath) throws RemoteException {
        ManagedObjectReference mor = getVimService().reloadVirtualMachineFromPath_Task(getMOR(), configurationPath);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public void refreshStorageInfo() throws RemoteException {
        getVimService().refreshStorageInfo(getMOR());
    }

    //SDK2.5 signature for back compatibility
    public Task relocateVM_Task(VirtualMachineRelocateSpec spec) throws RemoteException {
        return relocateVM_Task(spec, null);
    }

    //SDK4.0 signature
    public Task relocateVM_Task(VirtualMachineRelocateSpec spec, VirtualMachineMovePriority priority) throws RemoteException {
        ManagedObjectReference mor = getVimService().relocateVM_Task(getMOR(), spec, priority);
        return new Task(getServerConnection(), mor);
    }

    //SDK4.1 signature for back compatibility
    public Task removeAllSnapshots_Task() throws RemoteException {
        return removeAllSnapshots_Task(null);
    }

    //SDK5.0 signature
    public Task removeAllSnapshots_Task(Boolean consolidate) throws RemoteException {
        ManagedObjectReference mor = getVimService().removeAllSnapshots_Task(getMOR(), consolidate);
        return new Task(getServerConnection(), mor);
    }

    public void resetGuestInformation() throws RemoteException {
        getVimService().resetGuestInformation(getMOR());
    }

    public Task resetVM_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().resetVM_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    //SDK2.5 signature for back compatibility
    public Task revertToCurrentSnapshot_Task(HostSystem host) throws RemoteException {
        return revertToCurrentSnapshot_Task(host, null);
    }

    //SDK4.0 signature
    public Task revertToCurrentSnapshot_Task(HostSystem host, Boolean suppressPowerOn) throws RemoteException {
        ManagedObjectReference mor = getVimService().revertToCurrentSnapshot_Task(getMOR(), host == null ? null : host.getMOR(), suppressPowerOn);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public void setDisplayTopology(VirtualMachineDisplayTopology[] displays) throws RemoteException {
        getVimService().setDisplayTopology(getMOR(), displays);
    }

    public void setScreenResolution(int width, int height) throws RemoteException {
        getVimService().setScreenResolution(getMOR(), width, height);
    }

    public void shutdownGuest() throws RemoteException {
        getVimService().shutdownGuest(getMOR());
    }

    /**
     * @since SDK4.0
     */
    public Task startRecording_Task(String name, String description) throws RemoteException {
        ManagedObjectReference mor = getVimService().startRecording_Task(getMOR(), name, description);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task stopRecording_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().stopRecording_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task stopReplaying_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().stopReplaying_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task startReplaying_Task(VirtualMachineSnapshot replaySnapshot) throws RemoteException {
        ManagedObjectReference mor = getVimService().startReplaying_Task(getMOR(), replaySnapshot.getMOR());
        return new Task(getServerConnection(), mor);
    }

    public void standbyGuest() throws RemoteException {
        getVimService().standbyGuest(getMOR());
    }

    public Task suspendVM_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().suspendVM_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task terminateFaultTolerantVM_Task(VirtualMachine vm) throws RemoteException {
        ManagedObjectReference mor = getVimService().terminateFaultTolerantVM_Task(getMOR(),
                vm == null ? null : vm.getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.0
     */
    public Task turnOffFaultToleranceForVM_Task() throws RemoteException {
        ManagedObjectReference mor = getVimService().turnOffFaultToleranceForVM_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    public void unmountToolsInstaller() throws RemoteException {
        getVimService().unmountToolsInstaller(getMOR());
    }

    public void unregisterVM() throws RemoteException {
        getVimService().unregisterVM(getMOR());
    }

    public Task upgradeTools_Task(String installerOptions) throws RemoteException {
        ManagedObjectReference mor = getVimService().upgradeTools_Task(getMOR(), installerOptions);
        return new Task(getServerConnection(), mor);
    }

    public Task upgradeVM_Task(String version) throws RemoteException {
        ManagedObjectReference mor = getVimService().upgradeVM_Task(getMOR(), version);
        return new Task(getServerConnection(), mor);
    }

    /**
     * Send a non-maskable interrupt (NMI). Currently, there is no way to verify if the NMI was actually received by
     * the guest OS.
     *
     * @throws InvalidState
     * @throws RuntimeFault
     * @throws RemoteException
     * @since 6.0
     */
    public void sendNMI() throws InvalidState, RuntimeFault, RemoteException {
        getVimService().sendNMI(getMOR());
    }
}
