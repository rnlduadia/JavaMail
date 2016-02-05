/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package managemail;

/**
 *
 * @author Administrator
 */
public class AccountContact {
    private String name;
    private String position;
    private String id;
    private String server;    

    
    public void setName(String name){
        this.name = name;
    }
    public void setPosition(String position){
        this.position = position;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setServer(String server){
        this.server = server;
    }
    
    public String getName(){
        return this.name;
    }
    public String getPosition(){
        return this.position;
    }
    public String getId(){
        return this.id;
    }
    public String getServer(){
        return this.server;
    }
    
}
