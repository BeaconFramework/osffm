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

import java.util.List;
import org.jclouds.openstack.keystone.v2_0.domain.Tenant;
import org.jclouds.openstack.keystone.v2_0.domain.User;

/**
 * This class need to be reviewed.
 * @author agalletta
 */
public class JcloudTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
     //   DBMongo mongo=new DBMongo();
     //   mongo.init("./cfg/configuration_bigDataPlugin.xml");
        
        //       KeystoneTest key=new KeystoneTest(mongo);

   //     KeystoneTest key=new KeystoneTest();
        //key.serviceGet("test");
   //     key.servicetList();
    //    key.listRole();
   //   Tenant t=key.createTenant();
   //   key.createUser(t);
    //  key.listUser();
   //   key.listRole();
   //     key.listTenant();
   
                GlanceTest glance=new GlanceTest();

       //    GlanceTest glance=new GlanceTest(mongo);
        
    //    glance.listImagesdatailsMongo();
       // List l=mongo.getListDB();
       // for(int i =0;i<l.size();i++){
       //     System.out.println(l.get(i));
       // }
  
   //   GlanceTest glance=new GlanceTest(mongo);
  //     glance.listRegions();
 //       glance.createImage();
      //  glance.deleteImage();
     //   glance.listImagesdatails();
   // glance.listImages();
        
  //      NovaTest nova=new NovaTest(mongo);
  //      nova.testQuota();
        //  nova.listServer2();
      //  nova.listServer2Mongo();
       // nova.stopVm();
      //  nova.startVm();
        
      // // nova.createFlavor();
     //   nova.listFlavors();
      //   nova.createvm();
    //   nova.getStatus();
        
       
        
     //  NeutronTest neutron = new NeutronTest();
//      neutron.listNetworks();
//neutron.listRegions();
//neutron.createRouter();
       // neutron.deleteNetworks();
      // neutron.createNetwork();
      // neutron.updateNetwork();
       //neutron.createNetwork2();
       
     //  neutron.createSubnet();
       
     //  neutron.listNetworks();
     //  neutron.listSubnet();


     //   neutron.getStatus();
   //    neutron.listRegions();
  //     neutron.listExtension();
       //neutron.createFloading();
        //NovaTest nova=new NovaTest();
        //nova.test();
             
            
    }
    
}