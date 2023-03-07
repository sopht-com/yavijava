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

import com.vmware.vim25.DasConfigFault;
import com.vmware.vim25.HostCapability;
import com.vmware.vim25.HostConfigFault;
import com.vmware.vim25.HostConfigInfo;
import com.vmware.vim25.HostConfigManager;
import com.vmware.vim25.HostConnectFault;
import com.vmware.vim25.HostConnectInfo;
import com.vmware.vim25.HostConnectSpec;
import com.vmware.vim25.HostFlagInfo;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.HostIpmiInfo;
import com.vmware.vim25.HostLicensableResourceInfo;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostMaintenanceSpec;
import com.vmware.vim25.HostPowerOpFailed;
import com.vmware.vim25.HostRuntimeInfo;
import com.vmware.vim25.HostServiceTicket;
import com.vmware.vim25.HostSystemReconnectSpec;
import com.vmware.vim25.HostSystemResourceInfo;
import com.vmware.vim25.HostSystemSwapConfiguration;
import com.vmware.vim25.HostTpmAttestationReport;
import com.vmware.vim25.InvalidIpmiLoginInfo;
import com.vmware.vim25.InvalidIpmiMacAddress;
import com.vmware.vim25.InvalidLogin;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.NotSupported;
import com.vmware.vim25.RequestCanceled;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.Timedout;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.mo.util.MorUtil;

import java.rmi.RemoteException;

/**
 * The managed object class corresponding to the one defined in VI SDK API reference.
 *
 * @author Steve JIN (http://www.doublecloud.org)
 */

public class HostSystem extends ManagedEntity {

    private HostConfigManager configManager = null;

    public HostSystem(ServerConnection sc, ManagedObjectReference mor) {
        super(sc, mor);
    }

    public HostCapability getCapability() {
        return (HostCapability) getCurrentProperty("capability");
    }

    public HostConfigInfo getConfig() {
        return (HostConfigInfo) getCurrentProperty("config");
    }

    public Datastore[] getDatastores() throws RemoteException {
        return getDatastores("datastore");
    }

    public HostDatastoreBrowser getDatastoreBrowser() throws RemoteException {
        return (HostDatastoreBrowser) getManagedObject("datastoreBrowser");
    }

    public HostHardwareInfo getHardware() {
        return (HostHardwareInfo) getCurrentProperty("hardware");
    }

    /**
     * @since SDK5.0
     */
    public HostLicensableResourceInfo getLicensableResource() {
        return (HostLicensableResourceInfo) getCurrentProperty("licensableResource");
    }

    public Network[] getNetworks() throws RemoteException {
        return getNetworks("network");
    }

    public HostRuntimeInfo getRuntime() {
        return (HostRuntimeInfo) getCurrentProperty("runtime");
    }

    public HostListSummary getSummary() {
        return (HostListSummary) getCurrentProperty("summary");
    }

    public HostSystemResourceInfo getSystemResources() {
        return (HostSystemResourceInfo) getCurrentProperty("systemResources");
    }

    public VirtualMachine[] getVms() throws RemoteException {
        return getVms("vm");
    }

    public HostServiceTicket acquireCimServicesTicket() throws RemoteException {
        return getVimService().acquireCimServicesTicket(getMOR());
    }

