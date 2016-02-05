/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package managemail;

/**
 *
 * @author Administrator
 */
public class Account {
    private String name;
    private String position;
    private String id;
    private String server;
    private String path;
    private String pwd;
    private String fileinfo;
    //private String host="190.1.100.215";
    private String host="127.0.0.1";

    public void setFileinfo(String fileinfo){
        this.fileinfo = fileinfo;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }
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
    public void setPath(String path){
        this.path = path;
    }
    public String getFileinfo(){
        return this.fileinfo;
    }
    public String getPwd(){
        return this.pwd;
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
    public String getPath(){
        return this.path;
    }
    public String getHost(){
        return this.host;
    }
}
