/**Copyright 2016, University of Messina.
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package JClouds_Adapter;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.inject.Module;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.NeutronApiMetadata;
import org.jclouds.openstack.neutron.v2.domain.AllocationPool;
import org.jclouds.openstack.neutron.v2.domain.ExtraDhcpOption;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP.CreateFloatingIP;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Network.CreateBuilder;
import org.jclouds.openstack.neutron.v2.domain.Network.CreateNetwork;
import org.jclouds.openstack.neutron.v2.domain.Network.UpdateBuilder;
import org.jclouds.openstack.neutron.v2.domain.Network.UpdateNetwork;
import org.jclouds.openstack.neutron.v2.domain.NetworkStatus;
import org.jclouds.openstack.neutron.v2.domain.Networks;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Ports;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.domain.Subnet.CreateSubnet;
import org.jclouds.openstack.neutron.v2.domain.Subnets;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.v2_0.features.ExtensionApi;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * This class need to be reviewed.
 * @author agalletta
 * @author gtricomi
 */
public class NeutronTest {
   
    private final NeutronApi neutronApi;
    private final Set<String> regions;
    private final String regionName;
   // private final ComputeService computeService;
    
 
    
 public NeutronTest() {
      Iterable<Module> modules = ImmutableSet.<Module>of( new SLF4JLoggingModule());
       //  Iterable<Module> modules = ImmutableSet.<Module>of( );
        
        String provider = "openstack-neutron";
        String identity = "admin:admin"; // tenantName:userName
        String credential = "password";
        this.regionName = "RegionOne";

        neutronApi = ContextBuilder.newBuilder(provider)
     //   neutronApi = ContextBuilder.newBuilder(new NeutronApiMetadata())
                .endpoint("http://172.17.4.113:35357/v2.0")
                .credentials(identity, credential)
                .modules(modules)
                .buildApi(NeutronApi.class);
        regions = neutronApi.getConfiguredRegions();

    }

    public NeutronTest(String endpoint, String tenant, String user, String password, String regionName) {
        // Iterable<Module> modules = ImmutableSet.<Module>of( new SLF4JLoggingModule());
        Iterable<Module> modules = ImmutableSet.<Module>of();

        String provider = "openstack-neutron";
        String identity = tenant + ":" + user; // tenantName:userName
        this.regionName = regionName;

        neutronApi = ContextBuilder.newBuilder(provider)
                //   neutronApi = ContextBuilder.newBuilder(new NeutronApiMetadata())
                .endpoint(endpoint)
                .credentials(identity, password)
                .modules(modules)
                .buildApi(NeutronApi.class);
        regions = neutronApi.getConfiguredRegions();

    }

