/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.SOUTHBR;

import static API.SOUTHBR.FA_REST_Client.LOGGER;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Exception.WSException;

/**
 *
 * @author Giuseppe Tricomi
 */
public class FA_client4Sites extends FA_REST_Client{
    private String siteName="",faIP="",faPort="";
    public FA_client4Sites(String endpoint,String tenantName,String userName,String password){
        super(endpoint, tenantName,userName,password);
    }
    
    /**
     * 
     * @param TenantId
     * @param faURL
     * @param body, This JSONObject contains a JSONArray that is composed by JSONObject made by 4 elements: name,tenant_id,fa_url,site_proxy. Last element is a JSONArray composed by ip and port of FA_datapath.
     * @return
     * @throws WSException
     * @author gtricomi
     */
    public boolean createSiteTable(String TenantId, String faURL,String body)throws WSException{
        boolean result= true;
        HttpBasicAuthFilter auth=new HttpBasicAuthFilter(this.getUserName(), this.getPassword());
        Response r=this.createInsertingrequest("http://"+faURL+"/net-fa/tenants/"+TenantId+"/sites",body,auth,"put",MediaType.APPLICATION_JSON);
        
        
        try{
            this.checkResponse(r);//as answer we expect a status code 200
        }
        catch(WSException wse){
            LOGGER.error("Exception occurred in createTenantFA method, the web service has answer with bad status!\n"+wse.getMessage());
            result=false;
            throw wse;
        }
        return result;
    }
    /**
     * This function prepare the object for FA Create Site Table function.
     * @param sites
     * @return 
     * @author gtricomi
     */
    public String constructSiteTableJSON(ArrayList<HashMap<String,Object>> sites){
        //>>>BEACON this String need to be reviewed
        String result="";
        String tmp="[";
        boolean first=true;
        JSONArray ja=new JSONArray();
        try{
        for(HashMap elem:sites){
            /*
            [
            {"tenant_id": "ab6a28b9f3624f4fa46e78247848544e",
            "name": "site1",
            "site_proxy": [{"ip": "10.0.0.33", "port": 4789}],
            "fa_url": "10.0.0.33:4567"},
            {"tenant_id": "0ce39f6ae8044445b31d5b7f9b34062b",
            "name": "site2",
            "site_proxy": [{"ip": "10.0.0.38", "port": 4789}],
            "fa_url": "10.0.0.38:4567"}]
            */
            if(!first)
                tmp=tmp+", ";
            tmp=tmp+"{";
            tmp=tmp+("\"tenant_id\": \""+elem.get("tenant_id")+"\", ");
            tmp=tmp+("\"name\": \""+elem.get("name")+"\", ");
            tmp=tmp+("\"site_proxy\": [{\"ip\": \""+elem.get("site_proxyip")+"\", \"port\": "+elem.get("site_proxyport")+"}], ");
            tmp=tmp+("\"fa_url\": \""+elem.get("fa_url")+"\"");
            tmp=tmp+"}";
            first=false;
        }
        }
        catch(Exception e){
            
        }
        tmp=tmp+"]";
        return tmp;    
    }
    
}