    public Task disconnectHost() throws RemoteException {
        ManagedObjectReference mor = getVimService().disconnectHost_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.1
     */
    public void enterLockdownMode() throws RemoteException {
        getVimService().enterLockdownMode(getMOR());
    }

    /**
     * keep the old signature for compatibility
     */
    public Task enterMaintenanceMode(int timeout, boolean evacuatePoweredOffVms) throws RemoteException {
        return enterMaintenanceMode(timeout, evacuatePoweredOffVms, null);
    }

    /**
     * @since SDK5.5
     */
    public Task enterMaintenanceMode(int timeout, boolean evacuatePoweredOffVms, HostMaintenanceSpec maintenanceSpec) throws RemoteException {
        ManagedObjectReference mor = getVimService().enterMaintenanceMode_Task(getMOR(), timeout, Boolean.valueOf(evacuatePoweredOffVms), maintenanceSpec);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.1
     */
    public void exitLockdownMode() throws RemoteException {
        getVimService().exitLockdownMode(getMOR());
    }

    public Task exitMaintenanceMode(int timeout) throws RemoteException {
        ManagedObjectReference mor = getVimService().exitMaintenanceMode_Task(getMOR(), timeout);
        return new Task(getServerConnection(), mor);
    }

    public Task powerDownHostToStandBy(int timeSec, boolean evacuatePoweredOffVms) throws RemoteException {
        ManagedObjectReference mor = getVimService().powerDownHostToStandBy_Task(getMOR(), timeSec, Boolean.valueOf(evacuatePoweredOffVms));
        return new Task(getServerConnection(), mor);
    }

    public Task powerUpHostFromStandBy(int timeSec) throws RemoteException {
        ManagedObjectReference mor = getVimService().powerUpHostFromStandBy_Task(getMOR(), timeSec);
        return new Task(getServerConnection(), mor);
    }

    public HostConnectInfo queryHostConnectionInfo() throws RemoteException {
        return getVimService().queryHostConnectionInfo(getMOR());
    }

    /**
     * @since SDK5.1
     */
    public HostTpmAttestationReport queryTpmAttestationReport() throws RemoteException {
        return getVimService().queryTpmAttestationReport(getMOR());
    }

    /**
     * @since SDK5.1
     */
    public void updateSystemSwapConfiguration(HostSystemSwapConfiguration sysSwapConfig) throws RemoteException {
        getVimService().updateSystemSwapConfiguration(getMOR(), sysSwapConfig);
    }

    public long queryMemoryOverhead(long memorySize, int videoRamSize, int numVcpus) throws RemoteException {
        return getVimService().queryMemoryOverhead(getMOR(), memorySize, new Integer(videoRamSize), numVcpus);
    }

    public long queryMemoryOverheadEx(VirtualMachineConfigInfo vmConfigInfo) throws RemoteException {
        return getVimService().queryMemoryOverheadEx(getMOR(), vmConfigInfo);
    }

    public Task rebootHost(boolean force) throws RemoteException {
        ManagedObjectReference mor = getVimService().rebootHost_Task(getMOR(), force);
        return new Task(getServerConnection(), mor);
    }

    public Task reconfigureHostForDAS() throws RemoteException {
        ManagedObjectReference mor = getVimService().reconfigureHostForDAS_Task(getMOR());
        return new Task(getServerConnection(), mor);
    }

    //SDK4.1 signature for back compatibility
    public Task reconnectHost_Task(HostConnectSpec hcs) throws RemoteException {
        return reconnectHost_Task(hcs, null);
    }

    //SDK5.0 signature
    public Task reconnectHost_Task(HostConnectSpec cnxSpec, HostSystemReconnectSpec reconnectSpec) throws RemoteException {
        ManagedObjectReference mor = getVimService().reconnectHost_Task(getMOR(), cnxSpec, reconnectSpec);
        return new Task(getServerConnection(), mor);
    }

    /**
     * @since SDK4.1
     */
    public long retrieveHardwareUptime() throws RemoteException {
        return getVimService().retrieveHardwareUptime(getMOR());
    }

    public Task shutdownHost_Task(boolean force) throws RemoteException {
        ManagedObjectReference mor = getVimService().shutdownHost_Task(getMOR(), force);
        return new Task(getServerConnection(), mor);
    }

    public void updateFlags(HostFlagInfo hfi) throws RemoteException {
        getVimService().updateFlags(getMOR(), hfi);
    }

    public void updateSystemResources(HostSystemResourceInfo resourceInfo) throws RemoteException {
        getVimService().updateSystemResources(getMOR(), resourceInfo);
    }

    /**
     * @since 4.0
     */
    public void updateIpmi(HostIpmiInfo ipmiInfo) throws RemoteException {
        getVimService().updateIpmi(getMOR(), ipmiInfo);
    }

    private HostConfigManager getConfigManager() throws RemoteException {
        if (configManager == null) {
            configManager = (HostConfigManager) getCurrentProperty("configManager");
        }
        return configManager;
    }

    public OptionManager getOptionManager() throws RemoteException {
        return new OptionManager(getServerConnection(),
                getConfigManager().getAdvancedOption());
    }

    public HostAutoStartManager getHostAutoStartManager() throws RemoteException {
        return new HostAutoStartManager(getServerConnection(),
                getConfigManager().getAutoStartManager());
    }

    public HostBootDeviceSystem getHostBootDeviceSystem() throws RemoteException {
        return (HostBootDeviceSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getBootDeviceSystem());
    }

    public HostDateTimeSystem getHostDateTimeSystem() throws RemoteException {
        return (HostDateTimeSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getDateTimeSystem());
    }

    public HostDiagnosticSystem getHostDiagnosticSystem() throws RemoteException {
        return (HostDiagnosticSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getDiagnosticSystem());
    }

    public HostEsxAgentHostManager getHostEsxAgentHostManager() throws RemoteException {
        return (HostEsxAgentHostManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getEsxAgentHostManager());
    }

    public HostCacheConfigurationManager getHostCacheConfigurationManager() throws RemoteException {
        return (HostCacheConfigurationManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getCacheConfigurationManager());
    }

    public HostCpuSchedulerSystem getHostCpuSchedulerSystem() throws RemoteException {
        return (HostCpuSchedulerSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getCpuScheduler());
    }

    public HostDatastoreSystem getHostDatastoreSystem() throws RemoteException {
        return (HostDatastoreSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getDatastoreSystem());
    }

    public HostFirmwareSystem getHostFirmwareSystem() throws RemoteException {
        return (HostFirmwareSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getFirmwareSystem());
    }

    /**
     * @since SDK5.5
     */
    public HostGraphicsManager getHostGraphicsManager() throws RemoteException {
        return (HostGraphicsManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getGraphicsManager());
    }

    /**
     * @since SDK4.0
     */
    public HostKernelModuleSystem getHostKernelModuleSystem() throws RemoteException {
        return (HostKernelModuleSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getKernelModuleSystem());
    }

    /**
     * @since SDK4.0
     */
    public LicenseManager getLicenseManager() throws RemoteException {
        return (LicenseManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getLicenseManager());
    }

    /**
     * @since SDK4.0
     */
    public HostPciPassthruSystem getHostPciPassthruSystem() throws RemoteException {
        return (HostPciPassthruSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getPciPassthruSystem());
    }

    /**
     * @since SDK4.0
     */
    public HostVirtualNicManager getHostVirtualNicManager() throws RemoteException {
        return (HostVirtualNicManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getVirtualNicManager());
    }

    public HostHealthStatusSystem getHealthStatusSystem() throws RemoteException {
        return (HostHealthStatusSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getHealthStatusSystem());
    }

    public HostFirewallSystem getHostFirewallSystem() throws RemoteException {
        return (HostFirewallSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getFirewallSystem());
    }

    public HostImageConfigManager getHostImageConfigManager() throws RemoteException {
        return (HostImageConfigManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getImageConfigManager());
    }

    public HostMemorySystem getHostMemorySystem() throws RemoteException {
        return (HostMemorySystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getMemoryManager());
    }

    public HostNetworkSystem getHostNetworkSystem() throws RemoteException {
        return (HostNetworkSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getNetworkSystem());
    }

    public HostPatchManager getHostPatchManager() throws RemoteException {
        return (HostPatchManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getPatchManager());
    }

    public HostServiceSystem getHostServiceSystem() throws RemoteException {
        return (HostServiceSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getServiceSystem());
    }

    public HostSnmpSystem getHostSnmpSystem() throws RemoteException {
        return (HostSnmpSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getSnmpSystem());
    }

    public HostStorageSystem getHostStorageSystem() throws RemoteException {
        return (HostStorageSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getStorageSystem());
    }

    /**
     * @since SDK5.5
     */
    public HostVFlashManager getHostVFlashManager() throws RemoteException {
        return (HostVFlashManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getVFlashManager());
    }

    public IscsiManager getIscsiManager() throws RemoteException {
        return (IscsiManager) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getIscsiManager());
    }

    /**
     * @deprecated as of SDK 4.0, use getHostVirtualNicManager instead
     */
    public HostVMotionSystem getHostVMotionSystem() throws RemoteException {
        return (HostVMotionSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getVmotionSystem());
    }

    /**
     * @since SDK5.5
     */
    public HostVsanInternalSystem getHostVsanInternalSystem() throws RemoteException {
        return (HostVsanInternalSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getVsanInternalSystem());
    }

    /**
     * @since SDK5.5
     */
    public HostVsanSystem getHostVsanSystem() throws RemoteException {
        return (HostVsanSystem) MorUtil.createExactManagedObject(getServerConnection(),
                getConfigManager().getVsanSystem());
    }
}