    public void printListNetworks() {

        NetworkApi networkApi = neutronApi.getNetworkApi(regionName);
        Networks it2 = networkApi.list(new PaginationOptions());
        Iterator iter = it2.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    public boolean deleteNetwork(String uuidNet) {

        NetworkApi networkApi = neutronApi.getNetworkApi(regionName);

        return networkApi.delete(uuidNet);

    }

    public void createNetwork() {

        NetworkApi networkApi = neutronApi.getNetworkApi(regionName);
        Network net;
        CreateNetwork cn;
        CreateBuilder cb = Network.createBuilder("mt-test");

        cb = cb.adminStateUp(Boolean.TRUE);
        cb = cb.name("ert");
        cb = cb.shared(Boolean.TRUE);
        cb = cb.external(Boolean.TRUE);
        cb = cb.tenantId("d43c22b4619948d2b265bf082775780e");

        cn = cb.build();
        net = networkApi.create(cn);

        System.out.println(net.toString());

    }

    public void updateNetwork() {

        NetworkApi networkApi = neutronApi.getNetworkApi(regionName);
        Network net;
        UpdateNetwork cn;
        UpdateBuilder cb = Network.updateBuilder();

        // cb=cb.adminStateUp(Boolean.TRUE);
        cb = cb.name("new-name");
        // cb=cb.shared(Boolean.TRUE);
        //cb=cb.external(Boolean.TRUE);
        //cb=cb.tenantId("d43c22b4619948d2b265bf082775780e");

        cn = cb.build();
        net = networkApi.update("927918d7-d470-40fd-aa12-ebe32bfd90f9", cn);

        System.out.println(net.toString());

    }
 
 public void listNetworks(){//ok
     
     NetworkApi networkApi=neutronApi.getNetworkApi("RegionOne");
        Networks it2=networkApi.list(new PaginationOptions());
     Iterator  iter=it2.iterator();
     while(iter.hasNext()){
////        System.out.println(it2.first());
     System.out.println(iter.next());
    }
   }
 
  public void deleteNetworks(){//ok
     
     NetworkApi networkApi=neutronApi.getNetworkApi("RegionOne");
     
     
     networkApi.delete("c9c4b3a7-3aae-4252-ac76-48c15510b755");
     networkApi.delete("9a10e519-8143-4b2f-aafc-6547bbd023b2");
     networkApi.delete("83781ef8-4ad5-4f38-8b4f-92e8f291bee1");
     networkApi.delete("710b73af-ed7c-4e79-b5a0-a02124279e20");
     networkApi.delete("6d72cba5-751e-4f44-bbc2-694326b438b1");
     networkApi.delete("485b084f-d3ce-45bf-8aa0-865e1b752c87");
     networkApi.delete("39b58dac-4269-4bfd-9a7e-0032d8859de4");
     networkApi.delete("300cdf99-6334-4382-bc9c-2f06c139254a");
     networkApi.delete("214bfed5-e44e-4051-953e-b5edd0968770");
     networkApi.delete("13669208-ccef-407d-b954-0c719125b0db");
  
   }
 

    public void createNetwork2() {

        NetworkApi networkApi = neutronApi.getNetworkApi(regionName);
        Network net;
        CreateNetwork cn;
        CreateBuilder cb = Network.createBuilder("jclouds-test2");

        cb.adminStateUp(Boolean.TRUE);
        cb.shared(Boolean.TRUE);
        cb.external(Boolean.TRUE);
        cb.tenantId("b809dcb956a74b009ac728931536d04e");

        cn = cb.build();
        net = networkApi.create(cn);

        System.out.println(net.toString());

    }
   
    public NetworkStatus getStatus(String netId) {

        NetworkApi networkApi = neutronApi.getNetworkApi(regionName);
        Network net = networkApi.get(netId);
        return net.getStatus();

    }
 
    public void createFloading() {

        Optional<FloatingIPApi> floading = neutronApi.getFloatingIPApi(regionName);
        FloatingIP.CreateBuilder fl = FloatingIP.createBuilder("927918d7-d470-40fd-aa12-ebe32bfd90f9");
        fl.fixedIpAddress("192.168.0.34");
        CreateFloatingIP crg = fl.build();
        FloatingIPApi fload = floading.get();

        // crg.getFloatingIpAddress()
        FloatingIP f = fload.create(crg);

        System.out.println(f.toString());

    }
  
    public void printListExtension() {

        ExtensionApi ext = neutronApi.getExtensionApi(regionName);

        System.out.println(ext);
        Set s = ext.list();

        Iterator it = s.iterator();

        while (it.hasNext()) {

            System.out.println(it.next());

        }

    }

    public void listRegions(){


      Iterator it=regions.iterator();

      while(it.hasNext()){

          System.out.println(it.next());

      }

   }

   public void printListSubnet() {

         SubnetApi subnetApi = neutronApi.getSubnetApi(regionName);
         Subnets it2 = subnetApi.list(new PaginationOptions());
         Iterator iter = it2.iterator();

         while (iter.hasNext()) {

             System.out.println(iter.next());
         }
     }


    public void listSubnet(){

       SubnetApi subnetApi=neutronApi.getSubnetApi("RegionOne");
       Subnets it2=subnetApi.list(new PaginationOptions());
       Iterator  iter=it2.iterator();

       while(iter.hasNext()){

           System.out.println(iter.next());
      }
    }

  
    public void createSubnet(){

        SubnetApi subnetApi=neutronApi.getSubnetApi("RegionOne");
        CreateSubnet cs;
        AllocationPool ap;
        AllocationPool.Builder apb;
        org.jclouds.openstack.neutron.v2.domain.Subnet.CreateBuilder cb;
        cb=Subnet.createBuilder("927918d7-d470-40fd-aa12-ebe32bfd90f9", "192.168.0.0/24");

        List<AllocationPool> pool = new ArrayList<>();

        apb=new AllocationPool.Builder();
        apb.start("192.168.0.5");
        apb.end("192.168.0.253");

        ap=apb.build();
        pool.add(ap);

        cb.name("mysubnet");
        cb.ipVersion(4);
        cb.allocationPools(pool);
        cb.gatewayIp("192.168.0.1");
        cb.enableDhcp(Boolean.TRUE);

        cs=cb.build();

        subnetApi.create(cs);

    }

    public void createRouter() {

        //  Optional<RouterApi> router=neutronApi.getRouterApi("RegionOne");
        for (String region : neutronApi.getConfiguredRegions()) {
            RouterApi routerApi = neutronApi.getRouterApi(region).get();
            NetworkApi networkApi = neutronApi.getNetworkApi(region);
            SubnetApi subnetApi = neutronApi.getSubnetApi(region);

        }

    }

    public Port getPort(String portId) {
        //passare uuid macchina
        PortApi portApi = neutronApi.getPortApi(regionName);

        Port port = portApi.get(portId);

        return port;
    }

    public ArrayList<Port> getPortFromDeviceId(String deviceId) {//questo si deve usare
        //passare uuid macchina
        PortApi portApi = neutronApi.getPortApi(regionName);

        Ports ports = portApi.list(new PaginationOptions());
        Port p = null;
        Iterator<Port> iter = ports.iterator();
        ArrayList<Port> arp=new ArrayList<Port>();
        while (iter.hasNext()) {
            p = iter.next();
            if (deviceId.equals(p.getDeviceId())) {
                arp.add(p);
            }
        }
        return arp;
    }

    public void listPorts() {
//passare uuid macchina
        PortApi portApi = neutronApi.getPortApi(regionName);

        Ports it2 = portApi.list(new PaginationOptions());
        Port p;
        Iterator<Port> iter = it2.iterator();
        while (iter.hasNext()) {
            p = iter.next();
            System.out.println(p);
        }

    }

    public JSONObject portToJson(Port port) {

        JSONObject obj = new JSONObject();

        try {
            obj.put("adminStateUp", port.getAdminStateUp());
            obj.put("allowedAddressPairs", port.getAllowedAddressPairs());
            obj.put("id", port.getId());
            obj.put("status", port.getStatus().toString());
            obj.put("vifDetails", this.mapToJSON(port.getVifDetails()));
            obj.put("qosQueueId", port.getQosQueueId());
            obj.put("name", port.getName());
            obj.put("networkId", port.getNetworkId());
            obj.put("macAddress", port.getMacAddress());
            obj.put("fixedIps", this.ipToArray(port.getFixedIps()));
            obj.put("deviceId", port.getDeviceId());
            obj.put("deviceOwner", port.getDeviceOwner());
            obj.put("tenantId", port.getTenantId());
            JSONArray array = new JSONArray();
            array.put(port.getSecurityGroups());
            obj.put("securityGroups", array);
            obj.put("extraDhcpOptions", this.dhcpOptToArray(port.getExtraDhcpOptions()));
            obj.put("vnicType", port.getVnicType().toString());
            obj.put("hostId", port.getHostId());
            obj.put("profile", this.mapToJSON(port.getProfile()));
            obj.put("portSecurity", port.getPortSecurity());
            obj.put("profileId", port.getProfileId());
            obj.put("macLearning", port.getMacLearning());
            obj.put("qosRxtxFactor", port.getQosRxtxFactor());
            obj.put("vifType", port.getVifType().toString());

        } catch (Exception e) {

            e.printStackTrace();

        }
        return obj;
    }

    public String portToString(Port port) {

        JSONObject obj = this.portToJson(port);
        return obj.toString();

    }

    private JSONArray ipToArray(ImmutableSet<IP> list) {

        JSONArray ipArray = new JSONArray();
        UnmodifiableIterator<IP> it = list.iterator();
        JSONObject ipJson;
        IP ip;
        // Map <String,Object> output;
        try {
            while (it.hasNext()) {
                ip = it.next();

                ipJson = new JSONObject();
                ipJson.put("ipAddress", ip.getIpAddress());
                ipJson.put("subnetId", ip.getSubnetId());
                ipArray.put(ipJson);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ipArray;
    }

    // ImmutableSet<ExtraDhcpOption>
    private JSONArray dhcpOptToArray(ImmutableSet<ExtraDhcpOption> list) {

        JSONArray dhcpOptArray = new JSONArray();
        UnmodifiableIterator<ExtraDhcpOption> it = list.iterator();
        JSONObject dhcpOptJson;
        ExtraDhcpOption dhcpOpt;
        // Map <String,Object> output;
        try {
            while (it.hasNext()) {
                dhcpOpt = it.next();
                dhcpOptJson = new JSONObject();
                dhcpOptJson.put("id", dhcpOpt.getId());
                dhcpOptJson.put("optionName", dhcpOpt.getOptionName());
                dhcpOptJson.put("optionValue", dhcpOpt.getOptionValue());
                dhcpOptArray.put(dhcpOptJson);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dhcpOptArray;
    }

//I 
    private JSONObject mapToJSON(ImmutableMap<String, Object> map) {

        JSONObject oggetto = null;

        if (map != null) {
            oggetto = new JSONObject(map);
        }

        return oggetto;
    }
}