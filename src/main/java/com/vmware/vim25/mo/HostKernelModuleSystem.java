package com.vmware.vim25.mo;

import com.vmware.vim25.KernelModuleInfo;
import com.vmware.vim25.ManagedObjectReference;

import java.rmi.RemoteException;

public class HostKernelModuleSystem extends ManagedObject {
    public HostKernelModuleSystem(ServerConnection sc, ManagedObjectReference mor) {
        super(sc, mor);
    }

    public String queryConfiguredModuleOptionString(String name) throws RemoteException {
        return getVimService().queryConfiguredModuleOptionString(getMOR(), name);
    }

    public KernelModuleInfo[] queryModules() throws RemoteException {
        return getVimService().queryModules(getMOR());
    }

    public void updateModuleOptionString(String name, String options) throws RemoteException {
        getVimService().updateModuleOptionString(getMOR(), name, options);
    }

}
